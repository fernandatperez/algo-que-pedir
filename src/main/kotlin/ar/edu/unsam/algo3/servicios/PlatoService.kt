package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.modelo.plato.PlatoResponse
import ar.edu.unsam.algo3.modelo.plato.toDTO
import ar.edu.unsam.algo3.repositorio.repositorioPlato

val platoService: PlatoService = PlatoService()

class PlatoService {

    fun getPlatos() {

    }

    fun getPlato(id: Int): PlatoResponse {
        val platoModelo = repositorioPlato.obtenerObjeto(id)
        return platoModelo.toDTO()
    }
}