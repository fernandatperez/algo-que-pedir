import ar.edu.unsam.algo3.repositorio.Repositorio
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.errores.IdInexistente
import ar.edu.unsam.algo3.errores.ObjetoIDoVerificacionFallaron
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.delivery.Delivery
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.utils.Direccion
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.collections.shouldBeEmpty
import org.uqbar.geodds.Point
import org.uqbar.geodds.Polygon

class RepositorioSpec : DescribeSpec({

//    Instancia cada una de estas variables antes de cada test (it)
    lateinit var local: Local
    lateinit var plato: Plato
    lateinit var plato2: Plato

    beforeTest {
        local = Local(nombre = "Rappi", direccion = Direccion(calle = "Cosme"))
        plato = Plato(nombre = "Fideos con tuco", local = local, descripcion = "Fideulis")
        plato2 = Plato(nombre = "Lasagna", local = local, descripcion = "lasagnita")
    }

    describe("Pruebas rapidas de repositorios") {

        it("Se aniade objetos a la coleccion y se testea su integracion") {
            val repositorio = Repositorio<Plato>()

            repositorio.crear(plato)
//            repositorio.coleccion.forEach{ (id, plato) -> println("$id = ${plato.nombre}") }
            repositorio.obtenerObjeto(1) shouldBe plato
            repositorio.objetoEnColeccion(1) shouldBe true
            repositorio.objetoEnColeccion(plato.id) shouldBe true
            repositorio.objetoEnColeccion(plato2.id) shouldBe false
        }

        it("Eliminando contenido de un repositorio") {
            val repositorio = Repositorio<Plato>()

            repositorio.crear(plato)
            repositorio.crear(plato2)
            repositorio.eliminarDeColeccion(plato2.id)

            repositorio.coleccion.size shouldBe 1
        }

        it("Obtener por ID") {
            val repositorio = Repositorio<Plato>()

            repositorio.crear(plato)
            repositorio.obtenerObjeto(1).shouldBeSameInstanceAs(plato)
        }

        it("Testing del metodo buscarPor()") {
            val repositorioPlatos1 = Repositorio<Plato>()
            val repositorioPlatos2 = Repositorio<Plato>()

            repositorioPlatos1.crear(plato)
            repositorioPlatos2.crear(plato)
            (repositorioPlatos1.coleccion[0] === repositorioPlatos2.coleccion[0]) shouldBe true // Esto, debe/puede pasar?
            // Son el mismo plato en distintos repos, tiene sentido que si el precio cambia, cambie en los 2, pero no se...
        }

        it("Reemplazar un plato por un plato nuevo") {
            // Arrange
            val repositorio = Repositorio<Plato>()

            // Act
            repositorio.crear(plato) // se crea con el ID: 1
            plato2.id = 1 // Esto es raro, pero es con fines de testear la funcion actualizar
            repositorio.actualizar(plato2) // reemplaza objeto y verifica que sea un ID valido
            // Assert
            repositorio.obtenerObjeto(1) shouldBe plato2
        }

        it("Se lanza una excepción si intento actualizar un plato con un ID inexistente") {
            // Arrange
            val repositorio = Repositorio<Plato>()
            // Arrange
            val excepcion = shouldThrow<IdInexistente> {
                // Act
                // No se puede actualizar por que nunca se creo, no existe en la coleccion.
                repositorio.actualizar(plato)
            }
            // Assert
            excepcion.message shouldBe "El ID = ${plato.id} no existe en la coleccion o no hay ningun objeto asociado a el"
        }

        it("En cualquier caso, devuelve una lista vacia si no coincide con el criterio de busqueda") {
            val repoIng = Repositorio<Ingrediente>()
            val repoPlato = Repositorio<Plato>()
            val repoUsuario = Repositorio<Usuario>()
            val repoLocal = Repositorio<Local>()
            val repoDelivery = Repositorio<Delivery>()
            val listaPuntos = mutableListOf(
                Point(-34.52168415387585, -58.513714921128056),
                Point(-34.53030621937303, -58.5339238418408),
                Point(-34.549925523494096, -58.523963730918084),
                Point(-34.53863012525849, -58.4997130260628)
            )
            val ingrediente = Ingrediente(nombre = "Curcuma")
            val usuario = Usuario(nombre = "Mateo", apellido = "mamon", username = "Matute123", mailPrincipal = "matute@hotmail.com")
            val delivery = Delivery(username = "RicardoYa", zonaDeTrabajo = Polygon(listaPuntos))

            repoIng.crear(ingrediente)
            repoPlato.crear(plato)
            repoUsuario.crear(usuario)
            repoLocal.crear(local)
            repoDelivery.crear(delivery)

            // Coincide nombre exacto
            repoIng.buscar("Curcu").isEmpty() shouldBe true
            // Parcialmente con varias cosas, exactamente con la calle
            repoPlato.buscar("Beranjena").isEmpty() shouldBe true
            // Parcialmente con nombre o apellido, exacto con username
            repoUsuario.buscar("Matu").isEmpty() shouldBe true
            // Parcial con nombre o exacto con calle
            repoLocal.buscar("Lacalle").isEmpty() shouldBe true
            // Coincidir con comiendo del user
            repoDelivery.buscar("RicardiY").isEmpty() shouldBe true
        }

        it("Devuelve el ingrediente si coincide exactamente con el nombre buscado") {
            // Arrange
            val repositorioIngrediente = Repositorio<Ingrediente>()
            val ingrediente = Ingrediente(nombre = "zanahoria")
            // Act
            repositorioIngrediente.crear(ingrediente)
            // Assert
            repositorioIngrediente.buscar("zanahoria")[0].nombre shouldBe "zanahoria"
            repositorioIngrediente.buscar("zanah").shouldBeEmpty()
        }

        it("Devuelve el plato si coincide con algunos criterios parciales y totales") {
            // Arrange
            val repositorioPlatos = Repositorio<Plato>()
            // Act
            repositorioPlatos.crear(plato)
            // Assert
            repositorioPlatos.buscar("fi")[0].descripcion shouldBe "Fideulis"
            repositorioPlatos.buscar("fide")[0].nombre shouldBe "Fideos con tuco"
            repositorioPlatos.buscar("Rapp")[0].local.nombre shouldBe "Rappi"
            repositorioPlatos.buscar("Cosme")[0].local.direccion.calle shouldBe "Cosme"
        }

        it("Si el objeto no esta bien creado, no se agrega al repositorio") {
            val repositorioLocales = Repositorio<Local>()
            val localNoVerifica = Local()

            val excepcion = shouldThrow<ObjetoIDoVerificacionFallaron> {
                repositorioLocales.crear(localNoVerifica)
            }

            excepcion.message shouldBe "El ID o el objeto ya se encuentran en la coleccion, o bien la verificacion fallo"
        }
    }
})