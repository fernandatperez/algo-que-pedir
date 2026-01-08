export type StoreJSON = {
  id: number
  name: string
  address: string
}

export class ValidationMessage {
  constructor(
    public field: string,
    public message: string
  ) {}
}

export class Store {
  errors: ValidationMessage[] = []

  constructor(
    public id?: number,
    public name: string = '',
    public address: string = '',
  ) {}

  static fromJson(storeJSON: StoreJSON): Store {
    return Object.assign(new Store(), storeJSON, {})
  }

  addError(field: string, message: string) {
    this.errors.push(new ValidationMessage(field, message))
  }

  validate(){
    this.errors = []
    if(!this.name){
      this.addError('name', 'Debe ingresar nombre')
    }
    if(!this.address){
      this.addError('name', 'Debe ingresar nombre')
    }
  }
}