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
    
    // Eventos
    onInput?: (section: string, field: string) => void;
    onBlur?: (section: string, field: string) => void;
    
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
    onInput,
    onBlur,
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
        onchange={onInput ? () => onInput(section, field) : undefined}
        onblur={onBlur ? () => onBlur(section, field) : undefined}
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
      oninput={onInput ? () => onInput(section, field) : undefined}
      onblur={onBlur ? () => onBlur(section, field) : undefined}
      {required}
      {min}
      {max}
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