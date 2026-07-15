import Typography from '@mui/material/Typography'
import { Avatar, FormControl, Box, Container, Button, IconButton } from '@mui/material'
import * as React from 'react'
import { useNavigate } from 'react-router-dom'
import KeyboardArrowRightIcon from '@mui/icons-material/KeyboardArrowRight'
import LogoutButton from '../../components/LogoutButton'
import { useState } from 'react'
import { UserProfile } from '../../domain/userProfile'
import { userService } from '../../services/UserService'
import ValidationField from '../../components/ValidationField/ValidationField'
import { ValidationMessage } from '../../domain/validationMessage'
import { useUserProfile } from '../../customHooks/useUserProfile'

import '../search-criteria/search-criteria.css'
import './profile.css'

const Profile = () => {
    const { profile, setProfile, setProfileOG, showToast } = useUserProfile()
    const [errors, setErrors] = useState<Array<ValidationMessage>>([])

    const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()

        profile.validate()

        if (profile.errors.length > 0) {
            setErrors(profile.errors)
            return errors
        }

        try {
            // console.log(UserProfile.fromJSON(profile))
            const updatedProfile = await userService.updateProfile(profile)
            setProfile(updatedProfile)
            setProfileOG(UserProfile.fromJSON(updatedProfile.toJSON())) // ... es una copia!
            showToast('Usuario modificado con exito', 'success', 2000)
        } catch (error) {
            console.error('Error al guardar el perfil', error)
            showToast('Error al guardar el perfil', 'error')
        } finally {
            setErrors([])
        }
    }

    const updateInput = (field: keyof UserProfile, value: unknown) => {
        const updated = new UserProfile(
            profile.id,
            field === 'name' ? String(value) : profile.name,
            field === 'email' ? String(value) : profile.email,
            field === 'lastName' ? String(value) : profile.lastName,
            field === 'address' ? String(value) : profile.address,
            field === 'location' ? String(value) : profile.location,
            field === 'latitude' ? Number(value) : profile.latitude,
            field === 'longitude' ? Number(value) : profile.longitude,
            profile.ingredientsToAvoid,
            profile.preferredIngredients
        )
        setProfile(updated)
    }

    const navigate = useNavigate()

    // React Router navigation to Ingredient Criteria page
    const handleNavigateTo = (link: string) => {
        navigate(link)
    }

    return(
        <Container className='container-profile' sx={{ pb: 9 }}>
            
            <Typography variant="h6" className='title-main-container'>Perfil</Typography>

            {/* AVATAR */}
            <Container className='section-profile'>
                <Box className='avatar-img'>
                    <Avatar
                        alt='Olivia Bennett'
                        src='/src/assets/avatar-profile.jpg'
                        sx={{ width: 100, height: 100 }}
                    />
                </Box>
                <Box>
                    <Typography variant="h5" className='primary-title-profile'>{`${profile?.name} ${profile?.lastName}`}</Typography>
                    <Typography variant="body2" color='gray' align='center'>{profile?.email}</Typography>
                </Box>
            </Container>

            {/* PERSONAL INFORMATION */}
            <form onSubmit={onSubmit} className='group-section'> 
                <Typography variant="h6" sx={{ fontWeight: 700 }}>Informacion personal</Typography>

                <Box>
                    <FormControl className='form-field'>
                        <div className='input-wrapper'>
                            <label htmlFor="user-name" className='label-profile'>Nombre</label>
                            <input
                                type="text"
                                id="user-name"
                                name="user-name"
                                value={profile?.name}
                                className='input-profile'
                                onChange={(event) => updateInput('name', event.target.value)}
                            />
                            <ValidationField field='name' errors={errors} />
                        </div>

                        <div className='input-wrapper'>
                            <label htmlFor="user-lastName" className='label-profile'>Apellido</label>
                            <input
                                type="text"
                                id="user-lastName"
                                name="user-lastName"
                                value={profile?.lastName}
                                className='input-profile'
                                onChange={(event) => updateInput('lastName', event.target.value)}
                            />
                            <ValidationField field='lastName' errors={errors} />
                        </div>

                        <div className='input-wrapper'>
                            <label htmlFor="user-address" className='label-profile'>Dirección</label>
                            <input
                                type="text"
                                id="user-address"
                                name="user-address"
                                value={profile?.address}
                                className='input-profile'
                                onChange={(event) => updateInput('address', event.target.value)}
                            />
                            <ValidationField field='address' errors={errors} />
                        </div>

                        <div className='input-wrapper'>
                            <label htmlFor="user-location" className='label-profile'>Ubicación</label>
                            <input
                                type="text"
                                id="user-location"
                                name="user-location"
                                value={profile?.location}
                                className='input-profile'
                                onChange={(event) => updateInput('location', event.target.value)}
                            />
                            <ValidationField field='location' errors={errors} />
                        </div>

                        <div className='input-wrapper-div'>
                            <div className='input-wrapper-direction'>
                                <label htmlFor="user-latitude" className='label-profile'>Latitud</label>
                                <input
                                    type="text"
                                    id="user-latitude"
                                    name="user-latitude"
                                    value={profile?.latitude}
                                    className='input-profile'
                                    onChange={(event) => updateInput('latitude', event.target.value)}
                                />
                                <ValidationField field='latitude' errors={errors} />
                            </div>
                            <div className='input-wrapper-direction'>
                                <label htmlFor="user-longitude" className='label-profile'>Longitud</label>
                                <input
                                    type="text"
                                    id="user-longitude"
                                    name="user-longitude"
                                    value={profile?.longitude}
                                    className='input-profile'
                                    onChange={(event) => updateInput('longitude', event.target.value)}
                                />
                                <ValidationField field='longitude' errors={errors} />
                            </div>
                        </div>
                    </FormControl>
                </Box>

                {/* PREFERENCES */}
                <Container className='group-section'>
                    <Typography variant="h6" sx={{fontWeight: 700}}>Preferencias</Typography>

                    <Box className='form-field-preferences' >
                        <Box className='main-box-preferences' >
                            <Typography variant="body1" sx={{fontWeight: 600}} >Criterios de Busqueda</Typography>
                            <IconButton size='small' onClick={() => handleNavigateTo('/profile/search-criteria')} className='icon-style' data-testid="search-criteria-link"> 
                                <KeyboardArrowRightIcon/>
                            </IconButton>                            
                        </Box>

                        <Box className='main-box-preferences' >
                            <Typography variant="body1" sx={{fontWeight: 600}} >Ingredientes a evitar</Typography>
                            <IconButton size='small' onClick={() => handleNavigateTo('/profile/ingredient-criteria/avoid')} className='icon-style'> 
                                <KeyboardArrowRightIcon/>
                            </IconButton>
                        </Box> 

                        <Box className='main-box-preferences' >
                            <Typography variant="body1" sx={{fontWeight: 600}} >Ingredientes preferidos</Typography>
                            <IconButton size='small' onClick={() => handleNavigateTo('/profile/ingredient-criteria/prefers')} className='icon-style'> 
                                <KeyboardArrowRightIcon/>
                            </IconButton>
                        </Box>
                    </Box>

                </Container>
                
                <Button type='submit' variant="contained" className='btn-primary' data-testid="guardar-perfil-btn">Guardar</Button>
                <LogoutButton/>
            </form>

        </Container>
    )
}

export default Profile