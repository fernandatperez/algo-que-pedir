import { Navigate, Outlet, useLocation } from 'react-router-dom'
// import { useAuth } from './AuthContext'
import { userService } from '../../services/UserService'


const RequireAuth = () => {
  const location = useLocation() // para

  // preguntar al service si esta logueado y volar el context
  if (!userService.isAuth()) return <Navigate to={'/login'} replace state={{ from: location }} />
  return <Outlet /> // renderiza las rutas hijas protegidas
} 
export default RequireAuth

// Si no estas logueado, te redirigire a /login. Pero además deja “miguitas de pan” 
// para que, después de loguearte, vuelvas a la página original.
// --
// ? replace del state:
// Evitás que, al apretar “Atrás” en el navegador, el usuario vuelva a la ruta 
// protegida y quede atrapado en un ida-y-vuelta (redirect loop en el historial).
// --
// ? from:
// Estamos pasando un objeto con la clave from que guarda la ruta actual 
// (location) desde donde el usuario intentó entrar.
//    Lo Piola: si el usuario entró directamente a /profile sin estar logueada, 
//    la mandás a /login. Cuando se loguea, vuelve a /profile automáticamente 
//    (en lugar de mandarla siempre al /).