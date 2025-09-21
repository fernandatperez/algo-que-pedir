<!-- no entiendo que me esta tomando como 'legacy', no encuentro nada deprecado -->
<script lang="ts">
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/number-input.css";
  import "$lib/css/components-css/grid-table.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/9-store-profile.css";
  import Input from "$lib/Input.svelte";
  import FieldsetGroup from "$lib/FieldsetGroup.svelte";
  import {
    storeInfo,
    storeDir,
    storeCommission,
    paymentMethods,
  } from "$lib/data/formData";
</script>

<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title">Información del local</h1>

    <form id="form-store-profile" class="container-column form-store-profile">
      <!-- Datos del Local -->
      <div class="grid-cols-2 input-group-dir">
        <FieldsetGroup {...storeInfo} />
        <div class="img-store-container">
          <img
            src="/src/lib/assets/img/CarlosBakeShop.jpg"
            alt="local"
            class="img-store-profile"
          />
        </div>
      </div>

      <!-- Dirección -->
      <FieldsetGroup {...storeDir} />

      <!-- Porcentajes -->
      <FieldsetGroup {...storeCommission} />

      <!-- Métodos de pago -->
      <fieldset
        name="store-payment-methods"
        class="container-column content-section"
      >
        <h2 class="subtitle">Métodos de Pago</h2>
        <div class="payments-checkbox-group">
          {#each paymentMethods as method(method.id)}
            <label for={method.id} class="label-color">
              <input
                type="checkbox"
                id={method.id}
                name={method.id}
                value={method.value}
                class="payment-checkbox"
                checked={method.checked}
              />
              <span>{method.label}</span>
            </label>
          {/each}
        </div>
      </fieldset>

      <!-- Botones -->
      <section class="btn-group-actions">
        <button disabled class="btn btn-secondary btn-store">
          Descartar <span class="p-cambios display-none-mobile">Cambios</span>
        </button>
        <button disabled class="btn btn-primary btn-store">
          Guardar <span class="p-cambios display-none-mobile">Cambios</span>
        </button>
      </section>
    </form>
  </article>
</main>
