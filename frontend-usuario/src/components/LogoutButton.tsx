import { useNavigate } from 'react-router-dom'
import { Button } from '@mui/material'
import { userService } from '../services/UserService'

function LogoutButton() {
  const navigate = useNavigate()

  const handleLogout = () => {
    userService.logout()
    navigate('/login', { replace: true })
  }

  return (
    <Button variant="contained" className='btn-secondary' onClick={handleLogout}>Cerrar Sesión</Button>
  )
}

export default LogoutButton