package ar.edu.unsam.algo3.modelo.utils

import java.time.LocalDate

data class Mensaje(
    val fechaDeEmision: LocalDate = LocalDate.now(),
    val subject: String = "Aumentar prioridad",
    val content: String = "Traten de darle mas prioridad che",
    var leido: Boolean = false
)