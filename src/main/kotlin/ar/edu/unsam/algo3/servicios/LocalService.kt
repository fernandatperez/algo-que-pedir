package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.dto.CalificacionDTO
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.dto.LocalDomDTO
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.toLocalDomDTO
import org.uqbar.geodds.Point
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.repositorio.RepositorioCliente
import ar.edu.unsam.algo3.modelo.usuario.Calificacion
import ar.edu.unsam.algo3.repositorio.RepositorioPedido

@Service
class LocalService(
    private val repositorioLocal: RepositorioLocal,
    private val repositorioUsuario: RepositorioCliente,
    private val repositorioPedidos: RepositorioPedido,
) {
    //aca cambio para que el local que traiga sea el unico que matchee con el mail logueado
    fun get(mail: String): LocalDTO {
        val local = repositorioLocal.findByEmail(mail)
        return local.toDTO()
    }

    fun getByID(id: Int): LocalDTO =
        repositorioLocal.obtenerObjeto(id)?.toDTO() ?: throw NotFoundException("No se encontró el ingrediente de id <$id>")

    fun getByIDReact(id: Int): LocalDomDTO {
        val local = repositorioLocal.obtenerObjeto(id)
        val pedidosDelLocal = repositorioPedidos.getAllOrdersOfLocal(local)
        val cantidadPedidos = pedidosDelLocal.size

        return local.toLocalDomDTO().apply {
            numberOfOrders = cantidadPedidos
        }
    }

    fun getAll(): List<Local> =
        repositorioLocal.objetosDeRepositorio()

//    fun getBySearch(searchName: String?): List<Local> {
//        return if (searchName.isNullOrBlank()) {
//            getAll()
//        } else {
//            repositorioLocal.buscar(searchName)
//        }
//    }

    fun getBySearch(searchName: String?, userId: Int = 0): List<LocalDTO> {
        val resultados = if (searchName.isNullOrBlank()) {
            repositorioLocal.objetosDeRepositorio()
        } else {
            repositorioLocal.buscar(searchName)
        }

        if (userId != 0) {
            val usuarioLogueado = repositorioUsuario.obtenerObjeto(userId)

            return resultados.map { local ->
                val esCercano = usuarioLogueado.esCercano(local)
                local.toDTO(usuarioCercano = esCercano)
            }
        }

        return resultados.map { it.toDTO() }
    }

    fun getStoreRatingsByID(id: Int): List<CalificacionDTO> {
        val local = repositorioLocal.obtenerObjeto(id)
        val reviews: List<Calificacion> = local.obtenerCalificaciones()
        return reviews.map { it.toDTO() }
    }

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