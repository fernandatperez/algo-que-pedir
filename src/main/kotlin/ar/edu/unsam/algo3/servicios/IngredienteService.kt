package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.toDOM
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.errores.JSONVacioException
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.repositorio.Repositorio
import ar.edu.unsam.algo3.repositorio.RepositorioIngrediente
import kotlinx.serialization.json.*
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.repositorio.RepositorioPlato


open class ActualizadorIngredientes(
    val service: ServiceIngredientes,
    val repositorio: Repositorio<Ingrediente>
) {
    fun actualizarIng() {
        val jsonString = service.getIngredientes()
        val ingredienteActualizar = Json.decodeFromString<List<Ingrediente>>(jsonString)

        if (jsonVacio(ingredienteActualizar)) throw JSONVacioException("El JSON pareciera no tener contenido o hubo un error al tipearlo")
        ingredienteActualizar.forEach { ingrediente ->
            if (ingrediente.cumpleCriterioDeNuevo()) {
                repositorio.crear(ingrediente)
            } else if (repositorio.objetoEnColeccion(ingrediente.id)) {
                repositorio.actualizar(ingrediente)
            } // else throw IdInexistente("El ingrediente con id = ${ingrediente.id} no existe en la coleccion o no hay ningun objeto asociado a el")
        }
    }

    fun jsonVacio(listaIngredientes: List<Ingrediente>) = listaIngredientes.isEmpty()
}

interface ServiceIngredientes {
    fun getIngredientes(): String
}

class ServicioMock(val mensaje: String) : ServiceIngredientes {
    override fun getIngredientes(): String {
        return mensaje
    }
}

class InstanciaActualizador(
    mensaje: String,
    repositorio: Repositorio<Ingrediente>
) : ActualizadorIngredientes(
    service = ServicioMock(mensaje),
    repositorio = repositorio
) {}

// ========= LO NUEVO =========
@Service
class IngredienteService( val repositorioIngredientes: RepositorioIngrediente, val repositorioPlatos: RepositorioPlato) {
    fun ingredientes(): List<IngredienteDTO> =
        repositorioIngredientes.objetosDeRepositorio().map { it.toDTO() }

    fun ingredientePorId(id: Int): IngredienteDTO =
        repositorioIngredientes.obtenerObjeto(id)?.toDTO() ?: throw RuntimeException("No se encontró el pedido de id <$id>")

    fun crearIngrediente(ingredienteDTO: IngredienteDTO) {
        val ingredienteNuevo = Ingrediente(
            nombre = ingredienteDTO.name,
            costoMercado = ingredienteDTO.cost,
            esOrigenAnimal = ingredienteDTO.esOrigenAnimal,
            grupoAlimenticio = ingredienteDTO.foodGroup
        )
        ingredienteNuevo.cumpleCriterioDeCreacion()
        repositorioIngredientes.crear(ingredienteNuevo)
    }

    fun actualizarIngrediente(ingredienteDTO: IngredienteDTO): IngredienteDTO {
        val ingredienteActualizado = Ingrediente(
            nombre = ingredienteDTO.name,
            costoMercado = ingredienteDTO.cost,
            esOrigenAnimal = ingredienteDTO.esOrigenAnimal,
            grupoAlimenticio = ingredienteDTO.foodGroup
        ).apply { this.id = ingredienteDTO.id }

        ingredienteActualizado.cumpleCriterioDeCreacion()
        repositorioIngredientes.actualizar(ingredienteActualizado)

        return ingredienteActualizado.toDTO()
    }

    fun eliminarIngrediente(id: Int) {
        if (repositorioPlatos.algunoContieneIngrediente(id))
            throw BusinessException("No se puede eliminar el ingrediente por que algun plato aun lo tiene")
        repositorioIngredientes.eliminarDeColeccion(id)
    }
}