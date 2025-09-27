<script lang="ts">
    import '$lib/css/components-css/buttons.css'
    import '$lib/css/components-css/orderCard.css'
    import type { Order } from '../type/order';
    

    interface Props {
        order: Order
    }

    let { order } : Props = $props()

    const handleStateChange = () => {
        console.log("Preparando pedido", order.id);
        order.estado = "PREPARADO"
        console.log("Pedido preparado", order.id);
        // Aca iria la logica del POST para cambiar el estado del pedido a "Preparado"
        // Hay que ver en que estado está y a qué estado cambia !!
    }
</script>

<div class="order-card">
    <a href="/order-detail/{order.id}" data-testid="goto-detail">
        <header class="order" data-testid="order-id">Pedido #{order.id}</header>
    
        <div class="user">
            <i class="ph ph-user-circle"></i>
            <div class="user-info">
                <div class="name">{order.nombreCliente}</div>
                <div class="username">
                    <strong>usuario:</strong> {order.usuarioCliente}
                </div>
            </div>
        </div>
    </a>

    <p class="details">Hora: {order.createdAt.toLocaleString()} PM | Artículos: { order.platos.length} | Total: $ {order.precioTotal().toFixed(2)}</p>

    <address class="address-container">
        <div class="pin-container">
            <i class="ph ph-map-pin"></i>
        </div>
        <div class="address-coordinates">
            <span class="address">
                <strong>{order.direccionCliente}</strong>
            </span>
            <div class="coordinates">Lat: {order.latitudCliente}, Long: {order.longitudCliente}</div>
        </div>
    </address>

    <div class="payment">
        <i class="ph ph-credit-card"></i>
        <span class="payment-text">Pago con <b>{order.tipoPago}</b></span>
    </div>

    <div class="action-container">
        <button onclick={handleStateChange} class="btn btn-primary" disabled={order.estado=="PREPARADO"} data-testid="preparar">Preparar</button>
    </div>
</div>