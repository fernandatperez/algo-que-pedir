package ar.edu.unsam.algo3.servicios

import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.plato.PlatoDTO
import ar.edu.unsam.algo3.modelo.plato.fromDTO
import ar.edu.unsam.algo3.modelo.plato.toDTO
import ar.edu.unsam.algo3.repositorio.RepositorioLocal


// LocalService.kt

@Service
class LocalService(
    val repositorioLocal: RepositorioLocal
) {
    fun getLocal(): List<LocalDTO> =
        repositorioLocal.objetosDeRepositorio().map { it.toDTO() }

    fun updateLocal( id: Int,localDTO: LocalDTO) {
        val localDOM: Local = localDTO.fromDTO(). apply {this.id = id}
        repositorioLocal.actualizar(localDOM)
    }

}