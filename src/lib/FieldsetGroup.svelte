<script lang="ts">
  import Input from "$lib/Input.svelte";

  // defino el tipado de los inputs (hay que hacerlo en input.svelte)
  type InputField = {
    label_for: string;
    label_text: string;
    input_placeholder: string;
    input_id: string;
  };

  // dejo tipada cada
  export let title: string;
  export let name: string;
  export let fields: InputField[];
</script>

<fieldset name={name} class="container-column content-section">
  {#if title}
    <h2 class="subtitle">{title}</h2>
  {/if}
  <!-- preguntar a pablo que opina sobre la clase stacked-inputs -->
  <div class={name === "store-info" ? "stacked-inputs" : "grid-cols-2 input-group-dir"}>
    {#each fields as field}
      <Input
        label_for={field.label_for}
        label_text={field.label_text}
        input_placeholder={field.input_placeholder}
        input_id={field.input_id}
      />
    {/each}
  </div>
</fieldset>