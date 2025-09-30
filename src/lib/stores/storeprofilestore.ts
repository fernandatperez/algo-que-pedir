// src/lib/stores/storeProfileStore.ts
import { defaultData } from '$lib/data/mock/storeProfileDefaultData'
import { storeProfileService } from '$lib/services/StoreProfileService'
import type { FormData } from '$lib/type/storeProfileTypes'
import type { ValidationMessage } from '$lib/domain/validationMessage'

export function createStoreProfileStore() {
  let originalData = $state<FormData>({ ...defaultData })
  let formData = $state<FormData>({ ...defaultData })
  let errors = $state<ValidationMessage[]>([])
  let isSubmitting = $state(false)

  return {
    // Getters
    get data() { return formData },
    get originalData() { return originalData },
    get errors() { return errors },
    get isSubmitting() { return isSubmitting },
    
    // Actions
    updateForm(data: Partial<FormData>): void {
      if (data.storeInfo) {
        formData.storeInfo = { ...formData.storeInfo, ...data.storeInfo }
      }
      if (data.storeDir) {
        formData.storeDir = { ...formData.storeDir, ...data.storeDir }
      }
      if (data.storeCommission) {
        formData.storeCommission = { ...formData.storeCommission, ...data.storeCommission }
      }
      if (data.paymentMethods) {
        formData.paymentMethods = { ...formData.paymentMethods, ...data.paymentMethods }
      }
    },

    async saveData(): Promise<boolean> {
      isSubmitting = true
      
      try {
        // Validar solo al submit
        const validation = storeProfileService.validateForm(formData)
        errors = validation.errors
        
        if (!validation.isValid) {
          return false
        }

        // Guardar datos
        originalData.storeInfo = { ...formData.storeInfo }
        originalData.storeDir = { ...formData.storeDir }
        originalData.storeCommission = { ...formData.storeCommission }
        originalData.paymentMethods = { ...formData.paymentMethods }
        
        // Aquí iría la llamada al backend
        // await storeProfileService.saveToBackend(formData)
        
        errors = []
        return true
      } finally {
        isSubmitting = false
      }
    },

    discardChanges(): void {
      formData.storeInfo = { ...originalData.storeInfo }
      formData.storeDir = { ...originalData.storeDir }
      formData.storeCommission = { ...originalData.storeCommission }
      formData.paymentMethods = { ...originalData.paymentMethods }
      errors = []
    },

    resetToDefault(): void {
      originalData = { ...defaultData }
      formData = { ...defaultData }
      errors = []
    }
  }
}

export const storeProfileStore = createStoreProfileStore()