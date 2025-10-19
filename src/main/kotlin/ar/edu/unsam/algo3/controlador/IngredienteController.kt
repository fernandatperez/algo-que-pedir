package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.IngredienteDTO
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
    fun ingredientes() = ingredientesService.ingredientes()

    @GetMapping("/ingrediente/{id}")
    fun ingredientePorId(@PathVariable id: Int) = ingredientesService.ingredientePorId(id)

    @PostMapping("/crear-ingrediente")
    fun crearIngrediente(@RequestBody ingredienteDTO: IngredienteDTO) =
        ingredientesService.crearIngrediente(ingredienteDTO)

    @PutMapping("/actualizar-ingrediente/{id}")
    fun actualizarIngrediente(@RequestBody ingredienteDTO: IngredienteDTO) =
        ingredientesService.actualizarIngrediente(ingredienteDTO)

    @DeleteMapping("/eliminar-ingrediente/{id}")
    fun eliminarIngrediente(@PathVariable id: Int) =
        ingredientesService.eliminarIngrediente(id)
}