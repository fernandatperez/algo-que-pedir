<script lang='ts'>
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/component-css/grid-table.css";
  import "$lib/css/component-css/icon.css";
  import "$lib/css/component-css/buttons.css";
  import "$lib/css/pages-css/7-ingredients.css";

  import type { IngredientType } from '$lib/type/ingredient';

  interface Props {
    ingredient: IngredientType
  }

  const { ingredient }: Props = $props()
  // const { id, nombre, costo, grupoAlimenticio, origenIcon }: IngredientType = $props() }
</script>

<section class="cell" id="nombre-{ingredient.id}">{ingredient.name}</section>
<section class="cell">${ingredient.cost}</section>
<section class="cell later-hid" id="grupo-alimenticio-{ingredient.id}">{ingredient.foodGroup}</section>
<section class="cell col-centered later-hid"><i class="ph {ingredient.originIcon} gray-icon" id="origen-{ingredient.id}"></i></section>
