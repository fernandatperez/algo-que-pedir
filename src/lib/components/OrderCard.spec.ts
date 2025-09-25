import type { Order } from '../type/order'
import { firstOrder } from '../data/mock/orders'
import { describe, it } from 'vitest'
import { render } from '@testing-library/svelte'
import OrderCard from '$lib/OrderCard.svelte'


describe('order card', () => {
  const order : Order = firstOrder

  // este va a ser un async
  it('La card muestra los datos correctamente', () => {
    render(OrderCard, {order: firstOrder})
    // screen.getByTestId('order-id')
  })

})