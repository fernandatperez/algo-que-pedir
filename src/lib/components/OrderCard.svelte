<script lang="ts">
    import type { MouseEventHandler } from 'svelte/elements'
    import { type Order, Estado } from '../domain/order'

    interface Props {
        order: Order,
        action?: MouseEventHandler<HTMLButtonElement>
    }

    let { order, action = $bindable() } : Props = $props()
    
</script>

<style>
    @import url('$lib/css/components-css/buttons.css');
    @import url('$lib/css/components-css/orderCard.css');
</style>

<div class="order-card">
    <a href="/order-detail/{order.id}"  data-testid="goto-detail">
        <header class="order" data-testid="order-id">Pedido #{order.id}</header>
    
        <div class="user">
            <i class="ph ph-user-circle"></i>
            <div class="user-info">
                <div class="name">{order.nombre}</div>
                <div class="username">
                    <strong>usuario:</strong> {order.username}
                </div>
            </div>
        </div>
    </a>

    <p class="details">Hora: {order.horarioEntrega} PM | Artículos: { order.platos.length} | Total: $ {order.precioTotal().toFixed(2)}</p>

    <address class="address-container">
        <div class="pin-container">
            <i class="ph ph-map-pin"></i>
        </div>
        <div class="address-coordinates">
            <span class="address"
                ><strong>{order.direccion}</strong></span
            >
            <div class="coordinates">Lat: {order.lat}, Long: {order.long}</div>
        </div>
    </address>

    <div class="payment">
        <i class="ph ph-credit-card"></i>
        <span class="payment-text">Pago con <b>{order.metodoDePago}</b></span>
    </div>

    <div class="action-container">
        <button onclick={action} class="btn btn-primary" data-testid='preparar-{order.id}' disabled={order.estado!=Estado.PENDIENTE}> Preparar </button>
    </div>
</div>