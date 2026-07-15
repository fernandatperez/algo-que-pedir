import { Outlet } from 'react-router-dom'
import { ContextType } from '../../customHooks/useUserProfile'
import { useState } from 'react'
import { UserProfile } from '../../domain/userProfile'
import { useOnInit } from '../../customHooks/useOnInit'
import { userService } from '../../services/UserService'
import { Toast } from '../../components/Toast/ToastContainer'
import { useToast } from '../../components/Toast/useToast'

//  Para compartir estado entre componentes, una opcion CONTEXT
const ProfileContext = () => {
    const [profile, setProfile] = useState<UserProfile>(new UserProfile())
    const [profileOG, setProfileOG] = useState<UserProfile>(new UserProfile())
    const { toast, showToast } = useToast()
    
    const id = Number(localStorage.getItem('id'))

    const getProfile = async () => {
        try {
            const userProfile = await userService.getProfile(id)
            setProfile(userProfile)
            setProfileOG(UserProfile.fromJSON(userProfile.toJSON())) // ... es una copia!
            // console.log('Perfil obtenido con exito', userProfile)
        } catch (error) {
            console.info('Unexpected error', error)
            showToast('Error al cargar el perfil. Por favor intenta nuevamente.', 'error')
        }
    }

    const checkChanges = () => {
      // console.log("nuevo: ", profile)
      // console.log("viejo: ", profileOG)
      if (!profile.isEqual(profileOG) ) {
        // console.log("Hay cambios sin guardar!")
        showToast('Hay cambios sin guardar!', 'error', 5000)
      }
    }

    useOnInit(() => getProfile())
        
  return (
  <>
    <Outlet context={ {profile, setProfile, profileOG, setProfileOG, checkChanges, showToast} satisfies ContextType}/>
    <div id="toast-container">
      <Toast toast={toast} />
    </div>
  </>
)
}

export default ProfileContext