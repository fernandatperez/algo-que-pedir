package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.servicios.LocalService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController

class LocalController(
    private val localService: LocalService
) {

    @GetMapping("/store-profile")
    fun get(@RequestParam mail: String): LocalDTO {
        return localService.get(mail)
    }

    @GetMapping("/store-profile/{id}")
    fun get(@PathVariable id: Int): LocalDTO {
        return localService.getByID(id)
    }

    @GetMapping("/store-profiles")
    fun get(): List<LocalDTO> {
        return localService.getAll().map { it.toDTO()}}


    @PutMapping("/store-profile")
    fun update(
        @RequestParam mail: String,
        @RequestBody localDTO: LocalDTO
    ) {
        //se carga en el DTO del local el mail que trae de la session storage,
        //ya que local originalmente no tiene mail
            val localDTOConEmail = localDTO.copy(email = mail)
            localService.update(localDTOConEmail)
    }
}


