import { IngredientType, type IngredientJSON } from './ingredient'

// Tipo para datos que vienen del servidor/API
export type MenuItemJSON = {
  id: number
  nombre: string
  descripcion: string
  precio: number
  valorBase: number
  imagen: string
  costoProduccion: number
  esDeAutor: boolean
  enPromocion: boolean
  ingredientes: IngredientJSON[]
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

export class ValidationMessage { //esto pordriamos usar todos la misma
  constructor(
    public field: string,
    public message: string
  ) {}
}

export class MenuItemType {
  errors: ValidationMessage[] = []

  constructor(
    public id: number = -1,
    public nombre: string = ''.trim(),
    public descripcion: string = ''.trim(),
    public precio: number = 0,
    public valorBase: number = 0,
    public imagen: string = ''.trim(),
    public costoProduccion: number = 0,
    public esDeAutor: boolean = false,
    public enPromocion: boolean = false,
    public ingredientes: IngredientType[] = [],
    public fechaDeCreacion: Date = new Date(),
    public porcentajeDescuento: number = 0
  ) {}

  static fromJson(menuItemJSON: MenuItemJSON): MenuItemType {
    return Object.assign(new MenuItemType(), menuItemJSON, {
      ingredientes: menuItemJSON.ingredientes.map(ingJson => IngredientType.fromJson(ingJson)),
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
      ingredientes: this.ingredientes.map(ing => ing.toJSON()), // Feo pero sino me dice que puede ser undefined ->
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

  validate() {
    this.errors = []
    
    if (!this.nombre) {
      this.addError('nombre', 'Debe ingresar nombre del plato')
    }
    
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

    if (this.ingredientes.length == 0) {
      this.addError('ingredients', 'El plato debe tener al menos 1 ingrediente')
    }

  }
}