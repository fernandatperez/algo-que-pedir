package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.PedidoDTO
import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.repositorio.RepositorioPedido
import ar.edu.unsam.algo3.repositorio.RepositorioPlato
import org.springframework.stereotype.Service


@Service
class PedidoService(
    val repositorioPedidos: RepositorioPedido,
    val repositorioPlatos: RepositorioPlato
) {

    fun pedidos() = repositorioPedidos.allInstances()

    fun pedidosFiltrados(estado: Estado) = repositorioPedidos.filteredInstances(estado).map { it.toDTO() }

    fun pedidoPorId(id: Int): PedidoDTO = repositorioPedidos.buscarPorId(id)?.toDTO() ?: throw RuntimeException("No se encontró el pedido de id <$id>")

    fun actualizarEstado(id: Int, pedidoActualizado: PedidoDTO) : PedidoDTO {
        if (pedidoActualizado.id == null || pedidoActualizado.id == -1) {
            throw RuntimeException("Debe proveerse el ID de la tarea a actualizar")
        }
        if (pedidoActualizado.id!! != id) {
            throw RuntimeException("Id en URL distinto del id que viene en el body")
        }
        val pedido = pedidoActualizado.fromDTO()
        repositorioPedidos.update(pedido)
        return pedidoPorId(pedido.id)
    }

}