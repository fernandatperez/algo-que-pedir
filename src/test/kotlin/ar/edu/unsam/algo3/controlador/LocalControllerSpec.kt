package ar.edu.unsam.algo3.controlador

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ar.edu.unsam.algo3.servicios.UsuarioService

@Autowired
private lateinit var mockMvc: MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class ControladorLocalRealTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Test
    fun `PUT con datos validos retorna 200`() {
        val jsonValido = """
            {
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
            }
        """.trimIndent()

        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/store-profile")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValido)
                    .sessionAttr("userEmail", "jorge@hotmail.com")
            )
            .andExpect(MockMvcResultMatchers.status().isOk)
    }


//    @Test
//    fun `PUT con URL invalida retorna 500`() {
//        val jsonInvalido = """
//            {
//                "name": "Test",
//                "email": "jorge@hotmail.com",
//                "storeURL": "url-invalida",
//                "storeAddress": "Test 123",
//                "storeAltitude": 100,
//                "storeLatitude": -34.6,
//                "storeLongitude": -58.4,
//                "storeAppCommission": 50.0,
//                "storeAuthorCommission": 50.0,
//                "storePaymentEfectivo": true,
//                "storePaymentQR": false,
//                "storePaymentTransferencia": false
//            }
//        """.trimIndent()
//
//        mockMvc
//            .perform(
//                MockMvcRequestBuilders.put("/store-profile")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(jsonInvalido)
//            )
//            .andExpect(MockMvcResultMatchers.status().is5xxServerError)
//    }


    @Test
    fun `PUT sin medios de pago retorna 500`() {
        val jsonInvalido = """
            {
                "name": "Test",
                "storeURL": "https://valido.com",
                "email": "jorge@hotmail.com",
                "storeAddress": "Test 123",
                "storeAltitude": 100,
                "storeLatitude": -34.6,
                "storeLongitude": -58.4,
                "storeAppCommission": 50.0,
                "storeAuthorCommission": 50.0,
            }
        """.trimIndent()

        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/store-profile")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonInvalido)
            )
            .andExpect(MockMvcResultMatchers.status().is5xxServerError)
    }


    @Test
    fun `GET store-profile retorna 200`() {
        mockMvc
            .perform(MockMvcRequestBuilders.get("/store-profile"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}