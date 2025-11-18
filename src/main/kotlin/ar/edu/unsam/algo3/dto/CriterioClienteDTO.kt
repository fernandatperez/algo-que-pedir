package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.usuario.Fieles
import ar.edu.unsam.algo3.repositorio.RepositorioLocal

data class FielesDTO(
    val localesFavoritos: MutableList<Int>
)

fun FielesDTO.toModel(localRepo: RepositorioLocal): Fieles {
    val locales = this.localesFavoritos.map { localRepo.obtenerObjeto(it) }.toMutableSet()

    return Fieles().apply {
        locales.forEach { agregarLocalFavorito(it) }
    }
}