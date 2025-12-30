package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.usuario.Calificacion
import ar.edu.unsam.algo3.modelo.utils.redondear

data class SearchRequest(
    val searchName: String? = null,
    val userId: String? = null,
) {
    constructor() : this(null)
}

data class LocalDTO(
    val id: Int? = null,
    val name: String = "",
    val email: String? = null,
    val storeURL: String = "",
    val storeAddress: String = "",
    val storeAltitude: Int = 123,
    val storeLatitude: Double = 0.0,
    val storeLongitude: Double = 0.0,
    val storeAppCommission: Double = 0.0,
    val storeAuthorCommission: Double  = 0.0,
    val storePaymentEfectivo: Boolean = true,
    val storePaymentQR: Boolean = true,
    val storePaymentTransferencia: Boolean  = true,
    val usuarioCercano: Boolean = false,
)

fun Local.toDTO(usuarioCercano: Boolean = false): LocalDTO {
    return LocalDTO(
        id = this.id,
        name = this.nombre,
        email = this.email,
        storeURL = this.url,
        storeAddress = this.direccion.calle,
        storeAltitude = this.direccion.altura,
        storeLatitude = this.direccion.ubicacion.x,
        storeLongitude = this.direccion.ubicacion.y,
        storeAppCommission = this.regalias,
        storeAuthorCommission = this.porcentajeAcordado,
        storePaymentEfectivo = this.mediosDePago.contains(Pago.EFECTIVO),
        storePaymentQR = this.mediosDePago.contains(Pago.QR),
        storePaymentTransferencia = this.mediosDePago.contains(Pago.TRANSFERENCIA_BANCARIA),
        usuarioCercano = usuarioCercano
    )
}

data class LocalDomDTO(
    val id: Int,
    val name: String,
    val storeURL: String,
    val deliveryTimeAvg: String,
    val deliveryFee: Double,
    var numberOfOrders: Int,
    val paymentTypes: Set<Pago>,
    val reviews: List<CalificacionDTO>,
    var userDistance: Double,
)

fun Local.toLocalDomDTO(): LocalDomDTO {
    return LocalDomDTO(
        id = this.id,
        name = this.nombre,
        storeURL = this.url,
        deliveryTimeAvg = this.promedioTiempoEntrega(),
        deliveryFee = this.deliveryFee(),
        numberOfOrders = 0, // Se asigna despues en el getByReact
        paymentTypes = this.mediosDePago,
        reviews = this.calificaciones.map { it.toDTO() },
        userDistance = 0.0 // Se asigna despues en el getByReact
    )
}