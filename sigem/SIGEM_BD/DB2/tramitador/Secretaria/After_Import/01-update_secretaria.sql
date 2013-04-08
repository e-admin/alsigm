
---Actualización de las vistas


 create  view SEC_TBL_VIEW_PROPUESTA   as
SELECT  cast ( ID as integer) AS ID ,
cast(  ( ID ) as varchar(6)) AS VALOR ,
cast(  ( TITULO ) as varchar(256)) AS SUSTITUTO ,
cast (1 as smallint) as VIGENTE , cast (ID as  smallint)AS ORDEN
FROM  SEC_PROPUESTA;


create  view SEC_TBL_VIEW_SESION_P   as
SELECT  cast ( ID as integer) AS ID ,
cast(  ( ID ) as varchar(6)) AS VALOR ,
cast(  ( NUMEXP ) as varchar(30)) AS SUSTITUTO ,
cast (1 as smallint) as VIGENTE , cast (ID as  smallint)AS ORDEN
FROM  SEC_SESION_PLENARIA;


---Valores Tablas de validación específicas del módulo de secretaría

INSERT INTO sec_vldtbl_aprobacion (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_308949855 ,'A','Aprobada', 1, 1);
INSERT INTO sec_vldtbl_aprobacion (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_308949855,'NA','No aprobada', 1, 2);
INSERT INTO sec_vldtbl_aprobacion (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_308949855,'AC','Aprobación condicionada', 1, 3);


INSERT INTO sec_vldtbl_prioridad (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_1682456425 ,'A','Alta', 1, 1);
INSERT INTO sec_vldtbl_prioridad (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_1682456425,'M','Media', 1, 2);
INSERT INTO sec_vldtbl_prioridad (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_1682456425,'B','Baja', 1, 3);



INSERT INTO sec_vldtbl_num_ordinal (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_2142120743,'1','Primera', 1, 1);
INSERT INTO sec_vldtbl_num_ordinal (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_2142120743 ,'2','Segunda', 1, 2);
INSERT INTO sec_vldtbl_num_ordinal (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_2142120743 ,'3','Tercera', 1, 3);


INSERT INTO sec_vldtbl_tipo_sesion (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_410954785 ,'O','Ordinaria', 1, 1);
INSERT INTO sec_vldtbl_tipo_sesion (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_410954785 ,'E','Extraordinaria', 1, 2);

INSERT INTO SEC_VLDTBL_ORG_COMPETENTE (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR SPAC_SQ_660930754 , 'PLE', 'Sesión Plenaria' , 1, 1);
INSERT INTO SEC_VLDTBL_ORG_COMPETENTE (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR SPAC_SQ_660930754 , 'JUN', 'Sesión Junta Gobierno' , 1, 2);


INSERT INTO SEC_VLDTBL_TIPOS_PROPUESTA (id, valor, sustituto, vigente, orden)  VALUES (NEXTVAL FOR SPAC_SQ_763381636 , '01', 'Ordinaria' , 1, 1);
INSERT INTO SEC_VLDTBL_TIPOS_PROPUESTA (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR SPAC_SQ_763381636 , '02', 'Urgencia' , 1, 2);
INSERT INTO SEC_VLDTBL_CLF_PROPUESTA(id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR SPAC_SQ_1674673126, '00', 'Asuntos de urgencia', '1', '1');
INSERT INTO SEC_VLDTBL_CLF_PROPUESTA(id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR SPAC_SQ_1674673126 , '01', 'Otros asuntos', '1', '2');


