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
  import { MENU_ITEMS_JSON_MOCK } from "$lib/data/mock/menuItems";
  import type { MenuItemType } from "$lib/domain/menuItem";
  import type { MenuItemJSON } from "$lib/domain/menuItem";
  import { createEmptyMenuItem } from "$lib/domain/menuItem"; // esta funcion crea un objeto vacio, es para el boton agregar nuevo objeto
  import { goto } from '$app/navigation'

  import { menuItemsService } from "$lib/services/MenuItemService.js"
    import { showError } from "$lib/domain/errorHandler";
    import { onMount } from "svelte";

  let menuitems = $state<MenuItemJSON[]>([]);

    const findMenuItems = async () => {
    try{
      menuitems = await menuItemsService.getAllMenuItems()
    } catch (error){
      showError('Conexion al servidor fallida', error)
    }
  }

  const crearPlato = () => {
    goto('/dish-edit/nuevoPlato')
  }

  onMount(findMenuItems)


</script>

<main class="container-column">
  <div class="main-container container-column main-content">
    <div class="w-100 flex-row jc-space-between gap-1 margin-2em">
      <div class="text-wrapper">
        <h1 class="header-title ellipsis-text">Gestion del menú</h1>
      </div>
      <button class="btn-add" onclick={crearPlato} >Agregar nuevo plato</button>
      <!-- en este boton se podria usar -->
    </div>
    <h2 class="subtitle">Platos disponibles</h2>
    <div class="container-column content-section">
      <!-- menuitems es la lista, menuitem es la const en el componente -->
      {#each menuitems as item (item.id)}
        <MenuItem menuitem={item} />
      {/each}
    </div>
  </div>
</main>
