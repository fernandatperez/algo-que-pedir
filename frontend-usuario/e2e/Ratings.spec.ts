import { test, expect } from '@playwright/test'

test.describe("OrderDetail y Ratings", () => {

  test.beforeEach(async ({request, page}) => {
    await request.post('http://localhost:9000/test/reset') // 1) resetear backend a estado inicial

    await page.addInitScript(() => {
      window.localStorage.clear()
      window.localStorage.setItem('id', '4')
      window.localStorage.setItem('userName', 'Buzz')
      window.localStorage.setItem('email', 'woodyteamo@gmail.com')
    })

    await page.goto('http://localhost:5173/')
  })

  test("Se puede calificar un restaurante", async ({page}) => {
    await page.goto('http://localhost:5173/store-ratings')
    // Tiene un local a calificar desde el bootstrap
    await expect(page.getByTestId('calificar-1')).toBeVisible()
    // Me guardo el nombre del restaurante para usarlo en la validacion de datos
    const storeNames = await page.getByTestId('restaurant-title').allInnerTexts()
    const firstName = storeNames.at(0)!
    // Entro a calificar
    await page.getByTestId('calificar-1').click()
    // Chequeo que los datos esten bien
    await expect(page.getByTestId('restaurant-name')).toContainText(firstName)
    await page.getByTestId('textarea-rating').fill('Muy bueno')
    await page.getByTestId('guardar-rating').click()
    // El local desaparece de a calificar
    await expect(page.getByTestId('calificar-1')).not.toBeVisible()
    // vemos la reseña en el restaurante
    await page.goto('http://localhost:5173/store-detail/1')
    await page.getByTestId('reseñas-tab').click()
    await page.getByTestId('cargar-mas-reseñas').click()

    const reseñas = await page.getByTestId('reseña-texto').allInnerTexts()
    await expect(reseñas.at(-1)).toContain('Muy bueno')
    
  })

  test("Se puede calificar cuando se confirma un pedido", async ({page}) => {
    await page.goto('http://localhost:5173/store-ratings')
    // Tiene un local a calificar desde el bootstrap
    await expect(page.getByTestId('calificar-1')).toBeVisible()
    const storeToRate = await page.locator('[data-testid^="calificar-"]').all()
    expect(storeToRate.length).toBe(1)
    // hacemos un pedido
    await page.getByTestId('nav-link-home').click()
    await page.getByTestId('store-card-2').click()
    const firstDish = page.locator('[data-testid^="dish-card-"]').first()
    await firstDish.click()
    await page.getByTestId('agregar-a-pedido').click()
    await page.getByTestId('ver-pedido-btn').click()
    await page.getByTestId('reservar-confirmar-pedido-btn').click()
    // Lo confirmamos
    await page.goto('http://localhost:5173/')
    await page.getByTestId('nav-link-orders').click()
    await page.getByTestId('restaurant-card-2-18').click()
    await page.getByTestId('reservar-confirmar-pedido-btn').click()
    // toast de success
    await expect(page.getByTestId('toast-orderCheckout-test')).toBeVisible()
    await expect(page.getByTestId('toast-orderCheckout-test')).toContainText('Pedido confirmado')
    // Vamos a "Calificar"
    await page.getByTestId('nav-link-ratings').click()
    const storesToRate = await page.locator('[data-testid^="calificar-"]').all()
    expect(storesToRate.length).toBe(2)
    await expect(page.getByTestId('calificar-2')).toBeVisible()
  })


  
})