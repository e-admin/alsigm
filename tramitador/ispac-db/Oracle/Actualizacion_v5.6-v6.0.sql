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


CREATE TABLE SPAC_VLDTBL_TIPO_NOTIF (ID NUMBER NOT NULL, VALOR VARCHAR2(2),SUSTITUTO VARCHAR2(64), VIGENTE NUMBER(1),ORDEN NUMBER(10));

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


--
-- Cambiar el evento de lanzamiento de las reglas InitObraMenorRule, InitQuejaRule, InitSubvencionRule
--
UPDATE SPAC_P_EVENTOS SET EVENTO=32 WHERE EVENTO=1 AND ID_REGLA IN (20, 21, 22);


--
-- Formulario para intervinientes
--
--     Actualizacion_v5.6-v6.0_Files\frm_entidades\thirdForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=3;
--     Aumentar la versión del formulario
--

--
-- Formulario para expedientes
--
--     Actualizacion_v5.6-v6.0_Files\frm_entidades\expedienteForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=1;
--     Aumentar la versión del formulario
--

--
-- Informe etiqueta
--
--     SActualizacion_v5.6-v6.0_Files\informes\Etiqueta genérica del expediente.jrxml
--         --> SPAC_CT_INFORMES.xml cuando id=1;
--     Actualizar la fecha
--

