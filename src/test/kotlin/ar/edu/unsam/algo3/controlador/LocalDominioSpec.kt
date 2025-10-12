//package ar.edu.unsam.algo3.controlador
//
//import ar.edu.unsam.algo3.errores.BusinessException
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.assertThrows
//import org.uqbar.geodds.Point
//
//class LocalTest {
//
//    @Test
//    fun `local se crea correctamente`() {
//        val local = Local().apply {
//            nombre = "Mi Local"
//            url = "https://ejemplo.com"
//            direccion = Direccion("Av. Test", 123, Point(-34.6, -58.4))
//            comisionApp = 3.0
//            comisionAutor = 5.0
//            mediosDePago = mutableSetOf(Pago.EFECTIVO, Pago.QR)
//        }
//
//        assertEquals("Mi Local", local.nombre)
//        assertEquals(3.0, local.comisionApp)
//        assertTrue(local.mediosDePago.contains(Pago.EFECTIVO))
//    }
//
//    @Test
//    fun `validar local con datos correctos no lanza excepcion`() {
//        val local = Local().apply {
//            nombre = "Local Válido"
//            url = "https://valido.com"
//            comisionApp = 10.0
//            comisionAutor = 15.0
//            mediosDePago = mutableSetOf(Pago.EFECTIVO)
//        }
//
//        assertDoesNotThrow { local.validar() }
//    }
//
//    @Test
//    fun `validar local sin nombre lanza excepcion`() {
//        val local = Local().apply {
//            nombre = ""
//            url = "https://test.com"
//            comisionApp = 10.0
//            comisionAutor = 15.0
//            mediosDePago = mutableSetOf(Pago.EFECTIVO)
//        }
//
//        val exception = assertThrows<BusinessException> { local.validar() }
//        assertTrue(exception.message!!.contains("nombre"))
//    }
//
//    @Test
//    fun `validar local con URL invalida lanza excepcion`() {
//        val local = Local().apply {
//            nombre = "Local Test"
//            url = "url-invalida"
//            comisionApp = 10.0
//            comisionAutor = 15.0
//            mediosDePago = mutableSetOf(Pago.EFECTIVO)
//        }
//
//        val exception = assertThrows<BusinessException> { local.validar() }
//        assertTrue(exception.message!!.contains("URL"))
//    }
//
//    @Test
//    fun `validar local con comision mayor a 100 lanza excepcion`() {
//        val local = Local().apply {
//            nombre = "Local Test"
//            url = "https://test.com"
//            comisionApp = 101.0
//            comisionAutor = 15.0
//            mediosDePago = mutableSetOf(Pago.EFECTIVO)
//        }
//
//        val exception = assertThrows<BusinessException> { local.validar() }
//        assertTrue(exception.message!!.contains("comisión"))
//    }
//
//    @Test
//    fun `validar local sin medios de pago lanza excepcion`() {
//        val local = Local().apply {
//            nombre = "Local Test"
//            url = "https://test.com"
//            comisionApp = 10.0
//            comisionAutor = 15.0
//            mediosDePago = mutableSetOf()
//        }
//
//        val exception = assertThrows<BusinessException> { local.validar() }
//        assertTrue(exception.message!!.contains("método de pago"))
//    }
//
//    @Test
//    fun `actualizar local modifica todas las propiedades`() {
//        val localOriginal = Local().apply {
//            nombre = "Original"
//            url = "https://original.com"
//            direccion = Direccion("Calle Vieja", 1, Point(0.0, 0.0))
//            comisionApp = 1.0
//            comisionAutor = 2.0
//            mediosDePago = mutableSetOf(Pago.EFECTIVO)
//        }
//
//        val localNuevo = Local().apply {
//            nombre = "Nuevo"
//            url = "https://nuevo.com"
//            direccion = Direccion("Calle Nueva", 2, Point(1.0, 1.0))
//            comisionApp = 3.0
//            comisionAutor = 4.0
//            mediosDePago = mutableSetOf(Pago.QR, Pago.TRANSFERENCIA_BANCARIA)
//        }
//
//        localOriginal.actualizar(localNuevo)
//
//        assertEquals("Nuevo", localOriginal.nombre)
//        assertEquals("https://nuevo.com", localOriginal.url)
//        assertEquals("Calle Nueva", localOriginal.direccion.calle)
//        assertEquals(3.0, localOriginal.comisionApp)
//        assertEquals(4.0, localOriginal.comisionAutor)
//        assertEquals(2, localOriginal.mediosDePago.size)
//        assertTrue(localOriginal.mediosDePago.contains(Pago.QR))
//    }
}