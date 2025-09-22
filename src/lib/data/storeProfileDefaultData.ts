import type { FormData } from '../../types/storeProfileTypes'
/* datos por defecto de la tienda*/
export const defaultData: FormData = {
  storeInfo: {
    'store-name': "Carlo's Bake Shop",
    'url-store-img': '/src/lib/assets/img/CarlosBakeShop.jpg'
  },
  storeDir: {
    'store-dir': 'Av Siempre Viva',
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