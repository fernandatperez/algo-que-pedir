package ar.edu.unsam.algo3.repositorio

// Elementos de Repositorio: Plato, Local, Usuario, Ingrediente, Delivery, Cupon

interface ElementoDeRepositorio {
    var id: Int

    fun cumpleCriterioDeBusqueda(criterio: String): Boolean

    fun coincideParcialmenteCon(criterio: String, aCompararCon: String) =
        aCompararCon.contains(criterio, ignoreCase = true)

    fun coincideTotalmenteCon(criterio: String, aCompararCon: String) = aCompararCon.lowercase() == criterio.lowercase()

    fun noEstaVacio(string: String) = string.isNotBlank()

    fun cumpleCriterioDeCreacion()

    fun cumpleCriterioDeNuevo(): Boolean = id == 0
}