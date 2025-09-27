<script lang="ts">

  // Guarda la ruta activa
  let activeRoute = "orders";

  function setActive(route: string) {
    activeRoute = route;
  }

</script>

<!-- hay que cambiar las rutas despues -->
<nav class="options-and-icon">
  <a href="/orders" class="btn-empty {activeRoute === 'orders' ? 'active' : ''}"
    onclick={() => setActive('orders')}
  >Pedidos</a>
  <a href="/menu" class="btn-empty {activeRoute === 'menu' ? 'active' : ''}"
    onclick={() => setActive('menu')}
  >Menú</a>
  <a href="/ingredients" class="btn-empty {activeRoute === 'ingredients' ? 'active' : ''}"
    onclick={() => setActive('ingredients')}
  >ingredientes</a>
  <a href="/store-profile" class="btn-empty {activeRoute === 'store-profile' ? 'active' : ''}"
    onclick={() => setActive('store-profile')}
  >Cuentas</a>

  <!-- <i class="ph ph-list hidden"></i> -->
  <label id="nav-hamburger" class="hidden">
    <input type="checkbox" id="hamburger-toggle" />
    <span class="hidden"></span>
    <span class="hidden"></span>
    <span class="hidden"></span>
  </label>
  <a href="/store-profile">
    <i class="ph ph-user-circle user-img"></i>
  </a>
  <div class="mobile-icons">
    <i class="ph ph-shopping-cart hidden"></i>
    <i class="ph ph-fork-knife hidden"></i>
    <i class="ph ph-bowl-food hidden"></i>
    <i class="ph ph-user hidden"></i>
  </div>
</nav>

