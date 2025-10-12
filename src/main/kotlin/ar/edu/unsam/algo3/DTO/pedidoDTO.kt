package ar.edu.unsam.algo3.DTO

import ar.edu.unsam.algo2.algoQuePedir.ar.edu.unsam.algo3.Usuario
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.utils.Direccion
import org.uqbar.geodds.Point
import java.time.LocalTime

data class PedidoDTO (
    val id: Int,
    val nombre: String,
    val username: String,
    val lat: String,
    val long: String,
    val deliveryComission: Number,
    val metodoDePago: Pago,
    val estado: Estado,
    val horarioEntrega: String,
) {
    lateinit var platos: MutableList<PlatoDTO> // Lista de Platos
    lateinit var direccion: String
    var precioSubtotal: Double = 0.0
}

fun Pedido.toDTO(): PedidoDTO {
    val pedidoDTO = PedidoDTO(
        id = this.id,
        nombre = this.usuario.nombre,
        username = this.usuario.username,
        lat = this.usuario.direccion.ubicacion.x.toString(),
        long = this.usuario.direccion.ubicacion.y.toString(),
        deliveryComission = this.costoDeliveryPlatos(),
        metodoDePago = this.medioDePagoElegido,
        estado = this.estado,
        horarioEntrega = this.horarioEntrega.toString(),
    ).apply {
        val direccionEntera = this@toDTO.usuario.direccion.calle + " " + this@toDTO.usuario.direccion.altura
        this.direccion = direccionEntera
        this.precioSubtotal = this@toDTO.costoBasePlatos()
        this.platos = this@toDTO.platos.map { it.toDTO() }.toMutableList()
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

data class PlatoDTO (
    val id: Int,
    // alt: string
    val nombre: String,
    val descripcion: String,
//     val imagen: String,
    val esDeAutor: Boolean,
    val enPromocion: Boolean,
    val ingredientes: MutableList<Ingrediente>,
) {
    var precio: Double = 0.0
    var costoProduccion: Double = 0.0
}

fun Plato.toDTO() : PlatoDTO {
    val platoDTO = PlatoDTO(
        id = this.id,
        nombre = this.nombre,
        descripcion = this.descripcion,
        esDeAutor = this.esDeAutor,
        enPromocion = false,
        ingredientes = this.ingredientes,
    ).apply {
        this.costoProduccion = this@toDTO.costoProduccion()
        this.precio = this@toDTO.valorVenta()
    }
    return platoDTO
}
