package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.dto.LocalCardDTO
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.dto.LocalDetailDTO
import ar.edu.unsam.algo3.dto.toCardDTO
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.toDetailDTO
import org.uqbar.geodds.Point
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.repositorio.RepositorioPedido
import ar.edu.unsam.algo3.repositorio.RepositorioPlato


@Service
class LocalService(
    private val repositorioLocal: RepositorioLocal,  // ← Inyecta el repositorio específico
    private val repositorioPedidos: RepositorioPedido,
) {
    //aca cambio para que el local que traiga sea el unico que matchee con el mail logueado
    fun get(mail: String): LocalDTO {
        val local = repositorioLocal.findByEmail(mail)
        return local.toDTO()
    }

    fun getByID(id: Int): LocalDTO =
        repositorioLocal.obtenerObjeto(id)?.toDTO() ?: throw NotFoundException("No se encontró el ingrediente de id <$id>")

    fun getByIDReact(id: Int): LocalDetailDTO {
        val local = repositorioLocal.obtenerObjeto(id)
        val pedidosDeLocal = repositorioPedidos.getAllOrdersOfLocal(local)
        val AmountOfOrders = pedidosDeLocal.size

        val DTO = repositorioLocal.obtenerObjeto(id)?.toDetailDTO() ?: throw NotFoundException("No se encontró el ingrediente de id <$id>")

        DTO.numberOfOrders = AmountOfOrders

        return DTO
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

    fun getBySearch(searchName: String?): List<Local> {
        val resultados = if (searchName.isNullOrBlank()) {
            repositorioLocal.objetosDeRepositorio()
        } else {
            repositorioLocal.buscar(searchName)
        }
        return resultados
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