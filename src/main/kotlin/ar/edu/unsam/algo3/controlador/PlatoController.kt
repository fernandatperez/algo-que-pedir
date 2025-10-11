package ar.edu.unsam.algo3.controlador

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class PlatoController {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @GetMapping("/platos")
    fun getPlatos() {
//        Obtener la lista completa de platos para mostrar la viste
//        que pusiste
    }

    @GetMapping("/platos/{id}")
    fun getPlato() {
//        Obtener informacion de un plato especifico
    }

    @PostMapping("/platos")
    fun postNuevoPlato() {
//        Crear un nuevo plato usando boton "agregar plato"
    }

    @PutMapping("/platos/{id}")
    fun postEditarPlato() {
//        Editar un plato existente
    }
}