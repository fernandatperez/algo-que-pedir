<script lang="ts">
  export let label_for = "";
  export let label_text = "";
  export let input_type = "text";
  export let input_placeholder = "";
  export let input_id = "";
</script>

<!-- esta componentizacion esta pensada para los input de store-profile, es lo mismo que input.svelte 
 pero tiene las clases, lo hice aca para no tocar el otro codigo-->
<div class="container-column input-group">
  <label for={label_for} class="label-color">{label_text}</label>
  <input
    type={input_type}
    placeholder={input_placeholder}
    id={input_id}
    class="input-primary"
  />
</div>