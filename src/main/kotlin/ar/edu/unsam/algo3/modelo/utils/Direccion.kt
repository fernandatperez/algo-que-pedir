package ar.edu.unsam.algo3.modelo.utils

import org.uqbar.geodds.Point

open class Direccion(
    var calle: String = "",
    var altura: Int = 0,
    var ubicacion: Point = Point(0.0, 0.0)
) {
    fun esCercano(otraDireccion: Direccion, distanciaMax: Double): Boolean =
        distanciaHasta(otraDireccion.ubicacion) <= distanciaMax

    fun distanciaHasta(otraUbicacion: Point): Double = this.ubicacion.distance(otraUbicacion)
}
