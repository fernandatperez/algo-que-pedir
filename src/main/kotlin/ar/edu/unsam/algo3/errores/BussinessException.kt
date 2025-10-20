package ar.edu.unsam.algo3.errores


import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException
//aca tiene que ir un handler, capturo la exception del back y la paso a una exception de http
//ej. ver si en vez de tirar un not found especial o uno general --- ver
//log.info , log.error -- se tira por consola para registrar la cancelacion --no es de algo 3

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
class BusinessException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.NOT_FOUND) // 404
class NotFoundException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.CONFLICT) // 409
// La petición no pudo completarse debido a un conflicto con el estado actual del recurso
// (e.g., intentar crear un registro que ya existe, violando una restricción de unicidad).
class ConflictException(msg: String) : RuntimeException(msg)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 500
class InternalException(msg: String) : RuntimeException(msg)
