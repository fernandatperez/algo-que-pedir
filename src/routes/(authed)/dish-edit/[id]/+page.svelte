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
  import Ingredient from "$lib/components/Ingredient.svelte";
  import { goto } from "$app/navigation";
    import type { IngredientType } from "$lib/domain/ingredient.js";

  // Recibir los datos del +page.ts
  let { data } = $props()
  const { title, menuItem } = data

  let platoAutor: boolean = $state(false);
  let platoEnPromo: boolean = $state(false);

  const onSubmit = () => {

  }

  const irAEditar = (ing: IngredientType) => {
    // No anda esto ni idea Pablo resolvelo porfa
    // window.location.href = `/ingredient-edit/${ing.id}`  este funciona pero es medio villero
    goto(`/ingredient-edit/${ing.id}`)
  }

  const eliminarIng = (ing: IngredientType) => {
    // Copy paste del de Dana
    console.info('Ingrediente DELETED') 
  }
</script>

<!-- Content -->
<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title">{title}</h1>
    <form
      onsubmit={onSubmit}
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
                placeholder: menuItem.nombre,
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
              placeholder= {menuItem.descripcion}
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
                placeholder: menuItem.imagen,
              }}
            />
          </div>
        </div>
        <div class="i">
          <!-- Revisar decrecimiento-->
          <DinamicImage
            imageURL = {menuItem.imagen}
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
              placeholder: menuItem.precio
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
            <!-- Esto ya tiene una variable asignada al toggle, en el POST asignamos la variable a la de la clase -->
            <input type="checkbox" class="toggle" id="es-de-autor" onclick={() => platoAutor = toggleVariable(platoAutor)}/>
            <div class="background-div">
              <div class="circle-slide"></div>
            </div>
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
            <input type="checkbox" class="toggle" id="en-promocion" onclick={() => platoEnPromo = toggleVariable(platoEnPromo)}/>
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
          <!-- Suma del costo de todos los ingredientes -->
           <!-- Va a venir del back -->
          <div class="add-ing-button-price">
            <p>${menuItem.costoDeProduccion()}</p>
            <p>HOLA!</p>
          </div>
        </div>
        <div class="grid-table-container product-edit-ingredients-table">
          <header class="grid-table-row table-header">
            <section class="cell" id="name">Nombre</section>
            <section class="cell" id="name">Costo</section>
            <section class="cell later-hid" id="grupo-alimenticio">
              <span> Grupo </span>
              <span class="p-alimenticio display-none-mobile"> Alimenticio </span>
            </section>
            <section class="cell col-centered later-hid" id="origen">
              Origen
            </section>
            <section class="cell col-centered" id="acciones">Acciones</section>
          </header>
          {#each menuItem.ingredientes as ing}
            <article class="grid-table-row product-edit-ingredients-table-content">
              <Ingredient ingredient={ing} />
              <section class="cell multiple-action-buttons">
                <button disabled class="icon-action-btn hidden-icons" aria-label="Ver"><i class="ph ph-eye gray-icon"></i></button>
                <span><i class="ph ph-line-vertical gray-icon hidden-icons"></i></span>
                <button class="icon-action-btn" onclick={() => eliminarIng(ing)} aria-label="Eliminar"><i class="ph ph-trash gray-icon"></i></button>
              </section>
            </article>
          {/each}
        </div>
      </fieldset>

      <section class="btn-group-actions">
        <button disabled class="btn btn-secondary btn-dish"
          >Descartar <span class="p-cambios display-none-mobile">Cambios</span></button
        >
        <button disabled class="btn btn-primary btn-dish"
          >Guardar <span class="p-cambios display-none-mobile">Cambios</span></button
        >
      </section>
    </form>
  </article>
</main>

<style>
  .add-ing-button-price {
    display: flex;
    gap: 10em;
  }
</style>
