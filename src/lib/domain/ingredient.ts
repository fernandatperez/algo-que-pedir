import { ValidationMessage } from './validationMessage'

export type IngredientJSON = {
  id: number
  name: string
  cost: number
  foodGroup?: FoodGroupValue
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
  [FoodGroupValue.CEREALES_Y_TUBERCULOS]: { icon: 'ph-bread', label: 'Cereales y tuberculos' },
  [FoodGroupValue.LACTEOS]: { icon: 'ph-cow', label: 'Lácteos' },
  [FoodGroupValue.GRASAS_Y_ACEITES]: { icon: 'ph-cow', label: 'Grasas y aceites' },
  [FoodGroupValue.AZUCARES_Y_DULCES]: { icon: 'ph-jar', label: 'Azucares y dulces' },
}

export class IngredientType {
  errors: ValidationMessage[] = []

  constructor(
      public id?: number, 
      // trim: eliminar los espacios en blanco al inicio y al final del texto.
      public name: string = ''.trim(),
      public cost: number = 0,
      public foodGroup?: FoodGroupValue,
      public esOrigenAnimal: boolean = true 
  ) {}

  static fromJson(ingredientJSON: IngredientJSON): IngredientType {
    return Object.assign(new IngredientType(), ingredientJSON, {})
  }

  addError(field: string, message: string) {
    this.errors.push(new ValidationMessage(field, message))
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

// https://www.typescriptlang.org/docs/handbook/typescript-in-5-minutes-func.html#readonly-and-const

// const aplica a la referencia de la variable.
// Significa que no podes reasignar la variable foodGroups a otra cosa, pero si modificarla

// as const → convierte el contenido en literal y readonly
// readonly → hace que las propiedades del objeto no puedan ser modificadas
// Eso te da seguridad de tipos: no podés asignar un foodGroup que no esté en la lista.
// export const foodGroups = [
//   { label: 'Frutas y Verduras', value: 'Frutas y Verduras', icon: 'ph-plant' },
//   { label: 'Proteínas', value: 'Proteínas', icon: 'ph-cow' },
//   { label: 'Cereales y tuberculos', value: 'Cereales y tuberculos', icon: 'ph-grain' },
//   { label: 'Lácteos', value: 'Lácteos', icon: 'ph-cow' },
//   { label: 'Grasas y aceites', value: 'Grasas y aceites', icon: 'ph-cow' },
//   { label: 'Azucares y dulces', value: 'Azucares y dulces', icon: 'ph-jar' },
// ] as const // key y un objeto que tenga value e icon

// // typeof foodGroups → el tipo del array literal.
// // [number] → “cualquier elemento del array”.
// // ['value'] → extrae la propiedad value de cada elemento.
// // Resultado: una unión de todos los valores literales de value.
// export type FoodGroupValue = (typeof foodGroups)[number]['value'];

// export interface IngredientType {
//   id: number;
//   name: string;
//   cost: number;
//   foodGroup: FoodGroupValue;
//   originIcon: string;
//   esOrigenAnimal: boolean
// }

// // Un “objeto vacío” que te sirve como plantilla cuando quiero crear un nuevo ingrediente
// export function createEmptyIngredient(): IngredientType {
//   return {
//     id: 0,
//     name: ''.trim(),
//     cost: 0,
//     foodGroup: 'Frutas y Verduras', // le pongo un valor por defecto
//     originIcon: '',
//     esOrigenAnimal: true
//   }
// }
  
//   id: number
//   name: string
//   cost: number
//   foodGroup: FoodGroupValue
//   originIcon: string
//   esOrigenAnimal: boolean
//   constructor(id: number, name: string, cost: number, foodGroup: FoodGroupValue, originIcon: string, esOrigenAnimal: boolean) {
//     this.id = id 
//     this.name = name
//     this.cost = cost
//     this.foodGroup = foodGroup
//     this.originIcon = originIcon
//     this.esOrigenAnimal = esOrigenAnimal
//   }