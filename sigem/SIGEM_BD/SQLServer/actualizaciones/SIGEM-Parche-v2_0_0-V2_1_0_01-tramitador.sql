
--
-- Información de versión
--
DECLARE @sequence_id int
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_INFOSISTEMA';
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (@sequence_id, 'VERSIONBD', '6.1', getdate());
UPDATE SPAC_SQ_SEQUENCES	SET @sequence_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_INFOSISTEMA';
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (@sequence_id, 'VERSIONAPP', '6.1', getdate());
GO


--
-- Actualización del EntityApp de la lista de documentos del expediente
--
UPDATE spac_ct_aplicaciones set clase='ieci.tdw.ispac.ispacmgr.app.ListDocumentsEntityApp' where id =5;
GO


--
-- Cambio del tipo de la columna PARAMETROS en SPAC_CT_APLICACIONES
--
ALTER TABLE SPAC_CT_APLICACIONES ALTER COLUMN PARAMETROS text;
GO
