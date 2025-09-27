import { Order, type OrderJSON } from '../type/order'
import { ORDERS_MOCK } from '$lib/data/mock/orders'

class OrderService {

  async getOrders() {
    // llamada al back
    // orders = axios.get(urlOrders)
    return ORDERS_MOCK.map(Order.fromJSON)
  }

  // Orders filtradas
  async getFilteredOrders(state: string) {
    // order = axios.get(urlOrders/${state})
    const orders = ORDERS_MOCK.filter((order) => order.state == state)
    return orders.map(Order.fromJSON)
  }

  async getOrderByID(id: number){
    // order = axios.get(urlOrders/${id})
    const order = ORDERS_MOCK.find((order) => order.id == id)

    if (order != null) {
      return Order.fromJSON(order)
    }
    return new Order() // con id = -1 (no existe, procesar arriba)
  }

  async updateOrderState(order: Order) {
    // axios.put()
    // const foundOrder = ORDERS_MOCK.find((orderJSON) => orderJSON.id == order.id)
    const index = ORDERS_MOCK.findIndex((o) => o.id === order.id)
    // if (index === -1) {
    //   return { error: 'Pedido no encontrado' }
    // }
    ORDERS_MOCK[index] = order.toJSON()
    return Order.fromJSON(ORDERS_MOCK[index])
  }

}

export const orderService = new OrderService()