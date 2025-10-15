import { orderService } from '$lib/services/orderService'
import { redirect } from '@sveltejs/kit'

export async function load({ params }) {
  try {
    const id = Number(params.id)
    const order = await orderService.getOrderByID(id)
    // eslint-disable-next-line no-console
    // console.log('Order loaded:', order)

    if (order.id == -1) {
      // Si el pedido no existe te redirige a orders
      throw redirect(302, 'orders')
    }
    return {order}
  } catch (error) {
    // eslint-disable-next-line no-console
    console.error('Error al cargar el pedido', error)
    throw redirect(302, '/orders')
  }
}