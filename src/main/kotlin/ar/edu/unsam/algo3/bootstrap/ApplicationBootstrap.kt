package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.repositorio.Repositorio
import ar.edu.unsam.algo3.repositorio.RepositorioPedidos
import ar.edu.unsam.algo3.repositorio.RepositorioIngredientes
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.uqbar.geodds.Point

@Service
class ApplicationBootstrap(
    val repositorioPedidos: RepositorioPedidos,
    val repositorioIngredientes: RepositorioIngredientes
) : InitializingBean {

    private var local = Local(
        nombre = "un Local",
        direccion = Direccion(calle = "maipu"),
        mediosDePago = mutableSetOf(Pago.TRANSFERENCIA_BANCARIA, Pago.EFECTIVO, Pago.QR)
    )

    private lateinit var sofiamiller: Usuario
    private lateinit var ricardofort: Usuario
    private lateinit var alexcaniggia: Usuario

    private lateinit var carnederenacuajo: Ingrediente
    private lateinit var quesocheddar: Ingrediente
    private lateinit var lechuga: Ingrediente
    private lateinit var tomate: Ingrediente
    private lateinit var huevo: Ingrediente

    private lateinit var hamburguesa: Plato
    private lateinit var pizza: Plato
    private lateinit var ensalada: Plato

    fun crearClientes() {
        repositorioClientes.limpiarColeccion()
        sofiamiller = Usuario(
            nombre = "Sofía",
            apellido = "Miller",
            username = "smiller2006",
            mailPrincipal = "sofiamiller@gmail.com",
            direccion = Direccion(
                calle = "Av siempre viva",
                altura = 555,
                ubicacion = Point(40.7128,-74.006)
            ),
        )
        ricardofort = Usuario(
            nombre = "Ricardo",
            apellido = "Fort",
            username = "rickyricon",
            mailPrincipal = "rickyfort@gmail.com",
            direccion = Direccion(
                calle = "Maiame",
                altura = 354,
                ubicacion = Point(25.77427,-80.19366)
            ),
        )
        alexcaniggia = Usuario(
            nombre = "Alex",
            apellido = "Caniggia",
            username = "fuerabarat",
            mailPrincipal = "fuerabarat@gmail.com",
            direccion = Direccion(
                calle = "Roma, Italia",
                altura = 100,
                ubicacion = Point(41.89193,12.51133)
            ),
        )
        repositorioClientes.apply {
            this.crear(sofiamiller)
            this.crear(ricardofort)
            this.crear(alexcaniggia)
        }
    }

    fun crearIngredientes() {
        repositorioIngredientes.limpiarColeccion()
        carnederenacuajo = Ingrediente(
            nombre = "Carne de Renacuajo",
            costoMercado = 0.7,
            grupoAlimenticio = GrupoAlimenticio.PROTEINAS,
            esOrigenAnimal = true
        )
        quesocheddar = Ingrediente(
            nombre = "Queso Cheddar",
            costoMercado = 0.5,
            grupoAlimenticio = GrupoAlimenticio.LACTEOS,
            esOrigenAnimal = true
        )
        lechuga = Ingrediente(
            nombre = "Lechuga",
            costoMercado = 0.4,
            grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS,
            esOrigenAnimal = false
        )
        tomate = Ingrediente(
            nombre = "Tomate",
            costoMercado = 0.2,
            grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS,
            esOrigenAnimal = false
        )
        huevo = Ingrediente(
            nombre = "Huevo",
            costoMercado = 50.0,
            esOrigenAnimal = true,
            grupoAlimenticio = GrupoAlimenticio.PROTEINAS
        )
        repositorioIngredientes.apply {
            crear(carnederenacuajo)
            crear(quesocheddar)
            crear(lechuga)
            crear(tomate)
            crear(huevo)
        }
    }

    fun crearPlatos() {
        repositorioPlatos.limpiarColeccion()
        hamburguesa = Plato(
            nombre = "Hamburguesa con queso",
            descripcion = "Hamburguesa con queso acompañada de papas fritas y bebida",
            valorBase = 9.99,
            urldeImagen = "/src/lib/assets/img/hamburguesa2.jpg",
            local = local,
            ingredientes = mutableListOf(carnederenacuajo,quesocheddar,lechuga)
        )
        pizza = Plato(
            nombre = "Pizza Margarita",
            descripcion = "De muzzarella con tomate y albahaca",
            urldeImagen = "/src/lib/assets/img/pizza.png",
            valorBase = 11.75,
            local = local,
            ingredientes = mutableListOf(quesocheddar,lechuga,tomate)

        )
        ensalada = Plato(
            nombre = "Ensalada clásica",
            descripcion = "De hojas frescas y vegetales organicos",
            valorBase = 7.5,
            urldeImagen = "/src/lib/assets/img/ensalada.png",
            esDeAutor = true,
            local = local,
            ingredientes = mutableListOf(lechuga,tomate)
        )
        repositorioPlatos.apply {
            crear(hamburguesa)
            crear(pizza)
            crear(ensalada)
        }
    }

    fun crearPedidos() {
        repositorioPedidos.clearInit()
        repositorioPedidos.apply {
            crear(
                usuario = sofiamiller,
                local = local,
                platos = mutableListOf(hamburguesa, pizza, ensalada),
                medioDePago = Pago.QR,
                estado = Estado.PENDIENTE
            )
            crear(
                usuario = ricardofort,
                local = local,
                platos = mutableListOf(hamburguesa, ensalada),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.PENDIENTE
            )
            crear(
                usuario = alexcaniggia,
                local = local,
                platos = mutableListOf(pizza, hamburguesa),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.PREPARADO
            )
        }
    }

    override fun afterPropertiesSet() {
        this.crearClientes()
        this.crearIngredientes()
        this.crearPlatos()
        this.crearPedidos()
    }
}

val repositorioClientes = Repositorio<Usuario>()
val repositorioPlatos = Repositorio<Plato>()