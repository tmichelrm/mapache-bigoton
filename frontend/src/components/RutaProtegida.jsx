import { Navigate, Outlet } from 'react-router-dom'
import { obtenerSesion } from '../services/sesion'

// El "portero": si no hay sesión iniciada, manda al Login.
export default function RutaProtegida() {
  return obtenerSesion() ? <Outlet /> : <Navigate to="/login" replace />
}
