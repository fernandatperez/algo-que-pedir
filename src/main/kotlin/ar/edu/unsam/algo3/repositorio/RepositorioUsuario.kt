package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo2.algoQuePedir.ar.edu.unsam.algo3.Usuario

val usuario1 = Usuario(
    nombre = "Carlos",
    apellido = "Marquez",
    mailPrincipal = "Jorge@hotmail.com",
    password = "123"
)

val repositorioUsuario = Repositorio<Usuario>().apply {
    this.crear(usuario1)
}