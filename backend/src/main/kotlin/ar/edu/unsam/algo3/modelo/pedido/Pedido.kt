package ar.edu.unsam.algo3.modelo.pedido
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.modelo.cupon.Cupon
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.errores.CuponNoAplicable
import ar.edu.unsam.algo3.errores.MedioDePagoNoPermitido
import ar.edu.unsam.algo3.errores.NoEsElMismoUsuario
import ar.edu.unsam.algo3.errores.PlatoNoEsDeLocalException
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.cupon.nullCupon
import java.time.LocalDate
import java.time.LocalTime

enum class Estado {
    PENDIENTE,
    PREPARADO,
    ENTREGADO,
    CANCELADO,
    CONFIRMADO // nose si va esto, pero lo puse para que sea obvio
}

class Pedido(
    var usuario: Usuario = Usuario(),
    var local: Local,
    var platos: MutableList<Plato> = mutableListOf(),
    var medioDePagoElegido: Pago = Pago.EFECTIVO,
    var estado: Estado = Estado.PENDIENTE, // siempre tiene que comenzar en PENDIENTE según el profe.
    var cupon: Cupon? = nullCupon,
    val pedidoObservers: MutableList<PedidoConfirmadoObserver> = mutableListOf<PedidoConfirmadoObserver>(),
    var fechaCreacion: LocalDate = LocalDate.now()
) {
    var horarioEntrega: LocalTime = LocalTime.of(17, 0)
    var id: Int = -1

    fun pagaCon(medioDePago: Pago) {
        if (local.esValidoMedioDePago(medioDePago)) {
            this.medioDePagoElegido = medioDePago // Asigna el medio de pago si es válido
        } else {
            throw MedioDePagoNoPermitido("Este medio de pago no es aceptado por el local")
        }
    }

    fun agregarPlato(plato: Plato) {
        if (!(esLocalPropietarioPlatos(plato.local) || platos.isEmpty())) {
            throw PlatoNoEsDeLocalException("El plato no pertenece al mismo local")
        }
        platos.add(plato)
    }

    fun costoBasePlatos(): Double {
        return platos.sumOf { it.valorVenta() }
    }

    fun costoDeliveryPlatos(): Double {
        return platos.sumOf { it.aplicacionesPrecioBase() * 0.1 }
    }

    fun costoPedido(): Double =
        this.costoBasePlatos() + this.costoDeliveryPlatos()

    fun validarUsuarioDePedido(usuario: Usuario) { // todo: también tendria que ver que los platos sean del local
        if (this.usuario.id != usuario.id) {
            throw NoEsElMismoUsuario("El usuario no puede confirmar el pedido porque no le pertenece")
        }
        estado = Estado.CONFIRMADO // el usuario es el que lo confirma
        usuario.registrarLocalParaPuntuar(this)
    }

    fun estaEnEstado(estado: Estado) = this.estado == estado

    fun esCertificado(): Boolean {
        // Verificar si el usuario cumple con las condiciones
        return (usuario.esAntiguo() && (local.mejorPuntuado()))
    }

    fun totalAPagar(): Double =
        if (medioDePagoElegido == Pago.EFECTIVO) costoPedido() else costoPedido() * 1.05

    private fun esLocalPropietarioPlatos(localPlato: Local) = platos.all { it.local == localPlato }

    fun direccionEntrega() = usuario.direccion

    fun direccionRetiro() = local.direccion

    fun aplicarCupon(cupon: Cupon) {
        if (!cupon.esAplicable(this)) {
            throw CuponNoAplicable("Este cupon no cumple las condiciones necesarias para ser aplicado")
        } else {
            this.cupon = cupon
            cupon.fechaDeUsoCupon = LocalDate.now()
        }
    }

    fun tieneCuponAplicado(): Boolean = this.cupon != nullCupon

    fun totalAPagarConCupon(): Double =
        totalAPagar() - (cupon?.totalADescontar(this) ?: 0.0)
    // ?. es el operador de llamada segura, si cupon no es null hace lo de la derecha

    fun agregarObserver(pedidoObserver: PedidoConfirmadoObserver) {
        pedidoObservers.add(pedidoObserver)
    }

    fun dispararObservers() {
        pedidoObservers.forEach { it.pedidoConfirmado(this) }
    }

    fun totalmenteVegano() = platos.all{ it.esVegano() }

    fun actualizar(otroPedido: Pedido) {
        usuario = otroPedido.usuario
        local = Local()
        platos = otroPedido.platos
        medioDePagoElegido = otroPedido.medioDePagoElegido
        estado = otroPedido.estado
        id = otroPedido.id
    }

    fun prepararPedido() {
        this.estado = Estado.PREPARADO
    }

    fun cancelar() {
        this.estado = Estado.CANCELADO
    }
}



