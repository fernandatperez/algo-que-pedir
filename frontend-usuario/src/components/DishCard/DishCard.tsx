import React from 'react'
import { Card, CardContent, CardMedia, Typography } from '@mui/material'

interface Dish {
    id: string | number
    tag?: string
    nombre: string
    descripcion: string
    precio: number
    imagen: string
}

interface DishCardProps {
    dish: Dish
    onOpen: (id: string | number) => void
}

const DishCard = ({ dish, onOpen }: DishCardProps) => {
    return (
        <Card
        data-testid={`dish-card-${dish.id}`}
        key={dish.id}
        onClick={() => onOpen(dish.id)}
        variant='outlined'
        className="dish-card"
        >
            <CardContent className="dish-card-content">
                {dish.tag && (
                    <Typography variant='caption' color='error' className="dish-tag">
                        {dish.tag}
                    </Typography>
                )} {/* si tiene tag le pone esto, es un if */}
                <Typography className="dish-title">{dish.nombre}</Typography>
                <Typography variant='body2' className="dish-description">
                    {dish.descripcion}
                </Typography>
                <Typography className="dish-price">
                    ${dish.precio.toFixed(2)}
                </Typography>
            </CardContent>
            <CardMedia
                component='img'
                image={dish.imagen}
                alt={dish.nombre}
                className="dish-image"
                />
        </Card>
    )
}

export default DishCard