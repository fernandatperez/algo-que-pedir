package ar.edu.unsam.algo3.modelo.auditor

import ar.edu.unsam.algo3.modelo.pedido.Pedido

// === STRATEGY y NULL OBJECT PATTERN
// Cada strategy redefine a estos metodos:
// - Auditar(pedido) = recibe el pedido, realiza el registro necesario (acumulador o  contador)
// - CumpleObjetivo() = devuelve si se cumple o no el objetivo
// Se separa el registro del auditor
// El strategy lo unico que tiene que saber es auditar y si cumple.
// El NULL OBJECT PATTERN nos sirve para los locales que no quieren auditar. (default)

interface Objetivo{
    fun auditar(pedido: Pedido) // acumulador
    fun cumpleObjetivo(): Boolean // true o false
}

//superar un acumulado de ventas por más de un monto determinado.
class ObjetivoAcumuladoVentas(
    var montoDeterminado: Double,
    var acumuladorVentas: Double = 0.0
) : Objetivo {

    override fun auditar(pedido: Pedido) {
        acumuladorVentas += pedido.totalAPagarConCupon()
    }

    override fun cumpleObjetivo() =
        acumuladorVentas > montoDeterminado
    // Si el acumulado de ventas pasa el montoDeterminado = true
}

//conseguir vender por lo menos 5 veces más de 3 platos por pedido.
class ObjetivoPedidoGrande(
    var contadorDePedidos: Int = 0
): Objetivo {
    override fun auditar(pedido: Pedido) {
        if (pedido.platos.size > 3) {
            contadorDePedidos++
        }
    }

    override fun cumpleObjetivo(): Boolean =
        contadorDePedidos >= 5
    // si vendio al menos 5 pedidos con mas de 3 platos

}

//alcanzar una meta de platos veganos.
class ObjetivoPlatosVeganos(
    var contadorPlatosVeganos: Int = 0,
    var meta: Int
) : Objetivo {
    override fun auditar(pedido: Pedido) {
        contadorPlatosVeganos += pedido.platos.count { it.esVegano()}
    }

    override fun cumpleObjetivo(): Boolean =
        contadorPlatosVeganos >= meta
}

//una combinación de los anteriores, donde TODAS se deben cumplir.
// Composite
class ObjetivoCombinado (
    val objetivos: MutableSet<Objetivo> = mutableSetOf()
) : Objetivo {
    // cambiar nombres
    fun agregarAuditoria(objetivo: Objetivo) {
        objetivos.add(objetivo)
    }

    fun sacarAuditoria(objetivo: Objetivo) {
        objetivos.remove(objetivo)
    }

    override fun auditar(pedido: Pedido) {
        objetivos.forEach{ it.auditar(pedido) }
    }

    override fun cumpleObjetivo(): Boolean =
        objetivos.all { it.cumpleObjetivo() }

}