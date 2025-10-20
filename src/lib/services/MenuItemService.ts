import { MenuItemType, type MenuItemJSON } from '$lib/domain/menuItem'

import axios from 'axios'
import { REST_SERVER_URL } from './configuration'
import { IngredientType } from '$lib/domain/ingredient'

class MenuItemsService {
  async getAllMenuItems(){
    const storeMail = sessionStorage.getItem('email') // envio el dato como query param
    const response = await axios.get<MenuItemJSON[]>(REST_SERVER_URL + '/platos', { params: { mail: storeMail }})
    const cosa = response.data.map(MenuItemType.fromJson)
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
    const storeMail = sessionStorage.getItem('email') // envio el dato como query param
    item.fechaCreacion = creacion.toISOString().split('T')[0]
    // const itemJSON: MenuItemJSON = item.toJSON()
    // // eslint-disable-next-line no-console
    // console.info(itemJSON.id)
    const {id, ...itemSinId} = item
    await axios.post<MenuItemJSON>(
      REST_SERVER_URL + '/platos',
      itemSinId, { params: { mail: storeMail }}
    )
  }

  async updateMenuItem(menuItem: MenuItemType) {
    const storeMail = sessionStorage.getItem('email') 
    const menuItemJSON = menuItem.toJSON()
    
    const updateResponse = await axios.put<MenuItemJSON>(
      REST_SERVER_URL + '/platos/' + menuItem.id, 
      menuItemJSON,
      { params: { mail: storeMail }}
    )
    
    return updateResponse
  }
}

export const menuItemsService = new MenuItemsService()