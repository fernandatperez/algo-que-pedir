import ar.edu.unsam.algo3.servicios.InstanciaActualizador
import ar.edu.unsam.algo3.repositorio.Repositorio
import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.errores.JSONVacioException
import ar.edu.unsam.algo3.errores.ObjetoIDoVerificacionFallaron
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ServiceIngredientesSpec : DescribeSpec({
    describe("Test de service ingredientes") {
        it("Testeo de creacion de ingredientes") {
            val repositorioIngrediente = Repositorio<Ingrediente>()

            val actualizadorIngredientes = InstanciaActualizador(
                mensaje = """[{
                        "nombre": "Leche",
                        "costo": 200.5,
                        "grupo": "LACTEOS",
                        "origenAnimal": true
                        }]""",
                repositorio = repositorioIngrediente
            )

            repositorioIngrediente.coleccion.isEmpty() shouldBe true

            actualizadorIngredientes.actualizarIng()

            repositorioIngrediente.objetoEnColeccion(1) shouldBe true

            val ingredienteCreado = repositorioIngrediente.obtenerObjeto(1)
            ingredienteCreado?.costoMercado shouldBe 200.5
        }

        it("Testeo de actualizacion de ingredientes") {
            val repositorioIngrediente = Repositorio<Ingrediente>()

            val ingrediente = Ingrediente(
                nombre = "Leche",
                costoMercado = 500.5,
                grupoAlimenticio = GrupoAlimenticio.PROTEINAS,
                esOrigenAnimal = false
            )
            repositorioIngrediente.crear(ingrediente)

            val actualizadorIngredientes = InstanciaActualizador(
                mensaje = """[{
                        "id": 1,
                        "nombre": "Leche",
                        "costo": 200.5,
                        "grupo": "LACTEOS",
                        "origenAnimal": true
                        }]""",
                repositorio = repositorioIngrediente
            )

            actualizadorIngredientes.actualizarIng()

            repositorioIngrediente.obtenerObjeto(1)?.costoMercado shouldBe 200.5
            repositorioIngrediente.obtenerObjeto(1)?.grupoAlimenticio shouldBe GrupoAlimenticio.LACTEOS
            repositorioIngrediente.obtenerObjeto(1)?.esOrigenAnimal shouldBe true
        }

        it("Actualizar un repo con un JSON con un ingrediente vacio da error") {
            val repositorioIngrediente = Repositorio<Ingrediente>()

            val actualizadorIngredientes = InstanciaActualizador(
                mensaje = """[{}]""",
                repositorio = repositorioIngrediente
            )

            val exception = shouldThrow<ObjetoIDoVerificacionFallaron> {
                actualizadorIngredientes.actualizarIng()
            }

            exception.message shouldBe "El ID o el objeto ya se encuentran en la coleccion, o bien la verificacion fallo"
        }

        it("Actualizar un repo con un JSON vacio da error") {
            val repositorioIngrediente = Repositorio<Ingrediente>()

            val actualizadorIngredientes = InstanciaActualizador(
                mensaje = """[]""",
                repositorio = repositorioIngrediente
            )

            val exception = shouldThrow<JSONVacioException> {
                actualizadorIngredientes.actualizarIng()
            }

            exception.message shouldBe "El JSON pareciera no tener contenido o hubo un error al tipearlo"
        }
    }
})