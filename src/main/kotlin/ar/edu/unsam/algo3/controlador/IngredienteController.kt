package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.ingrediente.ServiceIngredientes
import ar.edu.unsam.algo3.servicios.IngredienteService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
@RequestMapping("/ingredientes")
class IngredienteController(val ingredientesService: IngredienteService) {

    @GetMapping
    fun ingredientes() = ingredientesService.ingredientes()

    @GetMapping("/{id}")
    fun ingredientePorId(@PathVariable id: Int) = ingredientesService.ingredientePorId(id)
}