package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.repositorio.RepositorioPedido
import org.springframework.stereotype.Service


@Service
class PedidoService(
    val repositorioPedidos: RepositorioPedido
) {

    fun pedidosFiltrados(estado: Estado, email: String) = repositorioPedidos.filteredInstances(estado, email).map { it.toDTO() }

    fun buscarPorID(id: Int): Pedido = repositorioPedidos.buscarPorId(id) ?: throw NotFoundException("No se encontró el pedido de id <$id>")

    fun actualizarEstado(id: Int) : Pedido {
        val pedido = buscarPorID(id)
        pedido.prepararPedido()
        repositorioPedidos.update(pedido)
        return pedido
    }
}