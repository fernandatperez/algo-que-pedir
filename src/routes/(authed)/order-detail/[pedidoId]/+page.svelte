<script>
  import "$lib/css/style.css"
  import "$lib/css/fonts.css"
  import "$lib/css/nav-bars.css"
  import "$lib/css/flex-grid.css"
  import "$lib/css/component-css/user-details.css"
  import "$lib/css/component-css/buttons.css"
  import "$lib/css/component-css/grid-table.css"
  import "$lib/css/pages-css/4-order-details.css"

  import hamburguesaImg from "$lib/assets/img/hamburguesa.png"
  import pizzaImg from "$lib/assets/img/pizza.png"
  import ensaladaImg from "$lib/assets/img/ensalada.png"

  import GridRow from "$lib/components/GridRow.svelte"

  // 'page' is deprecated dice
  import { page } from "$app/stores";
  // import { page } from "$app/state";

  // id del pedido para hacer fetch/find() del pedido
  const pedidoId = $page.params.pedidoId;


  let pedido = $state({
    pedidoId: 12345,
    cliente: {
      nombre: "Sofía Miller",
      usuario: "smiller2006",
      direccion: {
        calle: "Av siempre viva 555",
        latitud: 40.7128,
        longitud: -74.006
      },
    },
    local: {
      nombre: "La buena comida",
      direccion: {
        calle: "Calle Falsa 123",
        latitud: 40.73061,
        longitud: -73.935242
      }
    },
    platos: [
      {
        nombre: "Hamburguesa con queso",
        descripcion: "Hamburguesa con queso acompañada de papas fritas y bebida",
        cantidad: 1,
        precio: 12.00,
        imagen: hamburguesaImg
      },
      {
        nombre: "Pizza",
        descripcion: "De muzzarella con tomate y albahaca",
        cantidad: 1,
        precio: 16.00,
        imagen: pizzaImg
      },
      {
        nombre: "Ensalada",
        descripcion: "De hojas frescas y vegetales organicos",
        cantidad: 1,
        precio: 10.00,
        imagen: ensaladaImg
      }
    ],
    estado: "Pendiente",
    cupon: null,
    pago: "Tarjeta de crédito"
  })
  
  // "fetch" del pedido
  // Esto sera un objeto de TS
  // pedido = {
  //   pedidoId: 12345,
  //   cliente: {
  //     nombre: "Sofía Miller",
  //     usuario: "smiller2006",
  //     direccion: {
  //       calle: "Av siempre viva 555",
  //       latitud: 40.7128,
  //       longitud: -74.006
  //     },
  //   },
  //   local: {
  //     nombre: "La buena comida",
  //     direccion: {
  //       calle: "Calle Falsa 123",
  //       latitud: 40.73061,
  //       longitud: -73.935242
  //     }
  //   },
  //   platos: [
  //     {
  //       nombre: "Hamburguesa con queso",
  //       descripcion: "Hamburguesa con queso acompañada de papas fritas y bebida",
  //       cantidad: 1,
  //       precio: 12.00,
  //       imagen: hamburguesaImg
  //     },
  //     {
  //       nombre: "Pizza",
  //       descripcion: "De muzzarella con tomate y albahaca",
  //       cantidad: 1,
  //       precio: 16.00,
  //       imagen: pizzaImg
  //     },
  //     {
  //       nombre: "Ensalada",
  //       descripcion: "De hojas frescas y vegetales organicos",
  //       cantidad: 1,
  //       precio: 10.00,
  //       imagen: ensaladaImg
  //     }
  //   ],
  //   estado: "Pendiente",
  //   cupon: null,
  //   pago: "Tarjeta de crédito"
  // }

</script>

<svelte:head>
  <title>Detalle del pedido</title>
</svelte:head>

