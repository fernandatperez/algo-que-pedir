package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.plato.PlatoRequest
import ar.edu.unsam.algo3.modelo.plato.PlatoResponse
import ar.edu.unsam.algo3.modelo.plato.toDTO
import ar.edu.unsam.algo3.repositorio.repositorioPlato

val platoService: PlatoService = PlatoService()

class PlatoService {

    fun getPlatos() {

    }

    fun obtenerPlato(id: Int): PlatoResponse {
//        obtenerObjeto ya hace la validacion de existencia del plato en el repo
        val platoModelo = repositorioPlato.obtenerObjeto(id)
        return platoModelo.toDTO()
    }

    fun modificarPlato(plato: PlatoRequest): PlatoResponse {
        repositorioPlato.obtenerObjeto(plato.id)
    }
}