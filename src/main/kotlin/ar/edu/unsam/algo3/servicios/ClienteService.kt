package ar.edu.unsam.algo3.servicios;

import ar.edu.unsam.algo3.dto.toDTO
import ar.edu.unsam.algo3.modelo.local.Local
import ar.edu.unsam.algo3.modelo.pedido.Pedido
import ar.edu.unsam.algo3.repositorio.RepositorioCliente
import org.springframework.stereotype.Service;

@Service
class ClienteService(
    val repositorioClientes: RepositorioCliente
) {

    fun obtenerLocalesPuntuables(id: Int): MutableSet<Local> {
        val cliente = repositorioClientes.obtenerObjeto(id)
        val localesPuntuables: MutableSet<Local> = cliente.obtenerLocalesAPuntuar()
        return localesPuntuables
    }

    fun confirmarPedido(clienteID: Int, pedidoID: Int): Int {
        return 1
    }

}