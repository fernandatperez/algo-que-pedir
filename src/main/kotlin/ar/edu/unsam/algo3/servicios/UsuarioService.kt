package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.repositorio.repositorioUsuarios
import org.springframework.stereotype.Service

@Service
class UsuarioService (
    val repositorioLocal: RepositorioLocal  // ← Inyecta el repositorio específico
){
    fun validar(usuario: Usuario): Local {
        val localRepo = this.buscar(usuario)
        if (localRepo.password == usuario.password) {
            return localRepo
        } else {
            throw RuntimeException("Las credenciales no coinciden")
        }
    }

    fun buscar(usuario: Usuario): Local {
        val localCorrespondiente = repositorioLocal.buscar(usuario.mailPrincipal)
        if (localCorrespondiente.isEmpty()) {
            throw RuntimeException("No existe un usuario con ese email")
        }
        else {
            return localCorrespondiente[0]
        }
    }

    fun generarUsuario(usuario: Usuario) {
        val existeUsuarioConMail: List<Usuario> = repositorioUsuarios.buscar(usuario.mailPrincipal)
        if (existeUsuarioConMail.isEmpty()) {
            repositorioUsuarios.crear(usuario)
        } else {
            throw RuntimeException("Ya existe un usuario con ese email")
        }
    }
}