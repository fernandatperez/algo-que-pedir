/* eslint-disable prefer-const */
import type { Order } from '$lib/domain/order'
import { ensalada, hamburguesa, pizza } from '$lib/data/mock/menuItems'

// Ejemplo de pedido
let firstOrder: Order = {
  id: 12345,
  nombreCliente: 'Sofía Miller',
  usuarioCliente: 'smiller2006',
  direccionCliente: 'Av siempre viva 555',
  latitudCliente: '40.7128',
  longitudCliente: '-74.006',
  
  platos: [hamburguesa, pizza, ensalada], // Lista de Platos
  precioSubtotal() { // Suma de Precios de Platos
    return this.platos.reduce((accumulator, currentItem) => {
      return accumulator + currentItem.precio
    }, 0)
  },
  comisionDelivery: 2.0, // Asi o porcentaje ??
  recargoPago() { 
    if (this.tipoPago == 'EFECTIVO') {
      return 1
    } else {
      return 1.1
    }
  }, // Recargo del 10%, solo cuando no es EFVO
  precioTotal() { return this.precioSubtotal() * this.recargoPago()  + this.comisionDelivery  }, // Calculado (metodo)
  tipoPago: 'TRANSFERENCIA',
  estado: 'PENDIENTE',
  createdAt: new Date(),
}

// Otro pedido
let secondOrder: Order = {
  id: 54545,
  nombreCliente: 'Ricardo Fort',
  usuarioCliente: 'rickyricon',
  direccionCliente: 'Maiame 354',
  latitudCliente: '25.77427',
  longitudCliente: '-80.19366',
  
  platos: [hamburguesa, ensalada], // Lista de Platos
  precioSubtotal() { // Suma de Precios de Platos
    return this.platos.reduce((accumulator, currentItem) => {
      return accumulator + currentItem.precio
    }, 0)
  },
  comisionDelivery: 12.0,
  recargoPago() { 
    if (this.tipoPago == 'EFECTIVO') {
      return 1
    } else {
      return 1.1
    }
  }, // Recargo del 10%, solo cuando no es EFVO
  precioTotal() { return this.precioSubtotal() * this.recargoPago()  + this.comisionDelivery  }, // Calculado (metodo)
  tipoPago: 'EFECTIVO',
  estado: 'PENDIENTE',
  createdAt: new Date(),
}

// Otro pedido
let thirdOrder: Order = {
  id: 7853,
  nombreCliente: 'Alex Caniggia',
  usuarioCliente: 'fuerabarat',
  direccionCliente: 'Roma, Italia',
  latitudCliente: '41.89193',
  longitudCliente: '12.51133',
  
  platos: [pizza, ensalada, hamburguesa], // Lista de Platos
  precioSubtotal() { // Suma de Precios de Platos
    return this.platos.reduce((accumulator, currentItem) => {
      return accumulator + currentItem.precio
    }, 0)
  },
  comisionDelivery: 18.0,
  recargoPago() { 
    if (this.tipoPago == 'EFECTIVO') {
      return 1
    } else {
      return 1.1
    }
  }, // Recargo del 10%, solo cuando no es EFVO
  precioTotal() { return this.precioSubtotal() * this.recargoPago()  + this.comisionDelivery  }, // Calculado (metodo)
  tipoPago: 'TRANSFERENCIA',
  estado: 'PREPARADO',
  createdAt: new Date(),
}

// Lista de type Order
export const ORDERS_MOCK : Order[] = [firstOrder, secondOrder, thirdOrder]