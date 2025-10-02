package ar.edu.unsam.algo2.algoQuePedir
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// defino los grupos alimenticios en una clase enumerada
// Mayúscula y separado por _ : SCREAMING_SNAKE_CASE

enum class GrupoAlimenticio {
    CEREALES_Y_TUBERCULOS,
    AZUCARES_Y_DULCES,
    LACTEOS,
    FRUTAS_Y_VERDURAS,
    GRASAS_Y_ACEITES,
    PROTEINAS
}

@Serializable
data class Ingrediente(
    val nombre: String = "",
    @SerialName("costo")
    var costoMercado: Double = 0.0, // puede ser decimal
    @SerialName("origenAnimal")
    val esOrigenAnimal: Boolean = true,
    @SerialName("grupo")
    val grupoAlimenticio: GrupoAlimenticio = GrupoAlimenticio.CEREALES_Y_TUBERCULOS // Para tener algun default, null no me gusta
) : ElementoDeRepositorio {

    //    ========== Metodos de busqueda del repositorio ==============
    override var id = 0

    override fun cumpleCriterioDeBusqueda(criterio: String): Boolean = coincideTotalmenteCon(criterio, nombre)

    override fun cumpleCriterioDeCreacion(): Boolean =
        noEstaVacio(nombre)
}