package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.AuthRegisterRequest
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.dto.AuthRequest
import ar.edu.unsam.algo3.dto.AuthResponse
import ar.edu.unsam.algo3.modelo.local.Local
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

        val userLocal = Local(
            email = request.email,
            password = request.password,
        )

        val userValidado = usuarioService.validar(userLocal)

        return AuthResponse(
            email = userValidado.email,
            name = userValidado.nombre,
//            nombreLocal = userValidado.nombre
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
        usuarioService.generarUsuario(userLocal)
        return AuthResponse(
            email = userLocal.email,
            name = userLocal.nombre,
//            nombreLocal = userLocal.nombre // esto hay que cambiarlo despues
        )
    }


}