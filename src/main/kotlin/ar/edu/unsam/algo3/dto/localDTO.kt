package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.local.Local

data class LocalDTO(
    val id: Int,
    val name: String,
    val email: String,
    val storeURL: String,
    val storeAddress: String,
    val storeAltitude: Int,
    val storeLatitude: Double,
    val storeLongitude: Double,
    val storeAppCommission: Double,
    val storeAuthorCommission: Double,
    val storePaymentEfectivo: Boolean,
    val storePaymentQR: Boolean,
    val storePaymentTransferencia: Boolean
)

fun Local.toDTO(): LocalDTO {
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
        storePaymentTransferencia = this.mediosDePago.contains(Pago.TRANSFERENCIA_BANCARIA)
    )
}


