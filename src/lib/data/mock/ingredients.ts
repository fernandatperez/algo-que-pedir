import type { IngredientType } from '$lib/type/ingredient'

export const INGREDIENT_MOCK: IngredientType[] = [
  { id: 1, nombre: 'Carne de Renacuajo', costo: '$0.50', grupoAlimenticio: 'Proteínas', origenIcon: 'ph-cow' },
  { id: 2, nombre: 'Queso Cheddar', costo: '$0.50', grupoAlimenticio: 'Lácteos', origenIcon: 'ph-cow' },
  { id: 3, nombre: 'Lechuga', costo: '$0.50', grupoAlimenticio: 'Frutas y Verduras', origenIcon: 'ph-plant' },
  { id: 4, nombre: 'Tomate', costo: '$0.50', grupoAlimenticio: 'Frutas y Verduras', origenIcon: 'ph-plant' }
]