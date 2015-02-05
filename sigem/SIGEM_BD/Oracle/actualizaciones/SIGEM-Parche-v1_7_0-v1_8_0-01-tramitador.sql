-----------------------------------
-- Actualización de v5.4 a v5.5.1
-----------------------------------


--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '5.5.1', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '5.5.1', current_timestamp);


--
-- A la entidad spac_dt_documentos se le añaden los campos id_reg_entidad e id_entidad  y se modifica su definicion en spac_ct_entities
--
--ALTER TABLE SPAC_DT_DOCUMENTOS ADD id_reg_entidad INTEGER;
--ALTER TABLE SPAC_DT_DOCUMENTOS ADD id_entidad INTEGER;
--update spac_ct_entidades set definicion='<entity version=''1.00''><editable>S</editable><dropable>N</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><type>3</type><nullable>S</nullable></field><field id=''2''><physicalName>numexp</physicalName><type>0</type><size>30</size><nullable>S</nullable></field><field id=''3''><physicalName>fdoc</physicalName><type>6</type><nullable>S</nullable></field><field id=''4''><physicalName>nombre</physicalName><type>0</type><size>100</size><nullable>S</nullable></field><field id=''5''><physicalName>autor</physicalName><type>0</type><size>250</size><nullable>S</nullable></field><field id=''6''><physicalName>id_fase</physicalName><type>3</type><nullable>S</nullable></field><field id=''7''><physicalName>id_tramite</physicalName><type>3</type><nullable>S</nullable></field><field id=''8''><physicalName>id_tpdoc</physicalName><type>3</type><nullable>S</nullable></field><field id=''9''><physicalName>tp_reg</physicalName><type>0</type><size>16</size><nullable>S</nullable></field><field id=''10''><physicalName>nreg</physicalName><type>0</type><size>16</size><nullable>S</nullable></field><field id=''11''><physicalName>freg</physicalName><type>6</type><nullable>S</nullable></field><field id=''12''><physicalName>infopag</physicalName><type>0</type><size>100</size><nullable>S</nullable></field><field id=''13''><physicalName>id_fase_pcd</physicalName><type>3</type><nullable>S</nullable></field><field id=''14''><physicalName>id_tramite_pcd</physicalName><type>3</type><nullable>S</nullable></field><field id=''15''><physicalName>estado</physicalName><type>0</type><size>16</size><nullable>S</nullable></field><field id=''16''><physicalName>origen</physicalName><type>0</type><size>128</size><nullable>S</nullable></field><field id=''17''><physicalName>descripcion</physicalName><type>0</type><size>250</size><nullable>S</nullable></field><field id=''18''><physicalName>origen_id</physicalName><type>3</type><nullable>S</nullable></field><field id=''19''><physicalName>destino</physicalName><type>0</type><size>250</size><nullable>S</nullable></field><field id=''20''><physicalName>autor_info</physicalName><type>0</type><size>250</size><nullable>S</nullable></field><field id=''21''><physicalName>estadofirma</physicalName><type>0</type><size>2</size><nullable>S</nullable></field><field id=''22''><physicalName>id_notificacion</physicalName><type>0</type><size>64</size><nullable>S</nullable></field><field id=''23''><physicalName>estadonotificacion</physicalName><type>0</type><size>2</size><nullable>S</nullable></field><field id=''24''><physicalName>destino_id</physicalName><type>3</type><nullable>S</nullable></field><field id=''25''><physicalName>fnotificacion</physicalName><type>6</type><nullable>S</nullable></field><field id=''26''><physicalName>faprobacion</physicalName><type>6</type><nullable>S</nullable></field><field id=''27''><physicalName>origen_tipo</physicalName><type>0</type><size>1</size><nullable>S</nullable></field><field id=''28''><physicalName>destino_tipo</physicalName><type>0</type><size>1</size><nullable>S</nullable></field><field id=''29''><physicalName>id_plantilla</physicalName><type>3</type><nullable>S</nullable></field><field id=''30''><physicalName>bloqueo</physicalName><type>0</type><size>2</size><nullable>S</nullable></field><field id=''31''><physicalName>repositorio</physicalName><type>0</type><size>8</size><nullable>S</nullable></field><field id=''32''><physicalName>extension</physicalName><type>0</type><size>4</size><nullable>S</nullable></field><field id=''33''><physicalName>ffirma</physicalName><type>6</type><nullable>S</nullable></field><field id=''34''><physicalName>infopag_rde</physicalName><type>0</type><size>256</size><nullable>S</nullable></field><field id=''35''><physicalName>id_reg_entidad</physicalName><type>3</type><nullable>S</nullable></field><field id=''36''><physicalName>id_entidad</physicalName><type>3</type><nullable>S</nullable></field></fields></entity>'
--where id=2;
--
-- Esto ya lo hace el script de actualización
--

