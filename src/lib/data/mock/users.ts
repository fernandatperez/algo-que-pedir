import { type UserJSON } from '$lib/domain/user'

const USER1_MOCK: UserJSON  = {
  username: 'juancarlos@hotmail.com',
  password: '112233'
}
const USER2_MOCK: UserJSON  = {
  username: 'matiasgonzales@hotmail.com',
  password: '112233'
}
const USER3_MOCK: UserJSON  = {
  username: 'martafort@hotmail.com',
  password: '112233'
}

export const USERS_LIST_MOCK: UserJSON[] = [
  USER1_MOCK,
  USER2_MOCK,
  USER3_MOCK
]

