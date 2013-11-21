--Actualizacion de la version 8.0.2 a la version 8.0.3 M9

-- Eliminar el país Birmania porque está repetido con Myanmar.
DELETE FROM SCR_COUNTRY where NAME='Birmania';
UPDATE SCR_COUNTRYEXREG SET ID_COUNTRY=153, NAME='Myanmar' WHERE ID=30;

-- Añadimos un nuevo tipo de dirección telemática
INSERT INTO scr_typeaddress (id, description, code) VALUES (6, 'Comparecencia Electrónica', 'TE');

-- Se borra la duplicidad de Gijon como ciudad
DELETE FROM SCR_CITIESEXREG WHERE id = 496;
DELETE FROM SCR_CITIES WHERE id = 5015;