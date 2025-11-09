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
    private lateinit var restauranteItaliano: Local

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
                    ubicacion = Point(-34.6162132380519, -58.390811751881536)
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

            restauranteItaliano = Local().apply {
                id = 7
                nombre = "Restaurante Italiano"
                email = "italiano@gmail.com"
                password = "123"
                url = "https://images.unsplash.com/photo-1534650075489-3baecec1e8b1?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1170"
                regalias = 0.05
                porcentajeAcordado = 0.1
                mediosDePago = mutableSetOf(Pago.EFECTIVO, Pago.QR, Pago.TRANSFERENCIA_BANCARIA)
                direccion = Direccion(
                    calle = "Via Giovan Battista",
                    altura = 2500,
                    ubicacion = Point(-34.59823, -58.39451)
                )
                puntuaciones = mutableListOf(4,5,4,5,5,4,3,5,4)
            }

            repositorioLocal.crear(localInicial)
            repositorioLocal.crear(mcdonals)
            repositorioLocal.crear(grido)
            repositorioLocal.crear(migusto)
            repositorioLocal.crear(lomitos)
            repositorioLocal.crear(sushipop)
            repositorioLocal.crear(restauranteItaliano)

        } else {
            val localExistente = repositorioLocal.obtenerObjeto(1)
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
    private lateinit var mozzarella: Ingrediente
    private lateinit var albahaca: Ingrediente
    private lateinit var pepperoni: Ingrediente
    private lateinit var bacon: Ingrediente
    private lateinit var crema: Ingrediente
    private lateinit var parmesano: Ingrediente
    private lateinit var pasta: Ingrediente
    private lateinit var pesto: Ingrediente

    private lateinit var hamburguesa: Plato
    private lateinit var pizza: Plato
    private lateinit var ensalada: Plato
    private lateinit var salmon: Plato
    private lateinit var spaghettis: Plato
    private lateinit var bigMac: Plato
    private lateinit var alitas: Plato
    private lateinit var pizzaMargherita: Plato
    private lateinit var pizzaPepperoni: Plato
    private lateinit var spaghettiCarbonara: Plato
    private lateinit var fettuccineAlfredo: Plato
    private lateinit var lasagnePortofino: Plato

    fun crearClientes() {
        repositorioClientes.limpiarColeccion()
        sofiamiller = Usuario(
            nombre = "Sofía",
            apellido = "Miller",
            username = "smiller2006",
            mailPrincipal = "sofiamiller@gmail.com",
            password = "123",
            direccion = Direccion(
                calle = "Av siempre viva",
                altura = 555,
                ubicacion = Point(40.7128,-74.006)
            ),
        ).apply{
            this.agregarPreferido(tomate)
            this.agregarEvitar(huevo)
        }
        ricardofort = Usuario(
            nombre = "Ricardo",
            apellido = "Fort",
            username = "rickyricon",
            mailPrincipal = "rickyfort@gmail.com",
            password = "123",
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
            password = "123",
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
            password = "123",
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
            password = "123",
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
        mozzarella = Ingrediente(
            nombre = "Mozzarella",
            costoMercado = 0.6,
            grupoAlimenticio = GrupoAlimenticio.LACTEOS,
            esOrigenAnimal = true,
        )
        albahaca = Ingrediente(
            nombre = "Albahaca",
            costoMercado = 0.3,
            grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS,
            esOrigenAnimal = false,
        )
        pepperoni = Ingrediente(
            nombre = "Pepperoni",
            costoMercado = 0.8,
            grupoAlimenticio = GrupoAlimenticio.PROTEINAS,
            esOrigenAnimal = true,
        )
        bacon = Ingrediente(
            nombre = "Bacon",
            costoMercado = 0.9,
            grupoAlimenticio = GrupoAlimenticio.PROTEINAS,
            esOrigenAnimal = true,
        )
        crema = Ingrediente(
            nombre = "Crema",
            costoMercado = 0.4,
            grupoAlimenticio = GrupoAlimenticio.LACTEOS,
            esOrigenAnimal = true,
        )
        parmesano = Ingrediente(
            nombre = "Queso Parmesano",
            costoMercado = 0.7,
            grupoAlimenticio = GrupoAlimenticio.LACTEOS,
            esOrigenAnimal = true,
        )
        pasta = Ingrediente(
            nombre = "Pasta",
            costoMercado = 0.5,
            grupoAlimenticio = GrupoAlimenticio.CEREALES_Y_TUBERCULOS,
            esOrigenAnimal = false,
        )
        pesto = Ingrediente(
            nombre = "Pesto",
            costoMercado = 0.6,
            grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS,
            esOrigenAnimal = false,
        )

        repositorioIngredientes.apply {
            crear(carnederenacuajo)
            crear(quesocheddar)
            crear(lechuga)
            crear(tomate)
            crear(huevo)
            crear(mozzarella)
            crear(albahaca)
            crear(pepperoni)
            crear(bacon)
            crear(crema)
            crear(parmesano)
            crear(pasta)
            crear(pesto)
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

        // Platos del Restaurante Italiano
        pizzaMargherita = Plato(
            nombre = "Pizza Margherita",
            descripcion = "Classic pizza with tomato sauce, mozzarella, and basil",
            valorBase = 12.99,
            urldeImagen = "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1169",
            local = restauranteItaliano,
            fechaDeCreacion = LocalDate.now().minusDays(15),
            ingredientes = mutableListOf(tomate, mozzarella, albahaca)
        )
        pizzaPepperoni = Plato(
            nombre = "Pizza Pepperoni",
            descripcion = "Pizza with tomato sauce, mozzarella, and pepperoni",
            valorBase = 13.99,
            urldeImagen = "https://images.unsplash.com/photo-1605478371310-a9f1e96b4ff4?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1170",
            local = restauranteItaliano,
            fechaDeCreacion = LocalDate.now().minusDays(15),
            ingredientes = mutableListOf(tomate, mozzarella, pepperoni)
        )
        spaghettiCarbonara = Plato(
            nombre = "Spaghetti Carbonara",
            descripcion = "Spaghetti with creamy sauce, bacon, and parmesan cheese",
            valorBase = 14.99,
            urldeImagen = "https://assets.tmecosys.com/image/upload/t_web_rdp_recipe_584x480_1_5x/img/recipe/ras/Assets/0346a29a89ef229b1a0ff9697184f944/Derivates/cb5051204f4a4525c8b013c16418ae2904e737b7.jpg",
            local = restauranteItaliano,
            fechaDeCreacion = LocalDate.now().minusDays(10),
            ingredientes = mutableListOf(pasta, bacon, crema, parmesano)
        )
        fettuccineAlfredo = Plato(
            nombre = "Fettuccine Alfredo",
            descripcion = "Fettuccine with creamy Alfredo sauce",
            valorBase = 13.99,
            urldeImagen = "https://www.modernhoney.com/wp-content/uploads/2018/08/Fettuccine-Alfredo-Recipe-1-500x500.jpg",
            local = restauranteItaliano,
            fechaDeCreacion = LocalDate.now().minusDays(12),
            ingredientes = mutableListOf(pasta, crema, parmesano)
        )
        lasagnePortofino = Plato(
            nombre = "Lasagne alla Portofino",
            descripcion = "Lasagne with creamy besciamella and pesto genovese",
            valorBase = 16.99,
            urldeImagen = "https://images.squarespace-cdn.com/content/v1/62422bb659ddd37045237686/0006ed59-9ec5-4858-b544-efb56b56d49b/8fe074b8-c1a4-4654-b6a4-3db060e8284c_4030x3024.jpeg",
            local = restauranteItaliano,
            fechaDeCreacion = LocalDate.now().minusDays(8),
            ingredientes = mutableListOf(pasta, crema, pesto, mozzarella)
        )

        repositorioPlatos.apply {
            crear(hamburguesa)
            crear(pizza)
            crear(ensalada)
            crear(salmon)
            crear(spaghettis)
            crear(bigMac)
            crear(alitas)
            crear(pizzaMargherita)
            crear(pizzaPepperoni)
            crear(spaghettiCarbonara)
            crear(fettuccineAlfredo)
            crear(lasagnePortofino)
        }
    }

    fun crearPedidos() {
        repositorioPedidos.clearInit()
        repositorioPedidos.apply {
//            Sofia Miller
//            ---
            crear(
                usuario = sofiamiller,
                local = mcdonals,
                platos = mutableListOf(bigMac, ensalada, alitas),
                medioDePago = Pago.QR,
                estado = Estado.PENDIENTE,
                fechaCreacion = LocalDate.of(2025, 10, 28)
            ) .apply {
                this.estado = Estado.CONFIRMADO
                sofiamiller.registrarLocalParaPuntuar(this)
            }

            crear(
                usuario = sofiamiller,
                local = localInicial,
                platos = mutableListOf(hamburguesa),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.ENTREGADO
            )
            crear(
                usuario = sofiamiller,
                local = grido,
                platos = mutableListOf(hamburguesa, alitas, pizza, ensalada, bigMac),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.PENDIENTE
            )
            crear(
                usuario = sofiamiller,
                local = restauranteItaliano,
                platos = mutableListOf(pizzaMargherita, spaghettiCarbonara),
                medioDePago = Pago.QR,
                estado = Estado.ENTREGADO
            )
//            ---
//            Ricardo Fort
//            ---
            crear(
                usuario = ricardofort,
                local = localInicial,
                platos = mutableListOf(hamburguesa, pizza),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.PENDIENTE
            )
            crear(
                usuario = ricardofort,
                local = mcdonals,
                platos = mutableListOf(bigMac),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.ENTREGADO
            )
            crear(
                usuario = ricardofort,
                local = restauranteItaliano,
                platos = mutableListOf(fettuccineAlfredo, pizzaPepperoni),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.PREPARADO
            )
//            ---
//            Alex Caniggia
//            ---
            crear(
                usuario = alexcaniggia,
                local = localInicial,
                platos = mutableListOf(spaghettis, alitas, bigMac),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.ENTREGADO
            )
            crear(
                usuario = alexcaniggia,
                local = localInicial,
                platos = mutableListOf(pizza),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.PREPARADO
            )
            crear(
                usuario = alexcaniggia,
                local = restauranteItaliano,
                platos = mutableListOf(lasagnePortofino),
                medioDePago = Pago.QR,
                estado = Estado.ENTREGADO
            )
//            ---
//            Buzz
//            ---
            crear(
                usuario = buzz,
                local = localInicial,
                platos = mutableListOf(hamburguesa),
                medioDePago = Pago.QR,
                estado = Estado.ENTREGADO
            )
//            ---
//            Locomotora
//            ---
            crear(
                usuario = locomotora,
                local = localInicial,
                platos = mutableListOf(hamburguesa),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.CANCELADO
            )
            crear(
                usuario = locomotora,
                local = mcdonals,
                platos = mutableListOf(alitas),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.ENTREGADO
            )
            crear(
                usuario = locomotora,
                local = mcdonals,
                platos = mutableListOf(alitas, hamburguesa),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.PENDIENTE
            )
//            ---
        }
    }

    override fun afterPropertiesSet() {
        this.crearLocalInicial()
        this.crearIngredientes()
        this.crearClientes()
        this.crearPlatos()
        this.crearPedidos()
    }
}