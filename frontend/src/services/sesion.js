const CLAVE = 'sesionBarberia'

export function guardarSesion(sesion) {
  try {
    localStorage.setItem(CLAVE, JSON.stringify(sesion))
  } catch {
  }
}

export function obtenerSesion() {
  try {
    return JSON.parse(localStorage.getItem(CLAVE))
  } catch {
    return null
  }
}

export function cerrarSesion() {
  try {
    localStorage.removeItem(CLAVE)
  } catch {
  }
}
