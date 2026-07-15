import { test, expect } from '@playwright/test';

test.describe("Home", () => {

  test.beforeEach(async ({request, page}) => {
    // 1) resetear backend a estado inicial
    await request.post('http://localhost:9000/test/reset')

    await page.addInitScript(() => {
      window.localStorage.setItem('id', '1') // sofia miller
      window.localStorage.setItem('userName', 'Sofía')
      window.localStorage.setItem('email', 'sofiamiller@gmail.com')
    });

    await page.goto("http://localhost:5173/")
  })

  test.afterEach(async () => {

  })

  test("Deberia mostrar los locales disponibles segun criterio de user", async ({page}) => {
    await expect(page).toHaveTitle("Algo que Pedir")
    // sofia miller no tiene locales disponibles por sus criterios
    await expect(page.getByTestId('no-stores-text')).toBeVisible()

    // sacamos todos sus criterios
    await page.getByTestId('nav-link-profile').click()
    await page.getByTestId('search-criteria-link').click()

    await expect(page.getByTestId('vegano-checkbox')).toBeChecked()
    await expect(page.getByTestId('fieles-checkbox')).toBeChecked()

    await page.getByTestId('vegano-checkbox').setChecked(false) // 1-dos formas de hacerlo
    await page.getByTestId('fieles-checkbox').click() // 2-dos formas de hacerlo
    // actualizamos el perfil
    await page.getByTestId('modificar-criteria-btn').click()
    await page.getByTestId('header-back-btn').click()
    await page.getByTestId('guardar-perfil-btn').click()

    // Vuelvo a home y hay locales
    await page.getByTestId('nav-link-home').click()
    await expect(page.getByTestId('no-stores-text')).not.toBeVisible()
    await expect(page.getByTestId('store-name-1')).toBeVisible()

  })

})