package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.repositorio.RepositorioIngredientes
import ar.edu.unsam.algo3.repositorio.Repositorio
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service

@Service
class IngredientesBootstrap(
    val repositorioIngredientes: RepositorioIngredientes
) : InitializingBean {

//    No llama a esto porque llama directamente a PedidosBootstrap
    fun crearIngredientes() {
        repositorioIngredientes.limpiarColeccion()
        val harina = Ingrediente(
            nombre = "Harina de Trigo",
            costoMercado = 150.0,
            esOrigenAnimal = false,
            grupoAlimenticio = GrupoAlimenticio.CEREALES_Y_TUBERCULOS
        )
        val huevo = Ingrediente(
            nombre = "Huevo de Gallina",
            costoMercado = 50.0,
            esOrigenAnimal = true,
            grupoAlimenticio = GrupoAlimenticio.PROTEINAS
        )
        repositorioIngredientes.apply{
            this.crear(harina)
            this.crear(huevo)
        }
    }
    override fun afterPropertiesSet() {
        this.crearIngredientes()
    }
}
