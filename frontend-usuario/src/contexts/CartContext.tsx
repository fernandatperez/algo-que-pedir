import * as React from 'react'
import { createContext, useContext } from 'react'
import { PaymentType } from '../domain/storeDom'

//* me traigo cosas que no uso, ya se, pero despues se puede evaluar cuales se sacan
type CartItem = {
    id: number
    title: string
    desc: string
    img: string
    tag?: string
    quantity: number
    unitPrice: number
    totalPrice: number
    localName: string
    localId: number // esto es para no mezclar
}

type CartContextType = {
    items: CartItem[]
    currentLocalId: number | null
    addItem: (item: CartItem) => void
    removeItem: (id: number) => void
    clearCart: () => void
    getTotalPrice: () => number
    totalItems: () => number
    setCurrentLocal: (localId: number) => void
    setCurrentPayment: (payment: PaymentType) => void
    currentPaymentMethod: PaymentType | null
}

const CartContext = createContext<CartContextType | undefined>(undefined)

// este es el provedor de el contexto
export const CartProvider = ({children}: {children: React.ReactNode}) => {
    const [items, setItems] = React.useState<CartItem[]>([])
    const [currentLocalId, setCurrentLocalId] = React.useState<number | null>(null)
    const [currentPaymentMethod, setCurrentPaymentMethod] = React.useState<PaymentType>(PaymentType.EFECTIVO)
    
    const addItem = (item: CartItem) => {

        if (items.length > 0 && item.localName != items[0]?.localName) {
            throw new Error('No puedes agregar platos de diferentes locales')
        }

        setCurrentLocalId(item.localId)
        const itemInOrder = items.find(i => i.id == item.id)

        if (itemInOrder) {
            setItems(items.map(i => i.id === item.id 
                    ? { ...i, quantity: i.quantity + item.quantity, total: (i.quantity + item.quantity) * i.unitPrice }
                    : i
            ))
        } else {
            setItems([...items, item]) 
        }
        // en React no se puede modificar el estado original directamente, entonces por eso se hace un map o el spread operator (...), 
        // se crea un nuevo array y se lo asigna a items, no se puede usar push porque no lo vuelve a renderizar al ser el mismo objeto en memorio
    }

    const removeItem = (id: number) => {
        setItems(items.filter(item => item.id != id))
    }

    const clearCart = () => {
        setItems([])
        setCurrentLocalId(null)
    }

    const getTotalPrice = () => {
        return items.reduce((sum, item) => sum + item.totalPrice, 0)
    }

    const totalItems = () => {
        return items.reduce((sum, item) => sum + item.quantity, 0)
    }

    const setCurrentLocal = (localId: number) => {
        setCurrentLocalId(localId)
    }

    const setCurrentPayment = (payment: PaymentType) => {
        setCurrentPaymentMethod(payment)
    }

    return (
        <CartContext.Provider value={{ items, currentLocalId, addItem, removeItem, clearCart, getTotalPrice, totalItems, setCurrentLocal, setCurrentPayment, currentPaymentMethod }}>
            {children}
        </CartContext.Provider>
    )
}

// custom hook/hook helper para no repetir useContext()
// en cada componente y garantizar que se use dentro del provider
export const useCart = () => {
    const context = useContext(CartContext)
    if (!context) {
        throw new Error('useCart debe usarse dentro de CartProvider')
    }
    return context
}