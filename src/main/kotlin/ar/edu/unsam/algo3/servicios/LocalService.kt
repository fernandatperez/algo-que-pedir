package ar.edu.unsam.algo3.servicios

import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.dominio.Local
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.errores.BusinessException


// LocalService.kt
@Service
class LocalService(
    private val repositorioLocal: RepositorioLocal  // ← Inyecta el repositorio específico
) {

    fun actualizarPerfil(localDTO: LocalDTO): Local {
        // Convertir DTO a dominio
        val localActualizado = localDTO.fromDTO()
        localActualizado.id = 1

        try {
            val localExistente = repositorioLocal.obtenerObjeto(1) as Local

            localExistente.actualizar(localActualizado)
            localExistente.validar()

            repositorioLocal.actualizar(localExistente)
            return localExistente

        } catch (e: Exception) {
            localActualizado.validar()
            repositorioLocal.crear(localActualizado)
            return localActualizado
        }
    }

    fun obtenerPerfil(): Local {
//        println("obtenerPerfil")
        return try {
//            println("obtenerPerfil entro al try")
            repositorioLocal.obtenerObjeto(1)
        } catch (e: Exception) {
//            println("obtenerPerfil entro al catch")
            throw BusinessException("Perfil de tienda no configurado")
        }
    }
}