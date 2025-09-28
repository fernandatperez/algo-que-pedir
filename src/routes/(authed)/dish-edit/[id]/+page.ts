import { menuItemsService } from '$lib/services/MenuItemService'
import { redirect } from '@sveltejs/kit'

export async function load({ params }) {
  try {
    const id = parseInt(params.id)
    const menuItem = await menuItemsService.getMenuItem(id)
    if (menuItem.id == -1) {
      const title = 'Crear nuevo plato'
      return { title, menuItem}
    } else {
      const title = 'Editar plato'
      return { title, menuItem }
    }
  }
  catch (error) {
    // eslint-disable-next-line no-console
    console.error('Error al cargar el plato', error)
    throw redirect(302, '/menu')
  }
}