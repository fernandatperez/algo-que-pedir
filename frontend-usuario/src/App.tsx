import { BrowserRouter } from 'react-router-dom'
import { AppRouter } from './routes/routes'
import { ThemeConfig } from './config/theme.config'
import { CartProvider } from './contexts/CartContext'


function App(){
  return(
    <ThemeConfig>
      <CartProvider>
          <BrowserRouter>
            <AppRouter />
          </BrowserRouter>
      </CartProvider>
    </ThemeConfig>
  )
}

export default App
