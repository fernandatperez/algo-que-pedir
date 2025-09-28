<script lang="ts">
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/number-input.css";
  import "$lib/css/components-css/grid-table.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/9-store-profile.css";
  
  import ValidationField from '$lib/components/ValidationField.svelte';
  import { formStore } from "$lib/actions/storeProfileActions.svelte";
  import type { ValidationMessage } from '$lib/domain/store';

  import {
    storeInfo,
    storeDir,
    storeCommission,
    paymentMethods
  } from "$lib/data/mock/storeProfileNewData";

  // ✅ Obtener todas las funciones del store
  const { formData, originalData, saveData, discardChanges, validateForm, validateField } = formStore;

  // ✅ Variable para errores con tipo explícito
  let errors = $state<ValidationMessage[]>([]);
  //toda esta porqueria de aca en adelante la tengo que poner en otro lado por que se ve muy mal aca
  // funcion para validar en el momento
  function onInput(section: string, fieldId: string) {
    // Usar setTimeout para no validar en cada tecla
    setTimeout(() => {
      if (validateForm) {
        const result = validateForm();
        errors = result.errors;
      }
    }, 500);
  }
// valida onBlur (cuando hago click fuera del input)
  function onBlur(section: string, fieldId: string) {
    if (validateField) {
      validateField(section, fieldId);
      const result = validateForm();
      errors = result.errors;
    }
  }

  // maneja el envio del formulario
  async function handleSubmit(event: Event) {
    event.preventDefault();
    
    // Validar todo el formulario
    const validationResult = validateForm();
    errors = validationResult.errors;
    
    if (validationResult.isValid) {
      // Guardar datos
      await saveData();
      errors = []; // Limpiar errores después de guardar
      alert('Datos guardados correctamente');
    } else {
      alert('Por favor corrige los errores antes de guardar');
    }
  }

  // funcion para descartar cambios
  function handleDiscardChanges() {
    discardChanges();
    errors = []; // Limpiar errores también
  }
</script>

<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title">Información del local</h1>

    <!-- ✅ Cambiar a on:submit y quitar use:enhance -->
    <form id="form-store-profile" class="container-column form-store-profile" onsubmit={handleSubmit}>
      
      <!-- Datos del Local -->        
      <fieldset name={storeInfo.name} class="container-column content-section form-section-store-commission">
        <h2 class="subtitle">{storeInfo.title}</h2>
        <div class="grid-cols-2 input-group-dir">
          <div>
          <!-- estaria buenisimo componentizar el fieldset-->
          {#each storeInfo.fields as field (field.input_id)}
            <div class="input-field">
              <label for={field.input_id} class="label-color">
                {field.label_text}
              </label>
              <input
                type="text"
                id={field.input_id}
                name={field.input_id}
                placeholder={field.input_placeholder}
                class="input"
                bind:value={formData.storeInfo[field.input_id]}
                oninput={() => onInput('storeInfo', field.input_id)}
                onblur={() => onBlur('storeInfo', field.input_id)}
              />
              <!-- pongo las validaciones dentro del each para que ciclen en cada input_Id-->
              <ValidationField errors={errors} field={field.input_id} />
            </div>
          {/each}
          </div>
          <div>
          <img
            src={formData.storeInfo["url-store-img"] || "/src/lib/assets/img/CarlosBakeShop.jpg"}
            alt="local"
            class="img-store-profile"
          />
          </div>
        </div>
      </fieldset>

      <!-- Dirección -->
      <fieldset name={storeDir.name} class="container-column content-section form-section-store-commission">
        <h2 class="subtitle">{storeDir.title}</h2>
        <div class="grid-cols-2 input-group-dir">
          {#each storeDir.fields as field (field.input_id)}
            <div class="input-field">
              <label for={field.input_id} class="label-color">
                {field.label_text}
              </label>
              <input
                type="text"
                id={field.input_id}
                name={field.input_id}
                placeholder={field.input_placeholder}
                class="input"
                bind:value={formData.storeDir[field.input_id]}
                oninput={() => onInput('storeDir', field.input_id)}
                onblur={() => onBlur('storeDir', field.input_id)}
              />
              <ValidationField errors={errors} field={field.input_id} />
            </div>
          {/each}
        </div>
      </fieldset>

      <!-- Porcentajes -->
      <fieldset name={storeCommission.name} class="container-column content-section form-section-store-commission">
        <h2 class="subtitle">{storeCommission.title}</h2>
        <div class="grid-cols-2 input-group-dir">
          {#each storeCommission.fields as field (field.input_id)}
            <div class="input-field">
              <label for={field.input_id} class="label-color">
                {field.label_text}
              </label>
              <input
                type="number"
                id={field.input_id}
                name={field.input_id}
                placeholder={field.input_placeholder}
                class="input"
                bind:value={formData.storeCommission[field.input_id]}
                oninput={() => onInput('storeCommission', field.input_id)}
                onblur={() => onBlur('storeCommission', field.input_id)}
              />
              <ValidationField errors={errors} field={field.input_id} />
            </div>
          {/each}
        </div>
      </fieldset>

      <!-- Métodos de pago -->
      <fieldset name="store-payment-methods" class="container-column content-section">
        <h2 class="subtitle">Métodos de Pago</h2>
        <div class="payments-checkbox-group">
          {#each paymentMethods as method (method.id)}
            <label for={method.id} class="label-color">
              <input
                type="checkbox"
                id={method.id}
                name={method.id}
                value={method.value}
                class="payment-checkbox"
                bind:checked={formData.paymentMethods[method.id]}
                onchange={() => onInput('paymentMethods', method.id)}
              />
              <span>{method.label}</span>
            </label>
          {/each}
        </div>
      </fieldset>
      
      <!-- Botones -->
      <section class="btn-group-actions">
        <button
          type="button"
          class="btn btn-secondary btn-store"
          onclick={handleDiscardChanges}
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