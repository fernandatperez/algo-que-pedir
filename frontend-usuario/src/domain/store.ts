import { ValidationMessage } from './validationMessage'

export type StoreJSON ={
  id: number
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
  searchName: string
  usuarioCercano: boolean
}


export class StoreType {
  errors: ValidationMessage[] = []

  constructor(
    public id: number= 0,
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
    public searchName: string = ''.trim(),
    public usuarioCercano: boolean = false,
  ) {}


  static fromJson(storeJSON: StoreJSON): StoreType {
    return Object.assign(new StoreType(), storeJSON, {
      storeAppCommission: storeJSON.storeAppCommission*100,
      storeAuthorCommission: storeJSON.storeAuthorCommission*100,
      usuarioCercano: storeJSON.usuarioCercano,
    })
  }

  toJSON(): StoreJSON {
    return {
      id:this.id,
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
      searchName: this.searchName,
      usuarioCercano: this.usuarioCercano,
    }
  }

  setSearchValue(inputSearchName: string){
    this.searchName = inputSearchName
    var nombreabuscar = this.searchName
    // console.info('Buscando:', nombreabuscar)
  }

  addError(field: string, message: string) {
    this.errors.push(new ValidationMessage(field, message))
  }

  invalid(): boolean {
    return this.errors.length > 0
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

    if (!this.storeAddress || this.storeAddress.trim() === '' || this.storeAddress === 'null' || this.storeAddress === 'undefined') {
      this.addError('address', 'Debe ingresar dirección')
    }

    if (this.storeAltitude > 2147483647 || this.storeAltitude < -2147483648) {
      this.addError('altitude', 'El valor de altura es demasiado grande')
    }

    if (this.storeAltitude == 0) {
      this.addError('altitude', 'Debe ingresar altura válida')
    }

    if (this.storeLatitude == 0) {
      this.addError('latitude', 'Debe ingresar latitud válida')
    }

    if (this.storeLatitude < -90 || this.storeLatitude > 90) {
      this.addError('latitude', 'La latitud debe estar entre -90 y 90 grados')
    }

    if (this.storeLongitude == 0) {
      this.addError('longitude', 'Debe ingresar altura válida')
    }

    if (this.storeLongitude < -90 || this.storeLongitude > 90) {
      this.addError('latitude', 'La latitud debe estar entre -90 y 90 grados')
    }

    if (this.storeAuthorCommission == 0) {
      this.addError('authorcommission', 'Debe ingresar una comision')
    }

    if (this.storeAuthorCommission < 0 || this.storeAuthorCommission > 1){
      this.addError('authorcommission', 'La comision debe ser de 0% a 100%')
    }

    if (this.storeAppCommission == 0) {
      this.addError('appcommission', 'Debe ingresar una comision')
    }

    if (this.storeAppCommission < 0 || this.storeAppCommission > 1){
      this.addError('appcommission', 'La comision debe ser de 0% a 100%')
    }

    if (!this.storePaymentEfectivo && !this.storePaymentQR && !this.storePaymentTransferencia )
      this.addError('metodopago', 'Debe ingresar al menos un metodo de pago')
  }
}
