<script lang="ts">
  import Grid from "$lib/components/GridRow.svelte"
  import OrderState from "$lib/components/OrderState.svelte"
  
  import { goto } from "$app/navigation"

  let { data } = $props()
  let { order } = data

  if (!order) {
    goto('/orders')
  }
</script>

<style>
  @import url('$lib/css/flex-grid.css');
  @import url('$lib/css/components-css/user-details.css');
  @import url('$lib/css/components-css/buttons.css');
  @import url('$lib/css/pages-css/4-order-details.css');
</style>

<svelte:head>
  <title>Detalle del pedido</title>
</svelte:head>

<main class="container-column">
  <div class="container-column main-content">
    <div class="w-100 flex-row jc-space-between header-container text-wrapper">
      <h1 class="header-title jc-space-between ellipsis-text" data-testid="order-id">Pedido #{order.id}</h1>
      <div class="flex-row state-btn-container">
        <h2 class="subtitle ellipsis-text">Estado del Pedido</h2>
        <OrderState estado={order.estado}  />
        <!-- <span class="btn btn-alternate {estadoColor}">{order.estado}</span> -->
      </div>
    </div>
    <section class="content-section-grid grid-cols-2">
      <section>
        <h3 class="padding-y-05">Cliente</h3>
        <div class="user">
          <i class="ph ph-user-circle"></i>
          <div class="user-info">
            <div class="name">{order.nombre}</div>
            <div class="username"><strong>usuario:</strong> {order.username}</div>
          </div>
        </div>
      </section>
      <section>
        <h3 class="padding-y-05">Direccion de entrega</h3>
        <address class="address-container">
          <div class="pin-container">
            <i class="ph ph-map-pin"></i>
          </div>
          <div>
            <div class="address"><strong>{order.direccionEntera}</strong></div>
            <div class="coordinates">Lat: {order.lat}, Long: {order.long}</div>
          </div>
        </address>
      </section>
    </section>

    <section class="content-section-grid grid">
      <section>
        <h3>Resumen del pedido</h3>
      </section>

      <!-- Grid Content  -->
      <Grid platos={order.platos} />
      </section>

    <section class="content-section-grid grid">
      <h3>Pago</h3>
      <div class="flex-row jc-space-between">
        <p>Subtotal</p>
        <p>${order.precioSubtotal.toFixed(2)}</p>
      </div>
      <div class="flex-row jc-space-between">
        <p>Incremento por tipo de pago</p>
        <p>${order.recargoTipoDePago().toFixed(2)}</p>
      </div>
      <div class="flex-row jc-space-between">
        <p>Comision del delivery</p>
        <p>${order.deliveryFee.toFixed(2)}</p>
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
        <span class="payment-text">Pago con <b>{order.metodoDePago.split('_').join(' ')}</b></span>
      </div>
      <div class="action-container">
        <button class="btn btn-primary" onclick={() => goto('/orders')}>Volver</button>
        <!-- {#if order.estado != Estado.ENTREGADO && order.estado != Estado.CANCELADO}
        <button onclick={cancelar} class="btn btn-primary" data-testid='cancelar-{order.id}'> Cancelar </button>
        {/if}
        {#if order.estado === Estado.PENDIENTE}
        <button onclick={} class="btn btn-primary btn-preparar" data-testid='preparar-{order.id}'> Preparar </button>
        {/if} -->
      </div>
    </section>
  </div>
</main>
