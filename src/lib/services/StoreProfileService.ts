import { StoreType, type StoreJSON } from '$lib/domain/store'
import { getAxiosData } from './common'
import { REST_SERVER_URL } from './configuration'
import axios from 'axios'

class StoreService {
  async getStore(){

    const storeMail = sessionStorage.getItem('email') // envio el dato como query param
    const queryStore = () => axios.get<StoreJSON[]>(REST_SERVER_URL + '/store-profile', { params: { mail: storeMail }})
    return (await getAxiosData(queryStore)).map(StoreType.fromJson)


    //! esto es lo que deberia hacer ya que estas buscando una store no una lista
    //! no lo cambio porque hay que cambiar la pagina svelte
    // const queryStore = await axios.get<StoreJSON>(REST_SERVER_URL + '/store-profile')
    // const local = StoreType.fromJson(queryStore.data)
    // return local
  }
  
  async updateStore(storeId: number, store: StoreType): Promise<void> {

    await axios.put(`${REST_SERVER_URL}/store-profile/${storeId}`, store)
    
  
  }
}

export const storeService = new StoreService()