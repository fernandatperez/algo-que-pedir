import { FormEvent, useState } from 'react'
import { Link, useLocation, useNavigate } from 'react-router-dom'
import { TextField } from '@mui/material'
import {Button} from '@mui/material'
import { CookingPot, } from 'phosphor-react'
import './login-register.css'
import { UserJSONLoginRequest, UserType } from '../../domain/user'
import { userService } from '../../services/UserService'
import { ValidationMessage } from '../../domain/validationMessage'
import ValidationField from '../../components/ValidationField/ValidationField'
import { getErrorMessage } from '../../domain/errorHandler'
import { Toast } from '../../components/Toast/ToastContainer'
import { useToast } from '../../components/Toast/useToast'
import { useOnInit } from '../../customHooks/useOnInit'

const Login = () => {
  const { toast, showToast } = useToast()
  const [user, setUser] = useState<UserJSONLoginRequest>({email: '', password: ''})
  const [errors, setErrors] = useState<Array<ValidationMessage>>([])
  
  const navigate = useNavigate()
  const location = useLocation()
  const from = location.state?.from?.pathname || '/'

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault()

    const form = e.currentTarget as HTMLFormElement
    const formData = new FormData(form)

    const userLogin = new UserType(
      'nombre',
      'apellido',
      (formData.get('password') ?? '').toString(),
      (formData.get('email') ?? '').toString(),
    )

    userLogin.validate()

    if (userLogin.errors.length > 0) {
      setErrors(userLogin.errors)
      return errors
    }

    try {
      let validation = await userService.login(userLogin.email, userLogin.password)
      if (validation) {
        navigate(from, {replace: true}) // recordar la ruta “from” y volver tras login
      }
    } catch (error) {
      const errorMessage = getErrorMessage(error)
      showToast(errorMessage, 'error')
    } finally {
      setErrors([])
    }
  }

  const actualizar = (clave: keyof typeof user, valor: unknown) => {
    setUser({
      ...user,
      [clave]: valor
    })
  }

  useOnInit(() => {
    // console.log(location.state?.from?.pathname)
    if (location.state?.from?.pathname != '/' && location.state?.from?.pathname != undefined) showToast('Debe loguearse.', 'error')
  })
    
  return (
    <div className="main-container-login">
      <div className='header-logo'>
        <CookingPot weight='fill' className='cooking-pot-logo'></CookingPot>
        <h1>Algo que pedir</h1>
      </div>

      <form
        onSubmit={handleSubmit}
        id='login-form'
        className='auth-form'
      >
        <TextField
          id='email'
          label="Email*"
          type="email"
          name='email'
          value={user.email}
          onChange={(e) => actualizar('email', e.target.value)}
          error={!errors?.every(valMess => valMess.field != 'email')}
          slotProps={{ 
            input: {
              inputProps: {
                'data-testid': 'emailInput'
              },
            }, 
          }}
        />
        <ValidationField field='email' errors={errors} />

        <TextField
          id='password'
          label="Contraseña*"
          type="password"
          name='password'
          value={user.password}
          onChange={(e) => actualizar('password', e.target.value)}
          error={!errors?.every(valMess => valMess.field != 'password')}
          slotProps={{ 
            input: {
              inputProps: {
                'data-testid': 'passwordInput'
              },
            }, 
          }}
        />
        <ValidationField field='password' errors={errors} />

        <Button variant="contained" color="primary" 
          type="submit"
          className='auth-submit-btn'
          data-testid='iniciarBtn'
        >
          Iniciar sesión
        </Button>
      </form>

      <span>¿No tenes cuenta? <Link to={'/register'} className='auth-anchor'>Registrate</Link></span>

      <div id="toast-container">
        <Toast toast={toast} />
      </div>
    </div>
  )
}

export default Login