import { Order } from '../domain/order'
import { firstOrder, thirdOrder } from '../data/mock/orders'
import { describe, it, expect } from 'vitest'
import { render, screen, waitFor } from '@testing-library/svelte'

import OrderCard from '../components/OrderCard.svelte'
import orderPage from '../../routes/(authed)/orders/+page.svelte'

describe('order card', () => {

  // este va a ser un async
  it('La card muestra los datos correctamente', () => {
    render(OrderCard, {order: Order.fromJSON(firstOrder)})
    const orderId = screen.getByTestId('order-id').innerHTML
    expect(orderId).toBe('Pedido #12345')
  })

  // CAMBIAR? En realidad quiero saber si el anchor navega correctamente
  it('renderiza link con la URL correcta', () => {
    render(OrderCard, {order: Order.fromJSON(firstOrder)})
    const link = screen.getByTestId('goto-detail')
    expect(link).toHaveAttribute('href', '/order-detail/12345')
  })
  
  it('La card te lleva el detalle del pedido', () => {
    render(OrderCard, {order: Order.fromJSON(firstOrder)})
    screen.getByTestId('goto-detail').click()
    const orderId = screen.getByTestId('order-id').innerHTML
    expect(orderId).toBe('Pedido #12345')
  })

  it('El boton de preparar esta disabled cuando el pedido esta PREPARADO', () => {
    render(OrderCard, {order: Order.fromJSON(thirdOrder)})
    expect(screen.getByTestId('preparar-12345')).toHaveAttribute('disabled')
  })

  // async para funcion onclick que cambia estado
  it('El boton de preparar cambia el estado del pedido a PREPARADO', async () => {
    render(orderPage)
    render(OrderCard, {order: Order.fromJSON(firstOrder)})
    screen.getByTestId('preparar-12345').click()
    screen.getByTestId('btn-PREPARADO').click()
    await waitFor(() => {
      expect(screen.getByTestId('preparar-12345')).toHaveAttribute('disabled') // bueno no se porque el disabled no se le pone reactivamente....
    })
  })

})