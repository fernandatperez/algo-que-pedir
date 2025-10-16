<script lang="ts">
  import DinamicImage from "$lib/components/DinamicImage.svelte";
  import { toggleVariable } from "$lib/utils";
  import Ingredient from "$lib/components/Ingredient.svelte";
  import type { ValidationMessage } from "$lib/domain/validationMessage";
  import { showError } from "$lib/domain/errorHandler.js";
  import { menuItemsService } from "$lib/services/MenuItemService.js";
  import { MenuItemType } from "$lib/domain/menuItem.js";
  import { goto } from "$app/navigation";
  import { IngredientType } from "$lib/domain/ingredient.js";
  import ValidationField from "$lib/components/ValidationField.svelte";
  import { InputTypes } from "$lib/components/InputPropsI.js";
  import Modal from "$lib/components/Modal.svelte";
  import Input from "$lib/components/Input.svelte";
  import { toasts } from '$lib/components/toast/toastStore'
  import { ingredientService } from "$lib/services/IngredientService.js";

  // Recibir los datos del +page.ts
  let { data } = $props()
  const { nuevoItem, item } = data
  
  let itemEdit = $state(item.toJSON())
  // console.info("INGREDIENTES:", itemEdit.ingredientes[0])
  let errors: ValidationMessage[] = $state([])
  let toastLock: boolean = false

  let platoAutor: boolean = $derived(itemEdit.esDeAutor);
  let platoEnPromo: boolean = $derived(itemEdit.enPromocion);

  let modalId: number = $state(0)

  let showModalAdd = $state(false)
  let showModalDelete = $state(false)

  const productionCost = $derived(itemEdit.ingredientes.reduce((acc, ing) => {return acc + ing.cost}, 0).toFixed(2))

  const onSubmit = async (ev: SubmitEvent) => {
    const esNuevoItem = itemEdit.id == -1
    ev.preventDefault() // cancela el comportamiento por defecto del navegador frente al evento del submit
    
    // ev.currentTarget: es el elemento que tiene asignado el event listener
    // as HTMLFormElement es un type assertion de TypeScript: le decís explícitamente al compilador “esto es un formulario”
    const form = ev.currentTarget as HTMLFormElement
    const formData = new FormData(form) // creo el formData
    
    // Con form data
    const menuItem: MenuItemType = new MenuItemType(
      itemEdit.id,
      (formData.get("nombre") ? formData.get("nombre") : itemEdit.nombre) as string,
      (formData.get("descripcion") ? formData.get("descripcion") : itemEdit.descripcion) as string,
      itemEdit.precio,
      (formData.get("valorBase") ? formData.get("valorBase") : itemEdit.valorBase) as number,
      (formData.get("imagen") ? formData.get("imagen") : itemEdit.imagen) as string,
      itemEdit.costoProduccion,
      platoAutor,
      platoEnPromo,
      itemEdit.ingredientes
    )
    console.info("MENU ITEM GENERADO:", menuItem)
    menuItem.validate()
    
    if (menuItem.errors.length > 0) {
      errors = [...menuItem.errors]
      return 
    }
    
    try {
      if (esNuevoItem) {
        await menuItemsService.createMenuItem(menuItem)
        toasts.push('Plato generado exitosamente. Seras redirigido a Menu', {type: 'success'})
      } else {
        await menuItemsService.updateMenuItem(menuItem)
        toasts.push('Plato modificado con exito. Seras redirigido a Menu', {type: 'success'})
      }
      // Aca poner un toast de guardado exitoso
      setTimeout(() => {
        goto("/menu")
      }, 2000)
      errors = [] // limpiar errores
    } catch (error) {
      if(!toastLock) {
        toasts.push("Error al generar el plato", {type: 'error'})
        setTimeout(releaseToast, 5000)
      }
      showError("Algo fallo.", error)
    }
  }

  const releaseToast = () => {
    toastLock = false
  }  
  
  const deleteItem = (ingredientId: number) => {
    const index = itemEdit.ingredientes.findIndex(i => i.id == ingredientId)
    if (index != -1) {
      itemEdit.ingredientes.splice(index, 1)
      selectedIngs.length = 0
    }
    showModalDelete = false
  }

  const discardBtn = () => {
    // Aca deberia aparecer el cartel de dana de confirmar descartar
      goto("/menu")
  }
  
  let selectedIngs: IngredientType[] = $state([])
  let availableIngs: IngredientType[] = $state([])

  const fetchIng = async () => {
    try {
      const allIngs = await ingredientService.getAllIngredients()
      console.info(allIngs)
      availableIngs = MenuItemType.availableIngs(allIngs, itemEdit)
      console.info(availableIngs)
      showModalAdd = true
    } catch (error) {
      console.error(error)
    }
  }
  
  // let availableIngs: IngredientType[] = fetchIng

  const guardarModal = () => {
    showModalAdd = false
    selectedIngs.forEach(it => itemEdit.ingredientes.push(it))
    selectedIngs.length = 0 // ??
  }

  const descartarModal = () => {
    showModalAdd = false
    selectedIngs.length = 0 // ??
  }

  function openModal(id: number) {
    modalId = id
    showModalDelete = true
  }

