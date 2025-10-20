<!-- muy importante poner ts -->
<script lang="ts">
  import MenuItem from '$lib/components/MenuItem.svelte';

  import type { MenuItemJSON, MenuItemJSONReduced, MenuItemType } from "$lib/domain/menuItem";
  import { goto } from '$app/navigation'

  import { menuItemsService } from "$lib/services/MenuItemService.js"
  import { showError } from "$lib/domain/errorHandler";
  import { onMount } from "svelte";

  let menuitems: MenuItemJSONReduced[]

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

<style>
  @import url("$lib/css/flex-grid.css");
  @import url("$lib/css/components-css/buttons.css");
  @import url("$lib/css/pages-css/5-menu.css");
</style>

<main class="container-column">
  <div class="main-container-menu container-column main-content">
    <div class="w-100 flex-row jc-space-between gap-1">
      <div class="text-wrapper">
        <h1 class="header-title-menu ellipsis-text">Gestion del menú</h1>
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
