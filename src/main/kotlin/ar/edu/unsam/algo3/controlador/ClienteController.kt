package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.CalificacionDTO
import ar.edu.unsam.algo3.dto.ClientePerfilDTO
import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.dto.LocalDomDTO
import ar.edu.unsam.algo3.dto.OrderDTO
import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.dto.toLocalDomDTO
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
    @GetMapping("/perfil/{id}")
    fun get(@PathVariable id: Int) {

    }

    @PutMapping("/perfil/")
    fun update(@RequestParam id: Int, @RequestBody perfilDTO: ClientePerfilDTO) {

    }

    @GetMapping("/locales-puntuables/{id}")
    fun getUnratedStores(@PathVariable id: Int): List<LocalDomDTO> {
        val localesDTO =
            clienteService.obtenerLocalesPuntuables(id).map { local ->
                local.toLocalDomDTO()
            }
        return localesDTO
    }

    @PostMapping("/puntuar-local")
    fun postStoreRate(@RequestParam localId: Int, @RequestParam userId: Int, @RequestBody calificacionDTO: CalificacionDTO) {
        clienteService.puntuarLocal(userId, localId, calificacionDTO)
    }

    @PostMapping("/confirmar-pedido/")
    fun postConfirm(@RequestParam clienteID: Int, @RequestParam pedidoID: Int) {
        clienteService.confirmarPedido(clienteID, pedidoID)
    }

    @PostMapping("/cancel-order/{id}")
    fun cancelOrder(@PathVariable id: Int, @RequestParam userId: Int): OrderDTO {
        return clienteService.cancelarOrden(id, userId)
    }

    @GetMapping("/criterio-ingrediente/{criterio}")
    fun getIngredientePorCriterio(@RequestParam id: Int, @PathVariable criterio: String): Set<IngredienteDTO> {
        return clienteService.obtenerIngredientesPorCriterio(id, criterio)
    }

    @GetMapping("/ingredientes-disponibles")
    fun getIngredientesDisponibles(@RequestParam id: Int): Set<IngredienteDTO> {
        return clienteService.obtenerIngredientesDisponibles(id)
    }

    @PutMapping("/actualizar-ingredientes/{criterio}")
    fun actualizarIngredientesPorCriterio(@RequestParam id: Int, @PathVariable criterio: String, @RequestBody ingredientes: List<IngredienteDTO>): Set<IngredienteDTO> {
        val ingredientes = ingredientes.map { it.fromDTO()}.toList()
        return clienteService.actualizarIngredientesPorCriterio(id, criterio, ingredientes)
    }

    @DeleteMapping("/eliminar-ingrediente-usuario/{criterio}")
    fun eliminarIngredientePorCriterio(@RequestParam id: Int, @PathVariable criterio: String, @RequestParam ingredienteId: Int) =
        clienteService.eliminarIngredientePorCriterio(id, criterio, ingredienteId)

}