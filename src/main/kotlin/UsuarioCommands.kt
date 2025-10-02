package ar.edu.unsam.algo2.algoQuePedir

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
    var puntuarStrategy : CriteriosPuntuacion =  PuntuarFijo(3)
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

class PuntuarFijo(var puntaje: Int) : CriteriosPuntuacion {
    override fun puntuarLocal(usuario: Usuario) {
        usuario.localesAPuntuar.keys.forEach { // .keys devuelve solo las claves del mapa, es decir, los Local que están pendientes de puntuación.
            usuario.puntuarLocal(it, puntaje)
        }
    }
}

class PuntuarAleatorio() : CriteriosPuntuacion {
    override fun puntuarLocal(usuario: Usuario) {
        usuario.localesAPuntuar.keys.forEach {
            usuario.puntuarLocal(it, Random.nextInt(1, 6)) // desde el primero a 1 menos que el ultimo (1-5)
        }
    }
}

class PuntuarActual() : CriteriosPuntuacion {
    override fun puntuarLocal(usuario: Usuario) {
        usuario.localesAPuntuar.keys.forEach {
            val puntaje = it.promedioPuntuacion().toInt()
            usuario.puntuarLocal(it, puntaje)
        }
    }
}
