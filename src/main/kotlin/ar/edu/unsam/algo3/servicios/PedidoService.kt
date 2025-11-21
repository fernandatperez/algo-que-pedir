package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.dto.OrderDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.repositorio.RepositorioCliente
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.repositorio.RepositorioPedido
import ar.edu.unsam.algo3.repositorio.RepositorioPlato
import org.springframework.stereotype.Service


@Service
class PedidoService(
    val repositorioPedidos: RepositorioPedido,
    val repositorioPlato: RepositorioPlato,
    val repositorioCliente: RepositorioCliente,
    val repositorioLocal: RepositorioLocal,
) {
    fun pedidosFiltradosLocal(estado: Estado, email: String) = repositorioPedidos.filteredStoreInstances(estado, email).map { it.toDTO() }

    fun pedidosFiltradosUsuario(estado: Estado, email: String) = repositorioPedidos.filteredUserInstances(estado, email).map { it.toDTO() }

    fun buscarPorID(id: Int): Pedido = repositorioPedidos.buscarPorId(id)

    fun actualizarEstado(id: Int) : Pedido {
        val pedido = buscarPorID(id)
        pedido.prepararPedido()
        repositorioPedidos.update(pedido)
        return pedido
    }

    fun createOrder(order: OrderDTO) {
        //! todo: aca lo mejor es buscar el
        // usuario
        // local
        // platos
        val usuario: Usuario = repositorioCliente.obtenerObjeto(order.userID)
        val local: Local = repositorioLocal.obtenerObjeto(order.localID)
        val platos: MutableList<Plato> = order.platosIDs.map { repositorioPlato.obtenerObjeto(it) }.toMutableList()
//        println(platos.size)

        val medioDePago = Pago.valueOf(order.medioDePago)
        val estadoPedido = Estado.valueOf(order.estado)

        if (order.subtotal != platos.sumOf { it.valorVenta() }) throw BusinessException("El valor de un plato cambio. Mal ahi.")

        repositorioPedidos.crear(
            usuario = usuario,
            local = local,
            platos = platos,
            medioDePago = medioDePago,
            estado = estadoPedido,
            //medioDePago = Pago.EFECTIVO,
            //estado = Estado.PENDIENTE,
        ) . apply {
            usuario.registrarLocalParaPuntuar(this)
        }
    }
}