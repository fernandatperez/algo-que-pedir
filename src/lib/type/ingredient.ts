export class Ingredient {
  id: number = 0
  nombre: string = ''
  costo: string = ''
  grupoAlimenticio: string = ''
  origenIcon: string = ''

  constructor(init?: Partial<Ingredient>) {
    Object.assign(this, init)
  }
}

// export const ingredients: Ingredient[] = [
//   new Ingredient({
//     id: 1,
//     nombre: 'Carne de Renacuajo',
//     costo: '$0.50',
//     grupoAlimenticio: 'Proteínas',
//     origenIcon: 'ph-cow',
//   }),
//   new Ingredient({
//     id: 2,
//     nombre: 'Queso Cheddar',
//     costo: '$0.50',
//     grupoAlimenticio: 'Lácteos',
//     origenIcon: 'ph-cow',
//   }),
//   new Ingredient({
//     id: 3,
//     nombre: 'Lechuga',
//     costo: '$0.50',
//     grupoAlimenticio: 'Frutas y Verduras',
//     origenIcon: 'ph-plant',
//   }),
//   new Ingredient({
//     id: 4,
//     nombre: 'Tomate',
//     costo: '$0.50',
//     grupoAlimenticio: 'Frutas y Verduras',
//     origenIcon: 'ph-plant',
//   }),
// ]