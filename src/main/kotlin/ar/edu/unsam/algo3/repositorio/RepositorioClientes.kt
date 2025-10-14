package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.modelo.usuario.Usuario
import org.springframework.stereotype.Repository

@Repository
class RepositorioClientes: Repositorio<Usuario>() {
}