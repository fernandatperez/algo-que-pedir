import { Button, Card, Container, Divider, Typography } from '@mui/material'
import { useState } from 'react'
import RestaurantCard from '../../components/RestaurantCard/RestaurantCard'
import { Navigator } from '../../routes/Navigator'
import { userService } from '../../services/UserService'
import { useOnInit } from '../../customHooks/useOnInit'
import './store-ratings.css'
import { Store } from '../../domain/storeDom'
import { useToast } from '../../components/Toast/useToast'
import { Toast } from '../../components/Toast/ToastContainer'
import { getErrorMessage } from '../../domain/errorHandler'

const StoreRatings = () => {
    const [unratedStores, setUnratedStores] = useState<Store[]>([])
    const { toast, showToast } = useToast()

    const navigation = Navigator()
    
    const getUnratedStores = async () => {
        try {
            const unratedStores: Store[] = await userService.getUnratedStores()
            setUnratedStores(unratedStores)
        } catch (error) {
            const errorMessage = getErrorMessage(error)
            showToast('Error al cargar las ordenes. ' + errorMessage, 'error')
        }
    }

    useOnInit(getUnratedStores)

    const showUnratedStores = () => {
        return unratedStores
        .map(store => 
            <Container sx={{padding: '0.5em'}} key={store.id} data-testid={`store-to-rate-${store.id}`}>
                <RestaurantCard 
                src={store.storeURL} 
                alt='Imagen de local' 
                name={store.name} 
                detail = {`${store.gradePointAvg} · ${store.deliveryTimeAvg} · ${store.isExpensive ? '$$' : '$'}`}
                icon={<Button sx={{textAlign: 'center', textTransform: 'none'}} variant="contained" color="success" data-testid={`calificar-${store.id}`}>Calificar</Button>}
                buttonOnClickFunction={() => navigation.goTo(`/rate-store/${store.id}`, { name : store.name })}
                />
            </Container>
        )
    }

    return (
        <>
            <div className='main-container' style={{padding: '0.5em'}}>
                {/* <section className='section-title-and-tabs'> */}
                    <Typography 
                    variant='h5' sx={{margin: '1.5rem 0'}}>
                        Restaurantes a calificar
                    </Typography>
                    <Divider sx={{width: '90%'}}/>
                    {unratedStores.length != 0 ? 
                        showUnratedStores() : 
                        <Card variant='outlined' className='no-unrated-stores-card'>
                            <Typography variant='subtitle1' sx={{margin: '2rem 0' }}>
                                Todavia no hay locales para puntuar
                            </Typography>
                            <Typography sx={{margin: '2rem 0', color: 'text.secondary'}}>
                                Realiza pedidos para poder otorgar calificaciones
                            </Typography>
                        </Card>}
                {/* </section> */}
                <div id="toast-container">
                    <Toast toast={toast} />
                </div>
            </div>
        </>
    )
}

export default StoreRatings