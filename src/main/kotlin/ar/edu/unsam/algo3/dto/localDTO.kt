package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.utils.redondear

data class SearchRequest(
    val searchName: String? = null
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

data class LocalDetailDTO(
    val id: Int,
    val name: String,
    val imageURL: String,
    val gradePointAvg: Double,
    val numberOfReviews: Int,
    var numberOfOrders: Int,
)

fun Local.toDetailDTO(): LocalDetailDTO {
    return LocalDetailDTO(
        id = this.id,
        name = this.nombre,
        imageURL = this.url,
        gradePointAvg = this.promedioPuntuacion().redondear(1),
        numberOfReviews = this.cantidadDePuntuaciones(),
        numberOfOrders = 2,
    )
}


