package ar.edu.unsam.algo3.controlador


import org.springframework.web.bind.annotation.*
import ar.edu.unsam.algo3.servicios.LocalService
import ar.edu.unsam.algo3.dominio.Local

@RestController
@CrossOrigin("*")
class ControladorLocal(val localService: LocalService) {

    @PutMapping("/store-profile")
    fun actualizarPerfil(@RequestBody local: Local): Local {
        // El JSON no necesita enviar ID
        return  localService.actualizarPerfil(local)
    }

    @GetMapping("/store-profile")
    fun obtenerPerfil(): Local = localService.obtenerPerfil()

// El frontend envía JSON sin ID, o con ID=1
}
