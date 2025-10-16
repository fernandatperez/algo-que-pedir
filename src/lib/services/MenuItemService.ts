import { MenuItemType, type MenuItemJSON } from '$lib/domain/menuItem'

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
    const creacion = new Date()
    item.fechaCreacion = creacion.toISOString().split('T')[0]
    // const itemJSON: MenuItemJSON = item.toJSON()
    // // eslint-disable-next-line no-console
    // console.info(itemJSON.id)
    const {id, ...itemSinId} = item
    await axios.post<MenuItemJSON>(
      REST_SERVER_URL + '/platos',
      itemSinId
    )
  }

  async updateMenuItem(menuItem: MenuItemType) {
    const menuItemJSON = menuItem.toJSON()
    return axios.put<MenuItemJSON>(REST_SERVER_URL + '/platos/' + menuItem.id, menuItemJSON)
  }
}

export const menuItemsService = new MenuItemsService()