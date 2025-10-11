package ar.edu.unsam.algo3.mock

import ar.edu.unsam.algo3.modelo.plato.Plato
import java.time.LocalDate

// TODO: [PROFE dijo] Vean la implementación de un boostrap para sacar los mocks, otra cosa que quiero investiguen y la hablamos

val pastaAlbondigas: Plato = Plato(
    nombre = "Pasta con albondigas",
    descripcion = "Deliciosa pasta con salsa de tomates y albondigas de cerdo",
    local = LocalPollos,
//    nombre local: Pollos Fire
//    nombre calle local: Avenida Melo
    valorBase = 12.99,
    ingredientes = mutableListOf(
        carneDeRenacuajo,
        quesoCheddar,
        lechuga,
        tomate
    )
)

val hambuerguesa: Plato = Plato(
    nombre = "Hamburguesa con queso y panceta",
    descripcion = "Combo de hamburguesa con papas y bebida",
    local = LocalPollos,
//    nombre local: Pollos Fire
//    nombre calle local: Avenida Melo
    valorBase = 9.99,
    ingredientes = mutableListOf(
        carneDeRenacuajo,
        quesoCheddar,
    )
)

val ensaladaHuerta: Plato = Plato(
    nombre = "Ensalada de la Huerta",
    descripcion = "Ensalada fresca con hojas mixtas y vinagreta",
    local = LocalPollos,
//    nombre local: Pollos Fire
//    nombre calle local: Avenida Melo
    valorBase = 7.5,
    ingredientes = mutableListOf(
        carneDeRenacuajo,
    )
)

val pizzaMuzzaTomate: Plato = Plato(
    nombre = "Pizza con mozzarella y Tomate",
    descripcion = "Pizza a la piedra con salsa de tomates frescos y extra queso",
    local = LocalPollos,
//    nombre local: Pollos Fire
//    nombre calle local: Avenida Melo
    valorBase = 11.75,
    ingredientes = mutableListOf(
        carneDeRenacuajo,
        quesoCheddar,
        lechuga,
        tomate
    )
)

val salmonGrillado: Plato = Plato(
    nombre = "Salmon con vegetales grillados",
    descripcion = "Salmon fresco a la plancha acompañado de vegetales",
    local = LocalPollos,
//    nombre local: Pollos Fire
//    nombre calle local: Avenida Melo
    valorBase = 14.25,
    ingredientes = mutableListOf(
        quesoCheddar,
        lechuga,
    )
)