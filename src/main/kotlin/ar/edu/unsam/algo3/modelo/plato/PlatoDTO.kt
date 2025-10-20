package ar.edu.unsam.algo3.modelo.plato

import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.toDOM
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.mock.LocalPollos
import ar.edu.unsam.algo3.modelo.local.Local
import org.springframework.hateoas.mediatype.alps.Descriptor

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
    val ingredientes: MutableList<IngredienteDTO>,
    val store: Local // Esto deberia ser DTO tambien
    //val fechaCreacion: String,
    //val porcentajeDescuento: Double
)

fun Plato.toDTO(): PlatoDTO {
    return PlatoDTO(
        id = this.id,
        nombre = this.nombre,
        descripcion = this.descripcion,
        precio = this.valorVenta(),
        valorBase = this.getValorBase(),
        imagen = this.urldeImagen,
        costoProduccion = this.costoProduccion(),
        esDeAutor = this.esDeAutor,
        enPromocion = this.esNuevo(),
        ingredientes = this.ingredientes.map{ it -> it.toDTO()}.toMutableList(),
//        store = this.local.toResponse(), // Cuando tengamos lo mismo
        store = this.local
    )
}

fun Plato.fromDTO(platoDTO: PlatoDTO): Plato {
    val ingredientesDom = platoDTO.ingredientes.map { it -> it.toDOM() }.toMutableList()
    return Plato(
        nombre = platoDTO.nombre,
        descripcion = platoDTO.descripcion,
        urldeImagen = platoDTO.imagen,
        esDeAutor = platoDTO.esDeAutor,
        ingredientes = ingredientesDom,
        local = platoDTO.store
    ).apply {
        this.id = this@fromDTO.id
        setValorBase(platoDTO.valorBase)
    }
}