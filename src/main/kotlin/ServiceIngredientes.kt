package ar.edu.unsam.algo2.algoQuePedir

import kotlinx.serialization.json.*

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