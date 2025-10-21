import { MenuItemType } from '$lib/domain/menuItem.js'
import { menuItemsService } from '$lib/services/MenuItemService'
import { redirect } from '@sveltejs/kit'

export async function load({ params }) {
  try {
    const nuevoItem = params.id === 'nuevoPlato'
    let item: MenuItemType
    if (nuevoItem) {
      item = new MenuItemType()
      // eslint-disable-next-line no-console
      // console.log('Plato cargado:', item)
      return {nuevoItem, item}
    } else {
      item = await menuItemsService.getMenuItem(+params.id) // + para que sea un number
      // eslint-disable-next-line no-console
      // console.log('Plato cargado:', item)
      // item.setPromocion()
      return {nuevoItem, item}
    }

  }
  catch (error) {
    // eslint-disable-next-line no-console
    console.error('Error al cargar el plato', error)
    throw redirect(302, '/menu')
  }
}