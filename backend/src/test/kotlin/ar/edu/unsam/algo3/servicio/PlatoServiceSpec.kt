package ar.edu.unsam.algo3.servicio

import ar.edu.unsam.algo3.controlador.PlatoController
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.repositorio.RepositorioPedido
import ar.edu.unsam.algo3.repositorio.Repositorio
import ar.edu.unsam.algo3.servicios.PlatoService
import io.kotest.core.spec.style.AnnotationSpec
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate
import kotlin.test.Test



@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerIntegrationTest {

        @Autowired
        private lateinit var mockMvc: MockMvc

        @Autowired
        private lateinit var repositorioPedidos: RepositorioPedido

//        @Test
//        fun `GET pedidos filtrados por estado PENDIENTE retorna 200`() {
//            mockMvc.perform(
//                MockMvcRequestBuilders.get("/pedidos")
//                    .param("estado", "PENDIENTE")
//                    .param("local", "jorge@hotmail.com")
//                    .contentType(MediaType.APPLICATION_JSON)
//            )
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk)
//                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
//        }

//        @Test
//        fun `GET pedidos filtrados por estado PREPARADO retorna 200`() {
//            mockMvc.perform(
//                MockMvcRequestBuilders.get("/pedidos")
//                    .param("estado", "PREPARADO")
//                    .param("local", "jorge@hotmail.com")
//                    .contentType(MediaType.APPLICATION_JSON)
//            )
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk)
//                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
//        }

        @Test
        fun `GET pedido por ID existente retorna 200`() {

            mockMvc.perform(
                MockMvcRequestBuilders.get("/pedido/1")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
        }

        @Test
        fun `GET pedido por ID inexistente retorna 404`() {
            mockMvc.perform(
                MockMvcRequestBuilders.get("/pedido/9999")
                    .contentType(MediaType.APPLICATION_JSON)
            )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound)
        }
}



