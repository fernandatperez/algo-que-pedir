package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.modelo.usuario.Usuario

val usuario1 = Usuario(
    nombre = "Carlos",
    apellido = "Marquez",
    mailPrincipal = "jorge@hotmail.com",
    password = "123"
)

val repositorioUsuario = Repositorio<Usuario>().apply {
    this.crear(usuario1)
}