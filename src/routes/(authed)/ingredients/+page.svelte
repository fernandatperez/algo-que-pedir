<script lang="ts">
  import Ingredient from "$lib/components/Ingredient.svelte"
  import { goto } from '$app/navigation'

  import { IngredientType } from "$lib/domain/ingredient"
  import type { ValidationMessage } from '$lib/domain/validationMessage'
  import { foodGroupDict, FoodGroupValue } from '$lib/domain/ingredient'
  import { ingredientService } from '$lib/services/IngredientService'
  import { onMount, tick } from 'svelte'
  import { showError } from '$lib/domain/errorHandler'
  import ValidationField from '$lib/components/ValidationField.svelte'
  import Modal from '$lib/components/Modal.svelte'
  import { toasts } from '$lib/components/toast/toastStore'
  import { InputTypes } from "$lib/components/InputPropsI"
  import Input from "$lib/components/Input.svelte"

  // Valores reactivos $state()
  // https://svelte.dev/docs/svelte/$state
  let ingredients = $state<IngredientType[]>([])
  
  // Creamos un ingrediente nuevo 
  let newIngredient = new IngredientType()

  let selectFoodGroup = $state(newIngredient.foodGroup)
  let displayedFoodGroup = $derived(selectFoodGroup)

  let errors: ValidationMessage[] = $state([])
  let toastLock: boolean = false

  // Estado para mostrar/ocultar formulario
  let showForm = $state(false)

  // Estado para mostrar/ocultar el modal
  let showModal = $state(false)
  let modalId: number = $state(0)

  const findIngredients = async () => {
    try{
      ingredients = await ingredientService.getAllIngredients()
    } catch (error){
      showError('Conexion al servidor fallida', error)
    }
  }

  // onMount es un ciclo de vida (lifecycle hook).
  // Se ejecuta una sola vez cuando el componente ya está montado en el DOM.
  // hace que findIngredients se ejecute automáticamente cuando el componente ya está en pantalla
  onMount(findIngredients)

  function openModal(id: number) {
    modalId = id
    showModal = true
  }

  const deleteIngredient = async (ingredient: IngredientType) => {
    try {
      await ingredientService.deleteIngredient(ingredient)
      findIngredients()
      toasts.push('Ingrediente eliminado exitosamente', {type: 'success'})
      showModal = false
    } catch (error) {
      if(!toastLock) {
        // toasts.push('Error al eliminar el ingrediente', {type: 'error'})
        showError('Error al eliminar el ingrediente', error)
        toastLock = true
        setTimeout(releaseToast, 5000)
      }
      await findIngredients()
    }
  }

  const onSubmit = async (ev: SubmitEvent) => {
    ev.preventDefault() // cancela el comportamiento por defecto del navegador frente al evento del submit
    errors = []
    // ev.currentTarget: es el elemento que tiene asignado el event listener
    // as HTMLFormElement es un type assertion de TypeScript: le decís explícitamente al compilador “esto es un formulario”
    const form = ev.currentTarget as HTMLFormElement
    const formData = new FormData(form) // creo el formData

    const ingredient = new IngredientType(
      newIngredient.id,
      (formData.get("name") ?? "").toString(),
      Number(formData.get("cost") ?? 0),
      (formData.get("foodGroup") ?? "") as FoodGroupValue,
      Boolean(formData.get("esOrigenAnimal") ?? "true")
    )
    console.info("el nuevo ingrediente es ", ingredient)

    ingredient.validate()

    if (ingredient.errors.length > 0) {
      errors = [...ingredient.errors]
      return errors
    }

    try {
      await ingredientService.createIngredient(ingredient)
      await findIngredients()

      toasts.push('Ingrediente guardado exitosamente', {type: 'success'})
    } catch (error: unknown) {
      showError("Error al crear el ingrediente", error)
    } finally {
      resetNewIngredient()
    }
  }

  const releaseToast = () => {
    toastLock = false
  }  

  const resetNewIngredient = () => {
    newIngredient = new IngredientType() // nuevo objeto
    displayedFoodGroup = newIngredient.foodGroup // le seteo el foodGroup default para que en la vista mobile no rompa
    showForm = false
    errors = [] // limpiar errores
  }

</script>

<style>
  @import url("$lib/css/flex-grid.css");
  @import url("$lib/css/components-css/flex-table.css");
  @import url("$lib/css/components-css/icon.css");
  @import url("$lib/css/components-css/buttons.css");
  @import url("$lib/css/pages-css/7-ingredients.css");

  .aniadir-ing {
    text-align: center;
  }
</style>

