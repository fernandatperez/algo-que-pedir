package ar.edu.unsam.algo3.modelo.plato

fun Plato.toDTO(): PlatoResponse {
    return PlatoResponse(
        nombre = this.nombre,
        descripcion = this.descripcion,
        URLdeImagen = this.urldeImagen,
        valorBase = this.valorVenta(),
        costoDeProduccion = this.costoProduccion(),
        platoAutor = this.esDeAutor,
        platoEnPromo = this.esNuevo(),
        ingredientes = this.ingredientes
    )
}