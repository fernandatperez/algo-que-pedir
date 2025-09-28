import { writable } from 'svelte/store'

export type Toast = {
  id: number
  message: string
  type?: 'error' | 'info' | 'success'
  duration?: number
}

function createToastStore() {
  const { subscribe, update } = writable<Toast[]>([])
  let counter = 0

  function push(message: string, opts: Partial<Omit<Toast, 'id'>> = {}) {
    const id = ++counter
    const toast: Toast = { id, message, type: 'info', duration: 3000, ...opts }
    update((toasts) => [...toasts, toast])

    // auto-remove after duration
    setTimeout(() => {
      remove(id)
    }, toast.duration)

    return id
  }

  function remove(id: number) {
    update((toasts) => toasts.filter((toast) => toast.id !== id))
  }

  return { subscribe, push, remove }
}

export const toasts = createToastStore()
