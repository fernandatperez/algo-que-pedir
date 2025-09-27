/* eslint-disable prefer-const */
import { type OrderJSON, Estado, Pago } from '$lib/type/order'
import { ensalada, hamburguesa, pizza } from '$lib/data/mock/menu-items'

// Ejemplo de pedido JSON
let firstOrder: OrderJSON = {
  id: 12345,
  name: 'Sofía Miller',
  user: 'smiller2006',
  address: 'Av siempre viva 555',
  lat: '40.7128',
  long: '-74.006',
  dishes: [hamburguesa, pizza, ensalada], // Lista de Platos
  deliveryComission: 2.0, // Asi o porcentaje ??
  paymentMethod: Pago.TRANSFERENCIA,
  state: Estado.PENDIENTE,
  createdAt: new Date().toLocaleString(),
}

// Otro pedido
let secondOrder: OrderJSON = {
  id: 54545,
  name: 'Ricardo Fort',
  user: 'rickyricon',
  address: 'Maiame 354',
  lat: '25.77427',
  long: '-80.19366',
  dishes: [hamburguesa, ensalada], // Lista de Platos
  deliveryComission: 2.0, // Asi o porcentaje ??
  paymentMethod: Pago.EFECTIVO,
  state: Estado.PENDIENTE,
  createdAt: new Date().toLocaleString(),
}

// Otro pedido
let thirdOrder: OrderJSON = {
  id: 7853,
  name: 'Alex Caniggia',
  user: 'fuerabarat',
  address: 'Roma, Italia',
  lat: '41.89193',
  long: '12.51133',
  dishes: [pizza, ensalada, hamburguesa], // Lista de Platos
  deliveryComission: 2.0, // Asi o porcentaje ??
  paymentMethod: Pago.TRANSFERENCIA,
  state: Estado.PREPARADO,
  createdAt: new Date().toLocaleString(),
}

// Lista de type OrderJSON
export const ORDERS_MOCK : OrderJSON[] = [firstOrder, secondOrder, thirdOrder]