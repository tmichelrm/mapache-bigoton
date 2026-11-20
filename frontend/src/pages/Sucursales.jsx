import { useState } from 'react'
import { Store, MapPin, Pencil, Trash2, Plus } from 'lucide-react'
import Modal from '../components/Modal'
import { sucursalesApi, barberosApi } from '../services/api'
import { useRecurso } from '../services/useRecurso'

const vacio = { nombre: '', direccion: '' }

export default function Sucursales() {
  const { datos: sucursales, cargando, error, recargar } = useRecurso(sucursalesApi)
  const { datos: barberos } = useRecurso(barberosApi)
  const [formulario, setFormulario] = useState(null)

  const barberosDe = (id) => barberos.filter((b) => b.idSucursal === id).length

  function cambiar(campo, valor) {
    setFormulario({ ...formulario, [campo]: valor })
  }

  async function guardar(evento) {
    evento.preventDefault()
    try {
      if (formulario.idSucursal) {
        await sucursalesApi.actualizar(formulario.idSucursal, formulario)
      } else {
        await sucursalesApi.crear(formulario)
      }
      setFormulario(null)
      recargar()
    } catch {
      alert('No se pudo guardar la sucursal.')
    }
  }

  async function eliminar(sucursal) {
    if (!window.confirm(`¿Eliminar "${sucursal.nombre}"?`)) return
    try {
      await sucursalesApi.eliminar(sucursal.idSucursal)
      recargar()
    } catch {
      alert('No se pudo eliminar. Tiene barberos asignados: cámbialos de sucursal primero.')
    }
  }

  return (
    <>
      <div className="encabezado">
        <div>
          <h1>Sucursales</h1>
          <p className="subtitulo">Ubicaciones de El Mapache Bigotón</p>
        </div>
        <button className="boton-verde" onClick={() => setFormulario({ ...vacio })}>
          <Plus size={16} /> Agregar sucursal
        </button>
      </div>

      {cargando && <p className="mensaje">Cargando…</p>}
      {error && <p className="mensaje error">{error}</p>}

      <div className="rejilla-servicios">
        {sucursales.map((s) => (
          <div className="tarjeta" key={s.idSucursal}>
            <div className="servicio-arriba">
              <span className="servicio-icono"><Store size={18} /></span>
              <div className="acciones">
                <button className="icono-boton editar" onClick={() => setFormulario({ ...s })} title="Editar">
                  <Pencil size={14} />
                </button>
                <button className="icono-boton borrar" onClick={() => eliminar(s)} title="Eliminar">
                  <Trash2 size={14} />
                </button>
              </div>
            </div>
            <h2>{s.nombre}</h2>
            <p className="direccion"><MapPin size={14} /> {s.direccion}</p>
            <div className="servicio-pie">
              <span className="duracion">{barberosDe(s.idSucursal)} barberos</span>
            </div>
          </div>
        ))}
      </div>

      {formulario && (
        <Modal titulo={formulario.idSucursal ? 'Editar sucursal' : 'Agregar sucursal'} onCerrar={() => setFormulario(null)}>
          <form onSubmit={guardar}>
            <label htmlFor="nombre">Nombre</label>
            <input id="nombre" required maxLength={50} value={formulario.nombre}
              onChange={(e) => cambiar('nombre', e.target.value)} />

            <label htmlFor="direccion">Dirección</label>
            <input id="direccion" required maxLength={150} value={formulario.direccion}
              onChange={(e) => cambiar('direccion', e.target.value)} />

            <div className="modal-botones">
              <button type="button" className="boton-claro" onClick={() => setFormulario(null)}>Cancelar</button>
              <button type="submit" className="boton-verde">Guardar</button>
            </div>
          </form>
        </Modal>
      )}
    </>
  )
}
