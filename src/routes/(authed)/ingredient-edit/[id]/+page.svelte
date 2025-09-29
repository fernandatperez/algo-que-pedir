<script lang="ts">
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/component-css/input.css"
  import "$lib/css/component-css/switch-button.css"
  import "$lib/css/component-css/grid-table.css"
  import "$lib/css/component-css/icon.css"
  import "$lib/css/component-css/buttons.css"
  import "$lib/css/pages-css/8-ingredient-edit.css"
  
  import { goto } from '$app/navigation'
  import { foodGroupDict, type FoodGroupValue } from '$lib/domain/ingredient'
  import { IngredientType, type IngredientJSON, type ValidationMessage } from '$lib/domain/ingredient'
  import { ingredientService } from '$lib/services/IngredientService.js'
  import { showError } from '$lib/domain/errorHandler.js'
  import ValidationField from '$lib/components/ValidationField.svelte'
  
  let { data } = $props()
  const { ingredient } = data
  
  let errors: ValidationMessage[] = $state([])
  
  let ingredientEdit = ingredient
  // Guardamos el ingrediente original para comparar
  
  // let buscarHabilitado = $derived(ingredientEdit.name !== newIngredient.name ||
  // ingredientEdit.cost !== newIngredient.cost || ingredientEdit.foodGroup !== newIngredient.foodGroup ||
  // ingredientEdit.esOrigenAnimal !== newIngredient.esOrigenAnimal)
  
  const onSubmit = async (ev: SubmitEvent) => {
    ev.preventDefault() // cancela el comportamiento por defecto del navegador frente al evento del submit

    // ev.currentTarget: es el elemento que tiene asignado el event listener
    // as HTMLFormElement es un type assertion de TypeScript: le decís explícitamente al compilador “esto es un formulario”
    const form = ev.currentTarget as HTMLFormElement
    const formData = new FormData(form) // creo el formData

    const ingredient = new IngredientType(
      ingredientEdit?.id,
      (formData.get("name") ?? "").toString(),
      Number(formData.get("cost") ?? 0),
      (formData.get("foodGroup") ?? "") as FoodGroupValue,
      Boolean(formData.get("esOrigenAnimal") ?? "true")
    )
    console.info("el nuevo ingrediente es ", ingredient)

    ingredient.validate()

    if (ingredient.errors.length > 0) {
      errors = [...ingredient.errors]
      return
    }

    try {
      await ingredientService.updateIngredient(ingredient)
      goto("/ingredients")
      errors = [] // limpiar errores
    } catch (error) {
      showError("Error al crear el ingrediente", error)
    }
  }
  
  const onCancel = () => {
    errors = [] // limpiar errores
  }
</script>

<section class="container-column">
  <section class="main-content">

    <h1 class="header-title"> Editar ingrediente </h1>

    {#if ingredientEdit}
      <form onsubmit={onSubmit} onreset={onCancel} class="ingredient-edit-section" id="form-ingredient-edit">
        <section class="input-group">
          <label class="label-color" for="form-ingredient-name">Nombre del ingrediente*</label>
          <input type="text" id="form-ingredient-name" value={ingredientEdit.name} class="input-primary" name="name" required>
          <ValidationField errors={errors} field="name" />
        </section>

        <section class="input-group">
          <label class="label-color" for="form-ingredient-cost">Costo*</label>
          <input type="text" id="form-ingredient-cost" value={ingredientEdit.cost} class="input-primary" name="cost" required>
          <ValidationField errors={errors} field="cost" />
        </section>

        <section class="input-group">
          <label class="label-color" for="form-ingredient-group">Grupo Alimenticio</label>
          <select id="form-ingredient-group" class="input-primary" name="foodGroup" required value={ingredientEdit.foodGroup}>
            <option value="" disabled selected hidden>{ingredientEdit.foodGroup}</option>
            {#each Object.entries(foodGroupDict) as [value, grupo]}
              <option value={value}> {grupo.label} </option>
            {/each}
          </select>
          <ValidationField errors={errors} field="foodGroup" />
        </section>

        <section class="animal-origin-display input-group">
          <h3>Origen animal</h3>
          <div class="slide-button">
            <input type="checkbox" class="toggle" id="switch-button" checked = {ingredientEdit.esOrigenAnimal} name="esOrigenAnimal"/>
            <div class="background-div">
              <div class="circle-slide"></div>
            </div>
          </div>          
          <ValidationField errors={errors} field="esOrigenAnimal" />
        </section>
      </form>

      <section class="btn-group-actions">
        <button form="form-ingredient-edit" class="btn btn-secondary" onclick={() => goto('/ingredients')} type="reset">Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
        <button form="form-ingredient-edit" class="btn btn-primary" type="submit">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
        
      </section>
    {:else}
      <p>Ingrediente no encontrado.</p>
    {/if}
  </section>

</section>