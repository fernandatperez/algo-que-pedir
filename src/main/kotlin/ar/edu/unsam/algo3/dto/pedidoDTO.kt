package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import java.time.LocalDate

data class PedidoDTO (
    val id: Int,
    val nombre: String,
    val username: String,
    var direccion: String,
    var altura: Int,
    val lat: String,
    val long: String,
    val deliveryComission: Number,
    val metodoDePago: Pago,
    val estado: Estado,
    val horarioEntrega: String,
    val fechaCreacion: LocalDate,
    val local: LocalDTO
) {
    lateinit var platos: MutableList<PlatoDTO> // Lista de Platos
    lateinit var direccionEntera: String
    var precioSubtotal: Double = 0.0
    var serviceFee: Double = 0.0
    var deliveryFee: Double = 0.0
}

fun Pedido.toDTO(): PedidoDTO {
    val pedidoDTO = PedidoDTO(
        id = this.id,
        nombre = this.usuario.nombre,
        username = this.usuario.username,
        direccion = this.usuario.direccion.calle,
        altura = this.usuario.direccion.altura,
        lat = this.usuario.direccion.ubicacion.x.toString(),
        long = this.usuario.direccion.ubicacion.y.toString(),
        deliveryComission = this.costoDeliveryPlatos(),
        metodoDePago = this.medioDePagoElegido,
        estado = this.estado,
        horarioEntrega = this.horarioEntrega.toString(),
        fechaCreacion = this.fechaCreacion,
        local = this.local.toDTO()
    ).apply {
        this.direccionEntera = this@toDTO.usuario.direccion.calle + " " + this@toDTO.usuario.direccion.altura

        this.precioSubtotal = this@toDTO.costoBasePlatos() // subtotal
        this.serviceFee = if (this@toDTO.medioDePagoElegido == Pago.EFECTIVO) 0.0 else this.precioSubtotal * 0.1 // serviceFee
        this.deliveryFee = this@toDTO.local.deliveryFee() // deliveryFee

        this.platos = this@toDTO.platos.map { it.toDTO() }.toMutableList()


    }
    return pedidoDTO
}

// ver como cambiar esto para que sea mejor?
data class OrderDTO (
    val userID: Int,
    val localID: Int,
    val platosIDs: MutableList<Int>,
    val medioDePago: String,
    val estado: String,
    val subtotal: Double
)

//fun Pedido.fromOrderDTO(order : OrderDTO): PedidoDTO {
//    return
//}