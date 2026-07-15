import { useEffect, useState } from 'react'
import { ValidationMessage } from '../../domain/validationMessage'
import './validationField.css'

const ValidationField = ({field, errors} : {field: string, errors: ValidationMessage[]}) => {
  
    const errorsFrom = (errors: ValidationMessage[], field: string) => errors
    .filter((_) => _.field === field)
    .map((_) => _.message)
    .join('. ')

    const [errorMessage, setErrorMessage] = useState<string>('')
    
    useEffect(() => {
        const newMessage = errorsFrom(errors, field)
        setErrorMessage(newMessage)

        const timer = setTimeout(() => {
            setErrorMessage('')
        }, 4000)

        return () => clearTimeout(timer)
    }, [errors])

    return (
        <>
        {!!errorMessage && (
            <div className='error' data-testid={'error-field-' + field}>
                {errorMessage}
            </div>
        )}
        </>
  )
}

export default ValidationField