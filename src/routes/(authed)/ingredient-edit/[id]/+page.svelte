<script lang="ts">
  import '$lib/css/flex-grid.css'
  import "$lib/css/components-css/switch-button.css"
  import "$lib/css/components-css/icon.css"
  import "$lib/css/components-css/buttons.css"
  import "$lib/css/pages-css/8-ingredient-edit.css"
  
  import { goto } from '$app/navigation'
  import { foodGroupDict, type FoodGroupValue } from '$lib/domain/ingredient'
  import type { ValidationMessage } from '$lib/domain/validationMessage.js'
  import { IngredientType} from '$lib/domain/ingredient'
  import { ingredientService } from '$lib/services/IngredientService.js'
  import { showError } from '$lib/domain/errorHandler.js'
  import ValidationField from '$lib/components/ValidationField.svelte'
  import { InputTypes } from "$lib/components/InputPropsI.js"
  import { toasts } from '$lib/components/toast/toastStore'
  import Input from '$lib/components/Input.svelte';

  let { data } = $props()
  const { ingredient } = data
  
  let errors: ValidationMessage[] = $state([])
  let toastLock: boolean = false

  let ingredientEdit = $state({...ingredient})

  let ingredientLock = $derived(
    ingredient?.name != ingredientEdit?.name || ingredient?.cost != ingredientEdit?.cost ||
    ingredient?.foodGroup != ingredientEdit?.foodGroup || ingredient?.esOrigenAnimal != ingredientEdit?.esOrigenAnimal
  )
  
  const onSubmit = async (ev: SubmitEvent) => {
    ev.preventDefault() // cancela el comportamiento por defecto del navegador frente al evento del submit

    // ev.currentTarget: es el elemento que tiene asignado el event listener
    // as HTMLFormElement es un type assertion de TypeScript: le decís explícitamente al compilador “esto es un formulario”
    const form = ev.currentTarget as HTMLFormElement
    const formData = new FormData(form) // creo el formData

    const ingredient = new IngredientType(
      ingredientEdit?.id,
      (formData.get("name") ?? "").toString(),
      Number(formData.get("cost") ?? 0),
      (formData.get("foodGroup") ?? "") as FoodGroupValue,
      ingredientEdit.esOrigenAnimal,
    )
    console.info("el nuevo ingrediente es ", ingredient)

    ingredient.validate()

    if (ingredient.errors.length > 0) {
      errors = [...ingredient.errors]
      return
    }

    try {
      await ingredientService.updateIngredient(ingredient)
      goto("/ingredients")
      errors = [] // limpiar errores
    } catch (error) {
      if(!toastLock) {
        toasts.push('Error al crear el ingrediente', {type: 'error'})
        toastLock = true
        setTimeout(releaseToast, 5000)
      }
      showError("Error al crear el ingrediente", error)
    }
  }
  
  const onCancel = () => {
    errors = [] // limpiar errores
  }

  const releaseToast = () => {
    toastLock = false
  }

  let options = Object.keys(foodGroupDict).map(key =>
      ({ value: key, label: foodGroupDict[key as FoodGroupValue].label }))
</script>

<section class="container-column">
  <section class="main-content ingredient-edit-content">

    <h1 class="header-title"> Editar ingrediente </h1>

    {#if ingredientEdit}
      <form onsubmit={onSubmit} onreset={onCancel} class="ingredient-edit-section" id="form-ingredient-edit">
        <section class="input-group">
          <Input 
              label_text="Nombre del ingrediente*"
              label_for="form-ingredient-name"
              input_type={InputTypes.Normal}
              bind:value={ingredientEdit.name as string}
              class="input-primary"
              id="from-ingredient-name"
              name="name"
          />
          <ValidationField errors={errors} field="name" />
          <!-- <label class="label-color" for="form-ingredient-name">Nombre del ingrediente*</label>
          <input type="text" id="form-ingredient-name" bind:value={ingredientEdit.name} class="input-primary" name="name" required> -->
        </section>

        <section class="input-group">
          <Input 
              label_text ="Costo*"
              label_for="form-ingredient-cost"
              input_type={InputTypes.Normal}
              bind:value={ingredientEdit.cost as number}
              class= "input-primary"
              id= "form-ingredient-cost"
              name= "cost"
              type="number"
              step="any"
          />
          <ValidationField errors={errors} field="cost" />
          <!-- <label class="label-color" for="form-ingredient-cost">Costo*</label>
          <input type="text" id="form-ingredient-cost" bind:value={ingredientEdit.cost} class="input-primary" name="cost" required> -->
        </section>

        <section class="input-group">
          <!-- No componetizado -->
          <label for="from-ingredient-group" class="label-color">
            <span>
              Grupo Alimenticio
            </span>
            <select name="foodGroup" id="form-ingredient-group" class="input-primary" bind:value={ingredientEdit.foodGroup}>
              {#each options as option}
                <option value={option.value}>{option.label}</option>
              {/each}
            </select>
          </label>

          <ValidationField errors={errors} field="foodGroup" />
          <!-- <label class="label-color" for="form-ingredient-group">Grupo Alimenticio</label>
          <select id="form-ingredient-group" class="input-primary" name="foodGroup" required value={ingredientEdit.foodGroup}> 
            <option value="" disabled selected hidden>{ingredientEdit.foodGroup}</option>
            {#each Object.keys(foodGroupDict) as value}
              <option value={value}>{foodGroupDict[value as FoodGroupValue].label}</option>
            {/each}
          </select> -->
        </section>

        <section class="animal-origin-display input-group">
          <h3>Origen Animal</h3>
          <div class="slide-button">
            <!-- <Input
              description="Origen animal"
              input_type={InputTypes.Checkbox}
              labelProps={{
                class: "label-color",
                for: "switch-button",
              }}
              inputProps={{
                class: "toggle",
                id: "switch-button",
                name: "esOrigenAnimal",
              }}
              bind:checked={ingredientEdit.esOrigenAnimal}
            /> -->
            <input type="checkbox" class="toggle" id="switch-button" bind:checked={ingredientEdit.esOrigenAnimal} name="esOrigenAnimal"/>
            <div class="background-div">
              <div class="circle-slide"></div>
            </div>
          </div>          
          <ValidationField errors={errors} field="esOrigenAnimal" />
        </section>
      </form>

      <section class="btn-group-actions">
        <button form="form-ingredient-edit" class="btn btn-secondary" onclick={() => goto('/ingredients')} type="reset">Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
        <button form="form-ingredient-edit" class="btn btn-primary" disabled={!ingredientLock} type="submit">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
      </section>
    {:else}
      <p>Ingrediente no encontrado.</p>
    {/if}
  </section>

</section>