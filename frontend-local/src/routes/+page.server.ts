import { redirect } from '@sveltejs/kit'

export function load() {
  // 301 o 308 -> https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status#redirection_messages
  throw redirect(308, '/login') // Ver como sacar esto. Redireccion a login sin credenciales
}