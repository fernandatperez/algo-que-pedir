import { INGREDIENT_MOCK } from '$lib/data/mock/ingredients'
import { describe, expect, it, vi } from 'vitest'
import { render } from 'vitest-browser-svelte'
import { screen } from '@testing-library/svelte'

import IngredientItem from '../components/Ingredient.svelte'

describe('Ingredient', () => {
  it('muestra correctemente todos los ingredientes del mock', () => {
    const mockItem = INGREDIENT_MOCK[0] 
    render( IngredientItem, { mockItem: mockItem } )

    // Verificar nombre
    const nombre = screen.getByTestId('name')
    expect(nombre.textContent).toBe('Carne de Renacuajo')
  })
})