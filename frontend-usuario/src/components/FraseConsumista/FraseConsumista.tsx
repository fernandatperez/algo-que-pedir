import Chip from '@mui/material/Chip'
import Stack from '@mui/material/Stack'

const FraseConsumista = ({ frase, eliminarFrase }: {frase: string, eliminarFrase: (frase: string) => void}) => {

    const handleDelete = () => {
        eliminarFrase(frase)
    }

  return (
    <Stack direction="row" spacing={1}>
      <Chip label={frase} variant="outlined" onDelete={handleDelete}
      slotProps={{
        label: { 'data-testid': `frase-${frase}` } as any
      }} />
    </Stack>
  )
}

export default FraseConsumista