<section class="flex-column">
  <section class="main-content-ingredients">
    <section class="section-title">
      <h1 class="header-title-ingredients ingredients-title">Ingredientes</h1>
      <div class="new-ingredient">
        <!--Es un button-->
        <button class="btn-add aniadir-ing" onclick={() => (showForm = true)}>Nuevo Ingrediente</button>
      </div>
    </section>

    <section class="content-section-ingredients form-section-product-ingredients container-column">
      <div class="flex-table-container">
        <header class="flex-table-row table-header">
          <section class="cell" id="name">Nombre</section>
          <section class="cell" id="name">Costo</section>
          <section class="cell later-hid" id="grupo-alimenticio">
            <span> Grupo </span>
            <span class="p-alimenticio display-none-mobile"> Alimenticio </span>
          </section>
          <section class="cell col-centered later-hid" id="origen">
            Origen
          </section>
          <section class="cell col-centered" id="acciones">Acciones</section>
        </header>

        <!-- Formulario emergente por si quiero agregar otro ingrediente -->
        {#if showForm}
        <!-- use:enhance: te trae la data del form cuando llamas al onSubmit, permitiendo sacar el bind:value  -->
        <!-- type="reset" -> onreset={reset} -->
        <!-- type="submit" -> use:enhance={onSubmit} pero es necesario el es necesario el +page.server.ts, asi que de baja-->
          <form onsubmit={onSubmit} onreset={resetNewIngredient} id="form-ingredient" class="flex-table-row">
            <div class="fieldset-section">
              <section class="cell">
                <Input 
                  label_text=""
                  label_for=""
                  input_type={InputTypes.Normal}
                  value=""
                  class= "input-primary"
                  placeholder= "Huevo"
                  name= "name"
                />
                <ValidationField errors={errors} field="name" />
              </section>
              <section class="cell">
                <Input
                  label_text=""
                  label_for=""
                  input_type={InputTypes.Normal}
                  value=""
                  class= "input-primary"
                  placeholder= "0.80"
                  name= "cost"
                  type="number"
                  step="any"
                />
                <ValidationField errors={errors} field="cost" />
              </section>
              <section class="cell display-none-mobile">
                <select class="input-primary" name="foodGroup" bind:value={displayedFoodGroup}>
                  <option value="" disabled selected hidden>Seleccionar</option>
                  {#each Object.keys(foodGroupDict) as value}
                    <option value={value}>{foodGroupDict[value as FoodGroupValue].label}</option>
                  {/each}
                </select>
                <ValidationField errors={errors} field="foodGroup" />
              </section>
              <section class="cell display-none-mobile icon-cell">
                <!-- Para que me muestre el icono del foodGroup que selecciono -->
                {#if displayedFoodGroup && foodGroupDict[displayedFoodGroup]}
                  <i class={"ph " + foodGroupDict[displayedFoodGroup].icon + " gray-icon"}></i>
                {/if}
              </section>
            </div>
            <section class="btn-group-actions btn-group-new-ingredient cell">
              <button form="form-ingredient" class="btn btn-secondary" type="reset">Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
              <button form="form-ingredient" class="btn btn-primary" type="submit">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
            </section>
          </form>
        {/if}

        <!-- Renderizamos cada ingrediente -> Props { ingredient: Ingredient } -->
        <!-- Usamos el rest property {...ing} para pasar todas las propiedades de la lista de ingredientes -->
        {#each ingredients as ing}
        <article class="flex-table-row product-edit-ingredients-table-content">
            <Ingredient ingredient={ing} />

            <section class="cell multiple-action-buttons">
              <button disabled class="icon-action-btn hidden-icons" aria-label="Ver"><i class="ph ph-eye gray-icon"></i></button>
              <span><i class="ph ph-line-vertical gray-icon hidden-icons"></i></span>
              <button class="icon-action-btn" onclick={() => goto (`/ingredient-edit/${ing.id}`)} aria-label="Editar"><i class="ph ph-pencil gray-icon"></i></button>
              <span><i class="ph ph-line-vertical gray-icon"></i></span>
              <button class="icon-action-btn" onclick={() =>{deleteIngredient ; openModal(ing.id as number);}} aria-label="Eliminar"><i class="ph ph-trash gray-icon"></i></button>
            </section>

          </article>
        {/each}

        {#if showModal && modalId}
          <Modal
            title ={`¿Seguro que querés eliminar el ingrediente "${ingredients.find(i => i.id === modalId)?.name}"?`} 
            confirmLabel="Sí"
            cancelLabel="No"
            actionConfirm={() => {
              const ing = ingredients.find(i => i.id === modalId)
              if (ing) deleteIngredient(ing)
              showModal = false
            }}
            actionCancel={() => showModal = false}
          />
        {/if}
      </div>
    </section>
  </section>
</section>
