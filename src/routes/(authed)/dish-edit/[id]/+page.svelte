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
  import type { ValidationMessage } from "$lib/domain/ingredient.js";
  import { showError } from "$lib/domain/errorHandler.js";
  import { menuItemsService } from "$lib/services/MenuItemService.js";
  import { MenuItemType } from "$lib/domain/menuItem.js";
  import { goto } from "$app/navigation";

  // Recibir los datos del +page.ts
  let { data } = $props()
  const { nuevoItem, item } = data
  let errors: ValidationMessage[] = $state([])

  let platoAutor: boolean = $state(false);
  let platoEnPromo: boolean = $state(false);

  const itemEdit = $state(item.toJSON())

  const onSubmit = async (ev: SubmitEvent) => {
    ev.preventDefault() // cancela el comportamiento por defecto del navegador frente al evento del submit
    
    // ev.currentTarget: es el elemento que tiene asignado el event listener
    // as HTMLFormElement es un type assertion de TypeScript: le decís explícitamente al compilador “esto es un formulario”
    const form = ev.currentTarget as HTMLFormElement
    const formData = new FormData(form) // creo el formData
    
    // Con form data
    const menuItem: MenuItemType = new MenuItemType(
      itemEdit.id = 100,
      itemEdit.alt,
      (formData.get("product-name") ? formData.get("product-name") : itemEdit.nombre) as string,
      (formData.get("product-description") ? formData.get("product-description") : itemEdit.descripcion) as string,
      Number(formData.get("product-cost") ? formData.get("product-cost") : itemEdit.precio),
      (formData.get("url-product-image") ? formData.get("url-product-image") : itemEdit.imagen) as string,
      Boolean(formData.get("author-dish") ? formData.get("author-dish") : itemEdit.esDeAutor),
      Boolean(formData.get("on-promo-dish") ? formData.get("on-promo-dish") : itemEdit.enPromocion)
    )

    // Dodains way
    // Para este tendriamos que bindear los values de los componentes al valor del itemEdit
    // const menuItem: MenuItemType = MenuItemType.fromJson(itemEdit)
    
    console.info("El plato modificado quedo asi: ", menuItem)

    menuItem.validate()

    if (menuItem.errors.length > 0) {
      errors = [...menuItem.errors]
      return
    }

    try {
      await menuItemsService.updateMenuItem(menuItem)
      errors = [] // limpiar errores
    } catch (error) {
      showError("Error al crear el ingrediente", error)
    }

  }

  const removeItem = () => {}
</script>

<!-- Content -->
<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title-dish">{nuevoItem ? "Crear plato" : "Editar plato"}</h1>
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
                name: "product-name",
                placeholder: itemEdit.nombre,
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
              name="product-description"
              placeholder= {itemEdit.descripcion}
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
                name: "url-product-img",
                placeholder: itemEdit.imagen,
              }}
            />
          </div>
        </div>
        <div class="i">
          <!-- Revisar decrecimiento-->
          <DinamicImage
            imageURL = {itemEdit.imagen}
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
              name: "product-cost",
              placeholder: itemEdit.precio
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
            <input type="checkbox" class="toggle" id="es-de-autor" name="author-dish" onclick={() => platoAutor = toggleVariable(platoAutor)}/>
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
            <input type="checkbox" class="toggle" id="en-promocion" name="on-promo-dish" onclick={() => platoEnPromo = toggleVariable(platoEnPromo)}/>
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
        <div class="product-ingredients-cost-subtitle w-100">
          <h3 class="h3">Costo de Producción</h3>
          <!-- Suma del costo de todos los ingredientes -->
           <!-- Va a venir del back -->
          <p>${MenuItemType.costoDeProduccion(itemEdit)}</p>
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
          {#each itemEdit.ingredientes as ing}
            <article class="grid-table-row product-edit-ingredients-table-content">
              <Ingredient ingredient={ing} />
              <section class="cell multiple-action-buttons">
                <!-- Esto ahora funciona....................... -->
                <button class="icon-action-btn" onclick={() => goto (`/ingredient-edit/${ing.id}`)} aria-label="Editar"><i class="ph ph-pencil gray-icon"></i></button>
                <span><i class="ph ph-line-vertical gray-icon"></i></span>
                <!-- Esto sigue sin funcionar -->
                <button class="icon-action-btn" onclick={removeItem} aria-label="Eliminar"><i class="ph ph-trash gray-icon"></i></button>
              </section>
            </article>
          {/each}
        </div>
      </fieldset>

      <section class="btn-group-actions">
        <button disabled class="btn btn-secondary btn-dish"
          >Descartar <span class="p-cambios display-none-mobile">Cambios</span></button
        >
        <button class="btn btn-primary btn-dish" type="submit" onclick={() => goto('/menu')}
          >Guardar <span class="p-cambios display-none-mobile">Cambios</span></button
        >
      </section>
    </form>
  </article>
</main>

<style>
  .header-title-dish {
    font-size: var(--font-h1);
    font-weight: 700;
    font-style: normal;
    align-self: flex-start;
    margin: 0.5em 0em;
  }
</style>