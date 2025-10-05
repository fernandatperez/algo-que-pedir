<script lang="ts">
  import Input from "$lib/components/Input.svelte";
  import DinamicImage from "$lib/components/DinamicImage.svelte";
  import { toggleVariable } from "$lib/utils";
  import Ingredient from "$lib/components/Ingredient.svelte";
  import type { ValidationMessage } from "$lib/domain/validationMessage";
  import { showError } from "$lib/domain/errorHandler.js";
  import { menuItemsService } from "$lib/services/MenuItemService.js";
  import { MenuItemType } from "$lib/domain/menuItem.js";
  import { goto } from "$app/navigation";
  import { GLOBAL_ID } from "$lib/services/MenuItemService.js";
  import { MENU_ITEMS_JSON_MOCK } from "$lib/data/mock/menuItems.js";
  import { INGREDIENT_MOCK } from "$lib/data/mock/ingredients.js"
  import { IngredientType, type IngredientJSON } from "$lib/domain/ingredient.js";
  import ValidationField from "$lib/components/ValidationField.svelte";
  import InputNew from "$lib/components/InputNew.svelte";
  import { InputTypes } from "$lib/components/InputPropsI.js";
  import Modal from "$lib/components/Modal.svelte";
  import MenuItem from "$lib/components/MenuItem.svelte";
  import { toasts } from '$lib/components/toast/toastStore'

  /*
    TODO
      Se fue el padding entre inputs por que esta comentado en login.css
  
  */

  // Recibir los datos del +page.ts
  let { data } = $props()
  const { nuevoItem, item } = data

  let errors: ValidationMessage[] = $state([])
  let toastLock: boolean = false

  let platoAutor: boolean = $state(false);
  let platoEnPromo: boolean = $state(false);

  let itemEdit = $state({...item})
  let modalId: number = $state(0)

  let showModalAdd = $state(false)
  let showModalDelete = $state(false)


  const onSubmit = async (ev: SubmitEvent) => {
    const esNuevoItem = itemEdit.id == -1
    ev.preventDefault() // cancela el comportamiento por defecto del navegador frente al evento del submit
    
    // ev.currentTarget: es el elemento que tiene asignado el event listener
    // as HTMLFormElement es un type assertion de TypeScript: le decís explícitamente al compilador “esto es un formulario”
    const form = ev.currentTarget as HTMLFormElement
    const formData = new FormData(form) // creo el formData
    
    // Con form data
    const menuItem: MenuItemType = new MenuItemType(
      (itemEdit.id == -1 ? GLOBAL_ID() : itemEdit.id),
      itemEdit.alt,
      (formData.get("nombre") ? formData.get("nombre") : itemEdit.nombre) as string,
      (formData.get("descripcion") ? formData.get("descripcion") : itemEdit.descripcion) as string,
      (formData.get("precio") ? formData.get("precio") : itemEdit.precio) as number,
      (formData.get("imagen") ? formData.get("imagen") : itemEdit.imagen) as string,
      Boolean(formData.get("esDeAutor") ? formData.get("author-dish") : itemEdit.esDeAutor),
      Boolean(formData.get("enPromocion") ? formData.get("enPromocion") : itemEdit.enPromocion),
      itemEdit.ingredientes
    )
    
    console.info("El plato modificado quedo asi: ", menuItem)
    
    menuItem.validate()
    
    if (menuItem.errors.length > 0) {
      errors = [...menuItem.errors]
      console.info(errors)
      return 
    }
    
    try {
      if (esNuevoItem) {
        await menuItemsService.createMenuItem(menuItem)
      } else {
        await menuItemsService.updateMenuItem(menuItem)
      }
      console.info(MENU_ITEMS_JSON_MOCK) 
      // Aca poner un toast de guardado exitoso
      setTimeout(() => {
        goto("/menu")
      }, 3000)
      errors = [] // limpiar errores
    } catch (error) {
      if(!toastLock) {
        toasts.push("Error al crear el ingrediente", {type: 'error'})
        setTimeout(releaseToast, 5000)
      }
      showError("Error al crear el ingrediente", error)
    }
  }

  const findItem = async () => {
    try{
      itemEdit = await menuItemsService.getMenuItem(itemEdit.id)
    } catch (error){
      if(!toastLock) {
        toasts.push('Conexion al servidor fallida', {type: 'error'})
        setTimeout(releaseToast, 5000)
      }
      showError('Conexion al servidor fallida', error)
    }
  }

  // const removeItem = (id?: number) => {
  //   const itemIndex = itemEdit.ingredientes.findIndex(item => item.id == id)
  //   itemEdit.ingredientes.splice(itemIndex, 1)
  //   selectedIngs = itemEdit.ingredientes
  //   updateAvailables()
  // }

  const deleteItem = (ingredientId: number) => {
    const index = itemEdit.ingredientes.findIndex(i => i.id === ingredientId)
    if (index !== -1) {
      itemEdit.ingredientes.splice(index, 1)
      updateAvailables()
    }
    showModalDelete = false
  }


  const discardBtn = () => {
    // Aca deberia aparecer el cartel de dana de confirmar descartar
      goto("/menu")
  }
  
  let availableIngredients: IngredientJSON[] = $state(
    itemEdit.ingredientes && itemEdit.ingredientes.length > 0
      ? INGREDIENT_MOCK.filter(
        ing => !itemEdit.ingredientes.some(sel => sel.id === ing.id))
      : INGREDIENT_MOCK
  )
  
  let selectedIngs: IngredientType[] = $state([])
  
  const updateAvailables = () => {
    availableIngredients = INGREDIENT_MOCK.filter(
    ing => !itemEdit.ingredientes.some(itemIng => itemIng.id === ing.id))
    selectedIngs = []
  }

  const guardarModal = () => {
    showModalAdd = false
    selectedIngs.forEach(ing => itemEdit.ingredientes.push(ing))
    updateAvailables()
  }

  const descartarModal = () => {
    showModalAdd = false
  }

  const releaseToast = () => {
    toastLock = false
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
    position: absolute;
    right: 0;
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
            <InputNew
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
            <InputNew
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

        <div class="container-column">
          <InputNew
              label_text="Precio Base*"
              label_for="product-base-cost"
              input_type={InputTypes.Normal}
              type="number"
              class="input-primary number-input"
              id="product-base-cost"
              name="precio"
              bind:value={itemEdit.precio}
              placeholder="Escribir |"
            />
          <ValidationField errors={errors} field="precio" />
        </div>

        <div class="switch-button-group">
          <label for="es-de-autor">
            <h3>Plato de Autor</h3>
            <p class="display-none-mobile label-color">
              Aplica un porcentaje adicional al precio de venta
            </p>
          </label>
          <div class="slide-button">
            <input type="checkbox" class="toggle" id="es-de-autor" name="esDeAutor" onclick={() => platoAutor = toggleVariable(platoAutor)}/>
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
            <input type="checkbox" class="toggle" id="en-promocion" name="enPromocion" onclick={() => platoEnPromo = toggleVariable(platoEnPromo)}/>
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
          <p>${MenuItemType.costoDeProduccion(itemEdit)}</p>
          <button type="button" class="add-ingredient-btn" onclick={() => showModalAdd = true}>Añadir ingrediente</button>
          
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
              {#if availableIngredients.length != 0}
                {#each availableIngredients as ingr}
                  <label>
                    <input type="checkbox" bind:group={selectedIngs} value={ingr}>
                    {ingr.name}
                  </label>
                  <br>
                {/each}
              {:else}
                <span>No hay ingredientes para mostrar</span>
              {/if}
            {/snippet}
          </Modal>
        {/if}
          <!-- <div class="modal">
            <h3>Seleccionar ingredientes</h3>
            {/if}
            <button type="button" onclick={guardarModal}>Guardar</button>
          </div>
        {/if} -->
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
                <button type="button" class="icon-action-btn" onclick={() => goto (`/ingredient-edit/${ing.id}`)} aria-label="Editar"><i class="ph ph-pencil gray-icon"></i></button>
                <span><i class="ph ph-line-vertical gray-icon"></i></span>
                <button type="button" class="icon-action-btn" onclick={() =>{deleteItem ; openModal(ing.id as number);}} aria-label="Eliminar"><i class="ph ph-trash gray-icon"></i></button>
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