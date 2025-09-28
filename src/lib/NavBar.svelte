<script lang="ts">
  import "./css/components-css/navBar.css";
  // Guarda la ruta activa
  let activeRoute = "orders";

  function setActive(route: string) {
    activeRoute = route;
  }

  const routes = [
    { path: "/orders", label: "Pedidos", key: "orders" },
    { path: "/menu", label: "Menú", key: "menu" },
    { path: "/ingredients", label: "Ingredientes", key: "ingredients" },
    { path: "/store-profile", label: "Cuentas", key: "store-profile" }
  ];
</script>

<!-- hay que cambiar las rutas despues -->
<nav class="options-and-icon">
  {#each routes as route}
    <a 
      href={route.path} 
      class="btn-empty {activeRoute === route.key ? 'active' : ''}"
      on:click={() => setActive(route.key)}
    >
      {route.label}
    </a>
  {/each}

<div class="dropdown">
  <!-- <i class="ph ph-list hidden"></i> -->
  <label id="nav-hamburger" class="hidden">
    <input type="checkbox" id="hamburger-toggle" />
    <span class="hidden"></span>
    <span class="hidden"></span>
    <span class="hidden"></span>
    <!-- Dropdown dinámico -->
    <ul class="dropdown-menu">
      {#each routes as route}
      <li>
        <a href={route.path} on:click={() => setActive(route.key)}>
          {route.label}
        </a>
      </li>
      {/each}
    </ul>
  </label>
</div>

  <a class="icono-perfil" href="/store-profile" aria-label="ir a perfil"
    on:click={() => setActive("store-profile")}>
    <i class="ph ph-user-circle user-img" aria-label="icono perfil"></i>
  </a>

  <div class="mobile-icons">
    <a href="/orders" aria-label="ir a orders"
      on:click={() => setActive("orders")}>
      <i class="ph ph-shopping-cart hidden"></i>
    </a>
    <a href="/menu" aria-label="ir a menu"
      on:click={() => setActive("menu")}>
      <i class="ph ph-fork-knife hidden"></i>
    </a>
    <a href="/ingredients" aria-label="ir a ingredientes"
      on:click={() => setActive("ingredients")}>
      <i class="ph ph-bowl-food hidden"></i>
    </a>
    <a href="/store-profile" aria-label="ir a perfil"
      on:click={() => setActive("store-profile")}>
      <i class="ph ph-user hidden"></i>
    </a>
  </div>
</nav>


<style>
  /* si esto lo pongo en nav-bars.css no funciona no se porque */
  .dropdown {
    position: relative;
    display: inline-block;
  }

  .dropdown-menu {
    list-style: none;
    margin: 0;
    padding: 0.5rem 0;
    position: absolute;
    top: 120%;
    left: 0;
    background: #fff;
    border: 1px solid #ccc;
    border-radius: 5px;
    display: none;
    width: 9rem;
    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
  }

  .dropdown-menu li a {
    display: block;
    padding: 0.5rem 1rem;
    color: #333;
    text-decoration: none;
  }

  .dropdown-menu li a:hover {
    background: #f0f0f0;
  }
  #hamburger-toggle:checked ~ .dropdown-menu {
    display: block;
  }

  /* ================================= */
/* Desktop (>=769px) */
@media (min-width: 769px) {
  .dropdown {
    display: none;
  }
}

/* Tablet (431px – 768px) */
@media (min-width: 431px) and (max-width: 768px) {
  .dropdown {
    display: inline-block;
  }
}

/* Mobile (<=430px) */
@media (max-width: 430px) {
  .dropdown {
    display: none;
  }
  .icono-perfil {
    display: none;
  }
}
</style>
