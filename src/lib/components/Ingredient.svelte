<script lang='ts'>
  import type { IngredientType } from '$lib/domain/ingredient'

  interface Props {
    ingredient: IngredientType
  }

  let { ingredient }: Props = $props()
  // const { id, nombre, costo, grupoAlimenticio, origenIcon }: IngredientType = $props() }
</script>

<style>
  @import url('$lib/css/flex-grid.css');
  @import url("$lib/css/components-css/icon.css");
  @import url("$lib/css/components-css/buttons.css");
  @import url('$lib/css/components-css/grid-table.css');
  @import url("$lib/css/components-css/flex-table.css");
  @import url("$lib/css/pages-css/7-ingredients.css");
</style>

<section class="cell ellipsis-text" id="nombre-{ingredient.id}">{ingredient.name}</section>
<section class="cell">${ingredient.cost}</section>
<section class="cell later-hid" id="grupo-alimenticio-{ingredient.id}">{ingredient.foodGroup}</section>
{#if ingredient.foodGroup}
  <section class="cell col-centered later-hid">
    <i class={"ph " + ingredient.icono() + " gray-icon"} id={"origen-" + ingredient.id}></i>
  </section>
{:else}
  <section class="cell col-centered later-hid"><i class="ph ph-question gray-icon" id="origen-{ingredient.id}"></i></section>
{/if}
