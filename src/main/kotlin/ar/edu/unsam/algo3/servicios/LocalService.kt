package ar.edu.unsam.algo3.servicios

import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.repositorio.ElementoDeRepositorio


// LocalService.kt
@Service
class LocalService(
    private val repositorioLocal: RepositorioLocal  // ← Inyecta el repositorio específico
) {

    fun getLocal(): List<LocalDTO> =
            repositorioLocal.objetosDeRepositorio().map { it.toDTO() }
    fun updateLocal( id: Int,localDTO: LocalDTO) {
        val localDOM: Local = localDTO.fromDTO(). apply {this.id = id}
        repositorioLocal.actualizar(localDOM)
    }

}