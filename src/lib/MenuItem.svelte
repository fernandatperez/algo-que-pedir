<script lang="ts">
  import type { MenuItemType } from "$lib/type/menu-item.ts";

  const { menuitem } = $props<{ menuitem: MenuItemType }>();
  // aca decis que menuitem es de tipo MenuItemType que es la interfaz
  // const { imagen, alt = "", nombre, descripcion, precio } = $props<MenuItemType>();

  //! creo que tengo que poner algo asi
  // function irAIngredientEdit(id: number) {
  //   goto(`/ingredient-edit/${id}`);
  // }
</script>

<article class="meal">
  <div class="imgcontainer">
    <img
      src="src/lib/assets/img/{menuitem.imagen}"
      alt={menuitem.alt}
      class="image-meal"
    />
  </div>
  <div class="text-meal text-wrapper">
    <h4 class="ellipsis-text">{menuitem.nombre}</h4>
    <p class="gray-text hide-on-mobile ellipsis-text">
      {menuitem.descripcion}
    </p>
  </div>
  <div class="price-meal">{menuitem.precio}</div>
</article>
