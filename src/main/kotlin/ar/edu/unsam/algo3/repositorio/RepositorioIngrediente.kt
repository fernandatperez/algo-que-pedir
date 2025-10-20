package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import org.springframework.stereotype.Repository

@Repository
class RepositorioIngrediente : Repositorio<Ingrediente>() {
    fun ingredienteYaExiste(nombre: String): Boolean =
        this.coleccion.any {ing -> ing.nombre == nombre}
}