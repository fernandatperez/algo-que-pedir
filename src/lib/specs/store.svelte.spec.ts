// src/tests/storeProfile.test.ts
import { describe, expect, it, vi } from 'vitest'
import { render, screen } from '@testing-library/svelte'
import StoreProfile from '../../routes/(authed)/store-profile/+page.svelte'

// Mock de stores si es necesario
vi.mock('$lib/stores/storeProfileStore', () => ({
  storeProfileStore: {
    data: {
      storeInfo: { nombre: '', descripcion: '', telefono: '' },
      storeDir: { direccion: '', ciudad: '', pais: '' },
      storeCommission: { comision: '' },
      paymentMethods: { efectivo: false, tarjeta: false }
    },
    updateForm: vi.fn(),
    saveChanges: vi.fn(),
    discardChanges: vi.fn()
  }
}))

describe('StoreProfile', () => {
  it('debería renderizar el formulario correctamente', async () => {
    render(StoreProfile)
    
    // Uso await y findBy para elementos ya que cargan de manera asincronica
    expect(await screen.findByLabelText(/nombre de la tienda/i)).toBeInTheDocument()
    expect(await screen.findByLabelText(/dirección/i)).toBeInTheDocument()
    expect(await screen.findByLabelText(/Comisión de la App/i)).toBeInTheDocument()
    expect(await screen.findByLabelText(/Comisión del Autor/i)).toBeInTheDocument()
    expect(await screen.findByLabelText(/Efectivo/i)).toBeInTheDocument()
    expect(await screen.findByLabelText(/QR/i)).toBeInTheDocument()
    expect(await screen.findByLabelText(/Transferencia/i)).toBeInTheDocument()
  })
})