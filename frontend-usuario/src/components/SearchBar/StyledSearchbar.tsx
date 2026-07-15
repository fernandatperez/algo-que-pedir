import { Paper, Box, Toolbar, InputBase, styled } from '@mui/material'
import SearchIcon from '@mui/icons-material/Search'

export const StyledPaper = styled(Paper)(({ theme }) => ({
  marginTop: theme.spacing(2),
  marginRight: theme.spacing(5),
  marginBottom: theme.spacing(2),
  marginLeft: theme.spacing(5),
  top: 40,
  left: 40,
  right: 40
}))

export const StyledBox = styled(Box)({
  maxWidth: '100%',
  minWidth: '100%',
  borderRadius: 20
})

export const StyledToolbar = styled(Toolbar)({
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center'
})

export const SearchContainer = styled(Box)(({ theme }) => ({
  display: 'flex',
  alignItems: 'center',
  backgroundColor: 'white',
  borderRadius: theme.shape.borderRadius,
  padding: '0 10px',
  width: '100%',
  maxWidth: '600px'
}))

export const StyledSearchIcon = styled(SearchIcon)(({ theme }) => ({
  color: theme.palette.action.active,
  marginRight: theme.spacing(1),
  cursor: 'pointer',
  '&:hover': {
    color: theme.palette.primary.main
  }
}))

export const StyledInputBase = styled(InputBase)({
  width: '100%',
  '& .MuiInputBase-input': {
    padding: '8px 0'
  }
})