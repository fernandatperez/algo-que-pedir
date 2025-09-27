import type { FieldsetGroupProps, PaymentMethod } from '$lib/type/storeProfileTypes'

export const storeInfo: FieldsetGroupProps = {
  title: '',
  name: 'store-info',
  fields: [
    {
      label_for: 'store-name',
      label_text: 'Nombre del local*',
      input_placeholder: "Carlo's Bake Shop",
      input_id: 'store-name'
    },
    {
      label_for: 'url-store-img',
      label_text: 'URL de la imagen*',
      input_placeholder: '/src/lib/assets/img/CarlosBakeShop.jpg',
      input_id: 'url-store-img'
    }
  ]
}

export const storeDir: FieldsetGroupProps = {
  title: 'Dirección',
  name: 'store-dir',
  fields: [
    {
      label_for: 'store-dir',
      label_text: 'Dirección*',
      input_placeholder: 'Av Siempre Viva',
      input_id: 'store-dir'
    },
    {
      label_for: 'store-altitude',
      label_text: 'Altura*',
      input_placeholder: '555',
      input_id: 'store-altitude'
    },
    {
      label_for: 'store-latitude',
      label_text: 'Latitud*',
      input_placeholder: '-34.579839',
      input_id: 'store-latitude'
    },
    {
      label_for: 'store-longitude',
      label_text: 'Longitud*',
      input_placeholder: '-58.526695',
      input_id: 'store-longitude'
    }
  ]
}

export const storeCommission: FieldsetGroupProps = {
  title: 'Porcentajes',
  name: 'store-comission',
  fields: [
    {
      label_for: 'app-commission',
      label_text: 'Porcentaje de comisión con la app*',
      input_placeholder: '3',
      input_id: 'app-commission'
    },
    {
      label_for: 'author-commission',
      label_text: 'Porcentaje de comisión con autores de platos*',
      input_placeholder: '2',
      input_id: 'author-commission'
    }
  ]
}

export const paymentMethods: PaymentMethod[] = [
  { id: 'efectivo', label: 'Efectivo', value: 'EFECTIVO' },
  { id: 'qr', label: 'QR', value: 'QR' },
  { id: 'transferencia', label: 'Transferencia', value: 'TRANSFERENCIA' }
]


