package ar.edu.unsam.algo3.repositorio

import ar.edu.unsam.algo3.dominio.Local
import ar.edu.unsam.algo3.repositorio.Repositorio


import org.springframework.stereotype.Repository


@Repository
class RepositorioLocal : Repositorio<Local>() {
    // devuelve un local
    fun obtenerLocalPorId(id: Int): Local {
        return obtenerObjeto(id) as Local
    }

}