import { describe, test, expect } from 'vitest'

describe('dummy', () => {
    test('dummy test', () => {
        expect(1).toBe(1)
    })
})

// unit test
describe('StoreDetail', () => {
  test('calcula correctamente el precio total', () => {
    const selectedDish = { precio: 10 }
    const modalCounter = 3
    const calculateTotalPrice = () => selectedDish.precio * modalCounter
    expect(calculateTotalPrice()).toBe(30)
  })
})
