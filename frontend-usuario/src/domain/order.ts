import { es } from 'date-fns/locale'
import type { MenuItemType } from '../domain/menuItem'
import { StoreType } from './store'
import { format } from 'date-fns'
import { PaymentType, Store } from './storeDom'
// A MENU-ITEM LE FALTA: plato.cantidad

export enum Estado {
  PENDIENTE = 'PENDIENTE',
  PREPARADO = 'PREPARADO',
  ENTREGADO = 'ENTREGADO',
  CANCELADO = 'CANCELADO',
}

export type OrderForBack = {
  userID: number,
  localID: number,
  platosIDs: number[],
  medioDePago: string,
  estado: Estado,
  subtotal: number
}

export type ReducedOrderJSON = {
  id: number,
  store: StoreType
  totalPrice: number,
  date: string,
  articlesAmount: number,
  state: string
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
  deliveryComission: number
  metodoDePago: String
  estado: Estado
  horarioEntrega: string
  fechaCreacion: Date
  local: Store
  
  precioSubtotal: number
  serviceFee: number
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
    public deliveryComission: number = 0.0,
    public metodoDePago: PaymentType = PaymentType.EFECTIVO,
    public estado: Estado = Estado.PENDIENTE,
    public horarioEntrega: string = '',
    public fechaCreacion: Date = new Date(),
    public local: Store = new Store(),
    
    public precioSubtotal: number = 0.0,
    public serviceFee: number = 0.0,
    public deliveryFee: number = 0.0,
  ) {}

  // transforma json del back a una Order de ts
  static fromJSON(orderJSON: OrderJSON): Order {
    const order = Object.assign(new Order(), orderJSON, {})
    const local = Object.assign(new Store(), orderJSON.local, {})
    order.local = local
    return order
  }

  // Suma de Precios de Platos
  // precioSubtotal(): number { 
  //   return this.platos.reduce((accumulator, currentItem) => {
  //     return accumulator + currentItem.precio
  //   }, 0)
  // }
  
  aCobrarPorPedido(): number {
    if (this.metodoDePago == 'EFECTIVO') {
      return 0
    } else {
      return this.precioSubtotal * 0.1
    }
  }
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
    return this.precioSubtotal + this.deliveryFee + this.serviceFee 
  }

  get horarioEntregaString(): string {
    // return this.horarioEntrega.toLocaleString()
    return this.horarioEntrega.toString()
  }

  get fechaCreacionString(): string {
    return format(this.fechaCreacion, 'dd \'de\' MMM', { locale: es })
  }

  aparicionesDe(idPlato: number): number {
    return this.platos.filter(it => it.id == idPlato).length
  }

  platosSinRepetir(): MenuItemType[] {
    const map = new Map<number, MenuItemType>()

    for (const plato of this.platos) {
      map.set(plato.id, plato)
    }

    return [...map.values()]
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
      fechaCreacion: this.fechaCreacion,
      local: this.local,
      serviceFee: this.serviceFee,
      deliveryFee: this.deliveryFee
    }
  }
}
