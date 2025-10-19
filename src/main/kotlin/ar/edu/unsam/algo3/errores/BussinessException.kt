package ar.edu.unsam.algo3.errores


import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException
//aca tiene que ir un handler, capturo la exception del back y la paso a una exception de http
//ej. ver si en vez de tirar un not found especial o uno general --- ver
//log.info , log.error -- se tira por consola para registrar la cancelacion --no es de algo 3
@ResponseStatus(HttpStatus.BAD_REQUEST)
class BusinessException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(msg: String) : RuntimeException(msg)
