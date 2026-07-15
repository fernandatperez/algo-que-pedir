import { StoreJSON, StoreType } from '../domain/store'
import { Store, StoreDomJSON } from '../domain/storeDom'
import { StoreRate, StoreRateJSON } from '../domain/storeRate'
import { PaginationData, REST_SERVER_URL } from './configuration'
import axios from 'axios'

interface paginatedReviews {
    reviewsCut: StoreRate[],
    hasMore: boolean
}

class StoreService {
  async getStores(searchTerm?: string, userId: string = '') {
    const storeInstance = new StoreType()
    storeInstance.setSearchValue(searchTerm?.trim() || '')

    const result = await axios.post(`${REST_SERVER_URL}/store-profiles`, {
      searchName: storeInstance.searchName,
      userId: userId 
    })
    
    return result.data.map((storeJSON: StoreJSON) => StoreType.fromJson(storeJSON))
  }

  async getStore(id: number | null) {
    const response = await axios.get<StoreDomJSON>(`${REST_SERVER_URL}/store-profile-react/${id}?userID=${localStorage.getItem('id')}`)
    // console.log(response.data)
    const store = Store.fromJSON(response.data)
    // console.log(store)
    return store
  }

  async getReviewsByStore(id: number, paginationData: PaginationData): Promise<paginatedReviews> {
    const response = await axios.get(`${REST_SERVER_URL}/store-reviews/${id}?page=${paginationData?.page || 1}&limit=${paginationData?.limit || 10}`)
    const reviewsCut = response.data.reviewsCut.map((it: StoreRateJSON) => StoreRate.fromJSON(it))
    //console.log(reviewsCut)
    return { reviewsCut, hasMore: response.data.hasMore }
  }

  async getStoresDom() {
    const response = await axios.get<StoreDomJSON[]>(`${REST_SERVER_URL}/storesDom`)
    // console.log('response', response)
    return response.data.map(it => Store.fromJSON(it))
  }
}

export const storeService = new StoreService()
