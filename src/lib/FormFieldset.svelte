<!-- En FormFieldset.svelte - versión sin TypeScript estricto -->
<script lang="ts">
  import FormInput from '$lib/FormInput.svelte'

  // ✅ Sin tipos, solo JavaScript
  let { 
    title = "",
    name,
    fields = [],
    formData= $bindable(),
    section,
    errors,
  } = $props()

   let isStoreInfo = $derived(section === 'storeInfo')
</script>

{#if isStoreInfo}
  <fieldset form="form-store-profile" name={name} class="content-section form-section-store-dir">
    <div class="grid-cols-2 input-field" >
      {#each fields as fieldConfig (fieldConfig.field)}
        <div class="input-field">
          <FormInput
            bind:formData
            {section}
            field={fieldConfig.field}
            label={fieldConfig.label}
            type={fieldConfig.type}
            placeholder={fieldConfig.placeholder}
            required={fieldConfig.required}
            {errors}
          />
        </div>
      {/each}
    </div>
    <div>
     <img
            src={formData.storeInfo["url-store-img"] || "/src/lib/assets/img/CarlosBakeShop.jpg"}
            alt="local"
            class="img-store-profile"
          /></div>
  </fieldset>
{:else}
  <fieldset {name} class="container-column content-section form-section-store-commission">
    {#if title}
      <h2 class="subtitle">{title}</h2>
    {/if}
    
    <div class={ 'grid-cols-2 input-group-dir'}>
      {#each fields as fieldConfig (fieldConfig.field)}
        <FormInput
          bind:formData
          {section}
          field={fieldConfig.field}
          label={fieldConfig.label}
          type={fieldConfig.type}
          placeholder={fieldConfig.placeholder}
          required={fieldConfig.required}
          {errors}
        />
      {/each}
    </div>
  </fieldset>
{/if}