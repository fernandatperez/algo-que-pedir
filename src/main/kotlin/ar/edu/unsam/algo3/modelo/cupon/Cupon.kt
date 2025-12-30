package ar.edu.unsam.algo3.modelo.cupon
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.repositorio.ElementoDeRepositorio
import java.time.DayOfWeek
import java.time.LocalDate
import kotlin.math.min

abstract class Cupon(
    var porcentajeBaseDescuento: Double = 0.1,
    var fechaDeEmision: LocalDate = LocalDate.now(), // fecha de emision es pedida en la consigna
    var duracion: Long = 10,
    var fechaDeUsoCupon: LocalDate = LocalDate.now()
) : ElementoDeRepositorio {

    fun fechaConsulta(): LocalDate = LocalDate.now()
    fun cambiarFechaDeEmision(fechaInicio: LocalDate) {
        this.fechaDeEmision = fechaInicio
    }

    fun fechaVencimiento(): LocalDate = fechaDeEmision.plusDays(duracion)

    fun noEstaVencido(): Boolean = fechaConsulta().isBefore(fechaVencimiento())

    fun puedeAplicarMontoDescuento(pedido: Pedido): Boolean = pedido.totalAPagar() >= this.totalADescontar(pedido)

    abstract fun puedeAplicarDescuentoEspecial(pedido: Pedido): Boolean // esta es la primitiva

    fun esAplicable(pedido: Pedido): Boolean =
        this.noEstaVencido() && this.puedeAplicarMontoDescuento(pedido) && !pedido.tieneCuponAplicado() && puedeAplicarDescuentoEspecial(
            pedido
        ) // template method

    fun montoBaseDescuento(pedido: Pedido): Double = porcentajeBaseDescuento * pedido.totalAPagar()

    abstract fun descuentoEspecialPorCupon(pedido: Pedido): Double // esta es la primitiva

    //template method para saber cuanto es el total a pagar independientemente del cupon aplicado
    fun totalADescontar(pedido: Pedido): Double =
        montoBaseDescuento(pedido) + descuentoEspecialPorCupon(pedido) // template method

//    fun borrarCuponesNoAplicadosVencidos(repositorioCupones : Repositorio<Cupon>) {
//        if(!noEstaVencido() && !cuponUsado()) {
//            repositorioCupones.eliminarDeColeccion(this.id)
//        }
//    } // No puedo borrar un elemento de coleccion mientras la itero

    fun estaVencidoYNoAplicado() = !noEstaVencido() && !cuponUsado()

    fun cuponUsado() = fechaDeEmision == fechaDeUsoCupon // achekiar

    //    ========== Metodos de busqueda del repositorio ==============

    override var id = 0

    override fun cumpleCriterioDeBusqueda(criterio: String): Boolean =
        criterio.toDoubleOrNull()?.let { it == porcentajeBaseDescuento } ?: false // esto es asi...
    // primero lo hace int o null, let es una funcion extencion -> ( ?.let{} ) si lo que sea que este antes no es null ejecuta el codigo dentro de el let
    // ?: esto es el operador elvis, lo que hace es devolver lo que esta a la izquierda si no es null sino lo de la derecha, es como el operador ternario

    override fun cumpleCriterioDeCreacion() {
        if (porcentajeBaseDescuento == 0.0) throw NotFoundException("El cupon tiene que ser mayor a 0")
    }
}

class DescuentoPorDia : Cupon() {
    var porcentajeAplicado: Double = 0.05

    // En esta variable va el dia de descuento que se puede usar el cupon, seteada por ejemplo como DayOfWeek.FRIDAY
    // le voy a setear LocalDate.now().DayOfWeek para poder testear con fecha del dia y que no vayan rompiendo depende el día que se ejecutan
    var diaDeDescuento: DayOfWeek = LocalDate.now().dayOfWeek

    fun diaDeUsoCupon(): DayOfWeek = fechaDeUsoCupon.dayOfWeek

    fun descuentoExtra(pedido: Pedido): Boolean =
        pedido.platos.any { it.fechaDeCreacion.dayOfWeek == this.diaDeUsoCupon() } && this.diaDeUsoCupon() == diaDeDescuento

    override fun puedeAplicarDescuentoEspecial(pedido: Pedido): Boolean = LocalDate.now().dayOfWeek == diaDeDescuento

    override fun descuentoEspecialPorCupon(pedido: Pedido): Double =
        pedido.totalAPagar() * if (descuentoExtra(pedido)) porcentajeAplicado * 2 else porcentajeAplicado
}

class DescuentoPorLocal : Cupon() {
    val locales: MutableList<Local> = mutableListOf()
    var descuentoAplicado: Double = 500.0

    override fun puedeAplicarDescuentoEspecial(pedido: Pedido): Boolean =
        pedido.local in locales // this.locales.contains(pedido.local) asi estaba antes

    override fun descuentoEspecialPorCupon(pedido: Pedido): Double =
        if (pedido.esCertificado()) descuentoAplicado * 2 else descuentoAplicado
}

class DescuentoPorPorcentaje : Cupon() {
    var topeDeDescuento: Double = 500.0
    var porcentajeDescuentoEspecial: Double = 0.3

    override fun puedeAplicarDescuentoEspecial(pedido: Pedido): Boolean = true

    override fun descuentoEspecialPorCupon(pedido: Pedido): Double =
        min((pedido.totalAPagar() * porcentajeDescuentoEspecial), topeDeDescuento)
}

object nullCupon : Cupon() {
    override fun descuentoEspecialPorCupon(pedido: Pedido): Double = 00.00
    override fun puedeAplicarDescuentoEspecial(pedido: Pedido): Boolean = false
}

