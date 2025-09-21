<script>
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/component-css/grid-table.css";
  import "$lib/css/component-css/icon.css";
  import "$lib/css/component-css/buttons.css";
  import "$lib/css/component-css/input.css";
  import "$lib/css/pages-css/7-ingredients.css";

  import Ingredient from "$lib/Ingredient.svelte";

  // Valores reactivos $state()
  // te permite crear un estado reactivo , lo que significa que tu interfaz de usuario reacciona cuando cambia.
  // de padre a hijo
  // https://svelte.dev/docs/svelte/$state
  let ingredientes = $state([
    {
      id: 1,
      nombre: "Carne de Renacuajo",
      costo: "$0.50",
      grupoAlimenticio: "Proteínas",
      origenIcon: "ph-cow",
    },
    {
      id: 2,
      nombre: "Queso Cheddar",
      costo: "$0.50",
      grupoAlimenticio: "Lácteos",
      origenIcon: "ph-cow",
    },
    {
      id: 3,
      nombre: "Lechuga",
      costo: "$0.10",
      grupoAlimenticio: "Frutas y Verduras",
      origenIcon: "ph-plant",
    },
    {
      id: 4,
      nombre: "Tomate",
      costo: "$0.20",
      grupoAlimenticio: "Frutas y Verduras",
      origenIcon: "ph-plant",
    },
  ]);

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

  // Inputs del nuevo ingrediente
  let nuevoNombre = $state("");
  let nuevoCosto = $state("");
  let nuevoGrupo = $state("");

  // Acción: agregar ingrediente
  function guardarIngrediente() {
    // trim: eliminar los espacios en blanco al inicio y al final del texto.
    if (!nuevoNombre.trim()) return;

    const grupoSeleccionado = gruposAlimenticios.find(
      (grupo) => grupo.value === nuevoGrupo,
    );

    const nuevoIngrediente = {
      id: ingredientes.length + 1,
      nombre: nuevoNombre.trim(),
      costo: "$" + nuevoCosto || "$0.00",
      grupoAlimenticio: grupoSeleccionado?.label || "Sin grupo",
      origenIcon: grupoSeleccionado?.icon || "ph-question",
    };

    // Agrego el nuevoIngrediente a la lista
    // ingredientes.push(nuevoIngrediente);
    //  Tambien se puede hacer asi, cual es la diferencia???
    ingredientes = [nuevoIngrediente, ...ingredientes];

    // Reset
    nuevoNombre = "";
    nuevoCosto = "";
    nuevoGrupo = "";
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
                bind:value={nuevoNombre}
                required
              />
              <!-- bind:value permite que fluyan en sentido inverso, de hijo a padre -->
            </section>
            <section class="cell">
              <input
                class="input-primary"
                placeholder="$0.80"
                bind:value={nuevoCosto}
                required
              />
            </section>
            <section class="cell">
              <select class="input-primary" bind:value={nuevoGrupo}>
                <option value="" disabled selected hidden>Seleccionar</option>
                {#each gruposAlimenticios as grupo}
                  <option value={grupo.value}> {grupo.label} </option>
                {/each}
              </select>
            </section>
          </form>
        {/if}

        <!-- Renderizamos cada ingrediente -->
        <!-- Usamos el rest property {...ing} para pasar todas las propiedades de la lista de ingredientes -->
        {#each ingredientes as ing}
          <!-- Componente -->
          <Ingredient {...ing} />
        {/each}
      </div>
    </section>
    {#if mostrandoFormulario}
      <section class="btn-group-actions btn-group-new-ingredient">
        <button form="form-ingredient" class="btn btn-secondary" onclick={() => (mostrandoFormulario = false)}>Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
        <button form="form-ingredient" class="btn btn-primary" onclick={guardarIngrediente} type="submit">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
      </section>
    {/if}
  </section>
</section>
