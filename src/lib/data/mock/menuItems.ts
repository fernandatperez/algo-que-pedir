import { MenuItemType, type MenuItemJSON } from '$lib/domain/menuItem'

// Datos JSON "planos" que simularían venir del servidor
export const MENUITEMS_JSON_MOCK: MenuItemJSON[] = [
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

// Instancias individuales usando la clase
const hamburguesaJSON: MenuItemJSON = {
  id: 12,
  alt: 'Hamburguesa con queso',
  nombre: 'Hamburguesa con queso',
  descripcion: 'Hamburguesa con queso acompañada de papas fritas y bebida',
  precio: 12.0,
  imagen: 'hamburguesa.png',
}

const pizzaJSON: MenuItemJSON = {
  id: 13,
  alt: 'Pizza Margarita',
  nombre: 'Pizza Margarita',
  descripcion: 'De muzzarella con tomate y albahaca',
  precio: 16.0,
  imagen: 'pizza.png',
}

const ensaladaJSON: MenuItemJSON = {
  id: 14,
  alt: 'Ensalada clásica',
  nombre: 'Ensalada clásica',
  descripcion: 'De hojas frescas y vegetales organicos',
  precio: 10.0,
  imagen: 'ensalada.png',
}


// Array de instancias de MenuItemType asi tienen los metodos (sirve para despues los tests)
export const MENUITEMS_MOCK: MenuItemType[] = MENUITEMS_JSON_MOCK.map(jsonItem => 
  MenuItemType.fromJson(jsonItem)
)
export const hamburguesa = MenuItemType.fromJson(hamburguesaJSON)
export const pizza = MenuItemType.fromJson(pizzaJSON)
export const ensalada = MenuItemType.fromJson(ensaladaJSON)
