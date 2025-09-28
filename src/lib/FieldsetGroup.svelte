<!-- src/lib/components/FieldsetGroup.svelte -->
<script lang="ts">
  import Input from "$lib/Input.svelte";
  import type { ValidationMessage } from '$lib/domain/store';
  
  type InputField = {
    label_for: string;
    label_text: string;
    input_placeholder: string;
    input_id: string;
    type?: string;
    required?: boolean;
  };

  type FieldsetGroupProps = {
    title?: string;
    name: string;
    fields: InputField[];
    formData: any;
    section: string;
    onInput: (section: string, fieldId: string) => void;
    onBlur: (section: string, fieldId: string) => void;
    errors: ValidationMessage[];
  };

  let { 
    title = "", 
    name = "", 
    fields = [], 
    formData,
    section,
    onInput,
    onBlur,
    errors = []
  }: FieldsetGroupProps = $props();
</script>

<fieldset {name} class="container-column content-section form-section-store-commission">
  {#if title}
    <h2 class="subtitle">{title}</h2>
  {/if}
  
  <div class="grid-cols-2 input-group-dir">
    {#each fields as field (field.input_id)}
      <div class="input-field">
        <Input
          {formData}
          {section}
          {field}
          {onInput}
          {onBlur}
          {errors}
        />
      </div>
    {/each}
  </div>
</fieldset>