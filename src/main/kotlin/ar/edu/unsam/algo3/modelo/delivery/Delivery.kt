package ar.edu.unsam.algo3.modelo.delivery

import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.errores.YaEstaEnLaListaException
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.repositorio.ElementoDeRepositorio
import org.uqbar.geodds.Point
import org.uqbar.geodds.Polygon

/* Tienen nombre, un username, password, tipo y zonaDeTrabajo.
Estos, para aceptar entregar un pedido, necesitan que:
 - el pedido esté preparado
 - el retiro y la entrega estén dentro de la zona de trabajo,
 la misma está delimitada por un polígono, cada delivery determina
 su propia zona de trabajo.
*/

class Delivery(
    var name: String = "",
    var username: String = "",
    var password: String = "",
    var tipo: TipoDelivery = DeliveryGenerico,
    var zonaDeTrabajo: Polygon,
) : ElementoDeRepositorio {
    init {
        // Chequear si la zona de trabajo esta bien definida en la inicializacion.
        if (zonaDeTrabajo.surface.size < 3) {
            throw RuntimeException("Zona de trabajo debe tener al menos 3 puntos")
        }
    }

    fun agregarPuntoDeZona(punto: Point) {
        if (zonaDeTrabajo.surface.contains(punto)) {
            throw YaEstaEnLaListaException("El punto ya fue registrado en la zona.")
        }
        zonaDeTrabajo.add(punto)
    }

    fun puedeEntregar(pedido: Pedido) = pedido.estaEnEstado(Estado.PREPARADO)
            && tipo.puedeEntregar(pedido)
            && (estaEnZona(pedido.direccionRetiro()) && estaEnZona(pedido.direccionEntrega()))

    private fun estaEnZona(direccion: Direccion) = zonaDeTrabajo.isInside(direccion.ubicacion)

    //    ========== Metodos de busqueda del repositorio ==============

    override var id = 0

    fun coincideConElPrincipio(criterio: String, comparaCon: String) =
        comparaCon.lowercase().startsWith(criterio.lowercase())

    override fun cumpleCriterioDeBusqueda(criterio: String): Boolean = coincideConElPrincipio(criterio, username)

    override fun cumpleCriterioDeCreacion() {
        if(!noEstaVacio(name)) throw NotFoundException("El Delivery tiene que tener un nombre")
    }
}