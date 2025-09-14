<script>
  import '$lib/css/fonts.css'
  import '$lib/css/flex-grid.css'
  import "$lib/css/component-css/input.css";
  import "$lib/css/component-css/switch-button.css";
  import "$lib/css/component-css/grid-table.css";
  import "$lib/css/component-css/icon.css";
  import "$lib/css/component-css/buttons.css";
  import "$lib/css/pages-css/8-ingredient-edit.css";
  
  import { goto } from '$app/navigation';

  function irAIngredients () {
    goto ('/ingredients')
  }
</script>

<section class="container-column">
  <section class="main-content">

    <h1 class="header-title section-title"> Editar ingrediente </h1>

      <article class="ingredient-edit-section">
        <section class="input-group">
          <label class="label-color">Nombre del ingrediente*</label>
          <input type="text" placeholder="Lechuga" class="input-primary">
        </section>
        <section class="input-group">
          <label class="label-color">Costo*</label>
          <div class="input-with-icon">
              <i class="ph ph-currency-dollar-simple input-icon-dollar"></i>
              <input type="text" placeholder="5" class="input-primary input-ingredient-edit">
          </div>
        </section>
        <section class="input-group">
          <label class="label-color">Grupo Alimenticio</label>
          <select class="input-primary">
            <option value="" disabled selected hidden>Seleccionar</option>
            <option value="frutas">Frutas y verduras</option>
            <option value="verduras">Proteinas</option>
            <option value="carnes">Cereales y tuberculos</option>
            <option value="cereales">Lacteos</option>
          </select>
        </section>
        <section class="animal-origin-display input-group">
          <h3>Origen animal</h3>
          <div class="slide-button">
            <input type="checkbox" class="toggle">
            <div class="background-div">
              <div class="circle-slide"></div>
            </div>
          </div>
        </section>
      </article>
      
      <section class="btn-group-actions">
        <button class="btn btn-secondary" on:click={irAIngredients}>Descartar <span class="p-cambios display-none-mobile">Cambios</span></button>
        <button class="btn btn-primary">Guardar <span class="p-cambios display-none-mobile">Cambios</span></button>
      </section>

  </section>

</section>