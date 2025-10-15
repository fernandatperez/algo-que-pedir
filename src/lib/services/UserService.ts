import { UserType, type UserLoginJSON } from '$lib/domain/user'
import { USERS_LIST_MOCK } from '$lib/data/mock/users'
import axios from 'axios'
import { REST_SERVER_URL } from './configuration'
// import { getAxiosData } from './common'

class UserService {
  async getUser(username: string, password: string) {
    // Hace el POST al backend
    const response = await axios.post<UserLoginJSON>( REST_SERVER_URL + '/login',{
      correo: username,
      password: password
    })
    
    // eslint-disable-next-line no-console
    console.log(response.data)
    // Guardar datos en sessionStorage son solo para cuando esta el navegador se borra al cerrar la pestaña supuestamente....
    sessionStorage.setItem('userName', response.data.username)
    sessionStorage.setItem('email', response.data.email)
    //! no se en que usarlo igual, pero ahi estan
    
    return UserType.fromLoginJson(response.data)
  }

  async alreadyRegisteredUsername(username: string) {
    const foundUser = USERS_LIST_MOCK.find(user => 
      user.username !== undefined && 
      user.username === username
    )
    return !foundUser
  }

  async createUser(user: UserType) {
    const userJSON = { ...user }
    USERS_LIST_MOCK.push(userJSON)
    return userJSON  
  }
}

export const userService = new UserService()