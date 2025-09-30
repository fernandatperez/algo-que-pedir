<script lang="ts">
  import { toasts } from './toastStore'
  import { fade, fly } from 'svelte/transition'

  // console.log(toasts)
  const {errorMessage} = $props()
</script>

<div class="toast-container">
  {#each $toasts as toast (toast.id)}
    {#if toast.message == errorMessage}
      <div
        class="toast {toast.type}"
        in:fly={{ x: 200, duration: 200 }}
        out:fade={{ duration: 200 }}>
          {toast.message}
        </div>
    {/if}
  {/each}
</div>

<style>
.toast-container {
  margin-top: 1em;
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
  padding: 0.75rem 1rem;
  border-radius: 8px;
  color: white;
  font-size: 1.2em;
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
  background-color: #2563eb;
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

@keyframes slide-out {
  from {
    opacity: 1;
    transform: translateX(0);
  }
  to {
    opacity: 0;
    transform: translateX(50px);
  }
}
</style>
