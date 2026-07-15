package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.errores.JSONVacioException
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.repositorio.Repositorio
import ar.edu.unsam.algo3.repositorio.RepositorioIngrediente
import kotlinx.serialization.json.*
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.errores.ConflictException
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.repositorio.RepositorioPlato
open class ActualizadorIngredientes(
    val service: ServiceIngredientes,
    val repositorio: Repositorio<Ingrediente>,
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
    val repositorioIngredientes: RepositorioIngrediente,
    val repositorioPlatos: RepositorioPlato,
    private val repositorioLocal: RepositorioLocal
) {
    fun ingredientes(): List<Ingrediente> =
        repositorioIngredientes.objetosDeRepositorio()

    fun ingredientePorId(id: Int): Ingrediente =
        repositorioIngredientes.obtenerObjeto(id) ?: throw NotFoundException("No se encontró el ingrediente de id <$id>")

    fun crearIngrediente(ingredienteNuevo: Ingrediente) {
        ingredienteNuevo.cumpleCriterioDeCreacion()
        if (repositorioIngredientes.ingredienteYaExiste(ingredienteNuevo))
            throw ConflictException("El ingrediente '${ingredienteNuevo.nombre}' $${ingredienteNuevo.costoMercado} ya existe.")

        repositorioIngredientes.crear(ingredienteNuevo)
    }

    fun actualizarIngrediente(ingredienteActualizado: IngredienteDTO,): Ingrediente {
        val ingredienteAModificar = ingredienteActualizado.fromDTO()
        ingredienteAModificar.cumpleCriterioDeCreacion()
        repositorioIngredientes.actualizar(ingredienteAModificar)

        return ingredienteAModificar
    }

    fun eliminarIngrediente(id: Int) {
        repositorioIngredientes.eliminarDeColeccion(id)
    }
}