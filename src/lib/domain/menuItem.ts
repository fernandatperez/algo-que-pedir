import { IngredientType } from './ingredient'

// Tipo para datos que vienen del servidor/API
export type MenuItemJSON = {
  id: number
  alt: string
  nombre: string
  descripcion: string
  precio: number
  imagen: string
  esDeAutor: boolean
  enPromocion: boolean
  ingredientes: IngredientType[]
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
    public alt: string = ''.trim(),
    public nombre: string = ''.trim(),
    public descripcion: string = ''.trim(),
    public precio: number = 0,
    public imagen: string = ''.trim(),
    public esDeAutor: boolean = false,
    public enPromocion: boolean = false,
    public ingredientes: IngredientType[] = []
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
      alt: this.alt,
      nombre: this.nombre,
      descripcion: this.descripcion,
      precio: this.precio,
      imagen: this.imagen,
      esDeAutor: this.esDeAutor,
      enPromocion: this.enPromocion,
      ingredientes: this.ingredientes
    }
  }

  validate() {
    this.errors = []
    
    if (!this.nombre) {
      this.addError('nombre', 'Debe ingresar nombre del plato')
    }
    
    if (!this.descripcion) {
      this.addError('descripcion', 'Debe ingresar descripción del plato')
    }
    
    if (this.precio <= 0 || !this.precio) {
      this.addError('precio', 'El precio debe ser mayor a 0')
    }
    
    if (!this.imagen) {
      this.addError('imagen', 'Debe seleccionar una imagen')
    }
    
    if (!this.alt) {
      this.addError('alt', 'Debe ingresar texto alternativo para la imagen')
    }

    if (!this.esDeAutor) {
      this.addError('esDeAutor', 'Especifique si es o no de autor')
    }

    if (!this.enPromocion) {
      this.addError('enPromocion', 'Especifique si esta o no en promo')
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

  static costoDeProduccion(menuItemJSON: MenuItemJSON): number {
    const menuItem = Object.assign(new MenuItemType(), menuItemJSON, {})
    return menuItem.ingredientes.reduce((accumulator, currentItem) => 
      accumulator + currentItem.cost, 0
    )
  }
}

// Factory function sin nada, la idea seria que (AGREGAR NUEVO PLATO) llame esto y te lleva a una nueva pagina con esto o algo asi
export function createEmptyMenuItem(): MenuItemType {
  return new MenuItemType()
}