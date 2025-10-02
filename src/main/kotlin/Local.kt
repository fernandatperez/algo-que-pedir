package ar.edu.unsam.algo2.algoQuePedir

import Mensaje

enum class Pago {
    EFECTIVO,
    QR,
    TRANSFERENCIA_BANCARIA,
}

class Local(
    val nombre: String = "",
    val direccion: Direccion = Direccion(),
    var porcentajeAcordado: Double = 0.0,
    var regalias: Double = 0.0,
    val mediosDePago: MutableSet<Pago> = mutableSetOf(Pago.EFECTIVO),
    val inbox: MutableList<Mensaje> = mutableListOf()
) : ElementoDeRepositorio {

    private val puntuaciones = mutableListOf<Int>()

    fun mejorPuntuado() = this.promedioPuntuacion() in (4.0..5.0)

    //válido el medio de pago elegido en el pedido de los medios de pago disponibles
    fun esValidoMedioDePago(medioDePago: Pago): Boolean = mediosDePago.contains(medioDePago)

    //correccion de juli
    fun modificarRegalia(regalia: Double) {
        regalias = regalia
    }

    fun agregarPuntuacion(puntuacion: Int) {
        if (puntuacion in (1..5)) {
            puntuaciones.add(puntuacion)
        } else {
            throw SobrepasoPuntuacion("La puntuación debe estar entre 1 y 5")
        }
    }

    fun promedioPuntuacion(): Double {
        return if (puntuaciones.isNotEmpty()) puntuaciones.average() else 0.0
    }

    // el local lo prepara?
    fun prepararPedido(pedido: Pedido) {
        pedido.estado = Estado.PREPARADO
    }

    fun agregarMedioDePago(medio: Pago) {
        mediosDePago.add(medio)
    }

    fun eliminarMedioDePago(medio: Pago) {
        if (mediosDePago.contains(medio)) {
            mediosDePago.remove(medio)
        } else {
            throw RuntimeException("El local no posee ese medio de pago")
        }
    }

    fun recibirMensaje(mensaje: Mensaje) {
        inbox.add(mensaje)
    }

    fun leer(mensaje: Mensaje) {
        if (!inbox.contains(mensaje)) {
            throw RuntimeException("Ese mensaje no esta en el inbox")
        }
        mensaje.leido = true
    }

    fun borrarMensajesAntiguosYLeidos() {
        inbox.removeIf { (it.fechaDeEmision.diasHastaAhora() > 30) && (it.leido) } // Remueve de la coleccion segun la condicion
    }

    fun borrarMensaje(mssg: Mensaje) = inbox.remove(mssg) // utilizar este metodo dentro de un forEach lanza una excepcion
    // No se puede modificar una coleccion mientras se la recorre -> java.util.ConcurrentModificationException

    //    ========== Metodos de busqueda del repositorio ==============

    override var id = 0

    override fun cumpleCriterioDeBusqueda(criterio: String): Boolean =
        coincideParcialmenteCon(criterio, nombre) ||
                coincideTotalmenteCon(criterio, direccion.calle)

    override fun cumpleCriterioDeCreacion(): Boolean =
        noEstaVacio(nombre) && noEstaVacio(direccion.calle)



}