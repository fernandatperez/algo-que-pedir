package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.usuario.Calificacion

data class CalificacionDTO(
    val id: Int,
    val rate: Int,
    val experienceDesc: String
)

fun Calificacion.toDTO(): CalificacionDTO {
    return CalificacionDTO(
        id = 0,
        rate = this.puntaje,
        experienceDesc = this.comentario
    )
}