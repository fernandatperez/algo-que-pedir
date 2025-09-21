<script>
  import "$lib/css/pages-css/5-menu.css";
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/pages-css/5-menu.css";
  //   <link rel="stylesheet" href="../css/style.css" /> esto no hace falta pero no me acuerdo en donde se importa

  import MenuItem from "$lib/MenuItem.svelte";

  import { MENUITEMS_MOCK } from "$lib/data/mock/menu-items";
  // import type { MenuItemType } from "$lib/type/menu-item";
  import { createEmptyMenuItem } from "$lib/type/menu-item";
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
        menuitem={{
          id: 1,
          nombre: "Pasta con albóndigas",
          descripcion:
            "Deliciosa pasta con salsa de tomates y albondigas de cerdo",
          precio: "$12.99",
          imagen: "spagettis.png",
          alt: "spagettis",
        }}
      />

      <MenuItem
        menuitem={{
          id: 2,
          nombre: "Hamburguesa con queso y panceta",
          descripcion: "Combo de hamburguesa con papas y bebida",
          precio: "$9.99",
          imagen: "hamburguesa2.jpg",
          alt: "hamburguesa",
        }}
      />

      <MenuItem
        menuitem={{
          id: 3,
          nombre: "Ensalada de la Huerta",
          descripcion: "Ensalada fresca con hojas mixtas y vinagreta",
          precio: "$7.50",
          imagen: "ensalada.png",
          alt: "ensalada",
        }}
      />

      <MenuItem
        menuitem={{
          id: 4,
          nombre: "Pizza con mozzarella y tomate",
          descripcion:
            "Pizza a la piedra con salsa de tomates frescos y extra queso",
          precio: "$11.75",
          imagen: "pizza.png",
          alt: "pizza",
        }}
      />

      <MenuItem
        menuitem={{
          id: 5,
          nombre: "Salmon con vegetales grillados",
          descripcion: "Salmon fresco a la plancha acompañado de vegetales",
          precio: "$14.25",
          imagen: "salmon.png",
          alt: "salmon",
        }}
      />
    </div>
  </div>
</main>
