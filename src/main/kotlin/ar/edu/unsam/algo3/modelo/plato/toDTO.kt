package ar.edu.unsam.algo3.modelo.plato

fun Plato.toDTO(): PlatoResponse {
    return PlatoResponse(
        id = "1",
        nombre = this.nombre,
        descripcion = this.descripcion,
        imagen = this.urldeImagen,
        precio = this.valorVenta(),
        //costoDeProduccion = this.costoProduccion(),
        esDeAutor = this.esDeAutor,
        enPromocion = this.esNuevo(),
        ingredientes = this.ingredientes
    )
}