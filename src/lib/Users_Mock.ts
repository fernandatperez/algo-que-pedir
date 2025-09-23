import { User } from '$lib/User'

export const USERS_LIST_MOCK: User[] = []

const USER1_MOCK: User = new User(
  'juancarlos@hotmail.com',
  'Altacontraseña123'
)
const USER2_MOCK: User = new User(
  'ricardofort@hotmail.com',
  'tengoALTOcoche.1'
)
const USER3_MOCK: User = new User(
  'ndeah@gmail.com',
  'WoooOtroUSUARIO!!'
)
USERS_LIST_MOCK.push(USER1_MOCK, USER2_MOCK, USER3_MOCK)