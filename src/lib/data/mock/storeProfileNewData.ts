// src/lib/data/mock/storeProfileNewData.ts
export const storeInfo = {
  name: 'store-info',
  title: 'Datos del Local',
  fields: [
    {
      field: 'store-name',
      label: 'Nombre de la tienda',
      placeholder: 'Ingresa el nombre de tu tienda',
      required: true, 
      minLength: 2,
      maxLength: 100,
      type: 'text' as const  
    },
    {
      field: 'url-store-img',
      label: 'Imagen de la tienda',
      placeholder: 'URL de la imagen',
      required: false,
      type: 'text' as const 
    }
  ]
}

export const storeDir = {
  name: 'store-dir',
  title: 'Dirección',
  fields: [
    {
      field: 'store-dir',
      label: 'Dirección completa',
      placeholder: 'Calle, número, ciudad',
      required: true,
      type: 'text' as const  
    },
    {
      field: 'store-altitude',
      label: 'Altitud',
      placeholder: 'Altitud en metros',
      required: true,
      type: 'number' as const  
    },
    {
      field: 'store-latitude',
      label: 'Latitud',
      placeholder: 'Coordenada latitud',
      required: true,
      type: 'number' as const  
    },
    {
      field: 'store-longitude',
      label: 'Longitud',
      placeholder: 'Coordenada longitud',
      required: true,
      type: 'number' as const  
    }
  ]
}

export const storeCommission = {
  name: 'store-commission', 
  title: 'Comisiones',
  fields: [
    {
      field: 'app-commission',
      label: 'Comisión de la App (%)',
      placeholder: '0-100',
      required: true,
      type: 'number' as const  
    },
    {
      field: 'author-commission',
      label: 'Comisión del Autor (%)',  
      placeholder: '0-100',
      required: true,
      type: 'number' as const  
    }
  ]
}

export const paymentMethods = [
  {
    field: 'efectivo',
    label: 'Efectivo',
    type: 'checkbox' as const
  },
  {
    field: 'qr',
    label: 'QR',
    type: 'checkbox' as const
  },
  {
    field: 'transferencia',
    label: 'Transferencia', 
    type: 'checkbox' as const
  }
]