<script>
  import "$lib/css/pages-css/5-menu.css";
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/pages-css/5-menu.css";
  //   <link rel="stylesheet" href="../css/style.css" /> esto no hace falta pero no me acuerdo en donde se importa

  import MenuItem from "$lib/MenuItem.svelte";
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
      <MenuItem
        img="src/lib/assets/img/spagettis.png"
        alt="spagettis"
        nombre="Pasta con albondigas"
        descripcion="Deliciosa pasta con salsa de tomates y albondigas de cerdo"
        precio="$12.99"
      />

      <MenuItem
        img="src/lib/assets/img/hamburguesa2.jpg"
        alt="hamburguesa"
        nombre="Hamburguesa con queso y panceta"
        descripcion="Combo de hamburguesa con papas y bebida"
        precio="$9.99"
      />

      <MenuItem
        img="src/lib/assets/img/ensalada.png"
        alt="ensalada"
        nombre="Ensalada de la Huerta"
        descripcion="Ensalada fresca con hojas mixtas y vinagreta"
        precio="$7.50"
      />

      <MenuItem
        img="src/lib/assets/img/pizza.png"
        alt="pizza"
        nombre="Pizza con mozzarella y tomate"
        descripcion="Pizza a la piedra con salsa de tomates frescos y extra queso"
        precio="$11.75"
      />

      <MenuItem
        img="src/lib/assets/img/salmon.png"
        alt="salmon"
        nombre="Salmon con vegetales grillados"
        descripcion="Salmon fresco a la plancha acompañado de vegetales"
        precio="$14.25"
      />
    </div>
  </div>
</main>
