package ar.edu.unsam.algo3.DTO

import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.modelo.utils.Direccion
import org.uqbar.geodds.Point
import java.time.LocalTime

data class PedidoDTO (
    val id: Int,
    val nombre: String,
    val username: String,
    val lat: String,
    val long: String,
    val platos: MutableList<Plato>, // Lista de Platos
    val deliveryComission: Number,
    val metodoDePago: Pago,
    val estado: Estado,
    val horarioEntrega: String,
) {
    lateinit var direccion: String
}

fun Pedido.toDTO(): PedidoDTO {
    val pedidoDTO = PedidoDTO(
        id = this.id,
        nombre = this.usuario.nombre,
        username = this.usuario.username,
        lat = this.usuario.direccion.ubicacion.x.toString(),
        long = this.usuario.direccion.ubicacion.y.toString(),
        platos = this.platos, // Lista de Platos
        deliveryComission = this.costoDeliveryPlatos(),
        metodoDePago = this.medioDePagoElegido,
        estado = this.estado,
        horarioEntrega = this.horarioEntrega.toString(),
    ).apply {
        val direccionEntera = this@toDTO.usuario.direccion.calle + " " + this@toDTO.usuario.direccion.altura
        this.direccion = direccionEntera
    }
    return pedidoDTO
}

fun Pedido.fromDTO(
    id: Int,
    nombre: String,
    username: String,
    direccion: String,
    lat: String,
    long: String,
    platos: MutableList<Plato>, // Lista de Platos
    deliveryComission: Number,
    metodoDePago: Pago,
    estado: Estado,
    horarioEntrega: LocalTime,
) {
    val direccionUsuario = Direccion(
        calle = direccion,
        // altura: Int = 0,
        // deberia haber calle y altura pero por ahora queda asi
        ubicacion = Point(lat.toDouble(), long.toDouble()),
    )
    val usuario = Usuario(
        nombre = nombre,
        username = username,
        direccion = direccionUsuario,
    )

    val pedido = Pedido(
        usuario = usuario,
        local = Local(),
        platos = platos,
        medioDePagoElegido = metodoDePago,
        estado = estado,
    ).apply {
        this.horarioEntrega = horarioEntrega.plusMinutes(40)
        this.id = id
    }

}