
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
  import errorImage from '$lib/assets/img/error.png';
  import { tick } from 'svelte';
  
  let store = $state<StoreType>(new StoreType())
  let currentStore = $state<StoreType>(new StoreType())
  let originalStore = $state<StoreType>(new StoreType())
  let errors: ValidationMessage[] = $state([])
  let toastLock: boolean = false
  let showErrorImage = false

  const findStore = async () => {
    try{
      store = await storeService.getStore()
      originalStore = JSON.parse(JSON.stringify(store)) 
      currentStore = store 
    } catch (error){
      showError('Conexion al servidor fallida', error)
    }
  }

  async function discardChanges() {
    if (originalStore) {
      currentStore = Object.assign(new StoreType(), JSON.parse(JSON.stringify(originalStore)))
      errors = []
      showErrorImage = false
      await tick()

      // resetea los valores 
      setTimeout(() => {
        const form = document.getElementById('form-store-profile') as HTMLFormElement
        if (form && currentStore) {
          const inputs = form.querySelectorAll('input')
          inputs.forEach(input => {
            if (input.type === 'checkbox') {
              if (input.name === 'storePaymentEfectivo') input.checked = currentStore.storePaymentEfectivo
              if (input.name === 'storePaymentQR') input.checked = currentStore.storePaymentQR
              if (input.name === 'storePaymentTransferencia') input.checked = currentStore.storePaymentTransferencia
            } else {
              if (input.name === 'name') input.value = currentStore.name || ""
              if (input.name === 'storeURL') input.value = currentStore!.storeURL || ""
              if (input.name === 'storeAddress') input.value = currentStore.storeAddress || ""
              if (input.name === 'storeAltitude') input.value = currentStore.storeAltitude.toString() || "0"
              if (input.name === 'storeLatitude') input.value = currentStore.storeLatitude.toString() || "0"
              if (input.name === 'storeLongitude') input.value = currentStore.storeLongitude.toString() || "0"
              if (input.name === 'storeAppCommission') input.value = currentStore.storeAppCommission?.toString() || "0"
              if (input.name === 'storeAuthorCommission') input.value = currentStore.storeAuthorCommission?.toString() || "0"
            }
          })
        }
      })
    }
  }

   onMount(() => {
    findStore()

  })

  const onSubmit = async (ev: SubmitEvent) => {
    ev.preventDefault()
    errors = []
    const form = ev.currentTarget as HTMLFormElement
    showErrorImage = false
   
    const formData = new FormData(form)

    
    const store = new StoreType(
      currentStore?.id,
      (formData.get("name") ?? "")?.toString(),
      (formData.get("storeURL") ?? "")?.toString(),
      (formData.get("storeAddress") ?? "")?.toString(),
      Number(formData.get("storeAltitude") ?? 0),
      Number(formData.get("storeLatitude") ?? 0),
      Number(formData.get("storeLongitude") ?? 0),
      Number(formData.get("storeAppCommission") ?? 0) / 100, // porcentaje
      Number(formData.get("storeAuthorCommission") ?? 0) / 100, // porcentaje
      Boolean(formData.get("storePaymentEfectivo") ?? false), 
      Boolean(formData.get("storePaymentQR") ?? false),
      Boolean(formData.get("storePaymentTransferencia") ?? false), 
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
  if(!toastLock) {
    toasts.push('Error al actualizar la tienda', {type: 'error'})
  }
  showError("Error al actualizar la tienda", error)
}
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
                  value={currentStore.name || ""}
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
                value={currentStore.storeURL || ""}
                class= "input-primary"
                name= "storeURL"
                />
                <ValidationField errors={errors} field="url" />  
              </div>
            </div>  
            <div class="img-store-container">
              <img src={currentStore.storeURL} alt="local" class="img-store-profile" />
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
                value={currentStore.storeAddress || ""}
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
                value={currentStore.storeAltitude || 0}
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
              value={currentStore.storeLatitude || ""}
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
              value={currentStore.storeLongitude || ""}
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
              value={currentStore.storeAppCommission || ""}
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
              value={currentStore.storeAuthorCommission || ""}
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
              label_text="Efectivo" 
              value={currentStore.storePaymentEfectivo ?? false} 
              checked={currentStore.storePaymentEfectivo ?? false }
            />
            <!-- <p>{currentStore?.storePaymentEfectivo ?? false}</p> -->

            <!-- Checkbox QR -->
             <!-- alternativa a htmlinputelement bind:checked={storePaymentEfectivo}-->
            <Checkbox
              name="storePaymentQR"
              label_text="QR" 
              value={currentStore.storePaymentQR ?? false} 
              checked={currentStore.storePaymentQR ?? false }
            />

            <!-- Checkbox Transferencia -->
            <Checkbox
              name="storePaymentTransferencia"
              label_text="Transferencia" 
              value={currentStore.storePaymentTransferencia ?? false} 
              checked={currentStore.storePaymentTransferencia ?? false}
            />
            <ValidationField errors={errors} field="metodopago" />
          </div>
        </fieldset>           
    <section class="btn-group-actions">
      <button type="button" class="btn btn-secondary btn-store" onclick={discardChanges}>Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
      <button type="submit" class="btn btn-primary btn-store" > Guardar<span class="p-cambios display-none-mobile">Cambios</span></button>
    </section>
    </form>
  </article>
</main>