package ar.edu.unsam.algo3.errores

//import org.springframework.core.Ordered
//import org.springframework.core.annotation.Order
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.ControllerAdvice
//import org.springframework.web.bind.annotation.ExceptionHandler
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
//
//@Order(Ordered.HIGHEST_PRECEDENCE)
//@ControllerAdvice
//class RestExceptionHandler : ResponseEntityExceptionHandler() {
//
//    @ExceptionHandler(BusinessException)
//    fun handleBusinessException(e: BusinessException) {
//        ResponseEntity.badRequest().body(e.message)
//    }
//
//    @ExceptionHandler(NotFoundException)
//    fun handleNotFoundException(e: NotFoundException) {
//        ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
//    }
//
//    @ExceptionHandler(Exception)
//    fun handleException(e: Exception) {
//        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.message)
//    }
//}

/*
* TODO:
*   Revisar todo esto, es una buena manera y mas logica de tirar los errores con los codigos HTTP
*            que querramos.
* */