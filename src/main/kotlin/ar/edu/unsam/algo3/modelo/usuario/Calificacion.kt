package ar.edu.unsam.algo3.modelo.usuario

import ar.edu.unsam.algo3.dto.CalificacionLocalDTO

class Calificacion(
    val puntaje: Int,
    val descripcion: String
) {

    companion object {
        fun fromDTO(calificacionDTO: CalificacionLocalDTO): Calificacion {
            return Calificacion(
                puntaje = calificacionDTO.rate,
                descripcion = calificacionDTO.text
            )
        }
    }
}