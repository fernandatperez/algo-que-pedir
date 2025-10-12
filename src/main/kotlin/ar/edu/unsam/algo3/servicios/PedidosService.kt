package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.DTO.toDTO
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.repositorio.RepositorioPedidos
import ar.edu.unsam.algo3.repositorio.RepositorioPlatos
import org.springframework.stereotype.Service


@Service
class PedidosService(
    val repositorioPedidos: RepositorioPedidos,
    val repositorioPlatos: RepositorioPlatos
) {

    fun pedidos() = repositorioPedidos.allInstances()

    fun pedidosFiltrados(estado: Estado) = repositorioPedidos.filteredInstances(estado).map { it.toDTO() }
}