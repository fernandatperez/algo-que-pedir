package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.PlatoDTO
import ar.edu.unsam.algo3.dto.PlatoMenuDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.dto.toPlatoMenuDTO
import ar.edu.unsam.algo3.servicios.PlatoService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*") // Habilita comunicacion entre distintos puertos (acepta requests de cualquier parte)
@RestController
class PlatoController(val platoService: PlatoService) {
//  Inyeccion de dependencias de los singletons de servicios (y servicio conoce repo)

    @GetMapping("/platos")
    fun get(@RequestParam mail: String): List<PlatoMenuDTO> {
        val platos = platoService.getPlatos(mail)
        return platos.map { it.toPlatoMenuDTO() }
    }

    @GetMapping("/platos-react/{id}")
    fun getReact(@PathVariable id: Int, @RequestParam userId: Int): List<PlatoMenuDTO> {
        val platos = platoService.getPlatosByLocalId(id, userId)
        return platos.map { it.toPlatoMenuDTO() }
    }

    @GetMapping("/platos/{id}")
    fun get(@PathVariable id: Int): PlatoDTO {
//        Obtener informacion de un plato especifico
        return platoService.obtenerPlato(id).toDTO()
    }

    @PostMapping("/platos")
    fun create(@RequestBody objeto: PlatoDTO, @RequestParam mail: String): PlatoDTO {
        return platoService.crearPlato(objeto, mail).toDTO()
    }

    @PutMapping("/platos/{id}")
    fun update(
        @RequestBody platoAModificar: PlatoDTO,
        @RequestParam mail: String  // AGREGADO
    ): PlatoDTO {
        platoService.modificarPlato(platoAModificar, mail).toDTO()
        return platoService.obtenerPlato(platoAModificar.id).toDTO()
    }
}