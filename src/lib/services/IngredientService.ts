import { INGREDIENT_MOCK } from '$lib/data/mock/ingredients'
import { IngredientType } from '$lib/domain/ingredient'

class IngredientService {
  async getAllIngredients(){
    // despues cuando tengamos el backend, haremos la llamada a la API REST
    // const queryTareas = () => axios.get<TareaJSON[]>(REST_SERVER_URL + '/tareas')
    // return (await getAxiosData(queryTareas)).map(Tarea.fromJson)
    return INGREDIENT_MOCK.map(IngredientType.fromJson)
  }

  async getIngredientById(id: number) {
    return INGREDIENT_MOCK.find(ingredient => ingredient.id !== undefined && ingredient.id === id)
  }

  async createIngredient(ingredient: IngredientType){
    const ingredientJSON = { ...ingredient, id: ingredient.id as number }
    return INGREDIENT_MOCK.push(ingredientJSON)
  }

  async updateIngredient(ingredient: IngredientType){
    return {ingredient}
  }

  async deleteIngredient(ingredient: IngredientType){
    const index = INGREDIENT_MOCK.findIndex(item => item.id === ingredient.id)
    if (index !== -1) {
      return INGREDIENT_MOCK.splice(index, 1)[0]
    }
    return null
  }
}

export const ingredientService = new IngredientService()