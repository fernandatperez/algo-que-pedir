package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.usuario.Calificacion

data class CalificacionDTO(
    val rate: Int,
    val experienceDesc: String
)

fun Calificacion.toDTO(): CalificacionDTO {
    return CalificacionDTO(
        rate = this.puntaje,
        experienceDesc = this.comentario
    )
}

fun CalificacionDTO.fromDTO(): Calificacion {
    return Calificacion(
        puntaje = this.rate,
        comentario = this.experienceDesc
    )
}