import { MenuItemType, type MenuItemJSON, type MenuItemJSONReduced } from '../domain/menuItem'

import axios from 'axios'
import { REST_SERVER_URL } from './configuration'

class MenuItemsService {
  async getAllMenuItems(){
    const storeMail = sessionStorage.getItem('email') // envio el dato como query param
    const response = await axios.get<MenuItemJSONReduced[]>(REST_SERVER_URL + '/platos', { params: { mail: storeMail }})
    const menuItemReduced = response.data
    return menuItemReduced
  }

  async getItemsByStore(id: number, userId: number) {
    const response = await axios.get<MenuItemJSONReduced[]>(`${REST_SERVER_URL}/platos-react/${id}?userId=${userId}`)
    return response.data
  }

  async getMenuItem(searchId: number) {
    const response = await axios.get<MenuItemJSON>(
      REST_SERVER_URL + '/platos/' + searchId
    )
    // Como del back traigo IDs, los tengo que buscar para mostrarlos aca
    // const ingredients = await Promise.all(
    //   response.data.ingredientes.map(ing =>
    //     ingredientService.getIngredientById(ing.id?? 0)
    //   )
    // )

    const plato = MenuItemType.fromJson(response.data)
    // plato.ingredientes = ingredients

    if (plato != null)
      return plato

    return new MenuItemType()
  }

  async createMenuItem(item: MenuItemType) {
    const storeMail = sessionStorage.getItem('email') // envio el dato como query param
    item.fechaDeCreacion = new Date()
    // console.log(item.fechaDeCreacion)
    await axios.post<MenuItemJSON>(
      REST_SERVER_URL + '/platos',
      item.toJSON(), 
      { params: { mail: storeMail }}
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