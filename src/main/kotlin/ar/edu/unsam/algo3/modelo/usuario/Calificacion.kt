package ar.edu.unsam.algo3.modelo.usuario

import ar.edu.unsam.algo3.dto.CalificacionDTO
import ar.edu.unsam.algo3.dto.CalificacionLocalDTO

class Calificacion(
    val puntaje: Int,
    val comentario: String
) {

    companion object {
        fun fromDTO(calificacionDTO: CalificacionLocalDTO): Calificacion {
            return Calificacion(
                puntaje = calificacionDTO.rate,
                comentario = calificacionDTO.text
            )
        }
    }

}