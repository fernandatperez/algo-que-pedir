<script lang="ts">
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/components-css/grid-table.css"
  import "$lib/css/components-css/icon.css"
  import "$lib/css/components-css/buttons.css"
  import "$lib/css/components-css/input.css"
  import "$lib/css/pages-css/7-ingredients.css"

  import Ingredient from "$lib/components/Ingredient.svelte"
  import { goto } from '$app/navigation'

  import { IngredientType, ValidationMessage } from "$lib/domain/ingredient"
  import { foodGroupDict, type FoodGroupValue } from '$lib/domain/ingredient'
  import { enhance } from '$app/forms'
  import type { SubmitFunction } from '@sveltejs/kit'
  import { ingredientService } from '$lib/services/IngredientService';
  import { onMount } from 'svelte';
  import { showError } from '$lib/domain/errorHandler'
  import ValidationField from '$lib/components/ValidationField.svelte';
  import { Modal, Content, Trigger } from "sv-popup";

  // Valores reactivos $state()
  // https://svelte.dev/docs/svelte/$state
  let ingredients = $state<IngredientType[]>([])
  
  const findIngredients = async () => {
    try{
      ingredients = await ingredientService.getAllIngredients()
    } catch (error){
      showError('Conexion al servidor fallida', error)
    }
  }

  onMount(findIngredients)

  // Creamos un ingrediente nuevo 
  let newIngredient = <IngredientType>(new IngredientType())

  let errors: ValidationMessage[] = $state([])

  // Estado para mostrar/ocultar formulario
  let showForm = $state(false);

  // tiene que ser tipado por una clase de svelte 
  const onSubmit: SubmitFunction = async ({ formData, cancel }) => {
    // formData es un map (key, value) de los inputs del form
    const ingredient = new IngredientType(
      ingredients.length + 1,
      (formData.get('name') ?? "").toString(),
      parseFloat(formData.get('cost') as string),
      (formData.get('foodGroup') ?? "") as FoodGroupValue,
      formData.get("esOrigenAnimal") === "true"
    )
    
    ingredient.validate()

    if (ingredient.errors.length > 0) {
      errors = [...ingredient.errors]
      cancel()
      return
    }

    try {
      // guardar en el backend
      await ingredientService.createIngredient(ingredient)
      // refrescar lista
      // await findIngredients()
      // cerrar form
      showForm = false
      errors = [] // limpiar errores
    } catch (error) {
      showError("Error al crear el ingrediente", error)
      cancel()
    }

  }

  const onCancel = () => {
    // Reset
    newIngredient = new IngredientType();
    showForm = false;
    errors = [] // limpiar errores
  }

  // Estado para mostrar/ocultar el modal
  let showModal = $state(false)
  let modalId: number = $state(0)

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
      <div class="grid-table-container product-edit-ingredients-table">
        <header class="grid-table-row table-header">
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
        <!-- type="submit" -> use:enhance={onSubmit} -->
          <form method="POST" use:enhance={onSubmit} onreset={onCancel} id="form-ingredient" class="grid-table-row product-edit-ingredients-table-content">
            <section class="cell">
              <input
                class="input-primary"
                placeholder="Huevo"
                name="name"
              />
              <ValidationField errors={errors} field="name" />
            </section>
            <section class="cell">
              <input
                class="input-primary"
                placeholder="$0.80"
                name="cost"
              />
              <ValidationField errors={errors} field="cost" />
            </section>
            <section class="cell">
              <select class="input-primary" name="foodGroup">
                <option value="" disabled selected hidden>Seleccionar</option>
                {#each Object.entries(foodGroupDict) as [value, grupo]}
                  <option value={value}> {grupo.label} </option>
                  {/each}
              </select>
              <ValidationField errors={errors} field="foodGroup" />
            </section>

            <section class="btn-group-actions btn-group-new-ingredient">
              <button form="form-ingredient" class="btn btn-secondary" type="reset">Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
              <button form="form-ingredient" class="btn btn-primary" type="submit">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
            </section>
          </form>
          
        {/if}

        <!-- Renderizamos cada ingrediente -> Props { ingredient: Ingredient } -->
        <!-- Usamos el rest property {...ing} para pasar todas las propiedades de la lista de ingredientes -->
        {#each ingredients as ing}
        <article class="grid-table-row product-edit-ingredients-table-content">
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
          <div class="modal-backdrop">
            <div class="modal">
              <h2>¿Seguro que querés eliminar {ingredients.find(i => i.id === modalId)?.name}?</h2>
              <section class="cell multiple-action-buttons modal-btns">
                <button class="btn btn-secondary btn-modal" onclick={() => showModal = false}>No</button>
                <button class="btn btn-primary btn-modal" onclick={() => {
                  const ingredient = ingredients.find(i => i.id === modalId);
                  if (ingredient) deleteIngredient(ingredient);
                }}>Sí</button>
              </section>
            </div>
          </div>
        {/if}
        <!-- Modal de confirmación para eliminar -->
          <!-- <Modal class="modal-delete" basic small={true}>
          <Content class="modal-content">
            <h2>{`¿Seguro que querés eliminar ${ing.name}?`}</h2>
            <section class="cell multiple-action-buttons">
              <button class="btn btn-secondary" onclick={() => goto('/')} aria-label="Cancelar">No</button>
              <button class="btn btn-primary" onclick={() => deleteIngredient(ing)} aria-label="Eliminar">Si</button>
            </section>
          </Content>
          
          <Trigger>
            <button class="icon-action-btn" aria-label="Eliminar"><i class="ph ph-trash gray-icon" id="acciones-{ing.id}"></i></button>
          </Trigger>
        </Modal> -->

      </div>
    </section>
  </section>
</section>
