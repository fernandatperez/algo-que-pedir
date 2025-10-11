package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.plato.PlatoDTO
import ar.edu.unsam.algo3.modelo.plato.toDTO
import ar.edu.unsam.algo3.repositorio.repositorioPlato

val platoService: PlatoService = PlatoService()

class PlatoService {

    fun getPlatos(): List<PlatoDTO> {
        return repositorioPlato.objetosDeRepositorio().map { it.toDTO(it.id) }
    }

    fun obtenerPlato(id: Int): PlatoDTO {
//        obtenerObjeto ya hace la validacion de existencia del plato en el repo
        val platoModelo = repositorioPlato.obtenerObjeto(id)
        return platoModelo.toDTO(id)
    }

//    fun modificarPlato(plato: PlatoDTO): PlatoDTO {
//        repositorioPlato.obtenerObjeto(plato.id)
//    }
}