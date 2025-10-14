package ar.edu.unsam.algo3.dto
import ar.edu.unsam.algo3.dominio.Local
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.modelo.local.Pago
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

// este tambien es un extension method
fun Local.toDTO(): LocalDTO {
    return LocalDTO(
        id = this.id,
        name = this.nombre,
        storeURL = this.url,
        storeAddress = this.direccion.calle,
        storeAltitude = this.direccion.altura,
        storeLatitude = this.direccion.ubicacion.x,
        storeLongitude = this.direccion.ubicacion.y,
        storeAppCommission = this.comisionApp,
        storeAuthorCommission = this.comisionAutor,
        storePaymentEfectivo = this.mediosDePago.contains(Pago.EFECTIVO),
        storePaymentQR = this.mediosDePago.contains(Pago.QR),
        storePaymentTransferencia = this.mediosDePago.contains(Pago.TRANSFERENCIA_BANCARIA)
    )
}

fun LocalDTO.fromDTO(): Local {
    return Local().apply {
        nombre = this@fromDTO.name
        url = this@fromDTO.storeURL
        direccion = Direccion(
            calle = this@fromDTO.storeAddress,
            altura = this@fromDTO.storeAltitude,
            ubicacion = Point(this@fromDTO.storeLatitude, this@fromDTO.storeLongitude)
        )
        comisionApp = this@fromDTO.storeAppCommission
        comisionAutor = this@fromDTO.storeAuthorCommission
        mediosDePago = mutableSetOf<Pago>().apply {
            if (this@fromDTO.storePaymentEfectivo) add(Pago.EFECTIVO)
            if (this@fromDTO.storePaymentQR) add(Pago.QR)
            if (this@fromDTO.storePaymentTransferencia) add(Pago.TRANSFERENCIA_BANCARIA)
        }
    }
}
