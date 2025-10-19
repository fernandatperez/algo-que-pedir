package ar.edu.unsam.algo3.modelo.plato

import ar.edu.unsam.algo3.mock.LocalPollos
import ar.edu.unsam.algo3.modelo.local.Local
// Armar DTO aparte para el menu

// Esto seria edicion de plato. Falta armar el return de plato
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
    val ingredientes: List<Int>,
    val store: Local = LocalPollos, // Esto deberia ser DTO tambien
)

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
        ingredientes = this.ingredientes.map{ it.id},
//        store = this.local.toResponse(), // Cuando tengamos lo mismo
        store = this.local
    )
}

fun Plato.fromDTO(platoDTO: PlatoDTO): Plato {
    val ingredientesDom = repositorioIngredientes
    return Plato(
        nombre = platoDTO.nombre,
        descripcion = platoDTO.descripcion,
        urldeImagen = platoDTO.imagen,
        esDeAutor = platoDTO.esDeAutor,
        ingredientes = ingredientesDom
    ).apply {
        this.id = this@fromDTO.id
        setValorBase(platoDTO.valorBase)
    }
}