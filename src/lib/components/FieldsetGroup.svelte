<script lang="ts">
  import Input from "$lib/components/Input.svelte";
  import { InputTypes } from "./InputTypes";
  
  type InputField = {
    label_for: string;
    label_text: string;
    input_placeholder: string;
    input_id: string;
  };

  type FieldsetGroupProps = {
    title?: string;
    name: string;
    fields: InputField[];
  };
  let { title = "", name = "", fields = [] }: FieldsetGroupProps = $props();
</script>

<!-- quizas deberia sacar las clases de aca y ponerlas en el +page? -->
<fieldset {name} class="container-column content-section">
  {#if title}
    <h2 class="subtitle">{title}</h2>
  {/if}
  
  <div>
    {#each fields as field (field.input_id)}
    <!-- Te modifique esto perdon, pero estaba testing si era xq era una version vieja u otra cosa -->
      <Input
        description={field.label_text}
        input_type={InputTypes.Normal}
        labelProps={{
          for: field.label_for
        }}
        inputProps={{
          placeholder: field.input_placeholder,
          id: field.input_id
        }}
      />
    {/each}
  </div>
</fieldset>