package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.plato.PlatoDTO
import ar.edu.unsam.algo3.modelo.plato.toDTO
import ar.edu.unsam.algo3.servicios.PlatoService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/*
*   CAMBIOS REALIZADOS:
*       todos los endpoints reciben platos de dominio del service y devuelven dtos (para visualizacion)
*
*
* */


@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class PlatoController(val platoService: PlatoService) {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @GetMapping("/platos")
    fun getPlatos(): List<PlatoDTO> {
        val platos = platoService.getPlatos()
        return platos.map { it.toDTO() }
    }

    @GetMapping("/platos/{id}")
    fun getPlato(@PathVariable id: Int): PlatoDTO {
//        Obtener informacion de un plato especifico
        return platoService.obtenerPlato(id).toDTO()
    }

    @PostMapping("/platos") // Maxi
    fun createPlato(@RequestBody objeto: PlatoDTO): PlatoDTO {
        platoService.crearPlato(objeto)
        return platoService.obtenerPlato(objeto.id).toDTO()
    }

    @PutMapping("/platos/{id}")
    fun updatePlato(@PathVariable id: Int, @RequestBody platoAModificar: PlatoDTO): PlatoDTO {
//        Editar un plato existente
        platoService.modificarPlato(platoAModificar)
        return platoService.obtenerPlato(platoAModificar.id).toDTO()
    }
}