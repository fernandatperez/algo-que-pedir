<script lang="ts">
    import '$lib/css/components-css/buttons.css'
    import '$lib/css/components-css/orderCard.css'
    import type { MouseEventHandler } from 'svelte/elements'
    import { type Order, Estado } from '../domain/order'

    interface Props {
        order: Order,
        action: MouseEventHandler<HTMLButtonElement>
    }

    let { order, action = $bindable() } : Props = $props()
    
</script>

<div class="order-card">
    <a href="/order-detail/{order.id}">
        <header class="order">Pedido #{order.id}</header>
    
        <div class="user">
            <i class="ph ph-user-circle"></i>
            <div class="user-info">
                <div class="name">{order.name}</div>
                <div class="username">
                    <strong>usuario:</strong> {order.user}
                </div>
            </div>
        </div>
    </a>

    <p class="details">Hora: {order.createdAt} PM | Artículos: { order.dishes.length} | Total: $ {order.precioTotal().toFixed(2)}</p>

    <address class="address-container">
        <div class="pin-container">
            <i class="ph ph-map-pin"></i>
        </div>
        <div class="address-coordinates">
            <span class="address"
                ><strong>{order.address}</strong></span
            >
            <div class="coordinates">Lat: {order.lat}, Long: {order.long}</div>
        </div>
    </address>

    <div class="payment">
        <i class="ph ph-credit-card"></i>
        <span class="payment-text">Pago con <b>{order.paymentMethod}</b></span>
    </div>

    <div class="action-container">
        <button onclick={action} class="btn btn-primary" disabled={order.state!=Estado.PENDIENTE}> Preparar </button>
    </div>
</div>