package ar.edu.unsam.algo3.servicio

import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.repositorio.RepositorioIngrediente
import ar.edu.unsam.algo3.servicios.IngredienteService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest



@SpringBootTest
class IngredienteServiceIntegrationTest {

    @Autowired
    lateinit var ingredienteService: IngredienteService

    @Autowired
    lateinit var repositorioIngrediente: RepositorioIngrediente


    @Test
    fun `crear ingrediente valido lo guarda en la base de datos`() {
        // Given
        val ingredienteDTO = IngredienteDTO(
            name = "Carne de rata",
            cost = 1.5,
            esOrigenAnimal = false,
            foodGroup = GrupoAlimenticio.PROTEINAS,
            id = 10,
        )

        // When
        ingredienteService.crearIngrediente(ingredienteDTO)

        // Then - Buscar en la lista
        val ingredientesEncontrados = repositorioIngrediente.buscar("Carne de rata")

        assertTrue(ingredientesEncontrados.isNotEmpty())

        val ingredienteGuardado = ingredientesEncontrados.first()
        assertEquals("Carne de rata", ingredienteGuardado.nombre)
        assertEquals(1.5, ingredienteGuardado.costoMercado)
        assertEquals(GrupoAlimenticio.PROTEINAS, ingredienteGuardado.grupoAlimenticio)

    }


}