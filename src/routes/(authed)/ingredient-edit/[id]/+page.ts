import type { PageLoad } from './$types'
import { ingredientService } from '$lib/services/IngredientService'
import { showError } from '$lib/domain/errorHandler'

export const load: PageLoad = async ({ params }) => {
  const id = parseInt(params.id)
  try {
    const ingredient = await ingredientService.getIngredientById(id)
    return { ingredient }
  } catch (error) {
    showError('Error loading ingredient', error)
  }
}