-- Se aumenta el campo extension para los documentos
ALTER TABLE spac_dt_documentos MODIFY extension VARCHAR2(4);


--
-- Terceros
--
-- Identificación externa de la Direccion asociada a un tercero
ALTER TABLE spac_expedientes  ADD iddireccionpostal VARCHAR2(32);
ALTER TABLE spac_dt_intervinientes ADD iddireccionpostal VARCHAR2(32);

-- Cambio de tipo del identificador externo del tercero
ALTER TABLE spac_expedientes MODIFY idtitular VARCHAR2(32);
ALTER TABLE spac_dt_intervinientes MODIFY id_ext VARCHAR2(32);

--Se actualiza la definición de las entidades modificadas
--UPDATE spac_ct_entidades SET definicion='<entity version=''1.00''><editable>S</editable><dropable>N</dropable><status>0</status><fields><field id=''1''><physicalName>id_pcd</physicalName><type>3</type></field><field id=''2''><physicalName>numexp</physicalName><type>0</type><size>30</size></field><field id=''3''><physicalName>referencia_interna</physicalName><type>0</type><size>30</size></field><field id=''4''><physicalName>nreg</physicalName><type>0</type><size>16</size></field><field id=''5''><physicalName>freg</physicalName><type>6</type></field><field id=''6''><physicalName>estadoinfo</physicalName><type>0</type><size>128</size></field><field id=''7''><physicalName>festado</physicalName><type>6</type></field><field id=''8''><physicalName>nifciftitular</physicalName><type>0</type><size>16</size></field><field id=''9''><physicalName>idtitular</physicalName><type>0</type><size>32</size></field><field id=''10''><physicalName>domicilio</physicalName><type>0</type><size>128</size></field><field id=''11''><physicalName>ciudad</physicalName><type>0</type><size>64</size></field><field id=''12''><physicalName>regionpais</physicalName><type>0</type><size>64</size></field><field id=''13''><physicalName>cpostal</physicalName><type>0</type><size>5</size></field><field id=''14''><physicalName>identidadtitular</physicalName><type>0</type><size>128</size></field><field id=''15''><physicalName>tipopersona</physicalName><type>0</type><size>1</size></field><field id=''16''><physicalName>roltitular</physicalName><type>0</type><size>4</size></field><field id=''17''><physicalName>asunto</physicalName><type>0</type><size>512</size></field><field id=''18''><physicalName>finicioplazo</physicalName><type>6</type></field><field id=''19''><physicalName>poblacion</physicalName><type>0</type><size>64</size></field><field id=''20''><physicalName>municipio</physicalName><type>0</type><size>64</size></field><field id=''21''><physicalName>localizacion</physicalName><type>0</type><size>256</size></field><field id=''22''><physicalName>exprelacionados</physicalName><type>0</type><size>256</size></field><field id=''23''><physicalName>codprocedimiento</physicalName><type>0</type><size>16</size></field><field id=''24''><physicalName>nombreprocedimiento</physicalName><type>0</type><size>128</size></field><field id=''25''><physicalName>plazo</physicalName><type>3</type></field><field id=''26''><physicalName>uplazo</physicalName><type>0</type><size>1</size></field><field id=''27''><physicalName>formaterminacion</physicalName><type>0</type><size>1</size></field><field id=''28''><physicalName>utramitadora</physicalName><type>0</type><size>256</size></field><field id=''29''><physicalName>funcionactividad</physicalName><type>0</type><size>80</size></field><field id=''30''><physicalName>materias</physicalName><type>0</type><size>2</size></field><field id=''31''><physicalName>servpresactuaciones</physicalName><type>0</type><size>128</size></field><field id=''32''><physicalName>tipodedocumental</physicalName><type>0</type><size>16</size></field><field id=''33''><physicalName>palabrasclave</physicalName><type>0</type><size>64</size></field><field id=''34''><physicalName>fcierre</physicalName><type>6</type></field><field id=''35''><physicalName>estadoadm</physicalName><type>0</type><size>128</size></field><field id=''36''><physicalName>hayrecurso</physicalName><type>0</type><size>2</size></field><field id=''37''><physicalName>efectosdelsilencio</physicalName><type>0</type><size>1</size></field><field id=''38''><physicalName>fapertura</physicalName><type>6</type></field><field id=''39''><physicalName>observaciones</physicalName><type>0</type><size>256</size></field><field id=''40''><physicalName>idunidadtramitadora</physicalName><type>3</type></field><field id=''41''><physicalName>idproceso</physicalName><type>3</type></field><field id=''42''><physicalName>tipodireccioninteresado</physicalName><type>0</type><size>16</size></field><field id=''43''><physicalName>nversion</physicalName><type>0</type><size>16</size></field><field id=''44''><physicalName>idseccioniniciadora</physicalName><type>0</type><size>64</size></field><field id=''45''><physicalName>seccioniniciadora</physicalName><type>0</type><size>250</size></field><field id=''46''><physicalName>tfnofijo</physicalName><type>0</type><size>32</size></field><field id=''47''><physicalName>tfnomovil</physicalName><type>0</type><size>32</size></field><field id=''48''><physicalName>direcciontelematica</physicalName><type>0</type><size>60</size></field><field id=''49''><physicalName>id</physicalName><type>3</type></field><field id=''50''><physicalName>iddireccionpostal</physicalName><type>0</type><size>32</size></field></fields><validations><validation id=''1''><fieldId>27</fieldId><table>SPAC_TBL_003</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation><validation id=''2''><fieldId>35</fieldId><table>SPAC_TBL_004</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation><validation id=''3''><fieldId>42</fieldId><table>SPAC_TBL_005</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation><validation id=''4''><fieldId>16</fieldId><table>SPAC_TBL_002</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation><validation id=''5''><fieldId>17</fieldId><table/><tableType/><class/><mandatory>S</mandatory></validation></validations></entity>'
--WHERE ID = 1;

