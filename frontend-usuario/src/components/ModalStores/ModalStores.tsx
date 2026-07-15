import { Modal, Box, Card, Typography, Button } from '@mui/material'
import RestaurantCard from '../RestaurantCard/RestaurantCard'

import '../../pages/search-criteria/search-criteria.css'
import './modal-stores.css'

import { Store } from '../../domain/storeDom'

const ModalStores = ({open, onClose, stores, selectedIds, onToggle, onConfirm}: {open: boolean, onClose: () => void, stores: Store[], selectedIds: number[], onToggle: (id: number) => void, onConfirm: () => void}) => {

    return (
        <Modal open={open} onClose={onClose}>
            <Box className="modal-box-fieles">
                <Card className="modal-card-fieles">

                    {/* HEADER */}
                    <Typography variant="h6" className="modal-title-fieles">
                        Seleccione restaurantes
                    </Typography>

                    {/* LISTA SCROLLEABLE */}
                    <div className="modal-scroll-fieles">
                        {stores.length > 0 ? (
                            stores.map(store => (
                                <div
                                    key={store.id}
                                    className={`store-item-fieles ${selectedIds.includes(store.id) ? 'selected' : ''}`}
                                    onClick={() => onToggle(store.id)}
                                >
                                    <RestaurantCard
                                        src={store.storeURL}
                                        alt={store.name}
                                        name={store.name}
                                        detail={`${store.gradePointAvg} · ${store.deliveryTimeAvg} · ${store.isExpensive ? '$$' : '$'}`}
                                        icon={
                                            <input
                                                type="checkbox"
                                                readOnly
                                                checked={selectedIds.includes(store.id)}
                                                className="checkbox-selector"
                                            />
                                        }
                                        classNameCard='restaurant-card'
                                        classNameImage='restaurant-image-left-profile'
                                        classNameContent='restaurant-card-content'
                                        classNameIcon='restaurant-icon'
                                    />
                                </div>
                            ))
                        ) : (
                            <Typography className='empty-stores'>No hay restaurantes disponibles</Typography>
                        )}
                    </div>

                    {/* FOOTER */}
                    <div className="modal-footer-fieles">
                        <Button
                            fullWidth 
                            variant="outlined"
                            color="error"
                            onClick={onClose}
                            className="modal-cancel"
                        >
                            Cancelar
                        </Button>

                        <Button
                            fullWidth 
                            variant="contained"
                            color="error"
                            onClick={onConfirm}
                            className="modal-add"
                        >
                            Agregar
                        </Button>
                    </div>

                </Card>
            </Box>
        </Modal>
    )
}

export default ModalStores
