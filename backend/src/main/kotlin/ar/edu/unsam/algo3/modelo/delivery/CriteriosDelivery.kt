package ar.edu.unsam.algo3.modelo.delivery
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import java.time.LocalTime


// STATEFUL STRATEGY:
/* En este caso, es preferible tener clases en vez de objetos,
    ya que algunos tienen estado. */
/* Asi tenemos una clase/objeto de acceso global que se puede
    utilizar por el que quiera. */
/* COMPOSITION:
* Cada delivery tiene un TIPO que luego se debe poder cambiar. */

/* INTERFACE para todos los tipos de deliveries que deben
    saber decirnos si pueden entregar un pedido o no */
interface TipoDelivery {
    fun puedeEntregar(pedido: Pedido): Boolean
}

// GENERICO
// Solo se guia por los criterios generales. No hay criterio especial.
object DeliveryGenerico : TipoDelivery {
    override fun puedeEntregar(pedido: Pedido): Boolean = true
}

/* Solo realizan la entrega si el pedido tiene horario de retiro
dentro de un rango horario que ellos consideran seguro. */
// STATEFUL
class DeliverySeguro(
    var horarioComienzoSeguro: LocalTime = LocalTime.of(8, 0),
    var horarioFinalSeguro: LocalTime = LocalTime.of(20, 0),
) : TipoDelivery {

    override fun puedeEntregar(pedido: Pedido) = pedido.horarioEntrega in horarioComienzoSeguro..horarioFinalSeguro
}

// Solo entregan si el pedido es mayor a $ 30.000.
// STATELESS
object DeliveryCaro : TipoDelivery {
    override fun puedeEntregar(pedido: Pedido) = pedido.totalAPagar() >= 30000
}

// Están dispuestos a trabajar con determinados locales.
// STATEFUL
class DeliveryLocales : TipoDelivery {
    val localesPreferidos: MutableSet<Local> = mutableSetOf()

    fun agregarLocalPreferido(local: Local) {
        localesPreferidos.add(local)
    }

    override fun puedeEntregar(pedido: Pedido) = localesPreferidos.contains(pedido.local)
}

// Quieren pedidos certificados.
// STATELESS
object DeliveryCertificado : TipoDelivery {
    override fun puedeEntregar(pedido: Pedido) = pedido.esCertificado()
}

// Combinados
// Composite
abstract class DeliveryCombinado : TipoDelivery {
    val criterios: MutableSet<TipoDelivery> = mutableSetOf()

    fun agregarCriterio(criterio: TipoDelivery) {
        criterios.add(criterio)
    }

    fun eliminarCriterio(criterio: TipoDelivery) {
        criterios.remove(criterio)
    }

    abstract override fun puedeEntregar(pedido: Pedido): Boolean
}

class DeliveryCombinadoAnd : DeliveryCombinado() {
    override fun puedeEntregar(pedido: Pedido): Boolean =
        criterios.all { it.puedeEntregar(pedido) } // si criterios esta vacia => true
}

class DeliveryCombinadoOr : DeliveryCombinado() {
    override fun puedeEntregar(pedido: Pedido): Boolean =
        criterios.any { it.puedeEntregar(pedido) } || criterios.isEmpty()
    // si criterios esta vacia => false, por eso pongo el "||(or) .isEmpty()"
}
