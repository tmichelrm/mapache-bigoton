# barberia-api · Backend de El Mapache Bigotón

API REST con **Spring Boot 4.0.3 + Maven + MySQL**, armada con la misma estructura que la plantilla del maestro
(`atributos-api`): groupId `mx.edu.caidt`, paquete `mx.edu.caidt.barberia_api`, Java 17, Lombok y wrapper de Maven.
Corre en `http://localhost:8080`; el frontend (React) en `http://localhost:5173`.

---

## 1. Estructura del repositorio

Todo el equipo trabaja en un mismo repositorio (`API-Barber`), cada rol en su carpeta:

```
API-Barber/
├── README.md                  ← qué es el proyecto y cómo levantarlo (lo ve cualquiera al abrir el repo)
├── .gitignore
├── barberia-api/              ← BACKEND (Maven)                     · rol: Backend
│   ├── pom.xml
│   ├── mvnw / mvnw.cmd        ← wrapper de Maven (no hay que instalar Maven)
│   ├── sql/
│   │   ├── 01_base_de_datos.sql
│   │   └── 02_datos_de_ejemplo.sql
│   └── src/main/java/mx/edu/caidt/barberia_api/
│       ├── BarberiaApiApplication.java
│       ├── model/             ← entidades = tablas (Cliente, Barbero, Servicio, Cita, Usuario)
│       ├── repository/        ← consultas a la base de datos
│       ├── controller/        ← rutas REST (/api/...)
│       └── config/            ← CORS, login por token, manejo de errores
├── frontend/                  ← FRONTEND (React + Vite)             · rol: Frontend
│   └── src/ (pages, components, services, data)
└── docs/
    ├── enunciado.pdf          ← el problema planteado por el maestro
    ├── maquetas/              ← prototipo de pantallas (PDF)        · rol: UX
    └── pruebas.md             ← casos de prueba y resultados        · rol: Téster
```

**Cómo se conectan:** el navegador abre el frontend, el frontend pide datos a `barberia-api` por HTTP, y solo `barberia-api` habla con MySQL.

### Ramas de Git (sugerencia)

| Rama | Quién | Qué contiene |
|---|---|---|
| `main` | todos | Lo que ya funciona y está revisado |
| `backend` | Backend | Cambios de `barberia-api/` |
| `frontend` | Frontend | Cambios de `frontend/` |

Cada quien trabaja en su rama y, cuando algo funciona, abre un *Pull Request* hacia `main`.
Como cada rol toca carpetas distintas, casi no hay conflictos.

---

## 2. Qué incluye la API

| Ruta | Para qué |
|---|---|
| `POST /api/login` | Inicia sesión: recibe `{usuario, clave}`, devuelve `{token, nombre, rol}` |
| `POST /api/logout` | Cierra la sesión |
| `/api/clientes` · `/api/barberos` · `/api/servicios` · `/api/citas` | Listar (`GET`), ver (`GET /{id}`), crear (`POST`), editar (`PUT /{id}`) y eliminar (`DELETE /{id}`) |

Todas las rutas, menos el login, exigen la cabecera `Authorization: Bearer <token>`.

Reglas que valida el backend:
- Teléfono de cliente repetido → `409`.
- Eliminar algo que tiene citas asociadas → `409`.
- Cita que se empalma con otra del mismo barbero (sin contar las canceladas) → `409`.
- El `fin` de la cita siempre se calcula: `inicio + duración del servicio`.
- Cliente, barbero o servicio inexistente al crear una cita → `400`.
- Id que no existe al editar o eliminar → `404`.

---

## 3. Puesta en marcha

### Paso 1 · Crear la base de datos (MySQL Workbench)
1. Abre **MySQL Workbench** y conéctate a tu servidor.
2. **File → Open SQL Script…** → `barberia-api/sql/01_base_de_datos.sql`.
3. Ejecútalo completo con el rayo ⚡. Debe mostrar **5 tablas**: `barbero`, `cita`, `cliente`, `servicio`, `usuario`.
   Es seguro aunque ya hayas creado las tablas antes: no borra ni duplica nada.
4. *(Opcional)* Ejecuta `sql/02_datos_de_ejemplo.sql` **una sola vez** para tener datos de prueba.

