<script lang="ts">
  import "$lib/css/pages-css/4-order-details.css"
  import GridRow from "$lib/components/GridRow.svelte"
  import { goto } from "$app/navigation";

  let { data } = $props()
  let { order } = data

</script>

<svelte:head>
  <title>Detalle del pedido</title>
</svelte:head>

<main class="container-column">
  <div class="container-column main-content">
    <div class="w-100 flex-row jc-space-between header-container text-wrapper">
      <h1 class="header-title jc-space-between ellipsis-text" data-testid="order-id">Pedido #{order.id}</h1>
      <div class="flex-row state-btn-container">
        <h2 class="subtitle ellipsis-text">Estado del Pedido</h2>
        <span class="btn btn-alternate">{order.state}</span>
      </div>
    </div>
    <section class="content-section-grid grid-cols-2">
      <section>
        <h3 class="padding-y-05-05">Cliente</h3>
        <div class="user">
          <i class="ph ph-user-circle"></i>
          <div class="user-info">
            <div class="name">{order.name}</div>
            <div class="username"><strong>usuario:</strong> {order.user}</div>
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
            <div class="address"><strong>{order.address}</strong></div>
            <div class="coordinates">Lat: {order.lat}, Long: {order.long}</div>
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
        {#each order.dishes as plato }
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
        <p>${order.recargoTipoDePago().toFixed(2)}</p>
      </div>
      <div class="flex-row jc-space-between">
        <p>Comision del delivery</p>
        <p>${order.deliveryComission}</p>
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
        <span class="payment-text">Pago con <b>{order.paymentMethod}</b></span>
      </div>
      <div class="action-container">
        <button class="btn btn-primary" onclick={() => goto('/orders')}>Volver</button>
      </div>
    </section>
  </div>
</main>
