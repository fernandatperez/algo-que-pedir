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
class IngredienteControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @Test
    fun `POST con datos validos retorna 200`() {
        val jsonValido = """
        {
            "name": "Manteca",
            "costoMercado": 0.7,
            "foodGroup": "Grasas y aceites",
            "esOrigenAnimal": true
        }
    """.trimIndent()

        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/crear-ingrediente")
                    .param("mail", "jorge@hotmail.com")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonValido)
            ).andExpect(MockMvcResultMatchers.status().isOk)
    }


    @Test
    fun `PUT con URL invalida retorna 400`() {
        val jsonInvalido = """
            {
            "name": "Manteca",
            "costoMercado": 0.7,
            "foodGroup": "Grasas y aceites",
            "esOrigenAnimal": true
        }
        """.trimIndent()
        mockMvc.perform(
            MockMvcRequestBuilders.put("/actualizar-ingrediente/9")
                .contentType(MediaType.APPLICATION_JSON)
                .param("mail", "jorge@hotmail.com")
                .content(jsonInvalido)
        ).andExpect(MockMvcResultMatchers.status().is4xxClientError)


    }


    @Test
    fun `GET actualizar-ingrediente retorna 200`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/ingredientes")
            .param("mail", "jorge@hotmail.com"))
            .andExpect(MockMvcResultMatchers.status().isOk)
    }
}