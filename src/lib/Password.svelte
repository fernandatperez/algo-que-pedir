<script lang="ts">
import "$lib/css/components-css/input.css";

let {
  label_for = "",
  label_text = "",
  password_type = "text",
  password_placeholder = "",
  password_id = ""
} = $props()

</script>

<div>
  <label for={label_for} class="label-color">{label_text}</label>
  <div class="input-with-icon">
    <input
      type={password_type}
      id={password_id}
      class="input-primary input-password"
      placeholder={password_placeholder}
    />
    <i class="ph ph-eye-slash input-icon"></i>
  </div>
</div>