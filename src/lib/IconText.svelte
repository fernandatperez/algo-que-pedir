<script>
  let {
    title = "Algo que pedir",
    link = null,
    iconClass = "ph-fill ph-cooking-pot",
    containerClass = "pot-container",
    textClass = "title-header",
    wrapperClass = "",
  } = $props();
</script>

<div class={wrapperClass}>
  {#if link}
    <a class={containerClass} href={link} aria-label={title}>
      <i class={iconClass}></i>
    </a>
  {:else}
    <div class={containerClass}>
      <i class={iconClass}></i>
    </div>
  {/if}

  {#if link}
    <a href={link} class={textClass}>{title}</a>
  {:else}
    <span class={textClass}>{title}</span>
  {/if}
</div>
