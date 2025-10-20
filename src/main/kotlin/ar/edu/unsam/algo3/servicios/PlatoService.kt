package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.modelo.plato.Plato
//import ar.edu.unsam.algo3.dto.PlatoDTO
import ar.edu.unsam.algo3.mock.LocalPollos
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.dto.PlatoDTOUpdate
//import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.dto.fromDTOUpdate
//import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.repositorio.RepositorioIngrediente
import ar.edu.unsam.algo3.repositorio.RepositorioPlato
import org.springframework.stereotype.Service

@Service
class PlatoService(
    val repositorioPlatos: RepositorioPlato,
    val repositorioIngredientes: RepositorioIngrediente
) {

    fun getPlatos(): List<Plato> =
        repositorioPlatos.objetosDeRepositorio()

    fun obtenerPlato(id: Int): Plato {
//        obtenerObjeto ya hace la validacion de existencia del plato en el repo
        val platoModelo = repositorioPlatos.obtenerObjeto(id)
        if (platoModelo != null) {
            return platoModelo
        }
        throw BusinessException("No se encontro el plato en el repositorio")
    }

    fun obtenerIngredientes(ids: List<Int>): MutableList<Ingrediente> {
        val ingredientes = ids.map { id ->
            repositorioIngredientes.obtenerObjeto(id)
                ?: throw IllegalArgumentException("Ingrediente con id $id no encontrado")
        }.toMutableList()
        return ingredientes
    }

    fun crearPlato(platoDTO: PlatoDTOUpdate): Plato {
        val ingredientes = this.obtenerIngredientes(platoDTO.ingredientes)

        var platoDOM = Plato(
            nombre = platoDTO.nombre,
            descripcion = platoDTO.descripcion,
            valorBase = platoDTO.precio,
            urldeImagen = platoDTO.imagen,
            esDeAutor = platoDTO.esDeAutor,
            ingredientes = ingredientes,
            local = LocalPollos, // en ningun lugar pones el local no se como seria
        )
        platoDOM.crear()
        repositorioPlatos.crear(platoDOM)

        return platoDOM
    }

    fun modificarPlato(platoNuevo: PlatoDTOUpdate) {
//        Buscar plato en repositorio
        var platoAModificar = this.obtenerPlato(platoNuevo.id)

        val ingredientes = this.obtenerIngredientes(platoNuevo.ingredientes)
        platoAModificar = platoAModificar.fromDTOUpdate(platoNuevo, ingredientes)

        repositorioPlatos.actualizar(platoAModificar)

    }
}