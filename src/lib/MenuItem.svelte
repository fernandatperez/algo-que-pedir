<script lang="ts">
  import type { MenuItemType } from "$lib/type/menu-item.ts";
  import { goto } from "$app/navigation";

  const { menuitem } = $props<{ menuitem: MenuItemType }>();
  // aca decis que menuitem es de tipo MenuItemType que es la interfaz
  // const { imagen, alt = "", nombre, descripcion, precio } = $props<MenuItemType>();

  //! creo que tengo que poner algo asi
  // function irAIngredientEdit(id: number) {
  //   goto(`/ingredient-edit/${id}`);
  // }
</script>

  <!-- <button onclick={()=> goto(`/dish-edit/${menuitem.id}`)}>hola</button> -->

<a class="meal" href={`/dish-edit/${menuitem.id}`} data-testid="menu-link">
    <div class="imgcontainer">
      <img
        src="src/lib/assets/img/{menuitem.imagen}"
        alt={menuitem.alt}
        class="image-meal"
        data-testid="menu-imagen"
      />
    </div>
    <div class="text-meal text-wrapper">
      <h4 class="ellipsis-text" data-testid="menu-nombre">{menuitem.nombre}</h4>
      <p class="gray-text hide-on-mobile ellipsis-text" data-testid="menu-descripcion">
        {menuitem.descripcion}
      </p>
    </div>
    <div class="price-meal" data-testid="menu-precio">${menuitem.precio}</div>
</a>