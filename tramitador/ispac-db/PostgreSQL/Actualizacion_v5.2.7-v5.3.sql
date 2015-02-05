

-----------------------------------
-- Actualización de v5.2.7 a v5.3
-----------------------------------

--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '5.3', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '5.3', current_timestamp);


--
-- Relación múltiple entre sustitutos y períodos de fechas
--
CREATE SEQUENCE spac_sq_id_sssustitucionfecha INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;
CREATE TABLE spac_ss_sustitucionfecha (
	id integer NOT NULL,
	id_sustitucion integer,
	id_fechsustitucion integer
);
ALTER TABLE ONLY spac_ss_sustitucionfecha
	ADD CONSTRAINT pk_spac_ss_sustitucionfecha PRIMARY KEY (id);
ALTER TABLE ONLY spac_ss_sustitucionfecha
    ADD CONSTRAINT u_spac_ss_sustitucionfecha UNIQUE (id_sustitucion, id_fechsustitucion);
--
-- ¡¡ ATENCIÓN !!
--
-- Ejecutar Actualizacion_v5.2.7-v5.3_scripts.zip
--
-- y a continuación
--
-- UPDATE spac_ss_fechsustituciones
-- SET uid_sustituido = null;
-- ALTER TABLE spac_ss_fechsustituciones
-- DROP COLUMN uid_sustituido;


--
-- Variable global del identificador de la Fase Archivo en el catalogo
--
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'ID_STAGE_ARCHIVO', '4', 'Identificador de la fase Archivo en el catálogo');


--
-- Registro entrada/salida en documentos
--
ALTER TABLE spac_dt_documentos
    ALTER COLUMN origen_id TYPE character varying(20);
ALTER TABLE spac_dt_documentos
    ALTER COLUMN origen TYPE character varying(250);
ALTER TABLE spac_dt_documentos
    ALTER COLUMN destino_id TYPE character varying(20);

--
-- Eliminar regla InicializarValoresExpediente
--
DELETE FROM spac_ct_reglas WHERE id=4;
DELETE FROM spac_p_eventos WHERE id_regla=4;

--
-- Eliminar regla SetFechaCierreRule
--
DELETE FROM spac_p_eventos WHERE id_regla=10;

--
-- Eliminar regla AsignarTipoRegistro
--
DELETE FROM spac_ct_reglas WHERE id=7;
DELETE FROM spac_p_eventos WHERE id_regla=7;


--
-- Variable global con los Idiomas soportados
--
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'LANGUAGES', 'es;gl;eu;ca', 'Idiomas soportados');

