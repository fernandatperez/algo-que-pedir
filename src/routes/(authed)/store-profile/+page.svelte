
<script lang="ts">
  import { InputTypes } from "$lib/components/InputPropsI"
  import {StoreType} from "$lib/domain/store"
  import { showError } from '$lib/domain/errorHandler'
  import { onMount } from 'svelte'
  import { toasts } from '$lib/components/toast/toastStore';
  import {  storeService } from '$lib/services/StoreProfileService'
  import type { ValidationMessage } from '$lib/domain/validationMessage';
  import ValidationField from "$lib/components/ValidationField.svelte";
  import Input from "$lib/components/Input.svelte";
  import Checkbox from "$lib/components/checkbox.svelte";
  
  let store = $state<StoreType[]>([])
  let newStore = <StoreType>(new StoreType())
  let currentStore = $state<StoreType | null>(null) 
  let errors: ValidationMessage[] = $state([])
  let toastLock: boolean = false

  const findStore = async () => {
    try{
      store = await storeService.getStore()
      currentStore = store[0]
    } catch (error){
      showError('Conexion al servidor fallida', error)
    }
  }

  onMount(findStore)

  const onSubmit = async (ev: SubmitEvent) => {
    ev.preventDefault()
    errors = []
    const form =ev.currentTarget as HTMLFormElement
    const formData = new FormData(form)

    // Crear store actualizado con los valores ACTUALES del formulario
    const store = new StoreType(
      (formData.get("name" )?? "")?.toString(),
      (formData.get("storeURL" )?? "")?.toString(),
      (formData.get("storeAddress" )?? "")?.toString(),
      Number(formData.get("storeAltitude" )?? 0),
      Number(formData.get("storeLatitude" )?? 0),
      Number(formData.get("storeLongitude" )?? 0),
      Number(formData.get("storeAppCommission" )?? 0),
      Number(formData.get("storeAuthorCommission" )?? 0),
      // no estoy segura si deberia traer asi los checkbox del formdata
      formData.has("storePaymentEfectivo"),  
      formData.has("storePaymentQR"),         
      formData.has("storePaymentTransferencia") 
    )

    // Validar
    store.validate()

    if (store.errors.length > 0) {
      errors = [...store.errors]
      return errors
    }

    try {
      await storeService.updateStore(store)
      await findStore()
      errors = []
      toasts.push('Tienda actualizada exitosamente', {type: 'success'})
    } catch (error) {
      // error 500
      if(!toastLock) {
        toasts.push('Error al actualizar la tienda', {type: 'error'})
        setTimeout(releaseToast, 5000)
      }
      showError("Error al actualizar la tienda", error)
    } 
  }

  function releaseToast() {
    toastLock = false
  }
</script>

<style>
    /* Styles */
  @import url("$lib/css/flex-grid.css");
  @import url("$lib/css/components-css/number-input.css");
  @import url("$lib/css/components-css/grid-table.css");
  @import url("$lib/css/components-css/buttons.css");
  @import url("$lib/css/pages-css/9-store-profile.css");
</style>