### Paso 2 · Tu contraseña de MySQL
1. Copia `application-local.properties.example` y renombra la copia a **`application-local.properties`**
   (en la misma carpeta que `pom.xml`).
2. Cambia `CAMBIA_ESTA_CONTRASEÑA` por la de tu MySQL. Ese archivo no se sube a GitHub.

> Alternativa: en IntelliJ, *Run → Edit Configurations → Environment variables*: `DB_PASSWORD=tu_contraseña`.

### Paso 3 · Arrancar
- **IntelliJ:** *File → Open* → carpeta `barberia-api` (la que tiene `pom.xml`) → espera a que Maven termine de descargar →
  abre `BarberiaApiApplication.java` y pulsa el triángulo verde.
- **Terminal** (dentro de `barberia-api`): `.\mvnw.cmd spring-boot:run`

Debe aparecer `Started BarberiaApiApplication` y, la primera vez, `Usuario inicial creado: 'mariana'`.

### Paso 4 · Probar (PowerShell)
```powershell
$r = Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/login `
  -ContentType "application/json" `
  -Body '{"usuario":"mariana","clave":"Mapache2026"}'
$r

$h = @{ Authorization = "Bearer $($r.token)" }
Invoke-RestMethod -Uri http://localhost:8080/api/barberos -Headers $h
Invoke-RestMethod -Uri http://localhost:8080/api/citas -Headers $h
```
Si abres `http://localhost:8080/api/barberos` directo en el navegador debe salir **401**: las rutas están protegidas.

### Paso 5 · Conectar el frontend
En `frontend/src/services/api.js` cambia `const USAR_API = false` por `const USAR_API = true` y entra a `http://localhost:5173`
con **`mariana`** / **`Mapache2026`**.

> Cambia esa contraseña antes de entregar: agrega `app.admin.clave=TuNuevaClave` en `application-local.properties`
> **antes del primer arranque**, o borra el usuario de la tabla `usuario` y reinicia.

---

## 4. Pasar el repositorio a esta estructura

Hoy el repositorio tiene `BarberiaApi/` (Gradle) en la raíz. Para cambiarlo a esta estructura, **acuérdenlo primero con el equipo**
y háganlo una sola persona, en una rama:

```powershell
git checkout main
git pull
git checkout -b estructura

# 1) Quitar el backend anterior (vacío) y poner el nuevo
git rm -r BarberiaApi
# copia aquí la carpeta barberia-api del zip

# 2) Documentación
mkdir docs
# copia a docs/: enunciado.pdf y el prototipo (docs/maquetas/)

git add .
git commit -m "Reestructurar proyecto: backend Maven (barberia-api), frontend y docs"
git push -u origin estructura
```
Después abren un Pull Request hacia `main`. Quien trabaje el frontend, al terminar, hace `git merge main` en su rama.

---

## 5. Si algo falla

| Mensaje | Causa y solución |
|---|---|
| `Access denied for user 'root'` | Usuario o contraseña incorrectos (Paso 2). |
| `Unknown database 'barberiadb'` | No se ejecutó `01_base_de_datos.sql`. |
| `Communications link failure` | MySQL no está encendido. |
| `Port 8080 was already in use` | Otro programa usa el puerto. Ciérralo o cambia `server.port` y el `BASE_URL` del frontend. |
| `Table 'barberiadb.usuario' doesn't exist` | Falta ejecutar el script 01. |
| Login responde 401 | Usuario o clave incorrectos. La clave inicial es `Mapache2026`. |
| El navegador dice "CORS" | El frontend debe correr en el puerto `5173` (otro puerto: agrégalo en `app.cors.origenes`). |
| `release version 17 not supported` | Tu JDK es anterior a 17. |
| Error raro al arrancar | Lee el **primer** mensaje en rojo de la consola; la causa suele estar junto a `Caused by:`. |

## 6. Seguridad (para un proyecto escolar)

Contraseñas con BCrypt, rutas protegidas y token con vencimiento de 8 horas. Los tokens viven en memoria:
**al reiniciar el backend hay que volver a iniciar sesión**. Para uso real faltaría JWT firmado, HTTPS y Spring Security completo.
