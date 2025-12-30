package ar.edu.unsam.algo3.modelo.pedido

import ar.edu.unsam.algo3.modelo.usuario.Combinado
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.utils.Mail
import ar.edu.unsam.algo3.modelo.utils.MailSender
import ar.edu.unsam.algo3.modelo.utils.Mensaje
import ar.edu.unsam.algo3.modelo.auditor.Objetivo
import ar.edu.unsam.algo3.modelo.usuario.Vegano
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.repositorio.Repositorio
import java.time.LocalDate

interface PedidoConfirmadoObserver {
    fun pedidoConfirmado(pedido: Pedido)
}

// Si un local tiene un plato que cumple con preferencias de usuario manda mail
class PublicidadMailObserver(
    val repositorioPlatos: Repositorio<Plato>, // El repo conoce todos los platos de la app y el plato conoce a su local
    val mailSender: MailSender
) : PedidoConfirmadoObserver {
    override fun pedidoConfirmado(pedido: Pedido) {
        if (localContienePlatoPreferido(pedido)) {
            val mail = Mail(
                from = "publi@algoquepedir.com",
                to = pedido.usuario.mailPrincipal,
                subject = "Te puede interesar este plato!",
                content = "Basado en tus preferencias no te podes perder: ${platoRecomendado(pedido).nombre}",
            )
            mailSender.sendMail(mail)
        }
    }

    fun localContienePlatoPreferido(pedido: Pedido): Boolean = platosDelLocal(pedido).any { cumpleCondicion(pedido, it) }

    fun platosDelLocal(pedido: Pedido) = repositorioPlatos.buscar(pedido.local.nombre)
    // devuelve una lista de platos pertenecientes al local o lista Vacia

    private fun platoRecomendado(pedido: Pedido): Plato = platosRecomendables(pedido).first()

    private fun platosRecomendables(pedido: Pedido) = platosDelLocal(pedido).filter { cumpleCondicion(pedido, it) }

    private fun cumpleCondicion(pedido: Pedido, plato: Plato) = pedido.usuario.puedePedir(plato) && !pedido.platos.contains(plato)
    // Si el usuario puede pedir cualquier plato perteneciente al local
    // y ese plato NO ESTA en el pedido actual, se recomienda.

}

// Este observer es un objeto global (singleton) que guarda en un mapa los locales y sus auditorias elegidas. [local: strategy]
object AuditoriaObserver : PedidoConfirmadoObserver {
    val mapaDeLocalesDelObserver: MutableMap<Local, Objetivo> = mutableMapOf() // [local: strategy]

    override fun pedidoConfirmado(pedido: Pedido) {
        // Pide el local del pedido
        // Lo busca en su mapa de locales:
        // si está -> Le pide a su estrategy la audiotria [strategy.auditar()]
        // si no está -> No hace nada :)
        val local = pedido.local // va a ser Local
        if(mapaDeLocalesDelObserver.containsKey(local)) {
            mapaDeLocalesDelObserver[local]!!.auditar(pedido)
        }

    }

    fun agregarLocal(local: Local, objetivo: Objetivo) {
        mapaDeLocalesDelObserver[local] = objetivo
    }

    fun cumpleObjetivo(local: Local) = mapaDeLocalesDelObserver[local]?.let { it.cumpleObjetivo()  } ?: throw RuntimeException("El local no es auditado")
    // metdo para preguntar si un local cumple el objetivo. Retorna excepcion si no es auditado
}


// Si el usuario hizo un pedido 100% vegano, hay que cambiar su preferencia a una combinada que tenga su preferencia actual y vegana.
// STATELESS
object VeganoObserver : PedidoConfirmadoObserver {
    override fun pedidoConfirmado(pedido: Pedido) {
        if (pedido.totalmenteVegano()) {
            pedido.usuario.tipoDeCliente = Combinado(mutableSetOf(pedido.usuario.tipoDeCliente, Vegano))
        }
    }
}

// Si el pedido es certificado, queremos enviarle al inbox de mensajes del local, de la app, un mensaje avisando para que traten de darle prioridad.
class CertificadoObserver(
) : PedidoConfirmadoObserver {
    override fun pedidoConfirmado(pedido: Pedido) {
        if (pedido.esCertificado()) {
            val mensaje = Mensaje(
                fechaDeEmision = LocalDate.now(),
                subject = "Aumentar prioridad",
                content = "Traten de darle mas prioridad che",
                leido = false
            )
            pedido.local.recibirMensaje(mensaje)
        }
    }
}

