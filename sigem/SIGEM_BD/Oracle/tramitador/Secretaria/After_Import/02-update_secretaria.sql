

---Valores Tablas de validación específicas del módulo de secretaría

INSERT INTO sec_vldtbl_aprobacion (id, valor, sustituto, vigente, orden) VALUES (spac_sq_308949855.NEXTVAL ,'A','Aprobada', 1, 1);
INSERT INTO sec_vldtbl_aprobacion (id, valor, sustituto, vigente, orden) VALUES (spac_sq_308949855.NEXTVAL ,'NA','No aprobada', 1, 2);
INSERT INTO sec_vldtbl_aprobacion (id, valor, sustituto, vigente, orden) VALUES (spac_sq_308949855.NEXTVAL ,'AC','Aprobación condicionada', 1, 3);


INSERT INTO sec_vldtbl_prioridad (id, valor, sustituto, vigente, orden)  VALUES (spac_sq_1682456425.NEXTVAL ,'A','Alta', 1, 1);
INSERT INTO sec_vldtbl_prioridad  (id, valor, sustituto, vigente, orden) VALUES (spac_sq_1682456425.NEXTVAL ,'M','Media', 1, 2);
INSERT INTO sec_vldtbl_prioridad (id, valor, sustituto, vigente, orden) VALUES (spac_sq_1682456425.NEXTVAL ,'B','Baja', 1, 3);



INSERT INTO sec_vldtbl_num_ordinal (id, valor, sustituto, vigente, orden) VALUES (spac_sq_2142120743.NEXTVAL ,'1','Primera', 1, 1);
INSERT INTO sec_vldtbl_num_ordinal (id, valor, sustituto, vigente, orden) VALUES (spac_sq_2142120743.NEXTVAL ,'2','Segunda', 1, 2);
INSERT INTO sec_vldtbl_num_ordinal (id, valor, sustituto, vigente, orden) VALUES (spac_sq_2142120743.NEXTVAL ,'3','Tercera', 1, 3);


INSERT INTO sec_vldtbl_tipo_sesion (id, valor, sustituto, vigente, orden) VALUES (spac_sq_410954785.NEXTVAL ,'O','Ordinaria', 1, 1);
INSERT INTO sec_vldtbl_tipo_sesion (id, valor, sustituto, vigente, orden) VALUES (spac_sq_410954785.NEXTVAL ,'E','Extraordinaria', 1, 2);

INSERT INTO SEC_VLDTBL_ORG_COMPETENTE (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_660930754.NEXTVAL , 'PLE', 'Sesión Plenaria' , 1, 1);
INSERT INTO SEC_VLDTBL_ORG_COMPETENTE  (id, valor, sustituto, vigente, orden)VALUES (SPAC_SQ_660930754.NEXTVAL , 'JUN', 'Sesión Junta Gobierno' , 1, 2);


INSERT INTO SEC_VLDTBL_TIPOS_PROPUESTA (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_763381636.NEXTVAL , '01', 'Ordinaria' , 1, 1);
INSERT INTO SEC_VLDTBL_TIPOS_PROPUESTA (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_763381636.NEXTVAL , '02', 'Urgencia' , 1, 2);
INSERT INTO SEC_VLDTBL_CLF_PROPUESTA(id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_1674673126.NEXTVAL , '00', 'Asuntos de urgencia', '1', '1');
INSERT INTO SEC_VLDTBL_CLF_PROPUESTA(id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_1674673126.NEXTVAL , '01', 'Otros asuntos', '1', '2');



