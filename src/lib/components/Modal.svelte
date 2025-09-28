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
  }

  let data: ModalProps = $props()
</script>

<div class="modal-backdrop">
  <div class="modal">
    <h2>{data.title}</h2>
    <section class="cell multiple-action-buttons modal-btns">
      <button class="btn btn-secondary btn-modal" onclick={data.actionCancel}>{data.cancelLabel}</button>
      <button class="btn btn-primary btn-modal" onclick={data.actionConfirm}>{data.confirmLabel}</button>
    </section>
  </div>
</div>

<style>
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
</style>