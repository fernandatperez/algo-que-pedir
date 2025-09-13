<script>
    import "$lib/css/fonts.css";
    import "$lib/css/flex-grid.css";
    import "$lib/css/components-css/buttons.css";
    import "$lib/css/components-css/user-details.css";
    import "$lib/css/components-css/grid-table.css";
    import "$lib/css/pages-css/4-order-details.css";
</script>

<main class="container-column">
    <div class="container-column main-content">
        <div
            class="w-100 flex-row jc-space-between header-container text-wrapper"
        >
            <h1 class="header-title jc-space-between ellipsis-text">
                Pedido #12345
            </h1>
            <div class="flex-row gap-1 order-state-title">
                <h2 class="subtitle ellipsis-text">Estado del Pedido</h2>
                <button class="btn btn-alternate">Pendiente</button>
            </div>
        </div>
        <section class="content-section-grid grid-cols-2">
            <section>
                <h3 class="padding-y-05-05">Cliente</h3>
                <div class="user">
                    <i class="ph ph-user-circle"></i>
                    <div class="user-info">
                        <div class="name">Sofía Miller</div>
                        <div class="username">
                            <strong>usuario:</strong> smiller2006
                        </div>
                    </div>
                </div>
            </section>
            <section>
                <h3 class="padding-y-05-05">Direccion de entrega</h3>
                <address class="address-container">
                    <div class="pin-container">
                        <i class="ph ph-map-pin"></i>
                    </div>
                    <div>
                        <div class="address">
                            <strong>Av siempre viva 555</strong>
                        </div>
                        <div class="coordinates">
                            Lat: 40.7128, Long: -74.0060
                        </div>
                    </div>
                </address>
            </section>
        </section>

        <section class="content-section-grid grid">
            <section class="section-title">
                <h3>Resumen del pedido</h3>
            </section>

            <div class="grid-table-container product-edit-ingredients-table">
                <header class="grid-table-row table-header">
                    <div class="cell">Plato</div>
                    <div class="cell">Cantidad</div>
                    <div class="cell">Precio</div>
                </header>
                <section
                    class="grid-table-row product-edit-ingredients-table-content"
                >
                    <div class="cell product-cell ellipsis-text">
                        <div class="imgcontainer">
                            <img
                                src="/src/lib/assets/img/hamburguesa.png"
                                alt="hamburguesa"
                                class="hide-on-mobile"
                            />
                        </div>
                        <div class="text-wrapper">
                            <h4 class="ellipsis-text h4">
                                Hamburguesa con queso
                            </h4>
                            <p class="hide-on-mobile ellipsis-text">
                                Hamburguesa con queso acompañada de papas fritas
                                y bebida
                            </p>
                        </div>
                    </div>
                    <div class="cell">1</div>
                    <div class="cell">$12.00</div>
                </section>
                <section
                    class="grid-table-row product-edit-ingredients-table-content"
                >
                    <div class="cell product-cell">
                        <div class="imgcontainer">
                            <img
                                src="/src/lib/assets/img/pizza.png"
                                alt="pizza"
                                class="hide-on-mobile"
                            />
                        </div>
                        <div class="text-wrapper">
                            <h4 class="ellipsis-text h4">Pizza</h4>
                            <p class="hide-on-mobile ellipsis-text">
                                De muzzarella con tomate y albahaca
                            </p>
                        </div>
                    </div>
                    <div class="cell">1</div>
                    <div class="cell">$16.00</div>
                </section>
                <section
                    class="grid-table-row product-edit-ingredients-table-content"
                >
                    <div class="cell product-cell ellipsis-text">
                        <div class="imgcontainer">
                            <img
                                src="/src/lib/assets/img/ensalada.png"
                                alt="ensalada"
                                class="hide-on-mobile"
                            />
                        </div>
                        <div class="text-wrapper">
                            <h4 class="ellipsis-text h4">Ensalada</h4>
                            <p class="hide-on-mobile ellipsis-text">
                                De hojas frescas y vegetales organicos
                            </p>
                        </div>
                    </div>
                    <div class="cell">1</div>
                    <div class="cell">$10.00</div>
                </section>
            </div>
        </section>

        <section class="content-section-grid grid">
            <h3>Pago</h3>
            <div class="flex-row jc-space-between">
                <p>Subtotal</p>
                <p>$20.00</p>
            </div>
            <div class="flex-row jc-space-between">
                <p>Comision del delivery</p>
                <p>$2.00</p>
            </div>
            <div class="flex-row jc-space-between">
                <p>Incremento por tipo de pago</p>
                <p>$1.10</p>
            </div>
            <div class="flex-row jc-space-between">
                <p>Total</p>
                <p>$23.10</p>
            </div>
        </section>
        <section class="content-section-grid grid">
            <h3 class="padding-top-05-05 h3">Metodo de pago</h3>
            <div class="payment">
                <i class="ph ph-credit-card"></i>
                <span class="payment-text">Pago con tarjeta de crédito</span>
            </div>
            <div class="action-container">
                <button class="btn btn-primary">Volver</button>
            </div>
        </section>
    </div>
</main>
