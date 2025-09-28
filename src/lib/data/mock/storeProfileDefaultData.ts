
import type { FormData } from '$lib/type/storeProfileTypes'

/* datos por defecto de la tienda*/
/* esta bien que haya definido una clase de typescript? o deberia definirla vacia y poblar con un json? */
export const defaultData: FormData = {
  storeInfo: {
    'store-name': "Carlo's Bake Shop",
    'url-store-img': '/src/lib/assets/img/CarlosBakeShop.jpg'
  },
  storeDir: {
    'store-dir': 'Av Siempre Viva 123',
    'store-altitude': '555',
    'store-latitude': '-34.579839',
    'store-longitude': '-58.526695'
  },
  storeCommission: {
    'app-commission': '3',
    'author-commission': '2'
  },
  paymentMethods: {
    'efectivo': true,
    'qr': false,
    'transferencia': false
  }
}