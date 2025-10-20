package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.toDOM
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.servicios.IngredienteService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
class IngredienteController(val ingredientesService: IngredienteService) {

    @GetMapping("/ingredientes")
    fun ingredientes() =
        ingredientesService.ingredientes().map { it.toDTO() }

    @GetMapping("/ingrediente/{id}")
    fun ingredientePorId(@PathVariable id: Int) =
        ingredientesService.ingredientePorId(id).toDTO()

    @PostMapping("/crear-ingrediente")
    fun crearIngrediente(@RequestBody ingredienteDTO: IngredienteDTO) {
        val ingredienteNuevo = ingredienteDTO.toDOM()
        ingredientesService.crearIngrediente(ingredienteNuevo)
    }

    @PutMapping("/actualizar-ingrediente/{id}")
    fun actualizarIngrediente(@RequestBody ingredienteDTO: IngredienteDTO): IngredienteDTO {
        val ingredienteActualizado = ingredienteDTO.toDOM()
        ingredientesService.actualizarIngrediente(ingredienteActualizado)

        return ingredienteActualizado.toDTO()
    }

    @DeleteMapping("/eliminar-ingrediente/{id}")
    fun eliminarIngrediente(@PathVariable id: Int) =
        ingredientesService.eliminarIngrediente(id)
}