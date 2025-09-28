<script lang="ts">
    import "$lib/css/pages-css/3-orders.css"

    import OrderCard from "$lib/OrderCard.svelte"
    // import { ORDERS_MOCK } from "$lib/data/mock/orders";
    import { Estado, Order } from '$lib/type/order'
    import { orderService } from '$lib/services/orderService'
    import { onMount } from "svelte"

    // Para filtrar pedidos por estado
    let estado = $state('PENDIENTE')
    const handleStateChange = async (newState: string) => {
        estado = newState
        // console.log("Estado cambiado a:", estado)
        await getTareas()
    }

    // Todos los pedidos (ejemplo)
    // mejor pedirlo filtrado al back, y no pedir todo
    let orders = $state<Order[]>([]);
    let errorMessage = $state('')

    const getTareas = async () => {
        errorMessage = ''
        try {
            orders = await orderService.getFilteredOrders(estado)
            if (orders.length == 0) {
                errorMessage = 'No hay pedidos'
            }
        } catch (error) {
            errorMessage = 'Error de Conexion'
        }
    }
    
    onMount(getTareas)

    const prepararPedido = async (order: Order) => {
        // console.log("Preparando pedido", order.id)
        order.state = Estado.PREPARADO
        await orderService.updateOrderState(order)
        await getTareas()
        // console.log("Pedido preparado", order.id)
    }
    
</script>

<style>
    .error-text {
        background-color: #da8a8a;
        color: darkred;
        padding: 1em 3em;
        border-radius: 1em;
        border: 1px solid darkred;
        text-align: center;
    }
</style>

<!-- Tabs and content -->
<main class="title-tabs-grid">
    <h1 class="title">Pedidos actuales</h1>
    <section class="line">
        <div class="title-tabs">
            <nav class="tabs">
                <button onclick={() => handleStateChange("PENDIENTE".toUpperCase())} class="btn-empty tab" class:active={estado.toUpperCase() == "PENDIENTE"}>Pendientes</button>
                <button onclick={() => handleStateChange("PREPARADO".toUpperCase())} class="btn-empty tab" class:active={estado.toUpperCase() == "PREPARADO"}>Preparados</button>
                <button onclick={() => handleStateChange("ENTREGADO".toUpperCase())} class="btn-empty tab" class:active={estado.toUpperCase() == "ENTREGADO"}>Entregados</button>
                <button onclick={() => handleStateChange("CANCELADO".toUpperCase())} class="btn-empty tab" class:active={estado.toUpperCase() == "CANCELADO"}>Cancelados</button>
            </nav>
        </div>
    </section>

    <!-- Orders grid -->
    <section class="main-grid">
        <!-- Single order -->
        {#each orders as order}
            <OrderCard order={order} action={() => prepararPedido(order)} />
        {/each}
        {#if (errorMessage.trim() != '')}
            <div class="error-text">{errorMessage}</div>
        {/if}
    </section>
</main>
