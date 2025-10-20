import ar.edu.unsam.algo3.repositorio.Repositorio
import ar.edu.unsam.algo3.modelo.cupon.Cupon
import ar.edu.unsam.algo3.modelo.cupon.DescuentoPorDia
import ar.edu.unsam.algo3.modelo.cupon.DescuentoPorLocal
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.utils.ActualizacionIngredientes
import ar.edu.unsam.algo3.modelo.utils.AgregarLocales
import ar.edu.unsam.algo3.modelo.utils.BorrarCupones
import ar.edu.unsam.algo3.modelo.utils.BorrarMensajesAntiguosYLeidos
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.modelo.utils.Mail
import ar.edu.unsam.algo3.modelo.utils.MailSender
import ar.edu.unsam.algo3.modelo.utils.Mensaje
import ar.edu.unsam.algo3.modelo.utils.ProcesoAdministracion
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.verify
import org.uqbar.geodds.Point
import java.time.LocalDate

class ProcesosAdminSpec: DescribeSpec ({
    isolationMode = IsolationMode.InstancePerTest

    val repoIng = Repositorio<ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente>()
    val repoCupon = Repositorio<Cupon>()
    val repoLocal = Repositorio<Local>()

    describe("Tests de Procesos de Administrador") {
        val direccionCercana = Direccion(ubicacion = Point(-34.52564, -58.51289), calle = "Maipu")

        it("Borra de un local un mensaje que tiene en inbox hace más de 30 días y con estado leído") {
            val mockedMailSender = mockk<MailSender>(relaxUnitFun = true)
            val mensajeLeidoYAntiguo = Mensaje(
                fechaDeEmision = LocalDate.now().minusDays(31),
                subject = "Antiguo",
                content = "Mensaje antiguo"
            )
            val mensajeNoLeido = Mensaje(
                fechaDeEmision = LocalDate.now(),
                subject = "Reciente",
                content = "Mensaje reciente"
            )

            val local = Local(
                inbox = mutableListOf(mensajeNoLeido, mensajeLeidoYAntiguo),
                nombre = "mcdonalds",
                direccion = direccionCercana
            )

            local.leer(mensajeLeidoYAntiguo)

            repoLocal.crear(local)

            val procesoAdministracion = ProcesoAdministracion()
            procesoAdministracion.agregarAccion(
                BorrarMensajesAntiguosYLeidos(
                    mailSender = mockedMailSender,
                    repositorioLocales = repoLocal
                )
            )
            procesoAdministracion.ejecutarAcciones()

            local.inbox.size shouldBe 1
        }

        it("Actualiza los ingredientes del repositorio") {
            val mockedMailSender = mockk<MailSender>(relaxUnitFun = true)
            val ingrediente = ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente(
                nombre = "Leche",
                costoMercado = 500.5,
                grupoAlimenticio = ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio.PROTEINAS,
                esOrigenAnimal = false
            )
            repoIng.crear(ingrediente)

            val procesoAdministracion = ProcesoAdministracion()
            procesoAdministracion.agregarAccion(
                accion = ActualizacionIngredientes(
                    mailSender = mockedMailSender,
                    repositorioIng = repoIng,
                    mensaje = """[{
                    "id": 1,
                    "nombre": "Leche",
                    "costo": 200.5,
                    "grupo": "LACTEOS",
                    "origenAnimal": true
                    }]""",
                )
            )
            procesoAdministracion.ejecutarAcciones()

            val ingredienteActualizado = repoIng.obtenerObjeto(1)
            ingredienteActualizado?.costoMercado shouldBe 200.5
            ingredienteActualizado?.grupoAlimenticio shouldBe ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio.LACTEOS
        }
        it("Borra cupones no aplicados y vencidos") {
            val mockedMailSender = mockk<MailSender>(relaxUnitFun = true)
            val cuponSinUsar = DescuentoPorDia()
            val cuponVencido = DescuentoPorLocal()
            cuponVencido.fechaDeEmision = LocalDate.now().minusDays(90)
            cuponVencido.duracion = 10
            repoCupon.crear(cuponSinUsar)
            repoCupon.crear(cuponVencido)
            val procesoAdministracion = ProcesoAdministracion()
            procesoAdministracion.agregarAccion(
                BorrarCupones(
                    mailSender = mockedMailSender,
                    repositorioCupon = repoCupon
                )
            )
            procesoAdministracion.ejecutarAcciones()

            repoCupon.objetosDeRepositorio().size shouldBe 1
        }

        it("Agrega de forma masiva locales, partiendo de una lista dada.") {
            // Arrange
            val mockedMailSender = mockk<MailSender>(relaxUnitFun = true)
            val mcDonalds = Local(nombre = "mcdonalds", direccion = direccionCercana)
            val burgerKing = Local(nombre = "burgerking", direccion = direccionCercana)
            val mostaza = Local(nombre = "mostaza", direccion = direccionCercana)
            val wendys = Local(nombre = "wendys", direccion = direccionCercana)
            val localesNuevos = mutableListOf(mcDonalds, burgerKing, mostaza, wendys)
            val procesoAdministracion = ProcesoAdministracion().apply {
                agregarAccion(
                    AgregarLocales(
                        localesNuevos = localesNuevos,
                        mailSender = mockedMailSender,
                        repositorioLocales = repoLocal
                    )
                )
            }
            // Act
            procesoAdministracion.ejecutarAcciones()
            // Assert
            repoLocal.objetosDeRepositorio().size shouldBe 4
            verify(exactly = 1) {
                mockedMailSender.sendMail(
                    Mail(
                        from = "app@process.admins.com",
                        to = "admin@aqp.com.ar",
                        subject = "Process AgregarLocales",
                        content = "Se realizó el proceso: AgregarLocales"
                    )
                )
            }

        }
    }
})