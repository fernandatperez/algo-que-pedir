import { Box, IconButton, Typography } from '@mui/material'
import { ReactNode } from 'react'
import './restaurant-card.css'

const RestaurantCard = ({src, alt, name, detail, detail2, icon, cardOnClickFunction, buttonOnClickFunction, classNameCard, classNameImage, classNameContent, classNameIcon, id}:{src: string, alt: string, name: string, detail: string, detail2?: string, icon: ReactNode, cardOnClickFunction?: () => void, buttonOnClickFunction?: () => void, classNameCard?:string, classNameImage?:string, classNameContent?:string, classNameIcon?:string, id?: number}) => {
    return(
        <Box className={`main-box-restaurant ${classNameCard  ?? ''}`}>  
            <Box className='box-item-restaurant' onClick={cardOnClickFunction}>
                <Box
                    component='img'
                    src = {src}
                    alt = {alt}
                    className={`${classNameImage ?? 'img-restaurant'}`}
                />
                <Box className={`restaurant-card-content ${classNameContent ?? ''}`}>
                    <Typography variant="body2" className="restaurant-title" sx={{ fontWeight: 600 }} color='gray' data-testid="restaurant-title">{name}</Typography>
                    <Typography variant="body2" className="restaurant-description"  color='gray'>{detail}</Typography>
                    <Typography variant="body2" className="restaurant-description" color='gray'>{detail2}</Typography>
                </Box>
            </Box>
            
            <Box className={`${classNameIcon ?? 'icon-custom'}`} onClick={buttonOnClickFunction} >
                {icon}
            </Box>
        </Box>
    )
}

export default RestaurantCard