package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.CalificacionDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.dto.LocalDomDTO
import ar.edu.unsam.algo3.dto.SearchRequest
import ar.edu.unsam.algo3.dto.toLocalDomDTO
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
    fun getReact(@PathVariable id: Int): LocalDomDTO {
        return localService.getByIDReact(id)
    }

    @GetMapping("/store-profiles")
    fun getAll(): List<LocalDTO> {
        return localService.getAll().map { it.toDTO() }
    }

    @PostMapping("/store-profiles")
    fun getStores(@RequestBody searchRequest: SearchRequest): List<LocalDTO> {
        val userId = searchRequest.userId?.toIntOrNull() ?: 0
        val resultados = localService.getBySearch(searchRequest.searchName, userId)
        return resultados
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
    fun getRatings(
        @PathVariable id: Int,
        @RequestParam page: Int,
        @RequestParam limit: Int): Map<String, Any> {
        val (reviewsCut, hasMore) = localService.getStoreRatingsByID(id, page, limit)
        return mapOf(
            "reviewsCut" to reviewsCut,
            "hasMore" to hasMore
        )
    }

    @GetMapping("/storesDom")
    fun getStoresDom(): List<LocalDomDTO> =
        localService.getAllStoresDom().map { it.toLocalDomDTO() }
}


