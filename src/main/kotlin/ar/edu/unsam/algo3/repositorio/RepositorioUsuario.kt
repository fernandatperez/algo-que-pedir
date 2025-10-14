package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.modelo.usuario.Usuario
import org.springframework.stereotype.Repository

@Repository
class RepositorioUsuarios: Repositorio<Usuario>(

){

}

val usuario1 = Usuario(
    nombre = "Carlos",
    apellido = "Marquez",
    mailPrincipal = "jorge@hotmail.com",
    password = "123"
)

val repositorioUsuarios: RepositorioUsuarios = RepositorioUsuarios().apply {
    this.crear(usuario1)
}