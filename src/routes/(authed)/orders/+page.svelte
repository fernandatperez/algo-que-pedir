<script lang="ts">
    import "$lib/css/components-css/user-details.css";
    import "$lib/css/components-css/buttons.css";
    import "$lib/css/pages-css/3-orders.css";
    import OrderCard from "$lib/OrderCard.svelte";

    import hamburguesaImg from "$lib/assets/img/hamburguesa.png";
    import pizzaImg from "$lib/assets/img/pizza.png";
    import ensaladaImg from "$lib/assets/img/ensalada.png";

    // Ejemplo de pedido
    let single_pedido = $state({
        pedidoId: 12345,
        cliente: {
            nombre: "Sofía Miller",
            usuario: "smiller2006",
            direccion: {
                calle: "Av siempre viva 555",
                latitud: 40.7128,
                longitud: -74.006,
            },
        },
        local: {
            nombre: "La buena comida",
            direccion: {
                calle: "Calle Falsa 123",
                latitud: 40.73061,
                longitud: -73.935242,
            },
        },
        platos: [
            {
                nombre: "Hamburguesa con queso",
                descripcion:
                    "Hamburguesa con queso acompañada de papas fritas y bebida",
                cantidad: 1,
                precio: 12.0,
                imagen: hamburguesaImg,
            },
            {
                nombre: "Pizza",
                descripcion: "De muzzarella con tomate y albahaca",
                cantidad: 1,
                precio: 16.0,
                imagen: pizzaImg,
            },
            {
                nombre: "Ensalada",
                descripcion: "De hojas frescas y vegetales organicos",
                cantidad: 1,
                precio: 10.0,
                imagen: ensaladaImg,
            },
        ],
        estado: "Pendiente",
        cupon: null,
        pago: "Tarjeta de crédito",
    });

    // Todos los pedidos (ejemplo)
    let pedidos = $state([single_pedido, single_pedido, single_pedido, single_pedido, single_pedido, single_pedido, single_pedido]);

    let estado = $state("Pendiente");
    
    // Filtrar pedidos por estado
    const filtrarPedidos = (estado: string) => pedidos.filter((pedido) => pedido.estado === estado)
    
    let pedidosFiltrados = $derived(filtrarPedidos(estado));

    const handleStateChange = (newState: string) => {
        estado = newState;
        console.log("Estado cambiado a:", estado);
        // pedidosFiltrados = filtrarPedidos(estado);
    };

</script>

<!-- Tabs and content -->
<main class="title-tabs-grid">
    <h1 class="title">Pedidos actuales</h1>
    <section class="line">
        <div class="title-tabs">
            <nav class="tabs">
                <button onclick={() => handleStateChange("Pendiente")} class="btn-empty tab active">Pendientes</button>
                <button onclick={() => handleStateChange("Preparado")} class="btn-empty tab">Preparados</button>
                <button onclick={() => handleStateChange("Entregado")} class="btn-empty tab">Entregados</button>
                <button onclick={() => handleStateChange("Cancelado")} class="btn-empty tab">Cancelados</button>
            </nav>
        </div>
    </section>

    <!-- Orders grid -->
    <section class="main-grid">
        <!-- Single order -->
        {#each pedidosFiltrados as pedido}
            <OrderCard {pedido} />
        {/each}

    </section>
</main>
