import type { HTMLInputAttributes } from 'svelte/elements'

export enum InputTypes {
  Normal = 'normal',
  Hidden = 'hidden',
  Select = 'select',
  Checkbox = 'checkbox',
}
export type OptionalProps = {
  [key: string]: unknown // diff vals
}

// Extension de la clase HTMLAttributes
export interface InputPropsI extends HTMLInputAttributes {
    label_text: string
    label_for: string
    input_type: InputTypes
    value: number | string
}
