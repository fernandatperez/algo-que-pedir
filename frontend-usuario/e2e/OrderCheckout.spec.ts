import { test, expect } from '@playwright/test'

test.describe('StoreDetail y OrderCheckout', () => {

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

  test("User sin criterios reserva un pedido", async ({page}) => {
    // Recargo para traer las cosas sgun el user Buzz
    await page.reload()
    // Probamos que trae (al menos uno de) los restaurantes disponibles (todos)
    await expect(page.getByTestId('store-card-1')).toBeVisible()
    // Entramos al detalle del local
    await page.getByTestId('store-card-1').click()
    // Elegimos un plato
    await page.getByTestId('dish-card-1').click()
    // Se abre el modal con los datos correctos
    await expect(page.getByTestId('dish-modal')).toBeVisible()
    await expect(page.getByTestId('dish-modal')).toHaveId('modal-1')
    // Sumamos un plato
    expect(page.getByTestId('modal-counter')).toHaveText('1')
    await page.getByTestId('modal-add-btn').click()
    expect(page.getByTestId('modal-counter')).toHaveText('2')
    // Agregamos al pedido
    await page.getByTestId('agregar-a-pedido').click()
    // Vamos a ver el pedido
    await expect(page.getByTestId('ver-pedido-btn')).toContainText('2')
    await page.getByTestId('ver-pedido-btn').click()
    // Chequeamos que los datos sean correctos (id del plato: 1)
    await expect(page.getByTestId('item-quantity-1')).toContainText('2')
    // Lo reservamos
    // estaria bueno que el endpoint devuelva al menos el id de la order para luego usarlo
    await page.getByTestId('reservar-confirmar-pedido-btn').click()
    // Chequeamos que aparezca en la ventana de pedidos pendientes
    await page.getByTestId('nav-link-orders').click()
    await expect(page.getByTestId('restaurant-card-1-18')).toBeVisible()
    // await expect(page.getByTestId('order-detail-2')).toContainText("2 productos")
  })

    test('User no puede agregar un plato de otro local al pedido actual', async ({page}) => {
        // selecciona un local
        // await page.reload()
        // await page.getByTestId('store-card-7').scrollIntoViewIfNeeded()
        await expect(page.getByTestId('store-card-1')).toBeVisible() // con el 7 no anda no se por que 
        await page.getByTestId('store-card-1').click()

        // selecciona un plato
        await page.getByTestId('dish-card-1').click()
        await expect(page.getByTestId('dish-modal')).toBeVisible()
        await expect(page.getByTestId('dish-modal')).toHaveId('modal-1')

        // agrega 2 veces el mismo plato
        expect(page.getByTestId('modal-counter')).toHaveText('1')
        await page.getByTestId('modal-add-btn').click()
        await page.getByTestId('modal-rest-btn').click()
        expect(page.getByTestId('modal-rest-btn')).toBeDisabled
        await page.getByTestId('modal-add-btn').click()
        expect(page.getByTestId('modal-counter')).toHaveText('2')
        await page.getByTestId('agregar-a-pedido').click()
        
        // vemos que ver pedido muestra lo correcto
        await expect(page.getByTestId('ver-pedido-btn')).toBeVisible()
        await expect(page.getByTestId('ver-pedido-btn')).toHaveText('Ver pedido (2)')
        await page.getByTestId('ver-pedido-btn').click()
        // vemos que los datos sean 2 platos
        await expect(page.getByTestId('item-quantity-1')).toContainText('2')

        // vamos a home devuleta
        await page.getByTestId('header-back-btn').click()
        await page.getByTestId('header-back-btn').click()
        
        // entramos a otro local
        await expect(page.getByTestId('store-card-2')).toBeVisible() 
        await page.getByTestId('store-card-2').click()

        // selecciona el PRIMER plato que aparezca (sin importar su ID) porque no se cual carajo es el id de este
        const firstDish = page.locator('[data-testid^="dish-card-"]').first()
        await expect(firstDish).toBeVisible()
        await firstDish.click()

        // intentamos agregar el plato
        await expect(page.getByTestId('dish-modal')).toBeVisible()
        await expect(page.getByTestId('modal-counter')).toHaveText('1')
        await page.getByTestId('modal-add-btn').click()
        await expect(page.getByTestId('modal-counter')).toHaveText('2')

        // toca para agregar y salta el toast
        await page.getByTestId('agregar-a-pedido').click()

        // vemos que tira error
        await expect(page.getByTestId('toast-storeDetail-test')).toBeVisible()
        await expect(page.getByTestId('toast-storeDetail-test')).toContainText('No puedes agregar platos de diferentes locales')

        // cerramos el modal y sigue teniendo dos
        await expect(page.getByTestId('dish-modal')).toBeVisible()
        await page.getByTestId('cerrar-modal-store').click() 
        await expect(page.getByTestId('ver-pedido-btn')).toHaveText('Ver pedido (2)')

        // entramos y vemos que es el mismo local que antes
        await page.getByTestId('ver-pedido-btn').click()
        await expect(page.getByTestId('restaurant-name-test')).toContainText('Carlo\'s Bake Shop')
        await expect(page.getByTestId('item-quantity-1')).toContainText('2')
    })

})