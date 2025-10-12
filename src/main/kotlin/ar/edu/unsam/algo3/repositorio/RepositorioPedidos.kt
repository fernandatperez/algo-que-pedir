package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo2.algoQuePedir.ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.plato.Plato
import org.springframework.stereotype.Component


@Component
class RepositorioPedidos {

    val pedidos = mutableListOf<Pedido>()

    companion object {
        private var ultimoId = ID_INICIAL_REPOSITORY
    }

    fun allInstances(): List<Pedido> {
        return pedidos
    }

    fun filteredInstances(estado: Estado): List<Pedido> = allInstances().filter { it.estado == estado }

    fun crear(
        usuario: Usuario,
        local: Local,
        platos: MutableList<Plato>,
        medioDePago: Pago,
        estado: Estado,
    ) {
        val pedido = Pedido(
            usuario = usuario,
            local = local,
            platos = platos,
            medioDePagoElegido = medioDePago,
            estado = estado,
        )
        crear(pedido)
    }

    fun crear(pedido: Pedido): Pedido {
        pedido.id = ultimoId++
        pedidos.add(pedido)
        return pedido
    }

    fun buscarPorId(id: Int): Pedido? = allInstances().find { it.id == id }

    fun update(pedido: Pedido): Pedido {
        // busco el pedido por id y me guardo el indice
        val indexPedido = pedidos.indexOfFirst { it.id == pedido.id }
        pedidos.removeAt(indexPedido)
        pedidos.add(indexPedido, pedido)
        return pedido
    }

    fun delete(pedido: Pedido): Pedido {
        pedidos.remove(pedido)
        return pedido
    }

    fun clear() {
        pedidos.clear()
    }

    fun clearInit() {
        clear()
        ultimoId = ID_INICIAL_REPOSITORY
    }

}

//val repositorioPedidos = Repositorio<Pedido>().apply {
//}