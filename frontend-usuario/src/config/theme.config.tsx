import { ThemeProvider } from '@emotion/react'
import { JSX } from '@emotion/react/jsx-runtime'
import { createTheme, CssBaseline } from '@mui/material'
import React from 'react'

type themeProp = {
    children: JSX.Element
}

enum themePalette {
    background = '#F7F3F2',
    iconColor = '#383636',
    lightBackground = '#FFFFFF',
}

const theme = createTheme({
    palette: {
        mode: 'light',
        background: {
            default: themePalette.background,
            paper: themePalette.lightBackground,
        },
        primary: {
            main: themePalette.iconColor,
            contrastText: themePalette.lightBackground,
        },
    
    },
    components: {
        MuiAppBar: {
            styleOverrides: {
                root: {
                    backgroundColor: themePalette.lightBackground, 
                    color: themePalette.iconColor, 
                    boxShadow: '0 2px 4px rgba(0,0,0,0.1)', 
                },
            },
        },
    },
})

export const ThemeConfig: React.FC<themeProp> = ({ children }) => {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            {children}
        </ThemeProvider>
    )
}