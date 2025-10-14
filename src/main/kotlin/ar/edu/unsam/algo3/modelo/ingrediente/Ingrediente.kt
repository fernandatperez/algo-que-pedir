package ar.edu.unsam.algo3.modelo.ingrediente
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.repositorio.ElementoDeRepositorio
import com.fasterxml.jackson.annotation.JsonValue
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// defino los grupos alimenticios en una clase enumerada
// Mayúscula y separado por _ : SCREAMING_SNAKE_CASE

enum class GrupoAlimenticio(val nombre: String) {
    FRUTAS_Y_VERDURAS("Frutas y Verduras"),
    PROTEINAS("Proteínas"),
    CEREALES_Y_TUBERCULOS("Cereales y tuberculos"),
    LACTEOS("Lácteos"),
    GRASAS_Y_ACEITES("Grasas y aceites"),
    AZUCARES_Y_DULCES("Azucares y dulces");

    @JsonValue
    fun nombreAMostrar() = nombre
}

@Serializable
open class Ingrediente(
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