package ar.edu.unsam.algo3.modelo.plato

fun Plato.toDTO(id: Int): PlatoDTO {
    return PlatoDTO(
        id = id,
        nombre = this.nombre,
        descripcion = this.descripcion,
        imagen = this.urldeImagen,
        precio = this.valorVenta(),
        esDeAutor = this.esDeAutor,
        enPromocion = this.esNuevo(),
        costoDeProduccion = this.costoProduccion(),
        ingredientes = this.ingredientes
    )
}