<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title">Información del local</h1>
     <form action="" id="form-store-profile" class="container-column form-store-profile" onsubmit={onSubmit}>
      <!-- Datos del Local  -->
        <fieldset form="form-store-profile" name="store-info" class="content-section form-section-store-info">
          <div class="grid-cols-2 input-group-dir" >
            <div class="container-column form-section-store-info">
              <div class="container-column">
                <Input
                  label_text="Nombre del local*"
                  label_for="name"
                  input_type={InputTypes.Normal}
                  value={currentStore?.name || ""}
                  class= "input-primary"
                  name= "name"
                />
                <ValidationField errors={errors} field="name" />
              </div>   
              <div class="container-column">
                <Input
                label_text="URL de la imagen*"
                label_for="storeURL"
                input_type={InputTypes.Normal}
                value={currentStore?.storeURL || ""}
                class= "input-primary"
                name= "storeURL"
                />
                <ValidationField errors={errors} field="url" />  
              </div>
            </div>  
            <div class="img-store-container">
              <img src={currentStore?.storeURL || "src/lib/assets/img/CarlosBakeShop.jpg"} alt="local" class="img-store-profile">
            </div>  
            
          </div> 
          
          </fieldset>          
          <!-- Direccion  -->
          <fieldset form="form-store-profile" name="store-dir" class="container-column content-section">
            <h2 class="subtitle">Dirección</h2>
            <div class="grid-cols-2 input-group-dir ">
            <div>
              <Input
                label_text="Direccion Local"
                label_for="storeAddress"
                input_type={InputTypes.Normal}
                value={currentStore?.storeAddress || ""}
                class= "input-primary"
                name= "storeAddress"
              />
              <ValidationField errors={errors} field="address" />
            </div>
            <div>
              <Input
                label_text="Altura"
                label_for="storeAltitude"
                input_type={InputTypes.Normal}
                value={currentStore?.storeAltitude || 0}
                class= "input-primary"
                name= "storeAltitude"
                type="number"
                step="any"
              />
              <ValidationField errors={errors} field="altitude" />
           </div>  
           <div>
            <Input
              label_text="Latitud"
              label_for="storeLatitude"
              input_type={InputTypes.Normal}
              value={currentStore?.storeLatitude || ""}
              class= "input-primary"
              name= "storeLatitude"
              type="number"
              step="any"
            />
            <ValidationField errors={errors} field="latitude" />
          </div> 
          <div>
            <Input
              label_text="Longitud"
              label_for="storeLongitude"
              input_type={InputTypes.Normal}
              value={currentStore?.storeLongitude || ""}
              class= "input-primary"
              name= "storeLongitude"
              type="number"
              step="any"
            />
            <ValidationField errors={errors} field="longitude" />
          </div> 
          </div>
        </fieldset>
        <fieldset form="form-store-profile" name="store-comission" class="container-column content-section form-section-store-commission">
        <h2 class="subtitle">Porcentajes</h2>
        <div class="grid-cols-2 input-group-dir ">
          <div>
            <Input
              label_text="Porcentaje de comision con la app*"
              label_for="storeAppCommission"
              input_type={InputTypes.Normal}
              value={currentStore?.storeAppCommission || ""}
              class= "input-primary"
              name= "storeAppCommission"
              type="number"
              step="any"
            />
            <ValidationField errors={errors} field="appcommission" />
            </div>
      
            <div>
            <Input
              label_text="Porcentaje de comision con autores de platos*"
              label_for="storeAuthorCommission"
              input_type={InputTypes.Normal}
              value={currentStore?.storeAuthorCommission || ""}
              class= "input-primary number-input"
              name= "storeAuthorCommission"
              type="number"
              step="any"
            />
            <ValidationField errors={errors} field="authorcommission" />
            </div>         
        </div>  
        </fieldset>
    
        <fieldset form="form-store-profile" name="store-payment-methods" class="container-column content-section">
          <h2 class="subtitle">Metodos de Pago</h2>
          <div class="payments-checkbox-group">
            <!-- Checkbox Efectivo -->
            <Checkbox
              name="storePaymentEfectivo"
              label="Efectivo" 
              value="efectivo"
              checked={currentStore?.storePaymentEfectivo ?? false}
            />

            <!-- Checkbox QR -->
            <Checkbox
              name="storePaymentQR"
              label="QR" 
              value="qr"
              checked={currentStore?.storePaymentQR ?? true }
            />

            <!-- Checkbox Transferencia -->
            <Checkbox
              name="storePaymentTransferencia"
              label="Transferencia" 
              value="transferencia"
              checked={currentStore?.storePaymentTransferencia ?? false}
            />
            <ValidationField errors={errors} field="metodopago" />
          </div>
        </fieldset>           
    <section class="btn-group-actions">
      <button type="button" class="btn btn-secondary btn-store">Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
      <button type="submit" class="btn btn-primary btn-store" > Guardar<span class="p-cambios display-none-mobile">Cambios</span></button>
    </section>
    </form>
  </article>
</main>