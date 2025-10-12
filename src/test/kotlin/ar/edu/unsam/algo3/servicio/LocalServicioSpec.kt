package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.dominio.Local
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.uqbar.geodds.Point

@SpringBootTest
class LocalServiceIntegrationTest {

    @Autowired
    private lateinit var localService: LocalService

    @Autowired
    private lateinit var repositorioLocal: RepositorioLocal

    @BeforeEach
    fun limpiarRepositorio() {
        repositorioLocal.limpiarColeccion()
    }

    @Test
    fun `crear y obtener perfil funciona correctamente`() {
        // Given - Datos válidos
        val local = Local().apply {
            nombre = "Test Integracion"
            url = "https://integracion.com"
            direccion = Direccion("calle falsa", 999, Point(-34.6, -58.4))
            comisionApp = 10.0
            comisionAutor = 15.0
            mediosDePago = mutableSetOf(Pago.EFECTIVO, Pago.QR)
        }

        // When - Actualizar (crea nuevo)
        val resultadoCreacion = localService.actualizarPerfil(local)

        // Then - Verificar creación
        assertEquals("Test Integracion", resultadoCreacion.nombre)
        assertEquals(1, resultadoCreacion.id)

        // When - Obtener
        val resultadoObtencion = localService.obtenerPerfil()

        // Then - Verificar que es el mismo
        assertEquals("Test Integracion", resultadoObtencion.nombre)
        assertEquals(10.0, resultadoObtencion.comisionApp)
    }

    @Test
    fun `actualizar perfil modifica datos existentes`() {
        // Given - Crear local inicial
        val localInicial = Local().apply {
            nombre = "Inicial"
            url = "https://inicial.com"
            direccion = Direccion("calle falsa", 111, Point(1.0, 1.0))
            comisionApp = 1.0
            comisionAutor = 2.0
            mediosDePago = mutableSetOf(Pago.EFECTIVO)
        }
        localService.actualizarPerfil(localInicial)

        // When - Actualizar con nuevos datos
        val localActualizado = Local().apply {
            nombre = "Actualizado"
            url = "https://actualizado.com"
            direccion = Direccion("calle falsa", 222, Point(1.0, 1.0))
            comisionApp = 3.0
            comisionAutor = 4.0
            mediosDePago = mutableSetOf(Pago.QR)
        }
        val resultado = localService.actualizarPerfil(localActualizado)

        // Then - Verificar cambios
        assertEquals("Actualizado", resultado.nombre)
        assertEquals("https://actualizado.com", resultado.url)
        assertEquals(3.0, resultado.comisionApp)
        assertTrue(resultado.mediosDePago.contains(Pago.QR))
    }

    @Test
    fun `validaciones se ejecutan en actualizarPerfil`() {
        // Given - Datos inválidos
        val localInvalido = Local().apply {
            nombre = "Test"
            url = "url-invalida" // Sin http
            direccion = Direccion("Test", 123, Point(1.0, 1.0))
            comisionApp = 5.0
            comisionAutor = 10.0
            mediosDePago = mutableSetOf(Pago.EFECTIVO)
        }

        // When & Then - Verificar que lanza excepción
        val exception = assertThrows<BusinessException> {
            localService.actualizarPerfil(localInvalido)
        }
        assertTrue(exception.message!!.contains("URL debe comenzar con http"))
    }
}