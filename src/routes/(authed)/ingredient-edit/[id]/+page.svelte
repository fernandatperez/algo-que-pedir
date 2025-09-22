<script lang="ts">
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/component-css/input.css";
  import "$lib/css/component-css/switch-button.css";
  import "$lib/css/component-css/grid-table.css";
  import "$lib/css/component-css/icon.css";
  import "$lib/css/component-css/buttons.css";
  import "$lib/css/pages-css/8-ingredient-edit.css";
  
  import { goto } from '$app/navigation';
  import { page } from '$app/state';
  import { INGREDIENT_MOCK } from '$lib/data/mock/ingredients';
  import { foodGroups, type FoodGroupValue } from '$lib/type/ingredient';
  import type { IngredientType } from '$lib/type/ingredient';

  // Tomar el id de la URL (viene como string)
  const ingredientId = Number(page.params.id);

  // Buscar el ingrediente correcto, y declaro que no va a ser undefined
  let ingredient: IngredientType = INGREDIENT_MOCK.find(i => i.id === ingredientId)!;

  function irAIngredients () {
    goto ('/ingredients')
  }
</script>

<section class="container-column">
  <section class="main-content">

    <h1 class="header-title"> Editar ingrediente </h1>

    {#if ingredient}
      <form class="ingredient-edit-section" id="form-ingredient-edit">
        <section class="input-group">
          <label class="label-color" for="form-ingredient-name">Nombre del ingrediente*</label>
          <input type="text" id="form-ingredient-name" placeholder="{ingredient.name}" class="input-primary" required>
        </section>

        <section class="input-group">
          <label class="label-color" for="form-ingredient-cost">Costo*</label>
          <input type="text" id="form-ingredient-cost" placeholder="${ingredient.cost}" class="input-primary" required>
        </section>

        <section class="input-group">
          <label class="label-color" for="form-ingredient-group">Grupo Alimenticio</label>
          <select id="form-ingredient-group" class="input-primary" required>
            <option value="" disabled selected hidden>Seleccionar</option>
            {#each foodGroups as grupo}
              <option value={grupo.value}> {grupo.label} </option>
            {/each}
          </select>
        </section>

        <section class="animal-origin-display input-group">
          <h3>Origen animal</h3>
          <div class="slide-button">
            <input type="checkbox" class="toggle">
            <div class="background-div">
              <div class="circle-slide"></div>
            </div>
          </div>
        </section>

      </form>

      <section class="btn-group-actions">
        <button form="form-ingredient-edit" class="btn btn-secondary" on:click={irAIngredients}>Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
        <button form="form-ingredient-edit" class="btn btn-primary" type="submit">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
      </section>
    {:else}
      <p>Ingrediente no encontrado.</p>
    {/if}
  </section>

</section>