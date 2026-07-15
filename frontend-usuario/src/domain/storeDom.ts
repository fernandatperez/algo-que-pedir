import { StoreRate, StoreRateJSON } from './storeRate'

export type StoreDomJSON = {
  id: number,
  name: string,
  storeURL: string,
  deliveryTimeAvg: string,
  deliveryFee: number,
  numberOfOrders: number,
  paymentTypes: PaymentType[],
  reviews: StoreRateJSON[],
  userDistance: number
}

export type StoreReviewsJSON = {
  rate: number,
  experienceDesc: string
}

export enum PaymentType {
  EFECTIVO = 'EFECTIVO',
  QR = 'QR',
  TRANSFERENCIA_BANCARIA = 'TRANSFERENCIA_BANCARIA',
}

export class Store {
    id: number
    name: string
    storeURL: string
    reviews: StoreRate[]
    deliveryFee: number
    paymentTypes: PaymentType[]
    numberOfOrders: number
    deliveryTimeAvg: number
    userDistance: number

    constructor(
        id: number = -1,
        name: string = '',
        storeURL: string = '',
        reviews: StoreRate[] = [],
        deliveryFee: number = 0,
        paymentTypes: PaymentType[] = [PaymentType.EFECTIVO],
        numberOfOrders = 0,
        deliveryTimeAvg = 0,
        userDistance: number = 0
    ) {
        this.id = id
        this.name = name
        this.storeURL = storeURL
        this.reviews = reviews
        this.deliveryFee = deliveryFee
        this.paymentTypes = paymentTypes
        this.numberOfOrders = numberOfOrders
        this.deliveryTimeAvg = deliveryTimeAvg
        this.userDistance = userDistance
    }

    get gradePointAvg(): string {
        return (this.reviews.reduce((acc, rev) => acc + rev.rate, 0) / this.reviews.length).toFixed(1)
    }

    get numberOfReviews(): string {
        if(this.reviews.length >= 10) {
          return '10+'
        } else {
          return `${this.reviews.length}`
        }
    }

    get isExpensive(): boolean {
        return this.deliveryFee >= 5
    }

    offersFreeDelivery(): boolean {
      return this.deliveryFee != 0
    }

    static fromJSON(storeJSON: StoreDomJSON) : Store {
      const reviews = storeJSON.reviews.map(it => StoreRate.fromJSON(it))
      const store = Object.assign(new Store(), storeJSON, {})
      store.reviews = reviews
      return store
    }

    // get kmsToUser(userPoint: number) {
    //     return userPoint - ownPoint
    // }
}