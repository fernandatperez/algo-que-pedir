<script lang="ts">
  import "$lib/css/component-css/grid-table.css"

  let { plato } = $props();
</script>

<section class="grid-table-row product-edit-ingredients-table-content">
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
  <div class="cell">{"Falta attr"}</div>
  <div class="cell">${plato.precio.toFixed(2)}</div>
</section>
