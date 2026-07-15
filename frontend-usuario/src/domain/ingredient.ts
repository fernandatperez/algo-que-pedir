import { ValidationMessage } from './validationMessage'

export type IngredientJSON = {
  id?: number
  name: string
  cost: number
  foodGroup: FoodGroupValue
  esOrigenAnimal: boolean
}

export enum FoodGroupValue {
  FRUTAS_Y_VERDURAS = 'Frutas y Verduras',
  PROTEINAS = 'Proteínas',
  CEREALES_Y_TUBERCULOS = 'Cereales y tuberculos',
  LACTEOS = 'Lácteos',
  GRASAS_Y_ACEITES = 'Grasas y aceites',
  AZUCARES_Y_DULCES = 'Azucares y dulces'
}

// Record<Keys, Type> → crea un objeto cuyas claves son Keys y cuyos valores son Type
export const foodGroupDict: Record<FoodGroupValue, { icon: string; label: string }> = {
  [FoodGroupValue.FRUTAS_Y_VERDURAS]: { icon: 'ph-plant', label: 'Frutas y Verduras' },
  [FoodGroupValue.PROTEINAS]: { icon: 'ph-cow', label: 'Proteínas' },
  [FoodGroupValue.CEREALES_Y_TUBERCULOS]: { icon: 'ph-plant', label: 'Cereales y tuberculos' },
  [FoodGroupValue.LACTEOS]: { icon: 'ph-cow', label: 'Lácteos' },
  [FoodGroupValue.GRASAS_Y_ACEITES]: { icon: 'ph-cow', label: 'Grasas y aceites' },
  [FoodGroupValue.AZUCARES_Y_DULCES]: { icon: 'ph-plant', label: 'Azucares y dulces' },
}

export const gruposVegetales = [
  FoodGroupValue.FRUTAS_Y_VERDURAS,
  FoodGroupValue.CEREALES_Y_TUBERCULOS,
  FoodGroupValue.AZUCARES_Y_DULCES
]

export class IngredientType {
  errors: ValidationMessage[] = []

  constructor(
      public id?: number, 
      // trim: eliminar los espacios en blanco al inicio y al final del texto.
      public name: string = ''.trim(),
      public cost: number = 0,
      public foodGroup: FoodGroupValue = FoodGroupValue.AZUCARES_Y_DULCES,
      public esOrigenAnimal: boolean = true
  ) {
    if (gruposVegetales.find(grupo => grupo == this.foodGroup)) {
      this.esOrigenAnimal = false
    }
  }

  static fromJson(ingredientJSON: IngredientJSON): IngredientType {
    return Object.assign(new IngredientType(), ingredientJSON, {})
  }

  toJSON(): IngredientJSON {
    return {
      id: this.id,
      name: this.name,
      cost: this.cost,
      foodGroup: this.foodGroup,
      esOrigenAnimal: this.esOrigenAnimal
    }
  }

  icono(): string {
    return this.esOrigenAnimal ? 'ph-cow' : 'ph-plant'
  }

  addError(field: string, message: string) {
    this.errors.push(new ValidationMessage(field, message))
  }

  invalid(): boolean {
    return this.errors.length > 0
  }

  validate(){
    this.errors = []
    if(!this.name){
      this.addError('name', 'Debe ingresar nombre')
    }
    if(this.cost <= 0 || !this.cost){
      this.addError('cost', 'El costo debe ser mayor a 0')
    }
    if (!this.foodGroup || undefined){
      this.addError('foodGroup', 'Debe seleccionar un grupo alimenticio')
    }
  }
}