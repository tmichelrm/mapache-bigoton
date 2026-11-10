# Casos de prueba manuales (rol Téster)

Antes: MySQL con los dos scripts de `barberia-api/sql`, backend en :8080, frontend en :5173 con `USAR_API = true`.

| # | Prueba | Pasos | Resultado esperado |
|---|--------|-------|--------------------|
| 1 | Login correcto | usuario `mariana`, clave `Mapache2026` | Entra al Panel |
| 2 | Login incorrecto | clave equivocada | Mensaje rojo "Usuario o contraseña incorrectos." |
| 3 | Ruta protegida | Sin sesión abrir /citas | Redirige a /login |
| 4 | Cerrar sesión | Botón salir | Vuelve a /login; /panel ya no abre |
| 5 | Crear cliente | Clientes > Agregar | Aparece en la tabla y en MySQL (`SELECT * FROM cliente`) |
| 6 | Teléfono repetido | Crear otro cliente con el mismo teléfono | Mensaje de error, no se duplica |
| 7 | Editar / eliminar cliente sin citas | Editar y borrar | Se refleja en la tabla y en MySQL |
| 8 | Eliminar cliente con citas | Borrar uno con citas | Mensaje de error (no se borra) |
| 9 | CRUD de servicios | Crear, editar precio, eliminar | Cambios persisten al recargar |
| 10 | CRUD de barberos | Crear, marcar inactivo | Inactivo no sale al crear cita |
| 11 | Nueva cita | Elegir fecha, hora, barbero, cliente, servicio | Costo autocompleta; guarda y aparece en Citas |
| 12 | Empalme | Misma hora y barbero que otra cita | Aviso rojo y botón Guardar bloqueado |
| 13 | Editar cita | Cambiar la hora | Se actualiza; no choca consigo misma |
| 14 | Marcar atendida | ✓ en una cita PROGRAMADA | Pasa a COMPLETADA |
| 15 | Eliminar cita | Papelera | Desaparece |
| 16 | Panel | Revisar contadores | Coinciden con las citas de hoy |
| 17 | Backend apagado | Detener Spring y recargar | Mensaje de error en pantalla, sin pantalla en blanco |

Anota en cada fila: ✅ pasó / ❌ falló + captura.
