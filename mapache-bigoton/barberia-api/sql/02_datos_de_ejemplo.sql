-- =====================================================================
--  02_datos_de_ejemplo.sql  ·  Datos para ver algo en las pantallas
--  OPCIONAL. Ejecútalo UNA sola vez, después del 01.
--  Las citas se crean con la fecha de HOY y de MAÑANA, para que el
--  Panel principal y la pantalla de Citas muestren algo al abrirlos.
--  Si ya lo ejecutaste, no lo repitas (duplicaría las citas).
-- =====================================================================

USE BarberiaDB;

-- Barberos (no se repiten si ya existen con ese nombre)
INSERT INTO barbero (nombre, especialidad, activo)
SELECT 'Diego Morales', 'Clásicos y barba', b'1' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM barbero WHERE nombre = 'Diego Morales');
INSERT INTO barbero (nombre, especialidad, activo)
SELECT 'Mateo Vargas', 'Cortes modernos', b'1' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM barbero WHERE nombre = 'Mateo Vargas');
INSERT INTO barbero (nombre, especialidad, activo)
SELECT 'Bruno Castillo', 'Afeitado tradicional', b'1' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM barbero WHERE nombre = 'Bruno Castillo');

-- Servicios
INSERT INTO servicio (nombre, precio, duracion_minutos)
SELECT 'Corte', 260, 45 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM servicio WHERE nombre = 'Corte');
INSERT INTO servicio (nombre, precio, duracion_minutos)
SELECT 'Barba', 190, 30 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM servicio WHERE nombre = 'Barba');
INSERT INTO servicio (nombre, precio, duracion_minutos)
SELECT 'Corte + Barba', 420, 75 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM servicio WHERE nombre = 'Corte + Barba');
INSERT INTO servicio (nombre, precio, duracion_minutos)
SELECT 'Afeitado clásico', 240, 40 FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM servicio WHERE nombre = 'Afeitado clásico');

-- Clientes (el teléfono es único: si ya existe, se omite)
INSERT IGNORE INTO cliente (nombre, telefono, email) VALUES
    ('Alejandro Ruiz', '55 1824 6912', NULL),
    ('Ricardo Salas', '55 9173 2048', NULL),
    ('Santiago León', '55 6302 1187', NULL),
    ('Emilio Navarro', '55 4408 7625', NULL),
    ('Julián Herrera', '55 8031 4490', NULL);

-- Citas de HOY
INSERT INTO cita (id_Cliente, id_Barbero, id_Servicio, inicio, fin, estado)
VALUES ((SELECT id_Cliente FROM cliente WHERE telefono = '55 1824 6912' LIMIT 1),
        (SELECT id_Barbero FROM barbero WHERE nombre = 'Diego Morales' LIMIT 1),
        (SELECT id_Servicio FROM servicio WHERE nombre = 'Corte + Barba' LIMIT 1),
        TIMESTAMP(CURDATE(), '09:00:00'), TIMESTAMP(CURDATE(), '10:15:00'), 'PROGRAMADA'),
       ((SELECT id_Cliente FROM cliente WHERE telefono = '55 9173 2048' LIMIT 1),
        (SELECT id_Barbero FROM barbero WHERE nombre = 'Mateo Vargas' LIMIT 1),
        (SELECT id_Servicio FROM servicio WHERE nombre = 'Corte' LIMIT 1),
        TIMESTAMP(CURDATE(), '10:30:00'), TIMESTAMP(CURDATE(), '11:15:00'), 'PROGRAMADA'),
       ((SELECT id_Cliente FROM cliente WHERE telefono = '55 6302 1187' LIMIT 1),
        (SELECT id_Barbero FROM barbero WHERE nombre = 'Diego Morales' LIMIT 1),
        (SELECT id_Servicio FROM servicio WHERE nombre = 'Barba' LIMIT 1),
        TIMESTAMP(CURDATE(), '12:00:00'), TIMESTAMP(CURDATE(), '12:30:00'), 'PROGRAMADA'),
       ((SELECT id_Cliente FROM cliente WHERE telefono = '55 4408 7625' LIMIT 1),
        (SELECT id_Barbero FROM barbero WHERE nombre = 'Bruno Castillo' LIMIT 1),
        (SELECT id_Servicio FROM servicio WHERE nombre = 'Corte + Barba' LIMIT 1),
        TIMESTAMP(CURDATE(), '15:30:00'), TIMESTAMP(CURDATE(), '16:45:00'), 'PROGRAMADA');

-- Citas de MAÑANA
INSERT INTO cita (id_Cliente, id_Barbero, id_Servicio, inicio, fin, estado)
VALUES ((SELECT id_Cliente FROM cliente WHERE telefono = '55 8031 4490' LIMIT 1),
        (SELECT id_Barbero FROM barbero WHERE nombre = 'Mateo Vargas' LIMIT 1),
        (SELECT id_Servicio FROM servicio WHERE nombre = 'Afeitado clásico' LIMIT 1),
        TIMESTAMP(DATE_ADD(CURDATE(), INTERVAL 1 DAY), '11:00:00'),
        TIMESTAMP(DATE_ADD(CURDATE(), INTERVAL 1 DAY), '11:40:00'), 'PROGRAMADA'),
       ((SELECT id_Cliente FROM cliente WHERE telefono = '55 1824 6912' LIMIT 1),
        (SELECT id_Barbero FROM barbero WHERE nombre = 'Diego Morales' LIMIT 1),
        (SELECT id_Servicio FROM servicio WHERE nombre = 'Corte' LIMIT 1),
        TIMESTAMP(DATE_ADD(CURDATE(), INTERVAL 1 DAY), '16:00:00'),
        TIMESTAMP(DATE_ADD(CURDATE(), INTERVAL 1 DAY), '16:45:00'), 'PROGRAMADA');

-- Comprobación
SELECT 'clientes' AS tabla, COUNT(*) AS filas FROM cliente
UNION ALL SELECT 'barberos', COUNT(*) FROM barbero
UNION ALL SELECT 'servicios', COUNT(*) FROM servicio
UNION ALL SELECT 'citas', COUNT(*) FROM cita;
