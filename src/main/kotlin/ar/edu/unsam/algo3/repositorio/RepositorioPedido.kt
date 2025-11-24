package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import org.springframework.stereotype.Component
import java.time.LocalDate


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

    fun getAllOrdersOfLocal(local: Local): List<Pedido> {
        return allInstances().filter { it.local.email == local.email }
    }

    fun crear(
        usuario: Usuario,
        local: Local,
        platos: MutableList<Plato>,
        medioDePago: Pago,
        estado: Estado,
        fechaCreacion: LocalDate = LocalDate.now() // Agrego esto xq sino todos los pedidos son del mismo dia, hay que mandar la fecha en la que se realizan desde el front
    ): Pedido {
        val pedido = Pedido(
            usuario = usuario,
            local = local,
            platos = platos,
            medioDePagoElegido = medioDePago,
            estado = estado,
            fechaCreacion = fechaCreacion
        )
        crear(pedido)
        return pedido
    }

    fun crear(pedido: Pedido): Pedido {
        pedido.id = ultimoId++
        pedidos.add(pedido)
        return pedido
    }

    fun buscarPorId(id: Int): Pedido {
        val pedido = allInstances().find { it.id == id }
        if (pedido != null) {
//            println(pedido.toString())
//            println(pedido.platos.size)
            return pedido
        }
        throw NotFoundException("No existe el id $id en repositorio para obtenerlo de la coleccion")
    }

    fun update(pedido: Pedido): Pedido {
        // busco el pedido por id y me guardo el indice
        val indexPedido = pedidos.indexOfFirst { it.id == pedido.id }
        if (indexPedido == -1) throw NotFoundException("El id ${pedido.id} no existe en el repositorio")
        pedidos[indexPedido] = pedido
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