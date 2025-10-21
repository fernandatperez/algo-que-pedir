package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.PlatoDTOUpdate
import ar.edu.unsam.algo3.dto.PlatoMenuDTO
import ar.edu.unsam.algo3.dto.toDTOUpdate
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

    @GetMapping("/platos/{id}")
    fun get(@PathVariable id: Int): PlatoDTOUpdate {
//        Obtener informacion de un plato especifico
        return platoService.obtenerPlato(id).toDTOUpdate()
    }

    @PostMapping("/platos") // Maxi
    fun create(@RequestBody objeto: PlatoDTOUpdate, @RequestParam mail: String): PlatoDTOUpdate {
        return platoService.crearPlato(objeto, mail).toDTOUpdate()
    }

    @PutMapping("/platos/{id}")
    fun update(
//        @PathVariable id: Int,
        @RequestBody platoAModificar: PlatoDTOUpdate,
        @RequestParam mail: String  // AGREGADO
    ): PlatoDTOUpdate {
        platoService.modificarPlato(platoAModificar, mail).toDTOUpdate()
        return platoService.obtenerPlato(platoAModificar.id).toDTOUpdate()
    }
    // Editar un plato existente

}