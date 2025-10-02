import ar.edu.unsam.algo2.algoQuePedir.*
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class AuditoriaSpec: DescribeSpec ({
    isolationMode = IsolationMode.InstancePerTest

    val local = Local()

    val carne = Ingrediente(
        nombre = "carne",
        costoMercado = 1000.0,
        esOrigenAnimal = true,
        grupoAlimenticio = GrupoAlimenticio.PROTEINAS
    )
    val papa = Ingrediente(
        nombre = "papa",
        costoMercado = 200.0,
        esOrigenAnimal = false,
        grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS
    )
    val aceite = Ingrediente(
        nombre = "aceite",
        costoMercado = 500.0,
        esOrigenAnimal = false,
        grupoAlimenticio = GrupoAlimenticio.GRASAS_Y_ACEITES
    )

    val brocoli = Ingrediente(
        nombre = "Brocoli",
        costoMercado = 400.0,
        esOrigenAnimal = false,
        grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS
    )

    val milasConFritas = Plato(
        nombre = "milasConFritas",
        valorBase = 500.0,
        descripcion = "Clasico",
        local = local,
        fechaDeCreacion = LocalDate.of(2025, 3, 5),
        ingredientes = mutableListOf(aceite, carne)
    )

    val carneConPapas = Plato(
        nombre = "carneConPapas",
        valorBase = 700.0,
        descripcion = "Clasico",
        local = local,
        fechaDeCreacion = LocalDate.of(2025, 3, 5),
        ingredientes = mutableListOf(papa, carne)
    )

    val papasFritas = Plato(
        nombre = "Papitas Fritas",
        valorBase = 700.0,
        descripcion = "va con todo",
        local = local,
        fechaDeCreacion = LocalDate.of(2025, 3, 5),
        ingredientes = mutableListOf(papa)
    )

    val brocoliHervido = Plato(
        nombre = "Brocoli hervido",
        valorBase = 700.0,
        descripcion = "Nada mas saludable",
        local = local,
        fechaDeCreacion = LocalDate.of(2025, 3, 5),
        ingredientes = mutableListOf(brocoli)
    )

    val usuario = Usuario()

    describe("Tests de Auditoria"){
        it("Se cumple el objetivo y devuelve true si supera un acumulado de ventas"){
            // Arrange
            val objetivoAcumuladoVentas = ObjetivoAcumuladoVentas(500.0, 5.0)
            val pedido = Pedido(platos = mutableListOf(milasConFritas,carneConPapas), local = local, usuario = usuario).apply {
                agregarObserver(AuditoriaObserver)
            }
            AuditoriaObserver.agregarLocal(local, objetivoAcumuladoVentas)
            // Act
            usuario.confirmarPedido(pedido)
            // Assert
            AuditoriaObserver.cumpleObjetivo(local) shouldBe true
        }

        it("Si un local no tiene auditoria no rompe"){
            // Arrange
            val pedido = Pedido(platos = mutableListOf(milasConFritas), local = local, usuario = usuario)

            // Act & Assert
            shouldNotThrow<UninitializedPropertyAccessException> { // FUNCIONA :D
                usuario.confirmarPedido(pedido)
            }
        }

        it("Se testea una auditoria combinada que cumpla con el objetivo"){
            // Arrange
            val objetivoPlatosVeganos = ObjetivoPlatosVeganos(contadorPlatosVeganos = 2, meta=2)
            val objetivoAcumuladoVentas = ObjetivoAcumuladoVentas(500.0, 5.0)
            val objetivoCombinado = ObjetivoCombinado(mutableSetOf(objetivoAcumuladoVentas,objetivoPlatosVeganos))
            val pedido = Pedido(platos = mutableListOf(milasConFritas,carneConPapas), local = local, usuario = usuario).apply {
                agregarObserver(AuditoriaObserver)
            }
            val pedidoVegano = Pedido(platos = mutableListOf(papasFritas,brocoliHervido), local = local )
            AuditoriaObserver.agregarLocal(local, objetivoCombinado)
            // Act
            usuario.confirmarPedido(pedido)
            // Assert
            AuditoriaObserver.cumpleObjetivo(local) shouldBe true
        }

        it("Se testea una auditoria combinada que NO cumpla con el objetivo"){
            // Arrange
            val objetivoPlatosVeganos = ObjetivoPlatosVeganos(contadorPlatosVeganos = 5, meta=10) // Esta NO cumple
            val objetivoAcumuladoVentas = ObjetivoAcumuladoVentas(500.0, 5.0) // Esta SI cumple
            val objetivoCombinado = ObjetivoCombinado(mutableSetOf(objetivoAcumuladoVentas,objetivoPlatosVeganos))
            val pedido = Pedido(platos = mutableListOf(milasConFritas,carneConPapas), local = local, usuario = usuario).apply {
                agregarObserver(AuditoriaObserver)
            }
            val pedidoVegano = Pedido(platos = mutableListOf(papasFritas,brocoliHervido), local = local )
            AuditoriaObserver.agregarLocal(local, objetivoCombinado)
            // Act
            usuario.confirmarPedido(pedido)
            // Assert
            AuditoriaObserver.cumpleObjetivo(local) shouldBe false
        }

    }
})