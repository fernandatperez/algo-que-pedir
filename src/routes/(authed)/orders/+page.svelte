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

    
    // Filtrar pedidos por estado
    let estado = $state("Pendiente");

    const filtrarPedidos = (estado: string) => pedidos.filter((pedido) => pedido.estado === estado)
    
    let pedidosFiltrados = $derived(filtrarPedidos(estado));

    const handleStateChange = (newState: string) => {
        estado = newState;
        console.log("Estado cambiado a:", estado);
        // updateActiveTab(estado);
    };

    // Pongo clase active a boton de estado seleccionado

    // Esto no funciona
    // Se recomienda usar class:active={condicion} de sveltekit en el boton
    /* const updateActiveTab = (newState: string) => {
        const tabs = document.querySelectorAll(".tab")
        console.log(tabs);
        tabs.forEach((tab) => {
            if (tab.innerText === newState) {
                tab.classList.add("active"); // no funciona
            } else {
                tab.classList.remove("active"); // este fucniona ?
            }
        });
    };*/

</script>

<!-- Tabs and content -->
<main class="title-tabs-grid">
    <h1 class="title">Pedidos actuales</h1>
    <section class="line">
        <div class="title-tabs">
            <nav class="tabs">
                <button onclick={() => handleStateChange("Pendiente")} class="btn-empty tab" class:active={estado == "Pendiente"}>Pendientes</button>
                <button onclick={() => handleStateChange("Preparado")} class="btn-empty tab" class:active={estado == "Preparado"}>Preparados</button>
                <button onclick={() => handleStateChange("Entregado")} class="btn-empty tab" class:active={estado == "Entregado"}>Entregados</button>
                <button onclick={() => handleStateChange("Cancelado")} class="btn-empty tab" class:active={estado == "Cancelado"}>Cancelados</button>
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
