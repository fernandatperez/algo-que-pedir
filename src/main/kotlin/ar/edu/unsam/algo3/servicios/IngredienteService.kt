package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.DTO.IngredienteDTO
import ar.edu.unsam.algo3.DTO.toDTO
import ar.edu.unsam.algo3.errores.JSONVacioException
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.repositorio.Repositorio
import ar.edu.unsam.algo3.repositorio.RepositorioIngredientes
import kotlinx.serialization.json.*
import org.springframework.stereotype.Service

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
class IngredienteService(
    val repositorioIngredientes: RepositorioIngredientes
) {
    fun ingredientes(): List<IngredienteDTO> {
        return repositorioIngredientes.objetosDeRepositorio().map { it.toDTO() }
    }

    fun ingredientePorId(id: Int): IngredienteDTO {
        return repositorioIngredientes.obtenerObjeto(id).toDTO()
    }

    fun crearIngrediente(ingredienteDTO: IngredienteDTO): IngredienteDTO {
        val ingredienteNuevo = repositorioIngredientes.crear(
            name = ingredienteDTO.name,
            cost = ingredienteDTO.cost,
            esOrigenAnimal = ingredienteDTO.esOrigenAnimal,
            foodGroup = ingredienteDTO.foodGroup
        )
        return ingredienteNuevo.toDTO()
    }

    fun actualizarIngrediente(id: Int,ingredienteDTO: IngredienteDTO): IngredienteDTO {
        val ingredienteActualizado = Ingrediente(
            nombre = ingredienteDTO.name,
            costoMercado = ingredienteDTO.cost,
            esOrigenAnimal = ingredienteDTO.esOrigenAnimal,
            grupoAlimenticio = ingredienteDTO.foodGroup
        ).apply { this.id = id }

        repositorioIngredientes.actualizar(ingredienteActualizado)
        return ingredienteActualizado.toDTO()
    }

    fun eliminarIngrediente(id: Int) {
        repositorioIngredientes.eliminarDeColeccion(id)
    }
}