--UPDATE spac_ct_entidades SET definicion='<entity version=''1.00''><editable>S</editable><dropable>N</dropable><status>0</status><fields><field id=''1''><physicalName>id_ext</physicalName><type>0</type><size>32</size></field><field id=''2''><physicalName>rol</physicalName><type>0</type><size>4</size></field><field id=''3''><physicalName>tipo</physicalName><type>3</type></field><field id=''4''><physicalName>tipo_persona</physicalName><type>0</type><size>1</size></field><field id=''5''><physicalName>ndoc</physicalName><type>0</type><size>12</size></field><field id=''6''><physicalName>nombre</physicalName><type>0</type><size>250</size></field><field id=''7''><physicalName>dirnot</physicalName><type>0</type><size>250</size></field><field id=''8''><physicalName>email</physicalName><type>0</type><size>250</size></field><field id=''9''><physicalName>c_postal</physicalName><type>0</type><size>10</size></field><field id=''10''><physicalName>localidad</physicalName><type>0</type><size>150</size></field><field id=''11''><physicalName>caut</physicalName><type>0</type><size>150</size></field><field id=''12''><physicalName>tfno_fijo</physicalName><type>0</type><size>32</size></field><field id=''13''><physicalName>tfno_movil</physicalName><type>0</type><size>32</size></field><field id=''14''><physicalName>tipo_direccion</physicalName><type>0</type><size>1</size></field><field id=''15''><physicalName>direcciontelematica</physicalName><type>0</type><size>60</size></field><field id=''16''><physicalName>id</physicalName><type>3</type></field><field id=''17''><physicalName>numexp</physicalName><type>0</type><size>30</size></field><field id=''18''><physicalName>iddireccionpostal</physicalName><type>0</type><size>32</size></field></fields><validations><validation id=''1''><fieldId>6</fieldId><table/><tableType/><class/><mandatory>S</mandatory></validation><validation id=''2''><fieldId>14</fieldId><table>SPAC_TBL_005</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation><validation id=''3''><fieldId>2</fieldId><table>SPAC_TBL_002</table><tableType>3</tableType><class/><mandatory>N</mandatory></validation></validations></entity>'
--WHERE ID = 3;


