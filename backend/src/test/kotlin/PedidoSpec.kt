import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.errores.PlatoNoEsDeLocalException
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Calificacion
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.IsolationMode.*
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class PedidoSpec : DescribeSpec({
    isolationMode = InstancePerTest
    describe("Testeo de pedidos") {
        it("testeo un pedido certificado") {
            //arrange
            //lo limito por puntuacion, el tiempo de registro esta seteado en usuario al 01/01/2023
            val localCertificado = Local()
            val usuarioCertificado = Usuario()
            val pedidoCertificado = Pedido(usuario = usuarioCertificado, local = localCertificado)
            // Act
            localCertificado.agregarPuntuacion(Calificacion(5,""))
            usuarioCertificado.tiempoRegistrado()


            // Assert
            pedidoCertificado.esCertificado() shouldBe true
        }

        it("Genero un pedido NO certificado") {
            //arrange
            //para probar la otra combinacion, cambio la fecha de registro del usuario al dia de hoy
            val localNoCertificado = Local()
            val usuarioNoCertificado = Usuario(fechaDeRegistro = LocalDate.of(2025, 1, 1))
            val pedidoNoCertificado = Pedido(usuario = usuarioNoCertificado, local = localNoCertificado)
            // Act
            val usuarioCertificadoAgregaPuntuacion = localNoCertificado.agregarPuntuacion(Calificacion(5,""))
            val antiguedadEnLaApp = usuarioNoCertificado.tiempoRegistrado()

            // Assert
            pedidoNoCertificado.esCertificado() shouldBe false
        }

    }
    describe("testeo los montos a pagar") {
        it("verifico el monto a pagar de un plato con pago en efectivo (definido por defecto") {
            //arrange
            val platoPagoEfectivo = Plato(valorBase = 100.0)
            val localPagoEfectivo = Local()
            val pedidoPagoEfectivo = Pedido(local = localPagoEfectivo, platos = mutableListOf(platoPagoEfectivo))

            //act
            pedidoPagoEfectivo.pagaCon(Pago.EFECTIVO)
            platoPagoEfectivo.aplicacionesPrecioBase()


            //assert
            pedidoPagoEfectivo.totalAPagar() shouldBe 110

        }
    }

    it("verifico el monto a pagar de un plato con pago con QR") {
        //arrange
        val platoPagoQR = Plato(valorBase = 100.0)
        val localPagoQR = Local()
        //val localPagoQR = Local(mediosPago = mutableSetOf(Pagos.QR))
        val pedidoPagoQR = Pedido(local = localPagoQR, platos = mutableListOf(platoPagoQR))

        //act
        platoPagoQR.aplicacionesPrecioBase()
        localPagoQR.agregarMedioDePago(Pago.QR)
        pedidoPagoQR.pagaCon(Pago.QR)

        //assert
        pedidoPagoQR.totalAPagar() shouldBe 115.5

    }
    it("verifico que no se pueda agregar dos platos de distintos locales a un pedido") {
        //arrange
        val platoMcdonalds = Plato()
        val platoBurguerking = Plato()
        val mcdonalds = Local()
        //val localPagoQR = Local(mediosPago = mutableSetOf(Pagos.QR))
        val pedidoMcdonalds = Pedido(local = mcdonalds, platos = mutableListOf(platoMcdonalds))

        //act

        val excepcion = shouldThrow<PlatoNoEsDeLocalException> {
            pedidoMcdonalds.agregarPlato(platoBurguerking)
        }

        //assert


    }
})