<main class="container-column">
  <div class="container-column main-content">
    <div class="w-100 flex-row jc-space-between header-container text-wrapper">
      <h1 class="header-title jc-space-between ellipsis-text">Pedido #12345</h1>
      <div class="flex-row state-btn-container">
        <h2 class="subtitle ellipsis-text">Estado del Pedido</h2>
        <span class="btn btn-alternate">{pedido.estado}</span>
      </div>
    </div>
    <section class="content-section-grid grid-cols-2">
      <section>
        <h3 class="padding-y-05-05">Cliente</h3>
        <div class="user">
          <i class="ph ph-user-circle"></i>
          <div class="user-info">
            <div class="name">{pedido.cliente.nombre}</div>
            <div class="username"><strong>usuario:</strong> {pedido.cliente.usuario}</div>
          </div>
        </div>
      </section>
      <section>
        <h3 class="padding-y-05-05">Direccion de entrega</h3>
        <address class="address-container">
          <div class="pin-container">
            <i class="ph ph-map-pin"></i>
          </div>
          <div>
            <div class="address"><strong>{pedido.cliente.direccion.calle}</strong></div>
            <div class="coordinates">Lat: {pedido.cliente.direccion.latitud}, Long: {pedido.cliente.direccion.longitud}</div>
          </div>
        </address>
      </section>
    </section>

    <section class="content-section-grid grid">
      <section class="section-title">
        <h3>Resumen del pedido</h3>
      </section>

      <div class="grid-table-container product-edit-ingredients-table">
        <!-- Grid Header -->
        <header class="grid-table-row table-header">
          <div class="cell">Plato</div>
          <div class="cell">Cantidad</div>
          <div class="cell">Precio</div>
        </header>
        <!-- Grid Content  -->
        {#each pedido.platos as plato }
          <!-- <GridRow plato={plato} /> -->
          <section class="grid-table-row product-edit-ingredients-table-content">
            <div class="cell product-cell ellipsis-text">
              <div class="imgcontainer">
                <img
                  src={plato.imagen}
                  alt={plato.nombre}
                  class="hide-on-mobile"
                />
              </div>
              <div class="text-wrapper">
                <h4 class="ellipsis-text">{plato.nombre}</h4>
                <p class="hide-on-mobile ellipsis-text">
                  {plato.descripcion}
                </p>
              </div>
            </div>
            <div class="cell">{plato.cantidad}</div>
            <div class="cell">${plato.precio.toFixed(2)}</div>
          </section>
        {/each}

        <!-- <section class="grid-table-row product-edit-ingredients-table-content">
          <div class="cell product-cell ellipsis-text">
            <div class="imgcontainer">
              <img
                src={hamburguesaImg}
                alt="hamburguesa"
                class="hide-on-mobile"
              />
            </div>
            <div class="text-wrapper">
              <h4 class="ellipsis-text">{pedido.platos}</h4>
              <p class="hide-on-mobile ellipsis-text">
                Hamburguesa con queso acompañada de papas fritas y bebida
              </p>
            </div>
          </div>
          <div class="cell">1</div>
          <div class="cell">$12.00</div>
        </section>
        <section class="grid-table-row product-edit-ingredients-table-content">
          <div class="cell product-cell">
            <div class="imgcontainer">
              <img
                src={pizzaImg}
                alt="pizza"
                class="hide-on-mobile"
              />
            </div>
            <div class="text-wrapper">
              <h4 class="ellipsis-text">Pizza</h4>
              <p class="hide-on-mobile ellipsis-text">
                De muzzarella con tomate y albahaca
              </p>
            </div>
          </div>
          <div class="cell">1</div>
          <div class="cell">$16.00</div>
        </section>
        <section class="grid-table-row product-edit-ingredients-table-content">
          <div class="cell product-cell ellipsis-text">
            <div class="imgcontainer">
              <img
                src={ensaladaImg}
                alt="ensalada"
                class="hide-on-mobile"
              />
            </div>
            <div class="text-wrapper">
              <h4 class="ellipsis-text">Ensalada</h4>
              <p class="hide-on-mobile ellipsis-text">
                De hojas frescas y vegetales organicos
              </p>
            </div>
          </div>
          <div class="cell">1</div>
          <div class="cell">$10.00</div>
        </section> -->
      </div>
    </section>

    <section class="content-section-grid grid">
      <h3>Pago</h3>
      <div class="flex-row jc-space-between">
        <p>Subtotal</p>
        <p>$20.00</p>
      </div>
      <div class="flex-row jc-space-between">
        <p>Comision del delivery</p>
        <p>$2.00</p>
      </div>
      <div class="flex-row jc-space-between">
        <p>Incremento por tipo de pago</p>
        <p>$1.10</p>
      </div>
      <div class="flex-row jc-space-between">
        <p>Total</p>
        <p>$23.10</p>
      </div>
    </section>
    <section class="content-section-grid grid">
      <h3 class="padding-top-05-05 h3">Metodo de pago</h3>
      <div class="payment">
        <i class="ph ph-credit-card"></i>
        <span class="payment-text">Pago con <b>{pedido.pago}</b></span>
      </div>
      <div class="action-container">
        <button class="btn btn-primary">Volver</button>
      </div>
    </section>
  </div>
</main>
