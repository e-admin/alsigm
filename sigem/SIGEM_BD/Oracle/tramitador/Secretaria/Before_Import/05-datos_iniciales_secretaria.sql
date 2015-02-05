

--Variables de sistema
---==============================================================================
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_NOT_CNV_SP','NOT_CNV_SP','Código del tipo documental Notificacion convocatoria Sesion plenaria');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_NOT_CNV_CI','NOT_CNV_CI','Código del tipo documental Notificacion convocatoria Comisión Informativa');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_CERT_A_SP','CERT_A_SE','Código del tipo documental Certificado Acuerdo Sesion plenaria');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_NOT_A_SP','NOT_A_SE','Código del tipo documental Notificación Acuerdo ');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_NOT_A_JG','NOT_A_SE','Código del tipo documental Notificación Acuerdo ');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_PLANTDOC_NOT_A_SP','NOT_A_SP','Código de la plantilla Notificación Acuerdo Sesion plenaria');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_ACTA_SP','ACT_SP','Código del tipo documental Acta Sesión Plenaria');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_C_ERROR_SP','DCE_SP','Código del tipo documental Correción ErroR Acta Sesión Plenaria');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_LIBRO_ACTAS_SP','LIB_ACT','Código del tipo documental Libro de Actas');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_PCD_JUNTA_GOBIERNO','G007','Código del procedimiento Junta Gobierno');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_PCD_SESIONES_PLENARIAS','G010','Código del procedimiento Sesiones plenarias');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_PCD_GESTION_INTEGRANTES','G005','Código del procedimiento Gestión Integrantes');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_PCD_TRAMITACION_DECRETOS','G003','Codigo del PCD de Tramitación Decretos');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_NOT_CNV_JG','NOT_CNV_JG','Código del tipo documental Notificacion convocatoria Junta Gobierno');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_PLANTDOC_NOT_A_JG','NOT_A_JG','Código de la plantilla Notificación Acuerdo Junta Gobierno');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_LIBRO_ACTAS_JG','LIB_ACT_JG','Código del tipo documental Libro de Actas Junta Gobierno');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_PCD_LIBRO_ACTAS_JG','G008','Codigo del PCD de Libro Actas JG');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_C_ERROR_DEC','DCE_DEC','Código del tipo documental corrección error decreto');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_DEC','DEC','Código del tipo documental modelo decreto');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_NOT_DEC','NOT_DEC','Código del tipo documental notificación decreto');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_DILIGENCIA_APERTURA','DIL_APE','Código del tipo documental de la diligencia de apertura');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_DILIGENCIA_CIERRE','DIL_CIE','Código del tipo documental de la diligencia de cierre');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_LIBRO_DECRETOS','LIB_DEC','Código del tipo documental del libro decretos');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_PROP_DEC','PRO_DEC','Código del tipo documental propuesta decreto');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_TPDOC_CERT_DEC','CER_DEC','Código del tipo documental certificación decreto');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'COD_PCD_GESTION_PROPUESTAS','G006','Codigo del PCD de Gestion Propuestas');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'VALUE_RELACION_ENVIO_SESION','Sesión Plenaria/Propuesta','Relacion entre la propuesta y la SP');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'VALUE_RELACION_ENVIO_JUNTA','Junta Gobierno/Propuesta','Relacion entre la propuesta y la JG');


--- DECRETOS ---
-- Avisos de documentos firmados
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_DOCFIRMADO_EMAIL_FROM','user@server.es','Remitente email de los avisos de documentos firmados');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_DOCFIRMADO_EMAIL_SUBJECT','Documento firmado expediente ${NUMEXP}','Asunto email de los avisos de documentos firmados');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_DOCFIRMADO_EMAIL_CONTENT','El documento ${DOCUMENT_NAME} en referencia al expediente número ${NUMEXP} se ha firmado con fecha ${DOCUMENT_DATE_SIGN}','Contenido email de los avisos de documentos firmados');


-- Avisos de notificaciones firmadas
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_NOTIFICACIONES_FIRMADAS_EMAIL_FROM','user@server.es','Remitente email de los avisos de notificaciones firmadas');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_NOTIFICACIONES_FIRMADAS_SUBJECT','Notificaciones expediente ${NUMEXP}','Asunto email de los avisos de notificaciones firmadas');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_NOTIFICACIONES_FIRMADAS_EMAIL_CONTENT','Notificaciones firmadas para el expediente ${NUMEXP}','Contenido email de los avisos de notificaciones firmadas');


