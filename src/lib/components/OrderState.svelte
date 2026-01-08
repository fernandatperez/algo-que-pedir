<script lang="ts">
    import { Estado, type Order } from "$lib/domain/order"


  let { estado } = $props()

    // defino color del estado (con un switch-?!)
  const stateColor = () => {
      switch (estado) {
      case 'PENDIENTE':
          return 'pendiente'
      case 'PREPARADO':
          return 'preparado'
      case 'ENTREGADO':
          return 'entregado'
      default:
          return 'cancelado'
      }
  }
    let estadoColor = $derived(stateColor())
    
</script>

<style>
  @import url('$lib/css/components-css/buttons.css');
  .order-state {
    padding: .3em 1em;
  }

  .pendiente {
    background-color: var(--font-color-tertiary);
  }
  .preparado {
    /* background-color: ; */
  }
  .entregado {
    background-color: rgb(56, 187, 0);
  }
  .cancelado {
    background-color: var(--primary-color);
  }
</style>


<div class="order-state btn-alternate {estadoColor}">{estado}</div>