import { ValidationMessage } from './user'

export class StoreRate {
  MAX_CHARACTERS: number = 250

  errors: ValidationMessage[] = []
  rate: number
  experienceDesc: string

  constructor(
    rate: number = 0,
    experienceDesc: string = ''

  ) {
    this.rate = rate
    this.experienceDesc = experienceDesc
  }
  
  checkTextMaxLength (maxLength: number = this.MAX_CHARACTERS): boolean { return this.experienceDesc.length > maxLength }

  addError(field: string, message: string) {
    this.errors.push(new ValidationMessage(field, message))
  }
  
  validate(){
    this.errors = []
    if(this.checkTextMaxLength()){
      this.addError('experience-description', 'El texto es demasiado largo')
    }
  }

  static fromJSON(storeRateJSON: StoreRateJSON): StoreRate {
    return Object.assign(new StoreRate(), storeRateJSON, {})
  }
}

export type StoreRateJSON = {
  rate: number,
  experienceDesc: string
}