import { useEffect } from 'react'
/*
useEffect(() => { initialCallBack() }, ...args)
Llama al initialCallBack dentro del effect:
Una vez al montar el componente si no tiene 'args'.
Cada vez que cambie alguno de los valores dentro de 'args'.
*/
export const useOnInit = (initialCallBack: () => void) => {
    useEffect(() => {
        initialCallBack()
    }, [])
}