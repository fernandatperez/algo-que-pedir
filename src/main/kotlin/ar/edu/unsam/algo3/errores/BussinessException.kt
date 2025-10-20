package ar.edu.unsam.algo3.errores


import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

class BusinessException(msg: String) : RuntimeException(msg)
class NotFoundException(msg: String) : RuntimeException(msg)
//// La petición no pudo completarse debido a un conflicto con el estado actual del recurso
//// (e.g., intentar crear un registro que ya existe, violando una restricción de unicidad).
class ConflictException(msg: String) : RuntimeException(msg)
class InternalException(msg: String) : RuntimeException(msg)
