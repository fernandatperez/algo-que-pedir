package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.mock.LocalPollos
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.plato.Plato

// Armar DTO aparte para el menu
data class PlatoMenuDTO(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String
)

data class PlatoDTO(
    val id : Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val valorBase: Double,
    val imagen: String,
    val costoProduccion: Double,
    val esDeAutor: Boolean,
    val enPromocion: Boolean,
    val ingredientes: List<IngredienteDTO>,
    val store: Local = LocalPollos, // Esto deberia ser DTO tambien
)


// Esto seria edicion de plato. Falta armar el return de plato
data class PlatoDTOUpdate(
    val id : Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val valorBase: Double,
    val imagen: String,
    val costoProduccion: Double,
    val esDeAutor: Boolean,
    val enPromocion: Boolean,
    val ingredientes: List<Int>,
    val store: Local = LocalPollos, // Esto deberia ser DTO tambien
)

fun Plato.toDTOUpdate(): PlatoDTOUpdate {
    return PlatoDTOUpdate(
        id = this.id,
        nombre = this.nombre,
        descripcion = this.descripcion,
        precio = this.valorVenta(), // no mandar
        valorBase = this.getValorBase(),
        imagen = this.urldeImagen,
        costoProduccion = this.costoProduccion(),
        esDeAutor = this.esDeAutor,
        enPromocion = this.esNuevo(),
        ingredientes = this.ingredientes.map{ it.id },
//        store = this.local.toResponse(), // Cuando tengamos lo mismo
        store = this.local
    )
}

fun Plato.toDTO(): PlatoDTO {
    return PlatoDTO(
        id = this.id,
        nombre = this.nombre,
        descripcion = this.descripcion,
        precio = this.valorVenta(), // no mandar
        valorBase = this.getValorBase(),
        imagen = this.urldeImagen,
        costoProduccion = this.costoProduccion(),
        esDeAutor = this.esDeAutor,
        enPromocion = this.esNuevo(),
        ingredientes = this.ingredientes.map{ it.toDTO() },
//        store = this.local.toResponse(), // Cuando tengamos lo mismo
        store = this.local
    )
}

fun Plato.toPlatoMenuDTO(): PlatoMenuDTO {
    return PlatoMenuDTO(
        id = this.id,
        nombre = this.nombre,
        descripcion = this.descripcion,
        precio = this.valorVenta(),
        imagen = this.urldeImagen
    )
}
// Necesito que reciba la lista de ingredientes ya resuelta
fun Plato.fromDTOUpdate(platoDTO: PlatoDTOUpdate, ingredientesMap: MutableList<Ingrediente>): Plato {
    val plato = Plato(
        nombre = platoDTO.nombre,
        descripcion = platoDTO.descripcion,
        urldeImagen = platoDTO.imagen,
        esDeAutor = platoDTO.esDeAutor,
        ingredientes = ingredientesMap
    ).apply {
        this.id = this@fromDTOUpdate.id
        setValorBase(platoDTO.valorBase)
    }
    return plato
}

fun Plato.fromDTO(platoDTO: PlatoDTO): Plato {
    val plato = Plato(
        nombre = platoDTO.nombre,
        descripcion = platoDTO.descripcion,
        urldeImagen = platoDTO.imagen,
        esDeAutor = platoDTO.esDeAutor,
        ingredientes = platoDTO.ingredientes.map { it.toDOM() }.toMutableList()
    ).apply {
        this.id = this@fromDTO.id
        setValorBase(platoDTO.valorBase)
    }
    return plato
}