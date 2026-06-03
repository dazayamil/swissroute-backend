-- =====================================================================
-- Datos de ejemplo cargados al iniciar la aplicación.
-- Idempotente: solo inserta los registros que todavía no existen,
-- por lo que es seguro reiniciar la aplicación varias veces.
-- =====================================================================

-- Usuarios
INSERT INTO users (name, email, password, role, city_base, created_at)
SELECT 'Bytes Colaborativos', 'bytescolaborativos@swissroute.com', '$2a$10$2Ydvv1U4IFbsSg7PVP/AXOgbEGQClsLo88QspGPWKNwycgGuYxhMu', 'ADMIN', 'Zürich', CURRENT_TIMESTAMP -- password: 123123
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'bytescolaborativos@swissroute.com');

INSERT INTO users (name, email, password, role, city_base, created_at)
SELECT 'Chanti', 'chanti@swissroute.com', '$2a$10$2Ydvv1U4IFbsSg7PVP/AXOgbEGQClsLo88QspGPWKNwycgGuYxhMu', 'USER', 'Bern', CURRENT_TIMESTAMP -- password: 123123
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'chanti@swissroute.com');

INSERT INTO users (name, email, password, role, city_base, created_at)
SELECT 'Jorex', 'jorex@swissroute.com', '$2a$10$2Ydvv1U4IFbsSg7PVP/AXOgbEGQClsLo88QspGPWKNwycgGuYxhMu', 'USER', 'Genève', CURRENT_TIMESTAMP -- password: 123123
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'jorex@swissroute.com');

-- Rutas favoritas (del usuario chanti@swissroute.com)
INSERT INTO favorite_routes (user_id, name, origin, destination, transport_type, created_at)
SELECT u.id, 'Casa - Trabajo', 'Bern', 'Zürich HB', 'TRAIN', CURRENT_TIMESTAMP
FROM users u
WHERE u.email = 'chanti@swissroute.com'
  AND NOT EXISTS (SELECT 1 FROM favorite_routes fr WHERE fr.user_id = u.id AND fr.name = 'Casa - Trabajo');

INSERT INTO favorite_routes (user_id, name, origin, destination, transport_type, created_at)
SELECT u.id, 'Fin de semana', 'Bern', 'Interlaken Ost', 'TRAIN', CURRENT_TIMESTAMP
FROM users u
WHERE u.email = 'chanti@swissroute.com'
  AND NOT EXISTS (SELECT 1 FROM favorite_routes fr WHERE fr.user_id = u.id AND fr.name = 'Fin de semana');

-- Estaciones favoritas (del usuario chanti@swissroute.com)
INSERT INTO favorite_station (user_id, external_station_id, station_name, created_at)
SELECT u.id, '8507000', 'Bern', CURRENT_TIMESTAMP
FROM users u
WHERE u.email = 'chanti@swissroute.com'
  AND NOT EXISTS (SELECT 1 FROM favorite_station fs WHERE fs.user_id = u.id AND fs.external_station_id = '8507000');

INSERT INTO favorite_station (user_id, external_station_id, station_name, created_at)
SELECT u.id, '8503000', 'Zürich HB', CURRENT_TIMESTAMP
FROM users u
WHERE u.email = 'chanti@swissroute.com'
  AND NOT EXISTS (SELECT 1 FROM favorite_station fs WHERE fs.user_id = u.id AND fs.external_station_id = '8503000');

-- Historial de búsquedas (del usuario chanti@swissroute.com)
INSERT INTO search_history (user_id, origin, destination, num_results, query_date)
SELECT u.id, 'Bern', 'Zürich HB', 4, CURRENT_TIMESTAMP
FROM users u
WHERE u.email = 'chanti@swissroute.com'
  AND NOT EXISTS (SELECT 1 FROM search_history sh WHERE sh.user_id = u.id AND sh.origin = 'Bern' AND sh.destination = 'Zürich HB');

INSERT INTO search_history (user_id, origin, destination, num_results, query_date)
SELECT u.id, 'Bern', 'Genève', 3, CURRENT_TIMESTAMP
FROM users u
WHERE u.email = 'chanti@swissroute.com'
  AND NOT EXISTS (SELECT 1 FROM search_history sh WHERE sh.user_id = u.id AND sh.origin = 'Bern' AND sh.destination = 'Genève');
