package ar.edu.unsam.algo3.modelo.plato

import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente

// Esto deberia ser uno solo? tipo PlatoDTO por que mando lo mismo que recibo
data class PlatoRequest(
    val nombre: String,
    val descripcion: String,
    val URLdeImagen: String,
    val valorBase: Double,
    val platoAutor: Boolean,
    val platoEnPromo: Boolean,
    val costoDeProduccion: Double,
    val ingredientes: MutableList<Ingrediente>
) {}

data class PlatoResponse(
    val nombre: String,
    val descripcion: String,
    val URLdeImagen: String,
    val valorBase: Double,
    val platoAutor: Boolean,
    val platoEnPromo: Boolean,
    val costoDeProduccion: Double,
    val ingredientes: MutableList<Ingrediente>
) {}