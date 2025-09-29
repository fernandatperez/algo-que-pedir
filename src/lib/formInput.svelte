<!-- src/lib/components/FormInput.svelte -->
<script lang="ts">
  import "$lib/css/components-css/input.css";
  import ValidationField from '$lib/components/ValidationField.svelte';
  import type { ValidationMessage } from '$lib/domain/store';

  interface FormInputProps {
    // Conexión automática con formData
    formData: any;
    section: string;
    field: string;
    
    // Configuración
    label: string;
    type?: 'text' | 'number' | 'url' | 'checkbox';
    placeholder?: string;
    required?: boolean;
    min?: number;
    max?: number;
    
    
    // Validación
    errors: ValidationMessage[];
  }

  let {
    formData= $bindable(),
    section,
    field,
    label,
    type = 'text',
    placeholder = '',
    required = false,
    min,
    max,
    errors
  }: FormInputProps = $props();

  // Valor reactivo automático
  let value = $derived(formData[section][field]);
</script>

<div class="input-field">
  {#if type !== 'checkbox'}
    <label for={field} class="label-color">
      {label}
      {#if required}
        <span class="required-asterisk">*</span>
      {/if}
    </label>
  {/if}

  {#if type === 'checkbox'}
    <label for={field} class="label-color checkbox-label">
      <input
        id={field}
        name={field}
        type="checkbox"
        bind:checked={formData[section][field]}
      />
      <span>{label}</span>
    </label>
  {:else}
    <input
      id={field}
      name={field}
      type={type}
      placeholder={placeholder}
      class="input"
      bind:value={formData[section][field]}
      {required}
      {min}
      {max}
      formnovalidate
    />
  {/if}

  <ValidationField {errors} {field} />
</div>

<style>
  .checkbox-label {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    cursor: pointer;
  }
  
  .checkbox-label input[type="checkbox"] {
    margin: 0;
  }
  
  .required-asterisk {
    color: #e53e3e;
    margin-left: 2px;
  }
</style>