package ar.edu.unsam.algo3.modelo.plato

// import kotlinx.datetime.*
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.modelo.utils.diasHastaAhora
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.utils.redondear
import ar.edu.unsam.algo3.repositorio.ElementoDeRepositorio
import java.time.LocalDate
import kotlin.math.max

val porcentajePorNuevoMAX = 30.0
val porcentajePorNuevoMIN = 10.0

class Plato(
    val nombre: String = "",
    val descripcion: String = "",
    private var valorBase: Double = 0.0,
    var urldeImagen: String = "Sin URL para mostrar",
    var esDeAutor: Boolean = false,
    var local: Local = Local(),
    val ingredientes: MutableList<Ingrediente> = mutableListOf(),
    var fechaDeCreacion: LocalDate = LocalDate.of(2000, 1, 1),
) : ElementoDeRepositorio {

    var enPromocion: Boolean = false
    var porcentajeDescuento: Double = 0.0


    fun agregarIngrediente(ingrediente: Ingrediente) {
        if (this.contieneIngrediente(ingrediente)) {
            throw BusinessException("${ingrediente.nombre} ya pertenece al plato")
        } else {
            ingredientes.add(ingrediente)
        }
    }

    fun sacarIngrediente(ingrediente: Ingrediente) {
        if (this.contieneIngrediente(ingrediente)) {
            ingredientes.remove(ingrediente)
        } else {
            throw BusinessException("${ingrediente.nombre} no pertenece al plato.")
        }
    }

    private fun contieneIngrediente(ingrediente: Ingrediente) = ingredientes.contains(ingrediente)

    fun esDeOrigenAnimal() = ingredientes.any { it.esOrigenAnimal }

    private fun porcentajeAplicacion(): Double = valorBase * local.porcentajeAcordado

    private fun porcentajeRegalias(): Double = if (esDeAutor) valorBase * local.regalias else 0.0

    fun aplicacionesPrecioBase(): Double = valorBase + porcentajeAplicacion() + porcentajeRegalias() + costoProduccion()

    fun costoProduccion(): Double = ingredientes.sumOf { it.costoMercado }

    fun valorVenta(): Double = (if (this.esNuevo()) valorVentaNuevo() else valorVentaEnPromo()).redondear(2)

    private fun valorVentaNuevo(): Double =
        this.aplicacionesPrecioBase() - (this.aplicacionesPrecioBase() * this.porcentajePorNuevo())

    private fun valorVentaEnPromo(): Double =
        this.aplicacionesPrecioBase() - (this.aplicacionesPrecioBase() * this.porcentajeDescuento)

    fun aplicarDescuento(descuento: Double) {
        if (descuento < 1.0) {
            // Aplica descuento solo si el plato no es nuevo y el descuento es menor al 100%
            if (!this.esNuevo()) {
                porcentajeDescuento = descuento
            } else throw BusinessException("Descuento no aplicable. El plato es nuevo.")
        } else throw BusinessException("Descuento mayor al 100% no aplicable.")
    }

    fun sacarDescuento() {
        porcentajeDescuento = 0.0
    }

    fun diasDesdeCreacion() = fechaDeCreacion.diasHastaAhora()

    fun esNuevo(): Boolean = diasDesdeCreacion() <= 30

    fun porcentajePorNuevo(): Double = max(porcentajePorNuevoMAX - diasDesdeCreacion(), porcentajePorNuevoMIN) / 100

    fun esVegano(): Boolean = !this.esDeOrigenAnimal()

    fun getValorBase(): Double = this.valorBase

    fun setValorBase(valor: Double) { this.valorBase = valor }

    //    ========== Metodos de busqueda del repositorio ==============

    override var id = 0

    override fun cumpleCriterioDeBusqueda(criterio: String): Boolean =
        coincideParcialmenteCon(criterio, nombre) ||
                coincideParcialmenteCon(criterio, descripcion) ||
                coincideParcialmenteCon(criterio, local.nombre) ||
                coincideTotalmenteCon(criterio, local.direccion.calle) ||
                coincideParcialmenteCon(criterio, local.email)

    override fun cumpleCriterioDeCreacion() {
        if (!noEstaVacio(nombre)) throw NotFoundException("El Plato tiene que tener un nombre")
        if (!noEstaVacio(descripcion)) throw NotFoundException("El Plato tiene que tener una descripcion")
        if (!noEstaVacio(local.nombre)) throw NotFoundException("El Local tiene que tener un nombre")
        if (!noEstaVacio(local.direccion.calle)) throw NotFoundException("El Local tiene que tener una calle")

    }
}