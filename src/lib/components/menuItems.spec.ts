import { MENUITEMS_MOCK } from '../data/mock/menuItems'
import { describe, it, expect } from 'vitest'
import { render, screen } from '@testing-library/svelte'

import MenuItem from './MenuItem.svelte'

import userEvent from '@testing-library/user-event'

describe('MenuItem Component', () => {
  
  it('muestra correctamente todos los datos del menu item', () => {
    const mockItem = MENUITEMS_MOCK[0] // Pasta con albóndigas
    render(MenuItem, { menuitem: mockItem })
    // render lo que hace es es renderisar el componente es de testing-library como pone arriba
    // el parametro 2 son las props que le pasas al componente

    // Verificar nombre
    const nombre = screen.getByTestId('menu-nombre')
    expect(nombre.textContent).toBe('Pasta con albóndigas')
    
    // Verificar descripción
    const descripcion = screen.getByTestId('menu-descripcion')
    expect(descripcion.textContent).toBe('Deliciosa pasta con salsa de tomates y albondigas de cerdo')
    
    // Verificar precio con formato
    const precio = screen.getByTestId('menu-precio')
    expect(precio.textContent).toBe('$12.99')
    
    // Verificar imagen
    const imagen = screen.getByTestId('menu-imagen')
    expect(imagen).toHaveAttribute('src', 'src/lib/assets/img/spagettis.png')
    expect(imagen).toHaveAttribute('alt', 'spagettis')
  })

  it('genera el link correcto para la edicion del plato', () => {
    const mockItem = MENUITEMS_MOCK[1] // Hamburguesa
    render(MenuItem, { menuitem: mockItem })
    
    const link = screen.getByTestId('menu-link')
    expect(link).toHaveAttribute('href', '/dish-edit/2')
  })

  it('muestra correctamente diferentes items del menu', () => {
    const mockItem = MENUITEMS_MOCK[2] // Ensalada
    render(MenuItem, { menuitem: mockItem })
    
    expect(screen.getByTestId('menu-nombre').textContent).toBe('Ensalada de la Huerta')
    expect(screen.getByTestId('menu-precio').textContent).toBe('$7.5')
    expect(screen.getByTestId('menu-imagen')).toHaveAttribute('alt', 'ensalada')
  })

  it('todos los elementos tienen los data-testid necesarios para testing', () => {
    render(MenuItem, { menuitem: MENUITEMS_MOCK[0] })
    
    // Verificar que todos los elementos testeables tienen data-testid
    expect(screen.getByTestId('menu-link')).toBeInTheDocument()
    expect(screen.getByTestId('menu-imagen')).toBeInTheDocument()
    expect(screen.getByTestId('menu-nombre')).toBeInTheDocument()
    expect(screen.getByTestId('menu-descripcion')).toBeInTheDocument()
    expect(screen.getByTestId('menu-precio')).toBeInTheDocument()
  })

  describe('Interactividad y navegacion', () => {
    it('el enlace es clickeable y tiene el href correcto', async () => {
      const mockItem = MENUITEMS_MOCK[0]
      render(MenuItem, { menuitem: mockItem })
      
      const link = screen.getByTestId('menu-link')
      expect(link).toHaveAttribute('href', `/dish-edit/${mockItem.id}`)
      
      // Verificar que el elemento es interactivo
      await userEvent.hover(link)
      expect(link).toBeInTheDocument()
    })
  })

})

describe('dummy', () => {
  it('works', () => {
    expect(1 + 1).to.equal(2)
    //     expect(text.value).to.equal('')

  })
})


//! no me andan los tests


// Test de ejemplo que hizo el profe en el ejemplo de Twitter
// import { describe, it, expect } from 'vitest'
// import { render, screen } from '@testing-library/svelte'
// import userEvent from '@testing-library/user-event'

// import Twitter from './routes/+page.svelte'

// describe('twitter', () => {
//   it('should start with empty string', () => {
//     render(Twitter)

//     const text = screen.getByTestId('texto') as HTMLInputElement
//     expect(text.value).to.equal('')

//     const caracteres = screen.getByTestId('restantes')
//     expect(+caracteres.innerHTML).to.equal(140)
//     expect(caracteres.classList.contains('ok')).toBeTruthy()
//   })

//   it('should decrease remaining characters if a tweet is written - positive remaining characters', async () => {
//     render(Twitter)

//     const text = screen.getByTestId('texto') as HTMLInputElement
//     await userEvent.type(text, 'A new tweet')

//     const caracteres = screen.getByTestId('restantes')
//     expect(+caracteres.innerHTML).to.equal(129)
//   })

//   it('should decrease remaining characters if a tweet is written - negative remaining characters', async () => {
//     render(Twitter)

//     const text = screen.getByTestId('texto') as HTMLInputElement
//     await userEvent.type(text, '123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890')

//     const caracteres = screen.getByTestId('restantes')
//     expect(+caracteres.innerHTML).to.equal(-10)
//   })

//   it('should have a specific class for tweet reaching the limit', async () => {
//     render(Twitter)

//     const text = screen.getByTestId('texto') as HTMLInputElement
//     await userEvent.type(text, '1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789')

//     const caracteres = screen.getByTestId('restantes')
//     expect(caracteres.classList.contains('pasado')).toBeTruthy()
//   })

//   it('should have a specific class for tweet getting close to the limit', async () => {
//     render(Twitter)

//     const text = screen.getByTestId('texto') as HTMLInputElement
//     await userEvent.type(text, '123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012')

//     const caracteres = screen.getByTestId('restantes')
//     expect(caracteres.classList.contains('limite')).toBeTruthy()
//   })

// })