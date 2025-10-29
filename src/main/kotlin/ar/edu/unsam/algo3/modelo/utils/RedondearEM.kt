package ar.edu.unsam.algo3.modelo.utils

import kotlin.math.pow
import kotlin.math.round

fun Double.redondear(digitos: Int): Double {
    val factor = 10.0.pow(digitos)
    return round(this * factor) / factor
}