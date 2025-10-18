package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.PedidoDTO
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.servicios.PedidoService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class PedidoController(val pedidosService: PedidoService) {

    @GetMapping("/pedidos")
    fun pedidos() = pedidosService.pedidos()

    @GetMapping("/pedidos/")
    fun pedidos(@RequestParam estado: Estado, @RequestParam("local") email: String) = pedidosService.pedidosFiltrados(estado, email)

    @GetMapping("/pedido/{id}")
    fun pedidoPorId(@PathVariable id: Int) = pedidosService.buscarPorID(id)

    @PutMapping("/modificar_pedido/{id}")
    fun actualizarEstado(@RequestBody pedidoNuevo: PedidoDTO): PedidoDTO {
        return pedidosService.actualizarEstado(pedidoNuevo)
    }
}