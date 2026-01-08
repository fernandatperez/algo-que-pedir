import type { MenuItemType } from '$lib/domain/menuItem'
// A MENU-ITEM LE FALTA: plato.cantidad

export enum Estado {
  // PENDIENTE= 'PENDIENTE',
  CONFIRMADO= 'CONFIRMADO',
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
  nombre: string
  username: string
  direccion: string
  altura: string
  direccionEntera: string
  lat: string
  long: string
  platos: MenuItemType[] // Lista de Platos
  precioSubtotal: number
  deliveryComission: number
  metodoDePago: Pago
  estado: Estado
  horarioEntrega: string
  deliveryFee: number
}

export class Order {
  constructor(
    public id: number = -1, // no existe
    public nombre: string = '',
    public username: string = '',
    public direccion: string = '',
    public altura: string = '',
    public direccionEntera: string = '',
    public lat: string = '',
    public long: string = '',
    public platos: MenuItemType[] = [], // Lista de Platos
    public precioSubtotal: number = 0.0,
    public deliveryComission: number = 0.0,
    public metodoDePago: Pago = Pago.EFECTIVO,
    public estado: Estado = Estado.CONFIRMADO,
    public horarioEntrega: string = '',
    public deliveryFee: number = 0.0
  ) {}

  // transforma json del back a una Order de ts
  static fromJSON(orderJSON: OrderJSON): Order {
    return Object.assign(new Order(), orderJSON)
  }

  // Suma de Precios de Platos
  // precioSubtotal(): number { 
  //   return this.platos.reduce((accumulator, currentItem) => {
  //     return accumulator + currentItem.precio
  //   }, 0)
  // }
  
  // Recargo del 10%, solo cuando no es EFVO
  recargoPago(): number { 
    if (this.metodoDePago == 'EFECTIVO') {
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
      recargo = this.precioSubtotal * 0.1
    }
    return recargo 
  }

  precioTotal(): number { 
    return this.precioSubtotal * this.recargoPago()  + this.deliveryFee  
  }

  get horarioEntregaString(): string {
    // return this.horarioEntrega.toLocaleString()
    return this.horarioEntrega.toString()
  }

  toJSON(): OrderJSON {
    return {
      id: this.id,
      nombre: this.nombre,
      username: this.username,
      direccion: this.direccion,
      altura: this.altura,
      direccionEntera: this.direccionEntera,
      lat: this.lat,
      long: this.long,
      platos: this.platos, // Lista de Platos
      precioSubtotal: this.precioSubtotal,
      deliveryComission: this.deliveryComission,
      metodoDePago: this.metodoDePago,
      estado: this.estado,
      horarioEntrega: this.horarioEntrega,
      deliveryFee: this.deliveryFee
    }
  }
}
