package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.servicios.usuarioService
import ar.edu.unsam.algo3.dto.AuthRequest
import ar.edu.unsam.algo3.dto.AuthResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class AuthController {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @GetMapping("/ejemplo")
    fun defaultGet(): String = "hola"

    @PostMapping("/login") // esto no devuelve nada
    fun getUser(@RequestBody request: AuthRequest): AuthResponse {

        val user = Usuario(
            mailPrincipal = request.correo,
            password = request.password
        )
        val userValidado = usuarioService.validar(user)


        return AuthResponse(
            correo = userValidado.mailPrincipal,
            nombre = userValidado.nombre
        )
    }

}