--
-- Cambio en la vista de plazos para tener en cuenta las actividades
--
CREATE OR REPLACE VIEW SPAC_DEADLINE AS 
SELECT OBJ.NUMEXP, OBJ.FECHA_LIMITE, PROCEDIMIENTO.NOMBRE AS NOMBRE_PCD, OBJ.ID_RESP, OBJ.ID AS ID_OBJETO, 'RESOLUCIÓN EXPEDIENTE' AS DESCRIPCION, 1 AS TIPO
   FROM SPAC_PROCESOS OBJ, SPAC_CT_PROCEDIMIENTOS PROCEDIMIENTO
  WHERE OBJ.ID_PCD = PROCEDIMIENTO.ID AND OBJ.FECHA_LIMITE IS NOT NULL
UNION 
 SELECT OBJ.NUMEXP, OBJ.FECHA_LIMITE, PROCEDIMIENTO.NOMBRE AS NOMBRE_PCD, OBJ.ID_RESP, OBJ.ID AS ID_OBJETO, 'RESOLUCIÓN FASE' AS DESCRIPCION, 2 AS TIPO
   FROM SPAC_FASES OBJ, SPAC_CT_PROCEDIMIENTOS PROCEDIMIENTO
  WHERE OBJ.ID_PCD = PROCEDIMIENTO.ID AND OBJ.ESTADO = 1 AND OBJ.TIPO=1 AND OBJ.FECHA_LIMITE IS NOT NULL
UNION 
 SELECT OBJ.NUMEXP, OBJ.FECHA_LIMITE, PROCEDIMIENTO.NOMBRE AS NOMBRE_PCD, OBJ.ID_RESP, OBJ.ID AS ID_OBJETO, OBJ.NOMBRE AS DESCRIPCION, 3 AS TIPO
   FROM SPAC_TRAMITES OBJ, SPAC_CT_PROCEDIMIENTOS PROCEDIMIENTO
  WHERE OBJ.ID_PCD = PROCEDIMIENTO.ID AND OBJ.ESTADO = 1 AND OBJ.FECHA_LIMITE IS NOT NULL
UNION 
 SELECT OBJ.NUMEXP, OBJ.FECHA_LIMITE, PROCEDIMIENTO.NOMBRE AS NOMBRE_PCD, OBJ.ID_RESP, OBJ.ID AS ID_OBJETO, 'RESOLUCIÓN ACTIVIDAD' AS DESCRIPCION, 4 AS TIPO
   FROM SPAC_FASES OBJ, SPAC_P_PROCEDIMIENTOS PROCEDIMIENTO
  WHERE OBJ.ID_PCD = PROCEDIMIENTO.ID AND OBJ.ESTADO = 1 AND OBJ.TIPO=2 AND OBJ.FECHA_LIMITE IS NOT NULL;


--
-- Informes
--

-- Secuencia
CREATE SEQUENCE SPAC_SQ_ID_CTINFORMES START WITH 1 INCREMENT BY 1 MINVALUE 1 NOCACHE  NOCYCLE  ORDER ;

-- Tabla
CREATE TABLE SPAC_CT_INFORMES (
	ID NUMBER NOT NULL,
	NOMBRE VARCHAR(100),
	DESCRIPCION VARCHAR(255),
	XML CLOB,
	FECHA DATE,
	TIPO NUMBER,
	FILAS NUMBER(2),
	COLUMNAS NUMBER(2)
);

