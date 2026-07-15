// import { createContext, useContext, useEffect, useState } from 'react'
// import React from 'react'

// type AuthCtx = {
//     isAuth: boolean
//     login: () => void
//     logout: () => void
// }

// const Ctx = createContext<AuthCtx | null>(null)

// export function AuthProvider({ children }: { children: React.ReactNode }) {
//     const [isAuth, setIsAuth] = useState<boolean>(() => {
//         // sessionStorage por ahora, luego localStorage
//         // Token? capaz ahora si....
//         // lo setea en el init (solo una vez, evitamos re-leer en cada render) <- si fuera localStorage?
//         return sessionStorage.getItem('logged') === 'true'
//     })

//     // persistir cada cambio para sobrevivir recargas
//     useEffect(() => {
//         // setteo el sessionStorage con 'true' o 'false' dependiendo del isAuth()
//         sessionStorage.setItem('logged', String(isAuth))
//     }, [isAuth])

//     // modifico el isAuth cuando logueo o deslogueo
//     const login = () => setIsAuth(true)
//     const logout = () => setIsAuth(false)

//     // Exponer el contexto a toda la app
//     return <Ctx.Provider value={{ isAuth, login, logout }}>{children}</Ctx.Provider>
// }

// // custom hook/hook helper para no repetir useContext(Ctx) 
// // en cada componente y garantizar que se use dentro del provider
// export const useAuth = () => {
//     const ctx = useContext(Ctx)

//     // Si el context es null, ...
//     if (!ctx) throw new Error('useAuth debe usarse dentro de AuthProvider')
//     return ctx
// }