import { UserType, type UserLoginJSON, type UserRegisterJSON } from '$lib/domain/user'
import axios from 'axios'
import { REST_SERVER_URL } from './configuration'

class UserService {
  // Usamos el mismo, cambia en el back. Esta muy mal?
  async getUser(username: string, password: string) {
    // -------------------------------------------------------------------
    // const usuario: UserLoginJSON = {
    //   username: username,
    //   email: password
    // }
    // Seria mas bien asi, no?
    // const usuario: UserJSON = {
    //   username: username,
    //   password: password
    // } // Y esto si lo mandas al post, no mandas un objeto generado ahi y hay que cambiar el <UserLoginJSON> por <UserJSON>
    // --------------------------------------------------------------------
    // Hace el POST al backend
    const response = await axios.post<UserLoginJSON>( REST_SERVER_URL + '/login',{
      correo: username,
      password: password
    })
    
    // Guardar datos en sessionStorage son solo para cuando esta el navegador se borra al cerrar la pestaña supuestamente....
    sessionStorage.setItem('userName', response.data.username)
    sessionStorage.setItem('email', response.data.email)
    //! no se en que usarlo igual, pero ahi estan
    
    return UserType.fromLoginJson(response.data)
  }

  async createUser(user: UserType) {
    const userJSON: UserRegisterJSON = {
      name: 'Carlos',
      surname: 'Martinez',
      username: user.username,
      password: user.password
    }

    await axios.post<UserRegisterJSON>(
      REST_SERVER_URL + '/register', 
      userJSON
    )
  }
}

export const userService = new UserService()