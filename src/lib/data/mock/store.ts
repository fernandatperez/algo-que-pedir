import { type StoreJSON } from '$lib/domain/store'

/* datos por defecto de la tienda*/
/* esta bien que haya definido una clase de typescript? o deberia definirla vacia y poblar con un json? */
export const storeMOCK: StoreJSON = {
  name: "Carlo's Bake Shop",
  storeURL: '/src/lib/assets/img/CarlosBakeShop.jpg',
  storeAddress: 'Av Siempre Viva',             
  storeAltitude: 123,             
  storeLatitude: -34.579839,              
  storeLongitude: -58.526695,
  storeAppCommission: 3,
  storeAuthorCommission: 5,
  storePaymentEfectivo: true,
  storePaymentQR: false,
  storePaymentTransferencia: false,
}

export const STORE_MOCK : StoreJSON[] = [storeMOCK]
