<script lang="ts">
  import "./css/components-css/nav-bar.css";
</script>

<header class="main-nav">
  <div class="icon-and-text">
    <a href="../" class="pot-container" aria-label="Mark as complete">
      <i class="ph-fill ph-cooking-pot"></i>
    </a>
    <a href="../">
      <span class="main-title">Algo que pedir</span>
    </a>
  </div>

  <nav class="options-and-icon">
    <button class="btn-empty active">Pedidos</button>
    <button class="btn-empty">Menú</button>
    <button class="btn-empty">Ingredientes</button>
    <button class="btn-empty">Cuentas</button>
    <!-- <i class="ph ph-list hidden"></i> -->
    <label id="nav-hamburger" class="hidden">
      <input type="checkbox" id="hamburger-toggle" />
      <span class="hidden"></span>
      <span class="hidden"></span>
      <span class="hidden"></span>
    </label>

    <a href="/store-profile" class="icon-link" aria-label="perfil de la tienda">
       <i class="ph ph-user-circle user-img"></i>
    </a>
    
    <div class="mobile-icons">
      <i class="ph ph-shopping-cart hidden"></i>
      <i class="ph ph-fork-knife hidden"></i>
      <i class="ph ph-bowl-food hidden"></i>
      <i class="ph ph-user hidden"></i>
    </div>
  </nav>
</header>
