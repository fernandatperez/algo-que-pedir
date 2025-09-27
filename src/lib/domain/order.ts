import type { MenuItemType } from '$lib/domain/menu-item'
// A MENU-ITEM LE FALTA: plato.cantidad

export interface Order {
  id: number;
  nombreCliente: string;
  usuarioCliente: string;
  direccionCliente: string;
  latitudCliente: string;
  longitudCliente: string;
  platos: MenuItemType[]; // Lista de Platos
  precioSubtotal(): number;
  comisionDelivery: number;
  recargoPago(): number;
  precioTotal(): number; // Calculado (metodo)
  tipoPago: string;
  estado: string,
  
  // email?: string; // Optional property
  readonly createdAt: Date; // Read-only property
  // greet(): void; // Method signature
}
