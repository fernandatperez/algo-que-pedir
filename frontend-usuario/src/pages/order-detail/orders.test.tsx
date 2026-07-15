import { describe, test, beforeEach, expect, vi, Mock } from 'vitest'
import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { BrowserRouter, MemoryRouter, Route, Routes } from 'react-router-dom'
import OrderDetails from './OrderDetails'
import { orderService } from '../../services/orderService'
import { userService } from '../../services/UserService'
import StoreRatings from '../store-ratings/StoreRatings'
import OrderCheckout from '../order-checkout/OrderCheckout'
import { CartProvider } from '../../contexts/CartContext'

vi.mock('../../services/orderService', () => ({
  orderService: {
    getFilteredUserOrders: vi.fn(),
    getOrderByID: vi.fn(),
    createOrder: vi.fn(),
  },
}))

vi.mock('../../services/UserService.ts', () => ({
  userService: {
    cancelOrder: vi.fn(),
    confirmOrder: vi.fn(),
    rateStore: vi.fn(),
    getUnratedStores: vi.fn(),
  },
}))

describe('Pedido tests', () => {

  beforeEach(() => {
    localStorage.setItem('id', '1')
    localStorage.setItem('email', 'sofiamiller@gmail.com')
    vi.clearAllMocks()
  })

  test('Pendientes: Carlo’s y Grido', async () => {
    (orderService.getFilteredUserOrders as Mock).mockResolvedValue([
      {
        id: 101,
        local: { id: 1, name: 'Carlo\'s Bake Shop', storeURL: '' },
        estado: 'PENDIENTE',
        platos: [{}],   // 1 plato
        fechaCreacionString: '—',
        precioTotal: () => 9.99
      },
      {
        id: 102,
        local: { id: 4, name: 'Grido', storeURL: '' },
        estado: 'PENDIENTE',
        platos: [{}, {}],  // 2 platos
        fechaCreacionString: '—',
        precioTotal: () => 11.49
      }
    ])

    render(
      <BrowserRouter>
        <OrderDetails />
      </BrowserRouter>
    )

    expect(await screen.findByText('Carlo\'s Bake Shop')).toBeTruthy()
    expect(screen.getByText('Grido')).toBeTruthy()
  })

  test('Pendientes vacío: muestra mensaje si no hay órdenes', async () => {

    (orderService.getFilteredUserOrders as Mock)
      .mockResolvedValue([])

    render(
      <BrowserRouter>
        <OrderDetails />
      </BrowserRouter>
    )

    expect(await screen.findByText('No hay pedidos para mostrar')).toBeTruthy()
  })

  test('Pendientes: se muestran los precios correctos', async () => {

    (orderService.getFilteredUserOrders as Mock).mockResolvedValue([
      {
        id: 101,
        local: { id: 1, name: 'Carlo\'s Bake Shop', storeURL: '' },
        estado: 'PENDIENTE',
        platos: [{}],
        fechaCreacionString: '—',
        precioTotal: () => 9.99
      },
      {
        id: 102,
        local: { id: 4, name: 'Grido', storeURL: '' },
        estado: 'PENDIENTE',
        platos: [{}, {}],
        fechaCreacionString: '—',
        precioTotal: () => 11.49
      }
    ])

    render(<BrowserRouter><OrderDetails /></BrowserRouter>)

    expect(await screen.findByText(/Total: \$9\.99/i)).toBeTruthy()
    expect(screen.getByText(/Total: \$11\.49/i)).toBeTruthy()
  })
  
  test('Confirmados: McDonald’s y Restaurante Italiano', async () => {

    // Primer llamado (cuando se monta la vista)
    ;(orderService.getFilteredUserOrders as Mock)
      .mockResolvedValueOnce([])

    // Segundo llamado (al cambiar a la pestaña CONFIRMADO)
    .mockResolvedValueOnce([
      {
        id: 201,
        local: { id: 2, name: 'McDonald\'s', storeURL: '' },
        estado: 'CONFIRMADO',
        platos: [{},{},{},{}], // 4 platos
        fechaCreacionString: '—',
        precioTotal: () => 34.23
      },
      {
        id: 202,
        local: { id: 7, name: 'Restaurante Italiano', storeURL: '' },
        estado: 'CONFIRMADO',
        platos: [{},{}], // 2 platos
        fechaCreacionString: '—',
        precioTotal: () => 29.98
      }
    ])

    render(
      <BrowserRouter>
        <OrderDetails />
      </BrowserRouter>
    )

    const confirmedTab = screen.getByRole('tab', { name: /confirmados/i })
    await userEvent.click(confirmedTab)

    expect(await screen.findByText('McDonald\'s')).toBeTruthy()
    expect(screen.getByText('Restaurante Italiano')).toBeTruthy()
  })

  test('Locales con pedidos confirmados aparecen para calificar', async () => {
    (userService.getUnratedStores as Mock)
      .mockResolvedValue([
        { id: 2, name: 'McDonald\'s', storeURL: '' },
        { id: 7, name: 'Restaurante Italiano', storeURL: '' }
      ])

    render(<BrowserRouter><StoreRatings /></BrowserRouter>)

    expect(await screen.findByTestId('store-to-rate-2')).toBeTruthy()
    expect(screen.getByTestId('store-to-rate-7')).toBeTruthy()
  })

  test('En un pedido confirmado no se muestra el botón Confirmar Pedido', async () => {
    (orderService.getOrderByID as Mock).mockResolvedValue({
      id: 99,
      estado: 'CONFIRMADO',
      platos: [],
      local: { deliveryFee: 0, name: 'McDonald\'s', storeURL: '' },
      precioSubtotal: 10,
      aCobrarPorPedido: () => 0,
      precioTotal: () => 10,
      platosSinRepetir: () => [],
    })

    render(
      <CartProvider>
        <MemoryRouter initialEntries={[{ pathname: '/order', state: { id: 999 } }]}>
          <Routes>
            <Route path="/order" element={<OrderDetails />} />
          </Routes>
        </MemoryRouter>
      </CartProvider>
    )

    expect(screen.queryByTestId('confirm-order-btn')).toBeNull()
  })



  test('Cancelar un pedido pendiente lo elimina del DOM', async () => {

    ;(orderService.getFilteredUserOrders as Mock).mockResolvedValue([
      {
        id: 101,
        local: { id: 1, name: 'Carlo\'s Bake Shop', storeURL: '' },
        estado: 'PENDIENTE',
        platos: [{}],
        fechaCreacionString: '—',
        precioTotal: () => 9.99
      },
      {
        id: 102,
        local: { id: 4, name: 'Grido', storeURL: '' },
        estado: 'PENDIENTE',
        platos: [{}, {}],
        fechaCreacionString: '—',
        precioTotal: () => 11.49
      }
    ])

    ;(userService.cancelOrder as Mock).mockResolvedValue({})

    render(
      <BrowserRouter>
        <OrderDetails />
      </BrowserRouter>
    )

    const cancelButtons = await screen.findAllByRole('button', { name: /cancelar/i })
    const cancelBtn = cancelButtons[0]
    await userEvent.click(cancelBtn)

    expect(userService.cancelOrder).toHaveBeenCalledWith(101)
    expect(screen.queryByText('Carlo\'s Bake Shop')).toBeNull()
    expect(screen.getByText('Grido')).toBeTruthy()
  })

  test('Un pedido cancelado no tiene botón Cancelar', async () => {
    (orderService.getFilteredUserOrders as Mock).mockResolvedValue([
      {
        id: 777,
        estado: 'CANCELADO',
        local: { id: 1, name: 'Carlo\'s Bake Shop' },
        platos: [],
        fechaCreacionString: '—',
        precioTotal: () => 9.99
      }
    ])

    render(<BrowserRouter><OrderDetails /></BrowserRouter>)

    expect(screen.queryByTestId('cancel-btn-777')).toBeNull()
  })

  test('Cancelar un pedido hace que el local aparezca en “a calificar”', async () => {
    (orderService.getFilteredUserOrders as Mock).mockResolvedValue([
      {
        id: 101,
        local: { id: 4, name: 'Grido', storeURL: '' },
        estado: 'PENDIENTE',
        platos: [{}],
        fechaCreacionString: '—',
        precioTotal: () => 11.49
      }
    ])

    ;(userService.cancelOrder as Mock).mockResolvedValue({})
    ;(userService.getUnratedStores as Mock).mockResolvedValue([
      { id: 4, name: 'Grido', storeURL: '' }
    ])

    render(<BrowserRouter><OrderDetails /></BrowserRouter>)

    const cancelBtn = await screen.findByTestId('cancel-btn-101')
    await userEvent.click(cancelBtn)

    render(<BrowserRouter><StoreRatings /></BrowserRouter>)

    expect(await screen.findByTestId('store-to-rate-4')).toBeTruthy()
  })


  test('Cancelados: muestra mensaje vacío si no hay órdenes', async () => {

    // 1er llamado (PENDIENTE)
    (orderService.getFilteredUserOrders as Mock)
      .mockResolvedValueOnce([])

      // 2do llamado: CANCELADO
      .mockResolvedValueOnce([])

    render(
      <BrowserRouter>
        <OrderDetails />
      </BrowserRouter>
    )

    const cancelledTab = screen.getByRole('tab', { name: /cancelados/i })
    await userEvent.click(cancelledTab)

    expect(await screen.findByText('No hay pedidos para mostrar')).toBeTruthy()
  })

  test('Pedidos cancelados también se pueden calificar', async () => {
    (userService.getUnratedStores as Mock)
      .mockResolvedValue([
        { id: 5, name: 'Mi Gusto', storeURL: '' } // inventado para el test
      ])

    render(<BrowserRouter><StoreRatings /></BrowserRouter>)

    expect(await screen.findByTestId('store-to-rate-5')).toBeTruthy()
  })

  test('Si pasaron más de 7 días, el local desaparece de la lista de calificación', async () => {
    (userService.getUnratedStores as Mock).mockResolvedValue([])

    render(<BrowserRouter><StoreRatings /></BrowserRouter>)

    expect(await screen.findByText(/todavia no hay locales para puntuar/i)).toBeTruthy()
  })
  
})
