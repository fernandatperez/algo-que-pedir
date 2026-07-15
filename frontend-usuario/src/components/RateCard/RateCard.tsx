import { Box, Rating, Typography } from '@mui/material'
import './rateCard.css'

function RateCard({ calificacion, comentario } : { calificacion: number, comentario: string }) {
  return(
    <>
        <Box className='main-box-review'>  
            <Box className='box-item-review'>
                <Box>
                    <Rating name="read-only" value={calificacion} readOnly />
                    <Typography variant="body2" color='gray' data-testid="reseña-texto">"{comentario}"</Typography>
                </Box>
            </Box>
        </Box>
    </>
    )
}

export default RateCard