import { Box, Typography, IconButton } from '@mui/material'
import ArrowBackIcon from '@mui/icons-material/ArrowBack'
import { useNavigate } from 'react-router-dom'
import './header-back.css'

type BackToRoute = string | {
    path: string;
    state?: unknown;
    isNew?: boolean
}

type HeaderProps = {
    title: string
    backTo: BackToRoute
    onClickCustom?: () => void
}


const HeaderBack = ({ title, backTo, onClickCustom = () => {} }: HeaderProps) => {
    const navigate = useNavigate()

    return (
        <Box className="header-container">
            <Box className="header-content">
                <IconButton
                    onClick={() => {
                      onClickCustom() // funcion que quieras en el onClick (opcional)
                        if (typeof backTo === 'string') {
                        // navigate(-1) -> 
                        navigate(backTo)    
                    } else {
                        navigate(backTo.path, { state: backTo.state })
                        // navigate(-1) ->
                        }
                    }}
                    className="header-back-button"
                    data-testid="header-back-btn"
                >
                    <ArrowBackIcon />
                </IconButton>
                <Typography variant='h6' fontWeight='bold'>
                    {title}
                </Typography>
            </Box>
        </Box>
    )
}

export default HeaderBack