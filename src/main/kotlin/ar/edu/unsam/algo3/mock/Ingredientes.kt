package ar.edu.unsam.algo3.mock

import ar.edu.unsam.algo3.modelo.ingrediente.GrupoAlimenticio
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente

object carneDeRenacuajo: Ingrediente(
    nombre = "Carne de Renacuajo",
    costoMercado = 0.7,
    grupoAlimenticio = GrupoAlimenticio.PROTEINAS,
    esOrigenAnimal = true
)

object quesoCheddar: Ingrediente(
    nombre = "Queso Cheddar",
    costoMercado = 0.5,
    grupoAlimenticio = GrupoAlimenticio.LACTEOS,
    esOrigenAnimal = true
)

object lechuga: Ingrediente(
    nombre = "Lechuga",
    costoMercado = 0.4,
    grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS,
    esOrigenAnimal = false
)

object tomate: Ingrediente(
    nombre = "Tomate",
    costoMercado = 0.2,
    grupoAlimenticio = GrupoAlimenticio.FRUTAS_Y_VERDURAS,
    esOrigenAnimal = false
)