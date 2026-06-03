-- =====================================================================
-- SwissRoute - Script de creación de la base de datos (PostgreSQL)
-- =====================================================================
-- Crea el esquema completo de la aplicación: usuarios, rutas y estaciones
-- favoritas, historial de búsquedas y estaciones guardadas.
--
-- Uso:
--   createdb swissroute_db
--   psql -d swissroute_db -f schema.sql
-- =====================================================================

-- ---------------------------------------------------------------------
-- Tabla: users
-- ---------------------------------------------------------------------
CREATE TABLE users (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(20)  DEFAULT 'USER',
    city_base   VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT chk_users_role CHECK (role IN ('USER', 'ADMIN'))
);

-- ---------------------------------------------------------------------
-- Tabla: favorite_routes
-- ---------------------------------------------------------------------
CREATE TABLE favorite_routes (
    id              BIGSERIAL    PRIMARY KEY,
    user_id         BIGINT       NOT NULL,
    name            VARCHAR(255) NOT NULL,
    origin          VARCHAR(255) NOT NULL,
    destination     VARCHAR(255) NOT NULL,
    transport_type  VARCHAR(20),
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_favorite_routes_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT uq_favorite_routes_user_name UNIQUE (user_id, name),
    CONSTRAINT chk_favorite_routes_transport
        CHECK (transport_type IN ('TRAIN', 'TRAM', 'BUS', 'BOAT', 'CABLE_CAR'))
);

-- ---------------------------------------------------------------------
-- Tabla: favorite_station
-- ---------------------------------------------------------------------
CREATE TABLE favorite_station (
    id                   BIGSERIAL    PRIMARY KEY,
    user_id              BIGINT       NOT NULL,
    external_station_id  VARCHAR(255) NOT NULL,
    station_name         VARCHAR(255) NOT NULL,
    created_at           TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_favorite_station_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT uq_favorite_station_user_station UNIQUE (user_id, external_station_id)
);

-- ---------------------------------------------------------------------
-- Tabla: search_history
-- ---------------------------------------------------------------------
CREATE TABLE search_history (
    id           BIGSERIAL    PRIMARY KEY,
    user_id      BIGINT       NOT NULL,
    origin       VARCHAR(255) NOT NULL,
    destination  VARCHAR(255) NOT NULL,
    num_results  INTEGER,
    query_date   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_search_history_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- ---------------------------------------------------------------------
-- Tabla: stations
-- ---------------------------------------------------------------------
CREATE TABLE stations (
    id                   BIGSERIAL    PRIMARY KEY,
    user_id              BIGINT,
    external_station_id  VARCHAR(255),
    station_name         VARCHAR(255),
    created_at           TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_stations_user
        FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

-- ---------------------------------------------------------------------
-- Índices sobre las claves foráneas (consultas findByUserId)
-- ---------------------------------------------------------------------
CREATE INDEX idx_favorite_routes_user  ON favorite_routes (user_id);
CREATE INDEX idx_favorite_station_user ON favorite_station (user_id);
CREATE INDEX idx_search_history_user   ON search_history (user_id);
CREATE INDEX idx_stations_user         ON stations (user_id);

-- =====================================================================
-- Datos de ejemplo
-- =====================================================================
-- Las contraseñas se guardan con BCrypt. El hash de abajo corresponde a
-- la contraseña en texto plano: 123123
-- ---------------------------------------------------------------------
INSERT INTO users (name, email, password, role, city_base) VALUES
    ('Bytes Colaborativos', 'bytescolaborativos@swissroute.com', '$2a$10$2Ydvv1U4IFbsSg7PVP/AXOgbEGQClsLo88QspGPWKNwycgGuYxhMu', 'ADMIN', 'Zürich'), -- password: 123123
    ('Chanti', 'chanti@swissroute.com', '$2a$10$2Ydvv1U4IFbsSg7PVP/AXOgbEGQClsLo88QspGPWKNwycgGuYxhMu', 'USER', 'Bern'),      -- password: 123123
    ('Jorex', 'jorex@swissroute.com', '$2a$10$2Ydvv1U4IFbsSg7PVP/AXOgbEGQClsLo88QspGPWKNwycgGuYxhMu', 'USER', 'Genève');     -- password: 123123

-- Rutas favoritas (1 = bytescolaborativos, 2 = chanti, 3 = jorex)
INSERT INTO favorite_routes (user_id, name, origin, destination, transport_type) VALUES
    (1, 'Oficina', 'Zürich HB', 'Luzern', 'TRAIN'),
    (1, 'Aeropuerto', 'Zürich HB', 'Zürich Flughafen', 'TRAIN'),
    (2, 'Casa - Trabajo', 'Bern', 'Zürich HB', 'TRAIN'),
    (2, 'Fin de semana', 'Bern', 'Interlaken Ost', 'TRAIN'),
    (3, 'Trabajo', 'Genève', 'Lausanne', 'TRAIN'),
    (3, 'Paseo', 'Genève', 'Montreux', 'BOAT');

INSERT INTO favorite_station (user_id, external_station_id, station_name) VALUES
    (1, '8503000', 'Zürich HB'),
    (1, '8503016', 'Zürich Flughafen'),
    (2, '8507000', 'Bern'),
    (2, '8503000', 'Zürich HB'),
    (3, '8501008', 'Genève'),
    (3, '8501120', 'Lausanne');

INSERT INTO search_history (user_id, origin, destination, num_results) VALUES
    (1, 'Zürich HB', 'Luzern', 5),
    (1, 'Zürich HB', 'Bern', 6),
    (2, 'Bern', 'Zürich HB', 4),
    (2, 'Bern', 'Genève', 3),
    (3, 'Genève', 'Lausanne', 4),
    (3, 'Genève', 'Zürich HB', 3);
