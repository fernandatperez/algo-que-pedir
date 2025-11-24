package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Combinado
import ar.edu.unsam.algo3.modelo.usuario.Calificacion
import ar.edu.unsam.algo3.modelo.usuario.Fieles
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.modelo.usuario.Vegano
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
                mediosDePago = mutableSetOf(Pago.EFECTIVO, Pago.QR)
                direccion = Direccion(
                    calle = "Av. Siempre Viva",
                    altura = 123,
                    ubicacion = Point(-34.6162132380519, -58.390811751881536)
                )
                calificaciones = mutableListOf(
                    Calificacion(5, "Excelente panadería, los pasteles son increíbles"),
                    Calificacion(4, "Muy buena calidad, recomendado"),
                    Calificacion(5, "Las mejores tortas de la ciudad"),
                    Calificacion(4, "Servicio rápido y amable")
                )
            }

            mcdonals = Local().apply {
                id = 2
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
                calificaciones = mutableListOf(
                    Calificacion(4, "Rápido y conveniente"),
                    Calificacion(3, "Está bien para salir del paso"),
                    Calificacion(4, "Las hamburguesas son buenas"),
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
                mediosDePago = mutableSetOf(Pago.EFECTIVO, Pago.TRANSFERENCIA_BANCARIA)
                direccion = Direccion(
                    calle = "Lacroze",
                    altura = 5006,
                    ubicacion = Point(-34.54721730459346, -58.55472453690146)
                )
                calificaciones = mutableListOf(
                    Calificacion(5, "El mejor sushi de la zona"),
                    Calificacion(5, "Fresco y delicioso"),
                    Calificacion(4, "Muy buena presentación"),
                    Calificacion(5, "Volveré seguro"),
                    Calificacion(4, "Excelente relación precio-calidad")
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
                mediosDePago = mutableSetOf(Pago.QR, Pago.EFECTIVO)
                direccion = Direccion(
                    calle = "Av. Corrientes",
                    altura = 3454,
                    ubicacion = Point(-34.6035, -58.4115)  // lo cambio para que tambien sea cercano
                )
                calificaciones = mutableListOf(
                    Calificacion(4, "Buenos helados a buen precio"),
                    Calificacion(5, "Me encantan los sabores"),
                    Calificacion(4, "Atención rápida"),
                    Calificacion(3, "Está bien")
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
                mediosDePago = mutableSetOf(Pago.QR, Pago.TRANSFERENCIA_BANCARIA)
                direccion = Direccion(
                    calle = "Gral San Martin",
                    altura = 1904,
                    ubicacion = Point(-34.5235380087158, -58.48952314781408)
                )
                calificaciones = mutableListOf(
                    Calificacion(5, "Comida casera deliciosa"),
                    Calificacion(4, "Muy buen servicio"),
                    Calificacion(5, "Como comer en casa de la abuela"),
                    Calificacion(4, "Porciones generosas")
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
                mediosDePago = mutableSetOf(Pago.TRANSFERENCIA_BANCARIA, Pago.EFECTIVO)
                direccion = Direccion(
                    calle = "Presidente Illia",
                    altura = 3170,
                    ubicacion = Point(-34.5232471721764, -58.703041085973894)
                )
                calificaciones = mutableListOf(
                    Calificacion(5, "Los mejores lomitos de la ciudad"),
                    Calificacion(5, "Espectacular, muy completo"),
                    Calificacion(4, "Muy rico, volveré"),
                    Calificacion(5, "Excelente calidad de carne"),
                    Calificacion(4, "Recomendado")
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
                calificaciones = mutableListOf(
                    Calificacion(5, "Ahreeee, este local es posta genial, no lo puedo creer, realmente maravillado"),
                    Calificacion(4, "Bastante bueno"),
                    Calificacion(5, "Una locura"),
                    Calificacion(5, "Que delicia!"),
                    Calificacion(4, "Rico"),
                    Calificacion(5, "Goated"),
                    Calificacion(5, "Un espectaculo de sabores"),
                    Calificacion(4, "Realmente bueno, volveria"),
                    Calificacion(3, "Que se yo me obligo Google Maps soy re bobo"))
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
    private lateinit var arroz: Ingrediente
    private lateinit var salmonIngrediente: Ingrediente
    private lateinit var aguacate: Ingrediente
    private lateinit var helado: Ingrediente
    private lateinit var carneMolida: Ingrediente
    private lateinit var pan: Ingrediente

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
    private lateinit var sushiRoll: Plato
    private lateinit var sashimi: Plato
    private lateinit var heladoVainilla: Plato
    private lateinit var heladoChocolate: Plato
    private lateinit var lomitoCompleto: Plato
    private lateinit var lomitoSimple: Plato
    private lateinit var empanadas: Plato

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
            tipoDeCliente = Combinado(mutableSetOf(Vegano, Fieles().apply{
                this.agregarLocalFavorito(mcdonals)
            }))
        ).apply{
            this.id = 1
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
                calle = "Av. Corrientes",
                altura = 3400,
                ubicacion = Point(-34.6032, -58.4110)  //le cambio la ubi para que este cerca del mc
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
        arroz = Ingrediente(
            nombre = "Arroz",
            costoMercado = 0.3,
            grupoAlimenticio = GrupoAlimenticio.CEREALES_Y_TUBERCULOS,
            esOrigenAnimal = false,
        )
        salmonIngrediente = Ingrediente(
            nombre = "Salmón",
            costoMercado = 1.5,
            grupoAlimenticio = GrupoAlimenticio.PROTEINAS,
            esOrigenAnimal = true,
        )
        aguacate = Ingrediente(
            nombre = "Aguacate",
            costoMercado = 0.8,
            grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS,
            esOrigenAnimal = false,
        )
        helado = Ingrediente(
            nombre = "Helado",
            costoMercado = 0.6,
            grupoAlimenticio = GrupoAlimenticio.LACTEOS,
            esOrigenAnimal = true,
        )
        carneMolida = Ingrediente(
            nombre = "Carne Molida",
            costoMercado = 1.0,
            grupoAlimenticio = GrupoAlimenticio.PROTEINAS,
            esOrigenAnimal = true,
        )
        pan = Ingrediente(
            nombre = "Pan",
            costoMercado = 0.3,
            grupoAlimenticio = GrupoAlimenticio.CEREALES_Y_TUBERCULOS,
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
            crear(arroz)
            crear(salmonIngrediente)
            crear(aguacate)
            crear(helado)
            crear(carneMolida)
            crear(pan)
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
        sushiRoll = Plato(
            nombre = "California Roll",
            descripcion = "Roll de sushi con cangrejo, aguacate y pepino",
            valorBase = 15.50,
            urldeImagen = "https://cdn.pixabay.com/photo/2017/10/15/11/41/sushi-2853382_1280.jpg",
            local = sushipop,
            fechaDeCreacion = LocalDate.now().minusDays(20),
            ingredientes = mutableListOf(arroz, salmonIngrediente, aguacate)
        )
        sashimi = Plato(
            nombre = "Sashimi Variado",
            descripcion = "Selección de pescado crudo fresco",
            valorBase = 18.99,
            urldeImagen = "https://media.istockphoto.com/id/1324332485/es/foto/sashimi-mori.jpg?s=612x612&w=0&k=20&c=o0YSV_lENj294Marl80m5ie41EbL3H_7IO3iN-1yod4=",
            local = sushipop,
            fechaDeCreacion = LocalDate.now().minusDays(18),
            ingredientes = mutableListOf(salmonIngrediente)
        )

        heladoVainilla = Plato(
            nombre = "Helado de Vainilla",
            descripcion = "Cremoso helado artesanal de vainilla",
            valorBase = 5.50,
            urldeImagen = "https://www.recetasnestle.com.do/sites/default/files/srh_recipes/62099096785a3c939a1a1eefb06bf358.jpg",
            local = grido,
            fechaDeCreacion = LocalDate.now().minusDays(5),
            ingredientes = mutableListOf(helado)
        )
        heladoChocolate = Plato(
            nombre = "Helado de Chocolate",
            descripcion = "Intenso helado de chocolate con chips",
            valorBase = 5.99,
            urldeImagen = "https://images.cookforyourlife.org/wp-content/uploads/2020/06/Chocolate-Whipped-Ice-Cream-shutterstock_1010248351.jpg",
            local = grido,
            fechaDeCreacion = LocalDate.now().minusDays(5),
            ingredientes = mutableListOf(helado)
        )

        lomitoCompleto = Plato(
            nombre = "Lomito Completo",
            descripcion = "Lomito con carne, lechuga, tomate, huevo y queso",
            valorBase = 13.50,
            urldeImagen = "https://www.circuitogastronomico.com/wp-content/uploads/2022/11/el-meson-lomitodestacada.jpg",
            local = lomitos,
            fechaDeCreacion = LocalDate.now().minusDays(25),
            ingredientes = mutableListOf(carneMolida, lechuga, tomate, huevo, quesocheddar, pan)
        )
        lomitoSimple = Plato(
            nombre = "Lomito Simple",
            descripcion = "Lomito clásico con carne y vegetales",
            valorBase = 10.99,
            urldeImagen = "https://cdn.pedix.app/dKwKmIGsUlZubDzn7qgK/products/1699760410847.png?size=800x800",
            local = lomitos,
            fechaDeCreacion = LocalDate.now().minusDays(25),
            ingredientes = mutableListOf(carneMolida, lechuga, pan)
        )

        empanadas = Plato(
            nombre = "Empanadas Caseras",
            descripcion = "Empanadas de carne jugosas hechas en casa",
            valorBase = 8.50,
            urldeImagen = "https://http2.mlstatic.com/D_690001-MLA82051607857_012025-O.jpg",
            local = migusto,
            fechaDeCreacion = LocalDate.now().minusDays(15),
            ingredientes = mutableListOf(carneMolida)
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
            crear(sushiRoll)
            crear(sashimi)
            crear(heladoVainilla)
            crear(heladoChocolate)
            crear(lomitoCompleto)
            crear(lomitoSimple)
            crear(empanadas)
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
                platos = mutableListOf(bigMac, bigMac, ensalada, alitas),
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
                estado = Estado.PENDIENTE
            )
            crear(
                usuario = sofiamiller,
                local = grido,
                platos = mutableListOf(heladoVainilla, heladoChocolate),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.PENDIENTE
            )
            crear(
                usuario = sofiamiller,
                local = restauranteItaliano,
                platos = mutableListOf(pizzaMargherita, spaghettiCarbonara),
                medioDePago = Pago.QR,
                estado = Estado.PENDIENTE
            ) .apply {
                this.estado = Estado.CONFIRMADO
                sofiamiller.registrarLocalParaPuntuar(this)
            }
            crear(
                usuario = sofiamiller,
                local = sushipop,
                platos = mutableListOf(sushiRoll, sashimi),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.PREPARADO // este no se muestra en react
            ).apply {
                sofiamiller.registrarLocalParaPuntuar(this)
            }
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
                medioDePago = Pago.QR,
                estado = Estado.PREPARADO
            ).apply {
                ricardofort.registrarLocalParaPuntuar(this)
            }
            crear(
                usuario = ricardofort,
                local = restauranteItaliano,
                platos = mutableListOf(fettuccineAlfredo, pizzaPepperoni),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.PREPARADO
            ) .apply {
                this.estado = Estado.CONFIRMADO
                ricardofort.registrarLocalParaPuntuar(this)
            }
            crear(
                usuario = ricardofort,
                local = lomitos,
                platos = mutableListOf(lomitoCompleto),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.PENDIENTE
            )
//            ---
//            Alex Caniggia
//            ---
            crear(
                usuario = alexcaniggia,
                local = localInicial,
                platos = mutableListOf(pizza),
                medioDePago = Pago.QR,
                estado = Estado.PREPARADO
            ).apply {
                alexcaniggia.registrarLocalParaPuntuar(this)
            }
            crear(
                usuario = alexcaniggia,
                local = restauranteItaliano,
                platos = mutableListOf(lasagnePortofino),
                medioDePago = Pago.QR,
                estado = Estado.PREPARADO
            ).apply {
                alexcaniggia.registrarLocalParaPuntuar(this)
            }
            crear(
                usuario = alexcaniggia,
                local = migusto,
                platos = mutableListOf(empanadas),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.PENDIENTE
            )
//            ---
//            Buzz
//            ---
            crear(
                usuario = buzz,
                local = localInicial,
                platos = mutableListOf(hamburguesa),
                medioDePago = Pago.QR,
                estado = Estado.PREPARADO
            ).apply {
                buzz.registrarLocalParaPuntuar(this)
            }
            crear(
                usuario = buzz,
                local = grido,
                platos = mutableListOf(heladoVainilla, heladoChocolate),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.PENDIENTE
            )
//            ---
//            Locomotora
//            ---
            crear(
                usuario = locomotora,
                local = localInicial,
                platos = mutableListOf(hamburguesa),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.CANCELADO
            ).apply {
                locomotora.registrarLocalParaPuntuar(this)
            }
            crear(
                usuario = locomotora,
                local = mcdonals,
                platos = mutableListOf(alitas),
                medioDePago = Pago.QR,
                estado = Estado.PREPARADO
            ).apply {
                locomotora.registrarLocalParaPuntuar(this)
            }
            crear(
                usuario = locomotora,
                local = lomitos,
                platos = mutableListOf(lomitoCompleto, lomitoSimple),
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