package ar.edu.unsam.algo3.modelo.usuario

import ar.edu.unsam.algo3.dto.CalificacionDTO

class Calificacion(
    val puntaje: Int,
    val comentario: String
) {

    companion object {
        fun fromDTO(calificacionDTO: CalificacionDTO): Calificacion {
            return Calificacion(
                puntaje = calificacionDTO.rate,
                comentario = calificacionDTO.experienceDesc
            )
        }
    }
}