import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { BrowserRouter } from 'react-router-dom'
import SearchCriteria from './SearchCriteria'
import type { UserProfile } from '../../domain/userProfile'
import { Store } from '../../domain/storeDom'
import '@testing-library/jest-dom/vitest'


// ---- MOCKS DE MÓDULOS ----

// Mock del hook useUserProfile
vi.mock('../../customHooks/useUserProfile', () => ({
  useUserProfile: vi.fn(),
}))
import { useUserProfile } from '../../customHooks/useUserProfile'

// Mock del servicio de locales
vi.mock('../../services/LocalesService', () => ({
  storeService: {
    getStoresDom: vi.fn(),
  },
}))
import { storeService } from '../../services/LocalesService'

// Mock muy simple de ModalStores para poder inspeccionar los locales disponibles
let modalStores: any[] = []
vi.mock('../../components/ModalStores/ModalStores', () => ({
  default: (props: any) => { 
    // me gusrado los locales que se le pasan al modal
    modalStores = props.stores
    return (
    <div data-testid="modal-stores">
      {props.stores.map((s: any) => (
        <div key={s.id}>{s.name}</div>
      ))}
    </div>
  )},
}))

// Helpers para types de Vitest
type UseUserProfileMock = ReturnType<typeof vi.fn>

// ---- HELPERS DE PERFIL Y DATOS ----
const mockShowToast = vi.fn()
const mockSetProfile = vi.fn()
const mockCheckChanges = vi.fn()

// Creo o Modifico un perfil de usuario solo con lo que necesito
const buildMockedProfile = (overrides?: Partial<UserProfile & any>): UserProfile => {
  const base: any = {
    id: 1,
    maxDistance: 7,
    criteria: {
      type: 'combinado',
      criterios: [],
    },
    // implementación muy básica para no romper handleSave()
    agregarCriterios: vi.fn(function (this: any, nuevos: any[]) {
      this.criteria = { type: 'combinado', criterios: nuevos }
      return this
    }),
  }
  return { ...base, ...overrides } as UserProfile
}


const setupUseUserProfile = (profile: UserProfile) => {
  ;(useUserProfile as UseUserProfileMock).mockReturnValue({
    profile,
    setProfile: mockSetProfile,
    checkChanges: mockCheckChanges,
    showToast: mockShowToast,
  })
}

beforeEach(() => {
  vi.clearAllMocks()
})

/* =========================================================================
  1) Carga inicial: criterios del perfil → checkboxes + frases + maxDistance
  ========================================================================== */
describe('SearchCriteria > carga inicial del perfil', () => {
  it('marca como activos los criterios del perfil, carga las frases consumistas y maxDistance', async () => {
    const profile = buildMockedProfile({
      maxDistance: 5,
      criteria: {
        type: 'combinado',
        criterios: [
          { type: 'vegano' },
          { type: 'consumista', frasesFavoritas: ['promo', 'descuento'] },
        ],
      },
    })
    setupUseUserProfile(profile)
    const storeSpy = vi.spyOn(storeService, 'getStoresDom').mockResolvedValue([])

    render(
      <BrowserRouter>
        <SearchCriteria />
      </BrowserRouter>
    )

    // Los checkboxes aparecen en orden: vegano, exquisito, conservadores, fieles, consumista, impacientes
    const checkboxes = await screen.findAllByRole('checkbox') // x rol, mas facil que poner a todos data-testid (por MUI y slotProps)
    const veganoCheckbox = checkboxes[0] // Si llega a agregarse uno, esto rompe
    const consumistaCheckbox = checkboxes[4]
    const exquisitoCheckbox = checkboxes[1]

    expect(storeSpy).toHaveBeenCalledTimes(1)
    expect(veganoCheckbox).toBeChecked()
    expect(consumistaCheckbox).toBeChecked()
    expect(exquisitoCheckbox).not.toBeChecked()

    // Las frases consumistas iniciales deben aparecer en pantalla
    expect(screen.getByTestId('frase-promo').innerHTML).toBe('promo')
    expect(screen.getByTestId('frase-descuento').innerHTML).toBe('descuento')

    // El contador de distancia debe mostrar el maxDistance del perfil
    expect(screen.getByTestId('maxDistance').innerHTML).toBe('5')
  })
})

/* ===========================================================
  2) Locales fieles: el favorito NO debe aparecer como disponible
  =========================================================== */
describe('SearchCriteria > locales fieles', () => {
  it('no muestra en la lista de locales disponibles el que ya es favorito', async () => {

    const favoriteStore: Store = new Store(1, 'Restaurante fav', undefined)
    const otherStore: Store = new Store(2, 'Otro local', undefined)
      
    const profile = buildMockedProfile({
      criteria: {
        type: 'combinado',
        criterios: [
          {
            type: 'fieles',
            localesFavoritos: [favoriteStore],
          },
        ],
      },
    })
    setupUseUserProfile(profile)
    const storeSpy = vi
      .spyOn(storeService, 'getStoresDom')
      .mockResolvedValue([favoriteStore, otherStore])

    render(
      <BrowserRouter>
        <SearchCriteria />
      </BrowserRouter>
    )

    // Esperamos a que se carguen los locales desde el servicio
    await waitFor(() => {
      expect(storeSpy).toHaveBeenCalledTimes(1)
    })

    // Botón "Add" de la sección Fieles (el primero con clase btn-add en esa tarjeta).
    // Si le agregaste un data-testid tipo data-testid="btn-fieles-add", usá directamente getByTestId.
    const btnFielesAdd = screen.getByTestId('btn-fieles-add')
    await userEvent.click(btnFielesAdd)

    // chequear que este es el mockeado
    const modal = await screen.findByTestId('modal-stores')
    const modalStoresIds = modalStores.map(s => s.id)

    // En el modal deben aparecer SOLO los locales disponibles (sin el favorito)
    expect(modal).toBeInTheDocument()
    expect(modalStoresIds).not.toContain(favoriteStore.id)
    expect(modalStoresIds).toContain(otherStore.id)
  })
})

/* ===========================================================
   3) Consumista: al guardar frases, no se deben repetir
   =========================================================== */
describe('SearchCriteria > criterio consumista', () => {
  it('normaliza las frases y evita duplicados al guardar', async () => {
    const profile = buildMockedProfile({
      criteria: {
        type: 'combinado',
        criterios: [
          {
            type: 'consumista',
            frasesFavoritas: ['promo'],
          },
        ],
      },
    })
    setupUseUserProfile(profile)
    vi.spyOn(storeService, 'getStoresDom').mockResolvedValue([])

    render(
      <BrowserRouter>
        <SearchCriteria />
      </BrowserRouter>
    )

    // Abrimos el input de frases (botón con ícono + en la card de "Marketing")
    // Si le sumás data-testid="btn-consumista-add" al botón de Marketing, usalo acá:
    const btnConsumistaAdd = screen.getByTestId('btn-consumista-add')
    await userEvent.click(btnConsumistaAdd)
    const input = screen.getByTestId('input-frases')

    await userEvent.type(input, 'promo, Descuento, PROMO ')
    // Botón con el ícono de Check para guardar
    const btnGuardarFrases = screen.getByTestId('btn-frases-add')
    await userEvent.click(btnGuardarFrases)

    // Ahora deberían existir las frases "promo" y "descuento", pero sin duplicados ni espacios y en lowercase
    const promoOccurrences = screen.getAllByText('promo')
    const descuentoOccurrences = screen.getAllByText('descuento')

    expect(promoOccurrences.length).toBe(1)
    expect(descuentoOccurrences.length).toBe(1)
  })
})
