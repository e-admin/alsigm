
--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '6.1', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '6.1', current_timestamp);


--
-- Actualización del EntityApp de la lista de documentos del expediente
--
UPDATE spac_ct_aplicaciones set clase='ieci.tdw.ispac.ispacmgr.app.ListDocumentsEntityApp' where id =5;


--
--  Cambio del tipo de la columna PARAMETROS en SPAC_CT_APLICACIONES
--
ALTER TABLE SPAC_CT_APLICACIONES ALTER PARAMETROS TYPE text;