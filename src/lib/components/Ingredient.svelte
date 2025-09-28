<script lang='ts'>
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/components-css/grid-table.css";
  import "$lib/css/components-css/icon.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/pages-css/7-ingredients.css";

  import type { IngredientType } from '$lib/domain/ingredient';
  import { foodGroupDict } from '$lib/domain/ingredient';

  interface Props {
    ingredient: IngredientType
  }

  let { ingredient }: Props = $props()
  // const { id, nombre, costo, grupoAlimenticio, origenIcon }: IngredientType = $props() }
</script>

<section class="cell" id="nombre-{ingredient.id}">{ingredient.name}</section>
<section class="cell">${ingredient.cost}</section>
<section class="cell later-hid" id="grupo-alimenticio-{ingredient.id}">{ingredient.foodGroup}</section>
<section class="cell col-centered later-hid"><i class="ph {foodGroupDict[ingredient.foodGroup].icon} gray-icon" id="origen-{ingredient.id}"></i></section>
