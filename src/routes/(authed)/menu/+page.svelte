<script>
  import "$lib/css/pages-css/5-menu.css";
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/pages-css/5-menu.css";
  //   <link rel="stylesheet" href="../css/style.css" /> esto no hace falta pero no me acuerdo en donde se importa
</script>

<main class="container-column">
  <div class="main-container container-column main-content">
    <div class="w-100 flex-row jc-space-between gap-1 margin-2em">
      <div class="text-wrapper">
        <h1 class="header-title ellipsis-text">Gestion del menú</h1>
      </div>
      <button class="btn-add">Agregar nuevo plato</button>
    </div>
    <h2 class="subtitle">Platos disponibles</h2>
    <div class="container-column content-section">
      <article class="meal">
        <div class="imgcontainer">
          <img
            src="src/lib/assets/img/spagettis.png"
            alt="spagettis"
            class="image-meal"
          />
        </div>
        <div class="text-meal text-wrapper">
          <h4 class="ellipsis-text">Pasta con albondigas</h4>
          <p class="gray-text hide-on-mobile ellipsis-text">
            Deliciosa pasta con salsa de tomates y albondigas de cerdo
          </p>
        </div>
        <div class="price-meal">$12.99</div>
      </article>
      <article class="meal">
        <div class="imgcontainer">
          <img
            src="src/lib/assets/img/hamburguesa2.jpg"
            alt="hamburguesa"
            class="image-meal"
          />
        </div>
        <div class="text-meal text-wrapper">
          <h4 class="ellipsis-text">Hamburguesa con queso y panceta</h4>
          <p class="gray-text hide-on-mobile ellipsis-text">
            Combo de hamburguesa con papas y bebida
          </p>
        </div>
        <div class="price-meal">$9.99</div>
      </article>
      <article class="meal">
        <div class="imgcontainer">
          <img
            src="src/lib/assets/img/ensalada.png"
            alt="ensalada"
            class="image-meal"
          />
        </div>
        <div class="text-meal text-wrapper">
          <h4 class="ellipsis-text">Ensalada de la Huerta</h4>
          <p class="gray-text hide-on-mobile ellipsis-text">
            Ensalada fresca con hojas mixtas y vinagreta
          </p>
        </div>
        <div class="price-meal">$7.50</div>
      </article>
      <article class="meal">
        <div class="imgcontainer">
          <img
            src="src/lib/assets/img/pizza.png"
            alt="pizza"
            class="image-meal"
          />
        </div>
        <div class="text-meal text-wrapper">
          <h4 class="ellipsis-text">Pizza con mozzarella y tomate</h4>
          <p class="gray-text hide-on-mobile ellipsis-text">
            Pizza a la piedra con salsa de tomates frescos y extra queso
          </p>
        </div>
        <div class="price-meal">$11.75</div>
      </article>
      <article class="meal">
        <div class="imgcontainer">
          <img
            src="src/lib/assets/img/salmon.png"
            alt="salmon"
            class="image-meal"
          />
        </div>
        <div class="text-meal text-wrapper">
          <h4 class="ellipsis-text">Salmon con vegetales grillados</h4>
          <p class="gray-text hide-on-mobile ellipsis-text">
            Salmon fresco a la plancha acompañado de vegetales
          </p>
        </div>
        <div class="price-meal">$14.25</div>
      </article>
    </div>
  </div>
</main>
