package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo2.algoQuePedir.ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.servicios.usuarioService
import ar.edu.unsam.algo3.DTO.UsuarioDTO
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class ApplicationController {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @GetMapping("/ejemplo")
    fun defaultGet(): String = "hola"

    @PutMapping("/login")
    fun getUser(@RequestBody usuarioDTO: UsuarioDTO) {
        val user = Usuario(
            mailPrincipal = usuarioDTO.correo,
            password = usuarioDTO.password
        )
        usuarioService.validar(user)
    }

}