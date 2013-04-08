
--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '6.1', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '6.1', current_timestamp);


--
-- Actualización del EntityApp de la lista de documentos del expediente
--
UPDATE spac_ct_aplicaciones set clase='ieci.tdw.ispac.ispacmgr.app.ListDocumentsEntityApp' where id =5;
