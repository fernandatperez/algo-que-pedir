package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.PedidoDTO
import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.repositorio.RepositorioPedido
import ar.edu.unsam.algo3.repositorio.RepositorioPlato
import org.springframework.stereotype.Service


@Service
class PedidoService(
    val repositorioPedidos: RepositorioPedido
) {
    fun pedidos() = repositorioPedidos.allInstances()

    fun pedidosFiltrados(estado: Estado, email: String) = repositorioPedidos.filteredInstances(estado, email).map { it.toDTO() }

    fun buscarPorID(id: Int): Pedido = repositorioPedidos.buscarPorId(id) ?: throw NotFoundException("No se encontró el pedido de id <$id>")

    fun actualizarEstado(pedidoActualizado: PedidoDTO) : PedidoDTO {
        val pedido = buscarPorID(pedidoActualizado.id)
//        val pedido = pedidoActualizado.fromDTO()

//        pedido.actualizar(pedidoActualizado)
        repositorioPedidos.update(pedido)
        return buscarPorID(pedido.id).toDTO()
    }
}