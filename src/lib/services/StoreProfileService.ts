import { StoreType } from '$lib/domain/store'
import { STORE_MOCK } from '$lib/data/mock/store'

class StoreService {
  async getStore(){
    return STORE_MOCK.map(StoreType.fromJson)
  }

  async updateStore(store: StoreType){
    STORE_MOCK[0] = { ...store }
    return STORE_MOCK[0]
  }
}

export const storeService = new StoreService()