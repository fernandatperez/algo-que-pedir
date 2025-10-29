package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.ClientePerfilDTO
import ar.edu.unsam.algo3.dto.LocalCardDTO
import ar.edu.unsam.algo3.dto.toCardDTO
import ar.edu.unsam.algo3.servicios.ClienteService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController
class ClienteController( val clienteService: ClienteService ) {

    //    Esto cambialo como quieras, lo arme para que ya estuviera nada mas
    @GetMapping("/perfil")
    fun get(@RequestParam id: Int) {

    }

    @PutMapping("/perfil/{id}")
    fun update(@RequestParam id: Int, @RequestBody perfilDTO: ClientePerfilDTO) {

    }

    @GetMapping("/locales-puntuables")
    fun getUnratedStores(@RequestParam id: Int): List<LocalCardDTO> {
        val localesDTO =
            clienteService.obtenerLocalesPuntuables(id).map { local ->
                local.toCardDTO()
            }
        return localesDTO
    }

    @PostMapping("puntuar-local/{id}")
    fun postStoreRate(@PathVariable id: Int) {

    }
}