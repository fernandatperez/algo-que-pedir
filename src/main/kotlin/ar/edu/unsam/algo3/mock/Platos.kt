package ar.edu.unsam.algo3.mock

import ar.edu.unsam.algo3.modelo.plato.Plato
import java.time.LocalDate

object milaConFritas: Plato(
    nombre = "Milanesa con Papas fritas",
    descripcion = "Milanesa de lomo de 3cm de grosor. Papas fritas rebozadas en pimenton del Himalaya. Gustosas salsas a eleccion.",
    valorBase = 4.0,
    fechaDeCreacion = LocalDate.of(2025, 10, 8)
//    Aca faltan los ingredientes, pero creo que se puede laburar igual
)

object RabasConPomarola: Plato(
    nombre = "Rabas a la Pomarola",
    descripcion = "Exquisitas rabas rebozadas en panco acompañadas de salsa Pomarola. Decoradas con cilantro y mayonesa de ajo. Limon opcional.",
    valorBase = 7.0,
    esDeAutor = true,
    fechaDeCreacion = LocalDate.of(2023, 7, 10)
)

object Lasagna: Plato(
    nombre = "Lasagna de carne",
    descripcion = "6 pisos de nuestra deliciosa mezcla de verdura y carne. Lomo tiernizado en caldo vegetal por 2 horas, acompañado de verduras salteadas",
    valorBase = 10.0,
    esDeAutor = true,
    fechaDeCreacion = LocalDate.of(2023, 1, 9)
)

object Nachos: Plato(
    nombre = "Nachos con queso y bacon",
    descripcion = "Plato de nachos bañados con queso cheddar y una lluvia de bacon. Opcional acompañamiento de cantico de Mariachis. Se recomienda discrecion.",
    valorBase = 3.0,
    fechaDeCreacion = LocalDate.of(2022, 2, 1)
)