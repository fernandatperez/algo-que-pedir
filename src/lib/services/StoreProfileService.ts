import { defaultData } from '$lib/data/mock/storeProfileDefaultData'
import { storeInfo, storeDir, storeCommission, paymentMethods } from '$lib/data/mock/storeProfileNewData'
import type { FormData } from '$lib/type/storeProfileTypes'
import { ValidationMessage } from '$lib/domain/store'

export class StoreProfileService {
  private originalData: FormData = { ...defaultData }
  private currentData: FormData = { ...defaultData }
  private errors: ValidationMessage[] = []
  private isSubmitting: boolean = false

  // GET: Obtener datos actuales del formulario
  get(): FormData {
    return { ...this.currentData }
  }

  // GET ALL: Obtener todos los datos (originales)
  getAll(): FormData {
    return { ...this.originalData }
  }

  // UPDATE: Actualizar datos parcialmente
  update(data: Partial<FormData>): void {
    if (data.storeInfo) {
      this.currentData.storeInfo = { ...this.currentData.storeInfo, ...data.storeInfo }
    }
    if (data.storeDir) {
      this.currentData.storeDir = { ...this.currentData.storeDir, ...data.storeDir }
    }
    if (data.storeCommission) {
      this.currentData.storeCommission = { ...this.currentData.storeCommission, ...data.storeCommission }
    }
    if (data.paymentMethods) {
      this.currentData.paymentMethods = { ...this.currentData.paymentMethods, ...data.paymentMethods }
    }
  }

  // DELETE: Resetear a valores por defecto
  delete(): void {
    this.originalData = { ...defaultData }
    this.currentData = { ...defaultData }
    this.errors = []
  }

  // POST/PUT: Guardar cambios (equivalente a save)
  async save(): Promise<boolean> {
    this.isSubmitting = true
    
    try {
      // Validar solo al submit
      const validation = this.validateForm(this.currentData)
      this.errors = validation.errors
      
      if (!validation.isValid) {
        return false
      }

      // Guardar datos
      this.originalData = { ...this.currentData }
      
      // Aquí iría la llamada al backend
      // await this.saveToBackend(this.currentData)
      
      this.errors = []
      return true
    } finally {
      this.isSubmitting = false
    }
  }

  // Métodos auxiliares específicos del formulario
  discardChanges(): void {
    this.currentData = { ...this.originalData }
    this.errors = []
  }

  resetToDefault(): void {
    this.delete() // Reutiliza delete
  }

  // Getters específicos (pueden mantenerse como están)
  get formData(): FormData {
    return this.get()
  }

  get originalFormData(): FormData {
    return this.getAll()
  }

  get validationErrors(): ValidationMessage[] {
    return [...this.errors]
  }

  get submitting(): boolean {
    return this.isSubmitting
  }

  // Validaciones
  validateForm(formData: FormData): { isValid: boolean; errors: ValidationMessage[] } {
    const errors: ValidationMessage[] = []

    // Validar storeInfo 
    storeInfo.fields.forEach(fieldConfig => {
      if (fieldConfig.required) {
        const value = formData.storeInfo[fieldConfig.field] 
        if (!value || value.trim() === '') {
          errors.push({
            field: fieldConfig.field, 
            message: `${fieldConfig.label} es requerido` 
          })
        } else if (fieldConfig.minLength && value.trim().length < fieldConfig.minLength) {
          errors.push({
            field: fieldConfig.field,
            message: `${fieldConfig.label} debe tener al menos ${fieldConfig.minLength} caracteres`
          })
        }
      }
    })

    // Validar storeDir 
    storeDir.fields.forEach(fieldConfig => {
      if (fieldConfig.required) {
        const value = formData.storeDir[fieldConfig.field] 
        if (!value || value === '') {
          errors.push({
            field: fieldConfig.field, 
            message: `${fieldConfig.label} es requerido` 
          })
        } 
      }
    })

    // Validar storeCommission 
    storeCommission.fields.forEach(fieldConfig => {
      if (fieldConfig.required) {
        const value = formData.storeCommission[fieldConfig.field] 
        
        if (!value || value.trim() === '') {
          errors.push({
            field: fieldConfig.field, 
            message: `${fieldConfig.label} es requerido` 
          })
        } else if (fieldConfig.type === 'number') {
          const numValue = parseFloat(value)
          if (isNaN(numValue)) {
            errors.push({
              field: fieldConfig.field,
              message: `${fieldConfig.label} debe ser un número válido`
            })
          } else if (numValue < 0 || numValue > 100) {
            errors.push({
              field: fieldConfig.field,
              message: `${fieldConfig.label} debe estar entre 0 y 100`
            })
          }
        }
      }
    })

    // Validar paymentMethods 
    const hasPaymentMethod = paymentMethods.some(method => 
      formData.paymentMethods[method.field] === true 
    )
    
    if (!hasPaymentMethod) {
      errors.push({
        field: 'paymentMethods',
        message: 'Selecciona al menos un método de pago'
      })
    }

    return { isValid: errors.length === 0, errors }
  }

  // Validar campo individual
  validateField(formData: FormData, section: string, fieldId: string): ValidationMessage | null {
    const validSections: (keyof FormData)[] = ['storeInfo', 'storeDir', 'storeCommission', 'paymentMethods']
  
    if (!validSections.includes(section as keyof FormData)) {
      return null
    }

    const allFields = [...storeInfo.fields, ...storeDir.fields, ...storeCommission.fields]
    const fieldConfig = allFields.find(field => field.field === fieldId)
  
    if (!fieldConfig || !fieldConfig.required) return null

    const value = formData[section as keyof FormData][fieldId]

    if (!value || (typeof value === 'string' && value.trim() === '')) {
      return {
        field: fieldId,
        message: `${fieldConfig.label} es requerido`
      }
    }

    if (fieldConfig.type === 'number' && typeof value === 'string') {
      const numValue = parseFloat(value)
      if (isNaN(numValue)) {
        return {
          field: fieldId,
          message: `${fieldConfig.label} debe ser un número válido`
        }
      }
    }

    return null
  }

  // Para uso futuro con backend
  private async saveToBackend(formData: FormData): Promise<void> {
    // await fetch('/api/store-profile', {
    //   method: 'PUT',
    //   body: JSON.stringify(formData)
    // })
  }
}

// Instancia única
export const storeProfileService = new StoreProfileService()