</script>

<style>
  @import url("$lib/css/flex-grid.css");
  @import url("$lib/css/components-css/number-input.css");
  @import url("$lib/css/components-css/grid-table.css");
  @import url("$lib/css/components-css/buttons.css");
  @import url("$lib/css/components-css/input.css");
  @import url("$lib/css/components-css/switch-button.css");
  @import url("$lib/css/components-css/icon.css");
  @import url("$lib/css/pages-css/7-ingredients.css");
  @import url("$lib/css/pages-css/6-product-edit.css");

  .header-title-dish {
    font-size: var(--font-h1);
    font-weight: 700;
    font-style: normal;
    align-self: flex-start;
    margin: 0.5em 0em;
  }

  .add-ingredient-btn {
    background-color: var(--background-color-secondary);
    padding: 0.3em 1em;
    font-size: 1em;
    border-radius: 1em;
    position: absolute;
    right: 0;
  }

  @media screen and (max-width: 430px) {
    .product-edit-ingredients-table .grid-table-row {
      grid-template-columns: 2fr 1.5fr 1.5fr;  
    }
  }
</style>

<main class="container-column">
  <article class="container-column main-content">
    <h1 class="header-title-dish">{nuevoItem ? "Crear plato" : "Editar plato"}</h1>
    <form
      onsubmit={onSubmit}
      id="form-product-edit"
      class="container-column form-product-edit"
    >
      <fieldset
        form="form-product-edit"
        name="product-info"
        class="content-section form-section-product-info"
      >
        <div class="container-column product-info">
          <div class="container-column">
            <Input
              label_text="Nombre del Plato*"
              label_for="nombre"
              input_type={InputTypes.Normal}
              type="text"
              class="input-primary"
              id="product-name"
              name="name"
              bind:value={itemEdit.nombre}
              placeholder="Escribir |"
            />
            <ValidationField errors={errors} field="nombre" />
          </div>
          <div class="container-column input-group">
            <label for="product-description" class="label-color"
              >Descripción*</label
            >
            <textarea
              id="product-description"
              maxlength="1000"
              minlength="100"
              class="input-primary description-textarea"
              name="descripcion"
              value = {itemEdit.descripcion}
            ></textarea>
          </div>
          <div class="container-column">
            <Input
              label_text="URL de la imagen del plato*"
              label_for="url-product-img"
              input_type={InputTypes.Normal}
              type="text"
              class="input-primary"
              id="url-product-img"
              name="imagen"
              bind:value={itemEdit.imagen}
              placeholder="Escribir |"
            />
            <ValidationField errors={errors} field="imagen" />
          </div>
        </div>
        <div class="image-product-edit img-edit-container">
          <DinamicImage
            imageURL = {itemEdit.imagen}
            imageDescription = "product-load-img"
            imageProps={{
              class: "img-product-edit"
            }}
          />
        </div>
      </fieldset>
      <fieldset
        form="form-product-edit"
        name="product-cost"
        class="container-column content-section"
      >
        <h2 class="subtitle">Costos</h2>

        <div class="container-column input-group">
          <Input
              label_text="Precio Base*"
              label_for="product-base-cost"
              input_type={InputTypes.Normal}
              type="number"
              class="input-primary number-input"
              id="product-base-cost"
              name="valorBase"
              bind:value={itemEdit.valorBase}
              placeholder="Escribir |"
              step="any"
            />
          <ValidationField errors={errors} field="valorBase" />
        </div>

        <div class="switch-button-group">
          <label for="es-de-autor">
            <h3>Plato de Autor</h3>
            <p class="display-none-mobile label-color">
              Aplica un porcentaje adicional al precio de venta
            </p>
          </label>
          <div class="slide-button">
            <input
              type="checkbox"
              class="toggle"
              id="es-de-autor"
              name="esDeAutor"
              onclick={() => platoAutor = toggleVariable(platoAutor)}
              checked={platoAutor}
              />
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
            <input type="checkbox"
              class="toggle"
              id="en-promocion"
              name="enPromocion"
              onclick={() => platoEnPromo = toggleVariable(platoEnPromo)}
              checked={platoEnPromo}
              />
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
          <!-- Aca se agrega este $derived para que se muestre reactivamente. Cuando se guarda el menuItem lo que se envia es el costo de produccion del elemento. -->
          <p>${productionCost}</p>
          <button type="button" class="add-ingredient-btn" onclick={fetchIng}>Añadir ingredientes</button>
          
        </div>
        {#if showModalAdd}
          <Modal
            title="Seleccionar ingredientes"
            confirmLabel="Guardar"
            cancelLabel="Descartar"
            actionConfirm={guardarModal}
            actionCancel={descartarModal}
          >
            {#snippet children()}
              {#if availableIngs.length != 0}
                {#each availableIngs as ingr (ingr.id)}
                <div class="modal-checkbox">
                  <label>
                    <input type="checkbox" bind:group={selectedIngs} value={ingr}>
                    {ingr.name}
                  </label>
                  <br>
                </div>
                {/each}
              {:else}
                <span>No hay ingredientes para mostrar</span>
              {/if}
            {/snippet}
          </Modal>
        {/if}

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
        {#each itemEdit.ingredientes as ing (ing.id)}
          <article class="grid-table-row product-edit-ingredients-table-content">
            <Ingredient ingredient={ing} />
            <section class="cell multiple-action-buttons">
              <button type="button" class="icon-action-btn" onclick={() => goto (`/ingredient-edit/${ing.id}`)} aria-label="Editar"><i class="ph ph-pencil gray-icon"></i></button>
              <span><i class="ph ph-line-vertical gray-icon"></i></span>
              <button type="button" class="icon-action-btn" onclick={() =>{deleteItem ; openModal(ing.id as number);}} aria-label="Eliminar"><i class="ph ph-trash gray-icon"></i></button>
              <p>{ing.id}</p>
              <!-- <button type="button" class="icon-action-btn" onclick={() => removeItem(ing.id)} aria-label="Eliminar"><i class="ph ph-trash gray-icon"></i></button> -->
            </section>
          </article>            
        {/each}
         
        </div>
      </fieldset>
      {#if showModalDelete && modalId}
        <Modal
          title={`¿Seguro que querés eliminar el ingrediente "${itemEdit.ingredientes.find(i => i.id === modalId)?.name}"?`} 
          confirmLabel="Sí"
          cancelLabel="No"
          actionConfirm={() => deleteItem(modalId)}
          actionCancel={() => showModalDelete = false}
        />
      {/if}

      <section class="btn-group-actions">
        <button class="btn btn-secondary btn-dish" type="reset" onclick={discardBtn}
          >Descartar <span class="p-cambios display-none-mobile">Cambios</span></button
        >
        <button class="btn btn-primary btn-dish" type="submit"
          >Guardar <span class="p-cambios display-none-mobile">Cambios</span></button
        >
      </section>
    </form>
  </article>
</main>