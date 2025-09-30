<script lang='ts'>
  import type { ValidationMessage } from '$lib/domain/validationMessage'
  // import Toaster from './toast/Toaster.svelte';
  import ToastContainer from './toast/ToastContainer.svelte';

  const errorsFrom = (errors: ValidationMessage[], field: string) => errors
    .filter((_) => _.field === field)
    .map((_) => _.message)
    .join('. ')

  let { field, errors } = $props()

  let errorMessage = $derived(errorsFrom(errors, field))
</script>

<!-- <style>
.error {
  background-color: #da8a8a;
  color: darkred;
  padding: 0.5em 0.5em;
  border-radius: 1em;
  border: 1px solid darkred;
  text-align: center;
  margin: 0.8em;
}
</style>

{#if !!errorMessage}
  <div class='error' data-testid={'error-field-' + field}>
    {errorMessage}
  </div>
{/if} -->
<ToastContainer errorMessage={errorMessage}  />
<!-- <Toaster errorMessage={errorMessage} field={field} /> -->