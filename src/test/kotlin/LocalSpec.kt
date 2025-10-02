package ar.edu.unsam.algo2.algoQuePedir

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
                local.agregarPuntuacion(6)
            }
            // Assert
            excepcion.message shouldBe ("La puntuación debe estar entre 1 y 5")
        }
        it("Dado un local, se calcula el promedio de las puntuaciones de los usuarios") {
            // Arrange
            val local = Local()
            // Act
            val usuario_1 = local.agregarPuntuacion(1)
            val usuario_2 = local.agregarPuntuacion(2)
            val usuario_3 = local.agregarPuntuacion(3)
            // Assert
            local.promedioPuntuacion() shouldBe 2.0
        }
    }
})