package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.DTO.StoreProfileResponse
import ar.edu.unsam.algo3.DTO.StoreProfileRequest
import ar.edu.unsam.algo3.DTO.toDomain
import ar.edu.unsam.algo3.DTO.toResponse
import ar.edu.unsam.algo3.servicios.LocalService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class ControladorLocal(val localService: LocalService) {

    @GetMapping("/store-profile")
    fun obtenerPerfil(): List<StoreProfileResponse> { // Cambia a List<>
        return listOf(localService.obtenerPerfil().toResponse())
    }

    @PutMapping("/store-profile")
    fun actualizarPerfil(@RequestBody request: StoreProfileRequest): StoreProfileResponse {
        //aca uso el extension method toDomain
        val localDomain = request.toDomain()
        val localActualizado = localService.actualizarPerfil(localDomain)
        return localActualizado.toResponse()
    }
}