package ar.edu.unsam.algo3.servicios;

import ar.edu.unsam.algo3.dto.CalificacionDTO
import ar.edu.unsam.algo3.dto.ClientePerfilDTO
import ar.edu.unsam.algo3.dto.IngredienteDTO
import ar.edu.unsam.algo3.dto.OrderDTO
import ar.edu.unsam.algo3.dto.fromDTO
import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.errores.BusinessException
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.ingrediente.Ingrediente
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.plato.Plato
import ar.edu.unsam.algo3.modelo.usuario.Calificacion
import ar.edu.unsam.algo3.modelo.usuario.Usuario
import ar.edu.unsam.algo3.repositorio.RepositorioCliente
import ar.edu.unsam.algo3.repositorio.RepositorioIngrediente
import ar.edu.unsam.algo3.repositorio.RepositorioLocal
import ar.edu.unsam.algo3.repositorio.RepositorioPedido
import ar.edu.unsam.algo3.repositorio.RepositorioPlato
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service;

@Service
class ClienteService(
    val repositorioClientes: RepositorioCliente,
    val repositorioIngredientes: RepositorioIngrediente,
    val repositorioLocales: RepositorioLocal,
    val repositorioPedido: RepositorioPedido,
    val repositorioPlatos: RepositorioPlato
) {
    fun clientePorId(id: Int): Usuario =
        repositorioClientes.obtenerObjeto(id) ?: throw NotFoundException("No se encontró el cliente de id <$id>")

    fun actualizarPerfil(perfilActualizado: ClientePerfilDTO): Usuario{
        val perfilAModificar = perfilActualizado.fromDTO()
        perfilAModificar.cumpleCriterioDeCreacion()
        repositorioClientes.actualizar(perfilAModificar)

        return perfilAModificar
    }

    fun obtenerLocalesPuntuables(id: Int): MutableSet<Local> {
        val cliente = repositorioClientes.obtenerObjeto(id)
        val localesPuntuables: MutableSet<Local> = cliente.obtenerLocalesAPuntuar()
        return localesPuntuables
    }

    fun confirmarPedidoDeUsuario(id: Int, userId: Int): OrderDTO {
        val user = repositorioClientes.obtenerObjeto(userId)
        val order = repositorioPedido.buscarPorId(id)
        val platos: List<Plato> = order.platos.map{ repositorioPlatos.obtenerObjeto(it.id) }

        if (order.platos.sumOf { it.valorVenta() } != platos.sumOf { it.valorVenta() }) throw BusinessException("El valor de un plato cambio. Mal ahi.")
        user.confirmarPedido(order)
        return OrderDTO(
            user.id,
            order.local.id,
            order.platos.map { it.id }.toMutableList(),
            order.medioDePagoElegido.toString(),
            order.estado.toString(),
            order.costoBasePlatos()
        )
    }

    fun cancelarOrdenDeUsuario(id: Int, userId: Int): OrderDTO {
        val user = repositorioClientes.obtenerObjeto(userId)
        val order = repositorioPedido.buscarPorId(id)
        user.cancelarPedido(order)
        return OrderDTO(
            user.id,
            order.local.id,
            order.platos.map { it.id }.toMutableList(),
            order.medioDePagoElegido.toString(),
            order.estado.toString(),
            order.costoBasePlatos()
        )
    }

    fun puntuarLocal(clienteID: Int, localID: Int, calificacionDTO: CalificacionDTO): CalificacionDTO {
        val usuario: Usuario = repositorioClientes.obtenerObjeto(clienteID)
        val local = repositorioLocales.obtenerObjeto(localID)
        val calificacion = Calificacion.fromDTO(calificacionDTO)
        usuario.puntuarLocal(local, calificacion)
        return calificacion.toDTO()
    }

    fun obtenerIngredientesPorCriterio(id: Int, criterio: String): Set<IngredienteDTO> {
        val cliente = repositorioClientes.obtenerObjeto(id)

        if ( criterio == "avoid" ) return cliente.ingredientesEvitar.map { it.toDTO() }.toSet()
        if ( criterio == "prefers" ) return cliente.ingredientesPreferidos.map { it.toDTO() }.toSet()
        else throw NotFoundException("No se encontró el criterio <$criterio>")
    }

    fun obtenerIngredientesDisponibles(id: Int): Set<IngredienteDTO>{
        val cliente = repositorioClientes.obtenerObjeto(id)
        val ingredientes = repositorioIngredientes.objetosDeRepositorio()
        val ingredientesDisponibles = ingredientes.filter { !cliente.esIngredientePreferidoPorID(it.id) && !cliente.esIngredienteAEvitarPorID(it.id)}

        return ingredientesDisponibles.map { it.toDTO() }.toSet()
    }

    fun actualizarIngredientesPorCriterio(id: Int, criterio: String, ingredientes: List<Ingrediente>): Set<IngredienteDTO> {
        val cliente = repositorioClientes.obtenerObjeto(id)

        when (criterio) {
            "avoid" -> ingredientes.forEach { cliente.agregarEvitar(it)}
            "prefers" -> ingredientes.forEach { cliente.agregarPreferido(it) }
            else -> throw NotFoundException("No se encontró el criterio <$criterio>")
        }

        repositorioClientes.actualizar(cliente)

        return ingredientes.map { it.toDTO() }.toSet()
    }

}