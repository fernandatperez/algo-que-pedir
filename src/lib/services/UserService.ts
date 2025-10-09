import { UserType, type UserJSON } from '$lib/domain/user'
import { USERS_LIST_MOCK } from '$lib/data/mock/users'
import axios from 'axios'
import { REST_SERVER_URL } from './configuration'
import { getAxiosData } from './common'

class UserService {
  async getAllUsers(){
    return USERS_LIST_MOCK.map(UserType.fromJson)
  }

  async getUser(username: string, password: string) {
    const queryUsuario = () => axios.post<UserJSON>(REST_SERVER_URL + '/login', {
      'correo': username,
      'password': password // esto tiene que coincidir con ele DTO me parece
    })
    return UserType.fromJson(await getAxiosData(queryUsuario))
    // return USERS_LIST_MOCK.find(user => 
    //   user.username !== undefined && 
    //   user.username === username && 
    //   user.password === password)
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