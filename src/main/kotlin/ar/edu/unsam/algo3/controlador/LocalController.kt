package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.*
import ar.edu.unsam.algo3.servicios.LocalService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class LocalController(val localService: LocalService) {

    @GetMapping("/store-profile")
    fun getLocal(): List<LocalDTO> { // Cambia a List<>
        return localService.getLocal()
    }
    //si bien local es unico, no va a ser el unico en a bbdd
    @PutMapping("/store-profile/{id}")
    fun updateLocal(@PathVariable id: Int,@RequestBody request: LocalDTO){
        return localService.updateLocal(id,request)
    }
}