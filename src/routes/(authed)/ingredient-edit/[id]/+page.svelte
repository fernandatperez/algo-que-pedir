<script lang="ts">
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/components-css/input.css";
  import "$lib/css/components-css/switch-button.css";
  import "$lib/css/components-css/grid-table.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/pages-css/8-ingredient-edit.css";
  
  import { goto } from '$app/navigation';
  import { page } from '$app/state';
  import { INGREDIENT_MOCK } from '$lib/data/mock/ingredients';
  import { foodGroupDict, type FoodGroupValue } from '$lib/domain/ingredient';
  import type { IngredientJSON, IngredientType, ValidationMessage } from '$lib/domain/ingredient';

  // Tomar el id de la URL (viene como string)
  // const ingredientId = page.params.id;

  // // Buscar el ingrediente correcto, y declaro que no va a ser undefined
  // let ingredient: IngredientJSON = INGREDIENT_MOCK.find(i => i.id !== undefined && i.id.toString() === ingredientId)!;

  let { data } = $props()
  const { ingredient } = data

  let errors: ValidationMessage[] = $state([])
</script>

<section class="container-column">
  <section class="main-content">

    <h1 class="header-title"> Editar ingrediente </h1>

    {#if ingredient}
      <form class="ingredient-edit-section" id="form-ingredient-edit">
        <section class="input-group">
          <label class="label-color" for="form-ingredient-name">Nombre del ingrediente*</label>
          <input type="text" id="form-ingredient-name" value="{ingredient.name}" class="input-primary" required>
        </section>

        <section class="input-group">
          <label class="label-color" for="form-ingredient-cost">Costo*</label>
          <input type="text" id="form-ingredient-cost" value="${ingredient.cost}" class="input-primary" required>
        </section>

        <section class="input-group">
          <label class="label-color" for="form-ingredient-group">Grupo Alimenticio</label>
          <select id="form-ingredient-group" class="input-primary" required>
            <option value={ingredient.foodGroup} selected hidden>{ingredient.foodGroup}</option>
            {#each Object.entries(foodGroupDict) as [value, grupo]}
              <option value={value}> {grupo.label} </option>
            {/each}
          </select>
        </section>

        <section class="animal-origin-display input-group">
          <h3>Origen animal</h3>
          <div class="slide-button">
            <input type="checkbox" class="toggle" id="switch-button" checked = {ingredient.esOrigenAnimal} />
            <div class="background-div">
              <div class="circle-slide"></div>
            </div>
          </div>          
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