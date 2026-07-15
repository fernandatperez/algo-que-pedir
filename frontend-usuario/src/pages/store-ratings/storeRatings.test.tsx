import { describe, test, beforeEach, expect, vi, Mock } from 'vitest'
import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { MemoryRouter, Route, Routes } from 'react-router-dom'
import StoreRatings from './StoreRatings'
import RateStore from './rateStore'
import { userService } from '../../services/UserService'

vi.mock('../../services/UserService.ts', () => ({
  userService: {
    getUnratedStores: vi.fn(),
    rateStore: vi.fn(),
  },
}))

describe('StoreRatings Tests', () => {

  beforeEach(() => {
    vi.clearAllMocks()
    localStorage.setItem('id', '1')
  })

  const renderRatings = () =>
    render(
      <MemoryRouter initialEntries={['/store-ratings']}>
        <Routes>
          <Route path="/store-ratings" element={<StoreRatings />} />
          <Route path="/rate-store/:id" element={<RateStore />} />
        </Routes>
      </MemoryRouter>
    )

  test('Muestra únicamente los locales calificables (no pendientes)', async () => {

    ;(userService.getUnratedStores as Mock).mockResolvedValue([
      { id: 4, name: 'Grido', gradePointAvg: 4.5, deliveryTimeAvg: '20–30 min', isExpensive: false },
      { id: 7, name: 'Restaurante Italiano', gradePointAvg: 4.9, deliveryTimeAvg: '25–40 min', isExpensive: true }
    ])

    renderRatings()

    expect(await screen.findByTestId('store-to-rate-4')).toBeTruthy()
    expect(screen.getByTestId('store-to-rate-7')).toBeTruthy()
  })

  test('Un pedido PENDIENTE no aparece en StoreRatings', async () => {

    ;(userService.getUnratedStores as Mock).mockResolvedValue([
      { id: 1, name: 'Carlo\'s Bake Shop', gradePointAvg: 4.2, deliveryTimeAvg: '10–20 min', isExpensive: false },
    ])

    renderRatings()

    expect(await screen.findByTestId('store-to-rate-1')).toBeTruthy()
    expect(screen.queryByTestId('store-to-rate-4')).toBeNull()
  })

  test('El botón Calificar redirige al formulario RateStore', async () => {

    ;(userService.getUnratedStores as Mock).mockResolvedValue([
      { id: 7, name: 'Restaurante Italiano', gradePointAvg: 5, deliveryTimeAvg: '25–40 min', isExpensive: true }
    ])

    renderRatings()

    const btn = await screen.findByTestId('rate-btn-7')
    await userEvent.click(btn)

    expect(await screen.findByTestId('rating-form')).toBeTruthy()
  })

  test('Enviar rating llama userService.rateStore correctamente', async () => {

    ;(userService.getUnratedStores as Mock).mockResolvedValue([
      { id: 4, name: 'Grido', gradePointAvg: 4.5, deliveryTimeAvg: '20–30 min', isExpensive: false }
    ])

    ;(userService.rateStore as Mock).mockResolvedValue({})

    renderRatings()

    const btn = await screen.findByTestId('rate-btn-4')
    await userEvent.click(btn)

    const textarea = await screen.findByTestId('rating-textarea')
    await userEvent.type(textarea, 'Muy bueno')

    await userEvent.click(screen.getByTestId('submit-rating'))

    expect(userService.rateStore).toHaveBeenCalledWith(
      expect.any(Object),
      4
    )
  })

  test('Un local desaparece de la lista luego de ser calificado', async () => {

    ;(userService.getUnratedStores as Mock).mockResolvedValueOnce([
      { id: 4, name: 'Grido', gradePointAvg: 4.5, deliveryTimeAvg: '20–30 min', isExpensive: false }
    ])

    ;(userService.getUnratedStores as Mock).mockResolvedValueOnce([])

    ;(userService.rateStore as Mock).mockResolvedValue({})

    renderRatings()

    expect(await screen.findByTestId('store-to-rate-4')).toBeTruthy()

    const btn = screen.getByTestId('rate-btn-4')
    await userEvent.click(btn)

    await userEvent.type(await screen.findByTestId('rating-textarea'), 'ok')
    await userEvent.click(screen.getByTestId('submit-rating'))

    expect(screen.queryByTestId('store-to-rate-4')).toBeNull()
  })

})
