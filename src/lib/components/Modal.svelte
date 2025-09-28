<script lang="ts">
  // https://svelte.dev/playground/751b640049134b4683c3717bc4b4095c?version=5.39.6
  import { Modal, Content, Trigger } from "sv-popup";
  export let onConfirm: () => void = () => {};
  let open = false; // estado para abrir/cerrar el modal
</script>

<Modal basic small={true} bind:open>
  <Content>
    <h3>Estas seguro de que queres eliminar el ingrediente?</h3>
  </Content>

  <Trigger>
    <div class="modal-actions" style="display:flex; gap:0.5rem; justify-content:flex-end;">
      <button class="btn btn-secondary" on:click={() => (open = false)}>Cancelar</button>
      <button class="btn btn-primary" on:click={() => { onConfirm(); open = false }}>Confirmar</button>
    </div>
  </Trigger>
</Modal>

