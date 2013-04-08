

-----------------------------------
-- Actualización de v5.2.1 a v5.2.2
-----------------------------------

--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '5.2.2', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '5.2.2', current_timestamp);


--
-- Eliminar tablas y secuencias de organismos
--
DROP TABLE spac_organismos;
DROP SEQUENCE spac_sq_id_organismos;
DROP TABLE spac_organismos_numexp;


--
-- Tablas para Contador de número de expedientes
--

CREATE TABLE spac_numexp_contador
(
  anio integer NOT NULL,
  contador integer,
  formato character varying(32),
  CONSTRAINT pk_spac_numexp_contador PRIMARY KEY (anio)
) 

-- Inicializar el contador del número de expediente
INSERT INTO spac_numexp_contador(anio, contador, formato) VALUES (2008, 1, 'EXP$YR$/$NM$');

-- Añadir columna para el identificador de notificación en los documentos
ALTER TABLE spac_dt_documentos ADD COLUMN id_notificacion character varying(64);

-- Añadir estados de notificación a la tabla de validación
INSERT INTO spac_tbl_006 (id, valor, sustituto, vigente) VALUES (nextval('spac_sq_spac_tbl_006'), 'RE', 'Rechazada', 1);
INSERT INTO spac_tbl_006 (id, valor, sustituto, vigente) VALUES (nextval('spac_sq_spac_tbl_006'), 'ER', 'Error', 1);