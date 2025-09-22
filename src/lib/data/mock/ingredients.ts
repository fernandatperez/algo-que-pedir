import type { IngredientType } from '$lib/type/ingredient'

export const INGREDIENT_MOCK: IngredientType[] = [
  { id: 1, name: 'Carne de Renacuajo', cost: 0.70, foodGroup: 'Proteínas', originIcon: 'ph-cow', esOrigenAnimal: true },
  { id: 2, name: 'Queso Cheddar', cost: 0.50, foodGroup: 'Lácteos', originIcon: 'ph-cow', esOrigenAnimal: true },
  { id: 3, name: 'Lechuga', cost: 0.40, foodGroup: 'Frutas y Verduras', originIcon: 'ph-plant', esOrigenAnimal: false },
  { id: 4, name: 'Tomate', cost: 0.20, foodGroup: 'Frutas y Verduras', originIcon: 'ph-plant', esOrigenAnimal: false }
]