<script lang="ts">
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/components-css/flex-table.css"
  import "$lib/css/components-css/icon.css"
  import "$lib/css/components-css/buttons.css"
  import "$lib/css/components-css/input.css"
  import "$lib/css/pages-css/7-ingredients.css"

  import Ingredient from "$lib/components/Ingredient.svelte"
  import { goto } from '$app/navigation'

  import { IngredientType } from "$lib/domain/ingredient"
  import type { ValidationMessage } from '$lib/domain/validationMessage'
  import { foodGroupDict, type FoodGroupValue } from '$lib/domain/ingredient'
  import { ingredientService } from '$lib/services/IngredientService'
  import { onMount } from 'svelte'
  import { showError } from '$lib/domain/errorHandler'
  import ValidationField from '$lib/components/ValidationField.svelte'
  import Modal from '$lib/components/Modal.svelte'
  import { toasts } from '$lib/components/toast/toastStore'
  import Input from "$lib/components/Input.svelte"
  import { InputTypes } from "$lib/components/InputTypes"

  // Valores reactivos $state()
  // https://svelte.dev/docs/svelte/$state
  let ingredients = $state<IngredientType[]>([])
  
  // Creamos un ingrediente nuevo 
  let newIngredient = <IngredientType>(new IngredientType())

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
      showModal = false
    } catch (error: unknown) {
      showError('Error al eliminar la tarea', error)
      await findIngredients()
    }
  }

  const onSubmit = async (ev: SubmitEvent) => {
    ev.preventDefault() // cancela el comportamiento por defecto del navegador frente al evento del submit

    // ev.currentTarget: es el elemento que tiene asignado el event listener
    // as HTMLFormElement es un type assertion de TypeScript: le decís explícitamente al compilador “esto es un formulario”
    const form = ev.currentTarget as HTMLFormElement
    const formData = new FormData(form) // creo el formData

    const ingredient = new IngredientType(
      ingredients.length + 1,
      (formData.get("name") ?? "").toString(),
      Number(formData.get("cost") ?? 0),
      (formData.get("foodGroup") ?? "") as FoodGroupValue,
      Boolean(formData.get("esOrigenAnimal") ?? "true")
    )
    console.info("el nuevo ingrediente es ", ingredient)

    ingredient.validate()

    if (ingredient.errors.length > 0) {
      errors = [...ingredient.errors]
      if (!toastLock) {
        ingredient.errors.forEach(error => {
            toasts.push(error.message, {type: 'error'})
            toastLock = true
            setTimeout(releaseToast, 5000)
          })
      }
      return errors
    }

    try {
      await ingredientService.createIngredient(ingredient)

      await findIngredients()

      showForm = false
      errors = [] // limpiar errores
    } catch (error) {
      showError("Error al crear el ingrediente", error)
    }
  }

  const onCancel = () => {
    // Reset
    newIngredient = new IngredientType()
    showForm = false
    errors = [] // limpiar errores
  }

  const releaseToast = () => {
    toastLock = false
  }  
</script>

<section class="flex-column">
  <section class="main-content-ingredients">
    <section class="section-title">
      <h1 class="header-title-ingredients ingredients-title">Ingredientes</h1>
      <div class="new-ingredient">
        <!--Es un button-->
        <button class="btn-add" onclick={() => (showForm = true)}>Nuevo Ingrediente</button>
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
          <form onsubmit={onSubmit} onreset={onCancel} id="form-ingredient" class="flex-table-row product-edit-ingredients-table-content">
            <div class="fieldset-section">
              <section class="cell">
                <Input
                description=""
                input_type={InputTypes.Normal}
                inputProps={{
                  class: "input-primary",
                  placeholder: "Huevo",
                  name: "name",
                }}
                />
                <!-- <input class="input-primary" placeholder="Huevo" name="name"/> -->
                <ValidationField errors={errors} field="name" />
              </section>
              <section class="cell">
                <Input
                description=""
                input_type={InputTypes.Normal}
                inputProps={{
                  class: "input-primary",
                  placeholder: "0.80",
                  name: "cost",
                }}
                />
                <!-- <input class="input-primary" placeholder="$0.80" name="cost"/> -->
                <ValidationField errors={errors} field="cost" />
              </section>
              <section class="cell">
                <select class="input-primary" name="foodGroup">
                  <option value="" disabled selected hidden>Seleccionar</option>
                  <!-- {#each Object.entries(foodGroupDict) as [value, grupo]}
                    <option value={value}> {grupo.label} </option>
                  {/each} -->
                  {#each Object.keys(foodGroupDict) as value}
                    <option value={value}>{foodGroupDict[value as FoodGroupValue].label}</option>
                  {/each}
                </select>
                <ValidationField errors={errors} field="foodGroup" />
              </section>

            </div>
            <section class="btn-group-actions btn-group-new-ingredient">
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
