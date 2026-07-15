import { useLocation, useNavigate } from 'react-router-dom'

export const Navigator = () => {

  const navigate = useNavigate()
  const location = useLocation()
    
  const goTo = (url: string, params?: {}) => {
    navigate(url, { state: params })
  }

  const getStateData = () => { return location.state || {} }
  
  return { goTo, getStateData }
}