------------------------------------
-- ACTUALIZACION PARA EL REGISTRO --
------------------------------------

-- Fecha efectiva puede ser nula
ALTER TABLE sgmrtregistro ALTER COLUMN fecha_efectiva datetime NULL;

-- Tamanio de los campos al igual que estan en registro
ALTER TABLE sgmrtcatalogo_organos ALTER COLUMN organo varchar(16) NOT NULL;
ALTER TABLE sgmrtcatalogo_organos ALTER COLUMN descripcion varchar(250) NOT NULL;

ALTER TABLE sgmrtcatalogo_tramites ALTER COLUMN organo varchar(16) NOT NULL;

-- Tramites
UPDATE sgmrtcatalogo_docstramite SET codigo_documento = 'TRAM14D2' WHERE tramite_id = 'TRAM_14' AND codigo_documento = 'TRAM15D2';