export class ValidationMessage {
  constructor(
    public field: string,
    public message: string
  ) {}
}

export type UserJSON = {
  username: string
  password: string
}
//! esto lo saque porque por lo que entendi de el profe no tiene que estar la contraseña aca, la tenemos en el back

export interface UserLoginJSON {
  // token: string; // esto se podria poner
  username: string;
  email: string;
}

export class UserType {
  errors: ValidationMessage[] = []

  constructor(
    public username: string = ''.trim(),
    public password: string = ''.trim(),
    public email?: string
  ) {}

  static fromJson(userJSON: UserJSON): UserType {
    return Object.assign(new UserType(), userJSON, {})
  }

  static fromLoginJson(userJSON: UserLoginJSON): UserType {
    return Object.assign(new UserType(), userJSON, {})
  }

  addError(field: string, message: string) {
    this.errors.push(new ValidationMessage(field, message))
  }

  validate(){
    this.errors = []
    if(!this.username){
      this.addError('username', 'Debe ingresar email')
    }
    if (!this.password){
      this.addError('password', 'Debe ingresar una contraseña')
    }
  }
}
