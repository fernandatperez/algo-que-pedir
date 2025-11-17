package ar.edu.unsam.algo3.modelo.usuario

import ar.edu.unsam.algo3.modelo.pedido.Pedido
import kotlin.random.Random

// Command
interface UsuarioCommands { //todo: Este seria el COMMAND
    fun execute(usuario: Usuario)
}

class EstablecerPedido(val pedido: Pedido) : UsuarioCommands { //todo: Estas subclases serian las ConcreteCommand
    override fun execute(usuario: Usuario) {
        usuario.confirmarPedido(pedido)
    }
}

class Puntuar(
    var puntuarStrategy : CriteriosPuntuacion =  PuntuarFijo(
        Calificacion(
            3,
            "Son todes iguales"
        )
    )
) : UsuarioCommands {

    override fun execute(usuario: Usuario) {
        puntuarStrategy.puntuarLocal(usuario)
    }

    fun cambiarEstrategia(estrategia: CriteriosPuntuacion) { puntuarStrategy = estrategia }
}

// Strategy
interface CriteriosPuntuacion {
    fun puntuarLocal(usuario: Usuario)
}

class PuntuarFijo(var calificacion: Calificacion) : CriteriosPuntuacion {
    override fun puntuarLocal(usuario: Usuario) {
        usuario.localesAPuntuar.keys.forEach { // .keys devuelve solo las claves del mapa, es decir, los Local que están pendientes de puntuación.
            usuario.puntuarLocal(it, calificacion)
        }
    }
}

class PuntuarAleatorio() : CriteriosPuntuacion {
    override fun puntuarLocal(usuario: Usuario) {
        usuario.localesAPuntuar.keys.forEach {
            var calificacion = Calificacion(
                Random.nextInt(1, 6),
                "Descripcion random, no me interesa"
            )
            usuario.puntuarLocal(it, calificacion) // desde el primero a 1 menos que el ultimo (1-5)
        }
    }
}

class PuntuarActual() : CriteriosPuntuacion {
    override fun puntuarLocal(usuario: Usuario) {
        usuario.localesAPuntuar.keys.forEach {
            val puntaje = it.promedioPuntuacion().toInt()
            var calificacion = Calificacion(
                puntaje,
                "Acorde a su promedio...que se yo"
            )
            usuario.puntuarLocal(it, calificacion)
        }
    }
}
