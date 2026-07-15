
import React, { JSX, useState } from 'react'
import { AppBar } from '@mui/material'
import {
  StyledPaper,
  StyledBox,
  StyledToolbar,
  SearchContainer,
  StyledSearchIcon,
  StyledInputBase
} from './StyledSearchbar'

interface SearchBarProps {
  onSearch: (searchValue: string) => void;
  searchValue: string;
}

export default function SearchBar({ onSearch, searchValue }: SearchBarProps): JSX.Element {
  const [localSearchValue, setLocalSearchValue] = useState(searchValue)
  const [oldSearchValue, setOldSearchValue] = useState('')

  const handleSearchClick = (): void => {
    if (oldSearchValue.trim().toLowerCase() !== localSearchValue.trim().toLowerCase()) {
      onSearch(localSearchValue)
      console.info('buscando', localSearchValue)
      setOldSearchValue(localSearchValue)
    }
  }

  const handleKeyPress = (event: React.KeyboardEvent): void => {
    if (event.key === 'Enter') {
      handleSearchClick()
    }
  }

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>): void => {
    const value = event.target.value
    setLocalSearchValue(value)
  }

  return (
    <StyledPaper elevation={3}>
      <StyledBox>
        <AppBar position="static">
          <StyledToolbar>
            <SearchContainer>
              <StyledSearchIcon onClick={handleSearchClick} />
              <StyledInputBase
                name='search'
                placeholder="Buscá tu local para pedir…"
                inputProps={{ 'aria-label': 'search' }}
                value={localSearchValue}
                onChange={handleInputChange}
                onKeyUp={handleKeyPress}
                data-testid="search-input"
              />
            </SearchContainer>
          </StyledToolbar>
        </AppBar>
      </StyledBox>
    </StyledPaper>
  )
}