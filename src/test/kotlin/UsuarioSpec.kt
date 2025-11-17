import ar.edu.unsam.algo3.errores.PerteneceAotraListaException
import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.*
import ar.edu.unsam.algo3.modelo.utils.Direccion
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.assertions.throwables.shouldThrow
//import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

import org.uqbar.geodds.Point // se importo aca no me anda lo de bulid.gradle.kts

//import io.kotest.matchers.shouldNotBe

class UsuarioSpec : DescribeSpec({
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

    val direccionLocalCercano = Direccion(ubicacion = Point(-34.52564, -58.51289))
    val direccionLocalLejano = Direccion(ubicacion = Point(-34.577711, -58.52682))
    val localCercano = Local("Local Cercano", direccion = direccionLocalCercano)
    val localLejano = Local("Local Lejano", direccion = direccionLocalLejano)

    val milasConFritas = Plato(
        valorBase = 500.0,
        descripcion = "Clasico",
        local = localCercano,
        fechaDeCreacion = LocalDate.of(2025, 3, 5),
        ingredientes = mutableListOf(aceite, carne)
    )

    val carneConPapas = Plato(
        valorBase = 700.0,
        descripcion = "Clasico",
        local = localLejano,
        fechaDeCreacion = LocalDate.of(2025, 3, 5),
        ingredientes = mutableListOf(papa, carne)
    )

    describe("Tests de Usuario") {
        it("Confirmamos que la edad se calcula bien") {
            val usuario = Usuario(fechaNacimiento = LocalDate.now().minusYears(15))
            usuario.edad() shouldBe (15)
        }

        it("No se permite añadir ingrediente favorito que ya es a evitar") {
            val usuario = Usuario()
            usuario.agregarEvitar(papa)
            val excepcion = shouldThrow<PerteneceAotraListaException> {
                usuario.agregarPreferido(papa)
            }

            excepcion.message shouldBe "papa ya es un ingrediente a evitar"
            usuario.esIngredientePreferido(papa) shouldBe false // no deberia haberse agregado
        }

        it("No se permite añadir ingrediente a evitar que ya es preferido") {
            val usuario = Usuario()
            usuario.agregarPreferido(papa)
            val excepcion = shouldThrow<PerteneceAotraListaException> {
                usuario.agregarEvitar(papa)
            }

            excepcion.message shouldBe "papa ya es un ingrediente preferido"
            usuario.esIngredienteAEvitar(papa) shouldBe false // no deberia haberse agregado
        }

        it("Usuario cercano a local") {
            val usuario = Usuario(direccion = Direccion(ubicacion = Point(-34.52623, -58.52255)))
            usuario.esCercano(localCercano) shouldBe true // Verificamos que está cerca
        }

        it("Confirmar que usuario es lejano a local") {
            val usuario = Usuario()

            usuario.esCercano(localLejano) shouldBe false // Verificamos que está lejos (> 5 km)
        }

        it("Confirmar un pedido y puntuar un local lejano") {
            // Arrange
            val usuario = Usuario()
            val localAPuntuar = Local("Local A Puntuar", direccion = direccionLocalLejano)
            val pedidolejano = Pedido(local = localAPuntuar, usuario = usuario)

            // Act
            usuario.confirmarPedido(pedidolejano)
            usuario.puntuarLocal(pedidolejano.local, Calificacion(4,"")) // Confirmamos pedido y puntuamos

            // Assert
            localAPuntuar.promedioPuntuacion() shouldBe 4.0
        }

        it("Puntuar un local que no ha sido confirmado en los últimos 7 días") {
            val usuario = Usuario()
            val localAPuntuar = Local("Local A Puntuar", direccion = direccionLocalLejano)

            // le pongo a mano un local "confirmarPedido(localLejano)"
            usuario.localesAPuntuar[localAPuntuar] = LocalDate.now().minusDays(10) // se simula 10 dias

            val excepcion = shouldThrow<RuntimeException> {
                usuario.puntuarLocal(localAPuntuar, Calificacion(3,""))
            }

            excepcion.message shouldBe "No se puede puntuar el local"

            localAPuntuar.promedioPuntuacion() shouldBe 0
        }
    }

    describe("Un usuario NO puede pedir un pedido si:") {
        it("Un usuario Vegano intenta pedir un plato con ingredientes de origen animal") {
            // Arrange
            val usuario = Usuario(tipoDeCliente = Vegano)

            // Assert
            usuario.puedePedir(milasConFritas) shouldBe false
        }

        it("Un usuario Exquisito intenta pedir un plato que no es de autor") {
            // Arrange
            val usuario = Usuario(tipoDeCliente = Exquisito)

            // Assert
            usuario.puedePedir(milasConFritas) shouldBe false
        }

        it("Un usuario Conservador intenta pedir un plato que no tiene todos sus ingredientes preferidos") {
            // Arrange
            val usuario = Usuario(tipoDeCliente = Conservador)

            // Act
            usuario.agregarPreferido(carne)

            // Assert
            usuario.puedePedir(milasConFritas) shouldBe false
        }

        it("Un usuario Fiel intenta pedir en un local que no está en su lista de favoritos") {
            // Arrange
            val tipoUsuario = Fieles()
            val usuario = Usuario(tipoDeCliente = tipoUsuario)
            val localPreferido = Local()
            //val localNoPreferido = Local()

            // Act
            tipoUsuario.agregarLocalFavorito(localPreferido)

            // Assert
            usuario.puedePedir(milasConFritas) shouldBe false
        }

        it("Un usuario Consumista intenta pedir un plato cuya descripción no tiene ninguna de sus frases favoritas") {
            // Arrange
            val tipoUsuario = Consumista()
            val usuario = Usuario(tipoDeCliente = tipoUsuario)

            // Act
            tipoUsuario.agregarFrasesFavoritas("nutritivo")

            // Assert
            usuario.puedePedir(milasConFritas) shouldBe false
        }

        it("Un usuario Impaciente intenta pedir en un local lejano") {
            // Arrange
            val usuario = Usuario(tipoDeCliente = Impaciente)

            // Assert
            usuario.puedePedir(milasConFritas) shouldBe false
            // NOTA: si queres que de true cuando el local es cercano, acuérdate de ponerle la direccion al usuario :)
        }

    }

    describe("test de parte dos del tp para usuario") { // ya sé que es malo el nombre...

        it("Usuario generalista puede pedir si se cumple lo basico") {
            // Arrange
            val usuario = Usuario(tipoDeCliente = Generalista)

            // Assert
            usuario.puedePedir(milasConFritas) shouldBe true
            // Act
            usuario.agregarEvitar(carne)
            // Assert
            //usuario.puedePedir(milasConFritas) shouldBe false
        }

        it("debería permitir si todos los criterios permiten y no si uno no se cumple") {
            // Arrange
            val criterioFiel = Fieles()
            criterioFiel.agregarLocalFavorito(localCercano) // Act
            // Arrange
            val fielImpaciente = Combinado(mutableSetOf(criterioFiel, Impaciente))
            val usuario =
                Usuario(tipoDeCliente = fielImpaciente, direccion = Direccion(ubicacion = Point(-34.52623, -58.52255)))

            // Assert
            usuario.puedePedir(milasConFritas) shouldBe true // milasConFritas es de localCercano

            criterioFiel.agregarLocalFavorito(localLejano) // Act
            usuario.puedePedir(carneConPapas) shouldBe false // milasConFritas es de localLejano
            // por más que sea favorito y se cumple fiel, está lejos y no se cumple impaciente
        }


        it("debería actuar como Exquisito si la edad es par") {
            val fechaNacimientoPar = LocalDate.now().minusYears(30) // edad siempre par
            val usuarioExquisito = Usuario(
                tipoDeCliente = CambianteSegunEdad,
                fechaNacimiento = fechaNacimientoPar
            )

            val platoAutor = Plato(esDeAutor = true)
            val platoNoAutor = Plato(esDeAutor = false)

            usuarioExquisito.puedePedir(platoAutor) shouldBe true
            usuarioExquisito.puedePedir(platoNoAutor) shouldBe false
        }

        it("debería actuar como Conservador si la edad es impar") {
            val fechaNacimientoImpar = LocalDate.now().minusYears(31) // edad siempre impar
            val usuarioConservador = Usuario(
                tipoDeCliente = CambianteSegunEdad,
                fechaNacimiento = fechaNacimientoImpar
            )

            usuarioConservador.agregarPreferido(papa)

            usuarioConservador.puedePedir(carneConPapas) shouldBe false

            usuarioConservador.agregarPreferido(carne)
            usuarioConservador.puedePedir(carneConPapas) shouldBe true
        }

    }
})