<script lang='ts'>
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/component-css/grid-table.css";
  import "$lib/css/component-css/icon.css";
  import "$lib/css/component-css/buttons.css";
  import "$lib/css/pages-css/7-ingredients.css";
  import { goto } from '$app/navigation';

  let { id, nombre, costo, grupoAlimenticio, origenIcon } = $props()

  function irAIngredientEdit(id: number) {
    goto(`/ingredient-edit/${id}`);
  }
</script>

<article class="grid-table-row product-edit-ingredients-table-content">
  <section class="cell" id="nombre-{id}">{nombre}</section>
  <section class="cell">{costo}</section>
  <section class="cell later-hid" id="grupo-alimenticio-{id}">{grupoAlimenticio}</section>
  <section class="cell col-centered later-hid"><i class="ph {origenIcon} gray-icon" id="origen-{id}"></i></section>
  <section class="cell multiple-action-buttons">
      <button disabled class="icon-action-btn hidden-icons"><i class="ph ph-eye gray-icon"></i></button>
      <span><i class="ph ph-line-vertical gray-icon hidden-icons"></i></span>
      <button class="icon-action-btn" onclick={() => irAIngredientEdit(id)}><i class="ph ph-pencil gray-icon"></i></button>
      <span><i class="ph ph-line-vertical gray-icon"></i></span>
      <button disabled class="icon-action-btn"><i class="ph ph-trash gray-icon" id="acciones-{id}"></i></button>
  </section>
</article>