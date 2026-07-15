package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente

data class IngredienteDTO(
//    Tienen que ser los mismo nombre que estan en el front
    val id: Int,
    val name: String,
    val cost: Double,
    val esOrigenAnimal: Boolean,
    val foodGroup: GrupoAlimenticio
){}

fun Ingrediente.toDTO(): IngredienteDTO{
    val ingredienteDTO = IngredienteDTO(
        id = this.id,
        name = this.nombre,
        cost = this.costoMercado,
        esOrigenAnimal = this.esOrigenAnimal,
        foodGroup = this.grupoAlimenticio
    )
    return ingredienteDTO
}

// PARA ACTUALIZAR
fun IngredienteDTO.fromDTO(): Ingrediente{
    return Ingrediente(
        nombre = this.name,
        costoMercado = this.cost,
        grupoAlimenticio = this.foodGroup,
        esOrigenAnimal = this.esOrigenAnimal
    ).apply{
        this.id = this@fromDTO.id
        // "Usa el ID que me enviaron para actualizar la instancia correspondiente"
    }
}
