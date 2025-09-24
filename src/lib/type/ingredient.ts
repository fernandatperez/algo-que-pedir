export interface IngredientType {
  id: number;
  nombre: string;
  costo: string;
  grupoAlimenticio: string;
  origenIcon: string;
}

// Un “objeto vacío” que te sirve como plantilla cuando quiero crear un nuevo ingrediente
export function createEmptyIngredient(): IngredientType {
  return {
    id: 0,
    nombre: '',
    costo: '',
    grupoAlimenticio: '',
    origenIcon: ''
  }
}