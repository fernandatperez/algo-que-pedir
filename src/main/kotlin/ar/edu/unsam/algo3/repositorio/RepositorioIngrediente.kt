package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import org.springframework.stereotype.Repository

@Repository
class RepositorioIngrediente : Repositorio<Ingrediente>() {
    fun ingredienteYaExiste(ingredienteNuevo: Ingrediente): Boolean =
        this.coleccion.any {
            ing -> ing.nombre == ingredienteNuevo.nombre
                && ing.id == ingredienteNuevo.id
                && ing.costoMercado == ingredienteNuevo.costoMercado
        }
}