--- SESIONES PLENARIAS ---
-- Avisos de Sesión Plenaria Convocada
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CNV_SP_EMAIL_FROM','user@server.es','Remitente email de los avisos de notificaciones sesión plenaria convocada');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CNV_SP_EMAIL_SUBJECT','Notificación Convocatoria Sesión plenaria ${NUMEXP}','Asunto email de los avisos de notificaciones sesión plenaria convocada');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CNV_SP_EMAIL_CONTENT','Notificación Convocatoria para la Sesión plenaria del expediente ${NUMEXP}','Contenido email de los avisos de notificaciones sesión plenaria convocada');


-- Avisos de Certificados Acuerdos Propuesta Sesión Plenaria
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CAP_SP_EMAIL_FROM','user@server.es','Remitente email de los avisos de Certificación Acuerdo Sesión Plenaria');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CAP_SP_EMAIL_SUBJECT','Certificado Acuerdo Sesión plenaria ${NUMEXP}','Asunto email de los avisos de Certificación Acuerdo Sesión Plenaria');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CAP_SP_EMAIL_CONTENT','Certificado del Acuerdo de la Sesión plenaria ${NUMEXP}','Contenido email de los avisos de Certificación Acuerdo Sesión Plenaria');


--- JUNTAS DE GOBERNO ---
-- Avisos de Sesión de Xunta de Goberno Convocada
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CNV_JG_EMAIL_FROM','user@server.es','Remitente email de los avisos de notificaciones sesión plenaria convocada');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CNV_JG_EMAIL_SUBJECT','Notificación Convocatoria Sesión Xunta de Goberno ${NUMEXP}','Asunto email de los avisos de notificaciones sesión de Xunta de Goberno convocada');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CNV_JG_EMAIL_CONTENT','Notificación Convocatoria para la Sesión de Xunta de Goberno del expediente ${NUMEXP}','Contenido email de los avisos de notificaciones sesión de Xunta de Goberno convocada');


-- Avisos de Certificados Acuerdos Propuesta Sesión Junta de Goberno
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CAP_JG_EMAIL_FROM','user@server.es','Remitente email de los avisos de Certificación Acuerdo Sesión de Xunta de Goberno');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CAP_JG_EMAIL_SUBJECT','Certificado Acuerdo Sesión de Xunta de Goberno ${NUMEXP}','Asunto email de los avisos de Certificación Acuerdo Sesión de Xunta de Goberno');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_CAP_JG_EMAIL_CONTENT','Certificado del Acuerdo de la Sesión de Xunta de Goberno ${NUMEXP}','Contenido email de los avisos de Certificación Acuerdo Sesión de Xunta de Goberno');

---CONTADOR PARA DECRETOS-- (Tipo 1)
INSERT INTO SEC_CONTADORES (ID, ANIO, CONTADOR, TIPO, FORMATO)VALUES(SEC_SQ_ID_CONTADORES.NEXTVAL,2012,0,1,'$NM$/$YR$');
---CONTADOR PARA SESIONES PLENARIAS -- (Tipo 2)
INSERT INTO SEC_CONTADORES (ID, ANIO, CONTADOR, TIPO, FORMATO)VALUES(SEC_SQ_ID_CONTADORES.NEXTVAL,2012,0,2,'$YR$/$NM$');
---CONTADOR PARA Juntas de Gobierno -- (Tipo 3)
INSERT INTO SEC_CONTADORES (ID, ANIO, CONTADOR, TIPO, FORMATO)VALUES(SEC_SQ_ID_CONTADORES.NEXTVAL,2012,0,3,'$YR$/$NM$');

---CONTADOR PARA DECRETOS-- (Tipo 1)
INSERT INTO SEC_CONTADORES (ID, ANIO, CONTADOR, TIPO, FORMATO)VALUES(SEC_SQ_ID_CONTADORES.NEXTVAL,2013,0,1,'$NM$/$YR$');
---CONTADOR PARA SESIONES PLENARIAS -- (Tipo 2)
INSERT INTO SEC_CONTADORES (ID, ANIO, CONTADOR, TIPO, FORMATO)VALUES(SEC_SQ_ID_CONTADORES.NEXTVAL,2013,0,2,'$YR$/$NM$');
---CONTADOR PARA Juntas de Gobierno -- (Tipo 3)
INSERT INTO SEC_CONTADORES (ID, ANIO, CONTADOR, TIPO, FORMATO)VALUES(SEC_SQ_ID_CONTADORES.NEXTVAL,2013,0,3,'$YR$/$NM$');


--Valores tbl de validación de producto
---==============================================================================

