<script>
  import Navbar from "$lib/Nav-bar.svelte";

  let { children } = $props();
</script>

<Navbar />
<!-- tiene que comenzar si o si con Mayuscula para que se tome como un componente de svelte -->

{@render children?.()}
