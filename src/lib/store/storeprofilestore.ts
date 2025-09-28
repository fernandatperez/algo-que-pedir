import type { FormData } from '$lib/type/storeProfileTypes'
import { defaultData } from '$lib/data/mock/storeProfileDefaultData'

class StoreProfileStore {
  private originalData = $state<FormData>({ ...defaultData })
  private data = $state<FormData>({ ...defaultData })

  get formData() {
    return this.data
  }

  get originalData() {
    return this.originalData
  }

  saveData(): void {
    this.originalData = { ...this.data }
    alert('Cambios guardados correctamente')
  }

  discardChanges(): void {
    this.data = { ...this.originalData }
    alert('Cambios descartados')
  }

  resetToDefault(): void {
    this.originalData = { ...defaultData }
    this.data = { ...defaultData }
  }
}

export const storeProfileStore = new StoreProfileStore()