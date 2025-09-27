import type { MenuItemType } from '$lib/type/menu-item'
// A MENU-ITEM LE FALTA: plato.cantidad

export enum Estado {
  PENDIENTE= 'PENDIENTE',
  PREPARADO= 'PREPARADO',
  ENTREGADO= 'ENTREGADO',
  CANCELADO= 'CANCELADO',
}

export enum Pago {
  EFECTIVO = 'EFECTIVO',
  TRANSFERENCIA = 'TRANSFERENCIA',
  QR = 'QR',
}

export type OrderJSON = {
  id: number
  name: string
  user: string
  address: string
  lat: string
  long: string
  dishes: MenuItemType[] // Lista de Platos
  deliveryComission: number
  paymentMethod: Pago
  state: Estado
  createdAt: string
}

export class Order {
  constructor(
    public id: number = -1, // no existe
    public name: string = '',
    public user: string = '',
    public address: string = '',
    public lat: string = '',
    public long: string = '',
    public dishes: MenuItemType[] = [], // Lista de Platos
    public deliveryComission: number = 0.0,
    public paymentMethod: Pago = Pago.EFECTIVO,
    public state: Estado = Estado.PENDIENTE,
    public createdAt: Date = new Date(),
  ) {}

  // transforma json del back a una Order de ts
  static fromJSON(orderJSON: OrderJSON): Order {
    return Object.assign(new Order(), orderJSON, {
      createdAt: Date.parse(orderJSON.createdAt) // parseo string a Date
    })
  }

  // Suma de Precios de Platos
  precioSubtotal(): number { 
    return this.dishes.reduce((accumulator, currentItem) => {
      return accumulator + currentItem.precio
    }, 0)
  }
  
  // Recargo del 10%, solo cuando no es EFVO
  recargoPago(): number { 
    if (this.paymentMethod == 'EFECTIVO') {
      return 1
    } else {
      return 1.1
    }
  }

  recargoTipoDePago() : number {
    let recargo: number 
    if (this.recargoPago() == 1) {
      recargo = 0
    } else {
      recargo = this.precioSubtotal() * 0.1
    }
    return recargo 
  }

  precioTotal(): number { 
    return this.precioSubtotal() * this.recargoPago()  + this.deliveryComission  
  }

  get fechaEntregaToString(): string {
    return this.createdAt.toLocaleString()
  }

  toJSON(): OrderJSON {
    return {
      id: this.id,
      name: this.name,
      user: this.user,
      address: this.address,
      lat: this.lat,
      long: this.long,
      dishes: this.dishes, // Lista de Platos
      deliveryComission: this.deliveryComission,
      paymentMethod: this.paymentMethod,
      state: this.state,
      createdAt: this.fechaEntregaToString,
    }
  }
}
