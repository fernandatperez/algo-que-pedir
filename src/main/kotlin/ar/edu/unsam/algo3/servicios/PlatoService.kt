package ar.edu.unsam.algo3.servicios

import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.dto.PlatoDTOUpdate
import ar.edu.unsam.algo3.dto.fromDTOUpdate
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.repositorio.RepositorioIngrediente
import ar.edu.unsam.algo3.repositorio.RepositorioPlato
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PlatoService(
    val repositorioPlatos: RepositorioPlato,
    private val repositorioLocal: RepositorioLocal,
    val repositorioIngredientes: RepositorioIngrediente
) {

    fun getPlatos(mail: String): List<Plato> =
        repositorioPlatos.buscar(mail)

    fun obtenerPlato(id: Int): Plato {
        val platoModelo = repositorioPlatos.obtenerObjeto(id)
        if (platoModelo != null) {
            return platoModelo
        }
        throw NotFoundException("Plato con id $id no existe en el repositorio")
    }

    fun obtenerIngredientes(ids: List<Int>): MutableList<Ingrediente> {
        val ingredientes = ids.map { id ->
            repositorioIngredientes.obtenerObjeto(id)
                ?: throw NotFoundException("Ingrediente con id $id no existe en el repositorio")
        }.toMutableList()
        return ingredientes
    }

    fun crearPlato(platoDTO: PlatoDTOUpdate, mail: String): Plato  {
        val localDePlato = repositorioLocal.findByEmail(mail)

        val ingredientes = this.obtenerIngredientes(platoDTO.ingredientes)

        var platoDOM = Plato(
            nombre = platoDTO.nombre,
            descripcion = platoDTO.descripcion,
            valorBase = platoDTO.precio,
            urldeImagen = platoDTO.imagen,
            esDeAutor = platoDTO.esDeAutor,
            ingredientes = ingredientes,
            local = localDePlato,
            fechaDeCreacion = LocalDate.parse(platoDTO.fechaDeCreacion)
        ).apply {
            porcentajeDescuento = platoDTO.porcentajeDescuento
        }
        platoDOM.crear()
        println(platoDOM.toString())
        repositorioPlatos.crear(platoDOM)

        return platoDOM
    }

    fun modificarPlato(platoNuevo: PlatoDTOUpdate, mail: String): Plato {
        var platoAModificar = this.obtenerPlato(platoNuevo.id)

        val ingredientes = this.obtenerIngredientes(platoNuevo.ingredientes)
        platoAModificar = platoAModificar.fromDTOUpdate(platoNuevo, ingredientes)
        val localDePlato = repositorioLocal.findByEmail(mail)
        platoAModificar.local = localDePlato

        repositorioPlatos.actualizar(platoAModificar)
        return platoAModificar
    }
}