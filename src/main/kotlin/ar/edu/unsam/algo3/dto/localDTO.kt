package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.utils.redondear

data class SearchRequest(
    val searchName: String? = null,
    val userId: String? = null,
) {
    constructor() : this(null)
}

data class LocalDTO(
    val id: Int? = null,
    val name: String,
    val email: String? = null,
    val storeURL: String,
    val storeAddress: String,
    val storeAltitude: Int,
    val storeLatitude: Double,
    val storeLongitude: Double,
    val storeAppCommission: Double,
    val storeAuthorCommission: Double,
    val storePaymentEfectivo: Boolean,
    val storePaymentQR: Boolean,
    val storePaymentTransferencia: Boolean,
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

data class LocalCardDTO(
    val id: Int,
    val name: String,
    val imageURL: String,
    val gradePointAvg: Double,
    val deliveryTimeAvg: String,
    val isExpensive: Boolean
)

fun Local.toCardDTO(): LocalCardDTO {
    return LocalCardDTO(
        id = this.id,
        name = this.nombre,
        imageURL = this.url,
        gradePointAvg = this.promedioPuntuacion().redondear(1),
        deliveryTimeAvg = this.promedioTiempoEntrega(),
        isExpensive = this.esCostoso()
    )
}


