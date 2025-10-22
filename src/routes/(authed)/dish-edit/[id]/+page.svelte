<script lang="ts">
  import DinamicImage from "$lib/components/DinamicImage.svelte";
  import { toggleVariable } from "$lib/utils";
  import Ingredient from "$lib/components/Ingredient.svelte";
  import type { ValidationMessage } from "$lib/domain/validationMessage";
  import { showError } from "$lib/domain/errorHandler.js";
  import { menuItemsService } from "$lib/services/MenuItemService.js";
  import { MenuItemType, type MenuItemJSON } from "$lib/domain/menuItem.js";
  import { goto } from "$app/navigation";
  import { IngredientType } from "$lib/domain/ingredient.js";
  import ValidationField from "$lib/components/ValidationField.svelte";
  import { InputTypes } from "$lib/components/InputPropsI.js";
  import Modal from "$lib/components/Modal.svelte";
  import Input from "$lib/components/Input.svelte";
  import { toasts } from '$lib/components/toast/toastStore'
  import { ingredientService } from "$lib/services/IngredientService.js";
  import { AxiosError } from "axios";

  // Recibir los datos del +page.ts
  let { data } = $props()
  const { nuevoItem, item } = data
  
  let itemEdit = $state(item)
  let errors: ValidationMessage[] = $state([])
  let toastLock: boolean = false

  let platoAutor: boolean = $derived(item.esDeAutor);
  let platoEnPromo: boolean = $derived(item.enPromocion);

  let modalId: number = $state(0)

  let showModalAdd = $state(false)
  let showModalDelete = $state(false)
  let showModalDiscard = $state(false)

  const productionCost = $derived(itemEdit.ingredientes.reduce((acc, ing) => {return acc + ing.cost}, 0).toFixed(2))

  const newItem = $derived(() => {
    const ahora = new Date()
    const diferenciaMs = ahora.getTime() - itemEdit.fechaDeCreacion.getTime()
    const dias = diferenciaMs / (1000 * 60 * 60 * 24)
    return dias <= 30
  })

  const emptyIngredients = $derived(() => {
    return itemEdit.ingredientes.length == 0
  })

  const onSubmit = async (ev: SubmitEvent) => {
    const esNuevoItem = itemEdit.id == -1
    ev.preventDefault() // cancela el comportamiento por defecto del navegador frente al evento del submit
    
    // ev.currentTarget: es el elemento que tiene asignado el event listener
    // as HTMLFormElement es un type assertion de TypeScript: le decís explícitamente al compilador “esto es un formulario”
    const form = ev.currentTarget as HTMLFormElement
    const formData = new FormData(form) // creo el formData
    
    // Con form data
    const newItem: MenuItemType = new MenuItemType(
      itemEdit.id,
      (formData.get("name") ? formData.get("name") : itemEdit.nombre) as string,
      (formData.get("descripcion") ? formData.get("descripcion") : itemEdit.descripcion) as string,
      itemEdit.precio,
      Number(formData.get("valorBase") ? formData.get("valorBase") : itemEdit.valorBase),
      (formData.get("imagen") ? formData.get("imagen") : itemEdit.imagen) as string,
      +productionCost,
      platoAutor,
      platoEnPromo,
      itemEdit.ingredientes,
      itemEdit.fechaDeCreacion,
      Number(formData.get("porcentajeDescuento") ? formData.get("porcentajeDescuento") : itemEdit.porcentajeDescuento) /100, // porcentaje
    )
    // console.log(newItem.porcentajeDescuento)
    newItem.validate()
    // console.log("Validando nuevo plato:", newItem.errors)
    
    if (newItem.errors.length > 0) {
      errors = [...newItem.errors]
      return 
    }

    try {
      if (esNuevoItem) {
        // console.info("Nuevo Plato:", newItem)
        await menuItemsService.createMenuItem(newItem)
        toasts.push('Plato generado exitosamente. Seras redirigido a Menu', {type: 'success'})
      } else {
        // console.info("Nuevo Plato:", newItem)
        await menuItemsService.updateMenuItem(newItem)
        toasts.push('Plato modificado con exito. Seras redirigido a Menu', {type: 'success'})
      }
      setTimeout(() => {
        goto("/menu")
      }, 2000)
      errors = [] // limpiar errores
    } catch (error) {
      // if (error instanceof AxiosError) {
      //   if(!toastLock) {
      //     toasts.push("Error al generar el plato", {type: 'error'})
      //     setTimeout(releaseToast, 5000)
      //   }
      showError("Error al generar plato", error)
    }
  }

  const releaseToast = () => {
    toastLock = false
  }  
  
  const deleteItem = (ingredientId: number, event: MouseEvent) => {  
    if (event) {
      event.preventDefault()
      event.stopPropagation()
    }
    itemEdit.ingredientes = itemEdit.ingredientes.filter(i => i.id !== ingredientId)
    itemEdit = {...itemEdit} as MenuItemType
    showModalDelete = false
  }

  let selectedIngs: IngredientType[] = $state([])
  let availableIngs: IngredientType[] = $state([])

  const fetchIng = async () => {
    try {
      const allIngs = await ingredientService.getAllIngredients()
      const itemIngs = itemEdit.ingredientes.map(it => it.id as number)
      availableIngs = MenuItemType.availableIngs(allIngs, itemIngs)
      showModalAdd = true
    } catch (error) {
      console.error(error)
    }
  }
  
  const guardarModal = () => {
    showModalAdd = false
    selectedIngs.forEach(it => itemEdit.ingredientes.push(it))
    itemEdit = {...itemEdit} as MenuItemType
    selectedIngs.length = 0
  }

  const descartarModal = () => {
    showModalAdd = false
    selectedIngs.length = 0
  }

  function openModalDelete(id: number) {
    modalId = id
    showModalDelete = true
  }

  function discardChanges(event: MouseEvent) {
    if (event) {
      event.preventDefault()
      event.stopPropagation()
    }
    goto("/menu")
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
    color: white;
    background-color: var(--icon-color);
    padding: 0.3em 1em;
    font-size: 1em;
    border-radius: 1em;
    position: absolute;
    right: 0;
  }

  .no-promocion {
    color: var(--alternative-color);
    text-align: center;
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
              value={item.nombre}
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
              value = {item.descripcion}
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
              value={item.imagen}
              placeholder="Escribir |"
            />
            <ValidationField errors={errors} field="imagen" />
          </div>
        </div>
        <div class="image-product-edit img-edit-container">
          <DinamicImage
            imageURL = {item.imagen}
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
              value={item.valorBase}
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

      {#if newItem()}
        <p class="no-promocion">Los platos nuevos no pueden estar en promocion. 
          Esta funcionalidad se habilitará luego de 30 días de creado el plato.</p>
          <p class="no-promocion">¡Los platos nuevos se generaran automaticamente con un 30% de descuento!</p>
      {:else}
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
        
        {#if platoEnPromo}
          <div class="container-column input-group">
            <Input
            label_text="Porcentaje de Descuento*"
            label_for="product-discount-percentage"
            input_type={InputTypes.Normal}
            type="number"
            class="input-primary number-input"
            id="product-discount-percentage"
            name="porcentajeDescuento"
            value={itemEdit.porcentajeDescuento}
            placeholder="%"
            step="any"
            />
            <ValidationField errors={errors} field="porcentajeDescuento" />
          </div>
        {/if}
        <p class="label-color">El descuento se aplicará sobre el precio final del plato.</p>
      {/if}
      </fieldset>

      <fieldset
        form="form-product-edit"
        name="product-ingredients"
        class="container-column content-section form-section-product-ingredients"
      >
        <h2 class="subtitle product-edit-subtitle">Ingredientes</h2>
        <div class="product-ingredients-cost-subtitle w-100">
          <h3 class="h3">Costo de Producción</h3>
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
                    {ingr.name} ${ingr.cost}
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
            <Ingredient ingredient={ing}/>
            <section class="cell multiple-action-buttons">
              <!-- <button type="button" class="icon-action-btn" onclick={() => goto (`/ingredient-edit/${ing.id}`)} aria-label="Editar"><i class="ph ph-pencil gray-icon"></i></button>
              <span><i class="ph ph-line-vertical gray-icon"></i></span> -->
              <button type="button" class="icon-action-btn" onclick={() =>{deleteItem ; openModalDelete(ing.id as number);}} aria-label="Eliminar"><i class="ph ph-trash gray-icon"></i></button>
            </section>
          </article>            
        {/each}
         
        </div>
      </fieldset>
      {#if emptyIngredients()}
        <ValidationField errors={errors} field="ingredients" />        
      {/if}
      {#if showModalDelete && modalId}
        <Modal
          title={`¿Seguro que querés eliminar el ingrediente "${itemEdit.ingredientes.find(i => i.id === modalId)?.name}"?`} 
          confirmLabel="Sí"
          cancelLabel="No"
          actionConfirm={(e) => deleteItem(modalId, e)}
          actionCancel={() => showModalDelete = false}
        />
      {/if}

      <section class="btn-group-actions">
        <button class="btn btn-secondary btn-dish" type="button" onclick={() => showModalDiscard = true}
          >Descartar <span class="p-cambios display-none-mobile">Cambios</span></button
        >
        {#if showModalDiscard}
          <Modal
            title={`¿Seguro que querés descartar los cambios?`} 
            confirmLabel="Sí"
            cancelLabel="No"
            actionConfirm={(e) => discardChanges(e)}
            actionCancel={() => showModalDiscard = false}
          />
        {/if}
        <button class="btn btn-primary btn-dish" type="submit"
          >Guardar <span class="p-cambios display-none-mobile">Cambios</span></button
        >
      </section>
    </form>
  </article>
</main>
