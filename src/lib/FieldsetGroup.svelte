<script lang="ts">
  import Input from "$lib/Input.svelte";
  
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
      <Input
        label_for={field.label_for}
        label_text={field.label_text}
        input_placeholder={field.input_placeholder}
        input_id={field.input_id}
      />
    {/each}
  </div>
</fieldset>