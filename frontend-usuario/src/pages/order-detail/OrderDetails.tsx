import { Box, Typography, Tab, Container, Button} from '@mui/material'
import TabContext from '@mui/lab/TabContext'
import TabList from '@mui/lab/TabList'
import TabPanel from '@mui/lab/TabPanel'
import '../../index.css'
import './order-details.css'
import { useState } from 'react'
import { Order } from '../../domain/order'
import { orderService } from '../../services/orderService'
import { useOnInit } from '../../customHooks/useOnInit'
import RestaurantCard from '../../components/RestaurantCard/RestaurantCard'
import { useNavigate } from 'react-router-dom'
import { userService } from '../../services/UserService'
import { Toast } from '../../components/Toast/ToastContainer'
import { useToast } from '../../components/Toast/useToast'
import { getErrorMessage } from '../../domain/errorHandler'

function OrderDetails () {
  const [orders, setOrders] = useState<Order[]>([])
  const { toast, showToast } = useToast()
  const [state, setState] = useState('PENDIENTE')
  const navigate = useNavigate()

  const handleStateChange = (newState: string) => {
    setState(newState)
    getOrders(newState) 
  }

  const getOrders = async (newState: string) => {
    try {
        const newOrders = await orderService.getFilteredUserOrders(newState)
        setOrders(newOrders)
    } catch (error) {
      const errorMessage = getErrorMessage(error)
      showToast('Error al cargar las ordenes. ' + errorMessage, 'error')
    }
  }

  const cancelOrder = async (id: number) => {
    try {
      userService.cancelOrder(id)
      setOrders(prev => prev.filter(order => order.id != id))
    } catch (error) {
      const errorMessage = getErrorMessage(error)
      showToast('Error al cancelar la orden. ' + errorMessage, 'error')
    }
  }

  const showOrders = () => {
    return orders.map(order => 
      <Container sx={{padding: '0.5em'}} key={order.id} data-testid={`restaurant-card-${order.local.id}-${order.id}`}>
        <RestaurantCard 
          src={order.local.storeURL} 
          alt='Imagen de local' 
          name={order.local.name} 
          detail={'Total: $' + order.precioTotal().toFixed(2)}
          detail2 = {order.fechaCreacionString + ' · ' + order.platos.length + ' productos'}
          icon={order.estado == 'CANCELADO' ? '' : <Button color="error" className='btn-primary' data-testid={`cancel-btn-${order.id}`}>Cancelar</Button>}
          cardOnClickFunction={() => navigate('/order-checkout', {state: {id: order.local.id, isNew: false, orderId: order.id}})}
          buttonOnClickFunction={() => cancelOrder(order.id)}
          id={order.id}
        />
      </Container>
      )
  }

  useOnInit(() => handleStateChange(state))

  return (
    <>
    <div className='main-container' style={{padding: '0.5em'}}>
      <section className='section-title-and-tabs'>
        <Typography 
          variant='h5' sx={{margin: '1rem 0'}}>
            Pedidos
        </Typography>
        <Box sx={{ width: '100%', typography: 'body1'}}>
          <TabContext value={state}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
              <TabList onChange={(_, value) => handleStateChange(value)} aria-label='Tab-list' variant='scrollable'>
                <Tab label='Pendientes' value='PENDIENTE'/>
                <Tab label='Confirmados' value='CONFIRMADO'/>
                <Tab label='Completados' value='PREPARADO'/>
                <Tab label='Cancelados' value='CANCELADO'/>
              </TabList>
            </Box>
            <TabPanel value={state} sx={{padding: 0, marginTop: '0.5em'}}>
              {orders.length != 0 ?
              showOrders() :
              <Typography 
                variant='subtitle1' sx={{margin: '2rem 0', color: 'text.secondary'}}>
                  No hay pedidos para mostrar
              </Typography>
              }
            </TabPanel>
          </TabContext>
        </Box>
      </section>
      <div id="toast-container">
        <Toast toast={toast} />
      </div>
    </div>
    </>
  )
}

export default OrderDetails