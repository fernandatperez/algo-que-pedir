package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.servicios.LocalService
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/store-profile")
class LocalController(
    private val localService: LocalService
) {

    @GetMapping
    fun get(@RequestParam mail: String): LocalDTO {
        return localService.get(mail)
    }

    @PutMapping
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