INSERT INTO SPAC_TBL_002 (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_002.NEXTVAL , 'TLD', 'TRASLADO' , 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_002));
INSERT INTO SPAC_TBL_002 (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_002.NEXTVAL, 'TRA', 'TRAMITADOR' , 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_002));
INSERT INTO SPAC_TBL_002 (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_002.NEXTVAL, 'NOT', 'NOTIFICADO' , 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_002));
INSERT INTO SPAC_TBL_002 (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_002.NEXTVAL, 'TRP', 'TRAMITADOR-PROPUESTA' , 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_002));

INSERT INTO SPAC_TBL_004 (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_004.NEXTVAL, 'CNV', 'CONVOCADA' , 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_004));
INSERT INTO SPAC_TBL_004 (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_004.NEXTVAL, 'PLN', 'EN PLENO' , 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_004));
INSERT INTO SPAC_TBL_004 (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_004.NEXTVAL, 'XGB', 'EN JUNTA GOBIERNO' , 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_004));
INSERT INTO SPAC_TBL_004 (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_004.NEXTVAL, 'DFT', 'DECRETO FIRMADO Y TRASLADADO' , 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_004));
INSERT INTO SPAC_TBL_004 (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_004.NEXTVAL, 'ENS', 'ESPERANDO NOTIFICACIONES' , 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_004));
INSERT INTO SPAC_TBL_004 (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_004.NEXTVAL, 'DNT', 'DECRETO NOTIFICADO' , 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_004));


INSERT INTO SPAC_TBL_007 (id, valor,  vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_007.NEXTVAL, 'Sesión/Propuesta', 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_007));
INSERT INTO SPAC_TBL_007 (id, valor,  vigente, orden) VALUES (SPAC_SQ_SPAC_TBL_007.NEXTVAL, 'Junta Gobierno/Propuesta', 1, (SELECT MAX(ID)+1 FROM SPAC_TBL_007));


--Tags de documentos
---==============================================================================
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES(SPAC_SQ_ID_CTREGLAS.NEXTVAL,'GetDiligenciaInformativa', 'Datos de la diligencia Informativa asociada al trámite','ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags.GetDiligenciaInformativaRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES(SPAC_SQ_ID_CTREGLAS.NEXTVAL,'GetNumHojasLibroDecretos', 'Obtiene la hoja final del libro sin tener en cuenta la diligencia de cierre','ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags.GetNumHojasLibroDecretosRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES(SPAC_SQ_ID_CTREGLAS.NEXTVAL,'GetInfoNotificacionAcuerdosPropuestaSesion','Info Acuerdos Notif Propuesta sesion','ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags.GetInfoNotificacionAcuerdosPropuestaSesionRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES(SPAC_SQ_ID_CTREGLAS.NEXTVAL,'GetRecursosPropuestaSesion', 'Obtiene los recursos del participante de la propuesta','ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags.GetRecursosPropuestaSesionRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES(SPAC_SQ_ID_CTREGLAS.NEXTVAL,'CheckActaSPNotSigned', 'No permite llevar a cabo la operación si el acta de la SP está firmada','ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.plenarias.CheckActaSPNotSignedRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES(SPAC_SQ_ID_CTREGLAS.NEXTVAL,'CheckActaJGNotSigned', 'No permite llevar a cabo la operación si el acta de la JG está firmada','ieci.tecdoc.sgm.tram.secretaria.rules.sesiones.juntas.CheckActaJGNotSignedRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES(SPAC_SQ_ID_CTREGLAS.NEXTVAL,'GetInfoAcuerdosPropuestaSesion','Info Acuerdos Notif Propuesta sesion','ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags.GetInfoAcuerdosPropuestaSesionRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES(SPAC_SQ_ID_CTREGLAS.NEXTVAL,'GetNumHojasLibroActas', 'Obtiene la hoja final del libro sin tener en cuenta la diligencia de cierre','ieci.tecdoc.sgm.tram.secretaria.rules.libro.GetNumHojasLibroActasRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES(SPAC_SQ_ID_CTREGLAS.NEXTVAL,'GetOrdenDelDia','Lista de propuestas a tratar','ieci.tecdoc.sgm.tram.secretaria.rules.documentos.tags.GetOrdenDelDiaRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES( SPAC_SQ_ID_CTREGLAS.NEXTVAL ,'SetNumDecretoRule', 'Si firma Alcaldía se asigna el número de decreto','ieci.tecdoc.sgm.tram.secretaria.rules.signcircuit.SetNumDecretoRule',1);


INSERT INTO SPAC_CT_ENTIDADES (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion)
	VALUES (SPAC_SQ_ID_CTENTIDADES.NEXTVAL, 3, 'SEC_VLDTBL_ORGANO_GOBIERNO', 'ID', NULL, NULL, NULL, 'Tabla de validación para organo de gobierno', 'SPAC_SQ_661291693', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>3</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>250</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');

INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL , (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_ORGANO_GOBIERNO'), 'es', 'SEC_VLDTBL_ORGANO_GOBIERNO', 'Órgano gobierno');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_ORGANO_GOBIERNO'), 'gl', 'SEC_VLDTBL_ORGANO_GOBIERNO', 'Órgano gobierno');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_ORGANO_GOBIERNO'), 'eu', 'SEC_VLDTBL_ORGANO_GOBIERNO', 'Órgano gobierno');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_ORGANO_GOBIERNO'), 'ca', 'SEC_VLDTBL_ORGANO_GOBIERNO', 'Órgano gobierno');


INSERT INTO SEC_vldtbl_organo_gobierno (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_661291693.NEXTVAL , '01' , 'Pleno de Gobierno', 1, 1);
INSERT INTO SEC_vldtbl_organo_gobierno (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_661291693.NEXTVAL , '02' , 'Junta de Gobierno', 1, 2);

INSERT INTO SPAC_CT_ENTIDADES (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion)
	VALUES (SPAC_SQ_ID_CTENTIDADES.NEXTVAL, 3, 'SEC_VLDTBL_CARGO_ORGANO', 'ID', NULL, NULL, NULL, 'Tabla de validación para Cargo órgano', 'SPAC_SQ_562949042', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>3</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>250</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');

INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_CARGO_ORGANO'), 'es', 'SEC_VLDTBL_CARGO_ORGANO', 'Cargo Órgano');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_CARGO_ORGANO'), 'gl', 'SEC_VLDTBL_CARGO_ORGANO', 'Cargo Órgano');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_CARGO_ORGANO'), 'eu', 'SEC_VLDTBL_CARGO_ORGANO', 'Cargo Órgano');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_CARGO_ORGANO'), 'ca', 'SEC_VLDTBL_CARGO_ORGANO', 'Cargo Órgano');


