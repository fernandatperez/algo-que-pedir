<script lang="ts">
    import OrderCard from '$lib/components/OrderCard.svelte'
    import { Estado, Order } from '$lib/domain/order'
    import { orderService } from '$lib/services/orderService'
    import { onMount } from "svelte"
    import { showError } from "$lib/domain/errorHandler"
    // import Toaster from "$lib/components/toast/Toaster.svelte"
    import ToastContainer from "$lib/components/toast/ToastContainer.svelte";
    import { toasts } from "$lib/components/toast/toastStore";

    // Para filtrar pedidos por estado
    let estadoActual = $state('PENDIENTE')
    const handleStateChange = async (newState: string) => {
        estadoActual = newState
        // console.log("Estado cambiado a:", estado)
        await getTareas()
    }

    // Todos los pedidos (ejemplo)
    // mejor pedirlo filtrado al back, y no pedir todo
    let orders = $state<Order[]>([]);
    let errorMessage = $state('')
    let toastLock: boolean = false

    const getTareas = async () => {
        errorMessage = ''
        try {
            orders = await orderService.getFilteredOrders(estadoActual)
        } catch (error) {
            if (!toastLock) {
                toasts.push('Error cargando los pedidos', {type: 'error'})
                toastLock = true
                setTimeout(releaseToast, 5000)
            }
            showError('Error cargando los pedidos', error)
        }
    }
    
    onMount(getTareas)

    const prepararPedido = async (order: Order) => {
        // console.log("Preparando pedido", order.id)
        order.state = Estado.PREPARADO
        toasts.push('Pedido enviado a preparación', {type: 'info'})
        await orderService.updateOrderState(order)
        await getTareas()
        // console.log("Pedido preparado", order.id)
    }

    const releaseToast = () => {
        toastLock = false
    }
    
</script>

<style>
    @import url("$lib/css/pages-css/3-orders.css");
    .no-orders {
        background-color: var(--background-color-secondary);
        color: var(--font-color-secondary);
        padding: 1em 3em;
        border-radius: 1em;
        border: 1px solid var(--font-color-secondary);
        text-align: center;
    }
</style>

<!-- Tabs and content -->
<main class="title-tabs-grid">
    <h1 class="title">Pedidos actuales</h1>
    <section class="line">
        <div class="title-tabs">
            <nav class="tabs">
                {#each Object.keys(Estado) as estado (estado)}
                    <button onclick={() => handleStateChange(estado)} class="btn-empty tab" data-testid='btn-{estado}' class:active={estadoActual == estado}>{estado}</button>
                {/each}
            </nav>
        </div>
    </section>

    <!-- Orders grid -->
    <section class="main-grid">
        <!-- Single order -->
        {#each orders as order}
            <OrderCard order={order} action={() => prepararPedido(order)} />
        {/each}
        {#if orders.length == 0}
                <div class="no-orders">No hay pedidos</div>
        {/if}
    </section>
</main>
