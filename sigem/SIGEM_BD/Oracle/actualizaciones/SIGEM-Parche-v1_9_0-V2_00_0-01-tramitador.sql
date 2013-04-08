-----------------------------------
-- Actualización de v5.6 a v6.0
-----------------------------------


--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '6.0', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '6.0', current_timestamp);

--
-- Tabla para relacionar informes a nivel de formulario de búsqueda
--

CREATE TABLE SPAC_CT_FRMBUSQ_INFORMES (
  	ID_FMRBUSQUEDA NUMBER NOT NULL,
  	ID_INFORME NUMBER NOT NULL
);


--
-- Añadir columna XML_FORMATEADOR a SPAC_CT_APLICACIONES
--
ALTER TABLE SPAC_CT_APLICACIONES ADD XML_FORMATEADOR CLOB;

---
--- Añadir una nueva tabla de validacion con los tipos de notificación y su secuencia y sus valores iniciales
---


CREATE TABLE SPAC_VLDTBL_TIPO_NOTIF (
    ID NUMBER NOT NULL,
    VALOR VARCHAR2(2),
    SUSTITUTO VARCHAR2(64),
    VIGENTE NUMBER(1),
	ORDEN NUMBER(10)
);

ALTER TABLE SPAC_VLDTBL_TIPO_NOTIF
	ADD CONSTRAINT PK_SPAC_VLDTBL_TIPO_NOTIF PRIMARY KEY (ID);

CREATE SEQUENCE SPAC_SQ_VLDTBL_TIPO_NOTIF					START WITH 1 INCREMENT BY 1 MINVALUE 1 NOCACHE  NOCYCLE  ORDER ;


INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES 
(197, 3, 'SPAC_VLDTBL_TIPO_NOTIF', 'ID', NULL, NULL, NULL, 'Tabla de validación para tipos de notificación', 'SPAC_SQ_SPAC_VLDTBL_TIPO_NOTIF','<entity version=''1.00''><editable>N</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>2</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>sustituto</physicalName><descripcion><![CDATA[Texto que se muestra en la lista de selección]]></descripcion><type>0</type><size>64</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>');

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 197, 'es', 'SPAC_VLDTBL_TIPO_NOTIF', 'Tipos de Notificación');

INSERT INTO spac_vldtbl_tipo_notif (id, valor, sustituto, vigente, orden) VALUES (1, 'EM', 'E-MAIL', 1, 1);
INSERT INTO spac_vldtbl_tipo_notif (id, valor, sustituto, vigente, orden) VALUES (2, 'SM', 'SMS', 1, 2);

--Actualizamos la secuencia
ALTER SEQUENCE SPAC_SQ_VLDTBL_TIPO_NOTIF increment by 3;
SELECT SPAC_SQ_VLDTBL_TIPO_NOTIF.NEXTVAL FROM dual;
ALTER SEQUENCE SPAC_SQ_VLDTBL_TIPO_NOTIF increment by 1;


--
--Añadir a la tabla de circuitos de firma la dirección de notificación y el tipo de notificacion
---

ALTER TABLE spac_ctos_firma_detalle ADD TIPO_NOTIF VARCHAR2(2);
ALTER TABLE spac_ctos_firma_detalle ADD DIR_NOTIF VARCHAR2(64);

	
--
-- Acciones de publicación
--
INSERT INTO pub_acciones (id, nombre, clase, activa, descripcion, tipo) VALUES (PUB_SQ_ID_ACCIONES.NEXTVAL, 'CT-Cerrar Expediente', 'ieci.tdw.ispac.ispacpublicador.business.action.consultaTelematica.CerrarExpedienteAction', 1, 'Consulta Telemática - Cerrar expediente', 1);
INSERT INTO pub_acciones (id, nombre, clase, activa, descripcion, tipo) VALUES (PUB_SQ_ID_ACCIONES.NEXTVAL, 'MC-Envío e-mail', 'ieci.tdw.ispac.ispacpublicador.business.action.mensajesCortos.EnvioMailAction', 1, 'Mensajes Cortos - Envío de e-mail', 1);
INSERT INTO pub_acciones (id, nombre, clase, activa, descripcion, tipo) VALUES (PUB_SQ_ID_ACCIONES.NEXTVAL, 'MC-Envío SMS', 'ieci.tdw.ispac.ispacpublicador.business.action.mensajesCortos.EnvioSmsAction', 1, 'Mensajes Cortos - Envío de SMS', 1);


--
-- Reglas de publicación
--
INSERT INTO pub_reglas (id, id_pcd, id_fase, id_tramite, tipo_doc, id_evento, id_accion, id_condicion, atributos, id_aplicacion, orden, id_info) VALUES (PUB_SQ_ID_REGLAS.NEXTVAL, 3, 0, 0, 0, 2, (SELECT ID FROM PUB_ACCIONES WHERE NOMBRE='CT-Cerrar Expediente'), 1, null, 1, 1, 0);
INSERT INTO pub_reglas (id, id_pcd, id_fase, id_tramite, tipo_doc, id_evento, id_accion, id_condicion, atributos, id_aplicacion, orden, id_info) VALUES (PUB_SQ_ID_REGLAS.NEXTVAL, 4, 0, 0, 0, 2, (SELECT ID FROM PUB_ACCIONES WHERE NOMBRE='CT-Cerrar Expediente'), 1, null, 1, 1, 0);
INSERT INTO pub_reglas (id, id_pcd, id_fase, id_tramite, tipo_doc, id_evento, id_accion, id_condicion, atributos, id_aplicacion, orden, id_info) VALUES (PUB_SQ_ID_REGLAS.NEXTVAL, 5, 0, 0, 0, 2, (SELECT ID FROM PUB_ACCIONES WHERE NOMBRE='CT-Cerrar Expediente'), 1, null, 1, 1, 0);
																																																

