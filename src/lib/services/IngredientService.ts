import { INGREDIENT_MOCK } from '$lib/data/mock/ingredients'
import { IngredientType, type IngredientJSON } from '$lib/domain/ingredient'
import { REST_SERVER_URL } from './configuration'
import { getAxiosData } from './common'
import axios from 'axios'

class IngredientService {
  async getAllIngredients(){
    const queryIngredients = () => axios.get<IngredientJSON[]>(REST_SERVER_URL + '/ingredientes')
    return (await getAxiosData(queryIngredients)).map(IngredientType.fromJson)
    // return INGREDIENT_MOCK.map(IngredientType.fromJson)
  }

  async getIngredientById(id: number) {
    const queryById = () => axios.get<IngredientJSON>(REST_SERVER_URL + '/ingredientes/' + id)
    const ingredientJSON = await getAxiosData(queryById)
    return IngredientType.fromJson(ingredientJSON)
    // return INGREDIENT_MOCK.find(ingredient => ingredient.id !== undefined && ingredient.id === id)
  }

  async createIngredient(ingredient: IngredientType) {
    // return axios.post(REST_SERVER_URL + '/ingredientes', ingredient)
    const ingredientJSON = ingredient.toJSON()
    const response = await axios.post(REST_SERVER_URL + '/ingredientes', ingredientJSON)
    return response.data
    // const ingredientJSON = { ...ingredient, id: ingredient.id as number }
    // INGREDIENT_MOCK.push(ingredientJSON)
    // return ingredientJSON  
  }

  async updateIngredient(ingredient: IngredientType){
    return axios.put<IngredientJSON>(REST_SERVER_URL + '/ingredientes/' + ingredient.id, ingredient.toJSON())
    
    // const newIngredient = INGREDIENT_MOCK.findIndex(i => i.id === ingredient.id)
    // if (newIngredient !== -1) {
    //   // reemplazamos el ingrediente por el actualizado
    //   INGREDIENT_MOCK[newIngredient] = { ...ingredient, id: ingredient.id }
    //   return INGREDIENT_MOCK[newIngredient]
    // } else {
    //   throw new Error('Ingrediente no encontrado')
    // }
  }

  async deleteIngredient(ingredient: IngredientType){
    return axios.delete(REST_SERVER_URL + '/ingredientes/' + ingredient.id)
    // const index = INGREDIENT_MOCK.findIndex(item => item.id === ingredient.id)
    // if (index !== -1) {
    //   return INGREDIENT_MOCK.splice(index, 1)[0]
    // }
    // return null
  }
}

export const ingredientService = new IngredientService()