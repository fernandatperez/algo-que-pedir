<script lang="ts">
  import "$lib/css/flex-grid.css";
  import "$lib/css/fonts.css";
  import "$lib/css/components-css/number-input.css";
  import "$lib/css/components-css/grid-table.css";
  import "$lib/css/components-css/buttons.css";
  import "$lib/css/components-css/input.css";
  import "$lib/css/components-css/switch-button.css";
  import "$lib/css/pages-css/6-product-edit.css";

  import Input from "$lib/components/Input.svelte";
  import DinamicImage from "$lib/components/DinamicImage.svelte";
  import { toggleVariable } from "$lib/utils";
  import { InputTypes } from "$lib/components/InputTypes";
  import type { MenuItemType } from "$lib/domain/menuItem";

  // Recibir los datos del +page.ts
  let { data } = $props()
  const { menuItem } = data

//! =========== esto hay que cambiarloooooo ===========
  // Estados del formulario inicializados con los datos del menu item
  let inputValue: string = $state(menuItem?.nombre || "");
  let inputURL: string = $state(menuItem?.imagen || "");
  let descripcionValue: string = $state(menuItem?.descripcion || "");
  let precioValue: number = $state(menuItem?.precio || 0);



  let platoAutor: boolean = $state(false);
  let platoEnPromo: boolean = $state(false);
</script>

