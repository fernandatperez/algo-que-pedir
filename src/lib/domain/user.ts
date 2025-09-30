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

export class UserType {
  errors: ValidationMessage[] = []

  constructor(
    public username: string = ''.trim(),
    public password: string = ''.trim()
  ) {}

  static fromJson(userJSON: UserJSON): UserType {
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