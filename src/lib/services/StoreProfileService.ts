import { StoreType, type StoreJSON } from '$lib/domain/store'
import { getAxiosData } from './common'
import { REST_SERVER_URL } from './configuration'
import axios from 'axios'

class StoreService {
  async getStore(){
    const queryStore = () => axios.get<StoreJSON[]>(REST_SERVER_URL + '/store-profile')
    return (await getAxiosData(queryStore)).map(StoreType.fromJson)
    //return STORE_MOCK.map(StoreType.fromJson)
  }
  
  async updateStore(storeId: number, store: StoreType): Promise<void> {

    await axios.put(`${REST_SERVER_URL}/store-profile/${storeId}`, store)
    
  
  }
}

export const storeService = new StoreService()