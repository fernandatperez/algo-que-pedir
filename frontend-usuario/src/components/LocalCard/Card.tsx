import {  Typography } from '@mui/material'
import { useNavigate } from 'react-router-dom'
import { StoreType } from '../../domain/store'
import {
  CardsContainer,
  StoreCard,
  ImageContainer,
  StoreImage,
  StoreCardContent,
  NearbyStoreContainer,
  NearbyIcon,
  NearbyText,
  NoStoresText
} from './StyledCard'

interface MediaCardProps {
  stores: StoreType[];
}

export default function MediaCard({ stores }: MediaCardProps) {
  const navigate = useNavigate()

  const handleCardClick = (storeId: number) => {
    navigate(`store-detail/${storeId}`)
  }

  return (
    <CardsContainer sx={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'center' }} data-testid="media-card-container">
      {stores.length > 0 ? (
        stores.map((store) => (
          <StoreCard 
            key={store.id}
            onClick={() => handleCardClick(store.id)} 
            data-testid={`store-card-${store.id}`}
          >
            <ImageContainer>
              <StoreImage
                image={store.storeURL}
                title={`Imagen de ${store.name}`}
              />
            </ImageContainer>
            <StoreCardContent>
              <Typography noWrap component="div" className="store-name" data-testid={`store-name-${store.id}`}>
                {store.name}
              </Typography>
              <Typography noWrap variant="body2" className="store-address"data-testid={`store-address-${store.id}`}>
                {store.storeAddress} {store.storeAltitude}
              </Typography>
              {store.usuarioCercano && (
                <NearbyStoreContainer data-testid={`nearby-store-${store.id}`}>
                  <NearbyIcon />
                  <NearbyText variant="caption">
                    Local cercano
                  </NearbyText>
                </NearbyStoreContainer>
              )}
            </StoreCardContent>
          </StoreCard>
        ))
      ) : (
        <NoStoresText data-testid="no-stores-text">
          No hay locales disponibles
        </NoStoresText>
      )}
    </CardsContainer>
  
  )
}