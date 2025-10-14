package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.*
import ar.edu.unsam.algo3.servicios.LocalService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class LocalController(val localService: LocalService) {

    @GetMapping("/store-profile")
    fun obtenerPerfil(): List<LocalDTO> { // Cambia a List<>
        return listOf(localService.obtenerPerfil().toDTO())
    }

    @PutMapping("/store-profile")
    fun actualizarPerfil(@RequestBody request: LocalDTO): LocalDTO {
        return localService.actualizarPerfil(request).toDTO()
    }
}