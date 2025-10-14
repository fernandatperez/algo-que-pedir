package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.modelo.plato.PlatoDTO
import ar.edu.unsam.algo3.servicios.PlatoService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class PlatoController(val platoService: PlatoService) {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @GetMapping("/platos")
    fun getPlatos(): List<PlatoDTO> {
        return platoService.getPlatos()
    }

    @GetMapping("/platos/{id}")
    fun getPlato(@PathVariable id: Int): PlatoDTO {
//        Obtener informacion de un plato especifico
        return platoService.obtenerPlato(id)
    }

    @PostMapping("/platos") // Maxi
    fun postPlato(@RequestBody objeto: PlatoDTO) {
        platoService.crearPlato(objeto)
    }

    @PutMapping("/platos/{id}")
    fun putPlato(@PathVariable id: Int, @RequestBody platoAModificar: PlatoDTO) {
//        Editar un plato existente
        platoService.modificarPlato(id, platoAModificar)
    }
}