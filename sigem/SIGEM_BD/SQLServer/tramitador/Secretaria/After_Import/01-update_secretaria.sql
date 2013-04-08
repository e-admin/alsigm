---Valores Tablas de validación específicas del módulo de secretaría

---Actualización de las vistas


CREATE VIEW SEC_TBL_VIEW_PROPUESTA  (ID, VALOR, SUSTITUTO, VIGENTE , ORDEN) as
SELECT  CAST (( ID ) AS int),
	CAST ( (ID) AS  CHARACTER VARYING (6)),
	CAST ((TITULO) AS CHARACTER VARYING (256)),
	CAST (1 AS NUMERIC(1,0))  , CAST((ID) AS NUMERIC(10,0))
FROM  SEC_PROPUESTA
;

CREATE VIEW SEC_TBL_VIEW_SESION_P  (ID, VALOR, SUSTITUTO, VIGENTE , ORDEN) as
SELECT  CAST (( ID ) AS int),
	CAST ((ID) AS  CHARACTER VARYING (5)),
	CAST ((NUMEXP) AS CHARACTER VARYING (30)),
	CAST (1 AS NUMERIC(1,0))  , CAST((ID) AS NUMERIC(10,0))
FROM  SEC_SESION_PLENARIA
;

DROP PROCEDURE createViewJerarquiaByName;


DECLARE @sequence_sec_vldtbl_aprobacion_id int;
DECLARE @sequence_sec_vldtbl_prioridad_id int;
DECLARE @sequence_sec_vldtbl_num_ordinal_id int;
DECLARE @sequence_sec_vldtbl_tipo_sesion_id int;
DECLARE @sequence_sec_vldtbl_org_competente_id int;
DECLARE @sequence_sec_vldtbl_tipos_propuesta_id int;

UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_aprobacion_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_308949855';
INSERT INTO sec_vldtbl_aprobacion (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_aprobacion_id,'A','Aprobada', 1, 1);
UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_aprobacion_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_308949855';
INSERT INTO sec_vldtbl_aprobacion (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_aprobacion_id,'NA','No aprobada', 1, 2);
UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_aprobacion_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_308949855';
INSERT INTO sec_vldtbl_aprobacion (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_aprobacion_id,'AC','Aprobación condicionada', 1, 3);





UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_prioridad_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_1682456425';
INSERT INTO sec_vldtbl_prioridad (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_prioridad_id,'A','Alta', 1, 1);
UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_prioridad_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_1682456425';
INSERT INTO sec_vldtbl_prioridad (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_prioridad_id,'M','Media', 1, 2);
UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_prioridad_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_1682456425';
INSERT INTO sec_vldtbl_prioridad (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_prioridad_id,'B','Baja', 1, 3);




UPDATE SPAC_SQ_SEQUENCES
SET   @sequence_sec_vldtbl_num_ordinal_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_2142120743';
INSERT INTO sec_vldtbl_num_ordinal  (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES ( @sequence_sec_vldtbl_num_ordinal_id,'1','Primera', 1, 1);
UPDATE SPAC_SQ_SEQUENCES
SET   @sequence_sec_vldtbl_num_ordinal_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_2142120743';
INSERT INTO sec_vldtbl_num_ordinal (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES ( @sequence_sec_vldtbl_num_ordinal_id,'2','Segunda', 1, 2);
UPDATE SPAC_SQ_SEQUENCES
SET   @sequence_sec_vldtbl_num_ordinal_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_2142120743';
INSERT INTO sec_vldtbl_num_ordinal (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES ( @sequence_sec_vldtbl_num_ordinal_id,'3','Tercera', 1, 3);


UPDATE SPAC_SQ_SEQUENCES
SET   @sequence_sec_vldtbl_tipo_sesion_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_410954785';
INSERT INTO sec_vldtbl_tipo_sesion (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_tipo_sesion_id,'O','Ordinaria', 1, 1);
UPDATE SPAC_SQ_SEQUENCES
SET   @sequence_sec_vldtbl_tipo_sesion_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_410954785';
INSERT INTO sec_vldtbl_tipo_sesion (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_tipo_sesion_id,'E','Extraordinaria', 1, 2);

UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_org_competente_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_660930754';
INSERT INTO SEC_VLDTBL_ORG_COMPETENTE (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_org_competente_id, 'PLE', 'Sesión Plenaria' , 1, 1);
UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_org_competente_id = sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_660930754';
INSERT INTO SEC_VLDTBL_ORG_COMPETENTE (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_org_competente_id, 'JUN', 'Sesión Junta Gobierno' , 1, 2);



UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_tipos_propuesta_id= sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_763381636';
INSERT INTO SEC_VLDTBL_TIPOS_PROPUESTA (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN)  VALUES (@sequence_sec_vldtbl_tipos_propuesta_id, '01', 'Ordinaria' , 1, 1);
UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_tipos_propuesta_id= sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_763381636';
INSERT INTO SEC_VLDTBL_TIPOS_PROPUESTA (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (@sequence_sec_vldtbl_tipos_propuesta_id, '02', 'Urgencia' , 1, 2);



DECLARE @sequence_sec_vldtbl_clf_propuesta_id int;
UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_clf_propuesta_id= sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_1674673126';
INSERT INTO SEC_VLDTBL_CLF_PROPUESTA(id, valor, sustituto, vigente, orden) VALUES (@sequence_sec_vldtbl_clf_propuesta_id, '00', 'Asuntos de urgencia', '1', '1');
UPDATE SPAC_SQ_SEQUENCES
SET  @sequence_sec_vldtbl_clf_propuesta_id= sequence_id = sequence_id + 1
WHERE SEQUENCE_NAME='SPAC_SQ_1674673126';
INSERT INTO SEC_VLDTBL_CLF_PROPUESTA(id, valor, sustituto, vigente, orden) VALUES (@sequence_sec_vldtbl_clf_propuesta_id, '01', 'Otros asuntos', '1', '2');



