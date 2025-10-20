package ar.edu.unsam.algo3.controlador


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@Autowired
private lateinit var mockMvc: MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class PlatoControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Test
    fun `POST con datos validos retorna 200`() {
        val jsonValido = """
        {
            "nombre" : "Hamburguesa con queso",
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
        }
    """.trimIndent()

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/platos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValido)
                    .param("mail", "jorge@hotmail.com")
            ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `POST sin local retorna 400`() {
        val jsonValido = """
        {
            "nombre" : "Hamburguesa con queso",
            "descripcion": "Hamburguesa con queso acompañada de papas fritas y bebida",
            "valorBase": 9.99,
            "imagen": "/src/lib/assets/img/hamburguesa2.jpg",
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
        }
    """.trimIndent()

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/platos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValido)
            ).andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `get con datos validos retorna 200`() {
        val jsonValido = """
        {
            "nombre" : "Hamburguesa con queso",
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
        }
    """.trimIndent()

        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/platos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValido)
            ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `get platos con mail inexistente retorna lista vacia`() {
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/platos")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("mail", "noexiste@mail.com")
            )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0))  // ← Lista vacía
    }


    @Test
    fun `put a ID que existe retorna 200`() {
        val jsonValido = """
        {
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
        }
    """.trimIndent()

        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/platos/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValido)
                    .param("mail", "jorge@hotmail.com")
            ).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `GET platos retorna 200`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/platos")
        .param("mail", "jorge@hotmail.com"))
        .andExpect(MockMvcResultMatchers.status().isOk)
    }
}