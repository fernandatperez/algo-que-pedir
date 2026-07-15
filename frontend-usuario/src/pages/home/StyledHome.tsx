import { Box, Typography, styled } from '@mui/material'

export const HomeContainer = styled(Box)({
  width: '100vw',
  flexGrow: 1,
  minHeight: '100vh',
})

export const HeaderContainer = styled('div')(({ theme }) => ({
  display: 'flex',
  justifyContent: 'space-between',
  overflow: 'hidden',
  paddingLeft: 40,
  paddingRight: 40,
  marginTop: 30,
  marginBottom: 30
}))

export const TitleTypography = styled(Typography)({
  fontSize: 18,
  fontWeight: 'bolder'
})

export const SearchContainer = styled('div')({
  marginTop: 10
})

export const ContentBox = styled(Box)(({ theme }) => ({
  width: '100%',
  padding: theme.spacing(3),
  marginTop: '10px',
}))

export const StoresCountTypography = styled(Typography)(({ theme }) => ({
  marginTop: theme.spacing(1),
  marginLeft: theme.spacing(1),
  color: theme.palette.text.secondary
}))

export const CardsContainer = styled(Box)({
  display: 'flex',
  width: '100%'
})