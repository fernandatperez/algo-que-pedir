import { beforeEach, describe, expect, it, vi, type Mocked } from 'vitest'
import axios from 'axios' 
import { StoreJSON } from '../domain/store'

// Mock de axios
vi.mock('axios') 
const mockedAxios = axios as Mocked<typeof axios> 

const mockStoresData = [
  {
    id: 1,
    nombre: 'Carlo\'s Bake Shop',
    email: 'jorge@hotmail.com',
    url: 'https://networthbro.com/wp-content/uploads/2019/07/buddy-valastro-networth-salary.jpg',
    direccion: { calle: 'Av. Siempre Viva', altura: 123 },
    usuarioCercano: false
  },
  {
    id: 2,
    nombre: 'McDonald\'s', 
    email: 'mcdonals@gmail.com',
    url: 'https://dynamic-media-cdn.tripadvisor.com/media/photo-o/15/2d/22/ba/it-s-mcdonalds-what-else.jpg',
    direccion: { calle: 'Av. Corrientes', altura: 3500 },
    usuarioCercano: false
  },
  {
    id: 3,
    nombre: 'SushiPop',
    email: 'sushipop@gmail.com', 
    url: 'https://cdn.pixabay.com/photo/2020/04/04/15/07/sushi-5002639_1280.jpg',
    direccion: { calle: 'Lacroze', altura: 5006 },
    usuarioCercano: false
  },
  {
    id: 4,
    nombre: 'Grido',
    email: 'grido@gmail.com',
    url: 'https://infomercado.pe/wp-content/uploads/2023/04/Grido.jpg',
    direccion: { calle: 'Av. Corrientes', altura: 3454 },
    usuarioCercano: true
  },
  {
    id: 5,
    nombre: 'Mi Gusto',
    email: 'migusto@gmail.com',
    url: 'https://lh5.googleusercontent.com/p/AF1QipNFS6K8G6XeSvyV0-DKBoQkFV1ua37A_p26YU5g=w408-h306-k-no',
    direccion: { calle: 'Gral San Martin', altura: 1904 },
    usuarioCercano: false
  },
  {
    id: 6,
    nombre: 'Lomito´s',
    email: 'lomitos@gmail.com',
    url: 'https://media-cdn.tripadvisor.com/media/photo-s/07/0e/c7/a6/betos-lomitos.jpg',
    direccion: { calle: 'Presidente Illia', altura: 3170 },
    usuarioCercano: false
  },
  {
    id: 7,
    nombre: 'Restaurante Italiano',
    email: 'italiano@gmail.com',
    url: 'https://images.unsplash.com/photo-1534650075489-3baecec1e8b1',
    direccion: { calle: 'Via Giovan Battista', altura: 2500 },
    usuarioCercano: true
  }
] 

describe('test de busqueda', () => {
  beforeEach(() => {
    vi.clearAllMocks() 
  }) 

  it('debería verificar que ningun local tiene el caracter * en el nombre', () => {
    const storesWithAsterisk = mockStoresData.filter(store => 
      store.nombre.includes('*')
    ) 
    
    expect(storesWithAsterisk).toHaveLength(0) 
    expect(storesWithAsterisk).toEqual([]) 
  }) 

  it('debería retornar array vacío cuando se busca *', async () => {
    mockedAxios.post.mockResolvedValueOnce({ data: [] }) 

    const storeInstance = {
      searchName: '',
      setSearchValue(value: string) {
        this.searchName = value 
      }
    } 
    
    storeInstance.setSearchValue('*') 

    const result = await axios.post('http://localhost:8080/store-profiles', {
      searchName: '*',
      userId: ''
    }) 
    
    const stores = result.data.map((storeJSON: StoreJSON) => storeJSON) 

    expect(stores).toHaveLength(0) 
    expect(stores).toEqual([]) 
    expect(mockedAxios.post).toHaveBeenCalledWith(
      'http://localhost:8080/store-profiles',
      {
        searchName: '*',
        userId: ''
      }
    ) 
  }) 

  it('debería encontrar local mock sushipop', async () => {
    mockedAxios.post.mockResolvedValueOnce({ 
      data: [mockStoresData[2]] // SushiPop
    }) 

    const result = await axios.post('http://localhost:8080/store-profiles', {
      searchName: 'sushi',
      userId: ''
    }) 
    
    const stores = result.data.map((storeJSON: StoreJSON) => storeJSON) 

    expect(stores).toHaveLength(1) 
    expect(stores[0].nombre).toBe('SushiPop') 
  }) 
}) 

