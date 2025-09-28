<!-- src/lib/components/FormFieldset.svelte -->
<script lang="ts">
  import FormInput from '$lib/formInput.svelte'
  import type { ValidationMessage } from '$lib/domain/store';

  type InputType = 'text' | 'number'  | 'checkbox';

  interface FormField {
  field: string;
  label: string;
  type: 'text' | 'number' | 'checkbox'; 
  placeholder?: string;
  required?: boolean;
  minLength?: number;
  maxLength?: number;
}

  interface FormFieldsetProps {
    title?: string;
    name: string;
    fields: FormField[];
    formData: any;
    section: string;
    onInput?: (section: string, field: string) => void;
    onBlur?: (section: string, field: string) => void;
    errors: ValidationMessage[];
    layout?: 'grid-cols-2' | 'grid-cols-1' | 'grid-cols-3';
  }

  let {
    title = "",
    name,
    fields = [],
    formData = $bindable(),
    section,
    onInput,
    onBlur,
    errors,
    layout = 'grid-cols-2'
  }: FormFieldsetProps = $props();
</script>

<fieldset {name} class="container-column content-section form-section-store-commission">
  {#if title}
    <h2 class="subtitle">{title}</h2>
  {/if}
  
  <div class={layout}>
    {#each fields as fieldConfig (fieldConfig.field)}
      <FormInput
        bind:formData
        {section}
        field={fieldConfig.field}
        label={fieldConfig.label}
        type={fieldConfig.type}
        placeholder={fieldConfig.placeholder}
        required={fieldConfig.required}
        {onInput}
        {onBlur}
        {errors}
      />
    {/each}
  </div>
</fieldset>