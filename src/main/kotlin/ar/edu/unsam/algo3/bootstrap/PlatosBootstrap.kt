package ar.edu.unsam.algo3.bootstrap

import ar.edu.unsam.algo3.mock.LocalPollos
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.repositorio.Repositorio
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service

import ar.edu.unsam.algo3.mock.tomate
import ar.edu.unsam.algo3.mock.lechuga
import ar.edu.unsam.algo3.mock.quesoCheddar
import ar.edu.unsam.algo3.mock.carneDeRenacuajo
import ar.edu.unsam.algo3.modelo.plato.PlatoDTO
import ar.edu.unsam.algo3.modelo.plato.toDTO

// sacado de tareas de dodino, no se usa en ningun lado igualmente porque se rompe
@Service
class PlatosBootstrap(
    val repoPlatos: Repositorio<Plato>,
) : InitializingBean {

    fun crearPlatos() {

        val platos = listOf(
            Plato(
                nombre = "Pasta con albóndigas",
                descripcion = "Deliciosa pasta con salsa de tomates y albóndigas de cerdo",
                local = LocalPollos,
                valorBase = 12.99,
                urldeImagen = "/src/lib/assets/img/spagettis.png",
                ingredientes = mutableListOf(carneDeRenacuajo, quesoCheddar, lechuga, tomate)
            ),
            Plato(
                nombre = "Hamburguesa con queso y panceta",
                descripcion = "Combo de hamburguesa con papas y bebida",
                local = LocalPollos,
                valorBase = 9.99,
                urldeImagen = "/src/lib/assets/img/hamburguesa2.jpg",
                ingredientes = mutableListOf(carneDeRenacuajo, quesoCheddar)
            ),
            Plato(
                nombre = "Ensalada de la Huerta",
                descripcion = "Ensalada fresca con hojas mixtas y vinagreta",
                local = LocalPollos,
                valorBase = 7.5,
                urldeImagen = "/src/lib/assets/img/ensalada.png",
                ingredientes = mutableListOf(lechuga, tomate)
            ),
            Plato(
                nombre = "Pizza con mozzarella y tomate",
                descripcion = "Pizza a la piedra con salsa de tomates frescos y extra queso",
                local = LocalPollos,
                valorBase = 11.75,
                urldeImagen = "/src/lib/assets/img/pizza.png",
                ingredientes = mutableListOf(quesoCheddar, tomate)
            ),
            Plato(
                nombre = "Salmón con vegetales grillados",
                descripcion = "Salmón fresco a la plancha acompañado de vegetales",
                local = LocalPollos,
                valorBase = 14.25,
                urldeImagen = "/src/lib/assets/img/salmon.png",
                ingredientes = mutableListOf(quesoCheddar, lechuga)
            )
        )

        platos.forEach { repoPlatos.crear(it) }
    }

    // Se ejecuta automáticamente al iniciar Spring Boot en teoria...
    override fun afterPropertiesSet() {
        this.crearPlatos()
    }
}