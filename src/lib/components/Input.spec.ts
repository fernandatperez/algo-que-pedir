import Input from '$lib/components/Input.svelte'
import { describe, it, expect } from 'vitest'
import { render, screen, waitFor } from '@testing-library/svelte'
import { InputTypes } from './InputPropsI'

describe('el input', () => {

  const testLoginInput = {
    description: 'Usuario*',
    input_type: InputTypes.Normal,
    labelProps: {
      class: 'label-color',
      for: 'username',
    },
    inputProps: {
      class: 'input-primary',
      type: 'email',
      placeholder: 'Usuario',
      id: 'input-id',
      name: 'username',
    },
  }

  const testRegisterInput = {
    description:'Contraseña*',
    input_type:InputTypes.Hidden,
    labelProps:{
      class: 'label-color',
      for: 'password',
    },
    inputProps:{
      class: 'input-primary',
      id: 'password-id',
      name: 'password',
    },
  }
            

  const testIngInput = {
    description: 'Nombre del ingrediente*',
    input_type: InputTypes.Normal,
    labelProps: {
      class: 'label-color',
      for: 'form-ingredient-name',
    },
    inputProps: {
      class: 'input-primary',
      id: 'form-ingredient-name',
      type: 'text',
      name: 'name',
    },
    value: 'Lechuga',
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