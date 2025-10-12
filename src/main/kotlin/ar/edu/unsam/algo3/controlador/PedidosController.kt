package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.servicios.PedidosService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class PedidosController(val pedidosService: PedidosService) {

    @GetMapping("/pedidos")
    fun pedidos() = pedidosService.pedidos()

    @GetMapping("/pedidos/{estado}")
    fun pedidosFiltrados(@PathVariable estado: Estado) = pedidosService.pedidosFiltrados(estado)

}