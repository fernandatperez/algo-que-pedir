import * as React from 'react'
import {
    Box,
    Container,
    Typography,
    Button,
    IconButton,
    Divider,
    MenuItem,
    Select,
    FormControl,
} from '@mui/material'
import CloseIcon from '@mui/icons-material/Close'
import HeaderBack from '../../components/HeaderBack/HeaderBack'
import './order-checkout.css'
import { useCart } from '../../contexts/CartContext'
// import { StoreDetailJSON } from '../../domain/store'
import { storeService } from '../../services/LocalesService'
import { useLocation } from 'react-router-dom'
import { useOnInit } from '../../customHooks/useOnInit'
import { orderService } from '../../services/orderService'
import { Order, OrderForBack } from '../../domain/order'
import { PaymentType, Store } from '../../domain/storeDom'
import { Estado } from '../../domain/order'
import { useToast } from '../../components/Toast/useToast'
import { Toast } from '../../components/Toast/ToastContainer'
import { useNavigate } from 'react-router-dom'
import { userService } from '../../services/UserService'
import { getErrorMessage } from '../../domain/errorHandler'

const OrderCheckout = () => {
    // const [items, setItems] = React.useState<OrderItemType[]>(ordersMock)
    const [paymentMethod, setPaymentMethod] = React.useState<PaymentType>()
    const { toast, showToast } = useToast()
    const navigate = useNavigate()

    const paymentLabels: Record<PaymentType, string> = {
    [PaymentType.EFECTIVO]: 'Efectivo',
    [PaymentType.TRANSFERENCIA_BANCARIA]: 'Transferencia Bancaria',
    [PaymentType.QR]: 'Código QR'
    }

    const { items, removeItem, clearCart, getTotalPrice, currentLocalId, setCurrentPayment, currentPaymentMethod } = useCart()

    const handleClearCart = () => {
        clearCart()
    }
    
    const handleConfirmOrder = async () => {
        try {
            await userService.confirmOrder(Number(order?.id))
            
            showToast('Pedido confirmado', 'success')
            
            setTimeout(() => {
                clearCart()
                navigate('/order-details')
            }, 1500)
        } catch (error) {
            const errorMessage = getErrorMessage(error)
            showToast('Error al confirmar el pedido. ' + errorMessage, 'error')
            
        }
    }
    
    const [store, setStore] = React.useState<Store>()
    const [order, setOrder] = React.useState<Order>()
    
    const setPayment = (payment: PaymentType) => {
        setPaymentMethod(payment)
        setCurrentPayment(payment)
    }
    
    // estas variables sueltas se recalculan en cada render
    const subtotal = getTotalPrice()
    let serviceFee = currentPaymentMethod == 'EFECTIVO' ? 0 : subtotal * 0.1
    const total = subtotal + serviceFee + (store?.deliveryFee as number)
    
    const handleReserveOrder = async () => {
        try {
            // Set de ids de platos
            const itemsIDs = items.flatMap(plato =>
                Array(plato.quantity).fill(plato.id)
            )

            const orderData: OrderForBack = {
                // paso los ids de y el back se encarga de buscarlos
                userID: Number(localStorage.getItem('id')),
                localID: effectiveLocalId!,
                platosIDs: itemsIDs,
                medioDePago: currentPaymentMethod as PaymentType, 
                estado: Estado.PENDIENTE,
                subtotal: subtotal
            }
            // console.info(orderData)

            await orderService.createOrder(orderData)
            showToast('Pedido reservado', 'success')
            
            setTimeout(() => {
                clearCart()
                navigate('/home')
            }, 1000)
            
        } catch (error) {
            const errorMessage = getErrorMessage(error)
            showToast('Error al crear el pedido. ' + errorMessage, 'error')
        }
    }

    const location = useLocation()

    const { id } = location.state as { id: number }
    const { isNew } = location.state as { isNew: boolean }
    const { orderId } = location.state as { orderId: number }
    

    const effectiveLocalId = isNew ? (currentLocalId || id) : null
    // console.log(effectiveLocalId)

    const getStoreData = async () => {
        const backStoreResponse = await storeService.getStore(effectiveLocalId)
        setStore(backStoreResponse)
        // console.log(backStoreResponse.paymentTypes)
        setPaymentMethod(backStoreResponse?.paymentTypes[0] as PaymentType)
    }

    const getOrderandStoreData = async () => {
        const backOrderResponse = await orderService.getOrderByID(orderId)
        setOrder(backOrderResponse)   
        const backStoreResponse = await storeService.getStore(id)
        setStore(backStoreResponse)
    }

    useOnInit(() => {
        // console.info(id)
        // console.info(isNew)
        // console.info(orderId)
        isNew ? getStoreData() : getOrderandStoreData()
    })

    return (
        <Box className="order-checkout-container">
            {/* Asi anda tambien */}
            {/* <HeaderBack title={'Tu pedido'} backTo={isNew ? { path: `/store-detail/${id}` } : { path: '/order-details/'}} />  */}
            <HeaderBack title={'Tu pedido'} backTo={isNew ? { path: `/store-detail/${id}` } : { path: '/order-details/'}} />

            <Container className="order-content-container">
                {/* ==================== Restaurant Info ==================== */}
                <Box className="restaurant-section">
                    <Typography variant='h6' className="section-title-order-checkout">
                        Restaurante
                    </Typography>
                    <Box className="restaurant-info-box">
                        <Box
                            component='img'
                            src={store?.storeURL}
                            alt={store?.name}
                            className="restaurant-logo"
                        />
                        <Box>
                            <Typography className="restaurant-name" data-testid='restaurant-name-test'>
                                {store?.name}
                            </Typography>
                            <Typography variant='body2' className="restaurant-details">
                                {store?.gradePointAvg} · {store?.userDistance.toFixed(1)} km · Envío gratis
                            </Typography>
                        </Box>
                    </Box>
                </Box>

                <Divider className="section-divider" />

                {/* ==================== Items ==================== */}
                <Box className="items-section">
                    <Typography variant='h6' className="section-title-order-checkout">
                        Artículos
                    </Typography>
                    {isNew ? (items.map((item) => (
                        <Box key={item.id} className="item-row">
                            <Box className="item-info">
                                <Box className="item-name-container">
                                    <Typography className="item-name">
                                        {item.title}
                                    </Typography>
                                </Box>
                                <Typography variant='body2' className="item-quantity" data-testid={`item-quantity-${item.id}`}>
                                    Cantidad: {item.quantity}
                                </Typography>
                                <Typography variant='body2' className="item-unit-price">
                                    Precio unitario: ${item.unitPrice.toFixed(2)}
                                </Typography>
                            </Box>

                            <Box className="item-price-actions">
                                <Typography className="item-total">
                                    ${item.totalPrice.toFixed(2)}
                                </Typography>
                                <IconButton
                                    onClick={() => removeItem(item.id)}
                                    size='small'
                                    className="remove-item-button"
                                >
                                    <CloseIcon />
                                </IconButton>
                            </Box>
                        </Box>
                    ))) : (order?.platosSinRepetir().map((plato) => (
                        <Box key={plato.id} className="item-row">
                            <Box className="item-info">
                                <Box className="item-name-container">
                                    <Typography className="item-name">
                                        {plato.nombre}
                                    </Typography>
                                </Box>
                                <Typography variant='body2' className="item-quantity">
                                    Cantidad: {order.aparicionesDe(plato.id)}
                                </Typography>
                                <Typography variant='body2' className="item-unit-price">
                                    Precio unitario: ${plato.precio.toFixed(2)}
                                </Typography>
                            </Box>

                            <Box className="item-price-actions">
                                <Typography className="item-total">
                                    ${(plato.precio * order.aparicionesDe(plato.id)).toFixed(2)}
                                </Typography>
                            </Box>
                        </Box>
                    )))}
                </Box>

                <Divider className="section-divider" />

                {/* ==================== Summary ==================== */}
                <Box className="summary-section">
                    <Typography variant='h6' className="section-title-order-checkout">
                        Resumen
                    </Typography>
                    <Box className="summary-row">
                        <Typography variant='body2' className="summary-label">
                            Subtotal
                        </Typography>
                        <Typography variant='body2' className="summary-value" data-testid='subtotal-value'>
                            ${isNew ? subtotal.toFixed(2) : order?.precioSubtotal.toFixed(2)}
                        </Typography>
                    </Box>
                    <Box className="summary-row">
                        <Typography variant='body2' className="summary-label">
                            Recargo por tipo de pago
                        </Typography>
                        <Typography variant='body2' className="summary-value" data-testid='service-fee'>
                            ${isNew ? serviceFee.toFixed(2) : order?.aCobrarPorPedido().toFixed(2)}
                        </Typography>
                    </Box>
                    <Box className="summary-row">
                        <Typography variant='body2' className="summary-label">
                            Tarifa de entrega
                        </Typography>
                        <Typography variant='body2' className="summary-value" data-testid="delivery-fee">
                            ${isNew ? store?.deliveryFee.toFixed(2) : order?.local.deliveryFee.toFixed(2)}
                        </Typography>
                    </Box>
                </Box>

                <Divider className="section-divider" />

                {/* ==================== Total ==================== */}
                <Box className="total-section">
                    <Typography variant='h6' className="total-label">
                        Total
                    </Typography>
                    <Typography variant='h6' className="total-value" data-testid="total-value">
                        ${isNew ? total.toFixed(2) : order?.precioTotal().toFixed(2)}
                    </Typography>
                </Box>

                <Divider className="section-divider" />

                {/* ==================== Payment Method ==================== */}
                <Box className="payment-section">
                    <Typography variant='body2' className="payment-label">
                        Forma de pago
                    </Typography>
                    {isNew ? (store?.paymentTypes.length ? (
                        <FormControl fullWidth>
                            <Select
                                value={currentPaymentMethod == null ? paymentMethod : currentPaymentMethod}
                                onChange={(e) => setPayment(e.target.value as PaymentType)}
                                className="payment-select"
                                data-testid="payment-select"
                                >
                                {store?.paymentTypes?.map((pago) => (
                                    <MenuItem key={pago} value={pago}>
                                        {paymentLabels[pago]}
                                    </MenuItem>
                                ))}
                            </Select>
                        </FormControl>) : (null)) : (
                            <FormControl fullWidth disabled>
                                <Select value={order?.metodoDePago ?? ''} data-testid="payment-select">
                                    <MenuItem value={order?.metodoDePago}>{paymentLabels[order?.metodoDePago!]}</MenuItem>
                                    {store?.paymentTypes
                                    // .filter((pago) => (pago == order?.metodoDePago))
                                    .map((pago: PaymentType) => (
                                        <MenuItem key={pago} value={pago}>
                                            {paymentLabels[pago]}
                                        </MenuItem>)
                                )}
                                </Select>
                            </FormControl>
                        )}
                </Box>
            </Container>

            {/* ==================== Fixed Bottom Buttons ==================== */}
            { isNew || order?.estado === 'PENDIENTE' ? (
                <Box className="bottom-buttons-container">
                    <Button
                        fullWidth
                        variant='contained'
                        color='error'
                        onClick={isNew ? handleReserveOrder : handleConfirmOrder}
                        disabled={isNew && items.length === 0}
                        className="confirm-order-button"
                        data-testid="reservar-confirmar-pedido-btn"
                    >
                        {isNew ? 'Reservar pedido' : 'Confirmar Pedido'}
                    </Button>
                    { isNew ? <Button
                        fullWidth
                        variant='outlined'
                        color='error'
                        onClick={handleClearCart}
                        disabled={items.length === 0}
                        className="clear-cart-button"
                    >
                        Limpiar carrito de compras
                    </Button> : ''}
                </Box>
            ) : ''}

            {/* ==================== Toast ==================== */}
            <div id="toast-container" data-testid='toast-orderCheckout-test'>
                <Toast toast={toast} />
            </div>

        </Box>
    )
}

export default OrderCheckout