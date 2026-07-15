import { render, screen, waitFor, fireEvent } from '@testing-library/react'
import { beforeEach, describe, expect, Mock, test, vi } from 'vitest'
import { MemoryRouter } from 'react-router-dom'

import IngredientCriteria from './IngredientCriteria'
import { useUserProfile } from '../../customHooks/useUserProfile'
import { ingredientService } from '../../services/IngredientService'
import { FoodGroupValue, IngredientType } from '../../domain/ingredient'

vi.mock('react-router-dom', async () => {
    const actual = await vi.importActual('react-router-dom')
        return {
        ...actual,
        useParams: () => ({ criteria: 'avoid' }), // siempre empiezo con pagina de ingredientes a evitar
    }
})

vi.mock('../../customHooks/useUserProfile', () => ({
    useUserProfile: vi.fn(),
}))

// vi.fn() -> Simula callbacks o props de funciones
const showToastMock = vi.fn()

describe('Tests para IngredientCriteria', () => {
    const mockIngredients: IngredientType[] = [
        new IngredientType(1, 'Queso Cheddar', 0.5, FoodGroupValue.LACTEOS, true ),
        new IngredientType(2, 'Tomate', 0.2, FoodGroupValue.FRUTAS_Y_VERDURAS, false )
    ]

    beforeEach(() => {
        vi.clearAllMocks()   // NO usar restoreAllMocks, porque borra los spyOn
        vi.resetAllMocks()

        // ; -> aisla la llamada para que la linea anterior no se coma lo que sigue
        ;(useUserProfile as Mock).mockReturnValue({
            profile: {
            preferredIngredients: [],
            ingredientsToAvoid: [],
            },
            setProfile: vi.fn(), // Simula la función setProfile
            checkChanges: vi.fn(),
            showToast: showToastMock,
        })
    })

    test('CASO FELIZ: carga ingredientes correctamente del backend', async () => {
        // en vez de hacer la llamada real, resuelve con lo que hay en el mock de ingredientes
        vi.spyOn(ingredientService, 'getAllIngredients').mockResolvedValueOnce(mockIngredients)

        render(
            <MemoryRouter>
                <IngredientCriteria />
            </MemoryRouter>
        )

        fireEvent.click(screen.getByText('Añadir ingrediente'))

        expect(await screen.findByLabelText('Queso Cheddar')).toBeTruthy()
        expect(await screen.findByLabelText('Tomate')).toBeTruthy()
    })

    test('CASO TRISTE: no cargar los ingredientes y se llama showToast', async () => {
        vi.spyOn(ingredientService, 'getAllIngredients').mockRejectedValueOnce(new Error('Error forzado, falla backend'))

        render(
            <MemoryRouter>
                <IngredientCriteria />
            </MemoryRouter>
        )

        await waitFor(() => {
            expect(showToastMock).toHaveBeenCalledTimes(1)
            expect(showToastMock).toHaveBeenCalledWith('Error al cargar los ingredientes. Intente nuevamente.', 'error')
        })
    })

    test('No puedo agregar un ingrediente preferido cuando criterio = avoid', async () => {
        const queso = new IngredientType(1, 'Queso Cheddar', 0.5, FoodGroupValue.LACTEOS, true )
        const tomate = new IngredientType(2, 'Tomate', 0.2, FoodGroupValue.FRUTAS_Y_VERDURAS, false )

        // le agrego queso a la lista de ingredientes preferidos del usuario
        ;(useUserProfile as Mock).mockReturnValue({
            profile: {
                preferredIngredients: [queso],    
                ingredientsToAvoid: [],
            },
            setProfile: vi.fn(),
            checkChanges: vi.fn(),
            showToast: showToastMock,
        })

        vi.spyOn(ingredientService, 'getAllIngredients').mockResolvedValueOnce([queso, tomate])

        render(
            <MemoryRouter>
                <IngredientCriteria />
            </MemoryRouter>
        )

        fireEvent.click(screen.getByText('Añadir ingrediente'))

        // tomate tiene que aparecer
        expect(await screen.findByLabelText('Tomate')).toBeTruthy()

        // queso NO tiene que aparecer
        expect(screen.queryByLabelText('Queso Cheddar')).toBeNull()
    })
})
