<script lang="ts">
  // https://svelte.dev/playground/751b640049134b4683c3717bc4b4095c?version=5.39.6
  // no lo hice como en el ejemplo de arriba porque utiliza una libreria con los componentes hechos
  
  import type { MouseEventHandler } from "svelte/elements"

  interface ModalProps {
    // show: boolean
    title: string
    message?: string
    cancelLabel?: string
    confirmLabel?: string
    actionCancel: MouseEventHandler<HTMLButtonElement>
    actionConfirm: MouseEventHandler<HTMLButtonElement>
    children?: any // prop para contenido dinámico
  }

  let data: ModalProps = $props()
</script>

<div class="modal-backdrop">
  <div class="modal">
    <h2>{data.title}</h2>

    <!-- Si hay message, lo muestro -->
     <div class="modal-message">
       {#if data.message}
         <p>{@html data.message}</p> <!-- snippets -->
       {:else}
         <!-- Si no hay message, renderizar el snippet pasado -->
         {@render data.children?.()}
       {/if}
     </div>
    
    <section class="multiple-action-buttons modal-btns">
      <button class="btn btn-secondary btn-modal" onclick={data.actionCancel}>{data.cancelLabel}</button>
      <button class="btn btn-primary btn-modal" onclick={data.actionConfirm}>{data.confirmLabel}</button>
    </section>
  </div>
</div>

<style>
  @import url('$lib/css/components-css/buttons.css');
.modal-btns{
    gap: 1rem;
    margin: 1em;
}
.btn-modal{
    width: 100px;
}
.modal-backdrop {
    position: fixed;
    inset: 0;
    background: rgba(0,0,0,0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    gap: 1rem;
    margin: 0 auto;
}

.modal {
    text-align: center;
    background: white;
    padding: 1.5rem;
    border-radius: 0.5rem;
    max-width: 500px;
    width: 90%;
}
.modal-message {
  display: flex;
  flex-direction: column;
  align-items: center; /* opcional: alinea a la izquierda */
  margin-top: 1em;
}

.multiple-action-buttons {
    display: flex;
    justify-content: center;
}

</style>