import { MenuItemType, type MenuItemJSON } from '$lib/domain/menuItem'
import { MENU_ITEMS_JSON_MOCK } from '$lib/data/mock/menuItems'
let GLOBAL_VAR = 5
export const GLOBAL_ID = () => {
  GLOBAL_VAR += 1
  return GLOBAL_VAR
}

import axios from 'axios'
import { REST_SERVER_URL } from './configuration'

class MenuItemsService {
  async getAllMenuItems(){
    const response = await axios.get<MenuItemJSON[]>(REST_SERVER_URL + '/platos')
    // console.log('Respuesta del backend:', response.data)
    const cosa = response.data.map(MenuItemType.fromJson)
    // console.log('Primer item mapeado:', cosa[0]) 
    return cosa
    // return MENU_ITEMS_JSON_MOCK.map(MenuItemType.fromJson)
  }

  
  async getMenuItem(searchId: number) {
    const menuItem = MENU_ITEMS_JSON_MOCK.find(menuItem => 
      menuItem.id == searchId
    )

    if (menuItem != null) {
      return MenuItemType.fromJson(menuItem)
    }

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