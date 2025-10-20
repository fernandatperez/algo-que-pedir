import { MenuItemType, type MenuItemJSON, type MenuItemJSONReduced } from '$lib/domain/menuItem'

import axios from 'axios'
import { REST_SERVER_URL } from './configuration'
import { ingredientService } from './IngredientService'
import { IngredientType } from '$lib/domain/ingredient'

class MenuItemsService {
  async getAllMenuItems(){
    const storeMail = sessionStorage.getItem('email') // envio el dato como query param
    const response = await axios.get<MenuItemJSONReduced[]>(REST_SERVER_URL + '/platos', { params: { mail: storeMail }})
    const menuItemReduced = response.data
    return menuItemReduced

  }

  async getMenuItem(searchId: number) {
    const response = await axios.get<MenuItemJSON>(
      REST_SERVER_URL + '/platos/' + searchId
    )
    // Como del back traigo IDs, los tengo que buscar para mostrarlos aca
    const ingredients = await Promise.all(
      response.data.ingredientes.map(ingId =>
        ingredientService.getIngredientById(ingId)
      )
    )

    const plato = MenuItemType.fromJson(response.data)
    plato.ingredientes = ingredients

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