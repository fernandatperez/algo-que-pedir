package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.plato.Plato
import java.time.LocalDate

// Armar DTO aparte para el menu
data class PlatoMenuDTO(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String,
    val local: String,
)

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
    val ingredientes: List<IngredienteDTO>,
    val porcentajeDescuento: Double,
    var fechaDeCreacion: String
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
        enPromocion = this.enPromocion,
        ingredientes = this.ingredientes.map { it.toDTO() },
        porcentajeDescuento = this.porcentajeDescuento,
        fechaDeCreacion = this.fechaDeCreacion.toString(),
    )
}

fun Plato.toPlatoMenuDTO(): PlatoMenuDTO {
    return PlatoMenuDTO(
        id = this.id,
        nombre = this.nombre,
        descripcion = this.descripcion,
        precio = this.valorVenta(),
        imagen = this.urldeImagen,
        local = this.local.nombre
    )
}
// Necesito que reciba la lista de ingredientes ya resuelta
fun Plato.fromDTO(platoDTO: PlatoDTO, ingredientesMap: MutableList<Ingrediente>): Plato {
    val plato = Plato(
        nombre = platoDTO.nombre,
        descripcion = platoDTO.descripcion,
        urldeImagen = platoDTO.imagen,
        esDeAutor = platoDTO.esDeAutor,
        ingredientes = ingredientesMap
    ).apply {
        this.id = this@fromDTO.id
        this.enPromocion = platoDTO.enPromocion
        this.porcentajeDescuento = platoDTO.porcentajeDescuento
        this.fechaDeCreacion = LocalDate.parse(platoDTO.fechaDeCreacion)
        setValorBase(platoDTO.valorBase)
    }
    return plato
}