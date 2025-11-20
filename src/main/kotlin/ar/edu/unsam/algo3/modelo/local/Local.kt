package ar.edu.unsam.algo3.modelo.local

import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.utils.diasHastaAhora
import ar.edu.unsam.algo3.errores.SobrepasoPuntuacion
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.usuario.Calificacion
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.modelo.utils.Mensaje
import ar.edu.unsam.algo3.repositorio.ElementoDeRepositorio

enum class Pago {
    EFECTIVO,
    QR,
    TRANSFERENCIA_BANCARIA,
}

open class Local(
    var nombre: String = "",
    var email: String = "",
    var password: String = "",
    var direccion: Direccion = Direccion(),
    var porcentajeAcordado: Double = 0.0,
    var regalias: Double = 0.0,
    //esto lo agrego por que se pide en ALGO3
    var url: String = "",
    var mediosDePago: MutableSet<Pago> = mutableSetOf(Pago.EFECTIVO),
    val inbox: MutableList<Mensaje> = mutableListOf(),

) : ElementoDeRepositorio {

    fun deliveryFee(): Double = regalias + porcentajeAcordado

    var calificaciones = mutableListOf<Calificacion>()

    fun cantidadDePuntuaciones() = calificaciones.size

    fun mejorPuntuado() = this.promedioPuntuacion() in (4.0..5.0)

    //válido el medio de pago elegido en el pedido de los medios de pago disponibles
    fun esValidoMedioDePago(medioDePago: Pago): Boolean = mediosDePago.contains(medioDePago)

    //correccion de juli
    fun modificarRegalia(regalia: Double) {
        regalias = regalia
    }

    fun agregarPuntuacion(calificacion: Calificacion) {
        if (puntajeEntre1y5(calificacion.puntaje)) {
            calificaciones.add(calificacion)
//            puntuaciones.add(calificacion.puntaje)
//            comentarios.add(calificacion.descripcion)
        } else {
            throw SobrepasoPuntuacion("La puntuación debe estar entre 1 y 5")
        }
    }

    fun puntajeEntre1y5(puntaje: Int) = puntaje in (1..5)

    fun obtenerCalificaciones(): List<Calificacion> = this.calificaciones

    fun obtenerComentarios(): List<String> = this.calificaciones.map { it.comentario }

    fun obtenerPuntuaciones(): List<Int> = this.calificaciones.map { it.puntaje }

    fun promedioPuntuacion(): Double {
        return if (calificaciones.isNotEmpty()) calificaciones.map{ it.puntaje }.average() else 0.0
    }

//    Esto como hago para que no este hardcodeado? No tenemos una lista de pedidos realizados ni en cuanto
//    tiempo se hicieron...alto bondi agregar eso
    fun promedioTiempoEntrega(): String = "30-40 min"

//    Es costoso si tiene muchas regalias.
//    Lo habia pensado tambien por el lado de que si los platos eran muy caros pero es un bondi
//    xq local no conoce platos.
    fun esCostoso(): Boolean = this.regalias >= 0.5

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

    override fun cumpleCriterioDeBusqueda(criterio: String): Boolean {
    val criterioLower = criterio.lowercase()

    return coincideParcialmenteCon(criterioLower, nombre) ||
                coincideParcialmenteCon(criterio, direccion.calle) || coincideParcialmenteCon(criterio, email)
    }

    override fun cumpleCriterioDeCreacion() {
        if (!noEstaVacio(nombre)) throw NotFoundException("El Local tiene que tener un nombre")
//        Esto se va por que habria que poner en register un campo para darle una calle.
//        if (!noEstaVacio(direccion.calle)) throw NotFoundException("El Local tiene que tener una direccion")
        if (!noEstaVacio(email))throw NotFoundException("El Local tiene que tener una email")
        if (!noEstaVacio(direccion.ubicacion.x.toString() ))
            throw NotFoundException("El Local tiene que tener latitud informada")
        if ((direccion.ubicacion.y < -90 || direccion.ubicacion.y > 90 ) || direccion.ubicacion.y.isNaN())
            throw NotFoundException("El Local tiene que tener longitud entre -90° y 90°")
        if ((direccion.ubicacion.x < -90 || direccion.ubicacion.x > 90) || direccion.ubicacion.x.isNaN())
            throw NotFoundException("El Local tiene que tener latitud entre -90° y 90°")
        if ( regalias.isNaN() || regalias < 0 || regalias > 100)
            throw NotFoundException("El local debe tener un porcentaje valido entre 0 y 100" )

        if ( porcentajeAcordado.isNaN() || porcentajeAcordado < 0 || porcentajeAcordado > 100)
            throw NotFoundException("El local debe tener un porcentaje valido entre 0 y 100" )
    }


}