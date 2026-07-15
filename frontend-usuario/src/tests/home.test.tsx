
import { describe, expect, test, vi, beforeEach } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import { BrowserRouter } from 'react-router-dom'


// Mock de axios
vi.mock('axios', () => ({
  default: {
    post: vi.fn(() => Promise.resolve({ data: [] }))
  }
}))

// Mock del servicio
const mockGetStores = vi.fn()
vi.mock('../../services/LocalesService', () => ({
  storeService: {
    getStores: mockGetStores
  }
}))


// Mock localStorage
vi.stubGlobal('localStorage', {
  getItem: () => 'test-user'
})

import Home from '../pages/home/Home'

describe('Home - Tests con data-testid ', () => {
  beforeEach(() => {
    vi.clearAllMocks()
    mockGetStores.mockResolvedValue([])
  })

  test('renderiza todos los data-testid principales', async () => {
    render(
      <BrowserRouter>
        <Home />
      </BrowserRouter>
    )
    
    await waitFor(() => {
      expect(screen.getByTestId('home-container')).toBeDefined()
      expect(screen.getByTestId('search-container')).toBeDefined()
      expect(screen.getByTestId('search-input')).toBeDefined()
      expect(screen.getByTestId('SearchIcon')).toBeDefined()
      expect(screen.getByTestId('content-box')).toBeDefined()
      expect(screen.getByTestId('nearby-checkbox')).toBeDefined()
      expect(screen.getByTestId('CheckBoxOutlineBlankIcon')).toBeDefined()
      expect(screen.getByTestId('cards-container')).toBeDefined()
      expect(screen.getByTestId('media-card-container')).toBeDefined()
      expect(screen.getByTestId('no-stores')).toBeDefined()
    })
  })

  test('muestra el título Delivery', async () => {
    render(
      <BrowserRouter>
        <Home />
      </BrowserRouter>
    )
    
    await waitFor(() => {
      expect(screen.getByText('Delivery')).toBeDefined()
    })
  })

  test('muestra mensaje de no hay locales', async () => {
    render(
      <BrowserRouter>
        <Home />
      </BrowserRouter>
    )
    
    await waitFor(() => {
      const noStoresElement = screen.getByTestId('no-stores')
      expect(noStoresElement).toBeDefined()
      expect(noStoresElement.textContent).toBe('No hay locales disponibles')
    })
  })

  test('muestra checkbox de locales cercanos', async () => {
    render(
      <BrowserRouter>
        <Home />
      </BrowserRouter>
    )
    
    await waitFor(() => {
      expect(screen.getByText('Buscar locales cercanos')).toBeDefined()
      const checkbox = screen.getByTestId('nearby-checkbox')
      expect(checkbox).toBeDefined()
    })
  })

  test('muestra campo de búsqueda', async () => {
    render(
      <BrowserRouter>
        <Home />
      </BrowserRouter>
    )
    
    await waitFor(() => {
      const searchInput = screen.getByTestId('search-input')
      expect(searchInput).toBeDefined()
      expect(screen.getByPlaceholderText('Buscá tu local para pedir…')).toBeDefined()
    })
  })
  
})
