package ar.edu.unsam.algo3.servicios


import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.dto.LocalDTO
import ar.edu.unsam.algo3.modelo.local.Local
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class LocalServiceIntegrationTest {

    @Autowired
    private lateinit var localService: LocalService

    @Autowired
    private lateinit var repositorioLocal: RepositorioLocal

    @BeforeEach
    fun setUp() {
        repositorioLocal.limpiarColeccion() // ← Limpiar antes de cada test
    }

    @Test
    fun `getLocal deberia retornar todos los locales`() {
        val local1 = repositorioLocal.crear(Local(nombre = "nuevo local", email = "nuevo@gmail.com", password = "nuevo"))
        val local2 = repositorioLocal.crear(Local(nombre = "nuevo local2", email = "nuevo1@gmail.com", password = "nuevo"))

        val result = localService.getLocal()

        assertEquals(2, result.size)
        assertEquals("nuevo local", result[0].name)
        assertEquals("nuevo local2", result[1].name)
    }

    @Test
    fun `updateLocal deberia actualizar local existente`() {
        val email = "local@gmail.com"

        val localOriginal = Local(
            nombre = "local original",
            email = email,
            password = "local@gmail.com"
        )
        repositorioLocal.crear(localOriginal)

        val localDTO = LocalDTO(
            name = "local actualizado",
            email = email,  // mismo email
            storeURL = "https://etc",
            storeAuthorCommission = 1.0,
            id = 1,
            storeAddress = "asd",
            storeLongitude = 1.0,
            storeAltitude = 1,
            storeLatitude = 1.0,
            storePaymentQR = false,
            storeAppCommission = 1.0,
            storePaymentEfectivo = false,
            storePaymentTransferencia = true
        )

        localService.updateLocal(email = email, localDTO = localDTO)

        val localActualizado = repositorioLocal.findByEmail(email)
        assertEquals("local actualizado", localActualizado!!.nombre)
        assertEquals(email, localActualizado.email) // email se mantiene igual
    }

    @Test
    fun `updateLocal deberia lanzar excepcion cuando local no existe`() {
        val localDTO = LocalDTO(
            name = "local actualizado",
            email = "jorge@gmail.com",
            storeURL = "https://etc",
            storeAuthorCommission = 1.0,
            id = 1,
            storeAddress = "asd",
            storeLongitude = 1.0,
            storeAltitude = 1,
            storeLatitude = 1.0,
            storePaymentQR = false,
            storeAppCommission = 1.0,
            storePaymentEfectivo = false,
            storePaymentTransferencia = true
        )
        val emailInexistente = "pepito@hotmail.com"

        assertThrows<Exception> {
            localService.updateLocal(email = emailInexistente, localDTO)
        }
    }
}