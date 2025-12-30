package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.dto.AuthRequest
import ar.edu.unsam.algo3.repositorio.RepositorioCliente
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import io.kotest.core.annotation.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertEquals

//este test se va a tener que cambiar cuando se agregue que el usuario es el local
//tambien va a cambiar con el manejo de errores

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Tests de AuthController")
class AuthControllerTest(@Autowired val mockMvc: MockMvc) {

    @Autowired
    lateinit var repositorioCliente: RepositorioCliente

    @Autowired
    lateinit var repositorioLocal: RepositorioLocal

    @Test
    fun `login con local que no existe retorna error 400`() {
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
    fun `login exitoso con local valido`() {
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

    @Test
    fun `login de cliente exitoso`() {
        val userValido = """
            {
            "email": "sofiamiller@gmail.com",
            "password": "123"
            }
        """.trimIndent()

        mockMvc.perform(MockMvcRequestBuilders.post("/userLogin")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(userValido))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
    }

    @Test
    fun `regiter de cliente exitoso`() {
        val userValido = """
            {
            "name": "nombre",
            "lastName": "apellido",
            "email": "nombre@gmail.com",
            "password": "123"
            }
        """.trimIndent()

        mockMvc.perform(MockMvcRequestBuilders.post("/userRegister")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(userValido))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("nombre@gmail.com"))
    }

    @Test
    fun `no se puede registrar si el mail esta siendo usado (ya existe)`() {
        val userInvalido = """
            {
            "name": "nombre",
            "lastName": "apellido",
            "email": "sofiamiller@gmail.com",
            "password": "123"
            }
        """.trimIndent()

        val errorMessage = mockMvc.perform(MockMvcRequestBuilders.post("/userRegister")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(userInvalido))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
            .andReturn().resolvedException?.message

        assertEquals(errorMessage, "Email incorrecto")
    }
}