
USE BarberiaDB;

SET SQL_SAFE_UPDATES = 0;

-- 1) Tabla de sucursales
CREATE TABLE IF NOT EXISTS sucursal
(
    id_Sucursal BIGINT       NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(50)  NOT NULL,
    direccion   VARCHAR(150) NOT NULL,
    PRIMARY KEY (id_Sucursal)
);

-- 2) Columna id_Sucursal en barbero (solo si todavía no existe)
SET @existe = (SELECT COUNT(*) FROM information_schema.COLUMNS
               WHERE TABLE_SCHEMA = 'BarberiaDB' AND TABLE_NAME = 'barbero' AND COLUMN_NAME = 'id_Sucursal');
SET @sql = IF(@existe = 0,
              'ALTER TABLE barbero ADD COLUMN id_Sucursal BIGINT NULL',
              'SELECT ''barbero.id_Sucursal ya existe'' AS aviso');
PREPARE paso FROM @sql; EXECUTE paso; DEALLOCATE PREPARE paso;

-- 3) Llave foránea barbero -> sucursal (solo si todavía no existe)
SET @existe = (SELECT COUNT(*) FROM information_schema.TABLE_CONSTRAINTS
               WHERE TABLE_SCHEMA = 'BarberiaDB' AND TABLE_NAME = 'barbero'
                 AND CONSTRAINT_NAME = 'fk_barbero_sucursal');
SET @sql = IF(@existe = 0,
              'ALTER TABLE barbero ADD CONSTRAINT fk_barbero_sucursal FOREIGN KEY (id_Sucursal) REFERENCES sucursal (id_Sucursal)',
              'SELECT ''fk_barbero_sucursal ya existe'' AS aviso');
PREPARE paso FROM @sql; EXECUTE paso; DEALLOCATE PREPARE paso;

-- 4) Columna descripcion en servicio (solo si todavía no existe)
SET @existe = (SELECT COUNT(*) FROM information_schema.COLUMNS
               WHERE TABLE_SCHEMA = 'BarberiaDB' AND TABLE_NAME = 'servicio' AND COLUMN_NAME = 'descripcion');
SET @sql = IF(@existe = 0,
              'ALTER TABLE servicio ADD COLUMN descripcion VARCHAR(150) NULL',
              'SELECT ''servicio.descripcion ya existe'' AS aviso');
PREPARE paso FROM @sql; EXECUTE paso; DEALLOCATE PREPARE paso;

-- 5) Sucursales de ejemplo (no se repiten si ya existen con ese nombre)
--    Cámbialas por las direcciones que quieras.
INSERT INTO sucursal (nombre, direccion)
SELECT 'Sucursal Centro', 'Av. Juárez 120, Col. Centro, Ciudad de México' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sucursal WHERE nombre = 'Sucursal Centro');
INSERT INTO sucursal (nombre, direccion)
SELECT 'Sucursal Norte', 'Calz. Vallejo 845, Col. Industrial Vallejo, Ciudad de México' FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM sucursal WHERE nombre = 'Sucursal Norte');

-- 6) Barberos sin sucursal: se reparten entre las dos de ejemplo
UPDATE barbero
SET id_Sucursal = (SELECT id_Sucursal FROM sucursal WHERE nombre = 'Sucursal Centro' LIMIT 1)
WHERE id_Sucursal IS NULL AND nombre IN ('Diego Morales', 'Mateo Vargas');
UPDATE barbero
SET id_Sucursal = (SELECT id_Sucursal FROM sucursal WHERE nombre = 'Sucursal Norte' LIMIT 1)
WHERE id_Sucursal IS NULL AND nombre = 'Bruno Castillo';
-- Cualquier otro barbero que hayas creado antes: a la primera sucursal
UPDATE barbero
SET id_Sucursal = (SELECT MIN(id_Sucursal) FROM sucursal)
WHERE id_Sucursal IS NULL;

-- 7) Descripciones de los servicios de ejemplo (solo si están vacías)
UPDATE servicio SET descripcion = 'Corte de cabello con tijera o máquina, lavado y peinado.'
WHERE descripcion IS NULL AND nombre = 'Corte';
UPDATE servicio SET descripcion = 'Perfilado y arreglo de barba con toalla caliente.'
WHERE descripcion IS NULL AND nombre = 'Barba';
UPDATE servicio SET descripcion = 'Corte de cabello más arreglo de barba.'
WHERE descripcion IS NULL AND nombre = 'Corte + Barba';
UPDATE servicio SET descripcion = 'Afeitado tradicional con navaja y toalla caliente.'
WHERE descripcion IS NULL AND nombre = 'Afeitado clásico';

SET SQL_SAFE_UPDATES = 1;

-- Comprobación
SELECT b.nombre AS barbero, s.nombre AS sucursal, s.direccion
FROM barbero b LEFT JOIN sucursal s ON s.id_Sucursal = b.id_Sucursal;
SELECT nombre, descripcion FROM servicio;
