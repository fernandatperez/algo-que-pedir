package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.plato.PlatoDTO
import ar.edu.unsam.algo3.modelo.plato.toDTO
import ar.edu.unsam.algo3.bootstrap.repositorioPlatos
import ar.edu.unsam.algo3.mock.LocalPollos
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.plato.fromDTO
import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.toDOM

val platoService: PlatoService = PlatoService()

class PlatoService {

    fun getPlatos(): List<PlatoDTO> =
        repositorioPlatos.objetosDeRepositorio().map { it.toDTO() }

    fun obtenerPlato(id: Int): PlatoDTO {
//        obtenerObjeto ya hace la validacion de existencia del plato en el repo
        val platoModelo = repositorioPlatos.obtenerObjeto(id)
        return platoModelo.toDTO()
    }

    fun crearPlato(platoDTO: PlatoDTO) {
        val ingredientesDOM: MutableList<Ingrediente> = platoDTO.ingredientes.map { it: IngredienteDTO -> it.toDOM()}.toMutableList()
        var platoDOM = Plato(
            nombre = platoDTO.nombre,
            descripcion = platoDTO.descripcion,
            valorBase = platoDTO.precio,
            urldeImagen = platoDTO.imagen,
            esDeAutor = platoDTO.esDeAutor,
            ingredientes = ingredientesDOM,
            local = LocalPollos, // en ningun lugar pones el local no se como seria
        )
        repositorioPlatos.crear(platoDOM)
    }

    fun modificarPlato(id: Int, plato: PlatoDTO) {
        val platoDOM: Plato = Plato().fromDTO(plato). apply {this.id = id}
        repositorioPlatos.actualizar(platoDOM)
    }
}


/* no me funciona esto, se rompe el controller
@Service
class PlatoService(
    val repoPlatos: Repositorio<Plato> // Spring lo inyecta automaticaemnte
) {

    fun getPlatos(): List<PlatoDTO> =
        repoPlatos.objetosDeRepositorio().map { it.toDTO(it.id) }

    fun obtenerPlato(id: Int): PlatoDTO {
        val platoModelo = repoPlatos.obtenerObjeto(id)
        return platoModelo.toDTO(id)
    }
}
*/
