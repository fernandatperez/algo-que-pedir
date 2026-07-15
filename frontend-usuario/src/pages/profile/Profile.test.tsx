import { render, screen, fireEvent, waitFor } from '@testing-library/react'
import { MemoryRouter } from 'react-router-dom'
import { describe, expect, test, vi, beforeEach, Mock } from 'vitest'

import Profile from './Profile'
import { useUserProfile } from '../../customHooks/useUserProfile'
import { userService } from '../../services/UserService'
import { UserProfile } from '../../domain/userProfile'

vi.mock('../../customHooks/useUserProfile', () => ({
    useUserProfile: vi.fn(),
}))

vi.mock('../../services/UserService', () => ({
    userService: {
        updateProfile: vi.fn(),
    },
}))

describe('Tests para Profile', () => {
    const mockShowToast = vi.fn()
    const mockSetProfile = vi.fn()
    const mockSetProfileOG = vi.fn()

    const originalProfile = new UserProfile(1, 'Sofia', 'sofiamiller@example.com', 'Miller', 'Calle 123', 'CABA', -34.60, -58.39, [], [])

    const updatedProfile = new UserProfile(1, 'Ana Actualizada', 'sofiamiller@example.com', 'Miller', 'Av Siempreviva 742', 'Buenos Aires', -34.60, -58.39, [], [])

    beforeEach(() => {
        vi.clearAllMocks()
        vi.resetAllMocks()
        ;(useUserProfile as Mock).mockReturnValue({
            profile: originalProfile,
            setProfile: mockSetProfile,
            setProfileOG: mockSetProfileOG,
            showToast: mockShowToast,
        })
    })

    test('CASO FELIZ: guarda los cambios y se actualiza el perfil', async () => {
        vi.spyOn(userService, 'updateProfile').mockResolvedValueOnce(updatedProfile)

        // <MemoryRouter> -> proporciona el contexto necesario de React Router, por ejemplo useParams
        render(
            <MemoryRouter> 
                <Profile />
            </MemoryRouter>
        )

        fireEvent.change(screen.getByLabelText('Nombre'), {target: { value: 'Ana Actualizada' },})
        fireEvent.change(screen.getByLabelText('Dirección'), {target: { value: 'Av Siempreviva 742' },})
        fireEvent.change(screen.getByLabelText('Ubicación'), {target: { value: 'Buenos Aires' },})

        fireEvent.click(screen.getByText('Guardar'))

        await waitFor(() => {
            expect(userService.updateProfile).toHaveBeenCalledTimes(1)
            expect(mockShowToast).toHaveBeenCalledWith('Usuario modificado con exito', 'success')

            expect(mockSetProfile).toHaveBeenCalledWith(updatedProfile)
            expect(mockSetProfileOG).toHaveBeenCalledTimes(1)
        })
    })

    test('CASO TRISTE: ocurre un error al guardar y se muestra el toast de error', async () => {
        vi.spyOn(userService, 'updateProfile').mockRejectedValueOnce(new Error('Error forzado'))

        render(
            <MemoryRouter>
                <Profile />
            </MemoryRouter>
        )

        fireEvent.click(screen.getByText('Guardar'))

        await waitFor(() => {
            expect(userService.updateProfile).toHaveBeenCalledTimes(1)
            expect(mockShowToast).toHaveBeenCalledWith('Error al guardar el perfil','error')

            expect(mockSetProfile).not.toHaveBeenCalled()
            expect(mockSetProfileOG).not.toHaveBeenCalled()
        })
    })
    
    test('Si el perfil tiene errores de validación NO se guarda', async () => {
        originalProfile.validate = vi.fn(() => {
            originalProfile.errors = [{ field: 'name', message: 'Requerido' }]
        })

        render(
            <MemoryRouter>
                <Profile />
            </MemoryRouter>
        )

        fireEvent.click(screen.getByText('Guardar'))

        await waitFor(() => {
            expect(userService.updateProfile).not.toHaveBeenCalled()
            expect(mockShowToast).not.toHaveBeenCalled()
        })
    })
})
