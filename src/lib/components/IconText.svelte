<script lang="ts">
  let {
    title = "Algo que pedir",
    link = null,
    iconClass = "ph-fill ph-cooking-pot",
    containerClass = "pot-container",
    textClass = "title-header",
    wrapperClass = "",
  }: {
    title?: string;  
    link?: string | null;
    iconClass?: string;
    containerClass?: string;
    textClass?: string;
    wrapperClass?: string;
  } = $props();
</script> <!-- si le pones ? significa que no es obligatorio que se pase y usa los default -->
<!-- let { name, age }: { name: string; age: number } = person; -->

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
