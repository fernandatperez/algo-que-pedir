package ar.edu.unsam.algo2.algoQuePedir

// Para que un tipo generico pueda ser mas de una sola cosa
// class Repositorio<Type> where Type : UnaInterfaz, Type : OtraInterfaz...
// Generics (Type: ...)
class Repositorio<Type : ElementoDeRepositorio> {
    var contadorIDs: Int = 1
    val coleccion: MutableList<Type> = mutableListOf()

    fun generarID(): Int = contadorIDs++

    fun objetosDeRepositorio() = this.coleccion

    fun crear(objeto: Type) {
        if (objeto.cumpleCriterioDeCreacion()) {
            // Esto tiene sentido que sean 2 llamadas, xq que este bien creado o sea nuevo en el repo no van de la mano me parece.
            if (objeto.cumpleCriterioDeNuevo()) objeto.id = generarID()
            coleccion.add(objeto)
        } else throw ObjetoIDoVerificacionFallaron("El ID o el objeto ya se encuentran en la coleccion, o bien la verificacion fallo")
    }

    fun eliminarDeColeccion(id: Int) = coleccion.remove(obtenerObjeto(id))

    fun actualizar(objetoActualizado: Type) {
        // si lo encuentra, elimina el que estaba antes y agrega el nuevo
        eliminarDeColeccion(obtenerObjeto(objetoActualizado.id).id) // Esto puede parecer un toque raro pero tiene sentido, es el id del objeto que devuelve obtenerObjeto con el id del objetoActualizado
        coleccion.add(objetoActualizado)
    }

    fun objetoEnColeccion(id: Int) = coleccion.any { item -> item.id == id }

    fun obtenerObjeto(id: Int): Type {
        if (objetoEnColeccion(id)) {
            return coleccion.find { item -> item.id == id }!! // '!!' Asegura que no va a ser null
        } else { throw IdInexistente("El ID = $id no existe en la coleccion o no hay ningun objeto asociado a el") } // Se evalua tdo aca, asi que no hace falta tirar mas excepciones
    }

    fun limpiarColeccion() = coleccion.clear()

    fun buscar(criterio: String): List<Type> = coleccion.filter { item -> item.cumpleCriterioDeBusqueda(criterio) }
}