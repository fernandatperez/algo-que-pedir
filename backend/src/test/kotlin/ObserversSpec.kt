import ar.edu.unsam.algo3.repositorio.Repositorio
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.pedido.PublicidadMailObserver
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Generalista
import ar.edu.unsam.algo3.modelo.usuario.Vegano
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.modelo.utils.Mail
import ar.edu.unsam.algo3.modelo.utils.MailSender
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.mockk
import io.mockk.verify
import org.uqbar.geodds.Point
import java.time.LocalDate

class ObserversSpec : DescribeSpec ({
    isolationMode = IsolationMode.InstancePerTest

    val direccionCercana = Direccion(ubicacion = Point(-34.52564, -58.51289), calle = "Maipu")
    val mcDonalds = Local("McDonalds", direccion = direccionCercana)

    val carne = ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente(
        nombre = "carne",
        costoMercado = 1000.0,
        esOrigenAnimal = true,
        grupoAlimenticio = ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio.PROTEINAS
    )
    val papa = ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente(
        nombre = "papa",
        costoMercado = 200.0,
        esOrigenAnimal = false,
        grupoAlimenticio = ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio.FRUTAS_Y_VERDURAS
    )
    val aceite = ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente(
        nombre = "aceite",
        costoMercado = 500.0,
        esOrigenAnimal = false,
        grupoAlimenticio = ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio.GRASAS_Y_ACEITES
    )
    val espinaca = ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente(
        nombre = "espinaca",
        costoMercado = 800.0,
        esOrigenAnimal = false,
        grupoAlimenticio = ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio.FRUTAS_Y_VERDURAS
    )

    val milasConFritas = Plato(
        nombre = "Milas Con Fritas",
        valorBase = 500.0,
        descripcion = "Clasico",
        local = mcDonalds,
        fechaDeCreacion = LocalDate.of(2025, 3, 5),
        ingredientes = mutableListOf(aceite, carne)
    )

    val carneConPapas = Plato(
        nombre = "Carne Con Papas",
        valorBase = 700.0,
        descripcion = "Clasico",
        local = mcDonalds,
        fechaDeCreacion = LocalDate.of(2025, 3, 5),
        ingredientes = mutableListOf(papa, carne)
    )

    val tartaDeVerdura = Plato(
        nombre = "Tarta De Verdura",
        valorBase = 700.0,
        descripcion = "Clasico",
        local = mcDonalds,
        fechaDeCreacion = LocalDate.of(2025, 3, 5),
        ingredientes = mutableListOf(papa, espinaca)
    )

    val repoPlatos = Repositorio<Plato>().apply {
        crear(milasConFritas)
        crear(carneConPapas)
        crear(tartaDeVerdura)
    }

    describe("Tests de Observers") {

        it("Se envia un mail con recomendacion") {
            // Arrange
            val mockedMailSender = mockk<MailSender>(relaxUnitFun = true)
            val mailObserver = PublicidadMailObserver(repositorioPlatos = repoPlatos, mailSender = mockedMailSender)
            val usuario = Usuario(mailPrincipal = "usuario1@usuario.com", tipoDeCliente = Generalista)
            val pedido = Pedido(usuario, mcDonalds).apply {
                agregarPlato(milasConFritas)
                agregarObserver(mailObserver)
            }

            // Act
            usuario.confirmarPedido(pedido)

            // Assert
            verify(exactly = 1) {
                mockedMailSender.sendMail(
                    Mail(
                        from = "publi@algoquepedir.com",
                        to = pedido.usuario.mailPrincipal,
                        subject = "Te puede interesar este plato!",
                        content = "Basado en tus preferencias no te podes perder: Carne Con Papas"
                    )
                )
            }
        }

        it("NO se envia un mail con recomendacion") {
            // Arrange
            val mockedMailSender = mockk<MailSender>(relaxUnitFun = true)
            val mailObserver = PublicidadMailObserver(repositorioPlatos = repoPlatos, mailSender = mockedMailSender)
            val usuario = Usuario(mailPrincipal = "usuario1@usuario.com", tipoDeCliente = Vegano) // ahora es vegano
            val pedido = Pedido(usuario, mcDonalds).apply {
                agregarPlato(tartaDeVerdura)
                agregarObserver(mailObserver)
            }

            // Act
            usuario.confirmarPedido(pedido)

            // Assert
            verify(exactly = 0) {
                mockedMailSender.sendMail(any())
            }
        }
    }
})