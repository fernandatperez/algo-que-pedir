<script lang="ts">
    import type { MenuItemType } from "$lib/domain/menuItem"


  let { platos }: { platos: MenuItemType[] } = $props();
  console.log('Platos in GridRow:', platos);
</script>

<style>
  @import url("$lib/css/components-css/grid-table.css");

  /* MODIFICACIONES PARA MI TABLA */

  /* Cantidad de columnas */
  .grid-table-row {
    color: var(--border-light-color);
    grid-template-columns: 3fr 1fr 1fr; 
    color: var(--font-color-secondary);
  }
  .grid-table-row img {
    border-radius: 20%;
    width: 5em;
    height: auto;
    background-color: var(--background-color-alternate); 
    margin: 0em 1em 0em 0em;
  }
  
  /* texto de segunda columna */
  .table-content {
    font-weight: 500;
    color: var(--font-color-secondary);
    justify-content: space-between; 
    align-items: center;
    gap: 1em;
  }

  .cell:nth-child(2),
  .cell:nth-child(3) {
    text-align: center;
  }

  .product-cell {
    white-space: nowrap;
    overflow: hidden;
    padding: 2em 1em; 
    color: var(--font-color-secondary);
    font-weight: 450;
    display: flex;
    flex-direction: row;
  }

  .action-container {
    display: flex;
    justify-content: flex-end;
    width: 100%; /* !!! */
  }

  .action-container button {
      padding: 0.8em 1.6em; /* padding: top right bottom left; */
  }
  .action-container button:active {
      background-color: var(--button-active-color);
  }

</style>

<section class="grid-table-container product-edit-ingredients-table">
        <!-- Grid Header -->
        <header class="grid-table-row table-header">
          <div class="cell">Plato</div>
          <div class="cell">Cantidad</div>
          <div class="cell">Precio</div>
        </header>
        <!-- Grid Content  -->
        {#each platos as plato }
          <section class="grid-table-row table-content">
            <div class="cell product-cell ellipsis-text">
              <div class="imgcontainer">
                <img
                  src={plato.imagen}
                  alt={plato.nombre}
                  class="hide-on-mobile"
                />
              </div>
              <div class="text-wrapper">
                <h4 class="ellipsis-text">{plato.nombre}</h4>
                <p class="hide-on-mobile ellipsis-text">
                  {plato.descripcion}
                </p>
              </div>
            </div>
            <div class="cell">{1}</div>
            <div class="cell">${plato.costoProduccion.toFixed(2)}</div>
          </section>
        {/each}
</section>

