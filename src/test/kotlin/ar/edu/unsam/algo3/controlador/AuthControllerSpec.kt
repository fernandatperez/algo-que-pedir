package ar.edu.unsam.algo3.controlador

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

//este test se va a tener que cambiar cuando se agregue que el usuario es el local
//tambien va a cambiar con el manejo de errores

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `login con usuario que no existe retorna error 400`() {
        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = """
            {
                "correo": "noexiste@example.com",
                "password": "password123"
            }
            """
        }.andExpect {
            status { is4xxClientError() }
        }
    }

    @Test
    fun `login con password incorrecta retorna error`() {
        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = """
            {
                "correo": "admin@unsam.edu.ar",
                "password": "password_incorrecta"
            }
            """
        }.andExpect {
            status { is4xxClientError() }
        }
    }

    @Test
    fun `login exitoso con usuario valido`() {
        mockMvc.post("/login") {
            contentType = MediaType.APPLICATION_JSON
            content = """
            {
                "email": "jorge@hotmail.com",
                "password": "123"
            }
            """
        }.andExpect {
            status { isOk() }
        }
    }

    @Test
    fun `register exitoso`() {
        val email = "test${System.currentTimeMillis()}@example.com"

        mockMvc.post("/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """
            {
                "name": "Test",
                "surname": "User",
                "email": "$email",
                "password": "password123"
            }
            """
        }.andExpect {
            status { isOk() }
        }
    }
}