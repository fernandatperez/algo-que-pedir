import type { PageLoad } from './$types'
import { MENUITEMS_MOCK } from '$lib/data/mock/menuItems'
import { showError } from '$lib/domain/errorHandler'

export const load: PageLoad = async ({ params }) => {
  const id = parseInt(params.id)
  try {
    const menuItem = await MENUITEMS_MOCK.find(item => item.id !== undefined && item.id === id)
    if (!menuItem) {
      throw new Error('Menu item no encontrado')
    }
    return { menuItem }
  } catch (error) {
    showError('Error loading menu item', error)
    return { menuItem: null } // para no romper el componente
  }
}