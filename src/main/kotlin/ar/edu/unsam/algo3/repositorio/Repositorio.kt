package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.errores.IdInexistente
import ar.edu.unsam.algo3.errores.ObjetoIDoVerificacionFallaron
import ar.edu.unsam.algo3.errores.BusinessException

// Para que un tipo generico pueda ser mas de una sola cosa
// class Repositorio<Type> where Type : UnaInterfaz, Type : OtraInterfaz...
// Generics (Type: ...)
import org.springframework.stereotype.Repository


open class Repositorio<Type : ElementoDeRepositorio> {
    var contadorIDs: Int = 1
    val coleccion: MutableList<Type> = mutableListOf()
    fun generarID(): Int = contadorIDs++

    fun objetosDeRepositorio() = this.coleccion

    fun crear(objeto: Type) {
//        if (objeto.cumpleCriterioDeCreacion()) {
            // Esto tiene sentido que sean 2 llamadas, xq que este bien creado o sea nuevo en el repo no van de la mano me parece.
            if (objeto.cumpleCriterioDeNuevo()) objeto.id = generarID()
            coleccion.add(objeto)
//        } else throw ObjetoIDoVerificacionFallaron("El ID o el objeto ya se encuentran en la coleccion, o bien la verificacion fallo")
    }

//    Poner la validacion aca
    fun eliminarDeColeccion(id: Int) =
        if (id == -1) throw BusinessException("No existe elemento en repositorio")
        else coleccion.remove(obtenerObjeto(id))

    fun actualizar(objetoActualizado: Type) {
        val index = coleccion.indexOfFirst { it.id == objetoActualizado.id }
        if (index == -1) throw BusinessException("No existe elemento en repositorio")
        coleccion[index] = objetoActualizado
    }

    fun objetoEnColeccion(id: Int) = coleccion.any { item -> item.id == id }

    fun obtenerObjeto(id: Int): Type? = coleccion.find { item -> item.id == id }


    fun limpiarColeccion() = coleccion.clear()

    fun buscar(criterio: String): List<Type> = coleccion.filter { item -> item.cumpleCriterioDeBusqueda(criterio) }
}