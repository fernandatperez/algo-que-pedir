<script lang="ts">
    import "$lib/css/pages-css/3-orders.css";

    import OrderCard from "$lib/OrderCard.svelte";
    import { ORDERS_MOCK } from "$lib/data/mock/orders";


    // Todos los pedidos (ejemplo)
    // mejor pedirlo filtrado al back, y no pedir todo
    let orders = $state(ORDERS_MOCK);
    
    // Filtrar pedidos por estado
    let estado = $state("PENDIENTE");

    const filtrarPedidos = (estado: string) => orders.filter((order) => order.estado.toUpperCase() === estado)
    
    let filteredOrders = $derived(filtrarPedidos(estado));

    const handleStateChange = (newState: string) => {
        estado = newState;
        console.log("Estado cambiado a:", estado);
        // console.log("Pedidos filtrados:", pedidosFiltrados);
        // updateActiveTab(estado);
    };

</script>

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
        {#each filteredOrders as order}
            <OrderCard order={order} />
        {/each}

    </section>
</main>
