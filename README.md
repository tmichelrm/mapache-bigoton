# El Mapache Bigotón — Sistema de citas para barbería

Proyecto de *Fundamentos de Cómputo en la Nube*.

```
MySQL (BarberiaDB)  <--  Spring Boot API (:8080)  <--  React + Vite (:5173)
```

El navegador nunca habla con la base de datos: solo con la API.

## Estructura

| Carpeta | Contenido |
|---|---|
| `barberia-api/` | Backend Spring Boot (Maven) + scripts SQL en `sql/` |
| `frontend/` | Frontend React (Vite) |
| `docs/` | Enunciado, maquetas y casos de prueba |

## Cómo levantarlo

### 1. Base de datos
En MySQL Workbench ejecuta, en orden, `barberia-api/sql/01_base_de_datos.sql` y `02_datos_de_ejemplo.sql`.

### 2. Backend
1. En `barberia-api/` copia `application-local.properties.example` como `application-local.properties` y pon tu usuario y contraseña de MySQL (ese archivo **no se sube a Git**).
2. Desde `barberia-api/`: `.\mvnw.cmd spring-boot:run` (Windows) o `./mvnw spring-boot:run`.
3. Debe quedar escuchando en http://localhost:8080.

### 3. Frontend
```
cd frontend
npm install
npm run dev
```
Abre http://localhost:5173. Para usar el backend real, en `frontend/src/services/api.js` cambia `USAR_API` a `true`.

### Usuario inicial
`mariana` / `Mapache2026` (se crea automáticamente si la tabla `usuario` está vacía).

## API
Base `http://localhost:8080/api`: `clientes`, `barberos`, `servicios`, `citas` (GET, GET /{id}, POST, PUT /{id}, DELETE /{id}), `POST /login`, `POST /logout`. Todas excepto login requieren `Authorization: Bearer <token>`.

## Equipo
Backend · Frontend · UX · Téster — ver `docs/`.
