// Datos de ejemplo con la MISMA forma que la base de datos BarberiaDB.
// Solo se usan mientras USAR_API = false en services/api.js.
// Las fechas son relativas a HOY, para que el Panel y las Citas siempre muestren algo.

const pad = (n) => String(n).padStart(2, '0')

function fechaISO(desplazamientoDias) {
  const d = new Date()
  d.setDate(d.getDate() + desplazamientoDias)
  return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}`
}

const HOY = fechaISO(0)
const MANANA = fechaISO(1)
const AYER = fechaISO(-1)

export const clientes = [
  { idCliente: 1, nombre: 'Alejandro Ruiz', telefono: '55 1824 6912', email: null },
  { idCliente: 2, nombre: 'Ricardo Salas', telefono: '55 9173 2048', email: null },
  { idCliente: 3, nombre: 'Santiago León', telefono: '55 6302 1187', email: null },
  { idCliente: 4, nombre: 'Emilio Navarro', telefono: '55 4408 7625', email: null },
  { idCliente: 5, nombre: 'Julián Herrera', telefono: '55 8031 4490', email: null },
]

export const barberos = [
  { idBarbero: 1, nombre: 'Diego Morales', especialidad: 'Clásicos y barba', activo: true },
  { idBarbero: 2, nombre: 'Mateo Vargas', especialidad: 'Cortes modernos', activo: true },
  { idBarbero: 3, nombre: 'Bruno Castillo', especialidad: 'Afeitado tradicional', activo: true },
]

export const servicios = [
  { idServicio: 1, nombre: 'Corte', precio: 260, duracionMinutos: 45 },
  { idServicio: 2, nombre: 'Barba', precio: 190, duracionMinutos: 30 },
  { idServicio: 3, nombre: 'Corte + Barba', precio: 420, duracionMinutos: 75 },
  { idServicio: 4, nombre: 'Afeitado clásico', precio: 240, duracionMinutos: 40 },
]

export const citas = [
  { idCita: 1, idCliente: 1, idBarbero: 1, idServicio: 3, inicio: `${HOY}T09:00:00`, fin: `${HOY}T10:15:00`, estado: 'PROGRAMADA' },
  { idCita: 2, idCliente: 2, idBarbero: 2, idServicio: 1, inicio: `${HOY}T10:30:00`, fin: `${HOY}T11:15:00`, estado: 'PROGRAMADA' },
  { idCita: 3, idCliente: 3, idBarbero: 1, idServicio: 2, inicio: `${HOY}T12:00:00`, fin: `${HOY}T12:30:00`, estado: 'PROGRAMADA' },
  { idCita: 4, idCliente: 4, idBarbero: 3, idServicio: 3, inicio: `${HOY}T15:30:00`, fin: `${HOY}T16:45:00`, estado: 'PROGRAMADA' },
  { idCita: 5, idCliente: 5, idBarbero: 2, idServicio: 4, inicio: `${MANANA}T11:00:00`, fin: `${MANANA}T11:40:00`, estado: 'PROGRAMADA' },
  { idCita: 6, idCliente: 1, idBarbero: 1, idServicio: 1, inicio: `${MANANA}T16:00:00`, fin: `${MANANA}T16:45:00`, estado: 'PROGRAMADA' },
  { idCita: 7, idCliente: 2, idBarbero: 3, idServicio: 2, inicio: `${AYER}T13:00:00`, fin: `${AYER}T13:30:00`, estado: 'COMPLETADA' },
]
