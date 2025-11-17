import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.errores.SobrepasoPuntuacion
import ar.edu.unsam.algo3.modelo.usuario.Calificacion
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class LocalSpec : DescribeSpec({
    describe("Test de Local") {
        it("Dado un puntaje que no está entre los parámetros 1..5 se lanza una excepcion") {
            // Arrange
            val local = Local()
            val excepcion = shouldThrow<SobrepasoPuntuacion> {
                // Act
                local.agregarPuntuacion(Calificacion(6,""))
            }
            // Assert
            excepcion.message shouldBe ("La puntuación debe estar entre 1 y 5")
        }
        it("Dado un local, se calcula el promedio de las puntuaciones de los usuarios") {
            // Arrange
            val local = Local()
            // Act
            val usuario_1 = local.agregarPuntuacion(Calificacion(1,""))
            val usuario_2 = local.agregarPuntuacion(Calificacion(2,""))
            val usuario_3 = local.agregarPuntuacion(Calificacion(3,""))
            // Assert
            local.promedioPuntuacion() shouldBe 2.0
        }
    }
})