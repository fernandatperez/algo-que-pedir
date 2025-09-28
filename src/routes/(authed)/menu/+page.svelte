<!-- muy importante poner ts -->
<script lang="ts">
  import "$lib/css/pages-css/5-menu.css";
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/pages-css/5-menu.css";
  //   <link rel="stylesheet" href="../css/style.css" /> esto no hace falta pero no me acuerdo en donde se importa
  import MenuItem from '$lib/components/MenuItem.svelte';

  import { MENUITEMS_MOCK } from "$lib/data/mock/menuItems";
  import type { MenuItemType } from "$lib/domain/menuItem";
  import { createEmptyMenuItem } from "$lib/domain/menuItem"; // esta funcion crea un objeto vacio, es para el boton agregar nuevo objeto
  import { goto } from '$app/navigation'


  let menuitems = $state<MenuItemType[]>(MENUITEMS_MOCK);






</script>

<main class="container-column">
  <div class="main-container container-column main-content">
    <div class="w-100 flex-row jc-space-between gap-1 margin-2em">
      <div class="text-wrapper">
        <h1 class="header-title ellipsis-text">Gestion del menú</h1>
      </div>
      <button class="btn-add" onclick={() => goto (`/dish-edit/-1`)} >Agregar nuevo plato</button>
      <!-- en este boton se podria usar -->
    </div>
    <h2 class="subtitle">Platos disponibles</h2>
    <div class="container-column content-section">
      <!-- menuitems es la lista, menuitem es la const en el componente -->
      {#each menuitems as item}
        <MenuItem menuitem={item} />
      {/each}
    </div>
  </div>
</main>
