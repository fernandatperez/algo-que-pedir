package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.modelo.plato.PlatoRequest
import ar.edu.unsam.algo3.modelo.plato.PlatoResponse
import ar.edu.unsam.algo3.servicios.platoService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class PlatoController {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @GetMapping("/platos") // Maxi
    fun getPlatos() {
//        Obtener la lista completa de platos para mostrar la viste
//        que pusiste
    }

    @GetMapping("/platos/{id}")
    fun getPlato(@PathVariable id: Int): PlatoResponse {
//        Obtener informacion de un plato especifico
        return platoService.obtenerPlato(id)
    }

    @PostMapping("/platos") // Maxi
    fun postPlato() {
//        Crear un nuevo plato usando boton "agregar plato"
    }

    @PutMapping("/platos/{id}")
    fun putPlato(@PathVariable id: Int, @RequestBody platoAModificar: PlatoRequest): PlatoResponse {
//        Editar un plato existente
        platoService.modificarPlato(platoAModificar)
    }
}