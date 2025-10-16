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
        /*
            val user = Usuario(
                mailPrincipal = request.correo,
                password = request.password
            )
        */

        val userLocal = Local(
            email = request.correo,
            password = request.password,
        )

        val userValidado = usuarioService.validar(userLocal)
        // aca te tenes que fijas que es lo que devuelve la idea seria comparar el email con el de el local
        // y lo que tiene que devolver es tambien el local, de manera
        return AuthResponse(
            correo = userValidado.email,
            nombre = userValidado.nombre,
            nombreLocal = userValidado.nombre
        )
    }

    @PostMapping("/register")
    fun createUser(@RequestBody request: AuthRegisterRequest): AuthResponse {
        /*
            val usuario = Usuario(
                mailPrincipal = request.correo,
                nombre = request.nombre,
                password = request.password
            )
        */
        val userLocal = Local(
            email = request.correo,
            password = request.password,
            nombre = request.nombre
        )
//        Se genera el local
        usuarioService.generarUsuario(userLocal)
        return AuthResponse(
            correo = userLocal.email,
            nombre = userLocal.nombre,
            nombreLocal = userLocal.nombre // esto hay que cambiarlo despues
        )
    }


}