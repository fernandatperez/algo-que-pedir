package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.repositorio.RepositorioCliente
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import org.springframework.stereotype.Service

@Service
class AuthService (
    val repositorioLocal: RepositorioLocal,  // ← Inyecta el repositorio específico
    val respositorioClientes: RepositorioCliente
){
    // -- Auth de LOCAL
    fun validar(usuario: Local): Local {
        val localRepo = this.buscar(usuario)
        if (localRepo.password == usuario.password) {
            return localRepo
        } else {
            throw RuntimeException("Las credenciales no coinciden")
        }
    }

    fun buscar(usuario: Local): Local {
        val localCorrespondiente = repositorioLocal.buscar(usuario.email)
//        println(localCorrespondiente[0])
        if (localCorrespondiente.isEmpty()) {
            throw RuntimeException("No existe un usuario con ese email")
        }
        else {
            return localCorrespondiente[0]
        }
    }

    fun generarUsuario(usuario: Local) {
        val existeUsuarioConMail: List<Local> = repositorioLocal.buscar(usuario.email)
        if (existeUsuarioConMail.isEmpty()) {
            usuario.cumpleCriterioDeCreacion()
            repositorioLocal.crear(usuario)
        } else {
            throw RuntimeException("Ya existe un usuario con ese email")
        }
    }

    // -- Auth de USUARIO
    fun validar(usuario: Usuario): Usuario {
        val usuarioDelRepo = this.buscar(usuario)
        if (usuarioDelRepo.password == usuario.password) {
            return usuarioDelRepo
        } else {
            throw BusinessException("Las credenciales no coinciden")
        }
    }

    fun buscar(usuario: Usuario): Usuario {
        val usuarioCorrespondiente = respositorioClientes.buscar(usuario.mailPrincipal)
//        println(usuarioCorrespondiente)
        if (usuarioCorrespondiente.isEmpty()) {
            throw NotFoundException("Credenciales incorrectas.")
        }
        else {
            return usuarioCorrespondiente[0]
        }
    }

    fun generarUsuario(usuario: Usuario) {
        val existeUsuarioConMail: List<Usuario> = respositorioClientes.buscar(usuario.mailPrincipal)
        if (existeUsuarioConMail.isEmpty()) {
            usuario.cumpleCriterioDeCreacion()
            // se crea un usuario con mail y password
            // pero su nombre y apellido son "nombre apellido"
            // se van a cambiar en el perfil de usuario
            respositorioClientes.crear(usuario)
        } else {
            throw BusinessException("Email incorrecto")
//            throw RuntimeException("Ya existe un usuario con ese email")
        }
    }

}