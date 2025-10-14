package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.AuthRegisterRequest
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.dto.AuthRequest
import ar.edu.unsam.algo3.dto.AuthResponse
import ar.edu.unsam.algo3.repositorio.repositorioUsuarios
import ar.edu.unsam.algo3.servicios.UsuarioService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class AuthController( val usuarioService: UsuarioService ) {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

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

    @PostMapping("/register")
    fun createUser(@RequestBody request: AuthRegisterRequest): AuthResponse {
        val usuario = Usuario(
            nombre = request.name,
            apellido = request.surname,
            mailPrincipal = request.username,
            password = request.password
        )
        usuarioService.generarUsuario(usuario)

        return AuthResponse(
            correo = usuario.mailPrincipal,
            nombre = usuario.nombre
        )
    }


}