package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.local.Local
import org.uqbar.geodds.Point
import com.fasterxml.jackson.annotation.JsonProperty

data class LocalDTO(
    val id: Int,
    val name: String,
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

fun LocalDTO.fromDTO(): Local {
    return Local().apply {
        id = this@fromDTO.id
        nombre = this@fromDTO.name
        url = this@fromDTO.storeURL
        direccion = Direccion(
            calle = this@fromDTO.storeAddress,
            altura = this@fromDTO.storeAltitude,
            ubicacion = Point(this@fromDTO.storeLatitude, this@fromDTO.storeLongitude)
        )
        regalias = this@fromDTO.storeAppCommission
        porcentajeAcordado = this@fromDTO.storeAuthorCommission
        mediosDePago = mutableSetOf<Pago>().apply {
            if (this@fromDTO.storePaymentEfectivo) add(Pago.EFECTIVO)
            if (this@fromDTO.storePaymentQR) add(Pago.QR)
            if (this@fromDTO.storePaymentTransferencia) add(Pago.TRANSFERENCIA_BANCARIA)
        }
    }
}