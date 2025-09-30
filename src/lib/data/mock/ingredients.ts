import { FoodGroupValue, type IngredientJSON } from '$lib/domain/ingredient'

// simulamos los objetos JSON que vienen del backend
export const firstIngredient: IngredientJSON = {
  id: 1,
  name: 'Carne de Renacuajo',
  cost: 0.7,
  foodGroup: FoodGroupValue.PROTEINAS,
  esOrigenAnimal: true
}

export const secondIngredient: IngredientJSON = {
  id: 2,
  name: 'Queso Cheddar',
  cost: 0.5,
  foodGroup: FoodGroupValue.LACTEOS,
  esOrigenAnimal: true
}

export const thirdIngredient: IngredientJSON = {
  id: 3,
  name: 'Lechuga',
  cost: 0.4,
  foodGroup: FoodGroupValue.FRUTAS_Y_VERDURAS,
  esOrigenAnimal: false
}

export const fourthIngredient: IngredientJSON = {
  id: 4,
  name: 'Tomate',
  cost: 0.2,
  foodGroup: FoodGroupValue.FRUTAS_Y_VERDURAS,
  esOrigenAnimal: false
}

export const INGREDIENT_MOCK: IngredientJSON[] = [firstIngredient, secondIngredient, thirdIngredient, fourthIngredient]

// export const INGREDIENT_MOCK: IngredientType[] = [
//   // que devuelva el ingrediente creado
//   { id: 1, name: 'Carne de Renacuajo', cost: 0.70, foodGroup: 'Proteínas', originIcon: 'ph-cow', esOrigenAnimal: true },
//   { id: 2, name: 'Queso Cheddar', cost: 0.50, foodGroup: 'Lácteos', originIcon: 'ph-cow', esOrigenAnimal: true },
//   { id: 3, name: 'Lechuga', cost: 0.40, foodGroup: 'Frutas y Verduras', originIcon: 'ph-plant', esOrigenAnimal: false },
//   { id: 4, name: 'Tomate', cost: 0.20, foodGroup: 'Frutas y Verduras', originIcon: 'ph-plant', esOrigenAnimal: false }
// ]