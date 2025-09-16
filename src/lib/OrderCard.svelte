<script lang="ts">
    import '$lib/css/components-css/orderCard.css'

  let {pedido} = $$props

  const handleStateChange = () => {
      console.log("Preparando pedido", pedido.pedidoId);
      // Aca iria la logica del POST para cambiar el estado del pedido a "Preparado"
  };    
</script>

<a href="/order-detail/{pedido.pedidoId}" class="order-card">
    <header class="order">Pedido #{pedido.pedidoId}</header>

    <div class="user">
        <i class="ph ph-user-circle"></i>
        <div class="user-info">
            <div class="name">{pedido.cliente.nombre}</div>
            <div class="username">
                <strong>usuario:</strong> {pedido.cliente.usuario}
            </div>
        </div>
    </div>

    <p class="details">Hora: 12:30 PM | Artículos: 2 | Total: $25</p>

    <address class="address-container">
        <div class="pin-container">
            <i class="ph ph-map-pin"></i>
        </div>
        <div class="address-coordinates">
            <span class="address"
                ><strong>{pedido.cliente.direccion.calle}</strong></span
            >
            <div class="coordinates">Lat: {pedido.cliente.direccion.latitud}, Long: {pedido.cliente.direccion.longitud}</div>
        </div>
    </address>

    <div class="payment">
        <i class="ph ph-credit-card"></i>
        <span class="payment-text">Pago con {pedido.pago}</span>
    </div>

    <div class="action-container">
        <button onclick={handleStateChange} class="btn btn-primary"> Preparar </button>
    </div>
</a>