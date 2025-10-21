<script lang="ts">
  import "$lib/css/components-css/navBar.css"
  // Guarda la ruta activa
  import { page } from "$app/state"
  
  let activeRoute = "orders"

  function setActive(route: string) {
    activeRoute = route;
  }

  const routes = [
    { path: "/orders", label: "Pedidos", key: "orders", icon: "ph-shopping-cart" },
    { path: "/menu", label: "Menú", key: "menu", icon: "ph-fork-knife" },
    { path: "/ingredients", label: "Ingredientes", key: "ingredients", icon: "ph-bowl-food" },
    { path: "/store-profile", label: "Cuentas", key: "store-profile", icon: "ph-user", specialIcon: "ph-user-circle" }
  ]

  const isActive = (path: string) => {
    return path === '/'
      ? page.url.pathname === '/'
      : page.url.pathname === path || page.url.pathname.startsWith(`${path}/`)
  }
</script>

<!-- hay que cambiar las rutas despues -->
<nav class="options-and-icon">
  {#each routes as route}
    <a 
    class:active={isActive(route.path)} class="btn-empty"
    aria-current={page.url.pathname === route.path ? 'page' : undefined}
    href={route.path}>{route.label}
    </a>
  {/each}

  <!-- Texto del link activo (solo tablet) -->
  <!-- <span class="activeLink">
    {#each routes as route}
      {#if isActive(route.path)}
        <a class:active={isActive(route.path)} href={route.path}>
          {route.label}
        </a>
      {/if}
    {/each}
  </span> -->

<div class="dropdown">
  <!-- <i class="ph ph-list hidden"></i> -->
  <label id="nav-hamburger" class="hidden">
    <input type="checkbox" id="hamburger-toggle" />
    <span></span>
    <span></span>
    <span></span>
    <!-- Dropdown dinámico -->
    <ul class="dropdown-menu">
      {#each routes as route}
      <li>
        <!-- <a href={route.path} onclick={() => setActive(route.key)}>
          {route.label}
        </a> -->
        <a 
          class:active={isActive(route.path)}
          aria-current={page.url.pathname === route.path ? 'page' : undefined}
          href={route.path}>{route.label}
        </a>
        </li>
      {/each}
    </ul>
  </label>
</div>
<a class="icono-perfil" href="/store-profile" aria-label="ir a perfil"
  onclick={() => setActive("store-profile")}>
  <i class="ph ph-user-circle user-img" aria-label="icono perfil"></i>
</a>


  <div class="mobile-icons">
    {#each routes as route}
      <a 
        href={route.path} 
        aria-label={`ir a ${route.key}`}
        class:active={isActive(route.path)}>
        <i class={`ph ${route.icon} hidden`}></i>
      </a>
    {/each}
    <!-- <a href="/orders" aria-label="ir a orders"
      onclick={() => setActive("orders")} class:active={isActive('/orders')}>
      <i class="ph ph-shopping-cart hidden"></i>
    </a>
    <a href="/menu" aria-label="ir a menu"
      onclick={() => setActive("menu")} class:active={isActive('/menu')}>
      <i class="ph ph-fork-knife hidden"></i>
    </a>
    <a href="/ingredients" aria-label="ir a ingredientes"
      onclick={() => setActive("ingredients")} class:active={isActive('/ingredients')}>
      <i class="ph ph-bowl-food hidden"></i>
    </a>
    <a href="/store-profile" aria-label="ir a perfil"
      onclick={() => setActive("store-profile")} class:active={isActive('/store-profile')}>
      <i class="ph ph-user hidden"></i>
    </a> -->
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

  .activeLink{
    display: none;
  }

  /* ================================= */
/* Desktop (>=769px) */
@media (min-width: 769px) {
  .options-and-icon a {
  display: inline-flex;
  }
  .dropdown {
    display: none;
  }
  .icono-perfil {
    display: inline-flex;
  }
}

/* Tablet (431px – 768px) */
@media (min-width: 431px) and (max-width: 768px) {
  .options-and-icon > a {
    display: none; /* oculto links de texto */
  }
  .dropdown {
    display: inline-block;
  }
  .options-and-icon .icono-perfil,
  .options-and-icon .activeLink {
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
  .options-and-icon > a {
    display: none;
  }
  .mobile-icons{
    display: inline-flex; 
  }
  .hidden{
    display: inline-flex;
  }
}
</style>
