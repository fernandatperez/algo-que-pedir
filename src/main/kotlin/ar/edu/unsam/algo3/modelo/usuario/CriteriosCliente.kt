package ar.edu.unsam.algo3.modelo.usuario

import ar.edu.unsam.algo3.dto.FielesDeserializer
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.plato.Plato
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

// STRATEGY
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    Type(value = Vegano::class, name = "vegano"),
    Type(value = Fieles::class, name = "fieles"),
    Type(value = Exquisito::class, name = "exquisito"),
    Type(value = Conservador::class, name = "conservador"),
    Type(value = Impaciente::class, name = "impaciente"),
    Type(value = Generalista::class, name = "generalista"),
    Type(value = Combinado::class, name = "combinado"),
    Type(value = Consumista::class, name = "consumista"),
    Type(value = CambianteSegunEdad::class, name = "cambianteSegunEdad")
)
interface CriterioCliente {
    fun puedePedir(plato: Plato, usuario: Usuario): Boolean
    // fun getLocalesPreferidos(): List<Local> = mutableListOf()
}

// Están los usuarios veganos, los cuales no quieren platos de origen animal.
// STATELESS
object Vegano : CriterioCliente {
    override fun puedePedir(plato: Plato, usuario: Usuario) =
        !plato.esDeOrigenAnimal()
}

// Hay algunos que son más exquisitos y tienen el paladar más refinado y solo quieren platos de autor.
// STATELESS
object Exquisito : CriterioCliente {
    override fun puedePedir(plato: Plato, usuario: Usuario) =
        plato.esDeAutor
}

// Están los conservadores, estos van a lo seguro y quieren que el plato solo tenga ingredientes de sus preferidos.
// STATELESS
object Conservador : CriterioCliente {
    override fun puedePedir(plato: Plato, usuario: Usuario) =
        plato.ingredientes.all { usuario.esIngredientePreferido(it) }
}

// Los fieles, mientras que el plato esté elaborado por uno de sus locales preferidos.
// STATEFUL
@JsonDeserialize(using = FielesDeserializer::class) // porque necesitamos deserializar LocalDomDTO > Local
class Fieles : CriterioCliente {

    @JsonProperty("localesFavoritos")
    val localesFavoritos: MutableSet<Local> = mutableSetOf()

    fun agregarLocalFavorito(local: Local) {
        localesFavoritos.add(local)
    }

    override fun puedePedir(plato: Plato, usuario: Usuario) = localesFavoritos.contains(plato.local)

//    override fun getLocalesPreferidos(): List<Local> = localesFavoritos.toList()
}

// Los que se dejan llevar por el marketing si en la descripción del plato tienen las palabras/frases como "nutritivo", “bajo en sodio”, “sin azúcar”.
// STATEFUL
class Consumista @JsonCreator constructor(
    @param:JsonProperty("frasesFavoritas")
    val frasesFavoritas: MutableSet<String> = mutableSetOf() // Estado mutable
) : CriterioCliente {
    fun agregarFrasesFavoritas(frase: String) {
        frasesFavoritas.add(frase) // Modifica el estado
    }

    override fun puedePedir(plato: Plato, usuario: Usuario) =
        frasesFavoritas.any { frase -> frase in plato.descripcion }
}

// Los impacientes, como quieren recibir rápido su pedido, solo quieren platos de los locales cercanos.
// STATELESS
object Impaciente : CriterioCliente {
    override fun puedePedir(plato: Plato, usuario: Usuario) =
        usuario.esCercano(plato.local)
}

// -----------------------

// STATELESS
object Generalista : CriterioCliente {
    override fun puedePedir(plato: Plato, usuario: Usuario) = true
} // solo le interesa lo basico

// STATEFUL
class Combinado @JsonCreator constructor(
    @param:JsonProperty("criterios")
    var criterios: MutableSet<CriterioCliente>
) : CriterioCliente {
    override fun puedePedir(plato: Plato, usuario: Usuario) =
        this.criterios.all { it.puedePedir(plato, usuario) }

//    override fun getLocalesPreferidos(): List<Local> = criterios.flatMap{ it.getLocalesPreferidos() }
}

// STATEFUL
object CambianteSegunEdad : CriterioCliente {
    override fun puedePedir(plato: Plato, usuario: Usuario): Boolean =
        criterioSegunEdad(usuario).puedePedir(plato, usuario)

    private fun criterioSegunEdad(usuario: Usuario): CriterioCliente {
        return if (usuario.edad() % 2.0 == 0.0) {
            Exquisito
        } else {
            Conservador
        }
    }
}