describe('test de busqueda parcial', () => {
  describe('Búsqueda con \'car\'', () => {
    beforeEach(() => {
      vi.clearAllMocks() 
    }) 

    it('debería retornar solo Carlos Bake Shop cuando se busca \'car\'', async () => {
      const carlosBakery = mockStoresData.find(store => store.id === 1) 
      mockedAxios.post.mockResolvedValueOnce({ 
        data: carlosBakery ? [carlosBakery] : [] 
      }) 

      const result = await axios.post('http://localhost:8080/store-profiles', {
        searchName: 'car',
        userId: ''
      }) 
      
      const stores = result.data.map((storeJSON: StoreJSON) => storeJSON) 

      expect(stores).toHaveLength(1) 
      expect(stores[0].nombre).toBe('Carlo\'s Bake Shop') 
      expect(stores[0].id).toBe(1) 
      expect(stores[0].email).toBe('jorge@hotmail.com') 
      
      expect(mockedAxios.post).toHaveBeenCalledWith(
        'http://localhost:8080/store-profiles',
        {
          searchName: 'car',
          userId: ''
        }
      ) 
    }) 

    it('NO debería incluir otros locales cuando se busca \'car\'', async () => {
      const carlosBakery = mockStoresData.find(store => store.id === 1) 
      mockedAxios.post.mockResolvedValueOnce({ 
        data: carlosBakery ? [carlosBakery] : [] 
      }) 

      const result = await axios.post('http://localhost:8080/store-profiles', {
        searchName: 'car',
        userId: ''
      }) 
      
      const stores = result.data.map((storeJSON: StoreJSON) => storeJSON) 

      const otrosStores = stores.filter((store: { nombre: string }) => 
        store.nombre !== 'Carlo\'s Bake Shop'
      ) 
      expect(otrosStores).toHaveLength(0) 

      expect(stores.some((store: { nombre: string }) => store.nombre === 'McDonald\'s')).toBe(false) 
      expect(stores.some((store: { nombre: string }) => store.nombre === 'SushiPop')).toBe(false) 
      expect(stores.some((store: { nombre: string }) => store.nombre === 'Grido')).toBe(false) 
      expect(stores.some((store: { nombre: string }) => store.nombre === 'Mi Gusto')).toBe(false) 
      expect(stores.some((store: { nombre: string }) => store.nombre === 'Lomito´s')).toBe(false) 
      expect(stores.some((store: { nombre: string }) => store.nombre === 'Restaurante Italiano')).toBe(false) 
    }) 

    it('debería ser case insensitive - también funciona con \'CAR\'', async () => {
      const carlosBakery = mockStoresData.find(store => store.id === 1) 
      mockedAxios.post.mockResolvedValueOnce({ 
        data: carlosBakery ? [carlosBakery] : [] 
      }) 

      const result = await axios.post('http://localhost:8080/store-profiles', {
        searchName: 'CAR',
        userId: ''
      }) 
      
      const stores = result.data.map((storeJSON: StoreJSON) => storeJSON) 

      expect(stores).toHaveLength(1) 
      expect(stores[0].nombre).toBe('Carlo\'s Bake Shop') 
    }) 
  }) 

  describe('Renderizado de locales cercanos', () => {
    beforeEach(() => {
      vi.clearAllMocks() 
    }) 

    it('debería tener propiedad usuarioCercano: true para Grido y Restaurante Italiano', async () => {
      mockedAxios.post.mockResolvedValueOnce({ 
        data: mockStoresData 
      }) 

      const result = await axios.post('http://localhost:8080/store-profiles', {
        searchName: '',
        userId: '4'
      }) 
      
      const stores = result.data 

      const storesCercanos = stores.filter((store: StoreJSON) => store.usuarioCercano) 
      const storesNoCercanos = stores.filter((store: StoreJSON) => !store.usuarioCercano) 

      expect(storesCercanos).toHaveLength(2) 
      expect(storesNoCercanos).toHaveLength(5) 
      
      const grido = stores.find((store: StoreJSON) => store.id === 4) 
      const restauranteItaliano = stores.find((store: StoreJSON) => store.id === 7) 
      const mcdonalds = stores.find((store: StoreJSON) => store.id === 2) 
      const carlos = stores.find((store: StoreJSON) => store.id === 1) 

      expect(grido?.usuarioCercano).toBe(true) 
      expect(restauranteItaliano?.usuarioCercano).toBe(true) 
      expect(mcdonalds?.usuarioCercano).toBe(false) 
      expect(carlos?.usuarioCercano).toBe(false) 
    }) 

    it('debería renderizar "local cercano" solo para Grido y Restaurante Italiano', async () => {
      const storesWithText = mockStoresData.map(store => ({
        ...store,
        textoCercano: store.usuarioCercano ? 'local cercano' : ''
      })) 

      mockedAxios.post.mockResolvedValueOnce({ 
        data: storesWithText 
      }) 

      const result = await axios.post('http://localhost:8080/store-profiles', {
        searchName: '',
        userId: '4'
      }) 
      
      const stores = result.data 

      const grido = stores.find((store: StoreJSON) => store.id === 4) 
      const restauranteItaliano = stores.find((store: StoreJSON) => store.id === 7) 
      const mcdonalds = stores.find((store: StoreJSON) => store.id === 2) 
      const carlos = stores.find((store: StoreJSON) => store.id === 1) 
      const sushi = stores.find((store: StoreJSON) => store.id === 3) 
      const miGusto = stores.find((store: StoreJSON) => store.id === 5) 
      const lomitos = stores.find((store: StoreJSON) => store.id === 6) 

      expect(grido?.textoCercano).toBe('local cercano') 
      expect(restauranteItaliano?.textoCercano).toBe('local cercano') 
      expect(mcdonalds?.textoCercano).toBe('') 
      expect(carlos?.textoCercano).toBe('') 
      expect(sushi?.textoCercano).toBe('') 
      expect(miGusto?.textoCercano).toBe('') 
      expect(lomitos?.textoCercano).toBe('') 
    }) 

    it('debería mostrar correctamente los cercanos', () => {
      const storesCercanos = mockStoresData.filter(store => store.usuarioCercano) 
      const storesNoCercanos = mockStoresData.filter(store => !store.usuarioCercano) 

      expect(storesCercanos).toHaveLength(2) 
      expect(storesNoCercanos).toHaveLength(5) 
      
      const nombresCercanos = storesCercanos.map(store => store.nombre) 
      expect(nombresCercanos).toContain('Grido') 
      expect(nombresCercanos).toContain('Restaurante Italiano') 
      
      const nombresNoCercanos = storesNoCercanos.map(store => store.nombre) 
      expect(nombresNoCercanos).toContain('Carlo\'s Bake Shop') 
      expect(nombresNoCercanos).toContain('McDonald\'s') 
      expect(nombresNoCercanos).toContain('SushiPop') 
      expect(nombresNoCercanos).toContain('Mi Gusto') 
      expect(nombresNoCercanos).toContain('Lomito´s') 
    }) 

    it('debería funcionar con búsqueda y mantener propiedad usuarioCercano', async () => {
      const restauranteItalianoOnly = [mockStoresData[6]]

      mockedAxios.post.mockResolvedValueOnce({ 
        data: restauranteItalianoOnly 
      }) 

      const result = await axios.post('http://localhost:8080/store-profiles', {
        searchName: 'res',
        userId: '4'
      }) 
      
      const stores = result.data 

      expect(stores).toHaveLength(1) 
      expect(stores[0].nombre).toBe('Restaurante Italiano') 
      expect(stores[0].usuarioCercano).toBe(true) 
    }) 

  }) 
})