package ar.edu.unsam.algo3.servicios

import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.dto.toDTO
import org.uqbar.geodds.Point
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.errores.BusinessException


// LocalService.kt
@Service
class LocalService(
    private val repositorioLocal: RepositorioLocal  // ← Inyecta el repositorio específico
) {

    fun getAll(): List<LocalDTO> =
            repositorioLocal.objetosDeRepositorio().map { it.toDTO() }
    fun update(localDTO: LocalDTO) {
        //estos cambios son por quitar el fromDTO, que en vez de actualizar el objeto
        //lo pisaba con un objeto nuevo
        // busca el local existente por email

        val localActualizado = repositorioLocal.findByEmail(localDTO.email)
            ?: throw BusinessException("No se encontró local con email: $localDTO.email")

        // actualiza todo menos el mail
        localActualizado.apply {
            nombre = localDTO.name
            url = localDTO.storeURL
            direccion = Direccion(
                calle = localDTO.storeAddress,
                altura = localDTO.storeAltitude,
                ubicacion = Point(localDTO.storeLatitude, localDTO.storeLongitude)
            )
            regalias = localDTO.storeAppCommission
            porcentajeAcordado = localDTO.storeAuthorCommission
            mediosDePago = mutableSetOf<Pago>().apply {
                if (localDTO.storePaymentEfectivo) add(Pago.EFECTIVO)
                if (localDTO.storePaymentQR) add(Pago.QR)
                if (localDTO.storePaymentTransferencia) add(Pago.TRANSFERENCIA_BANCARIA)
            }
        }

        localActualizado.cumpleCriterioDeCreacion()

        repositorioLocal.actualizar(localActualizado)
    }

}