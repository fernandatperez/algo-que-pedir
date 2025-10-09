package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo2.algoQuePedir.ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.servicios.usuarioService
import ar.edu.unsam.algo3.DTO.UsuarioDTO
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class ApplicationController {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @GetMapping("/ejemplo")
    fun defaultGet(): String = "hola"
// esto estasba con put
    @PostMapping("/login") // esto no devuelve nada
    fun getUser(@RequestBody usuarioDTO: UsuarioDTO): UsuarioDTO {
        val user = Usuario(
            mailPrincipal = usuarioDTO.correo,
            password = usuarioDTO.password
        )
        val userValidado = usuarioService.validar(user)

        return UsuarioDTO(
            correo = userValidado.mailPrincipal,
            password = "" // no la devolves
        )
    }

}