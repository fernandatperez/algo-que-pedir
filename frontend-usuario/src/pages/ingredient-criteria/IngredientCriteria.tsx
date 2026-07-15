import { Box, Button, Card, Container, IconButton, Modal, Typography } from '@mui/material'
import { useParams } from 'react-router-dom'
import { useState } from 'react'
import ClearIcon from '@mui/icons-material/Clear'
import HeaderBack from '../../components/HeaderBack/HeaderBack'
import { IngredientType } from '../../domain/ingredient'
import { useOnInit } from '../../customHooks/useOnInit'
import { ingredientService } from '../../services/IngredientService'
import { useUserProfile } from '../../customHooks/useUserProfile'

import './ingredient-criteria.css'


// sessionStorage.setItem('id', '1')
// sessionStorage.setItem('email', 'sofiamiller@gmail.com')
// Sacar esto
// sessionStorage.setItem('id', localStorage.getItem('id'))
// sessionStorage.setItem('email', localStorage.getItem('email'))

const IngredientCriteria = () => {
    const { criteria } = useParams()
    const { profile, setProfile, checkChanges, showToast } = useUserProfile()

    const title = criteria === 'avoid' ? 'Ingredientes a evitar' : 'Ingredientes preferidos'

    const preferred = profile?.preferredIngredients || []
    const avoided = profile?.ingredientsToAvoid || []

    const currentList =
        criteria === 'avoid' ? avoided : preferred

    const [allIngredients, setAllIngredients] = useState<IngredientType[]>([])
    const [open, setOpen] = useState(false)
    const [selectedIds, setSelectedIds] = useState<number[]>([])

    useOnInit(() => {
        getAllIngredients()
    })

    const getAllIngredients = async () => {
        try {
            const all = await ingredientService.getAllIngredients()
            setAllIngredients(all)
        } catch (err) {
            console.error('Error loading ingredients', err)
            showToast('Error al cargar los ingredientes. Intente nuevamente.', 'error')
        }
    }

    // ingredientes que no estan en ninguna de las dos listas
    const availableIngredients = allIngredients.filter(
        ing =>
            !preferred.some(i => i.id === ing.id) &&
            !avoided.some(i => i.id === ing.id)
    )

    const handleSelect = (id: number) => {
        setSelectedIds(prev =>
            prev.includes(id)
                ? prev.filter(x => x !== id)
                : [...prev, id]
        )
    }

    const handleSave = () => {
        const selected = availableIngredients.filter(i => selectedIds.includes(i.id!))
        const updatedList = [...currentList, ...selected]

        // actualizar el profile global directamente
        setProfile(prev => {
            const updated = Object.assign(
                Object.create(Object.getPrototypeOf(prev)),
                prev
            )

            if (criteria === 'prefers') {
                updated.preferredIngredients = updatedList
            } else {
                updated.ingredientsToAvoid = updatedList
            }

            return updated
        })

        setSelectedIds([])
        setOpen(false)
    }

    const handleRemove = (id: number) => {
        const updatedList = currentList.filter(i => i.id !== id)

        setProfile(prev => {
            const updated = Object.assign(
                Object.create(Object.getPrototypeOf(prev)),
                prev
            )

            if (criteria === 'prefers') {
                updated.preferredIngredients = updatedList
            } else {
                updated.ingredientsToAvoid = updatedList
            }

            return updated
        })
    }

    return (
            <Container className="main-container-search" sx={{ pb: 9}}>
                
                <HeaderBack title={title} backTo="/profile" onClickCustom={checkChanges}/>

                <Box className="box-section-criteria">
                    <Box className="box-ingredients ingredients-group">
                        {currentList.length > 0 ? currentList.map(ing => (
                            <div key={ing.id} className="ingredient-item">
                                <Typography variant="body2">{ing.name}</Typography>
                                <IconButton size="small" className="icon-style" onClick={() => handleRemove(ing.id!)}>
                                    <ClearIcon />
                                </IconButton>
                            </div>
                        )) : (
                            <Typography variant="body2" className="no-ingredients">
                                No seleccionaste ingredientes
                            </Typography>
                        )}
                    </Box>
                </Box>

                {/* MODAL */}
                <Modal open={open} onClose={() => setOpen(false)}>
                    <Box className="modal">
                        <Card className="card-modal">
                            <Typography variant="h6">Seleccione</Typography>

                            <div className='div-scroll-ingredients'>
                                {availableIngredients.length > 0 ? (
                                    availableIngredients.map(ing => (
                                        <Box key={ing.id} className="modal-items">
                                            <label className="label-item">
                                                <input
                                                    type="checkbox"
                                                    checked={selectedIds.includes(ing.id!)}
                                                    onChange={() => handleSelect(ing.id!)}
                                                />
                                                {ing.name}
                                            </label>
                                        </Box>
                                    ))
                                ) : (
                                    <Typography variant="subtitle1">No hay ingredientes disponibles</Typography>
                                )}
                            </div>

                            <div className="btn-group">
                                <Button variant="contained" className="btn-secondary" onClick={() => setOpen(false)}>
                                    Cancelar
                                </Button>
                                <Button variant="contained" className="btn-primary" onClick={handleSave}>
                                    Agregar
                                </Button>
                            </div>
                        </Card>
                    </Box>
                </Modal>

            <Box className="see-order-container" sx={{ mt: 'auto' }}>
                <Button
                    variant="contained"
                    className="btn-primary btn-add-ingredient"
                    onClick={() => setOpen(true)}
                >
                    Añadir ingrediente
                </Button>
            </Box>
            
        </Container>
    )
}

export default IngredientCriteria
