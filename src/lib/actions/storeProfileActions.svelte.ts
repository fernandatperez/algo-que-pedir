import { defaultData } from '$lib/data/storeProfileDefaultData'
import type { FormData } from '../../types/storeProfileTypes'
// los types los esta tomando mal por la ruta que tenemos definida en tsconfig.json

// aca crea los datos reactivos
//algunas notas para no olvidarme:
//originalData --> datos que ya tengo por defecto de la tienda en mi JSON
//formData --> si ingreso algo en el input se va a guardar aca, aunque no aprete guardar cambios
// si presiono save data, ahhi si se guarda lo de formdata pisando originaData

//aca al principio, ambos tienen la misma info
function createFormStore() {
  let originalData = $state<FormData>({ ...defaultData })

  let formData = $state<FormData>({
    storeInfo: { ...originalData.storeInfo },
    storeDir: { ...originalData.storeDir },
    storeCommission: { ...originalData.storeCommission },
    paymentMethods: { ...originalData.paymentMethods }
  })

  //aca piso los datos original con los recibidos del form
  function saveData(): void {
    originalData.storeInfo = { ...formData.storeInfo }
    originalData.storeDir = { ...formData.storeDir }
    originalData.storeCommission = { ...formData.storeCommission }
    originalData.paymentMethods = { ...formData.paymentMethods }
    alert("Cambios guardados correctamente") 
    // Aqca se deberia salvar al backend cuando lo pongamos
  }

  //esta funcion se dispara cuando descartamos los cambios, pisando los datos que muestra el  
  //formulario con los datos originales
  function discardChanges(): void {
    formData.storeInfo = { ...originalData.storeInfo }
    formData.storeDir = { ...originalData.storeDir }
    formData.storeCommission = { ...originalData.storeCommission }
    formData.paymentMethods = { ...originalData.paymentMethods }
    alert("Cambios descartados")
  }

  //aca piso formdata (input modificados sin guardar con los datos originales)
  function resetToDefault(): void {
    originalData = { ...defaultData }
    formData = { ...defaultData }
  }


  return {
    get formData() { return formData },
    get originalData() { return originalData },
    saveData,
    discardChanges,
    resetToDefault
  }
}


export const formStore = createFormStore()



