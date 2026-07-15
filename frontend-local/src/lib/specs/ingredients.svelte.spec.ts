import { describe, it, expect } from 'vitest'
import { render, screen } from '@testing-library/svelte'
import '@testing-library/jest-dom' 
import userEvent from '@testing-library/user-event'

import { FoodGroupValue, IngredientType} from '$lib/domain/ingredient'
import { ingredientService } from '$lib/services/IngredientService'
import { INGREDIENT_MOCK, firstIngredient, thirdIngredient } from '$lib/data/mock/ingredients'
import Ingredient from '$lib/components/Ingredient.svelte'

describe('Ingredient Component', () => {
  it('muestra correctamente todos los datos de un ingrediente', () => {
    const mockIngredient = IngredientType.fromJson(firstIngredient) // Carne de Renacuajo
    render(Ingredient, { ingredient: mockIngredient })
    
    // Verificar nombre
    const nombre = screen.getByText('Carne de Renacuajo')
    expect(nombre).toBeInTheDocument()
    expect(nombre).toHaveAttribute('id', `nombre-${mockIngredient.id}`)

    // Verificar costo
    const costo = screen.getByText(`$${mockIngredient.cost}`)
    expect(costo).toBeInTheDocument()

    // Verificar grupo alimenticio
    const grupo = screen.getByText(FoodGroupValue.PROTEINAS)
    expect(grupo).toBeInTheDocument()
    expect(grupo).toHaveAttribute('id', `grupo-alimenticio-${mockIngredient.id}`)

    // Verificar icono
    const icono = document.getElementById(`origen-${mockIngredient.id}`)
    expect(icono).toBeInTheDocument()
    expect(icono).toHaveClass('ph-cow')
  })

  it('muestra el ícono de origen vegetal correctamente', () => {
    const mockIngredient = IngredientType.fromJson(thirdIngredient) // Lechuga
    render(Ingredient, { ingredient: mockIngredient })

    // Verificar ícono
    const icono = document.getElementById(`origen-${mockIngredient.id}`)
    expect(icono).toBeInTheDocument()
    expect(icono).toHaveClass('ph-plant')
  })

  it('todos los elementos tienen los id necesarios para testing', () => {
    const mockIngredient = IngredientType.fromJson(INGREDIENT_MOCK[0])
    render(Ingredient, { ingredient: mockIngredient })

    // Verificar que los elementos principales tienen su ID
    expect(screen.getByText(mockIngredient.name)).toHaveAttribute('id', `nombre-${mockIngredient.id}`)
    expect(screen.getByText(mockIngredient.foodGroup)).toHaveAttribute('id', `grupo-alimenticio-${mockIngredient.id}`)
    expect(document.getElementById(`origen-${mockIngredient.id}`)).toBeInTheDocument()
  })
})

describe('Ingredient Service & Domain Logic', () => {
  it('debería aumentar de 4 a 5 ingredientes cuando se agrega uno nuevo', async () => {
    // Para asegurar que este test sea consistente, trabajamos con una copia del mock
    const localMock = [...INGREDIENT_MOCK]

    ingredientService.getAllIngredients = async () => localMock.map(IngredientType.fromJson)

    ingredientService.createIngredient = async (ing: IngredientType) => {
    // Creamos el objeto JSON para guardarlo en el array mock
      const newIngredientJSON = { ...ing.toJSON(), id: localMock.length + 1 }
      localMock.push(newIngredientJSON)
      
      // Devolvemos un objeto que coincide con la firma de tipo esperada (con 'errors')
      return { ...ing, id: newIngredientJSON.id }
    }


    const ingredientesIniciales = await ingredientService.getAllIngredients()
    const cantidadInicial = ingredientesIniciales.length
    
    // Crear un nuevo ingrediente
    const nuevoIngrediente = new IngredientType()
    nuevoIngrediente.name = 'Azúcar'
    nuevoIngrediente.cost = 0.1
    nuevoIngrediente.foodGroup = FoodGroupValue.AZUCARES_Y_DULCES
    
    await ingredientService.createIngredient(nuevoIngrediente)
    
    const ingredientesActualizados = await ingredientService.getAllIngredients()
    
    expect(ingredientesActualizados.length).toBe(cantidadInicial + 1)
    
    // Verificar que el nuevo ingrediente está en la lista
    const ingredienteAgregado = ingredientesActualizados.find(item => item.name === 'Azúcar')
    expect(ingredienteAgregado).toBeDefined()
    expect(ingredienteAgregado?.name).toBe('Azúcar')
    expect(ingredienteAgregado?.cost).toBe(0.1)
  })

  it('la validación de IngredientType debe funcionar correctamente', () => {
    const ingrediente = new IngredientType()
    
    // Caso 1: Inválido (nombre y costo vacíos)
    ingrediente.validate()
    expect(ingrediente.invalid()).toBe(true)
    expect(ingrediente.errors.length).toBe(2)
    expect(ingrediente.errors.find(e => e.field === 'name')?.message).toBe('Debe ingresar nombre')
    expect(ingrediente.errors.find(e => e.field === 'cost')?.message).toBe('El costo debe ser mayor a 0')

    // Caso 2: Válido
    ingrediente.name = 'Sal'
    ingrediente.cost = 0.05
    ingrediente.foodGroup = FoodGroupValue.AZUCARES_Y_DULCES
    ingrediente.validate()
    expect(ingrediente.invalid()).toBe(false)
    expect(ingrediente.errors.length).toBe(0)
  })
})

import IngredientsPage from '../../routes/(authed)/ingredients/+page.svelte'

describe('Ruta /ingredients - Página de Ingredientes', () => {
  it('debería mostrar 5 ingredientes en la página cuando se agrega uno nuevo a través del formulario', async () => {
    render(IngredientsPage)

    // 1. Esperamos a que la página cargue los datos iniciales
    const initialRows = await screen.findAllByRole('article')
    const cantidadInicial = initialRows.length
    expect(cantidadInicial).toBe(4)

    // 2. Hacemos clic en el botón "Nuevo Ingrediente" para mostrar el formulario
    const nuevoIngredienteBtn = screen.getByRole('button', { name: /nuevo ingrediente/i })
    await userEvent.click(nuevoIngredienteBtn)
    
    // 3. Verificamos que el formulario está visible buscando uno de sus campos
    const nombreInput = screen.getByPlaceholderText('Huevo')
    expect(nombreInput).toBeInTheDocument()

    // 4. Rellenamos el formulario con los datos del nuevo ingrediente
    await userEvent.type(nombreInput, 'Papas')
    await userEvent.type(screen.getByPlaceholderText('0.80'), '0.35')
    await userEvent.selectOptions(
      screen.getByRole('combobox'), // El rol de un <select> es 'combobox'
      FoodGroupValue.CEREALES_Y_TUBERCULOS
    )
    
    // 5. Enviamos el formulario haciendo clic en "Guardar Cambios"
    const guardarBtn = screen.getByRole('button', { name: /guardar cambios/i })
    await userEvent.click(guardarBtn)

    // 6. Verificamos los resultados:
    //    - El nuevo ingrediente "Papas" debe aparecer en la lista.
    //    - La cantidad total de ingredientes debe ser la inicial + 1.
    expect(await screen.findByText('Papas')).toBeInTheDocument()

    const finalRows = screen.getAllByRole('article')
    expect(finalRows.length).toBe(cantidadInicial + 1)
    expect(finalRows.length).toBe(5)

    // Opcional: Verificar que el formulario se ha ocultado
    expect(screen.queryByPlaceholderText('Huevo')).not.toBeInTheDocument()
  })
})