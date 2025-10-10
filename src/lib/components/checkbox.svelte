<script>
  let { 
    name,
    label, 
    value = 'on',
    checked = false,
    class: className = ''
  } = $props();
  
  let isChecked = $state(checked);
</script>

<label class="checkbox-wrapper {className}">
  <input
    type="checkbox"
    {name}
    {value}
    checked={isChecked}
    onchange={() => isChecked = !isChecked}
    class="checkbox-input"
  />
  
  
  <span class="checkbox-label">{label}</span>
</label>
