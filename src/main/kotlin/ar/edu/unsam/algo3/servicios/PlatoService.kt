package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.plato.PlatoDTO
import ar.edu.unsam.algo3.modelo.plato.toDTO
import ar.edu.unsam.algo3.mock.LocalPollos
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.plato.fromDTO
import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.toDOM
import ar.edu.unsam.algo3.repositorio.RepositorioPlato
import org.springframework.stereotype.Service

/*
*   REPOSITORIOS:
*       No tira errores. Usemos mismos nombres para metodos (en service tambien, los que sean generales).
*
*   DOMINIOS:
*       Lista de ingredientes solo con IDs, los ingredientes los sacamos del repositorio de ingredientes. NO son DTOs.
*
*   CONTROLLERS:
*       Serializamos en el controller siempre que se pueda. Flexibilizable segun diseño.
*
*   SERVICES:
*       Devolvemos objetos de dominio siempre y cuando convenga.
*       NO inyectar un service en otro. SI se puede inyectar mas de 1 service.
*
*   Endpoint de local nos trae TODO lo de local.
* */

@Service
class PlatoService(
    val repositorioPlatos: RepositorioPlato
) {

    fun getPlatos(): List<PlatoDTO> =
        repositorioPlatos.objetosDeRepositorio().map { it.toDTO() }

    fun obtenerPlato(id: Int): Plato {
//        obtenerObjeto ya hace la validacion de existencia del plato en el repo
        val platoModelo = repositorioPlatos.obtenerObjeto(id)
        return platoModelo
    }

    fun crearPlato(platoDTO: PlatoDTO) {
        var platoDOM = Plato(
            nombre = platoDTO.nombre,
            descripcion = platoDTO.descripcion,
            valorBase = platoDTO.precio,
            urldeImagen = platoDTO.imagen,
            esDeAutor = platoDTO.esDeAutor,
            ingredientes = platoDTO.ingredientes.map { it.id },
            local = LocalPollos, // en ningun lugar pones el local no se como seria
        )
        repositorioPlatos.crear(platoDOM)
    }

//    fun modificarPlato(id: Int, plato: PlatoDTO) {
//        val platoDOM: Plato = Plato().fromDTO(plato).apply { this.id = id }
//        repositorioPlatos.actualizar(platoDOM)
//    }

    fun modificarPlato(platoUpdate: PlatoDTO) {
//        Buscar plato en repositorio
        val platoAModificar = this.obtenerPlato(platoUpdate.id)
        val ingredientesViejos = platoAModificar.ingredientes
        val ingredientesNuevos = platoUpdate.ingredientes
//        Diferencia de conjuntos y metemos en la lista los ingredientes traidos por ID del repositorio
    }


//    Mostrar a pablo
    private fun prepararPlatosParaIngrediente(ingrediente: Ingrediente, accion: (Plato) -> Unit) {

        val platosConIng = repositorioPlatos.objetosDeRepositorio()
            .filter { plato -> plato.ingredientes.any { ing -> ing.id == ingrediente.id } }

        platosConIng.forEach { plato ->
            plato.ingredientes.removeIf { ing -> ing.id == ingrediente.id }
        }

        platosConIng.forEach { plato ->
            accion(plato)
            repositorioPlatos.actualizar(plato)
        }
    }

    fun actualizarIngrediente(ingrediente: Ingrediente) {
        prepararPlatosParaIngrediente(ingrediente) { // syntactic sugar. Lambda despues de funcion. Se puede pasar como parametro tambien.
            plato -> plato.ingredientes.add(ingrediente)
        }
    }

    fun eliminarIngrediente(ingrediente: Ingrediente) {
        prepararPlatosParaIngrediente(ingrediente) {}
    }
}