-- =====================================================================
--  01_base_de_datos.sql  ·  Estructura de BarberiaDB
--  Ábrelo en MySQL Workbench y ejecútalo completo (rayo ⚡ o Ctrl+Shift+Enter).
--
--  Es SEGURO ejecutarlo aunque ya hayas creado las tablas antes:
--  usa IF NOT EXISTS, así que no borra ni duplica nada.
--  Lo único nuevo respecto a tu script es la tabla `usuario` (para el login).
-- =====================================================================

CREATE DATABASE IF NOT EXISTS BarberiaDB CHARACTER SET utf8mb4;

USE BarberiaDB;

CREATE TABLE IF NOT EXISTS cliente
(
    id_Cliente BIGINT       NOT NULL AUTO_INCREMENT,
    nombre     VARCHAR(50)  NOT NULL,
    telefono   VARCHAR(15)  NOT NULL,
    email      VARCHAR(100) NULL,
    PRIMARY KEY (id_Cliente),
    UNIQUE KEY uq_cliente_telefono (telefono)
);

CREATE TABLE IF NOT EXISTS barbero
(
    id_Barbero   BIGINT      NOT NULL AUTO_INCREMENT,
    nombre       VARCHAR(50) NOT NULL,
    especialidad VARCHAR(30) NULL,
    activo       BIT(1)      NOT NULL DEFAULT b'1',
    PRIMARY KEY (id_Barbero)
);

CREATE TABLE IF NOT EXISTS servicio
(
    id_Servicio      BIGINT         NOT NULL AUTO_INCREMENT,
    nombre           VARCHAR(50)    NOT NULL,
    precio           DECIMAL(10, 2) NOT NULL,
    duracion_minutos INT            NOT NULL,
    PRIMARY KEY (id_Servicio),
    CONSTRAINT ck_servicio_precio CHECK (precio >= 0),
    CONSTRAINT ck_servicio_duracion CHECK (duracion_minutos >= 5)
);

CREATE TABLE IF NOT EXISTS cita
(
    id_Cita     BIGINT      NOT NULL AUTO_INCREMENT,
    id_Cliente  BIGINT      NOT NULL,
    id_Barbero  BIGINT      NOT NULL,
    id_Servicio BIGINT      NOT NULL,
    inicio      DATETIME(6) NOT NULL,
    fin         DATETIME(6) NOT NULL,
    estado      ENUM('PROGRAMADA','COMPLETADA','CANCELADA') NOT NULL DEFAULT 'PROGRAMADA',
    PRIMARY KEY (id_Cita),
    CONSTRAINT fk_cita_cliente FOREIGN KEY (id_Cliente) REFERENCES cliente (id_Cliente),
    CONSTRAINT fk_cita_barbero FOREIGN KEY (id_Barbero) REFERENCES barbero (id_Barbero),
    CONSTRAINT fk_cita_servicio FOREIGN KEY (id_Servicio) REFERENCES servicio (id_Servicio),
    CONSTRAINT ck_cita_horario CHECK (fin > inicio),
    INDEX idx_cita_barbero_horario (id_Barbero, inicio, fin),
    INDEX idx_cita_cliente (id_Cliente)
);

-- NUEVA: quien inicia sesión en el sistema.
-- No se inserta ningún usuario aquí: el backend crea el primero al arrancar
-- (con la contraseña ya encriptada).
CREATE TABLE IF NOT EXISTS usuario
(
    id_Usuario BIGINT       NOT NULL AUTO_INCREMENT,
    nombre     VARCHAR(50)  NOT NULL,
    usuario    VARCHAR(30)  NOT NULL,
    clave_hash VARCHAR(100) NOT NULL,
    rol        VARCHAR(30)  NOT NULL DEFAULT 'Administradora',
    PRIMARY KEY (id_Usuario),
    UNIQUE KEY uq_usuario_usuario (usuario)
);

-- Comprobación: debe listar 5 tablas
SHOW TABLES;
