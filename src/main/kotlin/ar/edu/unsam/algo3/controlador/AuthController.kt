package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.AuthRegisterRequest
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.dto.AuthRequest
import ar.edu.unsam.algo3.dto.AuthResponse
import ar.edu.unsam.algo3.dto.AuthUserRegisterRequest
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.servicios.AuthService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class AuthController( val authService: AuthService ) {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @PostMapping("/login") // esto no devuelve nada
    fun getUser(@RequestBody request: AuthRequest): AuthResponse {

        val userLocal = Local(
            email = request.email,
            password = request.password,
        )

        val userValidado = authService.validar(userLocal)



        return AuthResponse(
            email = userValidado.email,
            name = userValidado.nombre,
            id = userValidado.id
        )
    }

    @PostMapping("/register")
    fun createUser(@RequestBody request: AuthRegisterRequest): AuthResponse {

        val userLocal = Local(
            email = request.email,
            password = request.password,
            nombre = request.name
        )
//        Se genera el local
        authService.generarUsuario(userLocal)
        return AuthResponse(
            email = userLocal.email,
            name = userLocal.nombre,
            id = userLocal.id
        )
    }

    @PostMapping("/userLogin")
    fun getUserCliente(@RequestBody request: AuthRequest): AuthResponse {
        // .fromDTO()
        val userAValidar = Usuario(
            mailPrincipal = request.email,
            password = request.password,
        )
        val userValidado = authService.validar(userAValidar)


        return AuthResponse(
            email = userValidado.mailPrincipal,
            name = userValidado.nombre,
            id = userValidado.id
        )
    }

    @PostMapping("/userRegister")
    fun createUserCliente(@RequestBody request: AuthUserRegisterRequest): AuthResponse {
        // .fromDTO()
        val userCliente = Usuario(
            mailPrincipal = request.email,
            nombre = request.name,
            apellido = request.lastName,
            password = request.password,
        )
        // Se genera el usuario
        authService.generarUsuario(userCliente)
        return AuthResponse(
            email = userCliente.mailPrincipal,
            name = userCliente.nombre,
            id = userCliente.id
        )
    }

}