INSERT INTO SEC_VLDTBL_CARGO_ORGANO (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_562949042.NEXTVAL, '01', 'Sr. Presidente', 1, 1);
INSERT INTO SEC_VLDTBL_CARGO_ORGANO (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_562949042.NEXTVAL, '02', 'Sr. Interventor', 1, 2);
INSERT INTO SEC_VLDTBL_CARGO_ORGANO (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_562949042.NEXTVAL, '03', 'Sr. Secretario', 1, 3);
INSERT INTO SEC_VLDTBL_CARGO_ORGANO (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_562949042.NEXTVAL, '04', 'Sr. Jefe Servicio', 1, 4);



INSERT INTO SPAC_CT_ENTIDADES (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion)
	VALUES (SPAC_SQ_ID_CTENTIDADES.NEXTVAL, 3, 'SEC_VLDTBL_AREA_GOBIERNO', 'ID', NULL, NULL, NULL, 'Tabla de validación para área de gobierno', 'SPAC_SQ_660930754', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>3</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>250</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');

INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_AREA_GOBIERNO'), 'es', 'SEC_VLDTBL_AREA_GOBIERNO', 'Área gobierno');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_AREA_GOBIERNO'), 'gl', 'SEC_VLDTBL_AREA_GOBIERNO', 'Área gobierno');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_AREA_GOBIERNO'), 'eu', 'SEC_VLDTBL_AREA_GOBIERNO', 'Área gobierno');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_AREA_GOBIERNO'), 'ca', 'SEC_VLDTBL_AREA_GOBIERNO', 'Área gobierno');


INSERT INTO SEC_vldtbl_area_gobierno (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_660930754.NEXTVAL, '01', 'Área Gobierno 1', 1, 1);
INSERT INTO SEC_vldtbl_area_gobierno (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_660930754.NEXTVAL, '02', 'Área Gobierno 2', 1, 2);
INSERT INTO SEC_vldtbl_area_gobierno (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_660930754.NEXTVAL, '03', 'Área Gobierno 3', 1, 3);


