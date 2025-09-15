<script>
    import "$lib/css/components-css/user-details.css";
    import "$lib/css/components-css/buttons.css";
    import "$lib/css/pages-css/3-orders.css";
    import OrderCard from "$lib/OrderCard.svelte";

    import hamburguesaImg from "$lib/assets/img/hamburguesa.png";
    import pizzaImg from "$lib/assets/img/pizza.png";
    import ensaladaImg from "$lib/assets/img/ensalada.png";

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

    let pedidos = $state([single_pedido, single_pedido, single_pedido, single_pedido, single_pedido, single_pedido, single_pedido]);

    // let filtro = "pendiente";
    // onclick={filtro = "pendiente"}
    // onclick={filtro = "preparado"}
    // onclick={filtro = "entregado"}
    // onclick={filtro = "cancelado"}

</script>

<!-- Tabs and content -->
<main class="title-tabs-grid">
    <section class="line">
        <div class="title-tabs">
            <h1 class="title">Pedidos actuales</h1>
            <nav class="tabs">
                <button class="btn-empty tab active">Pendientes</button>
                <button class="btn-empty tab">Preparados</button>
                <button class="btn-empty tab">Entregados</button>
                <button class="btn-empty tab">Cancelados</button>
            </nav>
        </div>
    </section>

    <!-- Orders grid -->
    <section class="main-grid">
        <!-- Single order -->
        {#each pedidos as pedido}
            <OrderCard {pedido} />
            <!-- <a href="/orders-details" class="grid-item">
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
                    <button class="btn btn-primary"> Preparar </button>
                </div>
            </a> -->
        {/each}



        <!-- Single order -->
        <!-- <article class="grid-item">
            <header class="order">Pedido #1234</header>

            <div class="user">
                <i class="ph ph-user-circle"></i>
                <div class="user-info">
                    <div class="name">Sofía Miller</div>
                    <div class="username">
                        <strong>usuario:</strong> smiller2006
                    </div>
                </div>
            </div>

            <p class="details">Hora: 12:30 PM | Artículos: 2 | Total: $25</p>

            <address class="address-container">
                <div class="pin-container">
                    <i class="ph ph-map-pin"></i>
                </div>
                <div>
                    <div class="address">
                        <strong>Av siempre viva 555</strong>
                    </div>
                    <div class="coordinates">Lat: 40.7128, Long: -74.0060</div>
                </div>
            </address>

            <div class="payment">
                <i class="ph ph-credit-card"></i>
                <span class="payment-text">Pago con tarjeta de crédito</span>
            </div>

            <div class="action-container">
                <button class="btn btn-primary">Preparar</button>
            </div>
        </article> -->

        <!-- Single order -->
        <!-- <article class="grid-item">
            <header class="order">Pedido #1234</header>

            <div class="user">
                <i class="ph ph-user-circle"></i>
                <div class="user-info">
                    <div class="name">Sofía Miller</div>
                    <div class="username">
                        <strong>usuario:</strong> smiller2006
                    </div>
                </div>
            </div>

            <p class="details">Hora: 12:30 PM | Artículos: 2 | Total: $25</p>

            <address class="address-container">
                <div class="pin-container">
                    <i class="ph ph-map-pin"></i>
                </div>
                <div>
                    <div class="address">
                        <strong>Av siempre viva 555</strong>
                    </div>
                    <div class="coordinates">Lat: 40.7128, Long: -74.0060</div>
                </div>
            </address>

            <div class="payment">
                <i class="ph ph-credit-card"></i>
                <span class="payment-text">Pago con tarjeta de crédito</span>
            </div>

            <div class="action-container">
                <button class="btn btn-primary">Preparar</button>
            </div>
        </article> -->

        <!-- Single order -->
        <!-- <article class="grid-item">
            <header class="order">Pedido #1234</header>

            <div class="user">
                <i class="ph ph-user-circle"></i>
                <div class="user-info">
                    <div class="name">Sofía Miller</div>
                    <div class="username">
                        <strong>usuario:</strong> smiller2006
                    </div>
                </div>
            </div>

            <p class="details">Hora: 12:30 PM | Artículos: 2 | Total: $25</p>

            <address class="address-container">
                <div class="pin-container">
                    <i class="ph ph-map-pin"></i>
                </div>
                <div>
                    <div class="address">
                        <strong>Av siempre viva 555</strong>
                    </div>
                    <div class="coordinates">Lat: 40.7128, Long: -74.0060</div>
                </div>
            </address>

            <div class="payment">
                <i class="ph ph-credit-card"></i>
                <span class="payment-text">Pago con tarjeta de crédito</span>
            </div>

            <div class="action-container">
                <button class="btn btn-primary">Preparar</button>
            </div>
        </article> -->

        <!-- Single order -->
        <!-- <article class="grid-item">
            <header class="order">Pedido #1234</header>

            <div class="user">
                <i class="ph ph-user-circle"></i>
                <div class="user-info">
                    <div class="name">Sofía Miller</div>
                    <div class="username">
                        <strong>usuario:</strong> smiller2006
                    </div>
                </div>
            </div>

            <p class="details">Hora: 12:30 PM | Artículos: 2 | Total: $25</p>

            <address class="address-container">
                <div class="pin-container">
                    <i class="ph ph-map-pin"></i>
                </div>
                <div>
                    <div class="address">
                        <strong>Av siempre viva 555</strong>
                    </div>
                    <div class="coordinates">Lat: 40.7128, Long: -74.0060</div>
                </div>
            </address>

            <div class="payment">
                <i class="ph ph-credit-card"></i>
                <span class="payment-text">Pago con tarjeta de crédito</span>
            </div>

            <div class="action-container">
                <button class="btn btn-primary">Preparar</button>
            </div>
        </article> -->

        <!-- Single order -->
        <!-- <article class="grid-item">
            <header class="order">Pedido #1234</header>

            <div class="user">
                <i class="ph ph-user-circle"></i>
                <div class="user-info">
                    <div class="name">Sofía Miller</div>
                    <div class="username">
                        <strong>usuario:</strong> smiller2006
                    </div>
                </div>
            </div>

            <p class="details">Hora: 12:30 PM | Artículos: 2 | Total: $25</p>

            <address class="address-container">
                <div class="pin-container">
                    <i class="ph ph-map-pin"></i>
                </div>
                <div>
                    <div class="address">
                        <strong>Av siempre viva 555</strong>
                    </div>
                    <div class="coordinates">Lat: 40.7128, Long: -74.0060</div>
                </div>
            </address>

            <div class="payment">
                <i class="ph ph-credit-card"></i>
                <span class="payment-text">Pago con tarjeta de crédito</span>
            </div>

            <div class="action-container">
                <button class="btn btn-primary">Preparar</button>
            </div>
        </article> -->

        <!-- Single order -->
        <!-- <article class="grid-item">
            <header class="order">Pedido #1234</header>

            <div class="user">
                <i class="ph ph-user-circle"></i>
                <div class="user-info">
                    <div class="name">Sofía Miller</div>
                    <div class="username">
                        <strong>usuario:</strong> smiller2006
                    </div>
                </div>
            </div>

            <p class="details">Hora: 12:30 PM | Artículos: 2 | Total: $25</p>

            <address class="address-container">
                <div class="pin-container">
                    <i class="ph ph-map-pin"></i>
                </div>
                <div>
                    <div class="address">
                        <strong>Av siempre viva 555</strong>
                    </div>
                    <div class="coordinates">Lat: 40.7128, Long: -74.0060</div>
                </div>
            </address>

            <div class="payment">
                <i class="ph ph-credit-card"></i>
                <span class="payment-text">Pago con tarjeta de crédito</span>
            </div>

            <div class="action-container">
                <button class="btn btn-primary">Preparar</button>
            </div>
        </article> -->
    </section>
</main>
