<script lang="ts">
  import IconText from "$lib/IconText.svelte";
  import NavBar from "$lib/NavBar.svelte";
  import "./css/components-css/nav-bar.css";
  import "../routes/style.css";
</script>

<header class="main-nav">
  <IconText link="../" wrapperClass="icon-and-text" textClass="main-title" />
  <NavBar />
</header>
