package ar.edu.unsam.algo2.algoQuePedir

import org.uqbar.geodds.Point

class Direccion(
    val calle: String = "",
    val altura: Int = 0,
    val ubicacion: Point = Point(0.0, 0.0)
) {
    fun esCercano(otraDireccion: Direccion, distanciaMax: Double): Boolean =
        distanciaHasta(otraDireccion.ubicacion) <= distanciaMax

    fun distanciaHasta(otraUbicacion: Point): Double = this.ubicacion.distance(otraUbicacion)
}
