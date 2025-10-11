package ar.edu.unsam.algo3.modelo.plato

fun Plato.toDTO(id: Int): PlatoResponse {
    return PlatoResponse(
        id = id,
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