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
     <form action="" id="form-store-profile" class="container-column form-store-profile">
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
               </div>   
                <div>
                  <Input
                    description="URL de la imagen*"
                    input_type={InputTypes.Normal}
                    inputProps={{
                    class: "input-primary",
                    label: "Imagen*",
                    name: "name",
                    }}/>
            </div>
            
            </div>  
            <div class="img-store-container"><img src="src\lib\assets\img\CarlosBakeShop.jpg" alt="local" class="img-store-profile"></div>  
            
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
                name: "name",
              }}/>
            </div>
            <div>
              <Input
                description="Altura"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Altura*",
                name: "name",
              }}/>
           </div>  
           <div>
                            <Input
                description="Latitud"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Latitud*",
                name: "name",
            }}/>
          </div> 
          <div>
              <Input
                description="Longitud"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Longitud*",
                name: "name",
            }}/>
          </div> 
          </div>
        </fieldset>
        <fieldset form="form-store-profile" name="store-comission" class="container-column content-section form-section-store-commission">
        <h2 class="subtitle">Porcentajes</h2>
        <div class="grid-cols-2 input-group-dir ">
        <div class="container-column input-group">
          <div class="grid-cols-2 input-group-dir ">
              <Input
                description="Porcentaje de comision con la app*"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Porcentaje de comision con la app*",
                name: "name",
            }}/>
            </div>
      
            <div class="container-column input-group">
              <Input
                description="Porcentaje de comision con autores de platos*"
                input_type={InputTypes.Normal}
                inputProps={{
                class: "input-primary",
                label: "Porcentaje de comision con autores de platos*",
                name: "name",
            }}/>
            </div>                    
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
                   class="payment-checkbox">
                   <span>Efectivo</span>
            </label>
        
            <label for="qr" class="label-color">
            <input type="checkbox" id="qr" 
                   name="qr" 
                   value="QR"
                   class="payment-checkbox">
                   <span>QR</span>
            </label>
    
            <label for="transferencia" class="label-color">
            <input type="checkbox" id="transferencia" 
                   name="transferencia" 
                   value="TRANSFERENCIA"
                   class="payment-checkbox">
                   <span>Transferencia</span>
            </label>
            </div>
          </fieldset>
                
                <section class="btn-group-actions">
                    <button disabled class="btn btn-secondary btn-store">Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
                    <button disabled class="btn btn-primary btn-store">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
                </section>
            </form>
  </article>
</main>