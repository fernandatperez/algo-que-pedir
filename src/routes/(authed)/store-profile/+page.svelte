<!-- no entiendo que me esta tomando como 'legacy', no encuentro nada deprecado -->
<script lang="ts">
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/number-input.css";
  import "$lib/css/components-css/grid-table.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/9-store-profile.css";

  import { formStore } from "$lib/actions/storeProfileActions.svelte";
  import {
    storeInfo,
    storeDir,
    storeCommission,
    paymentMethods,
  } from "$lib/data/storeProfileNewData";

  // Acceder a los datos originales de la tienda
  const { formData, originalData, saveData, discardChanges } = formStore;
</script>

<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title">Información del local</h1>

    <form id="form-store-profile" class="container-column form-store-profile">
      <!-- Datos del Local -->        
      <fieldset name={storeInfo.name} class="container-column content-section form-section-store-commission">
        <h2 class="subtitle">{storeInfo.title}</h2>
        <div class="grid-cols-2 input-group-dir">
          <div>
          {#each storeInfo.fields as field (field.input_id)}
            <div class="input-field ">
              <label for={field.input_id} class="label-color"
                >{field.label_text}</label
              >
              <input
                type="text"
                id={field.input_id}
                name={field.input_id}
                placeholder={field.input_placeholder}
                class="input"
                bind:value={formData.storeInfo[field.input_id]}
              />
            </div>
          {/each}
          </div>
          <div>
          <img
            src={formData.storeInfo["url-store-img"] ||
              "/src/lib/assets/img/CarlosBakeShop.jpg"}
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
              <label for={field.input_id} class="label-color"
                >{field.label_text}</label
              >
              <input
                type="text"
                id={field.input_id}
                name={field.input_id}
                placeholder={field.input_placeholder}
                class="input"
                bind:value={formData.storeDir[field.input_id]}
              />
            </div>
          {/each}
        </div>
      </fieldset>

      <!-- Porcentajes -->
      <fieldset
        name={storeCommission.name}
        class="container-column content-section form-section-store-commission"
      >
        <h2 class="subtitle">{storeCommission.title}</h2>
        <div class="grid-cols-2 input-group-dir">
          {#each storeCommission.fields as field (field.input_id)}
            <div class="input-field">
              <label for={field.input_id} class="label-color"
                >{field.label_text}</label
              >
              <input
                type="number"
                id={field.input_id}
                name={field.input_id}
                placeholder={field.input_placeholder}
                class="input"
                bind:value={formData.storeCommission[field.input_id]}
              />
            </div>
          {/each}
        </div>
      </fieldset>

      <!-- Métodos de pago -->
      <fieldset
        name="store-payment-methods"
        class="container-column content-section"
      >
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
              />
              <span>{method.label}</span>
            </label>
          {/each}
        </div>
      </fieldset>

      <!-- muestro en pantalla como toma con el bind los datos de input -->
      <fieldset class="container-column content-section">
        <h2 class="subtitle">Datos del Formulario (JSON)</h2>
        <pre class="json-preview">{JSON.stringify(formData, null, 2)}</pre>
      </fieldset>

      <!-- muestro datos originales (JSON guardado) -->
      <fieldset class="container-column content-section">
        <h2 class="subtitle">Datos Guardados (JSON Original)</h2>
        <pre class="json-preview">{JSON.stringify(originalData, null, 2)}</pre>
      </fieldset>

      <!-- Botones -->
      <section class="btn-group-actions">
        <button
          type="button"
          class="btn btn-secondary btn-store"
          on:click={discardChanges}
        >
          Descartar <span class="p-cambios display-none-mobile">Cambios</span>
        </button>
        <button
          type="button"
          class="btn btn-primary btn-store"
          on:click={saveData}
        >
          Guardar <span class="p-cambios display-none-mobile">Cambios</span>
        </button>
      </section>
    </form>
  </article>
</main>
