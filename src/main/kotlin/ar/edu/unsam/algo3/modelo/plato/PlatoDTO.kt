package ar.edu.unsam.algo3.modelo.plato

import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente

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
    val imagen: String,
    val precio: Double,
    val esDeAutor: Boolean,
    val enPromocion: Boolean,
    val costoDeProduccion: Double,
    val ingredientes: MutableList<Ingrediente>
) {}