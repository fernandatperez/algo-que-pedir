package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.errores.NotFoundException

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
        if (objeto.cumpleCriterioDeNuevo()) objeto.id = generarID() // esto deja baches. No esta piola (igual queda). Revisar
        coleccion.add(objeto)
    }

    fun eliminarDeColeccion(id: Int) =
        if (id == -1) throw NotFoundException("No existe el id $id en repositorio para eliminarlo de la coleccion")
        else coleccion.remove(obtenerObjeto(id))

    fun actualizar(objetoActualizado: Type) {
        val index = coleccion.indexOfFirst { it.id == objetoActualizado.id }
        if (index == -1) throw NotFoundException("No existe un indice donde exista este elemento en el repositorio. ${objetoActualizado.id}}")
        coleccion[index] = objetoActualizado
    }

    fun objetoEnColeccion(id: Int) = coleccion.any { item -> item.id == id }

    fun obtenerObjeto(id: Int): Type {
        val objeto = coleccion.find { item -> item.id == id }
        if (objeto != null) {
            return objeto
        }
        throw BusinessException("No existe el id $id en repositorio para obtenerlo de la coleccion")
    }


    fun limpiarColeccion() = coleccion.clear()

    fun buscar(criterio: String): List<Type> = coleccion.filter { item -> item.cumpleCriterioDeBusqueda(criterio) }


}