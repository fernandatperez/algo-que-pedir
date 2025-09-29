import { MenuItemType } from '$lib/domain/menuItem'
import { MENU_ITEMS_JSON_MOCK } from '$lib/data/mock/menuItems'
class MenuItemsService {
  async getAllMenuItems(){
    return MENU_ITEMS_JSON_MOCK.map(MenuItemType.fromJson)
  }

  async getMenuItem(searchId: number) {
    const menuItem = MENU_ITEMS_JSON_MOCK.find(menuItem => 
      menuItem.id == searchId
    )

    if (menuItem != null) {
      return MenuItemType.fromJson(menuItem)
    }

    return new MenuItemType()
  }

  async createMenuItem(item: MenuItemType) {
    const itemJSON = { ...item }
    MENU_ITEMS_JSON_MOCK.push(itemJSON)
    return itemJSON  
  }

  async updateMenuItem(menuItem: MenuItemType) {
    // Aca no se como seria
    const newItem = MENU_ITEMS_JSON_MOCK.findIndex(item => item.id == menuItem.id)
    if (newItem != -1) {
      MENU_ITEMS_JSON_MOCK[newItem] = {...menuItem, id: menuItem.id}
      return MENU_ITEMS_JSON_MOCK[newItem]
    } else {
      throw new Error('Item no encontrado')
    }
  }
}

export const menuItemsService = new MenuItemsService()