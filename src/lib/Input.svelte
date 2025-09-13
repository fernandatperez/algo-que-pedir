<script lang="ts">
import "$lib/css/components-css/input.css";

let {
  label_for = "",
  label_text = "",
  input_type = "text",
  input_placeholder = "",
  input_id = ""
} = $props()

</script>

<label for={label_for} class="label-color">{label_text}</label>
<input
  type={input_type}
  placeholder={input_placeholder}
  id={input_id}
  class="input-primary"
/>