import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import { MemoryRouter } from 'react-router-dom' // para testear el caso de {from: } del login
import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'

import Login from './Login'
import Register from './Register'
import { userService } from '../../services/UserService'

// ---- MOCK DEL HOOK useToast PARA CONTROLAR showToast ----
const showToastMock = vi.fn()
vi.mock('../../components/Toast/useToast', () => ({
  useToast: () => ({
    toast: null,
    showToast: showToastMock,
  }),
}))

const mockedNavigate = vi.fn()
vi.mock('react-router-dom', async () => {
  const mockedRouter = await vi.importActual('react-router-dom')

  return {
    ...mockedRouter,
    useNavigate: () => mockedNavigate,
  }
})

describe('Login component', () => {
  beforeEach(() => {
    vi.restoreAllMocks()
    showToastMock.mockReset()
  })

  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('envía el login con email y password válidos llamando a userService.login()', async () => {
    const loginSpy = vi
      .spyOn(userService, 'login')
      .mockResolvedValueOnce(true as never) // devuelve true as never, porque no me importa lo que devuelve (UserType) no lo uso.

    render(
      <MemoryRouter>
        <Login />
      </MemoryRouter>
    )

    const emailInput = screen.getByTestId('emailInput')
    const passwordInput = screen.getByTestId('passwordInput')
    const submitButton = screen.getByTestId('iniciarBtn')

    await userEvent.type(emailInput, 'test@example.com')
    await userEvent.type(passwordInput, 'password123')
    await userEvent.click(submitButton)

    await waitFor(() => {
      expect(loginSpy).toHaveBeenCalledTimes(1)
      expect(loginSpy).toHaveBeenCalledWith('test@example.com', 'password123')
    })
  })

  it('si viene un "from" distinto de "/" en location.state, al iniciar navega a esa direccion', async () => {
    const loginSpy = vi.spyOn(userService, 'login').mockResolvedValueOnce(true as never)

    render(
      <MemoryRouter
        initialEntries={[
          {
            pathname: '/login',
            state: { from: { pathname: '/profile' } },
          } as any,
        ]}
      >
        <Login />
      </MemoryRouter>
    )

    const emailInput = screen.getByTestId('emailInput')
    const passwordInput = screen.getByTestId('passwordInput')
    const submitButton = screen.getByTestId('iniciarBtn')

    await userEvent.type(emailInput, 'test@example.com')
    await userEvent.type(passwordInput, 'password123')
    await userEvent.click(submitButton)

    await waitFor(() => {
      expect(loginSpy).toHaveBeenCalledTimes(1)
      expect(mockedNavigate).toHaveBeenCalledWith(`/profile`, {replace: true})
    })
  })

  it('cuando el login falla, muestra el toast "Credenciales invalidas"', async () => {
    const loginSpy = vi
      .spyOn(userService, 'login')
      .mockRejectedValueOnce(new Error('Credenciales inválidas') as never)

    render(
      <MemoryRouter>
        <Login />
      </MemoryRouter>
    )

    const emailInput = screen.getByTestId('emailInput')
    const passwordInput = screen.getByTestId('passwordInput')
    const submitButton = screen.getByTestId('iniciarBtn')

    await userEvent.type(emailInput, 'test@example.com')
    await userEvent.type(passwordInput, 'password123')
    await userEvent.click(submitButton)

    await waitFor(() => {
      expect(loginSpy).toHaveBeenCalledTimes(1)
      // console.log(showToastMock.mock.calls) // [ [ 'Credenciales inválidas', 'error' ] ]
      expect(showToastMock).toHaveBeenCalledWith(
        'Credenciales inválidas',
        'error'
      )
    })
  })

  it('si viene un "from" distinto de "/" en location.state, muestra el toast "Debe loguearse."', async () => {
    vi.spyOn(userService, 'login').mockResolvedValueOnce(true as never)

    render(
      <MemoryRouter
        initialEntries={[
          {
            pathname: '/login',
            state: { from: { pathname: '/profile' } },
          } as any,
        ]}
      >
        <Login />
      </MemoryRouter>
    )

    await waitFor(() => {
      expect(showToastMock).toHaveBeenCalledWith(
        'Debe loguearse.',
        'error'
      )
    })
  })
})

//-- === REGISTER PAGE ===
describe('Register component', () => {
  beforeEach(() => {
    vi.restoreAllMocks()
    showToastMock.mockReset()
  })

  afterEach(() => {
    vi.restoreAllMocks()
  })

  it('cuando las contraseñas no coinciden, no llama a createUser y muestra toast de error', async () => {
    const createUserSpy = vi
      .spyOn(userService, 'createUser')
      .mockResolvedValueOnce(true as never)

    render(
      <MemoryRouter>
        <Register />
      </MemoryRouter>
    )

    const emailInput = screen.getByTestId('emailInput')
    const passwordInput = screen.getByTestId('passwordInput')
    const passwordRetryInput = screen.getByTestId('passwordRetryInput')
    const submitButton = screen.getByTestId('registerBtn')

    await userEvent.type(emailInput, 'user@example.com')
    await userEvent.type(passwordInput, 'password123')
    await userEvent.type(passwordRetryInput, 'otraCosa456')
    await userEvent.click(submitButton)

    await waitFor(() => {
      expect(createUserSpy).not.toHaveBeenCalled()
      expect(showToastMock).toHaveBeenCalledWith(
        'Las contraseñas no coinciden.',
        'error'
      )
    })
  })

  it('cuando los datos son válidos, llama a userService.createUser, muestra toast de éxito y navega a login', async () => {
    const createUserSpy = vi
      .spyOn(userService, 'createUser')
      .mockResolvedValueOnce(true as never)
    
    // vi.useFakeTimers()

    render(
      <MemoryRouter>
        <Register />
      </MemoryRouter>
    )

    const emailInput = screen.getByTestId('emailInput')
    const passwordInput = screen.getByTestId('passwordInput')
    const passwordRetryInput = screen.getByTestId('passwordRetryInput')
    const submitButton = screen.getByTestId('registerBtn')

    await userEvent.type(emailInput, 'user@example.com')
    await userEvent.type(passwordInput, 'password123')
    await userEvent.type(passwordRetryInput, 'password123')
    await userEvent.click(submitButton)
    
    await waitFor(() => {
      expect(createUserSpy).toHaveBeenCalledTimes(1)
      
      expect(showToastMock).toHaveBeenCalledWith(
        'Usuario generado con exito. Seras redirigido a la pagina de Ingreso',
        'success',
        2000
      )
      // vi.runAllTimers()
      expect(mockedNavigate).toHaveBeenCalledWith('/login')
    }, {timeout: 4000} ) // por las dudas, 4s para que se hayan completado los timeouts
  })
})
