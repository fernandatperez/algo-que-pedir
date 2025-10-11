package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.modelo.plato.PlatoResponse
import ar.edu.unsam.algo3.servicios.platoService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class PlatoController {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @GetMapping("/platos")
    fun getPlatos(): List<PlatoResponse> {
        return platoService.getPlatos()
    }

    @GetMapping("/platos/{id}")
    fun getPlato(@PathVariable id: Int): PlatoResponse {
//        Obtener informacion de un plato especifico
        return platoService.getPlato(id)
    }

    @PostMapping("/platos") // Maxi
    fun postNuevoPlato() {
//        Crear un nuevo plato usando boton "agregar plato"
    }

//    @PutMapping("/platos/{id}")
//    fun postEditarPlato(@PathVariable id: Int, @RequestBody): PlatoResponse {
////        Editar un plato existente
//
//    }
}