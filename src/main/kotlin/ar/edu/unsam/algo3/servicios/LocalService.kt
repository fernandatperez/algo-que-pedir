package ar.edu.unsam.algo3.servicios

import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.dto.toDTO
import org.uqbar.geodds.Point
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local


@Service
class LocalService(
    private val repositorioLocal: RepositorioLocal  // ← Inyecta el repositorio específico
) {
    //aca cambio para que el local que traiga sea el unico que matchee con el mail logueado
    fun get(mail: String): LocalDTO {
        val local = repositorioLocal.findByEmail(mail)
        return local.toDTO()
    }


    fun getAll(): List<Local> =
        repositorioLocal.objetosDeRepositorio()

    fun update(localDTO: LocalDTO) {
        val email = localDTO.email ?: throw BusinessException("Debe estar logueado para realizar cambios en el perfil")
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