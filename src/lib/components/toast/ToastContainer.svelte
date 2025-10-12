<script lang="ts">
  import { toasts } from './toastStore'
  import { fade, fly } from 'svelte/transition'

</script>

<div class="toast-container">
  {#each $toasts as toast (toast.id)}
    <div
      class="toast {toast.type}"
      in:fly={{ x: 200, duration: 200 }}
      out:fade={{ duration: 200 }}>
        {toast.message}
      </div>
    {/each}
  </div>

<style>
.toast-container {
  position: fixed;
  top: 5em;
  right: 1em;
  margin-top: 1em;
  margin-left: 1em;
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
  gap: 0.5rem;
  z-index: 1000;
}

.toast {
  width: 100%;
  text-align: center;
  padding: 1rem 1.25rem;
  border-radius: 8px;
  color: white;
  font-size: var(--font-h4);
  font-weight: 500;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  animation: slide-in 0.3s ease-out;
}

/* Colores por tipo */
.toast.success {
  background-color: #16a34a;
}

.toast.error {
  background-color: #dc2626;
}

.toast.info {
  background-color: var(--alternative-color);
}

@keyframes slide-in {
  from {
    opacity: 0;
    transform: translateX(50px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}
</style>
