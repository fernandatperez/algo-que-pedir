<script lang="ts">
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/component-css/grid-table.css";
  import "$lib/css/component-css/icon.css";
  import "$lib/css/component-css/buttons.css";
  import "$lib/css/component-css/input.css";
  import "$lib/css/pages-css/7-ingredients.css";

  import Ingredient from "$lib/components/Ingredient.svelte";

  import { INGREDIENT_MOCK } from "$lib/data/mock/ingredients";
  import type { IngredientType } from "$lib/type/ingredient";
  import { createEmptyIngredient } from "$lib/type/ingredient";

  // Valores reactivos $state()
  // te permite crear un estado reactivo , lo que significa que tu interfaz de usuario reacciona cuando cambia.
  // de padre a hijo
  // https://svelte.dev/docs/svelte/$state
  let ingredientes = $state<IngredientType[]>(INGREDIENT_MOCK);

  // Creamos un objeto vacío usando la funcion
  let nuevoIngrediente = $state(createEmptyIngredient());

  const gruposAlimenticios = [
    { value: "frutas", label: "Frutas y Verduras", icon: "ph-plant" },
    { value: "proteinas", label: "Proteínas", icon: "ph-cow" },
    { value: "cereales", label: "Cereales y tuberculos", icon: "ph-grain" },
    { value: "lacteos", label: "Lácteos", icon: "ph-cow" },
    { value: "grasas", label: "Grasas y aceites", icon: "ph-cow" },
    { value: "azucares", label: "Azucares y dulces", icon: "ph-jar" },
  ];

  // Estado para mostrar/ocultar formulario
  let mostrandoFormulario = $state(false);

  // Acción: agregar ingrediente
  function guardarIngrediente() {
    // trim: eliminar los espacios en blanco al inicio y al final del texto.
    if (!nuevoIngrediente.nombre.trim()) return;

    // Asigno un id al nuevo ingrediente
    nuevoIngrediente.id = ingredientes.length + 1;

    // Asigno el icono correspondiente al grupo alimenticio seleccionado
    const grupoSeleccionado = gruposAlimenticios.find(
      (grupo) => grupo.value === nuevoIngrediente.grupoAlimenticio,
    );
    nuevoIngrediente.origenIcon = grupoSeleccionado?.icon || "ph-question";

    // Agrego el nuevoIngrediente a la lista
    ingredientes = [nuevoIngrediente, ...ingredientes];

    reset();
  }

  function reset(){
    // Reset
    nuevoIngrediente = createEmptyIngredient();
    mostrandoFormulario = false;
  }

  // puedo usar $effect para que se guarde la informacion aunque recargue la pagina????
  // $effect(() => {
  // 	localStorage.setItem("ingredientes", JSON.stringify(ingredientes));
  // });
</script>

<section class="flex-column">
  <section class="main-content-ingredients">
    <section class="section-title">
      <h1 class="header-title-ingredients ingredients-title">Ingredientes</h1>
      <div class="new-ingredient">
        <!--Es un button-->
        <button class="btn-add" onclick={() => (mostrandoFormulario = true)}>Nuevo Ingrediente</button>
      </div>
    </section>

    <section class="content-section-ingredients form-section-product-ingredients container-column">
      <div class="grid-table-container product-edit-ingredients-table">
        <header class="grid-table-row table-header">
          <section class="cell" id="nombre">Nombre</section>
          <section class="cell" id="nombre">Costo</section>
          <section class="cell later-hid" id="grupo-alimenticio">
            <span> Grupo </span>
            <span class="p-alimenticio display-none-mobile"> Alimenticio </span>
          </section>
          <section class="cell col-centered later-hid" id="origen">
            Origen
          </section>
          <section class="cell col-centered" id="acciones">Acciones</section>
        </header>

        <!-- Formulario emergente por si quiero agregar otro ingrediente -->
        {#if mostrandoFormulario}
          <form
            id="form-ingredient"
            class="grid-table-row product-edit-ingredients-table-content"
          >
            <section class="cell">
              <input
                class="input-primary"
                placeholder="Huevo"
                bind:value={nuevoIngrediente.nombre}
                required
              />
              <!-- bind:value permite que fluyan en sentido inverso, de hijo a padre -->
            </section>
            <section class="cell">
              <input
                class="input-primary"
                placeholder="$0.80"
                bind:value={nuevoIngrediente.costo}
                required
              />
            </section>
            <section class="cell">
              <select class="input-primary" bind:value={nuevoIngrediente.grupoAlimenticio}>
                <option value="" disabled selected hidden>Seleccionar</option>

                {#each gruposAlimenticios as grupo}
                  <option value={grupo.value}> {grupo.label} </option>
                {/each}

              </select>
            </section>
          </form>
        {/if}

        <!-- Renderizamos cada ingrediente -> Props { ingredient: Ingredient } -->
        <!-- Usamos el rest property {...ing} para pasar todas las propiedades de la lista de ingredientes -->
        {#each ingredientes as ing}
          <Ingredient ingredient={ing} />
        {/each}

      </div>
    </section>
    {#if mostrandoFormulario}
      <section class="btn-group-actions btn-group-new-ingredient">
        <button form="form-ingredient" class="btn btn-secondary" onclick={() => (reset())}>Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
        <button form="form-ingredient" class="btn btn-primary" onclick={guardarIngrediente} type="submit">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
      </section>
    {/if}
  </section>
</section>
