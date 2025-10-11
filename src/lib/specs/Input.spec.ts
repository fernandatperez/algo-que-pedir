import Input from '$lib/components/Input.svelte'
import { describe, it, expect } from 'vitest'
import { render, screen, waitFor } from '@testing-library/svelte'
import { InputTypes } from '$lib/components/InputPropsI'

describe('el input', () => {
  const testLoginInput = {
    label_text: 'Usuario*',
    label_for: 'username',
    input_type: InputTypes.Normal,
    value: '',
    class: 'input-primary',
    type: 'email',
    placeholder: 'Usuario',
    id: 'input-id',
    name: 'username',
  }

  const testRegisterInput = {
    label_text: 'Contraseña*',
    label_for: 'password',
    input_type: InputTypes.Hidden,
    value: '',
    class: 'input-primary',
    id: 'password-id',
    name: 'password',
  }
        
  const testIngInput = {
    label_text: 'Nombre del ingrediente*',
    label_for: 'form-ingredient-name',
    input_type: InputTypes.Normal,
    class: 'input-primary',
    id: 'form-ingredient-name',
    type: 'text',
    name: 'name',
  }

  describe('unauthed', () => {
    it('login', () => {
      render(Input, {
        ...testLoginInput
      })
      expect(screen.getByTestId('label-normal').innerHTML).toBe('Usuario*')
    })
	
    it('register', () => {
      render(Input, {
        ...testRegisterInput
      } )

      const input = screen.getByTestId('input-hidden') as HTMLInputElement
      expect(screen.getByTestId('label-hidden').innerHTML).toBe('Contraseña*')
      expect(input).toHaveAttribute('type', 'password')
    })

    it('register input type cambia a text cuando se clickea el boton del ojo' , async () => {
      render(Input, {
        ...testRegisterInput
      } )

      const input = screen.getByTestId('input-hidden') as HTMLInputElement
      screen.getByTestId('eyeBtn-password').click()
      await waitFor(() => {
        expect(input).toHaveAttribute('type', 'text')
      })
    })
  })

})