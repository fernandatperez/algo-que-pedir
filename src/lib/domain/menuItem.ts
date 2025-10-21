import { IngredientType } from './ingredient'

// Tipo para datos que vienen del servidor/API
export type MenuItemJSON = {
  id: number
  // alt: string
  nombre: string
  descripcion: string
  precio: number
  valorBase: number
  imagen: string
  costoProduccion: number
  esDeAutor: boolean
  enPromocion: boolean
  // ->
  ingredientes: number[]
  fechaDeCreacion: string
  porcentajeDescuento: number
}

export type MenuItemJSONReduced = {
  id: number
  nombre: string
  descripcion: string
  imagen: string
  precio: number
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
    public valorBase: number = 0,
    public imagen: string = ''.trim(),
    public costoProduccion: number = 0,
    public esDeAutor: boolean = false,
    public enPromocion: boolean = false,
    // ingredientes es una lista de IDs
    public ingredientes: IngredientType[] = [],
    public fechaDeCreacion: Date = new Date(),
    public porcentajeDescuento: number = 0
  ) {}

  static fromJson(menuItemJSON: MenuItemJSON): MenuItemType {
    return Object.assign(new MenuItemType(), menuItemJSON, {
      porcentajeDescuento: menuItemJSON.porcentajeDescuento*100,
      fechaDeCreacion: new Date(menuItemJSON.fechaDeCreacion)
    })
  }

  addError(field: string, message: string) {
    this.errors.push(new ValidationMessage(field, message))
  }

  setPromocion() {
    if (this.porcentajeDescuento > 0) {
      this.enPromocion = true
    }
  }

  esNuevo(): boolean {
    const ahora = new Date()
    const diferenciaMs = ahora.getTime() - this.fechaDeCreacion.getTime()
    const dias = diferenciaMs / (1000 * 60 * 60 * 24)
    return dias <= 30
  }

  toJSON(): MenuItemJSON {
    return {
      id: this.id,
      nombre: this.nombre,
      descripcion: this.descripcion,
      precio: this.precio,
      valorBase: this.valorBase,
      imagen: this.imagen,
      costoProduccion: this.costoProduccion,
      esDeAutor: this.esDeAutor,
      enPromocion: this.enPromocion,
      ingredientes: this.ingredientes.map(ing => ing.id) as number[], // Feo pero sino me dice que puede ser undefined ->
      // "Store" tambien va a haber que serializarlo a JSON para mandarlo, y convertirlo en dominio en el back. CUando vuelva para aca, serializar a json en el back
      // y despues a dominio de aca
      fechaDeCreacion: this.fechaDeCreacion.toISOString().split('T')[0],
      porcentajeDescuento: this.porcentajeDescuento
    }
  }

  static availableIngs(availableIngredients: IngredientType[], menuItemIngredients: number[]): IngredientType[] {
    // -> ahora se filtra con IDs. Testear
    const difference: IngredientType[] = availableIngredients.filter(ingredient => !menuItemIngredients.find(id => ingredient.id == id))
    return difference
  }

  // costoDeProduccion(): string {
  //   return this.ingredientes.reduce((acc, ing) => {return acc + ing.cost}, 0).toFixed(2)
  // }

  validate() {
    this.errors = []
    
    if (!this.nombre) {
      this.addError('nombre', 'Debe ingresar nombre del plato')
    }
    
    // if (this.precio <= 0 || !this.precio) {
    //   this.addError('precio', 'El precio debe ser mayor a 0')
    // }
    
    if (!this.imagen) {
      this.addError('imagen', 'Debe seleccionar una imagen')
    }

    if (!this.valorBase) {
      this.addError('valorBase', 'Ingrese un valor base para el plato.')
    }

    if (this.valorBase <= 0) {
      this.addError('valorBase', 'El valor base no puede ser 0 o menor a el')
    }

    if (0 > this.porcentajeDescuento || this.porcentajeDescuento > 1) {
      this.addError('porcentajeDescuento', 'Ingrese un valor entre 0 y 100.')
    }

  }
  // Si no usamos esto saquemoslo ->

  // Metodo helper para formatear precio
  // getFormattedPrice(): string {
  //   return `$${this.precio}`
  // }

  // Metodo helper para obtener la ruta completa de la imagen no se si esta bien porque ya tiene parte de la ruta
  getImagePath(): string {
    return `src/lib/assets/img/${this.imagen}`
  }
}

// Factory function sin nada, la idea seria que (AGREGAR NUEVO PLATO) llame esto y te lleva a una nueva pagina con esto o algo asi
export function createEmptyMenuItem(): MenuItemType {
  return new MenuItemType()
}