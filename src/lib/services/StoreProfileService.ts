import { StoreType, type StoreJSON } from '$lib/domain/store'
import { STORE_MOCK } from '$lib/data/mock/store'
import { getAxiosData } from './common'
import { REST_SERVER_URL } from './configuration'
import axios from 'axios'

class StoreService {
  async getStore(){
    const queryStore = () => axios.get<StoreJSON[]>(REST_SERVER_URL + '/store-profile')
    return (await getAxiosData(queryStore)).map(StoreType.fromJson)
    //return STORE_MOCK.map(StoreType.fromJson)
  }
  

  
  async updateStore(store: StoreType){
    return axios.put<StoreJSON>(REST_SERVER_URL + '/store-profile', store.toJSON())
    //STORE_MOCK[0] = { ...store }
    //return STORE_MOCK[0]
  }
}

export const storeService = new StoreService()