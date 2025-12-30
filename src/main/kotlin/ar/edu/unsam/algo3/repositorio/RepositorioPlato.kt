package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.modelo.plato.Plato
import org.springframework.stereotype.Repository

@Repository
class RepositorioPlato: Repositorio<Plato>(

){
//    Deprecado
    fun algunoContieneIngrediente(id: Int): Boolean = this.coleccion.any { plato -> plato.ingredientes.any { ing -> ing.id == id }}
}