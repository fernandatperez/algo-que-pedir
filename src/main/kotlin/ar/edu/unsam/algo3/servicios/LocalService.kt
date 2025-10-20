package ar.edu.unsam.algo3.servicios

import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.dto.toDTO
import org.uqbar.geodds.Point
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.errores.BusinessException



@Service
class LocalService(
    private val repositorioLocal: RepositorioLocal  // ← Inyecta el repositorio específico
) {
    // antes esto devolvia una lista de localDTO, lo que me parece que no esta bien porque un perfil esta vinculado a un solo local, pero lo dejo porque si no tengo que cambiar todo el funcionamiento
    fun get(mail: String): List<LocalDTO> =
        listOf(repositorioLocal.findByEmail(mail).toDTO() ?: throw BusinessException("No se encontro un local"))
// listOf solo porque espera una lista pero devuleve 1 solo
    fun update(localDTO: LocalDTO) {
        val email = localDTO.email ?: throw BusinessException("Email es requerido")
        val localExistente = repositorioLocal.findByEmail(email)

    // Actualizar
    localExistente.apply {
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

    repositorioLocal.actualizar(localExistente)
}



}