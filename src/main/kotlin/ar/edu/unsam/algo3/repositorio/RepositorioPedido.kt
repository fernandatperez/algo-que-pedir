package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import org.springframework.stereotype.Component


@Component
class RepositorioPedido {

    val pedidos = mutableListOf<Pedido>()

    companion object {
        private var ultimoId = ID_INICIAL_REPOSITORY
    }

    fun allInstances(): List<Pedido> {
        return pedidos
    }

    fun filteredStoreInstances(estado: Estado, email: String): List<Pedido> = allInstances().filter { it.estado == estado && it.local.email == email }

    fun filteredUserInstances(estado: Estado, email: String): List<Pedido> = allInstances().filter { it.estado == estado && it.usuario.mailPrincipal == email }

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
        if (indexPedido == -1) throw NotFoundException("El id ${pedido.id} no existe en el repositorio")
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