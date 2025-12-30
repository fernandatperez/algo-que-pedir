import ar.edu.unsam.algo3.errores.DescuentoMayorAlCienException
import ar.edu.unsam.algo3.errores.NoAplicaDescuentoEnPlatoNuevoException
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.plato.Plato
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class PlatoSpec : DescribeSpec({
    val local = Local(
        porcentajeAcordado = 0.3,
        regalias = 0.5
    )
    describe("Errores") {
        it("Tira error si aplico desc. a producto nuevo") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                local = local,
                fechaDeCreacion = LocalDate.now()
            )
            val excepcion = shouldThrow<NoAplicaDescuentoEnPlatoNuevoException> {
                milasConFritas.aplicarDescuento(0.2)
            }
            excepcion.message shouldBe "Descuento no aplicable. El plato es nuevo."
        }

        it("Que pasa si el valorBase es 0?") {
            val milasConFritas = Plato(
                valorBase = 0.0,
                local = local,
                fechaDeCreacion = LocalDate.of(2020, 3, 5)
            )
            milasConFritas.aplicarDescuento(0.2)
            milasConFritas.valorVenta() shouldBe 0
        }

        it("Porcentaje de descuento mayor al 100%") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                local = local,
                fechaDeCreacion = LocalDate.of(2000, 3, 5)
            )
            val excepcion = shouldThrow<DescuentoMayorAlCienException> {
                milasConFritas.aplicarDescuento(1.5)
            }

            excepcion.message shouldBe "Descuento mayor al 100% no aplicable."
        }
    }

    describe("Calculos y porcentajes") {
        val carne = Ingrediente(
            costoMercado = 1000.0
        )
        val papas = Ingrediente(
            costoMercado = 200.0
        )
        val aceite = Ingrediente(
            costoMercado = 500.0
        )
        val panRallado = Ingrediente(
            costoMercado = 300.0
        )

        it("Plato sin costo de produccion, el valor es el base + acordado") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                local = local,
                fechaDeCreacion = LocalDate.of(2000, 1, 5)
            )
            milasConFritas.valorVenta() shouldBe 650.0
        }

        it("Mila con fritas costo produccion 2000") {
            val milasConFritas = Plato(
                local = local,
                ingredientes = mutableListOf(carne, papas, aceite, panRallado)
            )
            milasConFritas.costoProduccion() shouldBe (2000.0)
        }

        it("Valor de Venta de un plato") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                local = local,
                ingredientes = mutableListOf(carne, papas, aceite, panRallado)
            )
            milasConFritas.valorVenta() shouldBe 2650.0
        }

        it("Valor de venta base con regalias") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                local = local,
                esDeAutor = true,
                ingredientes = mutableListOf(carne, papas, aceite, panRallado)
            )
            milasConFritas.valorVenta() shouldBe 2900.0
        }

        it("Valor de venta de una mila en descuento (20%)") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                local = local,
                ingredientes = mutableListOf(carne, papas, aceite, panRallado)
            )
            milasConFritas.aplicarDescuento(0.2)
            milasConFritas.valorVenta() shouldBe 2120.0
        }

// <-- Resolución con fechas relativas para los tests
        it("Valor de venta de una mila nueva que salio el 22/3/25 7 dias despues (29/3/25) 23% descuento") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                ingredientes = mutableListOf(carne, papas, aceite, panRallado),
                local = local,
                fechaDeCreacion = LocalDate.now().minusDays(7), // <--
            )

            milasConFritas.valorVenta() shouldBe 2040.5
        }

        it("Valor de venta de una mila nueva (Salio hace 17 dias, descuento del 13%)") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                ingredientes = mutableListOf(carne, papas, aceite, panRallado),
                local = local,
                fechaDeCreacion = LocalDate.now().minusDays(17), // <--
            )
            milasConFritas.valorVenta() shouldBe 2305.5 // <--
        }

        it("Se aplica y se saca un descuento a un producto") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                local = local,
                ingredientes = mutableListOf(carne, papas, aceite, panRallado)
            )
            milasConFritas.aplicarDescuento(0.5)
            milasConFritas.sacarDescuento()
            milasConFritas.valorVenta() shouldBe 2650.0
        }

        it("El dia 30 tambien se aplica descuento") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                ingredientes = mutableListOf(carne, papas, aceite, panRallado),
                local = local,
                fechaDeCreacion = LocalDate.now().minusDays(30), // <--
            )
            milasConFritas.valorVenta() shouldBe 2385.0 // <--
        }

        it("El 31 ya no se aplica descuento") {
            val milasConFritas = Plato(
                valorBase = 500.0,
                ingredientes = mutableListOf(carne, papas, aceite, panRallado),
                local = local,
                fechaDeCreacion = LocalDate.now().minusDays(31) // <--
            )
            milasConFritas.valorVenta() shouldBe 2650.0
        }
    }
})