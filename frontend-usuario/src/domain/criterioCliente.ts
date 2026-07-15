import { MenuItemType } from './menuItem'
import { UserProfile } from './userProfile'
import { Store } from './storeDom'

export interface CriterioCliente {
  puedePedir(plato: MenuItemType, usuario: UserProfile): boolean
  type: string
}

export const Vegano: CriterioCliente = {
  type: 'vegano',
  puedePedir(plato: MenuItemType): boolean {
    return plato.ingredientes.every(ing => !ing.esOrigenAnimal)
  }
}

export const Exquisito: CriterioCliente = {
  type: 'exquisito',
  puedePedir(plato: MenuItemType): boolean {
    return plato.esDeAutor
  }
}

export const Conservador: CriterioCliente = {
  type: 'conservador',
  puedePedir(plato: MenuItemType, usuario: UserProfile): boolean {
    const preferredIds = new Set(usuario.preferredIngredients.map(ing => ing.id))
    
    return plato.ingredientes.every(ing => ing.id !== undefined && preferredIds.has(ing.id))
  }
}

export const Impaciente: CriterioCliente = {
  type: 'impaciente',
  puedePedir(plato: MenuItemType, usuario: UserProfile): boolean {
    // Lógica Placeholder: Simularía si usuario.latitude/longitude es cercano al plato.local.latitud/longitud

    // TODO

    // Por simplicidad, asumimos que siempre es cercano para demostrar el concepto.
    return true 
  }
}

export const Generalista: CriterioCliente = {
  type: 'generalista',
  puedePedir(): boolean { return true }
}

export class Fieles implements CriterioCliente {
  public type = 'fieles'

  public localesFavoritos: Store[] = []

  constructor(locales: Store[] = []) {
    this.localesFavoritos = locales
  }

  agregarLocalFavorito(local: Store) {
    this.localesFavoritos.push(local)
  }

  puedePedir(plato: MenuItemType, usuario: UserProfile): boolean {
    // Ver si el local del plato ya está entre los favoritos
    // const yaEsFavorito = this.localesFavoritos.some(
    //   fav => fav.id === plato.local.id
    // )

    // // Si no estaba → lo agregamos
    // if (!yaEsFavorito) {
    //   this.localesFavoritos.push(plato.local)
    // }

    // Fieles siempre permite pedir
    return true
  }


}

export class Consumista implements CriterioCliente {
  public type = 'consumista'

  public frasesFavoritas: string[] = []

  constructor(frases: string[] = []) {
    this.frasesFavoritas = frases
  }

  agregarFraseFavorita(frase: string) {
    this.frasesFavoritas.push(frase.toLowerCase())
  }

  puedePedir(plato: MenuItemType): boolean {
    const descripcion = plato.descripcion.toLowerCase() // Descripción del plato
    
    // Retorna true si CUALQUIERA de las frases favoritas está contenida en la descripción.
    for (const frase of Array.from(this.frasesFavoritas)) {
      if (descripcion.includes(frase)) {
        return true
      }
    }
    return false
  }
}

export class Combinado implements CriterioCliente {
  public type = 'combinado'
  
  // criterios: MutableSet<CriterioCliente> en Kotlin -> CriterioCliente[] en TS
  constructor(public criterios: CriterioCliente[]) {

  }

  puedePedir(plato: MenuItemType, usuario: UserProfile): boolean {
    return this.criterios.every(criterio => criterio.puedePedir(plato, usuario))
  }
}

/**
 * CambianteSegunEdad: Aplica Exquisito si la edad es par, Conservador si es impar.
 * NOTA: UserProfile no tiene una propiedad de edad, se usa una función de placeholder.
 */
// export class CambianteSegunEdad implements CriterioCliente {
  
//   // Placeholder para simular usuario.edad()
//   private getEdadDelUsuario(usuario: UserProfile): number {
//     console.warn("CambianteSegunEdad: Se requiere una propiedad o método en UserProfile para obtener la edad.")
//     // Valor de ejemplo. En una aplicación real, se calcularía a partir de la fecha de nacimiento.
//     return 30
//   }
  
//   private criterioSegunEdad(usuario: UserProfile): CriterioCliente {
//     const edad = this.getEdadDelUsuario(usuario)
//     // Lógica del Kotlin: edad() % 2.0 == 0.0
//     return edad % 2 === 0 ? Exquisito : Conservador 
//   }

//   puedePedir(plato: MenuItemType, usuario: UserProfile): boolean {
//     // Delega la responsabilidad al criterio seleccionado (Exquisito o Conservador).
//     return this.criterioSegunEdad(usuario).puedePedir(plato, usuario)
//   }
// }