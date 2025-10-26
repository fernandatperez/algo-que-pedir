import { Order, type OrderJSON } from '../domain/order'
import { REST_SERVER_URL } from './configuration'
import { getAxiosData } from './common'
import axios from 'axios'

class OrderService {

  // async getOrders() {
  //   llamada al back
  //   orders = axios.get(urlOrders)
  //   return ORDERS_MOCK.map(Order.fromJSON)
  // }
  
  async getOrders() {
    const queryOrders = () => axios.get<OrderJSON[]>(REST_SERVER_URL + '/pedidos')
    return (await getAxiosData(queryOrders)).map(Order.fromJSON)
  }

  // Orders filtradas
  // async getFilteredOrders(state: string) {
  //  order = axios.get(urlOrders/${state})
  //  const orders = ORDERS_MOCK.filter((order) => order.state == state)
  //  return orders.map(Order.fromJSON)
  // }
    
  // Orders filtradas
  async getFilteredOrders(estado: string) {
    const queryByState = () => axios.get<OrderJSON[]>(REST_SERVER_URL + '/pedidos/?estado=' + estado + '&local=' + sessionStorage.getItem('email'))
    return (await getAxiosData(queryByState)).map((it) => Order.fromJSON(it))
  }

  //async getOrderByID(id: number){
  //  order = axios.get(urlOrders/${id})
  //  const order = ORDERS_MOCK.find((order) => order.id == id)

  //  if (order != null) {
  //    return Order.fromJSON(order)
  //  }
  //  return new Order() // con id = -1 (no existe, procesar arriba)
  // }

  async getOrderByID(id: number) {
    const queryById = () => axios.get<OrderJSON>(REST_SERVER_URL + '/pedido/' + id)
    const orderJson = await getAxiosData(queryById)
    if (orderJson != null) {
      return Order.fromJSON(orderJson)
    }
    return new Order() // con id = -1 (no existe, procesar arriba)
  }

  //async updateOrderState(order: Order) {
  //   return axios.put<OrderJSON>(REST_SERVER_URL + '/orders/' + order.id, order.toJSON())
  //   const foundOrder = ORDERS_MOCK.find((orderJSON) => orderJSON.id == order.id)
  //  const index = ORDERS_MOCK.findIndex((o) => o.id === order.id)
  //   if (index === -1) {
  //     return { error: 'Pedido no encontrado' }
  //   }
  //  ORDERS_MOCK[index] = order.toJSON()
  //  return Order.fromJSON(ORDERS_MOCK[index])
  // }

  async updateOrderState(id: number) {
    return axios.put<OrderJSON>(REST_SERVER_URL + '/preparar_pedido/' + id)
  }

}

export const orderService = new OrderService()