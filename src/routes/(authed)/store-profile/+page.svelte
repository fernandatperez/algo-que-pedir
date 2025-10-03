
<script lang="ts">
  // Styles
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/number-input.css";
  import "$lib/css/components-css/grid-table.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/pages-css/9-store-profile.css";
  import Input from "$lib/components/Input.svelte";
  import { InputTypes } from "$lib/components/InputTypes"
  import {StoreType} from "$lib/domain/store"
  import { showError } from '$lib/domain/errorHandler'
  import { onMount } from 'svelte'
  import { toasts } from '$lib/components/toast/toastStore';
  import {  storeService } from '$lib/services/StoreProfileService'
  import type { ValidationMessage } from '$lib/domain/validationMessage';
  import ValidationField from "$lib/components/ValidationField.svelte";
  
  let store = $state<StoreType[]>([])
  let newStore = <StoreType>(new StoreType())
  let errors: ValidationMessage[] = $state([])
  let toastLock: boolean = false

  const findStore = async () => {
    try{
      store = await storeService.getStore()
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
      Boolean(formData.get("storePaymentEfectivo")?? "true"),
      Boolean(formData.get("storePaymentQR")?? "true"),
      Boolean(formData.get("storePaymentTransferencia")?? "true"),
    )
    console.info("el nuevo local es ", store)
    // Validar
    store.validate()

    if (store.errors.length > 0) {
      errors = [...store.errors]
      if (!toastLock) {
        store.errors.forEach(error => {
            toasts.push(error.message, {type: 'error'})
            toastLock = true
            setTimeout(releaseToast, 5000)
          })
      }
      return errors
    }

    try {
      await storeService.updateStore(store)
      await findStore()
      errors = []
      toasts.push('Tienda actualizada exitosamente', { type: 'success' })
    } catch (error) {
      showError("Error al actualizar la tienda", error)
    } 
  }

  function releaseToast() {
    toastLock = false
  }
</script>

<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title">Información del local</h1>
     <form action="" id="form-store-profile" class="container-column form-store-profile" onsubmit={onSubmit}>
      <!-- Datos del Local  -->
        <fieldset form="form-store-profile" name="store-info" class="content-section form-section-store-info">
          <div class="grid-cols-2 input-group-dir " >
            <div>
                <div>
                <Input
                    description="Nombre del local"
                    input_type={InputTypes.Normal}
                    inputProps={{
                    class: "input-primary",
                    label: "Nombre del local*",
                    name: "name",
                    }}
                />
                <ValidationField errors={errors} field="name" />
               </div>   
                <div>
                  <Input
                    description="URL de la imagen*"
                    input_type={InputTypes.Normal}
                    inputProps={{
                    class: "input-primary",
                    label: "Imagen*",
                    name: "storeURL",
                    }}/>
                  <ValidationField errors={errors} field="name" />  
            </div>
            
            </div>  
            <div class="img-store-container">
              <img src= "src/lib/assets/img/CarlosBakeShop.jpg" alt="local" class="img-store-profile">
            </div>  
            
          </div> 
          
          </fieldset>          
          <!-- Direccion  -->
          <fieldset form="form-store-profile" name="store-dir" class="container-column content-section">
            <h2 class="subtitle">Dirección</h2>
            <div class="grid-cols-2 input-group-dir ">
            <div>
              <Input
                description="Direccion Local"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Dirección*",
                name: "storeAddress",
              }}/>
              <ValidationField errors={errors} field="address" />
            </div>
            <div>
              <Input
                description="Altura"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Altura*",
                name: "storeAltitude",
              }}/>
              <ValidationField errors={errors} field="altitude" />
           </div>  
           <div>
                <Input
                description="Latitud"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Latitud*",
                name: "storeLatitude",
            }}/>
            <ValidationField errors={errors} field="latitude" />
          </div> 
          <div>
              <Input
                description="Longitud"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Longitud*",
                name: "storeLongitude",
            }}/>
            <ValidationField errors={errors} field="longitude" />
          </div> 
          </div>
        </fieldset>
        <fieldset form="form-store-profile" name="store-comission" class="container-column content-section form-section-store-commission">
        <h2 class="subtitle">Porcentajes</h2>
        <div class="grid-cols-2 input-group-dir ">
          <div>
              <Input
                description="Porcentaje de comision con la app*"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Porcentaje de comision con la app*",
                name: "storeAppCommission",
            }}/>
            <ValidationField errors={errors} field="appcommission" />
            </div>
      
            <div>
              <Input
                description="Porcentaje de comision con autores de platos*"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Porcentaje de comision con autores de platos*",
                name: "storeAuthorCommission",
            }}/>
            <ValidationField errors={errors} field="authorcommission" />
            </div>         
        </div>  
        </fieldset>
    
         <fieldset form="form-store-profile" name="store-payment-methods" class="container-column content-section">
          <h2 class="subtitle">Metodos de Pago</h2>
          <div class="payments-checkbox-group">
            
            <label for="efectivo" class="label-color">
            <input type="checkbox" id="efectivo" 
                   name="efectivo" 
                   value="EFECTIVO"
                   class="payment-checkbox"
            >
                   <span>Efectivo</span>
            </label>
        
            <label for="qr" class="label-color">
            <input type="checkbox" id="qr" 
                   name="qr" 
                   value="QR"
                   class="payment-checkbox"
            >
                   <span>QR</span>
            </label>

            <label for="transferencia" class="label-color">
            <input type="checkbox" id="transferencia" 
                   name="transferencia" 
                   value="TRANSFERENCIA"
                   class="payment-checkbox"
            >
                   <span>Transferencia</span>
            </label>
            <ValidationField errors={errors} field="metodopago" />
            </div>
          </fieldset>
                
                <section class="btn-group-actions">
                    <button type="button" class="btn btn-secondary btn-store">Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
                    <button type="submit" class="btn btn-primary btn-store" > Guardar
                     <span class="p-cambios display-none-mobile">Cambios</span>
                    </button>
                </section>
            </form>
  </article>
</main>