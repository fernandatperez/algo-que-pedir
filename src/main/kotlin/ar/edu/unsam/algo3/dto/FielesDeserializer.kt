package ar.edu.unsam.algo3.dto

import ar.edu.unsam.algo3.modelo.usuario.Fieles
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import org.springframework.stereotype.Component

class FielesDeserializer(
    private val localRepo: RepositorioLocal
) : StdDeserializer<Fieles>(Fieles::class.java) {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Fieles {
        val node: JsonNode = p.codec.readTree(p)

        val fieles = Fieles()

        val localesNode = node.get("localesFavoritos")
        if (localesNode != null && localesNode.isArray) {
            localesNode.forEach { localNode ->
                // Leés el id del LocalDomDTO
                val id = localNode.get("id")?.asInt() // TODO comentar esto, porque es un array de INTS.
                if (id != null) {
                    // Buscás el Local de dominio en tu repositorio
                    val local = localRepo.obtenerObjeto(id)
                    fieles.agregarLocalFavorito(local)
                }
            }
        }

        return fieles
    }
}
