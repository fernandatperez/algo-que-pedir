package ar.edu.unsam.algo3.controlador


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*


@Autowired
private lateinit var mockMvc: MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerSpec {

    @Autowired
    private lateinit var mockMvc: MockMvc

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
    fun `GET pedidoss retorna 200`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/pedidos")).andExpect(MockMvcResultMatchers.status().isOk)
    }
}