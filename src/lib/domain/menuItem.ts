import { storeMOCK } from '$lib/data/mock/store'
import { IngredientType } from './ingredient'
import type { StoreJSON } from './store'

// Tipo para datos que vienen del servidor/API
export type MenuItemJSON = {
  id: number
  // alt: string
  nombre: string
  descripcion: string
  precio: number
  imagen: string
  costoProduccion: number
  esDeAutor: boolean
  enPromocion: boolean
  ingredientes: IngredientType[]
  store: StoreJSON
}

export class ValidationMessage { //esto pordiramos usar todos la misma
  constructor(
    public field: string,
    public message: string
  ) {}
}

export class MenuItemType {
  errors: ValidationMessage[] = []

  constructor(
    public id: number = -1,
    // trim: eliminar los espacios en blanco al inicio y al final del texto
    // public alt: string = ''.trim(),
    public nombre: string = ''.trim(),
    public descripcion: string = ''.trim(),
    public precio: number = 0,
    public imagen: string = ''.trim(),
    public costoProduccion: number = 0,
    public esDeAutor: boolean = false,
    public enPromocion: boolean = false,
    public ingredientes: IngredientType[] = [],
    public store: StoreJSON = storeMOCK
  ) {}

  static fromJson(menuItemJSON: MenuItemJSON): MenuItemType {
    return Object.assign(new MenuItemType(), menuItemJSON, {})
  }

  addError(field: string, message: string) {
    this.errors.push(new ValidationMessage(field, message))
  }

  toJSON(): MenuItemJSON {
    return {
      id: this.id,
      // alt: this.alt,
      nombre: this.nombre,
      descripcion: this.descripcion,
      precio: this.precio,
      imagen: this.imagen,
      costoProduccion: this.costoProduccion,
      esDeAutor: this.esDeAutor,
      enPromocion: this.enPromocion,
      ingredientes: this.ingredientes,
      store: this.store
    }
  }

  static availableIngs(ingredients: IngredientType[], menuItem: MenuItemJSON): IngredientType[] {
    const difference: IngredientType[] = ingredients.filter(ingredient => !menuItem.ingredientes.find(ing => ingredient.id == ing.id))
    return difference
  }

  validate() {
    this.errors = []
    
    if (!this.nombre) {
      this.addError('nombre', 'Debe ingresar nombre del plato')
    }
    
    if (this.precio <= 0 || !this.precio) {
      this.addError('precio', 'El precio debe ser mayor a 0')
    }
    
    if (!this.imagen) {
      this.addError('imagen', 'Debe seleccionar una imagen')
    }
  }

  // Metodo helper para formatear precio
  getFormattedPrice(): string {
    return `$${this.precio}`
  }

  // Metodo helper para obtener la ruta completa de la imagen no se si esta bien porque ya tiene parte de la ruta
  getImagePath(): string {
    return `src/lib/assets/img/${this.imagen}`
  }
}

// Factory function sin nada, la idea seria que (AGREGAR NUEVO PLATO) llame esto y te lleva a una nueva pagina con esto o algo asi
export function createEmptyMenuItem(): MenuItemType {
  return new MenuItemType()
}