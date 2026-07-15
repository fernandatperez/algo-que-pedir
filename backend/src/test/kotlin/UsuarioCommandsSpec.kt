import ar.edu.unsam.algo3.*
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.usuario.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.uqbar.geodds.Point

class UsuarioCommandsSpec: DescribeSpec({
    val direccionLocalCercano = Direccion(ubicacion = Point(-34.52564, -58.51289))
    val direccionLocalLejano = Direccion(ubicacion = Point(-34.577711, -58.52682))
    val localCercano = Local("Local Cercano", direccion = direccionLocalCercano)
    describe("Tests de comandos sobre usuario (Command Pattern)") {
        it("EstablecerPedido debe confirmar el pedido en el usuario") {
            val usuario = Usuario()
            val pedido = Pedido(local = localCercano, usuario = usuario)

            usuario.agregarAcciones(EstablecerPedido(pedido))

            usuario.localesAPuntuar.containsKey(localCercano) shouldBe false

            usuario.ejecutarAcciones()

            usuario.localesAPuntuar.containsKey(localCercano) shouldBe true
        }

        it("Puntuar con estrategia fija debe puntuar todos los locales con el mismo puntaje") {
            val usuario = Usuario()
            val pedido = Pedido(local = localCercano, usuario = usuario)

            usuario.agregarAcciones(EstablecerPedido(pedido))

            usuario.agregarAcciones(Puntuar(PuntuarFijo(Calificacion(4,""))))

            localCercano.promedioPuntuacion() shouldBe 0

            usuario.ejecutarAcciones()

            localCercano.promedioPuntuacion() shouldBe 4
        }

        it("Puntuar con estrategia actual redondea el promedio actual del local") {
            val usuario = Usuario()
            val localprom = Local("TestLocalActual", direccion = direccionLocalLejano)
            localprom.agregarPuntuacion(Calificacion(3,""))
            localprom.agregarPuntuacion(Calificacion(4,"")) // Promedio = 3.5

            val pedido = Pedido(local = localprom, usuario = usuario)
            usuario.agregarAcciones(EstablecerPedido(pedido))
            usuario.agregarAcciones(Puntuar(PuntuarActual()))

            usuario.ejecutarAcciones()

            localprom.promedioPuntuacion() shouldBe 3.3333333333333335 // esto tendria que ser 3.3333333333333335
        }
    }
})