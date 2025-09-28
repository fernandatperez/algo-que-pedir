<script lang='ts'>
  type AppValidationMessage = import('$lib/domain/ingredient').ValidationMessage | import('$lib/domain/store').ValidationMessage

  const errorsFrom = (errors: AppValidationMessage[], field: string) => errors
    .filter((_) => _.field === field)
    .map((_) => _.message)
    .join('. ')

  let { field, errors } = $props()

  let errorMessage = $derived(errorsFrom(errors, field))
</script>
<style>
.error {
  background-color: #da8a8a;
  color: darkred;
  padding: 0.5em 1.5em;
  border-radius: 1em;
  border: 1px solid darkred;
  text-align: center;
  margin: 0.2em;
}
</style>
{#if !!errorMessage}
  <div class='error' data-testid={'error-field-' + field}>
    {errorMessage}
  </div>
{/if}