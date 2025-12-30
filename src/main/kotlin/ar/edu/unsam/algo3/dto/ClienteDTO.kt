package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.usuario.Combinado
import ar.edu.unsam.algo3.modelo.usuario.Conservador
import ar.edu.unsam.algo3.modelo.usuario.CriterioCliente
import ar.edu.unsam.algo3.modelo.usuario.Exquisito
import ar.edu.unsam.algo3.modelo.usuario.Fieles
import ar.edu.unsam.algo3.modelo.usuario.Generalista
import ar.edu.unsam.algo3.modelo.usuario.Impaciente
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.modelo.usuario.Vegano
import ar.edu.unsam.algo3.modelo.utils.Direccion
import kotlinx.serialization.internal.throwMissingFieldException
import org.uqbar.geodds.Point

data class ClientePerfilDTO(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val address: String,
    val location: Int,
    val latitude: Double,
    val longitude: Double,
    val ingredientsToAvoid: MutableSet<IngredienteDTO>,
    val preferredIngredients: MutableSet<IngredienteDTO>,
    val maxDistance: Double,
    // Recibe un mapa con string -> tipoDeCliente y Any -> Boolean, String...
    val criteria: CriterioCliente
)

fun Usuario.toUsuarioDTO(): ClientePerfilDTO{

    val usuarioDTO = ClientePerfilDTO(
        id = this.id,
        name = this.nombre,
        lastName = this.apellido,
        email = this.mailPrincipal,
        address = this.direccion.calle,
        location = this.direccion.altura,
        latitude = this.direccion.ubicacion.y,
        longitude = this.direccion.ubicacion.x,
        ingredientsToAvoid = this.ingredientesEvitar.map { it.toDTO() }.toMutableSet(),
        preferredIngredients = this.ingredientesPreferidos.map { it.toDTO() }.toMutableSet(),
        maxDistance = this.distanciaMaxima,
        criteria = this.tipoDeCliente // Asignamos la lista de nombres (Strings)
    )
    return usuarioDTO
}

fun ClientePerfilDTO.fromDTO(): Usuario {

    val usuario = Usuario(
        nombre = this.name,
        apellido = this.lastName,
        mailPrincipal = this.email,
        direccion = Direccion(
            calle = this.address,
            altura = this.location,
            ubicacion = Point(this.longitude, this.latitude)
        ),
        tipoDeCliente = this.criteria,
        distanciaMaxima = this.maxDistance
    ).apply {
        id = this@fromDTO.id

        this@fromDTO.ingredientsToAvoid.forEach { agregarEvitar(it.fromDTO()) }
        this@fromDTO.preferredIngredients.forEach { agregarPreferido(it.fromDTO()) }
    }

    return usuario
}
