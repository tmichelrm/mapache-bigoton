const pad = (n) => String(n).padStart(2, '0')
export const horaDe = (fechaHora) => fechaHora.slice(11, 16)

export const diaDe = (fechaHora) => fechaHora.slice(0, 10)

export const estados = {
  PROGRAMADA: { texto: 'Programada', clase: 'programada' },
  COMPLETADA: { texto: 'Completada', clase: 'completada' },
  CANCELADA: { texto: 'Cancelada', clase: 'cancelada' },
}

export function sumarMinutos(fechaHora, minutos) {
  const [fecha, hora] = fechaHora.split('T')
  const [anio, mes, dia] = fecha.split('-').map(Number)
  const [horas, mins] = hora.split(':').map(Number)
  const f = new Date(anio, mes - 1, dia, horas, mins + minutos)
  return `${f.getFullYear()}-${pad(f.getMonth() + 1)}-${pad(f.getDate())}T${pad(f.getHours())}:${pad(f.getMinutes())}:00`
}

export function fechaLarga(fecha) {
  const texto = new Date(`${fecha}T00:00:00`).toLocaleDateString('es-MX', {
    weekday: 'long', day: 'numeric', month: 'long', year: 'numeric',
  })
  return texto.charAt(0).toUpperCase() + texto.slice(1)
}

export function fechaCorta(fecha) {
  return new Date(`${fecha}T00:00:00`).toLocaleDateString('es-MX', {
    weekday: 'short', day: 'numeric', month: 'short',
  })
}
