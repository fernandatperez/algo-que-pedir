<script>
  import { onMount } from "svelte";
  import Header from "$lib/components/Header.svelte";

  let { children } = $props();
  let isReady = $state(false);

  onMount(() => {
    const user = sessionStorage.getItem('userName'); // se fija si le le llega algo de el back, usar token?
    if (!user && window.location.pathname !== '/login') {
      window.location.replace('/login'); // redirige porque si no se queda muerto
    } else {
      isReady = true;
    }
  });

</script>

{#if isReady}
  <Header /> <!-- tiene que comenzar si o si con Mayuscula para que se tome como un componente de svelte -->
  {@render children?.()}
{/if}