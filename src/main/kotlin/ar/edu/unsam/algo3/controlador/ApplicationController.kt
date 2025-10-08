package ar.edu.unsam.algo3.controlador

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ApplicationController {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @GetMapping("/ejemplo")
    fun defaultGet(): String = "hola"

    @GetMapping("/platos")
    fun getPlatos() {

    }

}