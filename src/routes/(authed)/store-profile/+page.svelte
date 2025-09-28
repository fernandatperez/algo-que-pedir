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
  
  // Service - ✅ Importar la instancia
  import { storeProfileService } from '$lib/services/StoreProfileService'
  import type { ValidationMessage } from '$lib/domain/store';
  
  // Data
  import { storeInfo, storeDir, storeCommission, paymentMethods } from "$lib/data/mock/storeProfileNewData";

  // Estados locales
  let formData = $state({ ...storeProfileService.formData })
  let errors = $state<ValidationMessage[]>([])
  let isSubmitting = $state(false)

  // Temporalmente en handleSubmit - para testear
function fillWithValidData() {
  formData = {
    storeInfo: {
      'store-name': 'Mi Tienda de Prueba',
      'url-store-img': 'https://ejemplo.com/imagen.jpg'
    },
    storeDir: {
      'store-dir': 'Calle Falsa 123',
      'store-altitude': '100',
      'store-latitude': '-34', 
      'store-longitude': '-58'
    },
    storeCommission: {
      'app-commission': '15',
      'author-commission': '10'
    },
    paymentMethods: {
      efectivo: true,
      qr: false,
      transferencia: true
    }
  }
}

// Llama esta función temporalmente para testear
// fillWithValidData()

  async function handleSubmit(event: Event) {
    event.preventDefault();
    isSubmitting = true
    fillWithValidData()
    try {
      // Usar el service para validar
      const validation = storeProfileService.validateForm(formData)
      errors = validation.errors
      
      if (!validation.isValid) {
        alert('Por favor corrige los errores antes de guardar')
        return
      }

      // Actualizar el service con los datos actuales
      storeProfileService.updateForm(formData)
      
      // Guardar usando el service
      const success = await storeProfileService.saveData()
      
      if (success) {
        errors = []
        alert('Datos guardados correctamente')
      }
    } catch (error) {
      alert('Error al guardar los datos')
    } finally {
      isSubmitting = false
    }
  }

  function handleDiscard() {
    // ✅ Usar el service para descartar cambios
    storeProfileService.discardChanges()
    
    // Actualizar el estado local con los datos del service
    formData = { ...storeProfileService.formData }
    errors = []
  }
</script>

<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title">Información del local</h1>

    <form id="form-store-profile" class="container-column form-store-profile" onsubmit={handleSubmit}>
      
      <!-- Store Info -->
      <div class="grid-cols-2 input-group-dir">
        <div>
          <FormFieldset
            title={storeInfo.title}
            name={storeInfo.name}
            fields={storeInfo.fields}
            bind:formData
            section="storeInfo"
            {errors}
          />
        </div>
        <div>
          <img
            src={formData.storeInfo["url-store-img"] || "/src/lib/assets/img/CarlosBakeShop.jpg"}
            alt="local"
            class="img-store-profile"
          />
        </div>
      </div>

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
        layout="grid-cols-1"
      />
      
      <!-- Buttons -->
      <section class="btn-group-actions">
        <button 
          type="button" 
          class="btn btn-secondary btn-store" 
          onclick={handleDiscard}
          disabled={isSubmitting}
        >
          Descartar <span class="p-cambios display-none-mobile">Cambios</span>
        </button>
        <button 
          type="submit" 
          class="btn btn-primary btn-store" 
          disabled={isSubmitting}
        >
          {#if isSubmitting}
            Guardando...
          {:else}
            Guardar <span class="p-cambios display-none-mobile">Cambios</span>
          {/if}
        </button>
      </section>
    </form>
  </article>
</main>