ALTER TABLE SPAC_CT_INFORMES ADD CONSTRAINT PK_SPAC_CT_INFORMES PRIMARY KEY (ID);

CREATE TABLE SPAC_P_INFORMES (
  	ID_OBJ NUMBER NOT NULL,
  	TP_OBJ NUMBER NOT NULL,
  	ID_INFORME NUMBER NOT NULL
);

ALTER TABLE SPAC_P_INFORMES ADD CONSTRAINT PK_SPAC_P_INFORMES PRIMARY KEY (ID_OBJ, TP_OBJ, ID_INFORME);

--- Informes iniciales
insert into spac_ct_informes (id, nombre, descripcion, xml, fecha, tipo)
values(SPAC_SQ_ID_CTINFORMES.NEXTVAL, 'Etiqueta',
 'Informe Etiquetas 4x2','<xml/>', current_timestamp ,1);
-- 
-- Incluir en el campo XML el contenido del fichero: 
--   SIGEM-Parche-v1_7_0-v1_8_0-01-tramitador_Files/informes/Etiqueta genérica del expediente.jrxml
--

insert into spac_ct_informes (id, nombre, descripcion, xml, fecha, tipo)
values(SPAC_SQ_ID_CTINFORMES.NEXTVAL, 'Ficha Expediente',
 'Informe Ficha Expediente', '<xml/>', current_timestamp ,1);
-- 
-- Incluir en el campo XML el contenido del fichero: 
--   SIGEM-Parche-v1_7_0-v1_8_0-01-tramitador_Files/informes/Ficha genérica del expediente.jrxml
--

-- 
-- Incluir en el campo frm_bsq de spac_ct_fmrbusqueda del formulario con id=1: 
--   SIGEM-Parche-v1_7_0-v1_8_0-01-tramitador_Files/frm_busqueda/busquedaAvanzada.xml
--


--
-- Recursos modificados
--
UPDATE SPAC_CT_ENTIDADES_RESOURCES SET CLAVE = 'NVERSION' WHERE ID_ENT = 1 AND CLAVE = 'VERSION';
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 1, 'es', 'IDDIRECCIONPOSTAL', 'Id. de Dirección Postal');
DELETE FROM SPAC_CT_ENTIDADES_RESOURCES WHERE ID_ENT = 2 AND CLAVE = 'NDOC';
DELETE FROM SPAC_CT_ENTIDADES_RESOURCES WHERE ID_ENT = 2 AND CLAVE = 'NUM_ACTO';
DELETE FROM SPAC_CT_ENTIDADES_RESOURCES WHERE ID = 153;
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 2, 'es', 'ID_NOTIFICACION', 'Id. de Notificación');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 2, 'es', 'AUTOR', 'Autor');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 3, 'es', 'IDDIRECCIONPOSTAL', 'Id. de Dirección Postal');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 7, 'es', 'ID_SUBPROCESO', 'Id. de Subproceso');


--
-- Recursos para direcciones en expediente e intervinientes
--
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 1, 'es', 'LBL_LIBRE', 'Libre');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 1, 'es', 'LBL_CONFIRMADA', 'Verificada');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 1, 'es', 'SEP_DIRECCIONES', 'DIRECCION NOTIFICACIÓN');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 3, 'es', 'SEP_DIRECCIONES', 'DIRECCION NOTIFICACIÓN');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 3, 'es', 'LBL_CONFIRMADA', 'Verificada');
INSERT INTO SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (SPAC_SQ_ID_ENTIDADESRESOURCES.NEXTVAL, 3, 'es', 'LBL_LIBRE', 'Libre');

--
-- Formulario para expedientes
--
--     SIGEM-Parche-v1_7_0-v1_8_0-01-tramitador_Files\frm_entidades\expedientForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=1;
--     Aumentar la versión del formulario
--

--
-- Formulario para intervinientes
--
--     SIGEM-Parche-v1_7_0-v1_8_0-01-tramitador_Files\frm_entidades\thirdForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=3;
--     Aumentar la versión del formulario
--
