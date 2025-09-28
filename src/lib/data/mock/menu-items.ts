import type { MenuItemType } from '$lib/type/menu-item'

//array de elementeos de el tipo interface MenuItemType 
// esto tiene que ser objetos individuales y no una lista
export const MENUITEMS_MOCK: MenuItemType[] = [
  {
    id: 1,
    nombre: 'Pasta con albóndigas',
    descripcion: 'Deliciosa pasta con salsa de tomates y albondigas de cerdo',
    precio: 12.99,
    imagen: 'spagettis.png',
    alt: 'spagettis'
  },
  {
    id: 2,
    nombre: 'Hamburguesa con queso y panceta',
    descripcion: 'Combo de hamburguesa con papas y bebida',
    precio: 9.99,
    imagen: 'hamburguesa2.jpg',
    alt: 'hamburguesa'
  },
  {
    id: 3,
    nombre: 'Ensalada de la Huerta',
    descripcion: 'Ensalada fresca con hojas mixtas y vinagreta',
    precio: 7.50,
    imagen: 'ensalada.png',
    alt: 'ensalada'
  },
  {
    id: 4,
    nombre: 'Pizza con mozzarella y tomate',
    descripcion: 'Pizza a la piedra con salsa de tomates frescos y extra queso',
    precio: 11.75,
    imagen: 'pizza.png',
    alt: 'pizza'
  },
  {
    id: 5,
    nombre: 'Salmon con vegetales grillados',
    descripcion: 'Salmon fresco a la plancha acompañado de vegetales',
    precio: 14.25,
    imagen: 'salmon.png',
    alt: 'salmon'
  }
]

// export const MOCK ingredientJASON


// A MENU-ITEM LE FALTA: plato.cantidad

const hamburguesa : MenuItemType = {
  id: 12,
  alt: 'Hamburguesa con queso',
  nombre: 'Hamburguesa con queso',
  descripcion: 'Hamburguesa con queso acompañada de papas fritas y bebida',
  // cantidad: 1,
  precio: 12.0,
  imagen: '../src/lib/assets/img/hamburguesa.png',
}

const pizza : MenuItemType = {
  id: 12,
  alt: 'Pizza Margarita',
  nombre: 'Pizza Margarita',
  descripcion: 'De muzzarella con tomate y albahaca',
  // cantidad: 1,
  precio: 16.0,
  imagen: '../src/lib/assets/img/pizza.png',
}

const ensalada : MenuItemType = {
  id: 12,
  alt: 'Ensalada clásica',
  nombre: 'Ensalada clásica',
  descripcion: 'De hojas frescas y vegetales organicos',
  // cantidad: 1,
  precio: 10.0,
  imagen: '../src/lib/assets/img/ensalada.png',
}

export { hamburguesa, pizza, ensalada }