package ar.edu.unsam.algo3.dominio

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonGetter
import com.fasterxml.jackson.annotation.JsonSetter
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.repositorio.ElementoDeRepositorio
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.dominio.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import org.uqbar.geodds.Point

class Local : Entity(), ElementoDeRepositorio {

    override var id: Int = 1

    @JsonProperty("name")
    var nombre: String = ""

    @JsonProperty("storeURL")
    var url: String = ""

    // Propiedad interna - usa Direccion
    var direccion: Direccion = Direccion()

    // JSON: storeAddress (String) -- direccion.calle
    @JsonGetter("storeAddress")
    fun getStoreAddress(): String = "${direccion.calle} "

    @JsonSetter("storeAddress")
    fun setStoreAddress(direccionCompleta: String) {
        direccion.calle = direccionCompleta
    }

    // JSON: storeAltitude ↔ direccion.altura
    @JsonGetter("storeAltitude")
    fun getStoreAltitude(): Int = direccion.altura

    @JsonSetter("storeAltitude")
    fun setStoreAltitude(altura: Int) {
        direccion.altura = altura
    }

    // JSON: storeLatitude ↔ direccion.ubicacion.x
    @JsonGetter("storeLatitude")
    fun getStoreLatitude(): Double = direccion.ubicacion.x

    @JsonSetter("storeLatitude")
    fun setStoreLatitude(latitud: Double) {
        direccion.ubicacion = Point(latitud, direccion.ubicacion.y)
    }

    // JSON: storeLongitude ↔ direccion.ubicacion.y
    @JsonGetter("storeLongitude")
    fun getStoreLongitude(): Double = direccion.ubicacion.y

    @JsonSetter("storeLongitude")
    fun setStoreLongitude(longitud: Double) {
        direccion.ubicacion = Point(direccion.ubicacion.x, longitud)
    }

    @JsonProperty("storeAppCommission")
    var comisionApp: Double = 0.0

    @JsonProperty("storeAuthorCommission")
    var comisionAutor: Double = 0.0

    // es una propiedad interna de la clase de dominio, el medio de pago es un enum
    var mediosDePago: MutableSet<Pago> = mutableSetOf()

    // JSON: aca como en el json del front son variables individuales, lo separa
    @JsonGetter("storePaymentEfectivo")
    fun getStorePaymentEfectivo(): Boolean = mediosDePago.contains(Pago.EFECTIVO)

    @JsonSetter("storePaymentEfectivo")
    fun setStorePaymentEfectivo(habilitado: Boolean) {
        if (habilitado) {
            mediosDePago.add(Pago.EFECTIVO)
        } else {
            mediosDePago.remove(Pago.EFECTIVO)
        }
    }

    @JsonGetter("storePaymentQR")
    fun getStorePaymentQR(): Boolean = mediosDePago.contains(Pago.QR)

    @JsonSetter("storePaymentQR")
    fun setStorePaymentQR(habilitado: Boolean) {
        if (habilitado) {
            mediosDePago.add(Pago.QR)
        } else {
            mediosDePago.remove(Pago.QR)
        }
    }

    @JsonGetter("storePaymentTransferencia")
    fun getStorePaymentTransferencia(): Boolean = mediosDePago.contains(Pago.TRANSFERENCIA_BANCARIA)

    @JsonSetter("storePaymentTransferencia")
    fun setStorePaymentTransferencia(habilitado: Boolean) {
        if (habilitado) {
            mediosDePago.add(Pago.TRANSFERENCIA_BANCARIA)
        } else {
            mediosDePago.remove(Pago.TRANSFERENCIA_BANCARIA)
        }
    }

    // Métodos auxiliares para acceder a las propiedades del modelo original
    var porcentajeAcordado: Double
        get() = comisionApp
        set(value) { comisionApp = value }

    var regalias: Double
        get() = comisionAutor
        set(value) { comisionAutor = value }

    fun validar() {
        if (nombre.isEmpty()) {
            throw BusinessException("Debe ingresar el nombre del local")
        }
        if (!url.startsWith("http")) {
            throw BusinessException("URL debe comenzar con http")
        }
        if (comisionAutor !in 0.0..100.0 || comisionApp !in 0.0..100.0) {
            throw BusinessException("La comisión debe ser entre 0% y 100%")
        }
        if (mediosDePago.isEmpty()) {
            throw BusinessException("Seleccione al menos un método de pago")
        }
    }

    fun actualizar(nuevoLocal: Local) {
        nombre = nuevoLocal.nombre
        url = nuevoLocal.url
        direccion = nuevoLocal.direccion
        comisionApp = nuevoLocal.porcentajeAcordado
        comisionAutor = nuevoLocal.regalias
        mediosDePago.clear()
        mediosDePago.addAll(nuevoLocal.mediosDePago)
    }

    override fun cumpleCriterioDeCreacion(): Boolean {
        return nombre.isNotEmpty() && url.startsWith("http")
    }

    override fun cumpleCriterioDeNuevo(): Boolean = id == 0

    override fun cumpleCriterioDeBusqueda(criterio: String): Boolean {
        return nombre.contains(criterio, ignoreCase = true) ||
                direccion.calle.contains(criterio, ignoreCase = true)
    }
}