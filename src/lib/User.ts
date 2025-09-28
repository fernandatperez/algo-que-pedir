export class User {
  email: string
  password: string
  verified: boolean = false
  constructor(email: string, password: string) {
    this.email = email
    this.password = password
  }
}