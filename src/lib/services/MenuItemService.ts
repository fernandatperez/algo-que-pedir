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

  async updateMenuItem(args: string) {
    // eslint-disable-next-line no-console
    console.info(args)
    return 0
  }
}

export const menuItemsService = new MenuItemsService()