<!-- Content -->
<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title section-title">Editar Plato</h1>
    <form
      action=""
      id="form-product-edit"
      class="container-column form-product-edit"
    >
      <!-- Datos del Plato  -->
      <fieldset
        form="form-product-edit"
        name="product-info"
        class="content-section form-section-product-info"
      >
        <div class="container-column product-info">
          <div class="container-column">
            <!-- Aca esta bien el bind:value? -->
            <Input
              description="Nombre del Plato*"
              input_type={InputTypes.Normal}
              labelProps={{
                class: "w-100",
                for: "product-name",
              }}
              inputProps={{
                type: "text",
                class: "input-primary",
                id: "product-name",
                placeholder: "Hamburguesa triple completa",
              }}
            />
          </div>
          <!-- No rinde elemento. Unico con textarea -->
          <div class="container-column input-group">
            <label for="product-description" class="label-color"
              >Descripción*</label
            >
            <textarea
              id="product-description"
              maxlength="1000"
              minlength="100"
              class="input-primary description-textarea"
              placeholder="Hamburguesa triple con una deliciosa cebolla morada salteada que junto con el acomparamiento de lechuga, tomate y cheddar generan una sensación única e inugualable. Viene sin adherezos."
            ></textarea>
          </div>
          <div class="container-column">
            <Input
              description="URL de la imagen del plato*"
              input_type={InputTypes.Normal}
              labelProps={{
                class: "w-100",
                for: "url-product-img",
              }}
              inputProps={{
                type: "text",
                id: "url-product-img",
                class: "input-primary",
                placeholder: "/url/de/tu/imagen.png",
              }}
            />
          </div>
        </div>
        <div class="i">
          <!-- Revisar decrecimiento-->
          <DinamicImage
            imageURL = {inputURL}
            imageDescription = "product-load-img"
            imageProps={{
              class: "img-product-edit"
            }}
          />
        </div>
      </fieldset>
      <!-- Costos  -->
      <fieldset
        form="form-product-edit"
        name="product-cost"
        class="container-column content-section"
      >
        <h2 class="subtitle">Costos</h2>

        <div class="container-column input-group">
          <Input 
            description="Precio Base*"
            input_type={InputTypes.Normal}
            labelProps={{
              class: "w-100",
              for: "product-base-cost"
            }}
            inputProps={{
              type: "number",
              id: "product-base-cost",
              class: "input-primary number-input",
              placeholder: "Escribir numero..."
            }}
          />
        </div>

        <div class="switch-button-group">
          <label for="es-de-autor">
            <h3>Plato de Autor</h3>
            <p class="display-none-mobile label-color">
              Aplica un porcentaje adicional al precio de venta
            </p>
          </label>
          <div class="slide-button">
            <input type="checkbox" class="toggle" id="es-de-autor" onclick={() => platoAutor = toggleVariable(platoAutor)}/>
            <div class="background-div">
              <div class="circle-slide"></div>
            </div>
            <!-- Y esto como hacemos para que cuando se clickee haga el toggle de la variable? -->
          </div>
        </div>

        <div class="switch-button-group">
          <label for="en-promocion">
            <h3>Plato de Promoción</h3>
            <p class="display-none-mobile label-color">
              Aplica un descuento al precio de venta
            </p>
          </label>
          <div class="slide-button">
            <input type="checkbox" class="toggle" id="en-promocion" onclick={() => platoAutor = toggleVariable(platoAutor)}/>
            <div class="background-div">
              <div class="circle-slide"></div>
            </div>
          </div>
        </div>
      </fieldset>

      <fieldset
        form="form-product-edit"
        name="product-ingredients"
        class="container-column content-section form-section-product-ingredients"
      >
        <h2 class="subtitle product-edit-subtitle">Ingredientes</h2>
        <div class="product-ingredients-cost-subtitle">
          <h3 class="h3">Costo de Producción</h3>
          <p>$100</p>
        </div>

        <div class="grid-table-container product-edit-ingredients-table">
          <header class="grid-table-row table-header">
            <div class="cell" id="nombre">Nombre</div>
            <div class="cell" id="grupo-alimenticio">
              <span>Grupo</span>
              <span class="p-alimenticio display-none-mobile">Alimenticio</span>
            </div>
            <div class="cell col-centered" id="origen">Origen</div>
            <div class="cell col-centered" id="acciones">Acciones</div>
          </header>

          <div class="grid-table-row product-edit-ingredients-table-content">
            <div class="cell" id="nombre-1">Carne de Renacuajo</div>
            <div class="cell" id="grupo-alimenticio-1">Proteínas</div>
            <div class="cell col-centered">
              <i class="ph ph-cow" id="origen-1"></i>
            </div>
            <button
              disabled
              class="cell col-centered icon-action-btn"
              aria-label="delete-icon"
              ><i class="ph ph-trash" id="acciones-1"></i></button
            >
          </div>

          <div class="grid-table-row product-edit-ingredients-table-content">
            <div class="cell" id="nombre-2">Queso Cheddar</div>
            <div class="cell" id="grupo-alimenticio-2">Lacteos</div>
            <div class="cell col-centered">
              <i class="ph ph-cow" id="origen-2"></i>
            </div>
            <button
              disabled
              class="cell col-centered icon-action-btn"
              aria-label="delete-icon"
              ><i class="ph ph-trash" id="acciones-2"></i></button
            >
          </div>

          <div class="grid-table-row product-edit-ingredients-table-content">
            <div class="cell" id="nombre-3">Lechuga</div>
            <div class="cell" id="grupo-alimenticio-3">Frutas y Verduras</div>
            <div class="cell col-centered">
              <i class="ph ph-plant" id="origen-3"></i>
            </div>
            <button
              disabled
              class="cell col-centered icon-action-btn"
              aria-label="delete-icon"
              ><i class="ph ph-trash" id="acciones-3"></i></button
            >
          </div>

          <div class="grid-table-row product-edit-ingredients-table-content">
            <div class="cell" id="nombre-4">Tomate</div>
            <div class="cell" id="grupo-alimenticio-4">Frutas y Verduras</div>
            <div class="cell col-centered">
              <i class="ph ph-plant" id="origen-4"></i>
            </div>
            <button
              disabled
              class="cell col-centered icon-action-btn"
              aria-label="delete-icon"
              ><i class="ph ph-trash" id="acciones-4"></i></button
            >
          </div>
        </div>
      </fieldset>

      <section class="btn-group-actions">
        <button disabled class="btn btn-secondary btn-dish"
          >Descartar <span class="p-cambios display-none-mobile">Cambios</span
          ></button
        >
        <button disabled class="btn btn-primary btn-dish"
          >Guardar <span class="p-cambios display-none-mobile">Cambios</span
          ></button
        >
      </section>
    </form>
  </article>
</main>