--
-- Regla: AvisoFirmanteRule
--
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (SPAC_SQ_ID_CTREGLAS.NEXTVAL, 'AvisoFirmanteRule', 'Regla para avisar a los firmantes de un circuito de firma', 'ieci.tecdoc.sgm.tram.rules.AvisoFirmanteRule', 1);


-- 
-- Añadir columna COD_PLANT a la tabla de plantillas 
-- 
ALTER TABLE SPAC_P_PLANTDOC ADD COD_PLANT VARCHAR2(16); 


--
-- Cambio del tipo de la columna MAPEO_RT en SPAC_CT_PROCEDIMIENTOS
--
ALTER TABLE SPAC_CT_PROCEDIMIENTOS ADD AUX VARCHAR2(4000);
UPDATE SPAC_CT_PROCEDIMIENTOS SET AUX = MAPEO_RT;
ALTER TABLE SPAC_CT_PROCEDIMIENTOS DROP COLUMN MAPEO_RT;
ALTER TABLE SPAC_CT_PROCEDIMIENTOS ADD MAPEO_RT CLOB;
UPDATE SPAC_CT_PROCEDIMIENTOS SET MAPEO_RT = AUX;
ALTER TABLE SPAC_CT_PROCEDIMIENTOS DROP COLUMN AUX;


-- Tablas jerarquicas

-- ==============================================================
-- Si ya se estaba haciendo uso de tablas jerarquicas, habra que modificar de forma manual
-- las existentes:
-- 1) En spac_ct_jerarquias se introduce un nuevo campo llamado 'tipo' cuyos posibles
-- valores son 1 --> la tabla jerarquica de relacion sera una TABLA, 2 --> la tabla jerarquica de relacion sera una VISTA
--
-- 2) Las tablas o vistas donde se establecen las relaciones ahora se nombran de la siguiente forma: SPAC_CT_JERARQUICA_<ID> ej:SPAC_CT_JERARQUICA_1,
-- antes era SPAC_CT_JERARQUIA_<ID> donde <ID> ocupa 3 caracteres, ej:  SPAC_CT_JERARQUICA_001
-- 3) Estas tablas o vistas van a contener un nuevo campo que se utilizara como clave primaria, quedando la estructura de la siguiente forma:
-- spac_ct_jerarquia_1(id,id_padre,id_hija), donde id es el nuevo campo
-- 4) Para alimentar la clave primaria se necesitaria una secuencia cuyo nombre seria: SPAC_SQ_<HASHCODE_NOMBRE_TABLA).
-- ==============================================================
CREATE TABLE SPAC_CT_JERARQUIAS(
  ID NUMBER NOT NULL,
  ID_ENTIDAD_PADRE NUMBER,
  ID_ENTIDAD_HIJA NUMBER,
  NOMBRE VARCHAR2(64),
  DESCRIPCION VARCHAR2(4000),
  TIPO NUMBER(1) DEFAULT 1
);
ALTER TABLE SPAC_CT_JERARQUIAS ADD CONSTRAINT PK_SPAC_CT_JERARQUIAS PRIMARY KEY (ID);
CREATE SEQUENCE SPAC_SQ_ID_CTJERARQUIAS	START WITH 1 INCREMENT BY 1 MINVALUE 1 NOCACHE  NOCYCLE  ORDER ;


--
-- Variables del sistema por defecto para la regla AvisoFirmanteRule
--
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_FIRMANTE_EMAIL_FROM', 'sigem@localhost', 'Emisor del correo electrónico');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_FIRMANTE_EMAIL_SUBJECT', 'Documento del expediente ${NUMEXP}', 'Asunto del correo electrónico');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_FIRMANTE_EMAIL_CONTENT', 'Contenido del correo:
- NumExp: ${NUMEXP}
- Documento: ${DOCUMENT_NAME}
- Descripción: ${DOCUMENT_DESC}
- Fecha: ${DOCUMENT_DATE}', 'Contenido del correo electrónico');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_FIRMANTE_SMS_USER', '', 'Usuario del sistema de envío de SMSs.');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_FIRMANTE_SMS_PASSWORD', '', 'Clave del usuario del sistema de envío de SMSs');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_FIRMANTE_SMS_SRC', 'SIGEM', 'Emisor del SMS');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.NEXTVAL, 'AVISO_FIRMANTE_SMS_TXT', 'Texto del SMS', 'Texto del SMS');


--
-- Cambiar el evento de lanzamiento de las reglas InitObraMenorRule, InitQuejaRule, InitSubvencionRule
--
UPDATE SPAC_P_EVENTOS SET EVENTO=32 WHERE EVENTO=1 AND ID_REGLA IN (20, 21, 22);


--
-- Formulario para intervinientes
--
--     SIGEM-Parche-v1_9_0-v1_10_0-01-tramitador_Files\frm_entidades\thirdForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=3;
--     Aumentar la versión del formulario
--

--
-- Formulario para expedientes
--
--     SIGEM-Parche-v1_9_0-v1_10_0-01-tramitador_Files\frm_entidades\expedienteForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=1;
--     Aumentar la versión del formulario
--

--
-- Informe etiqueta
--
--     SIGEM-Parche-v1_9_0-v1_10_0-01-tramitador_Files\informes\Etiqueta genérica del expediente.jrxml
--         --> SPAC_CT_INFORMES.xml cuando id=1;
--     Actualizar la fecha
--
