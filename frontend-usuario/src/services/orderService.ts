import { Order, OrderForBack, type OrderJSON } from '../domain/order'
import { REST_SERVER_URL } from './configuration'
import { getAxiosData } from './common'
import axios from 'axios'

class OrderService {
  
  async getOrders() {
    const queryOrders = () => axios.get<OrderJSON[]>(REST_SERVER_URL + '/pedidos')
    return (await getAxiosData(queryOrders)).map(Order.fromJSON)
  }

  async getFilteredOrders(estado: string) {
    const queryByState = () => axios.get<OrderJSON[]>(REST_SERVER_URL + '/pedidos/?estado=' + estado + '&local=' + localStorage.getItem('email'))
    return (await getAxiosData(queryByState)).map((it) => Order.fromJSON(it))
  }

  async getFilteredUserOrders(estado: string) {
    const queryByState = () => axios.get<OrderJSON[]>(REST_SERVER_URL + '/pedidos-usuario/?estado=' + estado + '&usuario=' + localStorage.getItem('email'))
    return (await getAxiosData(queryByState)).map((it) => Order.fromJSON(it))
  }

  async getOrderByID(id: number) {
    const queryById = () => axios.get<OrderJSON>(REST_SERVER_URL + '/pedido/' + id)
    const orderJson = await getAxiosData(queryById)
    if (orderJson != null) {
      return Order.fromJSON(orderJson)
    }
    return new Order() // con id = -1 (no existe, procesar arriba)
  }

  async updateOrderState(id: number) {
    return axios.put<OrderJSON>(REST_SERVER_URL + '/preparar_pedido/' + id)
  }

  async createOrder(order: OrderForBack) {
    return axios.post<OrderJSON>(REST_SERVER_URL + '/create-order/', order)
  }

}

export const orderService = new OrderService()