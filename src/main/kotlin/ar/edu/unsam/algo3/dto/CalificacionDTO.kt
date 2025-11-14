package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.usuario.Calificacion

data class CalificacionDTO(
    val puntaje: Int,
    val comentario: String
)

fun Calificacion.toDTO(): CalificacionDTO {
    return CalificacionDTO(
        puntaje = this.puntaje,
        comentario = this.comentario
    )
}