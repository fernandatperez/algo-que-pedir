<script>
    import "$lib/css/fonts.css";
    import "$lib/css/flex-grid.css";
    import "$lib/css/components-css/buttons.css";
    import "$lib/css/components-css/user-details.css";
    import "$lib/css/components-css/grid-table.css";
    import "$lib/css/pages-css/9-store-profile.css";
</script>

<!-- Content -->
    <main class="container-column">
        <article class="container-column main-content">
            <h1 class="header-title">Informacion del local</h1>
            <form action="" id="form-store-profile" class="container-column form-store-profile">
                <!-- Datos del Local  -->
                <fieldset form="form-store-profile" name="store-info"
                class="content-section form-section-store-info">
                    <div class="container-column store-info">
                        <div class="container-column input-group">
                            <label for="store-name" class="label-color">Nombre del local*</label>
                            <input type="text" id="store-name" 
                            class="input-primary"
                            placeholder="Carlo's Bake Shop">
                        </div>
                        <div class="container-column input-group">
                            <label for="url-store-img" class="label-color">URL de la imagen*</label>
                            <input type="text" id="url-store-img"
                            class="input-primary"
                            placeholder="http://images.google.com/CarlosBakeShop.jag">
                        </div>
                    </div>
                    <div class="img-store-container"><img src="\src\lib\assets\img\CarlosBakeShop.jpg" alt="local" class="img-store-profile"></div>
                </fieldset>
                <!-- Direccion  -->
                <fieldset form="form-store-profile" name="store-dir"
                class="container-column content-section">
                    <h2 class="subtitle">Dirección</h2>
                    <div class="grid-cols-2 input-group-dir ">
                        <div class="container-column input-group">
                            <label for="store-dir" class="label-color">Dirección*</label>
                            <input type="text" id="store-dir" 
                            class="input-primary"
                            placeholder="Escribir">
                        </div>
                        <div class="container-column input-group">
                            <label for="store-altitude" class="label-color">Altura*</label>
                            <input type="number" id="store-altitude"
                            class="input-primary number-input"
                            placeholder="Escribir numero...">
                        </div>
                        <div class="container-column input-group">
                            <label for="store-latitude" class="label-color">Latitud*</label>
                            <input type="number" id="store-latitude"
                            class="input-primary number-input"
                            placeholder="Escribir numero...">
                        </div>
                        <div class="container-column input-group">
                            <label for="store-longitude" class="label-color">Longitud*</label>
                            <input type="number" id="store-longitude"
                            class="input-primary number-input"
                            placeholder="Escribir numero...">
                        </div>
                    </div>
                </fieldset>
                
                <fieldset form="form-store-profile" name="store-comission" 
                class="container-column content-section form-section-store-commission">
                    <h2 class="subtitle">Porcentajes</h2>
                    <div class="grid-cols-2 input-group-dir ">
                        <div class="container-column input-group">
                            <label for="app-commission" class="label-color">Porcentaje de comision con la app*</label>
                            <input type="number" id="app-commission"
                            class="input-primary number-input"
                            placeholder="Escribir numero...">
                        </div>
                        <div class="container-column input-group">
                            <label for="author-commission" class="label-color">Porcentaje de comision con autores de platos*</label>
                            <input type="number" id="author-commission"
                            class="input-primary number-input"
                            placeholder="Escribir numero...">
                        </div>                    
                    </div>
                </fieldset>
    
                <fieldset form="form-store-profile" name="store-payment-methods" 
                class="container-column content-section">
                    <h2 class="subtitle">Metodos de Pago</h2>
                    <div class="payments-checkbox-group">
                        <label for="efectivo" class="label-color">
                            <input type="checkbox" id="efectivo" 
                            name="efectivo" 
                            value="EFECTIVO"
                            class="payment-checkbox">
                            <span>Efectivo</span>
                        </label>
        
                        <label for="qr" class="label-color">
                            <input type="checkbox" id="qr" 
                            name="qr" 
                            value="QR"
                            class="payment-checkbox">
                            <span>QR</span>
                        </label>
    
                        <label for="transferencia" class="label-color">
                            <input type="checkbox" id="transferencia" 
                            name="transferencia" 
                            value="TRANSFERENCIA"
                            class="payment-checkbox">
                            <span>Transferencia</span>
                        </label>
    
                    </div>
                </fieldset>
                
                <section class="btn-group-actions">
                    <button disabled class="btn btn-secondary btn-store">Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
                    <button disabled class="btn btn-primary btn-store">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
                </section>
            </form>
        </article>
    </main>
