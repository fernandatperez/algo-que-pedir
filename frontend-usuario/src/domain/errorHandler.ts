// import { toasts } from '../components/toast/toastStore'

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export const getErrorMessage = (error: any): string => {
  if (error.response && error.response.data) {
    const responseData = error.response.data
    const status = error.response.status
    if (typeof responseData === 'object' && responseData !== null){
      if (responseData.detalle) {
        return responseData.detalle
      }

      if(responseData.message && status < 500) {
        return responseData.message
      }
      
      if(status >= 500) {
        return 'Ocurrió un error, consulte al administrador del sistema.'
      }
      
    }
    if (typeof responseData === 'string' && status < 500){
      return responseData
    }
    
  } else if (error.code === 'ERR_NETWORK') {
    return 'Ocurrió un problema de conexión con el servidor. Intente nuevamente más tarde'
  } else if (error.message) {
    return error.message
  } return 'Error desconocido'
 
}

// export const showError = (operation: string, error: unknown) => {
//   // eslint-disable-next-line no-console
//   console.info(operation, error)
//   toasts.push(`${operation}. ${getErrorMessage(error)}`, { type: 'error' })
// }
