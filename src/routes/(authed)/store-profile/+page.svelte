<script lang="ts">
  // Styles
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/number-input.css";
  import "$lib/css/components-css/grid-table.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/9-store-profile.css";
  
  // Components
  import FormFieldset from '$lib/FormFieldset.svelte';
  import { toasts } from '$lib/components/toast/toastStore';
  
  // Service 
  import { storeProfileService } from '$lib/services/StoreProfileService'
  import type { ValidationMessage } from '$lib/domain/validationMessage';
  
  // Data
  import { storeInfo, storeDir, storeCommission, paymentMethods } from "$lib/data/mock/storeProfileNewData";

  // Estados locales
  let formData = $state({ ...storeProfileService.formData })
  let errors = $state<ValidationMessage[]>([])
  let toastLock: boolean = false

  async function handleSubmit(event: Event) {
    event.preventDefault();
    try {
      // Usa el service para validar
      const validation = storeProfileService.validateForm(formData)
      errors = validation.errors
      
      if (!validation.isValid) {
        if (!toastLock) {

            validation.errors.forEach(error => {
            toasts.push(error.message, {type: 'error'})
            toastLock = true
            setTimeout(releaseToast, 5000)
          })
        }
        alert('Por favor corrige los errores antes de guardar')
        return
      }

      const releaseToast = () => {toastLock = false  }
      // Actualiza con el service los datos actuales
      storeProfileService.update(formData)
      
      // Guarda usando el service
      const success = await storeProfileService.save()
      
      if (success) {
        errors = []
        alert('Datos guardados correctamente')
      }
    } catch (error) {
      alert('Error al guardar los datos')
    } 
  }

  function handleDiscard() {
    // usa el service para descartar cambios
    storeProfileService.discardChanges()
    
    // Actualizar el estado local con los datos del service
    formData = { ...storeProfileService.formData }
    errors = []
  }
</script>

<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title">Información del local</h1>

    <form id="form-store-profile" class="container-column form-store-profile" onsubmit={handleSubmit} novalidate>
      
      <!-- Store Info -->
      <FormFieldset
         title={storeInfo.title}
         name={storeInfo.name}
         fields={storeInfo.fields}
         bind:formData
         section="storeInfo"
         {errors}
       />
      
      
      <!-- Store Direction -->
      <FormFieldset
        title={storeDir.title}
        name={storeDir.name}
        fields={storeDir.fields}
        bind:formData
        section="storeDir"
        {errors}
      />

      <!-- Store Commission -->
      <FormFieldset
        title={storeCommission.title}
        name={storeCommission.name}
        fields={storeCommission.fields}
        bind:formData
        section="storeCommission"
        {errors}
      />

      <!-- Payment Methods -->
      
      <FormFieldset
        title="Métodos de Pago"
        name="payment-methods"
        fields={paymentMethods}
        bind:formData
        section="paymentMethods"
        {errors}
      />
      
      
      <!-- Buttons -->
      <section class="btn-group-actions">
        <button 
          type="button" 
          class="btn btn-secondary btn-store" 
          onclick={handleDiscard}
        >
          Descartar <span class="p-cambios display-none-mobile">Cambios</span>
        </button>
        <button 
          type="submit" 
          class="btn btn-primary btn-store"
        >
          Guardar <span class="p-cambios display-none-mobile">Cambios</span>
        </button>
      </section>
    </form>
  </article>
</main>