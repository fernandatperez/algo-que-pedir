package ar.edu.unsam.algo2.algoQuePedir

// STRATEGY
interface CriterioCliente {
    fun puedePedir(plato: Plato, usuario: Usuario): Boolean

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
class Fieles : CriterioCliente {
    val localesFavoritos: MutableSet<Local> = mutableSetOf()

    fun agregarLocalFavorito(local: Local) {
        localesFavoritos.add(local)
    }

    override fun puedePedir(plato: Plato, usuario: Usuario) = localesFavoritos.contains(plato.local)
}

// Los que se dejan llevar por el marketing si en la descripción del plato tienen las palabras/frases como "nutritivo", “bajo en sodio”, “sin azúcar”.
// STATEFUL
class Consumista : CriterioCliente {
    val frasesFavoritas: MutableSet<String> = mutableSetOf() // Estado mutable

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
class Combinado(var criterios: MutableSet<CriterioCliente>) : CriterioCliente {
    override fun puedePedir(plato: Plato, usuario: Usuario) =
        this.criterios.all { it.puedePedir(plato, usuario) }
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


