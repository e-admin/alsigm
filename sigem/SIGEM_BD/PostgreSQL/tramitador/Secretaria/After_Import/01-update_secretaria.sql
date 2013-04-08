---Valores Tablas de validación específicas del módulo de secretaría

INSERT INTO sec_vldtbl_aprobacion VALUES (NEXTVAL('spac_sq_308949855'),'A','Aprobada', 1, 1);
INSERT INTO sec_vldtbl_aprobacion VALUES (NEXTVAL('spac_sq_308949855'),'NA','No aprobada', 1, 2);
INSERT INTO sec_vldtbl_aprobacion VALUES (NEXTVAL('spac_sq_308949855'),'AC','Aprobación condicionada', 1, 3);


INSERT INTO sec_vldtbl_prioridad VALUES (NEXTVAL('spac_sq_1682456425'),'A','Alta', 1, 1);
INSERT INTO sec_vldtbl_prioridad VALUES (NEXTVAL('spac_sq_1682456425'),'M','Media', 1, 2);
INSERT INTO sec_vldtbl_prioridad VALUES (NEXTVAL('spac_sq_1682456425'),'B','Baja', 1, 3);



INSERT INTO sec_vldtbl_num_ordinal VALUES (NEXTVAL('spac_sq_2142120743'),'1','Primera', 1, 1);
INSERT INTO sec_vldtbl_num_ordinal VALUES (NEXTVAL('spac_sq_2142120743'),'2','Segunda', 1, 2);
INSERT INTO sec_vldtbl_num_ordinal VALUES (NEXTVAL('spac_sq_2142120743'),'3','Tercera', 1, 3);


INSERT INTO sec_vldtbl_tipo_sesion VALUES (NEXTVAL('spac_sq_410954785'),'O','Ordinaria', 1, 1);
INSERT INTO sec_vldtbl_tipo_sesion VALUES (NEXTVAL('spac_sq_410954785'),'E','Extraordinaria', 1, 2);

INSERT INTO SEC_VLDTBL_ORG_COMPETENTE VALUES (NEXTVAL('SPAC_SQ_660930754'), 'PLE', 'Sesión Plenaria' , 1, 1);
INSERT INTO SEC_VLDTBL_ORG_COMPETENTE VALUES (NEXTVAL('SPAC_SQ_660930754'), 'JUN', 'Sesión Junta Gobierno' , 1, 2);


INSERT INTO SEC_VLDTBL_TIPOS_PROPUESTA VALUES (NEXTVAL('SPAC_SQ_763381636'), '01', 'Ordinaria' , 1, 1);
INSERT INTO SEC_VLDTBL_TIPOS_PROPUESTA VALUES (NEXTVAL('SPAC_SQ_763381636'), '02', 'Urgencia' , 1, 2);
INSERT INTO SEC_VLDTBL_CLF_PROPUESTA(id, valor, sustituto, vigente, orden) VALUES (nextval('SPAC_SQ_1674673126'), '00', 'Asuntos de urgencia', '1', '1');
INSERT INTO SEC_VLDTBL_CLF_PROPUESTA(id, valor, sustituto, vigente, orden) VALUES (nextval('SPAC_SQ_1674673126'), '01', 'Otros asuntos', '1', '2');


---Actualización de las vistas
CREATE OR REPLACE VIEW SEC_TBL_VIEW_PROPUESTA AS
SELECT id AS id, id::character varying(6) AS valor, titulo::character varying(256) AS sustituto, 1::numeric(1,0) AS vigente, id::numeric(10,0) AS orden FROM SEC_PROPUESTA;

CREATE OR REPLACE VIEW SEC_TBL_VIEW_SESION_P AS
 SELECT id AS id, id::character varying(6) AS valor, numexp::character varying(30) AS sustituto, 1::numeric(1,0) AS vigente, id::numeric(10,0) AS orden FROM SEC_SESION_PLENARIA;
CREATE OR REPLACE FUNCTION createViewJerarquiaByName(IN name character varying, OUT id numeric) AS
$BODY$DECLARE stmt varchar(512);
BEGIN
stmt:='SELECT id from spac_ct_jerarquias where nombre=''' || name || '''' ;
EXECUTE  stmt INTO id;
stmt:='CREATE OR REPLACE VIEW SPAC_CT_JERARQUIA_'|| id ||' AS SELECT SEC_PROPUESTA_SESION.ID AS id, SEC_SESION_PLENARIA.ID AS id_padre ,  SEC_PROPUESTA_SESION.ID_PROPUESTA AS id_hija FROM SEC_PROPUESTA_SESION , SEC_SESION_PLENARIA WHERE SEC_SESION_PLENARIA.NUMEXP=SEC_PROPUESTA_SESION.NUMEXP;';
EXECUTE  stmt ;
END$BODY$
LANGUAGE 'plpgsql' VOLATILE;

ALTER FUNCTION createViewJerarquiaByName(IN name character varying, OUT id numeric) OWNER TO postgres;


