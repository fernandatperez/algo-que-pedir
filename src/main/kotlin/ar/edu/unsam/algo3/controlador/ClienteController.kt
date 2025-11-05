package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.ClientePerfilDTO
import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.LocalCardDTO
import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.dto.toCardDTO
import ar.edu.unsam.algo3.servicios.ClienteService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
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

    @GetMapping("criterio-ingrediente/{criterio}")
    fun getIngredientePorCriterio(@RequestParam id: Int, @PathVariable criterio: String): Set<IngredienteDTO> {
        return clienteService.obtenerIngredientesPorCriterio(id, criterio)
    }

    @GetMapping("ingredientes-disponibles")
    fun getIngredientesDisponibles(@RequestParam id: Int): Set<IngredienteDTO> {
        return clienteService.obtenerIngredientesDisponibles(id)
    }

    @PutMapping("actualizar-ingredientes/{criterio}")
    fun actualizarIngredientesPorCriterio(@RequestParam id: Int, @PathVariable criterio: String, @RequestBody ingredientes: List<IngredienteDTO>): Set<IngredienteDTO> {
        val ingredientes = ingredientes.map { it.fromDTO()}.toList()
        return clienteService.actualizarIngredientesPorCriterio(id, criterio, ingredientes)
    }

    @DeleteMapping("/eliminar-ingrediente-usuario/{criterio}")
    fun eliminarIngredientePorCriterio(@RequestParam id: Int, @PathVariable criterio: String, @RequestParam ingredienteId: Int) =
        clienteService.eliminarIngredientePorCriterio(id, criterio, ingredienteId)

}