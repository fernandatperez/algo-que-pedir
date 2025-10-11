import { MenuItemType, type MenuItemJSON } from '$lib/domain/menuItem'
import { MENU_ITEMS_JSON_MOCK } from '$lib/data/mock/menuItems'

import axios from 'axios'
import { REST_SERVER_URL } from './configuration'
import { IngredientType } from '$lib/domain/ingredient'

class MenuItemsService {
  async getAllMenuItems(){
    const response = await axios.get<MenuItemJSON[]>(REST_SERVER_URL + '/platos')
    // console.log('Respuesta del backend:', response.data)
    // como "cosa"... hdp
    const cosa = response.data.map(MenuItemType.fromJson)
    // console.log('Primer item mapeado:', cosa[0]) 
    return cosa
    // return MENU_ITEMS_JSON_MOCK.map(MenuItemType.fromJson)
  }

  async getMenuItem(searchId: number) {
    const response = await axios.get<MenuItemJSON>(
      REST_SERVER_URL + '/platos/' + searchId
    )
    
    const plato = MenuItemType.fromJson(response.data)
    plato.ingredientes = plato.ingredientes.map(ingredienteJSON =>
      IngredientType.fromJson(ingredienteJSON))

    if (plato != null)
      return plato

    return new MenuItemType()
  }

  async createMenuItem(item: MenuItemType) {
    const itemJSON = { ...item }
    MENU_ITEMS_JSON_MOCK.push(itemJSON)
    return MENU_ITEMS_JSON_MOCK  
  }

  async updateMenuItem(menuItem: MenuItemType) {
    // Aca no se como seria
    const newItem = MENU_ITEMS_JSON_MOCK.findIndex(item => item.id == menuItem.id)
    if (newItem != -1) {
      MENU_ITEMS_JSON_MOCK[newItem] = {...menuItem}
      return MENU_ITEMS_JSON_MOCK[newItem]
    } else {
      throw new Error('Item no encontrado')
    }
  }
}

export const menuItemsService = new MenuItemsService()