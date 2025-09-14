<script>
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/number-input.css";
  import "$lib/css/components-css/grid-table.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/9-store-profile.css";
  import InputField from '$lib/InputField.svelte';
  import CheckboxField from '$lib/CheckboxField.svelte';
  import ButtonGroup from '$lib/BttnGroup.svelte';
  import SectionTitle from '$lib/SectionTitle.svelte';
</script>

<!-- Content -->
<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title">Información del local</h1>
    <form id="form-store-profile" class="container-column form-store-profile">

      <!-- Datos del Local -->
      <fieldset form="form-store-profile" name="store-info" class="content-section form-section-store-info>">
      
        <div class="grid-cols-2 input-group-dir">
          <div>
            <InputField
             label_for="store-name"
             label_text="Nombre del local*"
             input_type="text"
             input_placeholder="Carlo's Bake Shop"
             input_id="store-name"
            />
            <InputField
             label_for="url-store-img"
             label_text="URL de la imagen*"
             input_type="text"
             input_placeholder="http://images.google.com/CarlosBakeShop.jpg"
             input_id="url-store-img"
            />
          </div>  
      
        <div class="img-store-container">
          <img src="/src/lib/assets/img/CarlosBakeShop.jpg" alt="local" class="img-store-profile" />
        </div>

      </div>
      </fieldset>

      <!-- Dirección -->
      <fieldset name="store-dir" class="container-column content-section">
        <h2 class="subtitle">Dirección</h2>
        <div class="grid-cols-2 input-group-dir">
          <InputField
            label_for="store-dir"
            label_text="Dirección*"
            input_type="text"
            input_placeholder="Escribir"
            input_id="store-dir"
          />

          <InputField
            label_for="store-altitude"
            label_text="Altura*"
            input_type="number"
            input_placeholder="Escribir número..."
            input_id="store-altitude"
          />

          <InputField
            label_for="store-latitude"
            label_text="Latitud*"
            input_type="number"
            input_placeholder="Escribir número..."
            input_id="store-latitude"
          />

          <InputField
            label_for="store-longitude"
            label_text="Longitud*"
            input_type="number"
            input_placeholder="Escribir número..."
            input_id="store-longitude"
          />
        </div>
      </fieldset>

      <!-- Porcentajes -->
      <fieldset name="store-comission" class="container-column content-section form-section-store-commission">
        <h2 class="subtitle">Porcentajes</h2>
        <div class="grid-cols-2 input-group-dir">
          <InputField
            label_for="app-commission"
            label_text="Porcentaje de comisión con la app*"
            input_type="number"
            input_placeholder="Escribir número..."
            input_id="app-commission"
          />

          <InputField
            label_for="author-commission"
            label_text="Porcentaje de comisión con autores de platos*"
            input_type="number"
            input_placeholder="Escribir número..."
            input_id="author-commission"
          />
        </div>
      </fieldset>

      <!-- Métodos de pago -->
      <fieldset name="store-payment-methods" class="container-column content-section">
        <h2 class="subtitle">Métodos de Pago</h2>
        <div class="payments-checkbox-group">
          <CheckboxField id="efectivo" name="efectivo" value="EFECTIVO" label_text="Efectivo" />
          <CheckboxField id="qr" name="qr" value="QR" label_text="QR" />
          <CheckboxField id="transferencia" name="transferencia" value="TRANSFERENCIA" label_text="Transferencia" />
        </div>
      </fieldset>

      <!-- Botones -->
      <ButtonGroup disabled={true} />
    </form>
  </article>
</main>