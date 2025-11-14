package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.CalificacionDTO
import ar.edu.unsam.algo3.dto.LocalCardDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.dto.LocalDetailDTO
import ar.edu.unsam.algo3.dto.SearchRequest
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

    @GetMapping("/store-profile-react/{id}")
    fun getReact(@PathVariable id: Int): LocalDetailDTO {
        return localService.getByIDReact(id)
    }

    @GetMapping("/store-profiles")
    fun getAll(): List<LocalDTO> {
        return localService.getAll().map { it.toDTO() }
    }

    @PostMapping("/store-profiles")
    fun getStores(@RequestBody searchRequest: SearchRequest): List<LocalDTO> {
        val resultados = localService.getBySearch(searchRequest.searchName)
        return resultados.map { it.toDTO() }
    }

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

    @GetMapping("/store-reviews/{id}")
    fun getRatings(@PathVariable id: Int): List<CalificacionDTO> {
        return localService.getStoreRatingsByID(id)
    }
}


