<script>
  import Header from "$lib/Header.svelte";

  let { children } = $props();
</script>

<Header />
<!-- tiene que comenzar si o si con Mayuscula para que se tome como un componente de svelte -->

{@render children?.()}
