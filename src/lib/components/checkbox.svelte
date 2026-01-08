<script lang="ts">
    import type { HTMLInputAttributes } from "svelte/elements";


interface Check extends HTMLInputAttributes{
    label_text: string
}

  let { 
    label_text,
    ...rest
  }:Check = $props();
</script>

<label class="checkbox-wrapper">
  <input
    type="checkbox"
    class="checkbox-input"
    {...rest}
  />
  <span class="checkbox-label">{label_text}</span>
</label>