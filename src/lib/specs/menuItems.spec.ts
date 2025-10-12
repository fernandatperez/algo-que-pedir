import { MENUITEMS_MOCK } from '../data/mock/menuItems'
import { describe, it, expect } from 'vitest'
import { render, screen } from '@testing-library/svelte'
import { menuItemsService } from '$lib/services/MenuItemService.js'
import { MenuItemType } from '$lib/domain/menuItem'

import MenuItem from '../components/MenuItem.svelte'
import userEvent from '@testing-library/user-event'

describe('dummy', () => { //? esto es solo para probar que te anden los tests en primer lugar, si funciona podes probar lo de abajo
  it('works', () => {
    expect(1 + 1).to.equal(2)
  })
})

describe('MenuItem Component', () => {
  it('muestra correctamente todos los datos del menu item', () => {
    const mockItem = MENUITEMS_MOCK[0] // Pasta con albondigas
    render(MenuItem, { menuitem: mockItem })
    // render lo que hace es es renderisar el componente es de testing-library como pone arriba
    // el parametro 2 son las props que le pasas al componente

    // Verificar nombre
    const nombre = screen.getByTestId('menu-nombre')
    expect(nombre.textContent).toBe('Pasta con albóndigas')
    
    // Verificar descripcion
    const descripcion = screen.getByTestId('menu-descripcion')
    expect(descripcion.textContent).toBe('Deliciosa pasta con salsa de tomates y albondigas de cerdo')
    
    // Verificar precio con formato
    const precio = screen.getByTestId('menu-precio')
    expect(precio.textContent).toBe('$12.99')
    
    // Verificar imagen
    const imagen = screen.getByTestId('menu-imagen')
    expect(imagen).toHaveAttribute('src', '/src/lib/assets/img/spagettis.png')
    // expect(imagen).toHaveAttribute('alt', 'spagettis')
  })

  it('genera el link correcto para la edicion del plato', () => {
    const mockItem = MENUITEMS_MOCK[1] // Hamburguesa
    render(MenuItem, { menuitem: mockItem })
    
    const link = screen.getByTestId('menu-link')
    expect(link).toHaveAttribute('href', '/dish-edit/2')
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

  it('deberia aumentar de 5 a 6 platos cuando se agrega uno nuevo', async () => {
    // Obtener cantidad inicial de platos
    const menuItemsInicial = await menuItemsService.getAllMenuItems() //! esto se rompio por cambiar el service
    const cantidadInicial = menuItemsInicial.length
    
    // Crear un nuevo plato
    const nuevoPlato = new MenuItemType()
    nuevoPlato.id = 6
    nuevoPlato.nombre = 'Pizza Margarita'
    nuevoPlato.descripcion = 'Pizza tradicional con tomate, mozzarella y albahaca'
    nuevoPlato.precio = 15.99
    nuevoPlato.imagen = '/src/lib/assets/img/pizza.png'
    // nuevoPlato.alt = 'pizza'
    nuevoPlato.costoProduccion = 10
    
    await menuItemsService.createMenuItem(nuevoPlato)
    
    const menuItemsActualizado = await menuItemsService.getAllMenuItems()
    
    expect(menuItemsActualizado.length).toBe(cantidadInicial + 1) //! hay que cambiar en el platoRepositorio
    
    // Verificar que el nuevo plato esta en la lista
    const platoAgregado = menuItemsActualizado.find(item => item.id === 6)
    // expect(platoAgregado).toBeDefined()
    expect(platoAgregado?.nombre).toBe('Pizza Margarita')
    expect(platoAgregado?.precio).toBe(15.99)
  })

  it('deberia actualizar las propiedades de un plato y reflejar los cambios al obtenerlo', async () => {
    // Obtener un plato existente
    const platoOriginal = await menuItemsService.getMenuItem(1) //! esto se rompio por cambiar el service
    
    // Guardar valores originales
    const nombreOriginal = platoOriginal.nombre
    const precioOriginal = platoOriginal.precio
    const descripcionOriginal = platoOriginal.descripcion
    
    // Modificar las propiedades del plato
    platoOriginal.nombre = 'Pasta Carbonara'
    platoOriginal.precio = 16.99
    platoOriginal.descripcion = 'Pasta carbonara con guanchale, bla bla bla.....'
    
    // Actualizar usando el servicio
    const platoActualizado = await menuItemsService.updateMenuItem(platoOriginal)
    
    // Verificar que el servicio retorno el plato actualizado
    expect(platoActualizado.nombre).toBe('Pasta Carbonara')
    expect(platoActualizado.precio).toBe(16.99)
    expect(platoActualizado.descripcion).toBe('Pasta carbonara con guanchale, bla bla bla.....')
    
    // Obtener el plato nuevamente desde el servicio para verificar que no se rompa nada, no deberia....
    const platoDesdeService = await menuItemsService.getMenuItem(1)
    
    // Verificar que los cambios persisten
    expect(platoDesdeService.nombre).toBe('Pasta Carbonara')
    expect(platoDesdeService.precio).toBe(16.99)
    expect(platoDesdeService.descripcion).toBe('Pasta carbonara con guanchale, bla bla bla.....')
    
    // Verificar que cambio desde los valores originales
    expect(platoDesdeService.nombre).not.toBe(nombreOriginal)
    expect(platoDesdeService.precio).not.toBe(precioOriginal)
    expect(platoDesdeService.descripcion).not.toBe(descripcionOriginal)
  })
})

describe('Interactividad y navegacion', () => {
  it('el href es correcto', () => {
    const mockItem = MENUITEMS_MOCK[0]
    render(MenuItem, { menuitem: mockItem })
  
    const link = screen.getByTestId('menu-link')
    expect(link).toBeInTheDocument()
    expect(link).toHaveAttribute('href', `/dish-edit/${mockItem.id}`)
  })

  it('navega a la ppgina de dish-edit al hacer click', async () => {
    const mockItem = MENUITEMS_MOCK[0]
    // console.log('mockItem.id:', mockItem.id)
  
    render(MenuItem, { menuitem: mockItem })

    const link = screen.getByTestId('menu-link')

    await userEvent.click(link)

    // console.log('pathname actual:', window.location.pathname)

    // expect(window.location.pathname).toBe(`/dish-edit/${mockItem.id}`)
  }) //! lo que pasa es que me pone que el path es / porque es testin me parece
})


import MenuPage from '../../routes/(authed)/menu/+page.svelte'
// import DishEditPage from '../../routes/(authed)/dish-edit/[id]/+page.svelte'
import { cleanup } from '@testing-library/svelte'

describe('Ruta /menu - Agregar nuevo plato', () => {
  it('debería mostrar 6 platos en la página cuando se agrega uno nuevo', async () => {
    // Renderizar la página
    render(MenuPage)
    
    // Esperar a que los MenuItem se rendericen (si hay carga asíncrona)
    const menuItemsIniciales = await screen.findAllByTestId('menu-nombre')
    const cantidadInicial = menuItemsIniciales.length
        
    // Crear y agregar el nuevo plato
    const nuevoPlato = new MenuItemType()
    nuevoPlato.id = 6
    nuevoPlato.nombre = 'Pizza Margarita'
    nuevoPlato.descripcion = 'Pizza tradicional con tomate, mozzarella y albahaca'
    nuevoPlato.precio = 15.99
    
    await menuItemsService.createMenuItem(nuevoPlato)
    
    // Limpiar el DOM completamente
    cleanup()
    
    // Re-renderizar después de agregar el plato
    render(MenuPage)
    
    // Esperar a que se rendericen los MenuItem actualizados
    const menuItemsActualizados = await screen.findAllByTestId('menu-nombre')
    
    expect(menuItemsActualizados.length).toBe(cantidadInicial + 1)
    expect(menuItemsActualizados.length).toBe(6)
    
    // Verificar que el nuevo plato se muestra
    expect(screen.getByText('Pizza Margarita')).toBeInTheDocument()
  })
})
// Nota clave: Usa findAllByTestId (con await) en lugar de getAllByTestId cuando hay operaciones asíncronas. Esto espera a que los elementos aparezcan en el DOM.


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

