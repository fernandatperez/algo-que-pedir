<script lang="ts">
  let { 
    name,
    label, 
    value = 'on',
    checked = false,
    class: className = ''
  } = $props();
</script>

<label class="checkbox-wrapper {className}">
  <input
    type="checkbox"
    {name}
    {value}
    {checked}
    class="checkbox-input"
  />
  <span class="checkbox-label">{label}</span>
</label>