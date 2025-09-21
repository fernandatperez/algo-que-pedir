<script lang="ts">
  let { img, alt, nombre, descripcion, precio } = $props();
</script>

<article class="meal">
  <div class="imgcontainer">
    <img src={img} {alt} class="image-meal" />
  </div>
  <div class="text-meal text-wrapper">
    <h4 class="ellipsis-text">{nombre}</h4>
    <p class="gray-text hide-on-mobile ellipsis-text">
      {descripcion}
    </p>
  </div>
  <div class="price-meal">{precio}</div>
</article>
