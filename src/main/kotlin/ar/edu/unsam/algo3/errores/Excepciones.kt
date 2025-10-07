package ar.edu.unsam.algo3.errores

class PerteneceAotraListaException(message: String) : RuntimeException(message) {}

class DescuentoMayorAlCienException(message: String) : RuntimeException(message) {}

class NoAplicaDescuentoEnPlatoNuevoException(message: String) : RuntimeException(message) {}

class SobrepasoPuntuacion(message: String) : RuntimeException(message) {}

class IngredienteNoEstaEnElPlatoException(message: String) : RuntimeException(message) {}

class IngredienteYaPerteneceAlPlato(message: String) : RuntimeException(message) {}

class YaEstaEnLaListaException(message: String) : RuntimeException(message) {}

class NoEsElMismoUsuario(message: String) : RuntimeException(message) {}

class MedioDePagoNoPermitido(message: String) : RuntimeException(message) {}

class ElCuponYaFueAplicado(message: String) : RuntimeException(message) {}

class IdInexistente(message: String) : RuntimeException(message) {}

class NoExisteNombreEnColeccion(message: String) : RuntimeException(message) {}

class ObjetoIDoVerificacionFallaron(message: String) : RuntimeException(message) {}

class CuponNoAplicable(message: String) : RuntimeException(message) {}

class ObjetoNoEstaEnLaLista(message: String) : RuntimeException(message) {}

class JSONVacioException(message: String) : RuntimeException(message) {}

class PlatoNoEsDeLocalException(message: String) : RuntimeException(message) {}

class SinPlatoPreferidoException(message: String) : RuntimeException(message) {}
