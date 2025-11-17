package ar.edu.unsam.algo3.controlador


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import ar.edu.unsam.algo3.controlador.PedidoController
import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.modelo.utils.Direccion
import ar.edu.unsam.algo3.repositorio.RepositorioPedido
import io.kotest.core.annotation.DisplayName
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions
import org.uqbar.geodds.Point
import java.time.LocalDate
import kotlin.test.assertEquals


@Autowired
private lateinit var mockMvc: MockMvc

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test de PedidoController")
class PedidoControllerSpec(@Autowired val mockMvc: MockMvc) {

    @Autowired
    lateinit var repositorioPedidos: RepositorioPedido
    // private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun initializePedidosParaTesting() {
//      -- Locales:
        var localInicial = Local().apply {
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
        var mcdonals = Local().apply {
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
//      -- Ingredientes:
        var carnederenacuajo = Ingrediente(costoMercado = 0.7, grupoAlimenticio = GrupoAlimenticio.PROTEINAS, esOrigenAnimal = true,)
        var quesocheddar = Ingrediente(costoMercado = 0.5, grupoAlimenticio = GrupoAlimenticio.LACTEOS, esOrigenAnimal = true,)
        var lechuga = Ingrediente(costoMercado = 0.4, grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS, esOrigenAnimal = false,)
        var tomate = Ingrediente(costoMercado = 0.2, grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS, esOrigenAnimal = false,)
        var huevo = Ingrediente(costoMercado = 50.0, esOrigenAnimal = true, grupoAlimenticio = GrupoAlimenticio.PROTEINAS,)
//      -- Platos:
        var bigMac = Plato( valorBase = 10.99, local = mcdonals, fechaDeCreacion = LocalDate.now().minusDays(31),
            ingredientes = mutableListOf(carnederenacuajo, lechuga, quesocheddar))
        var alitas = Plato( valorBase = 9.25, local = localInicial, fechaDeCreacion = LocalDate.now().minusDays(31),
            ingredientes = mutableListOf(carnederenacuajo))
//      -- Pedidos:
        repositorioPedidos.pedidos.clear()
        repositorioPedidos.apply {
//      ------------------
//          Sofia Miller
//      --- Usuario:
            var sofiamiller = Usuario(nombre = "Sofía", apellido = "Miller",
                username = "smiller2006", mailPrincipal = "sofiamiller@gmail.com", password = "123",
                direccion = Direccion(calle = "Av siempre viva", altura = 555, ubicacion = Point(40.7128,-74.006)
                ),
            ).apply{
                this.agregarPreferido(tomate)
                this.agregarEvitar(huevo)
            }
//      --- Pedido:
            crear(
                usuario = sofiamiller,
                local = localInicial,
                platos = mutableListOf(bigMac, alitas),
                medioDePago = Pago.EFECTIVO,
                estado = Estado.PENDIENTE,
                fechaCreacion = LocalDate.of(2025, 10, 28)
            ).apply {
                this.id = 1
                this.estado = Estado.CONFIRMADO
                sofiamiller.registrarLocalParaPuntuar(this)
            }
//      -------------------
//            Ricardo Fort
//      --- Usuario:
            var ricardofort = Usuario(nombre = "Ricardo", apellido = "Fort",
                username = "rickyricon", mailPrincipal = "rickyfort@gmail.com", password = "123",
                direccion = Direccion(calle = "Maiame", altura = 354, ubicacion = Point(25.77427,-80.19366)),
                )
//      --- Pedido:
            crear(
                usuario = ricardofort,
                local = mcdonals,
                platos = mutableListOf(bigMac),
                medioDePago = Pago.TRANSFERENCIA_BANCARIA,
                estado = Estado.ENTREGADO
            ).apply {
                this.id = 2
            }
        }
    }

    @Test
    fun `Se puede buscar un pedido por su ID`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/pedido/1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("smiller2006"))
    }

    @Test
    fun `Al buscar un pedido con id inexistente tira error`() {
        val errorMessage = mockMvc.perform(MockMvcRequestBuilders.get("/pedido/80"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andReturn().resolvedException?.message

        assertEquals(errorMessage, "No existe el id 80 en repositorio para obtenerlo de la coleccion")
    }

@Test
fun `Get pedido por ID retorna 200`() {
    val jsonValido = """
        {
            "id": 1,
            "nombre": "pedido de prueba"
            "username": "prueba",
            "direccion": "prueba",
            "altura": 123,
            "lat": 123,
            "long":123,
            "deliveryComission": 1,
            "metodoDePago": "QR",
            "estado": "PENDIENTE",
            "horarioEntrega": "YAAAA",
            "platos":{
            "nombre" : "Nueva hamburguesa con carne de renacuajo",
            "descripcion": "Hamburguesa con queso acompañada de papas fritas y bebida",
            "valorBase": 9.99,
            "imagen": "/src/lib/assets/img/hamburguesa2.jpg",
            "store": {
                "name": "Test Valido",
                "storeURL": "https://valido.com",
                "email": "jorge@hotmail.com",
                "storeAddress": "Test 123",
                "storeAltitude": 100,
                "storeLatitude": -34.6,
                "storeLongitude": -58.4,
                "storeAppCommission": 50.0,
                "storeAuthorCommission": 50.0,
                "storePaymentEfectivo": true,
                "storePaymentQR": false,
                "storePaymentTransferencia": false
            },
            "ingredientes": [
                {
                    "id": 1,
                    "name": "Carne de Renacuajo",
                    "cost": 0.7,
                    "esOrigenAnimal": true,
                    "foodGroup": "Proteínas"
                },
                {
                    "id": 2,
                    "name": "Queso Cheddar",
                    "cost": 0.5,
                    "esOrigenAnimal": true,
                    "foodGroup": "Lácteos"
                }]
        }}
    """.trimIndent()

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/pedido/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonValido)
        ).andExpect(MockMvcResultMatchers.status().isOk)
}

