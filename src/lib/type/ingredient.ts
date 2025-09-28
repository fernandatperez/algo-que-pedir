// https://www.typescriptlang.org/docs/handbook/typescript-in-5-minutes-func.html#readonly-and-const

// const aplica a la referencia de la variable.
// Significa que no podes reasignar la variable foodGroups a otra cosa, pero si modificarla

// as const → convierte el contenido en literal y readonly
// readonly → hace que las propiedades del objeto no puedan ser modificadas
// Eso te da seguridad de tipos: no podés asignar un foodGroup que no esté en la lista.
export const foodGroups = [
  { label: 'Frutas y Verduras', value: 'Frutas y Verduras', icon: 'ph-plant' },
  { label: 'Proteínas', value: 'Proteínas', icon: 'ph-cow' },
  { label: 'Cereales y tuberculos', value: 'Cereales y tuberculos', icon: 'ph-grain' },
  { label: 'Lácteos', value: 'Lácteos', icon: 'ph-cow' },
  { label: 'Grasas y aceites', value: 'Grasas y aceites', icon: 'ph-cow' },
  { label: 'Azucares y dulces', value: 'Azucares y dulces', icon: 'ph-jar' },
] as const

// typeof foodGroups → el tipo del array literal.
// [number] → “cualquier elemento del array”.
// ['value'] → extrae la propiedad value de cada elemento.
// Resultado: una unión de todos los valores literales de value.
export type FoodGroupValue = (typeof foodGroups)[number]['value'];

export interface IngredientType {
  id: number;
  name: string;
  cost: number;
  foodGroup: FoodGroupValue;
  originIcon: string;
  esOrigenAnimal: boolean
}

// Un “objeto vacío” que te sirve como plantilla cuando quiero crear un nuevo ingrediente
export function createEmptyIngredient(): IngredientType {
  return {
    id: 0,
    name: ''.trim(),
    cost: 0,
    foodGroup: 'Frutas y Verduras', // le pongo un valor por defecto
    originIcon: '',
    esOrigenAnimal: true
  }
}

// export class IngredientType {
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
// constructor(
//   public id: number = 0,
//   // trim: eliminar los espacios en blanco al inicio y al final del texto.
//   public name: string = ''.trim(),
//   public cost: number = 0,
//   public foodGroup: FoodGroupValue = 'Frutas y Verduras',
//   public originIcon: string = '',
//   public esOrigenAnimal: boolean = true
// ) {}
// }
