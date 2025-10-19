package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.*
import ar.edu.unsam.algo3.servicios.LocalService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ar.edu.unsam.algo3.errores.BusinessException
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController
@RequestMapping("/store-profile")
class LocalController(
    private val localService: LocalService
) {

    @GetMapping
    fun getLocal(): List<LocalDTO> {
        return localService.getLocal()
    }

    @PutMapping
    fun updateLocal(
        @RequestBody localDTO: LocalDTO, // ← DTO debe tener campo email
        request: HttpServletRequest
    ): ResponseEntity<Any> {
        try {
            val email = localDTO.email
                ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email requerido")

            println("✅ Actualizando local para: $email")
            localService.updateLocal(email, localDTO)
            return ResponseEntity.ok("Local actualizado correctamente")
        } catch (e: BusinessException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
        }
    }
}