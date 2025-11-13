import ar.edu.unsam.algo3.modelo.cupon.DescuentoPorDia
import ar.edu.unsam.algo3.modelo.cupon.DescuentoPorLocal
import ar.edu.unsam.algo3.modelo.cupon.DescuentoPorPorcentaje
import ar.edu.unsam.algo3.errores.CuponNoAplicable
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

class CuponSpec : DescribeSpec({
    isolationMode = InstancePerTest
    describe("Testeo de cupones") {
        it("Test de cupon por dia") {
            //arrange
            //seteo un plato creado hace tiempo por que no quiero que tenga ningun descuento por nuevo, solo me interesa
            //ver como aplican los descuentos por cupones
            val platoPedidoConCupon = Plato(valorBase = 1000.00, fechaDeCreacion = LocalDate.now().minusDays(364))
            val localPedidoConCupon = Local()
            val cuponDescuentoPorDia = DescuentoPorDia()
            val pedidoConCupon = Pedido(local = localPedidoConCupon, platos = mutableListOf(platoPedidoConCupon))
            //act
            pedidoConCupon.costoBasePlatos()
            platoPedidoConCupon.aplicacionesPrecioBase()
            pedidoConCupon.pagaCon(Pago.EFECTIVO)
            localPedidoConCupon.agregarMedioDePago(Pago.EFECTIVO)
            cuponDescuentoPorDia.fechaDeUsoCupon = LocalDate.now().minusDays(7)
            //assert
            //el costo del pedido es 1000$ mas el 10% por pedido por delivery, quedaria en 2200,
            //como se aplica descuento por local es de 500$ por pedido certificado mas 500$ por
            //pertener al local
            //primero valido que el cupon sea aplicable
            cuponDescuentoPorDia.esAplicable(pedidoConCupon) shouldBe true
            pedidoConCupon.tieneCuponAplicado() shouldBe false
            //aplico el cupon al pedido con todas las condiciones necesarias para que sume el descuento maximo
            // 10% descuento base, 10% que el cupon fue usado el mismo dia que se creo un plato del pedido
            pedidoConCupon.cupon = cuponDescuentoPorDia
            pedidoConCupon.tieneCuponAplicado() shouldBe true
            pedidoConCupon.totalAPagar() shouldBe 1100.0
            pedidoConCupon.totalAPagarConCupon() shouldBe 880.0

        }
        it("Test de cupon por local") {
            //arrange
            val platoPedidoConCupon = Plato(valorBase = 2000.00)
            val localPedidoConCupon = Local()
            val cuponDescuentoPorLocal = DescuentoPorLocal()
            val pedidoConCupon = Pedido(local = localPedidoConCupon, platos = mutableListOf(platoPedidoConCupon))
            //act
            pedidoConCupon.costoBasePlatos()
            platoPedidoConCupon.aplicacionesPrecioBase()
            pedidoConCupon.pagaCon(Pago.EFECTIVO)
            localPedidoConCupon.agregarMedioDePago(Pago.EFECTIVO)
            cuponDescuentoPorLocal.locales.add(localPedidoConCupon)
            //assert
            localPedidoConCupon.agregarPuntuacion(Calificacion(5, "ok"))
            pedidoConCupon.esCertificado() shouldBe true
            cuponDescuentoPorLocal.esAplicable(pedidoConCupon) shouldBe true
            pedidoConCupon.cupon = cuponDescuentoPorLocal
            // total a pagar del pedido sin cupon y con costo delivery 2200
            //total a pagar con cupon, %10 de descuento base osea 220 + 1000$ de descuento por cupon
            //emitido en el local y pedido certificado
            pedidoConCupon.totalAPagar() shouldBe 2200.0
            pedidoConCupon.totalAPagarConCupon() shouldBe 980.0
        }
        it("Test de cupon por tope de reintegro") {
            //arrange
            val platoPedidoConCupon = Plato(valorBase = 2000.00)
            val localPedidoConCupon = Local()
            val cuponDescuentoPorTope = DescuentoPorPorcentaje()
            val pedidoConCupon = Pedido(local = localPedidoConCupon, platos = mutableListOf(platoPedidoConCupon))
            //act
            pedidoConCupon.costoBasePlatos()
            platoPedidoConCupon.aplicacionesPrecioBase()
            pedidoConCupon.pagaCon(Pago.EFECTIVO)
            localPedidoConCupon.agregarMedioDePago(Pago.EFECTIVO)
            //assert
            cuponDescuentoPorTope.esAplicable(pedidoConCupon) shouldBe true
            pedidoConCupon.tieneCuponAplicado() shouldBe false
            pedidoConCupon.cupon = cuponDescuentoPorTope
            // total a pagar del pedido sin cupon y con costo delivery 2200
            //total a pagar con cupon, %10 de descuento base osea 220 + 500$ de descuento por cupon = 720$ total descuento

            pedidoConCupon.totalAPagar() shouldBe 2200.0
//            println(cuponDescuentoPorTope.montoBaseDescuento(pedidoConCupon))
//            println(cuponDescuentoPorTope.descuentoEspecialPorCupon(pedidoConCupon))
//            println(pedidoConCupon.totalAPagarConCupon())
            pedidoConCupon.totalAPagarConCupon() shouldBe 1480.0
        }
        it("Testeo que no se pueda agregar un cupon a un pedido que ya tiene uno aplicado") {
            //arrange
            val platoPedidoConCupon = Plato(valorBase = 2000.00)
            val localPedidoConCupon = Local()
            val cuponDescuentoPorTope = DescuentoPorPorcentaje()
            val cuponDescuentoPorDia = DescuentoPorDia()
            val pedidoConCupon = Pedido(
                local = localPedidoConCupon,
                platos = mutableListOf(platoPedidoConCupon),
                cupon = cuponDescuentoPorDia
            )
            //act
            pedidoConCupon.costoBasePlatos()
            platoPedidoConCupon.aplicacionesPrecioBase()
            pedidoConCupon.pagaCon(Pago.EFECTIVO)
            localPedidoConCupon.agregarMedioDePago(Pago.EFECTIVO)
            //assert
            pedidoConCupon.tieneCuponAplicado() shouldBe true
            val excepcion = shouldThrow<CuponNoAplicable> {
                pedidoConCupon.aplicarCupon(cuponDescuentoPorTope)
            }
            excepcion.message shouldBe "Este cupon no cumple las condiciones necesarias para ser aplicado"
        }
    }
})