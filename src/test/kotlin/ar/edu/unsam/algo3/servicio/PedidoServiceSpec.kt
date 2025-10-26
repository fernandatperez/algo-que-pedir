package ar.edu.unsam.algo3.servicio

import ar.edu.unsam.algo3.dto.PedidoDTO
import ar.edu.unsam.algo3.errores.NotFoundException
import ar.edu.unsam.algo3.modelo.local.Pago
import ar.edu.unsam.algo3.modelo.pedido.Estado
import ar.edu.unsam.algo3.repositorio.RepositorioPedido
import ar.edu.unsam.algo3.servicios.PedidoService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest



@SpringBootTest
class PedidoServiceSpec {
    @Autowired
    lateinit var repositorioPedidos : RepositorioPedido

    @Autowired
    lateinit var servicio: PedidoService

    @Test
    fun `pedidosFiltrados retorna pedidos por estado y email`() {

        val servicio = PedidoService(repositorioPedidos)
        val estado = Estado.PENDIENTE
        val email = "rickyricon"

        val resultado = servicio.pedidosFiltradosLocal(estado, email)


        assertNotNull(resultado)

        resultado.forEach { pedidoDTO ->
            assertEquals(estado, pedidoDTO.estado)
            assertEquals(email, pedidoDTO.username)
        }

    }

    @Test
    fun `pedidosFiltrados con email inexistente retorna lista vacia`() {

        val servicio = PedidoService(repositorioPedidos)
        val estado = Estado.PENDIENTE
        val emailInexistente = "noexiste@ejemplo.com"


        val resultado = servicio.pedidosFiltradosLocal(estado, emailInexistente)

        assertNotNull(resultado)
        assertTrue(resultado.isEmpty())
    }
    @Test
    fun `buscarPorID con id existente retorna pedido`() {

        val servicio = PedidoService(repositorioPedidos)
        val idExistente = 1

        val resultado = servicio.buscarPorID(idExistente)


        assertNotNull(resultado)
        assertEquals(idExistente, resultado.id)
    }

    @Test
    fun `buscarPorID con id inexistente lanza excepcion`() {

        val servicio = PedidoService(repositorioPedidos)
        val idInexistente = 9999


        assertThrows<NotFoundException> { servicio.buscarPorID(idInexistente)}
    }
//    @Test
//    fun `actualizarEstado con pedido existente actualiza y retorna DTO`() {
//
//        val servicio = PedidoService(repositorioPedidos)
//
//
//        val pedidoExistente = servicio.buscarPorID(1)
//        val nuevoEstado = Estado.PREPARADO // Estado diferente al actual
//
//        val pedidoDTOActualizado = PedidoDTO(
//            id = pedidoExistente.id,
//            estado = nuevoEstado,
//            username = "rickyricon",
//            nombre = "rickyricon",
//            deliveryComission = 5.0,
//            direccion = "direccion",
//            altura = 123,
//            long = "10.0",
//            lat = "10.0",
//            metodoDePago = Pago.QR,
//            horarioEntrega = "TARDE",
//        )
//
//        val resultado = servicio.actualizarEstado(pedidoDTOActualizado)
//
//
//        assertNotNull(resultado)
//        assertEquals(pedidoExistente.id, resultado.id)
//        assertEquals(nuevoEstado, resultado.estado)
//    }

    @Test
    fun `actualizarEstado con pedido inexistente lanza excepcion`() {

        val servicio = PedidoService(repositorioPedidos)
        val pedidoInexistenteDTO = PedidoDTO(
            id = 9999,
            estado = Estado.CANCELADO,
            username = "rickyricon",
            nombre = "rickyricon",
            deliveryComission = 5.0,
            direccion = "direccion",
            altura = 123,
            long = "10.0",
            lat = "10.0",
            metodoDePago = Pago.QR,
            horarioEntrega = "TARDE",
        )


        assertThrows<NotFoundException> {
            servicio.actualizarEstado(pedidoInexistenteDTO.id)
        }
    }
}