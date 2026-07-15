import { IngredientType, type IngredientJSON } from '../domain/ingredient'
import { REST_SERVER_URL } from './configuration'
import { getAxiosData } from './common'
import axios from 'axios'

class IngredientService {
  async getAllIngredients(){
    const queryIngredients = () => axios.get<IngredientJSON[]>(REST_SERVER_URL + '/ingredientes')
    return (await getAxiosData(queryIngredients)).map(IngredientType.fromJson)
  }

  async getIngredientById(id: number) {
    const queryById = () => axios.get<IngredientJSON>(REST_SERVER_URL + '/ingrediente/' + id)
    const ingredientJSON = await getAxiosData(queryById)
    return IngredientType.fromJson(ingredientJSON)
  }

  async createIngredient(ingredient: IngredientType) {
    const ingredientJSON = ingredient.toJSON()
    const response = await axios.post(REST_SERVER_URL + '/crear-ingrediente', ingredientJSON)
    return response.data
  }

  async updateIngredient(ingredient: IngredientType){
    return axios.put<IngredientJSON>(REST_SERVER_URL + '/actualizar-ingrediente/' + ingredient.id, ingredient.toJSON())
  }

  async deleteIngredient(ingredient: IngredientType){
    return axios.delete(REST_SERVER_URL + '/eliminar-ingrediente/' + ingredient.id)
  }
}

export const ingredientService = new IngredientService()