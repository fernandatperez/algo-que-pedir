package ar.edu.unsam.algo2.algoQuePedir

import java.time.LocalDate
import java.time.temporal.ChronoUnit

class Usuario(
    val nombre: String = "",
    val apellido: String = "",
    val username: String = "",
    val password: String = "",
    val fechaNacimiento: LocalDate = LocalDate.of(2000, 1, 1),
    val ingredientesPreferidos: MutableSet<Ingrediente> = mutableSetOf(),
    val ingredientesEvitar: MutableSet<Ingrediente> = mutableSetOf(),
    val direccion: Direccion = Direccion(),
    val distanciaMaxima: Double = 5.0, // km
    val fechaDeRegistro: LocalDate = LocalDate.of(2023, 1, 1), //agrego porque se usa en pedido.kt
    var tipoDeCliente: CriterioCliente = Generalista, // acepta todo
    val mailPrincipal: String = ""
) : ElementoDeRepositorio {

    fun edad() = fechaNacimiento.aniosHastaAhora()
    fun esIngredientePreferido(ingrediente: Ingrediente) = ingredientesPreferidos.contains(ingrediente)
    fun esIngredienteAEvitar(ingrediente: Ingrediente) = ingredientesEvitar.contains(ingrediente)

    fun esAntiguo() = this.tiempoRegistrado() > 1

    fun agregarPreferido(ingrediente: Ingrediente) { // el tipo es la clase Ingrediente
        if (esIngredienteAEvitar(ingrediente)) {
            throw PerteneceAotraListaException("${ingrediente.nombre} ya es un ingrediente a evitar")
        } else {
            ingredientesPreferidos.add(ingrediente)
        }
    }

    fun agregarEvitar(ingrediente: Ingrediente) {
        if (esIngredientePreferido(ingrediente)) {
            throw PerteneceAotraListaException("${ingrediente.nombre} ya es un ingrediente preferido")
        } else {
            ingredientesEvitar.add(ingrediente)
        }
    }

    fun eliminarPreferido(ingrediente: Ingrediente) {
        ingredientesPreferidos.remove(ingrediente)
    }

    fun eliminarEvitar(ingrediente: Ingrediente) {
        ingredientesEvitar.remove(ingrediente)
    }

    // local es del tipo clase Local
    fun esCercano(local: Local): Boolean = this.direccion.esCercano(
        local.direccion,
        distanciaMaxima
    ) // Esto devuelve un Double que representa la distancia

    fun confirmarPedido(pedido: Pedido) {
        if (!this.validarPlatosDePedido(pedido)) {
            throw RuntimeException("Hay por lo menos un plato que no puede pedir")
        }
        pedido.validarUsuarioDePedido(this)
        pedido.dispararObservers()
    }

    fun validarPlatosDePedido(pedido: Pedido) = pedido.platos.all { this.puedePedir(it) }

    val localesAPuntuar: MutableMap<Local, LocalDate> = mutableMapOf()

    // todo: tiene que recibir pedido y este tiene que saber el local y el local lo tiene que haber preparado
    fun registarLocalParaPuntuar(pedido: Pedido) {
        if (pedido.estaEnEstado(Estado.CONFIRMADO)) {
            localesAPuntuar[pedido.local] = LocalDate.now()
        }
    }

    fun puntuarLocal(local: Local, puntaje: Int) {
        if (!sePuedePuntuarLocal(local)) {
            throw RuntimeException("No se puede puntuar el local")
        }
        local.agregarPuntuacion(puntaje)
        localesAPuntuar.remove(local)
    }

    fun sePuedePuntuarLocal(local: Local): Boolean =
        localesAPuntuar.containsKey(local) && ChronoUnit.DAYS.between(localesAPuntuar[local], LocalDate.now()) <= 7

    // lo cambio a funcion para que no quede setteado
    fun tiempoRegistrado() = fechaDeRegistro.aniosHastaAhora()

    fun puedePedir(plato: Plato): Boolean = tipoDeCliente.puedePedir(plato, this)

    var acciones: MutableSet<UsuarioCommands> = mutableSetOf() // hago esto tipo command porque pone "setear algunas acciones" supongo que se puede hacer un strategy para como da puntuaciones

    fun agregarAcciones(accion: UsuarioCommands) {
        acciones.add(accion)
    }

    fun ejecutarAcciones() { // todo: Esto seria el Invoker que "invoca" a los comandos el Receiver seria Local
        acciones.forEach { it.execute(this) }
        acciones.clear()
    }

    //    ========== Metodos de busqueda del repositorio ==============

    override var id = 0

    override fun cumpleCriterioDeBusqueda(criterio: String) =
        coincideParcialmenteCon(criterio, nombre) ||
                coincideParcialmenteCon(criterio, apellido) ||
                coincideTotalmenteCon(criterio, username)

    override fun cumpleCriterioDeCreacion(): Boolean =
        noEstaVacio(nombre) && noEstaVacio(apellido) && noEstaVacio(username)
}

