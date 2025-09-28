export const storeInfo = {
  name: 'store-info',
  title: 'Datos del Local',
  fields: [
    {
      input_id: 'store-name',
      label_text: 'Nombre de la tienda',
      input_placeholder: 'Ingresa el nombre de tu tienda',
      required: true, 
      minLength: 2,
      maxLength: 100
    },
    {
      input_id: 'url-store-img', 
      label_text: 'Imagen de la tienda',
      input_placeholder: 'URL de la imagen',
      required: false // tengo que poner una imagen default si esta vacio
    }
  ]
}

export const storeDir = {
  name: 'store-dir',
  title: 'Dirección',
  fields: [
    {
      input_id: 'store-dir',
      label_text: 'Dirección completa',
      input_placeholder: 'Calle, número, ciudad',
      required: true 
    },
    {
      input_id: 'store-altitude',
      label_text: 'Altitud',
      input_placeholder: 'Altitud en metros',
      required: true,
      type: 'number'
    },
    {
      input_id: 'store-latitude',
      label_text: 'Latitud',
      input_placeholder: 'Coordenada latitud',
      required: true,
      type: 'number'
    },
    {
      input_id: 'store-longitude', 
      label_text: 'Longitud',
      input_placeholder: 'Coordenada longitud',
      required: true,
      type: 'number'
    }
  ]
}

export const storeCommission = {
  name: 'store-commission', 
  title: 'Comisiones',
  fields: [
    {
      input_id: 'app-commission',
      label_text: 'Comisión de la App (%)',
      input_placeholder: '0-100',
      required: true,
      type: 'number'
    },
    {
      input_id: 'author-commission',
      label_text: 'Comisión del Autor (%)', 
      input_placeholder: '0-100',
      required: true,
      type: 'number'
    }
  ]
}

export const paymentMethods = [
  {
    id: 'efectivo',
    label: 'Efectivo',
    value: 'efectivo'
  },
  {
    id: 'qr', 
    label: 'QR',
    value: 'qr'
  },
  {
    id: 'transferencia',
    label: 'Transferencia',
    value: 'transferencia'
  }
]

