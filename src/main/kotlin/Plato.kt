package ar.edu.unsam.algo2.algoQuePedir

// import kotlinx.datetime.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.max

val porcentajePorNuevoMAX = 30.0
val porcentajePorNuevoMIN = 10.0

class Plato(
    val nombre: String = "",
    val descripcion: String = "",
    private var valorBase: Double = 0.0,
    var esDeAutor: Boolean = false,
    val local: Local = Local(),
    val ingredientes: MutableList<Ingrediente> = mutableListOf(),
    val fechaDeCreacion: LocalDate = LocalDate.of(2000, 1, 1),
) : ElementoDeRepositorio {

    private var porcentajeDescuento: Double = 0.0


    fun agregarIngrediente(ingrediente: Ingrediente) {
        if (this.contieneIngrediente(ingrediente)) {
            throw IngredienteYaPerteneceAlPlato("${ingrediente.nombre} ya pertenece al plato")
        } else {
            ingredientes.add(ingrediente)
        }
    }

    fun sacarIngrediente(ingrediente: Ingrediente) {
        if (this.contieneIngrediente(ingrediente)) {
            ingredientes.remove(ingrediente)
        } else {
            throw IngredienteNoEstaEnElPlatoException("${ingrediente.nombre} no pertenece al plato.")
        }
    }

    private fun contieneIngrediente(ingrediente: Ingrediente) = ingredientes.contains(ingrediente)

    fun esDeOrigenAnimal() = ingredientes.any { it.esOrigenAnimal }

    private fun porcentajeAplicacion(): Double = valorBase * local.porcentajeAcordado

    private fun porcentajeRegalias(): Double = if (esDeAutor) valorBase * local.regalias else 0.0

    fun aplicacionesPrecioBase(): Double = valorBase + porcentajeAplicacion() + porcentajeRegalias() + costoProduccion()

    fun costoProduccion(): Double = ingredientes.sumOf { it.costoMercado }

    fun valorVenta(): Double = if (this.esNuevo()) valorVentaNuevo() else valorVentaEnPromo()

    private fun valorVentaNuevo(): Double =
        this.aplicacionesPrecioBase() - (this.aplicacionesPrecioBase() * this.porcentajePorNuevo())

    private fun valorVentaEnPromo(): Double =
        this.aplicacionesPrecioBase() - (this.aplicacionesPrecioBase() * this.porcentajeDescuento)

    fun aplicarDescuento(descuento: Double) {
        if (descuento < 1.0) {
            // Aplica descuento solo si el plato no es nuevo y el descuento es menor al 100%
            if (!this.esNuevo()) {
                porcentajeDescuento = descuento
            } else throw NoAplicaDescuentoEnPlatoNuevoException("Descuento no aplicable. El plato es nuevo.")
        } else throw DescuentoMayorAlCienException("Descuento mayor al 100% no aplicable.")
    }

    fun sacarDescuento() {
        porcentajeDescuento = 0.0
    }

    fun diasDesdeCreacion() = fechaDeCreacion.diasHastaAhora()

    fun esNuevo(): Boolean = diasDesdeCreacion() <= 30

    fun porcentajePorNuevo(): Double = max(porcentajePorNuevoMAX - diasDesdeCreacion(), porcentajePorNuevoMIN) / 100

    fun esVegano(): Boolean = !this.esDeOrigenAnimal()
    //    ========== Metodos de busqueda del repositorio ==============

    override var id = 0

    override fun cumpleCriterioDeBusqueda(criterio: String): Boolean =
        coincideParcialmenteCon(criterio, nombre) ||
                coincideParcialmenteCon(criterio, descripcion) ||
                coincideParcialmenteCon(criterio, local.nombre) ||
                coincideTotalmenteCon(criterio, local.direccion.calle)

    override fun cumpleCriterioDeCreacion(): Boolean =
        noEstaVacio(nombre) && noEstaVacio(descripcion) && noEstaVacio(local.nombre) && noEstaVacio(
            local.direccion.calle
        )
}