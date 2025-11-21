package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.OrderDTO
import ar.edu.unsam.algo3.dto.PedidoDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.servicios.PedidoService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class PedidoController(val pedidosService: PedidoService) {

    @GetMapping("/pedidos/")
    fun pedidos(@RequestParam estado: Estado, @RequestParam("local") email: String) = pedidosService.pedidosFiltradosLocal(estado, email)

    @GetMapping("/pedidos-usuario/")
    fun getByUserEmail(@RequestParam estado: Estado, @RequestParam("usuario") email: String) = pedidosService.pedidosFiltradosUsuario(estado, email)

    @GetMapping("/pedido/{id}")
    fun pedidoPorId(@PathVariable id: Int): PedidoDTO = pedidosService.buscarPorID(id).toDTO()

    @PutMapping("/preparar_pedido/{id}")
    fun actualizarEstado(@PathVariable id: Int): PedidoDTO {
        return pedidosService.actualizarEstado(id).toDTO()
    }

    @PostMapping("/create-order/")
    fun createOrder(@RequestBody order: OrderDTO) {
        pedidosService.createOrder(order)
    }

    @PostMapping("/debug-pedidos")
    fun debugPedidos(): List<Pedido> {
        return pedidosService.repositorioPedidos.allInstances()
    }
}