package ar.edu.unsam.algo3.dto
import ar.edu.unsam.algo3.dominio.Local
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.modelo.local.Pago
import org.uqbar.geodds.Point
import com.fasterxml.jackson.annotation.JsonProperty

//este va del front al back
data class StoreProfileRequest(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("storeURL")
    val storeURL: String,

    @JsonProperty("storeAddress")
    val storeAddress: String,

    @JsonProperty("storeAltitude")
    val storeAltitude: Int,

    @JsonProperty("storeLatitude")
    val storeLatitude: Double,

    @JsonProperty("storeLongitude")
    val storeLongitude: Double,

    @JsonProperty("storeAppCommission")
    val storeAppCommission: Double,

    @JsonProperty("storeAuthorCommission")
    val storeAuthorCommission: Double,

    @JsonProperty("storePaymentEfectivo")
    val storePaymentEfectivo: Boolean,

    @JsonProperty("storePaymentQR")
    val storePaymentQR: Boolean,

    @JsonProperty("storePaymentTransferencia")
    val storePaymentTransferencia: Boolean
)

//este va del back al front

data class StoreProfileResponse(
    @JsonProperty("id")
    val id: Int,

    @JsonProperty("name")
    val name: String,

    @JsonProperty("storeURL")
    val storeURL: String,

    @JsonProperty("storeAddress")
    val storeAddress: String,

    @JsonProperty("storeAltitude")
    val storeAltitude: Int,

    @JsonProperty("storeLatitude")
    val storeLatitude: Double,

    @JsonProperty("storeLongitude")
    val storeLongitude: Double,

    @JsonProperty("storeAppCommission")
    val storeAppCommission: Double,

    @JsonProperty("storeAuthorCommission")
    val storeAuthorCommission: Double,

    @JsonProperty("storePaymentEfectivo")
    val storePaymentEfectivo: Boolean,

    @JsonProperty("storePaymentQR")
    val storePaymentQR: Boolean,

    @JsonProperty("storePaymentTransferencia")
    val storePaymentTransferencia: Boolean
)

//lo dejo como extension method
fun StoreProfileRequest.toDomain(): Local {
    return Local().apply {
        nombre = this@toDomain.name
        url = this@toDomain.storeURL
        direccion = Direccion(
            calle = this@toDomain.storeAddress,
            altura = this@toDomain.storeAltitude,
            ubicacion = Point(this@toDomain.storeLatitude, this@toDomain.storeLongitude)
        )
        comisionApp = this@toDomain.storeAppCommission
        comisionAutor = this@toDomain.storeAuthorCommission
        mediosDePago = mutableSetOf<Pago>().apply {
            if (this@toDomain.storePaymentEfectivo) add(Pago.EFECTIVO)
            if (this@toDomain.storePaymentQR) add(Pago.QR)
            if (this@toDomain.storePaymentTransferencia) add(Pago.TRANSFERENCIA_BANCARIA)
        }
    }
}

// este tambien es un extension method
fun Local.toResponse(): StoreProfileResponse {
    return StoreProfileResponse(
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