package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.modelo.local.Local


import org.springframework.stereotype.Repository


@Repository
class RepositorioLocal : Repositorio<Local>(){
    //agrego este metodo para poder buscar por email los locales, ya que va a ser la clave
    fun findByEmail(email: String): Local {
        return coleccion.find { it.email == email } ?: throw Exception("Local no encontrado para email: $email")
    }
}