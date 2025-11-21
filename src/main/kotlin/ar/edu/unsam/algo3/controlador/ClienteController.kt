package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.CalificacionDTO
import ar.edu.unsam.algo3.dto.ClientePerfilDTO
import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.dto.LocalDomDTO
import ar.edu.unsam.algo3.dto.OrderDTO
import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.dto.toLocalDomDTO
import ar.edu.unsam.algo3.dto.toUsuarioDTO
import ar.edu.unsam.algo3.servicios.ClienteService
import org.springframework.core.annotation.Order
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

    @GetMapping("/perfil/{id}")
    fun clientePorId(@PathVariable id: Int) =
        clienteService.clientePorId(id).toUsuarioDTO()

    @PutMapping("/actualizar-perfil/{id}")
    fun update(@PathVariable id: Int, @RequestBody perfilDTO: ClientePerfilDTO): ClientePerfilDTO {
        return clienteService.actualizarPerfil(perfilDTO).toUsuarioDTO()
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
    fun postStoreRate(@RequestParam localId: Int, @RequestParam userId: Int, @RequestBody calificacionDTO: CalificacionDTO): CalificacionDTO {
        return clienteService.puntuarLocal(userId, localId, calificacionDTO)
    }

    @PostMapping("/confirm-order/{id}")
    fun postConfirm(@PathVariable id: Int, @RequestParam userId: Int): OrderDTO {
        return clienteService.confirmarPedidoDeUsuario(id, userId)
    }

    @PostMapping("/cancel-order/{id}")
    fun cancelOrder(@PathVariable id: Int, @RequestParam userId: Int): OrderDTO {
        return clienteService.cancelarOrdenDeUsuario(id, userId)
    }
}