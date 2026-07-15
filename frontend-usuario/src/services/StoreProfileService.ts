import { StoreType, type StoreJSON } from '../domain/store'
import { getAxiosData } from './common'
import { REST_SERVER_URL } from './configuration'
import axios from 'axios'

class StoreService {
  // async getStore(){
  //   const storeMail = sessionStorage.getItem('email') // envio el dato como query param
  //   const queryStore = () => axios.get<StoreJSON[]>(REST_SERVER_URL + '/store-profile', { params: { mail: storeMail }})
  //return (await getAxiosData(queryStore)).map(StoreType.fromJson)
  //   return StoreType.fromJson(await getAxiosData(queryStore))
  //}

  async getStore() {
    const storeMail = sessionStorage.getItem('email')
    const queryStore = () => axios.get<StoreJSON>(REST_SERVER_URL + '/store-profile', {params: { mail: storeMail } })
    const response = await getAxiosData(queryStore)
    return StoreType.fromJson(response)
    
  }
    

  
  async updateStore(store: StoreType): Promise<void> {
    const storeMail = sessionStorage.getItem('email') 
    await axios.put(`${REST_SERVER_URL}/store-profile`,store,{params: { mail: storeMail },headers: { 'Content-Type': 'application/json' }})
  
  }
}

  


export const storeService = new StoreService()