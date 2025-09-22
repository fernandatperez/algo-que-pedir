<script lang="ts">
  import "$lib/css/pages-css/4-order-details.css"

  import GridRow from "$lib/components/GridRow.svelte"
  import { ORDERS_MOCK } from "$lib/data/mock/orders"

  // 'page' is deprecated dice
  // import { page } from "$app/stores"
  import { page } from "$app/state";
    import type { Order } from "$lib/type/order";

  // id del pedido para hacer fetch/find() del pedido
  const pedidoId = page.params.id // No puedo parsear ni definir el tipo para el parametro de url

  // simulo un GET con el id. Hago un find()
  const order = ORDERS_MOCK.find( order => order.id.toString() == pedidoId )! // Hago el toString() en el id, ya que el parametro de url no lo puedo parsear
  /* Non-null assertion operator (!): If you are absolutely certain that find() 
  will always return a value (e.g., you've performed a prior check or the logic guarantees a match), 
  you can use the non-null assertion operator (!) to tell TypeScript to treat the value as non-nullable. */

  function recargoTipoDePago(order : Order) : number {
    let recargo: number 
    if (order.recargoPago() == 1) {
      recargo = 0
    } else {
      recargo = order.precioSubtotal() * 0.1
    }
    return recargo 
  }
  let recargo = $derived(recargoTipoDePago(order))

</script>

<svelte:head>
  <title>Detalle del pedido</title>
</svelte:head>

<main class="container-column">
  <div class="container-column main-content">
    <div class="w-100 flex-row jc-space-between header-container text-wrapper">
      <h1 class="header-title jc-space-between ellipsis-text">Pedido #{order.id}</h1>
      <div class="flex-row state-btn-container">
        <h2 class="subtitle ellipsis-text">Estado del Pedido</h2>
        <span class="btn btn-alternate">{order.estado}</span>
      </div>
    </div>
    <section class="content-section-grid grid-cols-2">
      <section>
        <h3 class="padding-y-05-05">Cliente</h3>
        <div class="user">
          <i class="ph ph-user-circle"></i>
          <div class="user-info">
            <div class="name">{order.nombreCliente}</div>
            <div class="username"><strong>usuario:</strong> {order.usuarioCliente}</div>
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
            <div class="address"><strong>{order.direccionCliente}</strong></div>
            <div class="coordinates">Lat: {order.latitudCliente}, Long: {order.longitudCliente}</div>
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
        {#each order.platos as plato }
          <GridRow plato={plato} />
        {/each}
      </div>
    </section>

    <section class="content-section-grid grid">
      <h3>Pago</h3>
      <div class="flex-row jc-space-between">
        <p>Subtotal</p>
        <p>${order.precioSubtotal().toFixed(2)}</p>
      </div>
      <div class="flex-row jc-space-between">
        <p>Incremento por tipo de pago</p>
        <p>${recargo.toFixed(2)}</p>
      </div>
      <div class="flex-row jc-space-between">
        <p>Comision del delivery</p>
        <p>${order.comisionDelivery}</p>
      </div>
      <div class="flex-row jc-space-between">
        <p>Total</p>
        <p>${order.precioTotal().toFixed(2)}</p>
      </div>
    </section>
    <section class="content-section-grid grid">
      <h3 class="padding-top-05-05 h3">Metodo de pago</h3>
      <div class="payment">
        <i class="ph ph-credit-card"></i>
        <span class="payment-text">Pago con <b>{order.tipoPago}</b></span>
      </div>
      <div class="action-container">
        <button class="btn btn-primary">Volver</button>
      </div>
    </section>
  </div>
</main>
