package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.modelo.local.Local


import org.springframework.stereotype.Repository


@Repository
class RepositorioLocal : Repositorio<Local>(){
    //agrego este metodo para poder buscar por email los locales, ya que va a ser la clave
    fun findByEmail(email: String): Local? {
        return coleccion.find { it.email == email }
    }
    //si se necesita se puede agregar mas
    //si se necesita modificar algun elemento
    //buscar entidad, hidratarla, buscar entidades que se utilicen, hidratarlas y despues guardar en bbdd
}