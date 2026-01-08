<script lang="ts">
</script>

<div class="gif-container">
  <h1>Wooops, something went wrong</h1>
  <img
    src="/src/lib/assets/img/Vídeo sin título.gif"
    alt="404-page-not-found"
    class="blend"
  />
  <h4>Dont worry, you can go back where its still safe</h4>
  <a href="/orders" class="back-home">home</a>
</div>

<style>
  .gif-container {
    display: flex;
    flex-direction: column;
    gap: 5rem;
    justify-content: center;
    height: 100vh;
    align-items: center;
  }

  .blend {
    max-width: 400px;
    border-radius: 20px; /* esquinas suaves */
    box-shadow: 0 0 60px rgba(0, 150, 255, 0.5); /* halo difuminado */
  }

  .back-home {
    display: flex;
    justify-content: center;
    background-color: rgba(36, 39, 216, 0.911);
    border-radius: 20%;
    width: 100px;
    padding: 1em;
    font-size: 1.5em;
  }

  a {
    color: rgb(255, 255, 255);
  }
</style>
