<script lang="ts">
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/component-css/grid-table.css";
  import "$lib/css/component-css/icon.css";
  import "$lib/css/component-css/buttons.css";
  import "$lib/css/component-css/input.css";
  import "$lib/css/pages-css/7-ingredients.css";

  import Ingredient from "$lib/components/Ingredient.svelte";
  import { goto } from '$app/navigation';

  import { INGREDIENT_MOCK } from "$lib/data/mock/ingredients";
  import type { IngredientType } from "$lib/type/ingredient";
  import { createEmptyIngredient } from "$lib/type/ingredient";
  import { foodGroups, type FoodGroupValue } from '$lib/type/ingredient';

  // Valores reactivos $state()
  // https://svelte.dev/docs/svelte/$state
  let ingredients = $state<IngredientType[]>(INGREDIENT_MOCK);

  // Estado para mostrar/ocultar formulario
  let showForm = $state(false);

  // Creamos un ingrediente nuevo 
  // <IngredientType> → es el tipo que va a tener tu estado (newIngredient)
  // new IngredientType() → es el valor inicial que le pasás al estado
  let newIngredient = $state<IngredientType>(createEmptyIngredient());

  function validarFormulario(){
    // Validar que el nombre no esté vacío
    if (newIngredient.name.trim() === "") {
      alert("El nombre no puede estar vacío");
      return false;
    }
  
    // Validar que el costo sea un número positivo
    if (isNaN(newIngredient.cost) || newIngredient.cost <= 0) {
      alert("El costo debe ser un número mayor a 0");
      return false;
    }
  
    // Validar que se haya seleccionado un grupo alimenticio
    if (!foodGroups.some(grupo => grupo.value === newIngredient.foodGroup)) {
      alert("Debe seleccionar un grupo alimenticio válido.");
      return false;
    }
  
    return true;
  }
  
  function saveIngredient() {
    if (validarFormulario()){
      // Asigno un id al nuevo ingrediente
      newIngredient.id = ingredients.length + 1;
  
      // Asigno el icono correspondiente al grupo alimenticio seleccionado
      const selectedgroup = foodGroups.find(
        (grupo) => grupo.value === newIngredient.foodGroup,
      );
      newIngredient.originIcon = selectedgroup?.icon || "ph-question";
  
      // Agrego el newIngredient a la lista
      ingredients = [newIngredient, ...ingredients];
      showForm = false;
    }
  }

  function reset(){
    // Reset
    newIngredient = createEmptyIngredient();
    showForm = false;
  }

  // puedo usar $effect para que se guarde la informacion aunque recargue la pagina????
  // $effect(() => {
  // 	localStorage.setItem("ingredientes", JSON.stringify(ingredientes));
  // });

  function goToIngredientEdit(id: number) {
    goto(`/ingredient-edit/${id}`);
  }  

  function deleteIngredient(ingredient: IngredientType) {
    const ingIndex = ingredients.indexOf(ingredient);
    // Elimino un solo elemento desde el indice que le seteo
    ingredients.splice(ingIndex, 1);
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
          <form
            id="form-ingredient"
            class="grid-table-row product-edit-ingredients-table-content"
          >
            <section class="cell">
              <input
                class="input-primary"
                placeholder="Huevo"
                bind:value={newIngredient.name}
              />
              <!-- bind:value permite que fluyan en sentido inverso, de hijo a padre -->
            </section>
            <section class="cell">
              <input
                class="input-primary"
                placeholder="$0.80"
                bind:value={newIngredient.cost}
              />
            </section>
            <section class="cell">
              <select class="input-primary" bind:value={newIngredient.foodGroup}>
                <option value="" disabled selected hidden>Seleccionar</option>

                {#each foodGroups as grupo}
                  <option value={grupo.value}> {grupo.label} </option>
                {/each}

              </select>
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
              <button class="icon-action-btn" onclick={() => goToIngredientEdit(ing.id)} aria-label="Editar"><i class="ph ph-pencil gray-icon"></i></button>
              <span><i class="ph ph-line-vertical gray-icon"></i></span>
              <button class="icon-action-btn" onclick={() => deleteIngredient(ing)} aria-label="Eliminar"><i class="ph ph-trash gray-icon" id="acciones-{ing.id}"></i></button>
            </section>
          </article>
        {/each}

      </div>
    </section>
    {#if showForm}
      <section class="btn-group-actions btn-group-new-ingredient">
        <button form="form-ingredient" class="btn btn-secondary" onclick={() => (reset())}>Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
        <button form="form-ingredient" class="btn btn-primary" onclick={saveIngredient} type="submit">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
      </section>
    {/if}
  </section>
</section>
