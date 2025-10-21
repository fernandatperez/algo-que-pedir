import { showError } from '$lib/domain/errorHandler.js'
import { orderService } from '$lib/services/orderService'
import { redirect } from '@sveltejs/kit'

export async function load({ params }) {
  try {
    const id = Number(params.id)
    const order = await orderService.getOrderByID(id)
    // eslint-disable-next-line no-console
    // console.log('Order loaded:', order)
    return {order}
  } catch (error) {
    // eslint-disable-next-line no-console
    showError('Error al cargar el pedido', error)
    throw redirect(302, '/orders')
  }
}