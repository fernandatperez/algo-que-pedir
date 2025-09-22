<script>
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/number-input.css";
  import "$lib/css/components-css/grid-table.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/9-store-profile.css";
  import SectionTitle from "$lib/SectionTitle.svelte";
  import Input from "$lib/Input.svelte";
  import { text } from "@sveltejs/kit";
</script>

<!-- Content -->
<main class="container-column">
  <article class="container-column main-content">
    <SectionTitle text="Información del local" />
    <form id="form-store-profile" class="container-column form-store-profile">
      <!-- Datos del Local -->
      <fieldset
        form="form-store-profile"
        name="store-info"
        class="content-section form-section-store-info>"
      >
        <div class="grid-cols-2 input-group-dir">
          <div>
            <Input 
             label_for="url-store-img" 
             label_text="Nombre del local*" 
             input_placeholder="Carlo's Bake Shop" 
             input_id="store-name"
            />

            <Input 
             label_for="url-store-img" 
             label_text="URL de la imagen*" 
             input_placeholder="http://images.google.com/CarlosBakeShop.jpg" 
             input_id="url-store-img"
            />
          </div>

          <div class="img-store-container">
            <img
              src="/src/lib/assets/img/CarlosBakeShop.jpg"
              alt="local"
              class="img-store-profile"
            />
          </div>
        </div>
      </fieldset>

      <!-- Dirección -->
      <fieldset name="store-dir" class="container-column content-section">
        <h2 class="subtitle">Dirección</h2>
        <div class="grid-cols-2 input-group-dir">
          <div class="form-group">
          <Input 
           label_for="store-dir" 
           label_text="Dirección*" 
           input_placeholder="Escribir" 
           input_id="store-dir"
          />
          <Input 
             label_for="store-altitude" 
             label_text="Altura*" 
             input_placeholder="Escribir número..." 
             input_id="store-altitude"
          />
          </div>
          <div class="form-group">
          <Input 
             label_for="store-latitude" 
             label_text="Latitud*" 
             input_placeholder="Escribir número..." 
             input_id="store-latitude"
          />
          <Input 
             label_for="store-longitude" 
             label_text="Longitud*" 
             input_placeholder="Escribir número..." 
             input_id="store-longitude"
          />
        </div>
      </div>  
      </fieldset>

      <!-- Porcentajes -->
      <fieldset
        name="store-comission"
        class="container-column content-section form-section-store-commission"
      >
      <h2 class="subtitle">Porcentajes</h2>
        <div class="grid-cols-2 input-group-dir">
          <div class="form-group">
          <Input 
             label_for="app-commission" 
             label_text="Porcentaje de comisión con la app*" 
             input_placeholder="Escribir número..." 
             input_id="app-commission"
          />
          </div>
          <div class="form-group">
          <Input 
             label_for="author-commission" 
             label_text="Porcentaje de comisión con autores de platos*" 
             input_placeholder="Escribir número..." 
             input_id="author-commission"
          />
          </div>
        </div>
      </fieldset>

      <!-- Métodos de pago -->
      <fieldset form="form-store-profile" name="store-payment-methods" class="container-column content-section">
      <h2 class="subtitle">Metodos de Pago</h2>
      <div class="payments-checkbox-group">
          <label for="efectivo" class="label-color">
              <input type="checkbox" 
               id="efectivo" 
               name="efectivo" 
               value="EFECTIVO"
               class="payment-checkbox">
               <span>Efectivo</span>
          </label>
        
          <label for="qr" class="label-color">
             <input 
                type="checkbox" 
                id="qr" 
                name="qr" 
                value="QR"
                class="payment-checkbox">
                <span>QR</span>
          </label>
    
          <label for="transferencia" class="label-color">
              <input 
                type="checkbox" 
                id="transferencia" 
                name="transferencia" 
                value="TRANSFERENCIA"
                class="payment-checkbox">
                <span>Transferencia</span>
          </label>
        </div>
      </fieldset>

      <!-- Botones -->
      <section class="btn-group-actions">
        <button disabled class="btn btn-secondary btn-store">Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
        <button disabled class="btn btn-primary btn-store">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
      </section>
    </form>
  </article>
</main>
