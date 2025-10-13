package ar.edu.unsam.algo3.modelo.plato

import ar.edu.unsam.algo3.DTO.IngredienteDTO
import ar.edu.unsam.algo3.mock.LocalPollos
import ar.edu.unsam.algo3.modelo.local.Local

// Esto deberia ser uno solo? tipo PlatoDTO por que mando lo mismo que recibo
//data class PlatoRequest(
//    val id: Int,
//    val nombre: String,
//    val descripcion: String,
//    val imagen: String,
//    val precio: Double,
//    val esDeAutor: Boolean,
//    val enPromocion: Boolean,
//    val costoDeProduccion: Double,
//    val ingredientes: MutableList<Ingrediente>
//) {}

data class PlatoDTO(
    val id : Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String,
    val costoProduccion: Double,
    val esDeAutor: Boolean,
    val enPromocion: Boolean,
    val ingredientes: MutableList<IngredienteDTO>,
    val store: Local = LocalPollos, // Esto deberia ser DTO tambien
) {}