export class ValidationMessage {
  constructor(
    public field: string,
    public message: string
  ) {}
}
// Mandas
export type UserJSONLoginRequest = {
  email: string
  password: string
}

// Recibis
export interface UserJSONResponse {
  email: string;
  name: string;
} 

export interface UserJSONRegisterRequest {
  name: string
  email: string
  password: string
}

export class UserType {
  errors: ValidationMessage[] = []

  constructor(
    public name: string = 'Default User'.trim(),
    public password: string = ''.trim(),
    public email: string = ''
  ) {}

  static fromJSON(userJSON: UserJSONResponse): UserType {
    return Object.assign(new UserType(), userJSON, {})
  }

  addError(field: string, message: string) {
    this.errors.push(new ValidationMessage(field, message))
  }

  validate(){
    this.errors = []
    if(!this.email){
      // eslint-disable-next-line no-console
      this.addError('email', 'Debe ingresar email')
    }
    if (!this.name){
      this.addError('name', 'Debe ingresar un nombre')
    }
    if (!this.password){
      this.addError('password', 'Debe ingresar una contraseña')
    }
  }
}
