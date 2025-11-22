CREATE DATABASE IF NOT EXISTS seguro_coche CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE seguro_coche;

CREATE TABLE IF NOT EXISTS cotizacion_seguro (
                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 nombre VARCHAR(150) NOT NULL,
    edad INT NOT NULL,
    anios_carnet INT NOT NULL,
    marca VARCHAR(100) NOT NULL,
    modelo VARCHAR(100) NOT NULL,
    anio_mat INT NOT NULL,
    uso ENUM('PRIVADO','PROFESIONAL') NOT NULL DEFAULT 'PRIVADO',
    tipo_cobertura ENUM('TERCEROS','TERCEROS_LUNAS','TODO_RIESGO') NOT NULL,
    asistencia BOOLEAN NOT NULL DEFAULT FALSE,
    veh_sustitucion BOOLEAN NOT NULL DEFAULT FALSE,
    precio_total DECIMAL(12,2) NOT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );
