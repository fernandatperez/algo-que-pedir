// tests/storeProfileService.test.ts
import { describe, expect, test } from 'vitest'
import type { FormData } from '$lib/type/storeProfileTypes'
import type { formStore } from '$lib/actions/storeProfileActions.svelte'

describe('FormStore', () => {
  describe('validateForm', () => {
    test('debería retornar válido con datos correctos', () => {
      const validData: FormData = {
        storeInfo: {
          'store-name': 'Tienda de Test',
          'url-store-img': 'www.test.com',
        },
        storeDir: {
          'store-altitude': '123',
          'store-latitude': '123',
          'store-dir': '888',

        },
        storeCommission: {
          'app-commission': '15',
          'author-commission': '2',
        },
        paymentMethods: {
          'efectivo': true,
          'qr':true,
          'transferencia': true,
        }
      }

      const result = validData.     
      expect(result.isValid).toBe(true)
      expect(result.errors).toHaveLength(0)
    })

    test('debería detectar campos requeridos vacíos', () => {
      const invalidData: FormData = {
        storeInfo: {
          nombre: '', // Vacío
          descripcion: '',
          telefono: ''
        },
        storeDir: {
          direccion: '',
          ciudad: '',
          pais: ''
        },
        storeCommission: {
          comision: ''
        },
        paymentMethods: {
          efectivo: false,
          tarjeta: false
        }
      }

      const result = storeProfileService.validateForm(invalidData)
      
      expect(result.isValid).toBe(false)
      expect(result.errors.length).toBeGreaterThan(0)
      expect(result.errors.some(e => e.message.includes('requerido'))).toBe(true)
    })

    test('debería validar formato de comisión', () => {
      const invalidData: FormData = {
        storeInfo: { nombre: 'Tienda', descripcion: 'Desc', telefono: '123' },
        storeDir: { direccion: 'Dir', ciudad: 'City', pais: 'Country' },
        storeCommission: { comision: '150' }, // Mayor a 100
        paymentMethods: { efectivo: true, tarjeta: false }
      }

      const result = storeProfileService.validateForm(invalidData)
      
      expect(result.isValid).toBe(false)
      expect(result.errors.some(e => e.message.includes('entre 0 y 100'))).toBe(true)
    })

    test('debería requerir al menos un método de pago', () => {
      const invalidData: FormData = {
        storeInfo: { nombre: 'Tienda', descripcion: 'Desc', telefono: '123' },
        storeDir: { direccion: 'Dir', ciudad: 'City', pais: 'Country' },
        storeCommission: { comision: '15' },
        paymentMethods: { efectivo: false, tarjeta: false } // Ninguno seleccionado
      }

      const result = storeProfileService.validateForm(invalidData)
      
      expect(result.isValid).toBe(false)
      expect(result.errors.some(e => e.message.includes('método de pago'))).toBe(true)
    })
  })

  describe('validateField', () => {
    test('debería validar campo individual correctamente', () => {
      const formData: FormData = {
        storeInfo: { nombre: '', descripcion: '', telefono: '' },
        storeDir: { direccion: '', ciudad: '', pais: '' },
        storeCommission: { comision: '' },
        paymentMethods: { efectivo: true, tarjeta: false }
      }

      const error = storeProfileService.validateField(formData, 'storeInfo', 'nombre')
      
      expect(error).not.toBeNull()
      expect(error?.message).toContain('requerido')
    })

    test('debería retornar null para campo válido', () => {
      const formData: FormData = {
        storeInfo: { nombre: 'Tienda Válida', descripcion: '', telefono: '' },
        storeDir: { direccion: '', ciudad: '', pais: '' },
        storeCommission: { comision: '' },
        paymentMethods: { efectivo: true, tarjeta: false }
      }

      const error = storeProfileService.validateField(formData, 'storeInfo', 'nombre')
      
      expect(error).toBeNull()
    })
  })
})