INSERT INTO SPAC_CT_ENTIDADES (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion)
	VALUES (SPAC_SQ_ID_CTENTIDADES.NEXTVAL, 3, 'SEC_VLDTBL_CARGO_AREA', 'ID', NULL, NULL, NULL, 'Tabla de validación para Cargo área', 'SPAC_SQ_1765192511', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>3</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>250</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');

INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_CARGO_AREA'), 'es', 'SEC_VLDTBL_CARGO_AREA', 'Cargo Área');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_CARGO_AREA'), 'gl', 'SEC_VLDTBL_CARGO_AREA', 'Cargo Área');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_CARGO_AREA'), 'eu', 'SEC_VLDTBL_CARGO_AREA', 'Cargo Área');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_VLDTBL_CARGO_AREA'), 'ca', 'SEC_VLDTBL_CARGO_AREA', 'Cargo Área');


INSERT INTO SEC_VLDTBL_CARGO_AREA (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_1765192511.NEXTVAL, '01', 'Cargo área1', 1, 1);
INSERT INTO SEC_VLDTBL_CARGO_AREA (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_1765192511.NEXTVAL, '02', 'Cargo área2', 1, 2);
INSERT INTO SEC_VLDTBL_CARGO_AREA (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_1765192511.NEXTVAL, '03', 'Cargo área3', 1, 3);
INSERT INTO SEC_VLDTBL_CARGO_AREA (id, valor, sustituto, vigente, orden) VALUES (SPAC_SQ_1765192511.NEXTVAL, '04', 'Cargo área4', 1, 4);




INSERT INTO SPAC_CT_ENTIDADES (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (SPAC_SQ_ID_CTENTIDADES.NEXTVAL, 3, 'SEC_TBL_VIEW_PROPUESTA', 'ID', NULL, NULL, NULL, 'Vista Propuesta', '<SIN SECUENCIA>', '<entity version=''1.00''><editable>N</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>6</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>sustituto</physicalName><descripcion><![CDATA[Texto que se muestra en la lista de selección]]></descripcion><type>0</type><size>256</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_TBL_VIEW_PROPUESTA'), 'es', 'SEC_TBL_VIEW_PROPUESTA', 'Vista Propuestas');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_TBL_VIEW_PROPUESTA'), 'gl', 'SEC_TBL_VIEW_PROPUESTA', 'Vista Propuestas');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_TBL_VIEW_PROPUESTA'), 'eu', 'SEC_TBL_VIEW_PROPUESTA', 'Vista Propuestas');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_TBL_VIEW_PROPUESTA'), 'ca', 'SEC_TBL_VIEW_PROPUESTA', 'Vista Propuestas');



INSERT INTO SPAC_CT_ENTIDADES (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (SPAC_SQ_ID_CTENTIDADES.NEXTVAL, 3, 'SEC_TBL_VIEW_SESION_P', 'ID', NULL, NULL, NULL, 'Vista Sesión / Propuesta', '<SIN SECUENCIA>', '<entity version=''1.00''><editable>N</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>6</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>sustituto</physicalName><descripcion><![CDATA[Texto que se muestra en la lista de selección]]></descripcion><type>0</type><size>30</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_TBL_VIEW_SESION_P'), 'es', 'SEC_TBL_VIEW_SESION_P', 'Vista Sesión / Propuesta');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_TBL_VIEW_SESION_P'), 'gl', 'SEC_TBL_VIEW_SESION_P', 'Vista Sesión / Propuesta');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_TBL_VIEW_SESION_P'), 'eu', 'SEC_TBL_VIEW_SESION_P', 'Vista Sesión / Propuesta');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, (SELECT ID FROM SPAC_CT_ENTIDADES WHERE NOMBRE='SEC_TBL_VIEW_SESION_P'), 'ca', 'SEC_TBL_VIEW_SESION_P', 'Vista Sesión / Propuesta');

INSERT INTO PUB_ACCIONES  (id, nombre, clase, activa, descripcion, tipo) VALUES(PUB_SQ_ID_ACCIONES.NEXTVAL,'EnvioMailDocAction','ieci.tdw.ispac.ispacpublicador.business.action.mensajesCortos.EnvioMailDocAction',1,'Mensajes Cortos - Envío email con adjuntos',1);


  INSERT INTO SPAC_CT_JERARQUIAS (ID, ID_ENTIDAD_PADRE, ID_ENTIDAD_HIJA, NOMBRE , DESCRIPCION , TIPO)
VALUES(
	SPAC_SQ_ID_CTJERARQUIAS.NEXTVAL,
	(select id from spac_ct_entidades where nombre='SEC_TBL_VIEW_SESION_P'),
	(select id from spac_ct_entidades where nombre='SEC_TBL_VIEW_PROPUESTA'),
	'Sesion-Propuesta',
	'Propuestas a tratar en la sesión',
	2);


