<script lang="ts">
	import favicon from "$lib/assets/favicon.ico";
    import ToastContainer from "$lib/components/toast/ToastContainer.svelte";
	import "./style.css";

	let { children } = $props();
</script>

<svelte:head>
	<link rel="icon" href={favicon} />
</svelte:head>

<!-- Pasa 1 componente por $props() y genera el html -->
{@render children?.()}
<ToastContainer />
