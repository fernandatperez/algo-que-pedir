import { Card, CardContent, Typography, Box, styled, TypographyProps } from '@mui/material'
import PlaceIcon from '@mui/icons-material/Place'
import { CardMedia } from '@mui/material'
import { ReactNode } from 'react'

export const CardsContainer = styled(Box)({
  display: 'flex',
  flexWrap: 'wrap'
})

export const StoreCard = styled(Card)(({ theme }) => ({
  width: 150,
  height: 200,
  minWidth: 150,
  minHeight: 200,
  cursor: 'pointer',
  '&:hover': { 
    boxShadow: theme.shadows[3] 
  },
  margin: theme.spacing(2),
  padding: theme.spacing(2),
  display: 'flex',
  flexDirection: 'column',
  overflow: 'hidden'
}))

export const ImageContainer = styled(Box)({
  position: 'relative'
})

export const StoreImage = styled(CardMedia)({
  height: 100,
  width: '100%',
  objectFit: 'cover',
  position: 'relative'
})

export const StoreCardContent = styled(CardContent)(({ theme }) => ({
  flexGrow: 1,
  padding: theme.spacing(1),
  '& .store-name': {
    maxWidth: '100px',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap'
  },
  '& .store-address': {
    color: theme.palette.text.secondary,
    maxWidth: '100px',
    fontSize: '0.8rem',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap'
  }
}))

export const NearbyStoreContainer = styled(Box)(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  marginTop: theme.spacing(0.5),
  color: theme.palette.error.main,
  fontSize: '0.75rem',
  fontWeight: 'bold'
}))

export const NearbyIcon = styled(PlaceIcon)(({ theme }) => ({
  fontSize: '1rem',
  marginRight: theme.spacing(0.5)
}))

export const NearbyText = styled(Typography)(({ theme }) => ({
  color: theme.palette.error.main,
  fontWeight: 'bold'
})) as typeof Typography

export const NoStoresText = styled(Typography)(({ theme }) => ({
  padding: theme.spacing(2)
})) as typeof Typography

// Componente StoreName con tipos correctos
interface StoreNameProps extends TypographyProps {
  children: ReactNode;
}

export const StoreName = ({ children, ...props }: StoreNameProps) => (
  <Typography noWrap component="div" sx={{ maxWidth: '100px' }} {...props}>
    {children}
  </Typography>
)