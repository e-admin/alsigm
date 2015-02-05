------------------------------------
-- ACTUALIZACION PARA EL REGISTRO --
------------------------------------

-- Fecha efectiva puede ser nula
ALTER TABLE sgmrtregistro ALTER COLUMN fecha_efectiva DROP NOT NULL;

-- Tamanio de los campos al igual que estan en registro
ALTER TABLE sgmrtcatalogo_organos ALTER organo TYPE character varying(16);
ALTER TABLE sgmrtcatalogo_organos ALTER descripcion TYPE character varying(250);

ALTER TABLE sgmrtcatalogo_tramites ALTER organo TYPE character varying(16);

-- Tramites
UPDATE sgmrtcatalogo_docstramite SET codigo_documento = 'TRAM14D2' WHERE tramite_id = 'TRAM_14' AND codigo_documento = 'TRAM15D2';