@Test
fun `Get pedido por ID que no existe retorna 400`() {
    val jsonValido = """
    {
        "id": 1,
        "nombre": "pedido de prueba"
        "username": "prueba",
        "direccion": "prueba",
        "altura": 123,
        "lat": 123,
        "long":123,
        "deliveryComission": 1,
        "metodoDePago": "QR",
        "estado": "PENDIENTE",
        "horarioEntrega": "YAAAA",
        "platos":{
        "nombre" : "Nueva hamburguesa con carne de renacuajo",
        "descripcion": "Hamburguesa con queso acompañada de papas fritas y bebida",
        "valorBase": 9.99,
        "imagen": "/src/lib/assets/img/hamburguesa2.jpg",
        "store": {
            "name": "Test Valido",
            "storeURL": "https://valido.com",
            "email": "jorge@hotmail.com",
            "storeAddress": "Test 123",
            "storeAltitude": 100,
            "storeLatitude": -34.6,
            "storeLongitude": -58.4,
            "storeAppCommission": 50.0,
            "storeAuthorCommission": 50.0,
            "storePaymentEfectivo": true,
            "storePaymentQR": false,
            "storePaymentTransferencia": false
        },
        "ingredientes": [
            {
                "id": 1,
                "name": "Carne de Renacuajo",
                "cost": 0.7,
                "esOrigenAnimal": true,
                "foodGroup": "Proteínas"
            },
            {
                "id": 2,
                "name": "Queso Cheddar",
                "cost": 0.5,
                "esOrigenAnimal": true,
                "foodGroup": "Lácteos"
            }]
    }}
""".trimIndent()

    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/pedido/9")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonValido)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError)

}

@Test
fun `GET pedidos filtrados por estado y local retorna 200`() {
    mockMvc.perform(
        MockMvcRequestBuilders.get("/pedidos/")
            .param("estado", "PENDIENTE")
            .param("local", "jorge@hotmail.com")
            .contentType(MediaType.APPLICATION_JSON)
    )
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk)
}
}