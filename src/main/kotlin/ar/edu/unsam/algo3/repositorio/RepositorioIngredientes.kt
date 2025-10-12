package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
class RepositorioIngredientes : Repositorio<Ingrediente>() {
    fun crear(
        name: String,
        cost: Double,
        esOrigenAnimal: Boolean,
        foodGroup: GrupoAlimenticio
    ): Ingrediente {
        val ingrediente = Ingrediente(
            nombre = name,
            costoMercado = cost,
            esOrigenAnimal = esOrigenAnimal,
            grupoAlimenticio = foodGroup
        )
        this.crear(ingrediente)
        return ingrediente
    }
}