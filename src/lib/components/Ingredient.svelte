<script lang='ts'>
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/component-css/grid-table.css";
  import "$lib/css/component-css/icon.css";
  import "$lib/css/component-css/buttons.css";
  import "$lib/css/pages-css/7-ingredients.css";
  import { goto } from '$app/navigation';

  import type { IngredientType } from '$lib/type/ingredient';

  interface Props {
    ingredient: IngredientType
  }

  const { ingredient }: Props = $props()
  // const { id, nombre, costo, grupoAlimenticio, origenIcon }: IngredientType = $props() }

  function irAIngredientEdit(id: number) {
    goto(`/ingredient-edit/${id}`);
  }  
</script>

<article class="grid-table-row product-edit-ingredients-table-content">
  <section class="cell" id="nombre-{ingredient.id}">{ingredient.name}</section>
  <section class="cell">${ingredient.cost}</section>
  <section class="cell later-hid" id="grupo-alimenticio-{ingredient.id}">{ingredient.foodGroup}</section>
  <section class="cell col-centered later-hid"><i class="ph {ingredient.originIcon} gray-icon" id="origen-{ingredient.id}"></i></section>
  <section class="cell multiple-action-buttons">
      <button disabled class="icon-action-btn hidden-icons" aria-label="Ver"><i class="ph ph-eye gray-icon"></i></button>
      <span><i class="ph ph-line-vertical gray-icon hidden-icons"></i></span>
      <button class="icon-action-btn" onclick={() => irAIngredientEdit(ingredient.id)} aria-label="Editar"><i class="ph ph-pencil gray-icon"></i></button>
      <span><i class="ph ph-line-vertical gray-icon"></i></span>
      <button class="icon-action-btn" aria-label="Eliminar"><i class="ph ph-trash gray-icon" id="acciones-{ingredient.id}"></i></button>
  </section>
</article>