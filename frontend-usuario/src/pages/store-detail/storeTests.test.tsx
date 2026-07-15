import { render, screen, waitFor, fireEvent } from '@testing-library/react'
import { MemoryRouter, Route, Routes } from 'react-router-dom'
import { describe, expect, test, vi, beforeEach, afterEach } from 'vitest'
import StoreDetail from './StoreDetail'
import { storeService } from '../../services/LocalesService'
import { menuItemsService } from '../../services/MenuItemService'
import { CartProvider } from '../../contexts/CartContext'
import { Store, PaymentType } from '../../domain/storeDom'
import { StoreRate } from '../../domain/storeRate'

// mock de los servicios
vi.mock('../../services/LocalesService')
vi.mock('../../services/MenuItemService')

describe('StoreDetail - Tests de Integracion?', () => {
  const mockStoreReviews = [
    new StoreRate(5, 'Excelente'),
    new StoreRate(4, 'Muy bueno'),
  ]

  const mockStore = new Store(
    1,
    'Restaurante Test',
    'https://example.com/store.jpg',
    mockStoreReviews,
    5.00,
    [PaymentType.EFECTIVO, PaymentType.QR],
    250,
    30
  )

  const mockMenuItems = [
    {
      id: 1,
      nombre: 'Pizza Margherita',
      descripcion: 'Pizza clásica con tomate y mozzarella',
      precio: 12.99,
      imagen: 'https://example.com/pizza.jpg',
      tag: 'Popular',
      local: 'Restaurante Test'
    },
    {
      id: 2,
      nombre: 'Pasta Carbonara',
      descripcion: 'Pasta con salsa carbonara',
      precio: 15.50,
      imagen: 'https://example.com/pasta.jpg',
      tag: 'Nuevo',
      local: 'Restaurante Test'
    }
  ]

  const mockReviews = {
    reviewsCut: [
      new StoreRate(5, 'Excelente comida y servicio'),
      new StoreRate(4, 'Muy bueno, recomendado')
    ],
    hasMore: false
  }

  beforeEach(() => {
    localStorage.setItem('id', '1')
    vi.spyOn(storeService, 'getStore').mockResolvedValue(mockStore)
    vi.spyOn(menuItemsService, 'getItemsByStore').mockResolvedValue(mockMenuItems)
    vi.spyOn(storeService, 'getReviewsByStore').mockResolvedValue(mockReviews)
  })

  afterEach(() => {
    vi.restoreAllMocks()
    localStorage.clear()
  })

  const renderWithRouter = (storeId = '1') => {
    return render( // esto de memory es lo que hace que este en la pagina que yo quiero, no tiene que replicar todas las "etiquetas" solo las necesarias
      <MemoryRouter initialEntries={[`/store/${storeId}`]}>
        <CartProvider>
          <Routes>
            <Route path="/store/:id" element={<StoreDetail />} />
          </Routes>
        </CartProvider>
      </MemoryRouter>
    )
  }

  describe('Carga inicial de datos', () => {
    test('debe cargar y mostrar la informacion del restaurante', async () => {
      renderWithRouter()

      await waitFor(() => {
        expect(screen.getByText('4.5 (2 reviews) · 250 pedidos')).toBeTruthy()
        expect(screen.getByTestId('data_Restaurante Test')).toBeTruthy()
      })
    })

    test('debe cargar y mostrar los platos del menu', async () => {
      renderWithRouter()

      await waitFor(() => {
        expect(screen.getByText('Pizza Margherita')).toBeTruthy()
        expect(screen.getByText('Pasta Carbonara')).toBeTruthy()
      })
    })
  })

  describe('Modal de plato y actualizacion de precio', () => {
    test('debe abrir el modal al hacer click en un plato con sus datos', async () => {
      renderWithRouter()

      await waitFor(() => {
        expect(screen.getByText('Pizza Margherita')).toBeTruthy()
      })

      // data-testid para hacer click en el card porque si no no me lo tomaba
      const pizzaCard = screen.getByTestId('dish-card-1')
      fireEvent.click(pizzaCard)

      await waitFor(() => {
        // verifico que el modal se abrio buscando elementos que SOLO aparecen en el modal
        expect(screen.getByText(/Precio unitario/i)).toBeTruthy() // esto: /i es para que sea case insensitve 
        expect(screen.getByText(/Precio total/i)).toBeTruthy()
        expect(screen.getByRole('button', { name: /Cancelar/i })).toBeTruthy()
        expect(screen.getByRole('button', { name: /Agregar al Pedido/i })).toBeTruthy()
      })
    })

    test('al aumentar la cantidad se actualiza el precio total correctamente', async () => {
      renderWithRouter()

      await waitFor(() => {
        expect(screen.getByText('Pizza Margherita')).toBeTruthy()
      })

      const pizzaCard = screen.getByTestId('dish-card-1')
      fireEvent.click(pizzaCard)

      await waitFor(() => {
        expect(screen.getByText(/Precio unitario/i)).toBeTruthy()
      })

      const allButtons = screen.getAllByRole('button')
      const increaseButton = allButtons.find(btn => btn.textContent?.trim() === '+')
      
      expect(increaseButton).toBeTruthy()

      fireEvent.click(increaseButton!) // aumento a 2

      await waitFor(() => {
        // vferifico que el precio total se actualizo a 25.98 (12.99 * 2)
        expect(screen.getByText('2')).toBeTruthy()
        const precioTexts = screen.getAllByText(/25\.98/) // \ esto es porque no toma algunos caracteres
        expect(precioTexts.length).toBeGreaterThan(0)
      })

      // Aumentar cantidad a 3
      fireEvent.click(increaseButton!)

      await waitFor(() => {
        expect(screen.getByText('3')).toBeTruthy() 
        const precioTexts = screen.getAllByText(/38\.97/)
        expect(precioTexts.length).toBeGreaterThan(0)
      })
    })

    test('no debe permitir disminuir la cantidad por debajo de 1', async () => {
      renderWithRouter()

      await waitFor(() => {
        expect(screen.getByText('Pizza Margherita')).toBeTruthy()
      })

      const pizzaCard = screen.getByText('Pizza Margherita').closest('div')
      fireEvent.click(pizzaCard!)

      await waitFor(() => {
        const decreaseButton = screen.getAllByRole('button', { name: '-' })[0]
        expect(decreaseButton.hasAttribute('disabled')).toBeTruthy()
      })
    })
  })

  describe('Boton Ver pedido', () => {
    test('el boton debe estar deshabilitado cuando el carrito esta vacio', async () => {
      renderWithRouter()

      await waitFor(() => {
        const verPedidoButton = screen.getByRole('button', { name: /Ver pedido \(0\)/i })
        expect(verPedidoButton.hasAttribute('disabled')).toBeTruthy()
      })
    })

    test('debe sumar correctamente las cantidades de multiples platos', async () => {
      renderWithRouter()

      await waitFor(() => {
        expect(screen.getByText('Pizza Margherita')).toBeTruthy()
      })

      // Agregar primer plato (2 unidades)
      let pizzaCard = screen.getByText('Pizza Margherita').closest('div')
      fireEvent.click(pizzaCard!)

      await waitFor(() => {
        const increaseButton = screen.getAllByRole('button', { name: '+' })[0]
        fireEvent.click(increaseButton)
      })

      await waitFor(() => {
        const addButton = screen.getByRole('button', { name: /Agregar al Pedido/i })
        fireEvent.click(addButton)
      })

      await waitFor(() => {
        expect(screen.getByText(/Ver pedido \(2\)/i)).toBeTruthy()
      })

      // Agregar segundo plato (3 unidades)
      const pastaCard = screen.getByText('Pasta Carbonara').closest('div')
      fireEvent.click(pastaCard!)

      await waitFor(() => {
        const increaseButton = screen.getAllByRole('button', { name: '+' })[0]
        fireEvent.click(increaseButton)
        fireEvent.click(increaseButton)
      })

      await waitFor(() => {
        const addButton = screen.getByRole('button', { name: /Agregar al Pedido/i })
        fireEvent.click(addButton)
      })

      await waitFor(() => {
        expect(screen.getByText(/Ver pedido \(5\)/i)).toBeTruthy()
      })
    })
  })
})