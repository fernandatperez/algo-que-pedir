package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.DTO.IngredienteDTO
import ar.edu.unsam.algo3.DTO.toDTO
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import org.springframework.stereotype.Service
import ar.edu.unsam.algo3.repositorio.RepositorioIngredientes

@Service
class IngredienteService(
    val repositorioIngredientes: RepositorioIngredientes
) {
    fun ingredientes(): List<IngredienteDTO> {
        return repositorioIngredientes.objetosDeRepositorio().map { it.toDTO() }
    }

    fun ingredientePorId(id: Int): IngredienteDTO {
        return repositorioIngredientes.obtenerObjeto(id).toDTO()
    }

    fun crearIngrediente(dto: IngredienteDTO): IngredienteDTO {
        val nuevo = repositorioIngredientes.crear(
            name = dto.name,
            cost = dto.cost,
            esOrigenAnimal = dto.esOrigenAnimal,
            foodGroup = dto.foodGroup
        )
        return nuevo.toDTO()
    }

    fun actualizarIngrediente(id: Int, dto: IngredienteDTO): IngredienteDTO {
        val actualizado = Ingrediente(
            nombre = dto.name,
            costoMercado = dto.cost,
            esOrigenAnimal = dto.esOrigenAnimal,
            grupoAlimenticio = dto.foodGroup
        ).apply { this.id = id }

        repositorioIngredientes.actualizar(actualizado)
        return actualizado.toDTO()
    }

    fun eliminarIngrediente(id: Int) {
        repositorioIngredientes.eliminarDeColeccion(id)
    }
}

