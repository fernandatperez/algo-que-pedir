<script lang="ts">
  export let disabled = true;
</script>

<!-- esta componentizacion esta pensada para los botones de accion guardar/descartar cambios-->
<section class="btn-group-actions">
  <button disabled={disabled} class="btn btn-secondary btn-store">
    Descartar <span class="p-cambios display-none-mobile">Cambios</span>
  </button>
  <button disabled={disabled} class="btn btn-primary btn-store">
    Guardar <span class="p-cambios display-none-mobile">Cambios</span>
  </button>
</section>