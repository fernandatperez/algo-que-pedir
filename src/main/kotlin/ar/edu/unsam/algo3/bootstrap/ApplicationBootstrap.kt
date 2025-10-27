package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.repositorio.*
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.uqbar.geodds.Point
import java.time.LocalDate

@Service
class ApplicationBootstrap(
    val repositorioLocal: RepositorioLocal,
    val repositorioPedidos: RepositorioPedido,
    val repositorioIngredientes: RepositorioIngrediente,
    val repositorioClientes: RepositorioCliente,
    val repositorioPlatos: RepositorioPlato
) : InitializingBean {

    private lateinit var localInicial: Local
    private lateinit var mcdonals: Local
    private lateinit var sushipop: Local
    private lateinit var migusto: Local
    private lateinit var grido: Local
    private lateinit var lomitos: Local


//    private var local = Local(
//        nombre = "un Local",
//        direccion = Direccion(calle = "maipu"),
//        mediosDePago = mutableSetOf(Pago.TRANSFERENCIA_BANCARIA, Pago.EFECTIVO, Pago.QR)
//    )

    private fun crearLocalInicial() {
        if (repositorioLocal.objetosDeRepositorio().isEmpty()) {

            localInicial = Local().apply {
                id = 1
                nombre = "Carlo's Bake Shop"
                email = "jorge@hotmail.com"
                password = "123"
                url = "https://networthbro.com/wp-content/uploads/2019/07/buddy-valastro-networth-salary.jpg"
                regalias = 0.03
                porcentajeAcordado = 0.06
                mediosDePago = mutableSetOf(Pago.EFECTIVO)
                direccion = Direccion(
                    calle = "Av. Siempre Viva",
                    altura = 123,
                    ubicacion = Point(-34.6162132380519, -58.390811751881536)  // lat, long
                )

            }

            mcdonals = Local().apply {
                id = 121
                nombre = "McDonald's"
                email = "mcdonals@gmail.com"
                password = "123"
                url = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/15/2d/22/ba/it-s-mcdonalds-what-else.jpg?w=900&h=-1&s=1"
                regalias = 0.05
                porcentajeAcordado = 0.1
                mediosDePago = mutableSetOf(Pago.EFECTIVO, Pago.QR)
                direccion = Direccion(
                    calle = "Av. Corrientes",
                    altura = 3500,
                    ubicacion = Point(-34.60315598140907, -58.41140804487885)
                )
            }

            sushipop = Local().apply {
                id = 3
                nombre = "SushiPop"
                email = "sushipop@gmail.com"
                password = "123"
                url = "https://cdn.pixabay.com/photo/2020/04/04/15/07/sushi-5002639_1280.jpg"
                regalias = 0.05
                porcentajeAcordado = 0.1
                mediosDePago = mutableSetOf(Pago.EFECTIVO)
                direccion = Direccion(
                    calle = "Lacroze",
                    altura = 5006,
                    ubicacion = Point(-34.54721730459346, -58.55472453690146)
                )
            }

            grido = Local().apply {
                id = 4
                nombre = "Grido"
                email = "grido@gmail.com"
                password = "123"
                url = "https://infomercado.pe/wp-content/uploads/2023/04/Grido.jpg"
                regalias = 0.4
                porcentajeAcordado = 0.9
                mediosDePago = mutableSetOf(Pago.QR)
                direccion = Direccion(
                    calle = "Ayacucho",
                    altura = 3454,
                    ubicacion = Point(-34.558853, -58.548947)
                )
            }

            migusto = Local().apply {
                id = 5
                nombre = "Mi Gusto"
                email = "migusto@gmail.com"
                password = "123"
                url = "https://lh5.googleusercontent.com/p/AF1QipNFS6K8G6XeSvyV0-DKBoQkFV1ua37A_p26YU5g=w408-h306-k-no"
                regalias = 0.5
                porcentajeAcordado = 0.5
                mediosDePago = mutableSetOf(Pago.QR)
                direccion = Direccion(
                    calle = "Gral San Martin",
                    altura = 1904,
                    ubicacion = Point(-34.5235380087158, -58.48952314781408)
                )
            }

            lomitos = Local().apply {
                id = 6
                nombre = "Lomito´s"
                email = "lomitos@gmail.com"
                password = "123"
                url = "https://media-cdn.tripadvisor.com/media/photo-s/07/0e/c7/a6/betos-lomitos.jpg"
                regalias = 0.5
                porcentajeAcordado = 0.5
                mediosDePago = mutableSetOf(Pago.TRANSFERENCIA_BANCARIA)
                direccion = Direccion(
                    calle = "Presidente Illia",
                    altura = 3170,
                    ubicacion = Point(-34.5232471721764, -58.703041085973894)
                )
            }



            repositorioLocal.crear(localInicial)
            repositorioLocal.crear(mcdonals)
            repositorioLocal.crear(grido)
            repositorioLocal.crear(migusto)
            repositorioLocal.crear(lomitos)
            repositorioLocal.crear(sushipop)

//            repositorioLocal.crear(local)

//            println("""
//                Local inicial creado exitosamente:
//                 Nombre: ${localInicial.nombre}
//                 Dirección: ${localInicial.direccion.calle} ${localInicial.direccion.altura}
//                 Ubicación: (${localInicial.direccion.ubicacion.x}, ${localInicial.direccion.ubicacion.y})
//                 URL: ${localInicial.url}
//                 Comisiones: App ${localInicial.regalias}% | Autor ${localInicial.porcentajeAcordado}%
//            """.trimIndent())

        } else {
            val localExistente = repositorioLocal.obtenerObjeto(1)
//            println(" Local existente: ${localExistente.nombre}")
        }
    }

    private lateinit var sofiamiller: Usuario
    private lateinit var ricardofort: Usuario
    private lateinit var alexcaniggia: Usuario
    private lateinit var buzz: Usuario
    private lateinit var locomotora: Usuario

    private lateinit var carnederenacuajo: Ingrediente
    private lateinit var quesocheddar: Ingrediente
    private lateinit var lechuga: Ingrediente
    private lateinit var tomate: Ingrediente
    private lateinit var huevo: Ingrediente

    private lateinit var hamburguesa: Plato
    private lateinit var pizza: Plato
    private lateinit var ensalada: Plato
    private lateinit var salmon: Plato
    private lateinit var spaghettis: Plato
    private lateinit var bigMac: Plato
    private lateinit var alitas: Plato


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
        buzz = Usuario(
            nombre = "Buzz",
            apellido = "Lightyear",
            username = "alinfinito",
            mailPrincipal = "woodyteamo@gmail.com",
            direccion = Direccion(
                calle = "Morph",
                altura = 256,
                ubicacion = Point(41.89193,12.51133)
            ),
        )
        locomotora = Usuario(
            nombre = "La Locomotora",
            apellido = "RIP",
            username = "noterindasss",
            mailPrincipal = "bostadevaca@gmail.com",
            direccion = Direccion(
                calle = "Rosario, Arg",
                altura = 111,
                ubicacion = Point(41.89193,12.51133)
            ),
        )
        repositorioClientes.apply {
            this.crear(sofiamiller)
            this.crear(ricardofort)
            this.crear(alexcaniggia)
            this.crear(buzz)
            this.crear(locomotora)
        }
    }

    fun crearIngredientes() {
        repositorioIngredientes.limpiarColeccion()
        carnederenacuajo = Ingrediente(
            nombre = "Carne de Renacuajo",
            costoMercado = 0.7,
            grupoAlimenticio = GrupoAlimenticio.PROTEINAS,
            esOrigenAnimal = true,
        )
        quesocheddar = Ingrediente(
            nombre = "Queso Cheddar",
            costoMercado = 0.5,
            grupoAlimenticio = GrupoAlimenticio.LACTEOS,
            esOrigenAnimal = true,
        )
        lechuga = Ingrediente(
            nombre = "Lechuga",
            costoMercado = 0.4,
            grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS,
            esOrigenAnimal = false,
        )
        tomate = Ingrediente(
            nombre = "Tomate",
            costoMercado = 0.2,
            grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS,
            esOrigenAnimal = false,
        )
        huevo = Ingrediente(
            nombre = "Huevo",
            costoMercado = 50.0,
            esOrigenAnimal = true,
            grupoAlimenticio = GrupoAlimenticio.PROTEINAS,
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
            local = localInicial,
            fechaDeCreacion = LocalDate.now().minusDays(31),
            ingredientes = mutableListOf(tomate, huevo)
        )
        pizza = Plato(
            nombre = "Pizza Margarita",
            descripcion = "De muzzarella con tomate y albahaca",
            urldeImagen = "/src/lib/assets/img/pizza.png",
            valorBase = 11.75,
            local = localInicial,
            fechaDeCreacion = LocalDate.now().minusDays(31),
            ingredientes = mutableListOf(tomate)

        )
        ensalada = Plato(
            nombre = "Ensalada clásica",
            descripcion = "De hojas frescas y vegetales organicos",
            valorBase = 7.5,
            urldeImagen = "/src/lib/assets/img/ensalada.png",
            esDeAutor = true,
            fechaDeCreacion = LocalDate.now().minusDays(31),
            local = mcdonals,
            ingredientes = mutableListOf(lechuga,quesocheddar)
        )
        salmon = Plato(
            nombre = "Salmón grillado",
            descripcion = "Filete de salmón grillado con guarnición de verduras asadas",
            valorBase = 14.5,
            urldeImagen = "/src/lib/assets/img/salmon.png",
            fechaDeCreacion = LocalDate.now().minusDays(31),
            local = mcdonals,
            ingredientes = mutableListOf(carnederenacuajo, lechuga)
        )

        spaghettis = Plato(
            nombre = "Spaghettis al pesto",
            descripcion = "Pasta fresca con salsa de albahaca y queso rallado",
            valorBase = 12.0,
            urldeImagen = "/src/lib/assets/img/spagettis.png",
            fechaDeCreacion = LocalDate.now().minusDays(21),
            local = mcdonals,
            ingredientes = mutableListOf(quesocheddar)
        )

        bigMac = Plato(
            nombre = "Big Mac",
            descripcion = "Doble carne, queso cheddar, lechuga, pepinos y salsa especial",
            valorBase = 10.99,
            urldeImagen = "/src/lib/assets/img/hamburguesa.png",
            local = mcdonals,
            fechaDeCreacion = LocalDate.now().minusDays(31),
            ingredientes = mutableListOf(carnederenacuajo, lechuga, quesocheddar)
        )

        alitas = Plato(
            nombre = "Alitas BBQ",
            descripcion = "Alitas de pollo bañadas en salsa barbacoa, acompañadas de papas fritas",
            valorBase = 9.25,
            urldeImagen = "/src/lib/assets/img/alitas.png",
            local = mcdonals,
            fechaDeCreacion = LocalDate.now().minusDays(31),
            ingredientes = mutableListOf(carnederenacuajo)
        )

        repositorioPlatos.apply {
            crear(hamburguesa)
            crear(pizza)
            crear(ensalada)
            crear(salmon)
            crear(spaghettis)
            crear(bigMac)
            crear(alitas)
        }
    }

    fun crearPedidos() {
        repositorioPedidos.clearInit()
        repositorioPedidos.apply {
            crear(
                usuario = sofiamiller,
                local = mcdonals, // a pedido de catt ;)
                platos = mutableListOf(bigMac, ensalada, alitas),
                medioDePago = Pago.QR,
                estado = Estado.PENDIENTE
            )
            crear(
                usuario = ricardofort,
                local = localInicial,
                platos = mutableListOf(hamburguesa, pizza),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.PENDIENTE
            )
            crear(
                usuario = alexcaniggia,
                local = localInicial,
                platos = mutableListOf(pizza),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.PREPARADO
            )
            crear(
                usuario = buzz,
                local = localInicial,
                platos = mutableListOf(hamburguesa),
                medioDePago = Pago.QR,
                estado = Estado.ENTREGADO
            )
            crear(
                usuario = locomotora,
                local = localInicial,
                platos = mutableListOf(hamburguesa),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.CANCELADO
            )
            crear(
                usuario = sofiamiller,
                local = localInicial,
                platos = mutableListOf(hamburguesa),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.ENTREGADO
            )
        }
    }

    override fun afterPropertiesSet() {
        this.crearLocalInicial()
        this.crearClientes()
        this.crearIngredientes()
        this.crearPlatos()
        this.crearPedidos()
    }
}