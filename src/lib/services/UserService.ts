import { UserType } from '$lib/domain/user'
import { USERS_LIST_MOCK } from '$lib/data/mock/users'

class UserService {
  async getAllUsers(){
    return USERS_LIST_MOCK.map(UserType.fromJson)
  }

  async getUser(username: string, password: string) {
    return USERS_LIST_MOCK.find(user => 
      user.username !== undefined && 
      user.username === username && 
      user.password === password)
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