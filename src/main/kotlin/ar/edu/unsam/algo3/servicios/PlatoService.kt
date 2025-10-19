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

@Service
class PlatoService(
    val repositorioPlatos: RepositorioPlato
) {

    fun getPlatos(): List<PlatoDTO> =
        repositorioPlatos.objetosDeRepositorio().map { it.toDTO() }

    fun obtenerPlato(id: Int): PlatoDTO {
//        obtenerObjeto ya hace la validacion de existencia del plato en el repo
        val platoModelo = repositorioPlatos.obtenerObjeto(id)
        return platoModelo.toDTO()
    }
// aca no vas a tener ID, te lo devuelve la BBDD (el repo de algo2)
//get create find update delete count search --> metodos estandar en controller y repo
    fun crearPlato(platoDTO: PlatoDTO) {
        var platoDOM = Plato(
            nombre = platoDTO.nombre,
            descripcion = platoDTO.descripcion,
            valorBase = platoDTO.precio,
            urldeImagen = platoDTO.imagen,
            esDeAutor = platoDTO.esDeAutor,
            ingredientes = platoDTO.ingredientes.map { it.toDOM() }.toMutableList(),
            local = LocalPollos, // en ningun lugar pones el local no se como seria
        )
        repositorioPlatos.crear(platoDOM)
    }
 //platoDTO ya trae el ID adentro
    fun modificarPlato(id: Int, plato: PlatoDTO) {
        val platoDOM: Plato = Plato().fromDTO(plato).apply { this.id = id }
        repositorioPlatos.actualizar(platoDOM)
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