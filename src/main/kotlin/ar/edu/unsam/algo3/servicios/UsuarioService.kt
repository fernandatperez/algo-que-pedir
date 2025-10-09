package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo2.algoQuePedir.ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.repositorio.repositorioUsuario

val usuarioService = UsuarioService()

class UsuarioService {
    fun validar(usuario: Usuario): Usuario {
        val usuarioRepo = this.buscar(usuario)
        if (usuarioRepo.password == usuario.password) {
            return usuarioRepo
        } else {
            throw RuntimeException("Las credenciales no coinciden")
        }
    }

    fun buscar(usuario: Usuario): Usuario {
        val usuarioEncontrado = repositorioUsuario.buscar(usuario.mailPrincipal)
        if (usuarioEncontrado.isEmpty()) {
            throw RuntimeException("No existe un usuario con ese email")
        }
        else {
            return usuarioEncontrado[0]
        }
    }
}