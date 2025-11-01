package ar.edu.unsam.algo3.servicios;

import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.repositorio.RepositorioCliente
import ar.edu.unsam.algo3.repositorio.RepositorioIngrediente
import org.springframework.stereotype.Service;

@Service
class ClienteService(
    val repositorioClientes: RepositorioCliente,
    val repositorioIngredientes: RepositorioIngrediente
) {

    fun obtenerLocalesPuntuables(id: Int): MutableSet<Local> {
        val cliente = repositorioClientes.obtenerObjeto(id)
        val localesPuntuables: MutableSet<Local> = cliente.obtenerLocalesAPuntuar()
        return localesPuntuables
    }

    fun obtenerIngredientesPorCriterio(id: Int, criterio: String): Set<IngredienteDTO> {
        val cliente = repositorioClientes.obtenerObjeto(id)

        if ( criterio == "avoid" ) return cliente.ingredientesEvitar.map { it.toDTO() }.toSet()
        if ( criterio == "prefers" ) return cliente.ingredientesPreferidos.map { it.toDTO() }.toSet()
        else throw NotFoundException("No se encontró el criterio <$criterio>")
    }

    fun obtenerIngredientesDisponibles(id: Int): Set<IngredienteDTO>{
        val cliente = repositorioClientes.obtenerObjeto(id)
        val ingredientes = repositorioIngredientes.objetosDeRepositorio()
        val ingredientesDisponibles = ingredientes.filter { !cliente.esIngredientePreferidoPorID(it.id) && !cliente.esIngredienteAEvitarPorID(it.id)}

        return ingredientesDisponibles.map { it.toDTO() }.toSet()
    }

    fun actualizarIngredientesPorCriterio(id: Int, criterio: String, ingredientes: List<Ingrediente>): Set<IngredienteDTO> {
        val cliente = repositorioClientes.obtenerObjeto(id)

        when (criterio) {
            "avoid" -> ingredientes.forEach {
                cliente.agregarEvitar(it)
            }
            "prefers" -> ingredientes.forEach {
                cliente.agregarPreferido(it)
            }
            else -> throw NotFoundException("No se encontró el criterio <$criterio>")
        }

        repositorioClientes.actualizar(cliente)

        return ingredientes.map { it.toDTO() }.toSet()
    }

}