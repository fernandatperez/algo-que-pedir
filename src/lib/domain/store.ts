import { ValidationMessage } from './validationMessage'


export type StoreJSON ={
  name: string
  storeURL: string
  storeAddress: string              
  storeAltitude: number              
  storeLatitude: number               
  storeLongitude: number
  storeAppCommission: number
  storeAuthorCommission: number
  storePaymentEfectivo: boolean
  storePaymentQR: boolean
  storePaymentTransferencia: boolean
}

export class StoreType {
  errors: ValidationMessage[] = []

  constructor(
    public name: string = ''.trim(), 
    public storeURL: string = ''.trim(), 
    public storeAddress: string = ''.trim(),               
    public storeAltitude: number = 0,            
    public storeLatitude: number = 0,              
    public storeLongitude: number = 0,
    public storeAppCommission: number = 0,
    public storeAuthorCommission: number = 0,
    public storePaymentEfectivo: boolean = true,
    public storePaymentQR: boolean = true,
    public storePaymentTransferencia: boolean = true,
  ) {}


  static fromJson(storeJSON: StoreJSON): StoreType {
    // Crear instancia pasando todos los parámetros
    return Object.assign(new StoreType(), storeJSON, {})
  }

  toJSON(): StoreJSON {
    return {
      name: this.name,
      storeURL: this.storeURL,
      storeAddress: this.storeAddress,                 
      storeAltitude: this.storeAltitude,             
      storeLatitude: this.storeLatitude,               
      storeLongitude: this.storeLongitude,
      storeAppCommission: this.storeAppCommission,
      storeAuthorCommission: this.storeAuthorCommission,
      storePaymentEfectivo: this.storePaymentEfectivo,
      storePaymentQR: this.storePaymentQR,
      storePaymentTransferencia: this.storePaymentTransferencia,
    }
  }

  addError(field: string, message: string) {
    this.errors.push(new ValidationMessage(field, message))
  }

  invalid(): boolean {
    return this.errors.length > 0
  }

  getValidImageUrl(defaultImage: string): string {
    if (!this.storeURL || 
        this.storeURL.trim() === '' || 
        this.storeURL === 'null' || 
        this.storeURL === 'undefined') {
      return defaultImage
    }
    return this.storeURL
  }


  validate() {
    this.errors = []
   
    if (!this.name) {
      this.addError('name', 'Debe ingresar nombre del local')
    }

    // Validación de URL vacía
    if (!this.storeURL || this.storeURL.trim() === '' || this.storeURL === 'null' || this.storeURL === 'undefined') {
      this.addError('url', 'Debe ingresar URL de imagen')
    }else{
    // Verificar que comience con http o https (como en el back)
      if (!this.storeURL.startsWith('http://') && !this.storeURL.startsWith('https://')) {
        this.addError('url', 'La URL debe comenzar con http:// o https://')
      }}

    

    if (!this.storeAddress) {
      this.addError('address', 'Debe ingresar dirección')
    }

    if (this.storeAltitude == 0) {
      this.addError('altitude', 'Debe ingresar altura válida')
    }

    if (this.storeLatitude == 0) {
      this.addError('latitude', 'Debe ingresar latitud válida')
    }

    if (this.storeLongitude == 0) {
      this.addError('longitude', 'Debe ingresar altura válida')
    }

    if (this.storeAuthorCommission == 0) {
      this.addError('authorcommission', 'Debe ingresar una comision')
    }

    if (this.storeAppCommission == 0) {
      this.addError('appcommission', 'Debe ingresar una comision')
    }

    if (!this.storePaymentEfectivo && !this.storePaymentQR && !this.storePaymentTransferencia )
      this.addError('metodopago', 'Debe ingresar al menos un metodo de pago')
  }
}
