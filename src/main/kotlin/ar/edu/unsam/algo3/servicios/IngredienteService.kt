package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.DTO.IngredienteDTO
import ar.edu.unsam.algo3.DTO.toDTO
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.errores.*
import ar.edu.unsam.algo3.repositorio.RepositorioIngredientes

@Service
class IngredienteService(
    val repositorioIngredientes: RepositorioIngredientes
) {
    fun ingredientes(): List<IngredienteDTO> {
        return repositorioIngredientes.objetosDeRepositorio().map { it.toDTO() }
    }

    // Este método ahora devuelve un solo DTO
    fun ingredientePorId(id: Int): IngredienteDTO {
        return repositorioIngredientes.obtenerObjeto(id).toDTO()
    }
}

