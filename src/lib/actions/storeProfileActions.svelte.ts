import { defaultData } from '$lib/data/mock/storeProfileDefaultData';
import { storeInfo, storeDir, storeCommission } from '$lib/data/mock/storeProfileNewData';
import type { FormData } from '$lib/type/storeProfileTypes';
import { ValidationMessage } from '$lib/domain/validationMessage';
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

  function validateForm(): { isValid: boolean; errors: ValidationMessage[] } {
    const errors: ValidationMessage[] = [];

    // Valida TODOS los campos de storeInfo
    storeInfo.fields.forEach(field => {
      if (field.required) {
        const value = formData.storeInfo[field.input_id];
        if (!value || value.trim() === '') {
          errors.push({
            field: field.input_id,
            message: `${field.label_text} es requerido`
          });
        } else if (field.minLength && value.trim().length < field.minLength) {
          errors.push({
            field: field.input_id,
            message: `${field.label_text} debe tener al menos ${field.minLength} caracteres`
          });
        }
      }
    });

    // Valida TODOS los campos de storeDir
    storeDir.fields.forEach(field => {
      if (field.required) {
        const value = formData.storeDir[field.input_id];
        if (!value || value.trim() === '') {
          errors.push({
            field: field.input_id,
            message: `${field.label_text} es requerido`
          });
        }
      }
    });

    // Valida TODOS los campos de storeCommission
    storeCommission.fields.forEach(field => {
      if (field.required) {
        const value = formData.storeCommission[field.input_id];
        
        // Validar que no esté vacío
        if (!value || value.trim() === '') {
          errors.push({
            field: field.input_id,
            message: `${field.label_text} es requerido`
          });
        } else if (field.type === 'number') {
          // Validación para numeros
          const numValue = parseFloat(value);
          if (isNaN(numValue)) {
            errors.push({
              field: field.input_id,
              message: `${field.label_text} debe ser un número válido`
            });
          } else if (numValue < 0 || numValue > 100) {
            errors.push({
              field: field.input_id,
              message: `${field.label_text} debe estar entre 0 y 100`
            });
          }
        }
      }
    });

    // valida que tenga al menos un swich encendido
    //ver por que no se esta mostrando el error de este field
    const hasPaymentMethod = Object.keys(formData.paymentMethods).some(key => formData.paymentMethods[key]);
    
    if (!hasPaymentMethod) {
      errors.push({
        field: 'paymentMethods',
        message: 'Selecciona al menos un método de pago'
      });
    }

    return { isValid: errors.length === 0, errors };
  }

  // valida cada field
  function validateField(section: string, fieldId: string): void {
    const allFields = [...storeInfo.fields, ...storeDir.fields, ...storeCommission.fields];
    const fieldConfig = allFields.find(field => field.input_id === fieldId);
    
    if (!fieldConfig) return;

    const formSection = section as keyof FormData;
    const value = formData[formSection][fieldId];
    
  }



  return {
    get formData() { return formData },
    get originalData() { return originalData },
    saveData,
    discardChanges,
    resetToDefault,
    validateField,
    validateForm
  }
}


export const formStore = createFormStore()



