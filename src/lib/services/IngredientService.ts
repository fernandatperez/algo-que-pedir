import { INGREDIENT_MOCK } from '$lib/data/mock/ingredients'
import { IngredientType, type IngredientJSON } from '$lib/domain/ingredient'
import { REST_SERVER_URL } from './configuration'
import { getAxiosData } from './common'
import axios from 'axios'

class IngredientService {
  async getAllIngredients(){
    const storeMail = sessionStorage.getItem('email') // envio el dato como query param
    const queryIngredients = () => axios.get<IngredientJSON[]>(REST_SERVER_URL + '/ingredientes', {params: {mail: storeMail}})
    return (await getAxiosData(queryIngredients)).map(IngredientType.fromJson)
  }

  async getIngredientById(id: number) {
    const queryById = () => axios.get<IngredientJSON>(REST_SERVER_URL + '/ingrediente/' + id)
    const ingredientJSON = await getAxiosData(queryById)
    return IngredientType.fromJson(ingredientJSON)
  }

  async createIngredient(ingredient: IngredientType) {
    const storeMail = sessionStorage.getItem('email')
    const ingredientJSON = ingredient.toJSON()
    const response = await axios.post(REST_SERVER_URL + '/crear-ingrediente', ingredientJSON, {params: {mail: storeMail}})
    return response.data
  }

  async updateIngredient(ingredient: IngredientType){
    const storeMail = sessionStorage.getItem('email') 
    return axios.put<IngredientJSON>(REST_SERVER_URL + '/actualizar-ingrediente/' + ingredient.id, ingredient.toJSON(), {params: {mail: storeMail}})
  }

  async deleteIngredient(ingredient: IngredientType){
    // const storeMail = sessionStorage.getItem('email') 
    return axios.delete(REST_SERVER_URL + '/eliminar-ingrediente/' + ingredient.id)
  }
}

export const ingredientService = new IngredientService()