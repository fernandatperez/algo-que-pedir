<script lang="ts">
  export let id = "";
  export let name = "";
  export let value = "";
  export let label_text = "";
</script>

<!-- esta componentizacion esta pensada para los checkbox de store-profile-->
<label for={id} class="label-color">
  <input
    type="checkbox"
    id={id}
    name={name}
    value={value}
    class="payment-checkbox"
  />
  <span>{label_text}</span>
</label>