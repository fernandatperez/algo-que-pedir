import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.errores.YaEstaEnLaListaException
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.delivery.Delivery
import ar.edu.unsam.algo3.modelo.delivery.DeliveryCaro
import ar.edu.unsam.algo3.modelo.delivery.DeliveryCertificado
import ar.edu.unsam.algo3.modelo.delivery.DeliveryCombinadoAnd
import ar.edu.unsam.algo3.modelo.delivery.DeliveryCombinadoOr
import ar.edu.unsam.algo3.modelo.delivery.DeliveryLocales
import ar.edu.unsam.algo3.modelo.delivery.DeliverySeguro
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Calificacion
import ar.edu.unsam.algo3.modelo.utils.Direccion
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.uqbar.geodds.Point
import org.uqbar.geodds.Polygon
import java.time.LocalTime

class DeliverySpec : DescribeSpec({
    describe("Delivery Spec") {
        it("No se puede agregar un punto que ya esta en la zona") {
            // Arrange
            val puntoRepetido = Point(-34.52168415387585, -58.513714921128056)
            val listaPuntos = mutableListOf(
                puntoRepetido,
                Point(-34.53030621937303, -58.5339238418408),
                Point(-34.549925523494096, -58.523963730918084),
            )
            val zona = Polygon(listaPuntos)
            val delivery = Delivery(zonaDeTrabajo = zona)

            val excepcion = shouldThrow<YaEstaEnLaListaException> {
                delivery.agregarPuntoDeZona(puntoRepetido)
            }
            // Assert
            excepcion.message shouldBe "El punto ya fue registrado en la zona."
            delivery.zonaDeTrabajo.surface.size shouldBe 3
        }

        it("No se puede crear una zona de menos de 3 puntos") {
            //Arrange
            val listaPuntos = mutableListOf(
                Point(-34.53030621937303, -58.5339238418408),
                Point(-34.549925523494096, -58.523963730918084),
            )
            val zona = Polygon(listaPuntos)

            val excepcion = shouldThrow<RuntimeException> {
                Delivery(zonaDeTrabajo = zona)
            }
            // Assert
            excepcion.message shouldBe "Zona de trabajo debe tener al menos 3 puntos"
        }
    }

    describe("El Delivery puede entregar un pedido") {
        val direccionEnZona = Direccion(ubicacion = Point(-34.54094877940498, -58.512127077357775))
        val localEnZona = Local("Local En Zona", direccion = direccionEnZona) //Pagos.EFECTIVO
        val usuarioEnZona = Usuario(direccion = direccionEnZona)
        val pedidoPreparado = Pedido(
            usuario = usuarioEnZona,
            local = localEnZona,
            estado = Estado.PREPARADO,
        )
        val listaPuntos = mutableListOf(
            Point(-34.52168415387585, -58.513714921128056),
            Point(-34.53030621937303, -58.5339238418408),
            Point(-34.549925523494096, -58.523963730918084),
            Point(-34.53863012525849, -58.4997130260628)
        )
        val zonaDelivery = Polygon(listaPuntos)

        it("Un Delivery Seguro puede entregar un pedido en horario seguro (17:00)") {
            // Arrange
            pedidoPreparado.horarioEntrega = LocalTime.of(17, 0)
            val tipoDeliverySeguro = DeliverySeguro(horarioFinalSeguro = LocalTime.of(17, 0))
            val deliverySeguro = Delivery(zonaDeTrabajo = zonaDelivery, tipo = tipoDeliverySeguro)

            // Assert
            deliverySeguro.puedeEntregar(pedidoPreparado) shouldBe true
        }

        it("Un Delivery Caro puede entregar un pedido mayor o igual a 30000") {
            // Arrange
            val platoCaro = Plato(valorBase = 30001.0) // El total es 33001.1
            platoCaro.agregarIngrediente(ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente())
            val pedidoCaro = Pedido(
                usuario = usuarioEnZona,
                local = localEnZona,
                estado = Estado.PREPARADO,
            )
            val deliveryCaro = Delivery(zonaDeTrabajo = zonaDelivery, tipo = DeliveryCaro)

            // Act
            pedidoCaro.agregarPlato(platoCaro)

            // Assert
            deliveryCaro.puedeEntregar(pedidoCaro) shouldBe true
        }

        it("Un Delivery Locales puede entregar a un local preferido") {
            // Arrange
            val tipoDeliveryLocal = DeliveryLocales()
            tipoDeliveryLocal.agregarLocalPreferido(local = localEnZona)
            val deliveryLocales = Delivery(zonaDeTrabajo = zonaDelivery, tipo = tipoDeliveryLocal)

            // Assert
            deliveryLocales.puedeEntregar(pedidoPreparado) shouldBe true
        }

        it("Un Delivery Certificado puede entregar un pedido certificado.") {
            // Arrange
            val deliveryCertificado = Delivery(zonaDeTrabajo = zonaDelivery, tipo = DeliveryCertificado)

            localEnZona.agregarPuntuacion(Calificacion(5,"")) // Promedio de puntuacion debe estar entre 4 y 5

            // Assert
            deliveryCertificado.puedeEntregar(pedidoPreparado) shouldBe true
        }

        it("Un Delivery entrega pedido en rango seguro Ó valor mayor a 30.000") {
            // Arrange
            val platoBarato =
                Plato(nombre = "Plato Barato", local = localEnZona, valorBase = 1.0) // El total es 1.1
            val pedidoBarato = Pedido(
                usuario = usuarioEnZona,
                local = localEnZona,
                estado = Estado.PREPARADO,
            ).apply { horarioEntrega = LocalTime.of(17, 0) }
            val tipoDeliverySeguro = DeliverySeguro().apply {
                horarioFinalSeguro = LocalTime.of(17, 0)
            }
            val tipoCombinadoOr = DeliveryCombinadoOr().apply {
                agregarCriterio(tipoDeliverySeguro)
                agregarCriterio(DeliveryCaro)
            }
            val deliverySeguroOCaro = Delivery(zonaDeTrabajo = zonaDelivery, tipo = tipoCombinadoOr)

            // Act
            platoBarato.agregarIngrediente(ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente()) // debe tener al menos 1 ingrediente
            pedidoBarato.agregarPlato(platoBarato)
            // Assert
            deliverySeguroOCaro.puedeEntregar(pedidoBarato) shouldBe true
        }

        it("Un Delivery entrega pedidos certificados Y locales determinados") {
            // Arrange
            val tipoDeliveryLocal = DeliveryLocales()
            val tipoCombinadoAnd = DeliveryCombinadoAnd().apply {
                agregarCriterio(tipoDeliveryLocal)
                agregarCriterio(DeliveryCertificado)
            }
            val deliveryLocalesYCertificado = Delivery(zonaDeTrabajo = zonaDelivery, tipo = tipoCombinadoAnd)
            // Act
            tipoDeliveryLocal.agregarLocalPreferido(local = localEnZona)
            localEnZona.agregarPuntuacion(Calificacion(5,"")) // Certificado: Promedio de puntuacion debe estar entre 4 y 5

            // Assert
            deliveryLocalesYCertificado.puedeEntregar(pedidoPreparado) shouldBe true
        }

        it("Un Delivery entrega pedidos certificados, de locales determinados y en rango seguro ó valor mayor a 30.000") {
            // Arrange
            val platoCaro = Plato(nombre = "Plato Caro", local = localEnZona, valorBase = 30000.0)
            val pedidoCaro = Pedido(
                usuario = usuarioEnZona,
                local = localEnZona,
                estado = Estado.PREPARADO,
            ).apply { horarioEntrega = LocalTime.of(17, 0) }
            val tipoDeliverySeguro = DeliverySeguro().apply {
                horarioFinalSeguro = LocalTime.of(19, 0) // NO CUMPLE
            }
            val tipoDeliveryLocal = DeliveryLocales()
            val tipoCombinadoOr = DeliveryCombinadoOr().apply {
                agregarCriterio(tipoDeliverySeguro)
                agregarCriterio(DeliveryCaro)
            }
            val tipoCombinadoAndOr = DeliveryCombinadoAnd().apply {
                agregarCriterio(tipoCombinadoOr) // cumple
                agregarCriterio(tipoDeliveryLocal) // cumple
                agregarCriterio(DeliveryCertificado) // cumple
            }
            val delivery = Delivery(zonaDeTrabajo = zonaDelivery, tipo = tipoCombinadoAndOr)

            // Act
            platoCaro.agregarIngrediente(ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente()) // debe tener al menos 1 ingrediente
            pedidoCaro.agregarPlato(platoCaro)
            tipoDeliveryLocal.agregarLocalPreferido(local = localEnZona)
            localEnZona.agregarPuntuacion(Calificacion(5,"")) // Certificado: Promedio de puntuacion debe estar entre 4 y 5

            // Assert
            delivery.puedeEntregar(pedidoCaro) shouldBe true

        }

    }

    describe("El Delivery NO puede entregar") {
        val direccionEnZona = Direccion(ubicacion = Point(-34.54094877940498, -58.512127077357775))
        val localEnZona = Local("Local En Zona", direccion = direccionEnZona) //Pagos.EFECTIVO
        val usuarioEnZona = Usuario(direccion = direccionEnZona)
        val pedidoEnZona = Pedido(
            usuario = usuarioEnZona,
            local = localEnZona,
            estado = Estado.PREPARADO,
        )
        val listaPuntos = mutableListOf(
            Point(-34.52168415387585, -58.513714921128056),
            Point(-34.53030621937303, -58.5339238418408),
            Point(-34.549925523494096, -58.523963730918084),
            Point(-34.53863012525849, -58.4997130260628)
        )
        val zonaDelivery = Polygon(listaPuntos)

        it("Un Delivery no puede entregar un pedido No Preparado.") {
            // Arrange
            pedidoEnZona.estado = Estado.PENDIENTE
            val delivery = Delivery(zonaDeTrabajo = zonaDelivery, tipo = DeliverySeguro())

            // Assert
            delivery.puedeEntregar(pedidoEnZona) shouldBe false
        }

        val direccionFueraZona = Direccion(ubicacion = Point(-34.52272327719093, -58.527468031156694))
        it("Un Delivery no puede entregar si el local no esta en zona") {
            // Arrange
            val localFueraZona = Local(nombre = "Local Fuera Zona", direccion = direccionFueraZona)
            val pedidoLocalFueraZona = Pedido(
                usuario = usuarioEnZona,
                local = localFueraZona,
                estado = Estado.PREPARADO,
            )
            val delivery = Delivery(zonaDeTrabajo = zonaDelivery, tipo = DeliverySeguro())

            // Assert
            delivery.puedeEntregar(pedidoLocalFueraZona) shouldBe false
        }

        it("Un Delivery no puede entregar si el usuario no esta en zona") {
            // Arrange
            val usuarioFueraZona = Usuario(direccion = direccionFueraZona)
            val pedidoUserFueraZona = Pedido(
                usuario = usuarioFueraZona,
                local = localEnZona,
                estado = Estado.PREPARADO,
            )
            val delivery = Delivery(zonaDeTrabajo = zonaDelivery, tipo = DeliverySeguro())

            // Assert
            delivery.puedeEntregar(pedidoUserFueraZona) shouldBe false
        }

        it("Un Delivery Seguro no puede entregar fuera de horario seguro (17:01)") {
            // Arrange
            pedidoEnZona.horarioEntrega = LocalTime.of(17, 1)
            val tipoDeliverySeguro = DeliverySeguro(horarioFinalSeguro = LocalTime.of(17, 0))
            val deliverySeguro = Delivery(zonaDeTrabajo = zonaDelivery, tipo = tipoDeliverySeguro)

            // Assert
            deliverySeguro.puedeEntregar(pedidoEnZona) shouldBe false
        }

        it("Un Delivery Caro puede entregar un pedido menor a 30000") {
            // Arrange
            val platoBarato = Plato(valorBase = 1.0) // El total es 1.1
            platoBarato.agregarIngrediente(ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente())
            val pedidoCaro = Pedido(
                usuario = usuarioEnZona,
                local = localEnZona,
                estado = Estado.PREPARADO,
            )
            val deliveryCaro = Delivery(zonaDeTrabajo = zonaDelivery, tipo = DeliveryCaro)

            // Act
            pedidoCaro.agregarPlato(platoBarato)

            // Assert
            deliveryCaro.puedeEntregar(pedidoCaro) shouldBe false
        }

        it("Un Delivery Locales no puede entregar a local no preferido") {
            // Arrange
            val localPreferido = Local(nombre = "Local Preferido", direccion = direccionEnZona)
            val tipoDeliveryLocal = DeliveryLocales()
            tipoDeliveryLocal.agregarLocalPreferido(local = localPreferido)
            val deliveryLocales = Delivery(zonaDeTrabajo = zonaDelivery, tipo = tipoDeliveryLocal)
            // Local del pedidoEnZona es localEnZona

            // Assert
            deliveryLocales.puedeEntregar(pedidoEnZona) shouldBe false
        }

        it("Un Delivery Locales no puede entregar sin locales preferidos") {
            val deliveryLocales = Delivery(zonaDeTrabajo = zonaDelivery, tipo = DeliveryLocales())
            deliveryLocales.puedeEntregar(pedidoEnZona) shouldBe false
        }

        it("Un Delivery Certificado no puede entregar un pedido No Certificado") {
            // Arrange
            val deliveryCertificado = Delivery(zonaDeTrabajo = zonaDelivery, tipo = DeliveryCertificado)
            localEnZona.agregarPuntuacion(Calificacion(3,"")) // Promedio de puntuacion debe estar entre 4 y 5

            // Assert
            deliveryCertificado.puedeEntregar(pedidoEnZona) shouldBe false
        }

        it("Un Delivery Combinado NO entrega pedido que no está en rango seguro NI valor mayor a 30.000") {
            // Arrange
            pedidoEnZona.horarioEntrega = LocalTime.of(18, 0)
            val platoBarato =
                Plato(nombre = "Plato Barato", local = localEnZona, valorBase = 1.0) // El total es 1.1
            val tipoDeliverySeguro = DeliverySeguro().apply {
                horarioFinalSeguro = LocalTime.of(17, 0)
            }
            val tipoCombinadoOr = DeliveryCombinadoOr().apply {
                agregarCriterio(tipoDeliverySeguro)
                agregarCriterio(DeliveryCaro)
            }
            val deliverySeguroOCaro = Delivery(zonaDeTrabajo = zonaDelivery, tipo = tipoCombinadoOr)

            // Act
            platoBarato.agregarIngrediente(ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente()) // debe tener al menos 1 ingrediente
            pedidoEnZona.agregarPlato(platoBarato)
            // Assert
            deliverySeguroOCaro.puedeEntregar(pedidoEnZona) shouldBe false
        }

        it("Un Delivery Combinado NO entrega pedidos certificados pero de locales NO preferidos") {
            // Arrange
            val tipoDeliveryLocal = DeliveryLocales()
            val tipoCombinadoAnd = DeliveryCombinadoAnd().apply {
                agregarCriterio(tipoDeliveryLocal)
                agregarCriterio(DeliveryCertificado)
            }
            val localPreferido = Local(nombre = "Local Preferido", direccion = direccionEnZona)
            val deliveryLocalesYCertificado = Delivery(zonaDeTrabajo = zonaDelivery, tipo = tipoCombinadoAnd)

            // Act
            tipoDeliveryLocal.agregarLocalPreferido(local = localPreferido)
            localEnZona.agregarPuntuacion(Calificacion(5,"")) // Certificado: Promedio de puntuacion debe estar entre 4 y 5

            // Assert
            deliveryLocalesYCertificado.puedeEntregar(pedidoEnZona) shouldBe false
        }

        it("Un Delivery Combinado NO entrega pedidos que cumplen criterios obligatorios pero no cumplen los no obligatorios") {
            // Arrange
            pedidoEnZona.horarioEntrega = LocalTime.of(17, 0)
            val platoBarato = Plato(nombre = "Plato Barato", local = localEnZona, valorBase = 1.0)
            val tipoDeliverySeguro = DeliverySeguro().apply {
                horarioFinalSeguro = LocalTime.of(17, 0)
            }
            val tipoDeliveryLocal = DeliveryLocales()
            val localPreferido = Local(nombre = "Local Preferido", direccion = direccionEnZona)
            val tipoCombinadoOr = DeliveryCombinadoOr().apply {
                agregarCriterio(tipoDeliveryLocal) // No cumple
                agregarCriterio(DeliveryCaro) // No cumple
            }
            val tipoCombinadoAndOr = DeliveryCombinadoAnd().apply {
                agregarCriterio(tipoDeliverySeguro) // cumple
                agregarCriterio(DeliveryCertificado) // cumple
                agregarCriterio(tipoCombinadoOr) // No cumple
            }

            val delivery = Delivery(zonaDeTrabajo = zonaDelivery, tipo = tipoCombinadoAndOr)

            // Act
            platoBarato.agregarIngrediente(ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente()) // debe tener al menos 1 ingrediente
            pedidoEnZona.agregarPlato(platoBarato)
            tipoDeliveryLocal.agregarLocalPreferido(local = localPreferido)
            localEnZona.agregarPuntuacion(Calificacion(5,"")) // Certificado: Promedio de puntuacion debe estar entre 4 y 5

            // Assert
            delivery.puedeEntregar(pedidoEnZona) shouldBe false

        }
    }
})