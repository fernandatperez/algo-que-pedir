package ar.edu.unsam.algo3.controlador

//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.ControllerAdvice
//import org.springframework.web.bind.annotation.ExceptionHandler
////esto lo agrego porque hay algunos controller que no tienen manejo de errores, hay que arreglarlos.
//@ControllerAdvice
//class GlobalExceptionHandler {
//
//    @ExceptionHandler(RuntimeException::class)
//    fun handleRuntimeException(ex: RuntimeException): ResponseEntity<Map<String, Any>> {
//        val errorResponse = mapOf(
//            "error" to (ex.message ?: "Error interno del servidor"),
//            "timestamp" to System.currentTimeMillis()
//        )
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
//    }
//}