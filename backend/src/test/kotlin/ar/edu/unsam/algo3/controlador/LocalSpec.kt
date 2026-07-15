package ar.edu.unsam.algo3.controlador

import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.modelo.utils.Direccion
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.repositorio.RepositorioCliente
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.hamcrest.Matchers.containsString
import org.hamcrest.Matchers.everyItem
import org.uqbar.geodds.Point



@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StoreProfilesControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var repositorioLocal: RepositorioLocal

    @Autowired
    private lateinit var repositorioUsuario: RepositorioCliente

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var usuarioTest: Usuario
    private lateinit var otroUsuario: Usuario

    @BeforeAll
    fun setUp() {
        repositorioLocal.limpiarColeccion()
        repositorioUsuario.limpiarColeccion()
        crearUsuariosDePrueba()
        crearLocalesDePrueba()
    }

    private fun crearUsuariosDePrueba() {
        usuarioTest = Usuario(
            nombre = "Usuario",
            apellido = "Test",
            username = "usuariotest",
            mailPrincipal = "test@usuario.com",
            password = "123",
            direccion = Direccion(
                calle = "Av. Test",
                altura = 123,
                ubicacion = Point(-34.6162132380519, -58.390811751881536)
            )
        ).apply {
            this.id = 123
        }

        otroUsuario = Usuario(
            nombre = "Otro",
            apellido = "Usuario",
            username = "otrousuario",
            mailPrincipal = "otro@usuario.com",
            password = "123",
            direccion = Direccion(
                calle = "Calle Secundaria",
                altura = 456,
                ubicacion = Point(-34.6162132380519, -58.390811751881536)
            )
        ).apply {
            this.id = 456
        }

        repositorioUsuario.apply {
            this.crear(usuarioTest)
            this.crear(otroUsuario)
        }
    }

    private fun crearLocalesDePrueba() {
        val locales = listOf(
            Local(
                nombre = "Pizzeria guerrin",
                email = "guerrin@gmail.com",
                password = "password123"
            ),
            Local(
                nombre = "Cafe Martinez",
                email = "martinez@gmail.com",
                password = "password123"
            ),
            Local(
                nombre = "Heladeria los dos gustos",
                email = "heladeria@gmail.com",
                password = "password123"
            ),
            Local(
                nombre = "Restaurante Buen Sabor",
                email = "buensabor@rest.com",
                password = "password123"
            ),
            Local(
                nombre = "Panadería la esquina",
                email = "dulce@pan.com",
                password = "pass123"
            ),
            Local(
                nombre = "Panadería San Martin",
                email = "salada@pan.com",
                password = "pass123"
            ),
            Local(
                nombre = "Churros el topo",
                email = "eltopo@gmail.com",
                password = "pass123"
            )
        )

        locales.forEach { repositorioLocal.crear(it) }
    }



    @Test
    fun `POST store-profiles deberia retornar locales que coincidan con el nombre de busqueda`() {
        // Given - Usar el userId 123 que existe
        val searchRequest = """
            {
                "userId": "123",
                "searchName": "guerrin"
            }
        """.trimIndent()

        // When & Then
        mockMvc.perform(
            post("/store-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(searchRequest)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].name").value(containsString("guerrin")))
    }

    @Test
    fun `POST store-profiles deberia retornar lista vacia cuando no hay coincidencias`() {
        // Given
        val searchRequest = """
            {
                "userId": "123",
                "searchName": "Zapateria"
            }
        """.trimIndent()

        // When & Then
        mockMvc.perform(
            post("/store-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(searchRequest)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(0))
    }

    @Test
    fun `POST store-profiles deberia manejar busquedas parciales`() {
        // Given
        val searchRequest = """
            {
                "userId": "123",
                "searchName": "topo"
            }
        """.trimIndent()

        // When & Then
        mockMvc.perform(
            post("/store-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(searchRequest)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].name").value("Churros el topo"))
    }


    @Test
    fun `POST store-profiles deberia manejar userId que no existe`() {
        // Given - Usar un userId que NO existe
        val searchRequest = """
            {
                "userId": "999",
                "searchName": "Tienda"
            }
        """.trimIndent()

        // When & Then
        mockMvc.perform(
            post("/store-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(searchRequest)
        )
            .andExpect(status().isNotFound) // Esperamos 404
    }


    @Test
    fun `POST store-profiles deberia manejar nombre de busqueda vacio`() {
        // Given
        val searchRequest = """
            {
                "userId": "123",
                "searchName": ""
            }
        """.trimIndent()

        // When & Then
        mockMvc.perform(
            post("/store-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(searchRequest)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
    }

    @Test
    fun `POST store-profiles deberia retornar multiples coincidencias para Panaderia`() {
        // Given
        val searchRequest = """
            {
                "userId": "123",
                "searchName": "Panadería"
            }
        """.trimIndent()

        // When & Then
        mockMvc.perform(
            post("/store-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(searchRequest)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[*].name").value(everyItem(containsString("Panadería"))))
    }

    @Test
    fun `POST store-profiles deberia funcionar con diferentes usuarios`() {
        // Given - Usar el otro usuario (456)
        val searchRequest = """
            {
                "userId": "456",
                "searchName": "Cafe"
            }
        """.trimIndent()

        // When & Then
        mockMvc.perform(
            post("/store-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(searchRequest)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].name").value("Cafe Martinez"))
    }
}