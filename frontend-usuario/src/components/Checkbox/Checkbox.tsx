import Checkbox from '@mui/material/Checkbox'
import { Box, Typography } from '@mui/material'

const label = { inputProps: { 'aria-label': 'Checkbox' } }

export default function ColorCheckboxes() {
  return (
    <Box display={'flex'} sx={{alignItems: 'center'}} marginLeft={1}>
      <Checkbox
        {...label}
        defaultChecked
        className='Checkbox'
        sx={{
          '&.Mui-checked': {
            color: '#de0d0d',
          },
        }}        
      />
      <Typography>
        Buscar locales cercanos
      </Typography>
    </Box>)
  
}