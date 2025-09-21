export interface MenuItemType { // se le pone Type como sufijo para mostrar que es un tipo de dato
  id: number;
  alt: string;
  nombre: string;
  descripcion: string;
  precio: string;
  imagen: string;
}

// factory function, me devuelve un objeto de tipo MenuItemType todo vacio
export function createEmptyMenuItem(): MenuItemType { // el MenuItemType al final significa que devuelve ese tipo de dato 
  return {
    id: 0,
    alt: '',
    nombre: '',
    descripcion: '',
    precio: '',
    imagen: ''
  };
}
