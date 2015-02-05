



---------------------------------------------------------
-- DATOS EXTRAIDOS DEL SCRIPT datos_iniciales  --
---------------------------------------------------------
--Tipos de documentos
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (1, 'Acuerdo Consejo de Gobierno', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (2, 'Admisión a trámite', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (3, 'Alegaciones', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'ENTRADA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (4, 'Archivo del expediente', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (5, 'Comunicación al interesado', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (6, 'Comunicación de apertura', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (7, 'Decreto de concesión', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (9, 'Emisión oficio de respuesta', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (10, 'Emisión oficio no admisión', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (12, 'Notificación oficio de respuesta', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (13, 'Propuesta de resolución', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (14, 'Remisión documentación', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'ENTRADA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (16, 'Notificación resolución', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-09 00:00:00', 'SALIDA', NULL);

-- Trámites
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (1, 'Admisión a trámite', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (2, 'Alegaciones', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (3, 'Archivo del expediente', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (4, 'Comunicación al interesado', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (5, 'Comunicación de apertura', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (6, 'Decreto de concesión', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (8, 'Emisión oficio de respuesta', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (9, 'Emisión oficio no admisión', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (11, 'Notificación oficio de respuesta', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (12, 'Propuesta de resolución', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (13, 'Remisión documentación', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (15, 'Acuerdo Consejo de Gobierno', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (17, 'Notificación resolución', NULL, NULL, NULL, NULL, NULL, '2007-10-09 00:00:00', 'SYSSUPERUSER');

-- Trámites asociados a las fases
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (1, 1, 1);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (2, 1, 5);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (4, 2, 2);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (6, 2, 12);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (7, 2, 15);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (9, 2, 8);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (10, 2, 11);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (11, 3, 3);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (12, 3, 4);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (13, 3, 6);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (14, 3, 9);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (16, 3, 13);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (18, 3, 17);

--Tipos de documentos asociados a los trámites
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (1, 1, 2);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (2, 2, 3);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (3, 4, 5);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (5, 5, 6);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (6, 6, 7);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (8, 8, 9);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (9, 12, 13);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (10, 13, 14);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (12, 15, 1);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (13, 11, 12);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (14, 9, 10);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (16, 17, 16);

--Plantillas
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (1, 2, '2007-10-08 18:46:19', 'Admisión a trámite', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (2, 6, '2007-10-08 18:48:10', 'Comunicación de apertura', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (3, 9, '2007-10-08 18:49:49', 'Emisión oficio de respuesta', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (4, 12, '2007-10-08 18:52:06', 'Notificación oficio de respuesta', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (6, 14, '2007-10-08 18:57:52', 'Remisión documentación', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (7, 5, '2007-10-08 18:59:48', 'Comunicación al interesado', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (8, 10, '2007-10-08 19:01:59', 'Emisión oficio no admisión', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (11, 13, '2007-10-09 09:32:51', 'Propuesta de resolución', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (12, 1, '2007-10-09 09:34:00', 'Acuerdo Consejo de Gobierno', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (13, 3, '2007-10-09 09:38:39', 'Alegaciones', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (18, 7, '2007-10-09 13:41:07', 'Decreto de concesión', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (34, 8, '2007-10-09 16:37:13', 'Emisión informe técnico', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (35, 8, '2007-10-09 16:37:44', 'Emisión informe jurídico', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (38, 16, '2007-10-09 16:51:22', 'Notificación resolución', '', NULL, NULL, NULL);

INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (137, 3, 'SPAC_TBL_010', 'ID', NULL, NULL, NULL, 'Tabla de validación para Tipos de Suelo', 'SPAC_SQ_SPAC_TBL_010', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 137, 'es', 'SPAC_TBL_010', 'Tipos de Suelo');
INSERT INTO spac_tbl_010 (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_spac_tbl_010, '1', 'Urbanizable', 1, 1);
INSERT INTO spac_tbl_010 (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_spac_tbl_010, '2', 'No urbanizable', 1, 2);
INSERT INTO spac_tbl_010 (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_spac_tbl_010, '3', 'Urbano', 1, 3);



INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (138, 3, 'SPAC_TBL_011', 'ID', NULL, NULL, NULL, 'Tabla de validación para Tipos de Finca', 'SPAC_SQ_SPAC_TBL_011', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 138, 'es', 'SPAC_TBL_011', 'Tipos de Finca');
INSERT INTO spac_tbl_011 (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_spac_tbl_011, '1', 'Unifamiliar', 1, 1);



INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (139, 3, 'SPAC_TBL_012', 'ID', NULL, NULL, NULL, 'Tabla de validación para Localización de Obras', 'SPAC_SQ_SPAC_TBL_012', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 139, 'es', 'SPAC_TBL_012', 'Localización de Obras');
INSERT INTO spac_tbl_012 (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_spac_tbl_012, '1', 'Exterior', 1, 1);
INSERT INTO spac_tbl_012 (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_spac_tbl_012, '2', 'Interior', 1, 2);

--
-- Entidad
--
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha)
	VALUES (200, 1, 'SGL_OBRAS_MENORES', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Obra Menor', 'SGL_SQ_ID_OBRAS_MENORES', NULL, NULL, current timestamp);

UPDATE spac_ct_entidades SET definicion ='<entity version=''1.00''>
<editable>S</editable>
<dropable>S</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>tipo_objeto</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''2''>
		<physicalName>tipo_localizador</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''3''>
		<physicalName>localizador</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''4''>
		<physicalName>actuacion_casco_historico</physicalName>
		<type>0</type>
		<size>2</size>
	</field>
	<field id=''5''>
		<physicalName>tipo_finca</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''6''>
		<physicalName>tipo_suelo</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''7''>
		<physicalName>ubicacion_inmueble</physicalName>
		<type>0</type>
		<size>512</size>
	</field>
	<field id=''8''>
		<physicalName>referencia_catastral</physicalName>
		<type>0</type>
		<size>20</size>
	</field>
	<field id=''9''>
		<physicalName>localizacion</physicalName>
		<type>0</type>
		<size>15</size>
	</field>
	<field id=''10''>
		<physicalName>descripcion</physicalName>
		<type>1</type>
	</field>
	<field id=''11''>
		<physicalName>presupuesto_total</physicalName>
		<type>4</type>
		<size>15</size>
		<precision>2</precision>
	</field>
	<field id=''12''>
		<physicalName>fecha_inicio</physicalName>
		<type>6</type>
	</field>
	<field id=''13''>
		<physicalName>fecha_terminacion</physicalName>
		<type>6</type>
	</field>
	<field id=''14''>
		<physicalName>ocupacion_via_publica</physicalName>
		<type>0</type>
		<size>2</size>
	</field>
	<field id=''15''>
		<physicalName>id</physicalName>
		<descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion>
		<type>3</type>
	</field>
	<field id=''16''>
		<physicalName>numexp</physicalName>
		<descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion>
		<type>0</type>
		<size>30</size>
	</field>
</fields>
<validations>
	<validation id=''1''>
		<fieldId>9</fieldId>
		<table>SPAC_TBL_012</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''2''>
		<fieldId>5</fieldId>
		<table>SPAC_TBL_011</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''3''>
		<fieldId>6</fieldId>
		<table>SPAC_TBL_010</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''4''>
		<fieldId>4</fieldId>
		<table>SPAC_TBL_009</table>
		<tableType>2</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''5''>
		<fieldId>14</fieldId>
		<table>SPAC_TBL_009</table>
		<tableType>2</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
</validations>
</entity>'
WHERE id = 200;

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'SGL_OBRAS_MENORES', 'Obra Menor');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'DESCRIPCION', 'Descripción Obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'TIPO_OBJETO', 'Tipo de Objeto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'TIPO_LOCALIZADOR', 'Tipo de Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'LOCALIZADOR', 'Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'UBICACION_INMUEBLE', 'Ubicación inmueble');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'REFERENCIA_CATASTRAL', 'Referencia catastral');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'LOCALIZACION', 'Localización obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'TIPO_FINCA', 'Tipo de finca');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'TIPO_SUELO', 'Tipo de suelo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'ACTUACION_CASCO_HISTORICO', 'Actuación dentro del Casco Histórico');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'OCUPACION_VIA_PUBLICA', 'Necesidad ocupar vía pública');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'PRESUPUESTO_TOTAL', 'Presupuesto total (sin IVA)');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'FECHA_INICIO', 'Fecha Inicio obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'es', 'FECHA_TERMINACION', 'Fecha Terminación');

--
-- Formulario
--
INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, ent_principal_id, ent_principal_nombre)
	VALUES (10, 'Obra Menor', 'Licencia para Obra Menor', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/SGL_OBRAS_MENORES/Obra_Menor.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_012</table><relation type=''FLD''><primary-field>LOCALIZACION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_011</table><relation type=''FLD''><primary-field>TIPO_FINCA</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_010</table><relation type=''FLD''><primary-field>TIPO_SUELO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, 200, 'SGL_OBRAS_MENORES');
UPDATE spac_ct_entidades SET frm_edit = 10 WHERE id = 200;

UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:SGL_OBRAS_MENORES)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SGL_OBRAS_MENORES" style="position: relative; height: 320px; width: 600px;">
<div id="label_SGL_OBRAS_MENORES:TIPO_OBJETO" style="position: absolute; top: 61px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:TIPO_OBJETO)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:TIPO_OBJETO" style="position: absolute; top: 58px; left: 150px;">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:TIPO_OBJETO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="25" maxlength="15" tabindex="102">
</ispac:htmlText>
</div>
<div id="label_SGL_OBRAS_MENORES:TIPO_LOCALIZADOR" style="position: absolute; top: 61px; left: 310px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:TIPO_LOCALIZADOR)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:TIPO_LOCALIZADOR" style="position: absolute; top: 58px; left: 440px;">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:TIPO_LOCALIZADOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="25" maxlength="15" tabindex="103">
</ispac:htmlText>
</div>
<div id="label_SGL_OBRAS_MENORES:LOCALIZADOR" style="position: absolute; top: 92px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:LOCALIZADOR)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:LOCALIZADOR" style="position: absolute; top: 89px; left: 150px;">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:LOCALIZADOR)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="25" maxlength="15" tabindex="104">
</ispac:htmlText>
</div>
<div id="label_SGL_OBRAS_MENORES:ACTUACION_CASCO_HISTORICO" style="position: absolute; top: 222px; left: 10px; width: 130px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:ACTUACION_CASCO_HISTORICO)" />:
</div>
<div id="data_SGL_OBRAS_MENORES:ACTUACION_CASCO_HISTORICO" style="position: absolute; top: 219px; left: 150px;">
<nobr>
<ispac:htmlTextImageFrame property="property(SGL_OBRAS_MENORES:ACTUACION_CASCO_HISTORICO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="5" id="SEARCH_SGL_OBRAS_MENORES_ACTUACION_CASCO_HISTORICO" target="workframe" action="selectValue.do?entity=SPAC_TBL_009" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="110">
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_ACTUACION_CASCO_HISTORICO" id="property(SGL_OBRAS_MENORES:ACTUACION_CASCO_HISTORICO)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:TIPO_FINCA" style="position: absolute; top: 189px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:TIPO_FINCA)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:TIPO_FINCA" style="position: absolute; top: 186px; left: 150px;">
<html:hidden property="property(SGL_OBRAS_MENORES:TIPO_FINCA)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_FINCA_SPAC_TBL_011:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="18" id="SEARCH_SGL_OBRAS_MENORES_TIPO_FINCA" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_011" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="108">
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_TIPO_FINCA" id="property(SGL_OBRAS_MENORES:TIPO_FINCA)" property="VALOR" />
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_TIPO_FINCA" id="property(TIPO_FINCA_SPAC_TBL_011:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:TIPO_SUELO" style="position: absolute; top: 189px; left: 310px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:TIPO_SUELO)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:TIPO_SUELO" style="position: absolute; top: 186px; left: 440px;">
<html:hidden property="property(SGL_OBRAS_MENORES:TIPO_SUELO)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_SUELO_SPAC_TBL_010:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="18" id="SEARCH_SGL_OBRAS_MENORES_TIPO_SUELO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_010" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="109">
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_TIPO_SUELO" id="property(SGL_OBRAS_MENORES:TIPO_SUELO)" property="VALOR" />
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_TIPO_SUELO" id="property(TIPO_SUELO_SPAC_TBL_010:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:UBICACION_INMUEBLE" style="position: absolute; top: 123px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:UBICACION_INMUEBLE)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:UBICACION_INMUEBLE" style="position: absolute; top: 120px; left: 150px;">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:UBICACION_INMUEBLE)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="83" maxlength="512" tabindex="105">
</ispac:htmlText>
</div>
<div id="label_SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL" style="position: absolute; top: 155px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL" style="position: absolute; top: 152px; left: 150px;">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:REFERENCIA_CATASTRAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="25" maxlength="20" tabindex="106">
</ispac:htmlText>
</div>
<div id="label_SGL_OBRAS_MENORES:LOCALIZACION" style="position: absolute; top: 155px; left: 310px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:LOCALIZACION)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:LOCALIZACION" style="position: absolute; top: 152px; left: 440px;">
<html:hidden property="property(SGL_OBRAS_MENORES:LOCALIZACION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(LOCALIZACION_SPAC_TBL_012:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="18" id="SEARCH_SGL_OBRAS_MENORES_LOCALIZACION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_012" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="107">
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_LOCALIZACION" id="property(SGL_OBRAS_MENORES:LOCALIZACION)" property="VALOR" />
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_LOCALIZACION" id="property(LOCALIZACION_SPAC_TBL_012:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:DESCRIPCION" style="position: absolute; top: 20px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:DESCRIPCION)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:DESCRIPCION" style="position: absolute; top: 17px; left: 150px;">
<ispac:htmlTextarea property="property(SGL_OBRAS_MENORES:DESCRIPCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="82" tabindex="101">
</ispac:htmlTextarea>
</div>
<div id="label_SGL_OBRAS_MENORES:PRESUPUESTO_TOTAL" style="position: absolute; top: 254px; left: 10px; width: 120px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:PRESUPUESTO_TOTAL)" />:
</div>
<div id="data_SGL_OBRAS_MENORES:PRESUPUESTO_TOTAL" style="position: absolute; top: 251px; left: 150px;" class="formsTitleB">
<ispac:htmlText property="property(SGL_OBRAS_MENORES:PRESUPUESTO_TOTAL)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="18" maxlength="15" tabindex="112">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGL_OBRAS_MENORES:FECHA_INICIO" style="position: absolute; top: 289px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:FECHA_INICIO)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:FECHA_INICIO" style="position: absolute; top: 286px; left: 150px;">
<nobr>
<ispac:htmlTextCalendar property="property(SGL_OBRAS_MENORES:FECHA_INICIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="113">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:FECHA_TERMINACION" style="position: absolute; top: 289px; left: 310px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:FECHA_TERMINACION)" />:</nobr>
</div>
<div id="data_SGL_OBRAS_MENORES:FECHA_TERMINACION" style="position: absolute; top: 286px; left: 440px;">
<nobr>
<ispac:htmlTextCalendar property="property(SGL_OBRAS_MENORES:FECHA_TERMINACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="114">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SGL_OBRAS_MENORES:OCUPACION_VIA_PUBLICA" style="position: absolute; top: 222px; left: 310px; width: 130px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SGL_OBRAS_MENORES:OCUPACION_VIA_PUBLICA)" />:
</div>
<div id="data_SGL_OBRAS_MENORES:OCUPACION_VIA_PUBLICA" style="position: absolute; top: 219px; left: 440px;">
<nobr>
<ispac:htmlTextImageFrame property="property(SGL_OBRAS_MENORES:OCUPACION_VIA_PUBLICA)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="5" id="SEARCH_SGL_OBRAS_MENORES_OCUPACION_VIA_PUBLICA" target="workframe" action="selectValue.do?entity=SPAC_TBL_009" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="111">
<ispac:parameter name="SEARCH_SGL_OBRAS_MENORES_OCUPACION_VIA_PUBLICA" id="property(SGL_OBRAS_MENORES:OCUPACION_VIA_PUBLICA)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
, frm_version = 1
WHERE id = 10;

--
-- Reglas
--

INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (NEXTVAL FOR SPAC_SQ_ID_CTREGLAS, 'InitObraMenorRule', 'Crea e inicializa los datos de una Obra Menor', 'ieci.tdw.ispac.api.rule.procedures.obras.InitObraMenorRule', 1);

--
-- Procedimiento
--
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt)
	VALUES (5, 'PCD-5', 'Obras menores', 1, 'Obras menores', NULL, 'Obras menores', NULL, NULL, NULL, NULL, NULL, 'INT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Documento de Identificación Personal
Descripción de la obra
Resguardo del pago de tasa', NULL, '04', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
	<table name="SGL_OBRAS_MENORES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="UBICACION_INMUEBLE" value="${xpath:/datos_especificos/ubicacion_inmueble}"/>
			<field name="DESCRIPCION" value="${xpath:/datos_especificos/descripcion_obras}"/>
		</mappings>
	</table>
</procedure>');

INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, id_group, ts_crt, ts_upd)
	VALUES (5, '5', 1, 'Obras menores', 2, 5, current date, current date);

--
-- Nodos
--
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (14, '14', 5, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (15, '15', 5, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (16, '16', 5, 1, NULL);

--
-- Fases
--
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre) VALUES (14, 1, 5, 'Fase Inicio');
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre) VALUES (15, 2, 5, 'Fase Instrucción');
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre) VALUES (16, 3, 5, 'Fase Terminación');

--
-- Flujos
--
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (18, '18', 5, 14, 15);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (19, '19', 5, 15, 16);

--
-- Trámites
--
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (27, '27', 7, 5, 15, 'Emisión informe', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (32, '32', 3, 5, 16, 'Archivo del expediente', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (33, '33', 14, 5, 14, 'Solicitud subsanación', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (34, '34', 12, 5, 15, 'Propuesta de resolución', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (35, '35', 10, 5, 15, 'Notificación', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (36, '36', 2, 5, 15, 'Alegaciones', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (37, '37', 16, 5, 16, 'Resolución expediente', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (38, '38', 17, 5, 16, 'Notificación resolución', 0, NULL, NULL, null);

--
-- Eventos
--
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla) SELECT 5, 1, 32, 5, ID FROM SPAC_CT_REGLAS WHERE NOMBRE = 'InitObraMenorRule';

--
-- Entidades
--
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (19, 1, 5, 0, 1, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (20, 7, 5, 1, 3, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (21, 3, 5, 1, 4, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (22, 2, 5, 1, 23, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (23, 200, 5, 0, 2, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (26, 8, 5, 1, 5, NULL);

--
-- Permisos
--
--INSERT INTO spac_ss_permisos (id, id_pcd, uid_usr, permiso) VALUES (5, 5, '2-1', 1);


------------
-- QUEJAS --
------------

--
-- Entidad
--
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha)
	VALUES (201, 1, 'SGQ_QUEJAS', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Datos específicos', 'SGQ_SQ_ID_QUEJAS', NULL, NULL, current timestamp);

UPDATE spac_ct_entidades SET definicion ='<entity version=''1.00''>
<editable>S</editable>
<dropable>S</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>id_organo_objeto</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''2''>
		<physicalName>organo_objeto</physicalName>
		<type>0</type>
		<size>512</size>
	</field>
	<field id=''3''>
		<physicalName>tipo_objeto</physicalName>
		<type>0</type>
		<size>10</size>
	</field>
	<field id=''4''>
		<physicalName>localizador_objeto</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''5''>
		<physicalName>asunto</physicalName>
		<type>1</type>
		<size>0</size>
	</field>
	<field id=''6''>
		<physicalName>tipo_organo_destino</physicalName>
		<type>0</type>
		<size>8</size>
	</field>
	<field id=''7''>
		<physicalName>id_organo_informe</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''8''>
		<physicalName>organo_informe</physicalName>
		<type>0</type>
		<size>512</size>
	</field>
	<field id=''9''>
		<physicalName>id</physicalName>
		<descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion>
		<type>3</type>
	</field>
	<field id=''10''>
		<physicalName>numexp</physicalName>
		<descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion>
		<type>0</type>
		<size>30</size>
	</field>
</fields>
</entity>'
WHERE id = 201;

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'es', 'SGQ_QUEJAS', 'Datos específicos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'es', 'ASUNTO', 'Texto de la Reclamación, Queja o Sugerencia');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'es', 'ID_ORGANO_OBJETO', 'Órgano Destinatario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'es', 'ORGANO_OBJETO', 'Órgano Destinatario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'es', 'TIPO_OBJETO', 'Tipo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'es', 'LOCALIZADOR_OBJETO', 'Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'es', 'TIPO_ORGANO_DESTINO', 'Tipo del Órgano de Destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'es', 'ID_ORGANO_INFORME', 'Id. de Órgano del Informe');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'es', 'ORGANO_INFORME', 'Órgano del Informe');

--
-- Formulario
--
INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, ent_principal_id, ent_principal_nombre)
	VALUES (11, 'Queja', 'Reclamación, Queja o Sugerencia', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/SGQ_QUEJAS/Queja.jsp', NULL, NULL, 201, 'SGQ_QUEJAS');
UPDATE spac_ct_entidades SET frm_edit = 11 WHERE id = 201;

UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGQ_QUEJAS:SGQ_QUEJAS)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SGQ_QUEJAS" style="position: relative; height: 235px; width: 600px;">
<div id="label_SGQ_QUEJAS:ID_ORGANO_OBJETO" style="position: absolute; top: 203px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGQ_QUEJAS:ID_ORGANO_OBJETO)" />:</nobr>
</div>
<div id="data_SGQ_QUEJAS:ID_ORGANO_OBJETO" style="position: absolute; top: 200px; left: 150px;">
<html:hidden property="property(SGQ_QUEJAS:ID_ORGANO_OBJETO)"/>
<nobr>
<ispac:htmlTextImageFrame property="property(SGQ_QUEJAS:ORGANO_OBJETO)"
			  readonly="true"
			  readonlyTag="false"
			  propertyReadonly="readonly"
			  styleClass="input"
			  styleClassReadonly="inputReadOnly"
			  size="46"
			  id="SELECT_SGQ_QUEJAS_ORGANO_OBJETO"
			  target="workframe"
			  action="viewUsersManager.do?captionkey=app.selectOrg.seleccionar&noviewusers=true&noviewgroups=true"
			  image="img/search-mg.gif"
			  showFrame="true" tabindex="102">
	<ispac:parameter name="SELECT_SGQ_QUEJAS_ORGANO_OBJETO" id="property(SGQ_QUEJAS:ID_ORGANO_OBJETO)" property="UID"/>
	<ispac:parameter name="SELECT_SGQ_QUEJAS_ORGANO_OBJETO" id="property(SGQ_QUEJAS:ORGANO_OBJETO)" property="NAME"/>
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGQ_QUEJAS:ASUNTO" style="position: absolute; top: 20px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGQ_QUEJAS:ASUNTO)" />:</nobr>
</div>
<div id="data_SGQ_QUEJAS:ASUNTO" style="position: absolute; top: 37px; left: 11px;">
<ispac:htmlTextarea property="property(SGQ_QUEJAS:ASUNTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="12" cols="80" tabindex="101">
</ispac:htmlTextarea>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
, frm_version = 1
WHERE id = 11;

--
-- Reglas
--
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (NEXTVAL FOR SPAC_SQ_ID_CTREGLAS, 'InitQuejaRule', 'Crea e inicializa los datos de una Queja, Reclamación o Sugerencia', 'ieci.tdw.ispac.api.rule.procedures.quejas.InitQuejaRule', 1);

--
-- Procedimiento
--
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt)
	VALUES (3, 'PCD-3', 'Reclamaciones, quejas y sugerencias', 1, 'Reclamaciones, quejas y sugerencias', NULL, 'Reclamaciones, quejas y sugerencias', NULL, NULL, NULL, NULL, NULL, 'INT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Documento de Identificación Personal
Descripción de la reclamación, queja o sugerencia
', NULL, '04', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
	<table name="SGQ_QUEJAS">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="ASUNTO" value="${xpath:/datos_especificos/asunto_queja}"/>
			<field name="ID_ORGANO_OBJETO" value="${xpath:/datos_especificos/cod_organo}"/>
			<field name="ORGANO_OBJETO" value="${xpath:/datos_especificos/descr_organo}"/>
		</mappings>
	</table>
</procedure>');

INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, id_group, ts_crt, ts_upd)
	VALUES (3, '3', 1, 'Reclamaciones, quejas y sugerencias', 2, 3, current date, current date);

--
-- Nodos
--
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (6, '6', 3, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (7, '7', 3, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (8, '8', 3, 1, NULL);

--
-- Fases
--
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre) VALUES (6, 1, 3, 'Fase Inicio');
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre) VALUES (7, 2, 3, 'Fase Instrucción');
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre) VALUES (8, 3, 3, 'Fase Terminación');

--
-- Flujos
--
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (8, '8', 3, 6, 7);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (9, '9', 3, 7, 8);

--
-- Trámites
--
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (5, '5', 1, 3, 6, 'Admisión a trámite', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (6, '6', 5, 3, 6, 'Comunicación de apertura', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (7, '7', 8, 3, 7, 'Emisión oficio de respuesta', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (8, '8', 11, 3, 7, 'Notificación oficio de respuesta', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (9, '9', 7, 3, 7, 'Emisión informe', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (10, '10', 13, 3, 8, 'Remisión documentación', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (11, '11', 4, 3, 8, 'Comunicación al interesado', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (12, '12', 9, 3, 8, 'Emisión oficio no admisión', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (13, '13', 10, 3, 8, 'Notificación', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (14, '14', 3, 3, 8, 'Archivo del expediente', 0, NULL, NULL, null);

--
-- Eventos
--
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla) SELECT 3, 1, 32, 7, ID FROM SPAC_CT_REGLAS WHERE NOMBRE = 'InitQuejaRule';




--
-- Entidades
--
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (11, 1, 3, 0, 1, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (12, 7, 3, 1, 3, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (13, 3, 3, 1, 4, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (14, 2, 3, 1, 25, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (25, 201, 3, 0, 2, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (27, 8, 3, 1, 5, NULL);

--
-- Permisos
--
--INSERT INTO spac_ss_permisos (id, id_pcd, uid_usr, permiso) VALUES (3, 3, '2-1', 1);


INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (140, 3, 'SPAC_TBL_013', 'ID', NULL, NULL, NULL, 'Tabla de validación para Tipos de Subvención', 'SPAC_SQ_SPAC_TBL_013', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 140, 'es', 'SPAC_TBL_013', 'Tipos de Subvención');
INSERT INTO spac_tbl_013 (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_spac_tbl_013, 'INV', 'Investigación', 1, 1);
INSERT INTO spac_tbl_013 (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_spac_tbl_013, 'TIC', 'Innovación Tecnológica', 1, 2);
INSERT INTO spac_tbl_013 (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_spac_tbl_013, 'PRO', 'Actividad Promocional', 1, 3);
INSERT INTO spac_tbl_013 (id, valor, sustituto, vigente, orden) VALUES (NEXTVAL FOR spac_sq_spac_tbl_013, 'OBR', 'Obras Menores', 1, 4);

--
-- Entidad
--
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion, frm_jsp, fecha)
	VALUES (202, 1, 'SGS_SUBVENCIONES', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Subvención', 'SGS_SQ_ID_SUBVENCIONES', NULL, NULL, current timestamp);

UPDATE spac_ct_entidades SET definicion ='<entity version=''1.00''>
<editable>S</editable>
<dropable>S</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>id_pcd</physicalName>
		<type>3</type>
	</field>
	<field id=''2''>
		<physicalName>anio_presupuestario</physicalName>
		<type>2</type>
		<size>4</size>
	</field>
	<field id=''3''>
		<physicalName>tipo_subvencion</physicalName>
		<type>0</type>
		<size>8</size>
	</field>
	<field id=''4''>
		<physicalName>fecha_convocatoria</physicalName>
		<type>6</type>
	</field>
	<field id=''5''>
		<physicalName>denominacion_proyecto</physicalName>
		<type>0</type>
		<size>10</size>
	</field>
	<field id=''6''>
		<physicalName>resumen_proyecto</physicalName>
		<type>1</type>
	</field>
	<field id=''7''>
		<physicalName>fecha_inicio_ejecucion</physicalName>
		<type>6</type>
	</field>
	<field id=''8''>
		<physicalName>fecha_fin_ejecucion</physicalName>
		<type>6</type>
	</field>
	<field id=''9''>
		<physicalName>duracion_proyecto</physicalName>
		<type>2</type>
		<size>2</size>
	</field>
	<field id=''10''>
		<physicalName>colectivo_destino</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''11''>
		<physicalName>importe_solicitado</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''12''>
		<physicalName>importe_concedido</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''13''>
		<physicalName>prevision_gastos</physicalName>
		<type>1</type>
	</field>
	<field id=''14''>
		<physicalName>importe_gastos</physicalName>
		<type>4</type>
		<size>10</size>
		<precision>2</precision>
	</field>
	<field id=''15''>
		<physicalName>total_gastos</physicalName>
		<type>4</type>
		<size>10</size>
		<precision>2</precision>
	</field>
	<field id=''16''>
		<physicalName>aportacion_ayto</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''17''>
		<physicalName>aportacion_entidad</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''18''>
		<physicalName>otras_aportaciones</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''19''>
		<physicalName>total_aportaciones</physicalName>
		<type>4</type>
		<size>13</size>
		<precision>2</precision>
	</field>
	<field id=''20''>
		<physicalName>finalidad_subvencion</physicalName>
		<type>1</type>
	</field>
	<field id=''21''>
		<physicalName>observaciones</physicalName>
		<type>1</type>
	</field>
	<field id=''22''>
		<physicalName>id</physicalName>
		<descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion>
		<type>3</type>
	</field>
	<field id=''23''>
		<physicalName>numexp</physicalName>
		<descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion>
		<type>0</type>
		<size>30</size>
	</field>
</fields>
<validations>
	<validation id=''1''>
		<fieldId>3</fieldId>
		<table>SPAC_TBL_013</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
</validations>
</entity>'
WHERE id = 202;

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'SGS_SUBVENCIONES', 'Subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'ANIO_PRESUPUESTARIO', 'Año presupuestario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'FECHA_CONVOCATORIA', 'Fecha de convocatoria');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'TIPO_SUBVENCION', 'Tipo de subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'COLECTIVO_DESTINO', 'Colectivo destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'DENOMINACION_PROYECTO', 'Denominación del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'RESUMEN_PROYECTO', 'Resumen del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'FECHA_INICIO_EJECUCION', 'Fecha inicio de ejecución');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'FECHA_FIN_EJECUCION', 'Fecha fin de ejecución');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'DURACION_PROYECTO', 'Duración del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'IMPORTE_SOLICITADO', 'Importe solicitado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'IMPORTE_CONCEDIDO', 'Importe concedido');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'PREVISION_GASTOS', 'Gastos previstos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'IMPORTE_GASTOS', 'Importe gastos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'APORTACION_AYTO', 'Aportación ayuntamiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'APORTACION_ENTIDAD', 'Aportación entidad solicitante');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'OTRAS_APORTACIONES', 'Otras aportaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'TOTAL_APORTACIONES', 'Total aportacion');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'FINALIDAD_SUBVENCION', 'Finalidad subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'OBSERVACIONES', 'Observaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'ID_PCD', 'Id. de Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'es', 'TOTAL_GASTOS', 'Total gastos');

--
-- Formulario
--
INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, ent_principal_id, ent_principal_nombre)
	VALUES (12, 'Subvención', 'Subvención', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/SGS_SUBVENCIONES/Subvención.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_013</table><relation type=''FLD''><primary-field>TIPO_SUBVENCION</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, 202, 'SGS_SUBVENCIONES');
UPDATE spac_ct_entidades SET frm_edit = 12 WHERE id = 202;

UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:SGS_SUBVENCIONES)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SGS_SUBVENCIONES" style="position: relative; height: 550px; width: 640px;">
  <div id="label_SGS_SUBVENCIONES:ANIO_PRESUPUESTARIO" style="position: absolute; top: 23px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:ANIO_PRESUPUESTARIO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:ANIO_PRESUPUESTARIO" style="position: absolute; top: 20px; left: 190px;">
<ispac:htmlText property="property(SGS_SUBVENCIONES:ANIO_PRESUPUESTARIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="4" tabindex="101">
</ispac:htmlText>
</div>
<div id="label_SGS_SUBVENCIONES:TIPO_SUBVENCION" style="position: absolute; top: 53px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:TIPO_SUBVENCION)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:TIPO_SUBVENCION" style="position: absolute; top: 50px; left: 190px;">
<html:hidden property="property(SGS_SUBVENCIONES:TIPO_SUBVENCION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_SUBVENCION_SPAC_TBL_013:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SGS_SUBVENCIONES_TIPO_SUBVENCION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_013" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="103">
<ispac:parameter name="SEARCH_SGS_SUBVENCIONES_TIPO_SUBVENCION" id="property(SGS_SUBVENCIONES:TIPO_SUBVENCION)" property="VALOR" />
<ispac:parameter name="SEARCH_SGS_SUBVENCIONES_TIPO_SUBVENCION" id="property(TIPO_SUBVENCION_SPAC_TBL_013:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SGS_SUBVENCIONES:FECHA_CONVOCATORIA" style="position: absolute; top: 23px; left: 352px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:FECHA_CONVOCATORIA)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:FECHA_CONVOCATORIA" style="position: absolute; top: 20px; left: 490px;">
<nobr>
<ispac:htmlTextCalendar property="property(SGS_SUBVENCIONES:FECHA_CONVOCATORIA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="102">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SGS_SUBVENCIONES:DENOMINACION_PROYECTO" style="position: absolute; top: 113px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:DENOMINACION_PROYECTO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:DENOMINACION_PROYECTO" style="position: absolute; top: 110px; left: 190px;">
<ispac:htmlText property="property(SGS_SUBVENCIONES:DENOMINACION_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="120" tabindex="105">
</ispac:htmlText>
</div>
<div id="label_SGS_SUBVENCIONES:RESUMEN_PROYECTO" style="position: absolute; top: 143px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:RESUMEN_PROYECTO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:RESUMEN_PROYECTO" style="position: absolute; top: 140px; left: 190px;">
<ispac:htmlTextarea property="property(SGS_SUBVENCIONES:RESUMEN_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="106">
</ispac:htmlTextarea>
</div>
<div id="label_SGS_SUBVENCIONES:FECHA_INICIO_EJECUCION" style="position: absolute; top: 183px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:FECHA_INICIO_EJECUCION)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:FECHA_INICIO_EJECUCION" style="position: absolute; top: 180px; left: 190px;">
<nobr>
<ispac:htmlTextCalendar property="property(SGS_SUBVENCIONES:FECHA_INICIO_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="107">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SGS_SUBVENCIONES:FECHA_FIN_EJECUCION" style="position: absolute; top: 183px; left: 354px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:FECHA_FIN_EJECUCION)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:FECHA_FIN_EJECUCION" style="position: absolute; top: 180px; left: 490px;">
<nobr>
<ispac:htmlTextCalendar property="property(SGS_SUBVENCIONES:FECHA_FIN_EJECUCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="108">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SGS_SUBVENCIONES:DURACION_PROYECTO" style="position: absolute; top: 213px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:DURACION_PROYECTO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:DURACION_PROYECTO" style="position: absolute; top: 210px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:DURACION_PROYECTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="5" maxlength="2" tabindex="109">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.months"/>
</div>
<div id="label_SGS_SUBVENCIONES:COLECTIVO_DESTINO" style="position: absolute; top: 83px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:COLECTIVO_DESTINO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:COLECTIVO_DESTINO" style="position: absolute; top: 80px; left: 190px;">
<ispac:htmlText property="property(SGS_SUBVENCIONES:COLECTIVO_DESTINO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="64" tabindex="104">
</ispac:htmlText>
</div>
<div id="label_SGS_SUBVENCIONES:IMPORTE_SOLICITADO" style="position: absolute; top: 243px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:IMPORTE_SOLICITADO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:IMPORTE_SOLICITADO" style="position: absolute; top: 240px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:IMPORTE_SOLICITADO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="17" tabindex="110">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:IMPORTE_CONCEDIDO" style="position: absolute; top: 243px; left: 372px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:IMPORTE_CONCEDIDO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:IMPORTE_CONCEDIDO" style="position: absolute; top: 240px; left: 490px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:IMPORTE_CONCEDIDO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="17" tabindex="111">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:PREVISION_GASTOS" style="position: absolute; top: 273px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:PREVISION_GASTOS)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:PREVISION_GASTOS" style="position: absolute; top: 270px; left: 190px;">
<ispac:htmlTextarea property="property(SGS_SUBVENCIONES:PREVISION_GASTOS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="112">
</ispac:htmlTextarea>
</div>
<div id="label_SGS_SUBVENCIONES:IMPORTE_GASTOS" style="position: absolute; top: 313px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:IMPORTE_GASTOS)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:IMPORTE_GASTOS" style="position: absolute; top: 310px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:IMPORTE_GASTOS)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="13" tabindex="113">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:APORTACION_AYTO" style="position: absolute; top: 343px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:APORTACION_AYTO)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:APORTACION_AYTO" style="position: absolute; top: 340px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:APORTACION_AYTO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="17" tabindex="114">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:APORTACION_ENTIDAD" style="position: absolute; top: 373px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:APORTACION_ENTIDAD)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:APORTACION_ENTIDAD" style="position: absolute; top: 370px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:APORTACION_ENTIDAD)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="17" tabindex="115">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:OTRAS_APORTACIONES" style="position: absolute; top: 403px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:OTRAS_APORTACIONES)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:OTRAS_APORTACIONES" style="position: absolute; top: 400px; left: 190px;" class="formsTitleB">
<ispac:htmlText property="property(SGS_SUBVENCIONES:OTRAS_APORTACIONES)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="15" maxlength="17" tabindex="116">
</ispac:htmlText>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:TOTAL_APORTACIONES" style="position: absolute; top: 433px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:TOTAL_APORTACIONES)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:TOTAL_APORTACIONES" style="position: absolute; top: 430px; left: 190px;" class="formsTitleB">
<logic:empty name="defaultForm" property="property(SGS_SUBVENCIONES:TOTAL_APORTACIONES)">0</logic:empty>
<logic:notEmpty name="defaultForm" property="property(SGS_SUBVENCIONES:TOTAL_APORTACIONES)">
	<bean:write name="defaultForm" property="property(SGS_SUBVENCIONES:TOTAL_APORTACIONES)" />
</logic:notEmpty>
&nbsp;<bean:message key="common.message.currency"/>
</div>
<div id="label_SGS_SUBVENCIONES:FINALIDAD_SUBVENCION" style="position: absolute; top: 463px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:FINALIDAD_SUBVENCION)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:FINALIDAD_SUBVENCION" style="position: absolute; top: 460px; left: 190px;">
<ispac:htmlTextarea property="property(SGS_SUBVENCIONES:FINALIDAD_SUBVENCION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="117">
</ispac:htmlTextarea>
</div>
<div id="label_SGS_SUBVENCIONES:OBSERVACIONES" style="position: absolute; top: 503px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SGS_SUBVENCIONES:OBSERVACIONES)" />:</nobr>
</div>
<div id="data_SGS_SUBVENCIONES:OBSERVACIONES" style="position: absolute; top: 500px; left: 190px;">
<ispac:htmlTextarea property="property(SGS_SUBVENCIONES:OBSERVACIONES)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" tabindex="118">
</ispac:htmlTextarea>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
, frm_version = 1
WHERE id = 12;

--
-- Reglas
--
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (NEXTVAL FOR SPAC_SQ_ID_CTREGLAS, 'InitSubvencionRule', 'Crea e inicializa los datos de una Subvención', 'ieci.tdw.ispac.api.rule.procedures.subvenciones.InitSubvencionRule', 1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (NEXTVAL FOR SPAC_SQ_ID_CTREGLAS, 'StoreSubventionRule', 'Calcula el total de una Subvención cuando se guarda', 'ieci.tdw.ispac.api.rule.procedures.subvenciones.StoreSubventionRule', 1);

--
-- Procedimiento
--
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt)
	VALUES (4, 'PCD-4', 'Concesión de subvención', 1, 'Concesión de subvención', NULL, 'Concesión de subvención
', NULL, NULL, NULL, NULL, NULL, 'INT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Documento de Identificación Personal
Instancia de solicitud', NULL, '04', '<?xml version="1.0" encoding="ISO-8859-1"?>
<procedure>
	<table name="SGS_SUBVENCIONES">
		<mappings>
			<!-- Mapeos de los datos específicos del expediente -->
			<field name="TIPO_SUBVENCION" value="${xpath:/datos_especificos/tipo_subvencion}"/>
			<field name="RESUMEN_PROYECTO" value="${xpath:/datos_especificos/resumen_proyecto}"/>
		</mappings>
	</table>
</procedure>');

INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, id_group, ts_crt, ts_upd)
VALUES (4, '4', 1, 'Concesión de subvención', 2, 4, current date, current date);

--
-- Nodos
--
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (10, '10', 4, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (11, '11', 4, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (12, '12', 4, 1, NULL);

--
-- Fases
--
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre) VALUES (10, 1, 4, 'Fase Inicio');
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre) VALUES (11, 2, 4, 'Fase Instrucción');
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre) VALUES (12, 3, 4, 'Fase Terminación');

--
-- Flujos
--
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (13, '13', 4, 10, 11);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (14, '14', 4, 11, 12);

--
-- Trámites
--
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (15, '15', 14, 4, 10, 'Solicitud subsanación', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (16, '16', 12, 4, 11, 'Propuesta de resolución', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (17, '17', 15, 4, 11, 'Acuerdo Consejo de Gobierno', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (18, '18', 10, 4, 11, 'Notificación', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (19, '19', 2, 4, 11, 'Alegaciones', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (20, '20', 6, 4, 12, 'Decreto de concesión', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (21, '21', 10, 4, 12, 'Notificación', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (22, '22', 3, 4, 12, 'Archivo del expediente', 0, NULL, NULL, null);

--
-- Eventos
--
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla) SELECT 4, 1, 32, 10, ID FROM SPAC_CT_REGLAS WHERE NOMBRE = 'InitSubvencionRule';
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla) SELECT 24, 5, 12, 12, ID FROM SPAC_CT_REGLAS WHERE NOMBRE = 'StoreSubventionRule';


--
-- Entidades
--
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (15, 1, 4, 0, 1, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (16, 7, 4, 1, 3, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (17, 3, 4, 1, 4, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (18, 2, 4, 1, 24, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (24, 202, 4, 0, 2, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (28, 8, 4, 1, 5, NULL);

--
-- Permisos
--
--INSERT INTO spac_ss_permisos (id, id_pcd, uid_usr, permiso) VALUES (4, 4, '2-1', 1);


---------------------------
-- ACTUALIZAR SECUENCIAS --
---------------------------

ALTER SEQUENCE spac_sq_id_ctentidades RESTART WITH 203;
ALTER SEQUENCE spac_sq_id_ctaplicaciones RESTART WITH 13;
ALTER SEQUENCE spac_sq_id_pprocedimientos RESTART WITH 6;
ALTER SEQUENCE spac_sq_id_pnodos RESTART WITH 17;
--ALTER SEQUENCE spac_sq_id_pfases RESTART WITH 17;
ALTER SEQUENCE spac_sq_id_pflujos RESTART WITH 20;
ALTER SEQUENCE spac_sq_id_ptramites RESTART WITH 39;
ALTER SEQUENCE spac_sq_id_peventos RESTART WITH 100;
ALTER SEQUENCE spac_sq_id_pentidades RESTART WITH 50;
ALTER SEQUENCE spac_sq_id_sspermisos RESTART WITH 6;



--
-- i18 en 'ca'
--

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 137, 'ca', 'SPAC_TBL_010', 'Tipos de Suelo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 138, 'ca', 'SPAC_TBL_011', 'Tipos de Finca');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 139, 'ca', 'SPAC_TBL_012', 'Localización de Obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 140, 'ca', 'SPAC_TBL_013', 'Tipos de Subvención');

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'SGL_OBRAS_MENORES', 'Obra Menor');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'DESCRIPCION', 'Descripción Obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'TIPO_OBJETO', 'Tipo de Objeto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'TIPO_LOCALIZADOR', 'Tipo de Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'LOCALIZADOR', 'Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'UBICACION_INMUEBLE', 'Ubicación inmueble');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'REFERENCIA_CATASTRAL', 'Referencia catastral');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'LOCALIZACION', 'Localización obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'TIPO_FINCA', 'Tipo de finca');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'TIPO_SUELO', 'Tipo de suelo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'ACTUACION_CASCO_HISTORICO', 'Actuación dentro del Casco Histórico');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'OCUPACION_VIA_PUBLICA', 'Necesidad ocupar vía pública');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'PRESUPUESTO_TOTAL', 'Presupuesto total (sin IVA)');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'FECHA_INICIO', 'Fecha Inicio obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'ca', 'FECHA_TERMINACION', 'Fecha Terminación');

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'ca', 'SGQ_QUEJAS', 'Datos específicos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'ca', 'ASUNTO', 'Texto de la Reclamación, Queja o Sugerencia');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'ca', 'ID_ORGANO_OBJETO', 'Órgano Destinatario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'ca', 'ORGANO_OBJETO', 'Órgano Destinatario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'ca', 'TIPO_OBJETO', 'Tipo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'ca', 'LOCALIZADOR_OBJETO', 'Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'ca', 'TIPO_ORGANO_DESTINO', 'Tipo del Órgano de Destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'ca', 'ID_ORGANO_INFORME', 'Id. de Órgano del Informe');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'ca', 'ORGANO_INFORME', 'Órgano del Informe');

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'SGS_SUBVENCIONES', 'Subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'ANIO_PRESUPUESTARIO', 'Año presupuestario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'FECHA_CONVOCATORIA', 'Fecha de convocatoria');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'TIPO_SUBVENCION', 'Tipo de subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'COLECTIVO_DESTINO', 'Colectivo destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'DENOMINACION_PROYECTO', 'Denominación del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'RESUMEN_PROYECTO', 'Resumen del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'FECHA_INICIO_EJECUCION', 'Fecha inicio de ejecución');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'FECHA_FIN_EJECUCION', 'Fecha fin de ejecución');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'DURACION_PROYECTO', 'Duración del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'IMPORTE_SOLICITADO', 'Importe solicitado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'IMPORTE_CONCEDIDO', 'Importe concedido');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'PREVISION_GASTOS', 'Gastos previstos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'IMPORTE_GASTOS', 'Importe gastos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'APORTACION_AYTO', 'Aportación ayuntamiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'APORTACION_ENTIDAD', 'Aportación entidad solicitante');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'OTRAS_APORTACIONES', 'Otras aportaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'TOTAL_APORTACIONES', 'Total aportacion');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'FINALIDAD_SUBVENCION', 'Finalidad subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'OBSERVACIONES', 'Observaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'ID_PCD', 'Id. de Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'ca', 'TOTAL_GASTOS', 'Total gastos');



--
-- i18 en 'eu'
--

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 137, 'eu', 'SPAC_TBL_010', 'Tipos de Suelo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 138, 'eu', 'SPAC_TBL_011', 'Tipos de Finca');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 139, 'eu', 'SPAC_TBL_012', 'Localización de Obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 140, 'eu', 'SPAC_TBL_013', 'Tipos de Subvención');

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'SGL_OBRAS_MENORES', 'Obra Menor');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'DESCRIPCION', 'Descripción Obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'TIPO_OBJETO', 'Tipo de Objeto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'TIPO_LOCALIZADOR', 'Tipo de Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'LOCALIZADOR', 'Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'UBICACION_INMUEBLE', 'Ubicación inmueble');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'REFERENCIA_CATASTRAL', 'Referencia catastral');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'LOCALIZACION', 'Localización obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'TIPO_FINCA', 'Tipo de finca');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'TIPO_SUELO', 'Tipo de suelo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'ACTUACION_CASCO_HISTORICO', 'Actuación dentro del Casco Histórico');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'OCUPACION_VIA_PUBLICA', 'Necesidad ocupar vía pública');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'PRESUPUESTO_TOTAL', 'Presupuesto total (sin IVA)');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'FECHA_INICIO', 'Fecha Inicio obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'eu', 'FECHA_TERMINACION', 'Fecha Terminación');

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'eu', 'SGQ_QUEJAS', 'Datos específicos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'eu', 'ASUNTO', 'Texto de la Reclamación, Queja o Sugerencia');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'eu', 'ID_ORGANO_OBJETO', 'Órgano Destinatario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'eu', 'ORGANO_OBJETO', 'Órgano Destinatario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'eu', 'TIPO_OBJETO', 'Tipo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'eu', 'LOCALIZADOR_OBJETO', 'Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'eu', 'TIPO_ORGANO_DESTINO', 'Tipo del Órgano de Destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'eu', 'ID_ORGANO_INFORME', 'Id. de Órgano del Informe');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'eu', 'ORGANO_INFORME', 'Órgano del Informe');

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'SGS_SUBVENCIONES', 'Subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'ANIO_PRESUPUESTARIO', 'Año presupuestario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'FECHA_CONVOCATORIA', 'Fecha de convocatoria');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'TIPO_SUBVENCION', 'Tipo de subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'COLECTIVO_DESTINO', 'Colectivo destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'DENOMINACION_PROYECTO', 'Denominación del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'RESUMEN_PROYECTO', 'Resumen del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'FECHA_INICIO_EJECUCION', 'Fecha inicio de ejecución');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'FECHA_FIN_EJECUCION', 'Fecha fin de ejecución');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'DURACION_PROYECTO', 'Duración del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'IMPORTE_SOLICITADO', 'Importe solicitado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'IMPORTE_CONCEDIDO', 'Importe concedido');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'PREVISION_GASTOS', 'Gastos previstos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'IMPORTE_GASTOS', 'Importe gastos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'APORTACION_AYTO', 'Aportación ayuntamiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'APORTACION_ENTIDAD', 'Aportación entidad solicitante');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'OTRAS_APORTACIONES', 'Otras aportaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'TOTAL_APORTACIONES', 'Total aportacion');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'FINALIDAD_SUBVENCION', 'Finalidad subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'OBSERVACIONES', 'Observaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'ID_PCD', 'Id. de Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'eu', 'TOTAL_GASTOS', 'Total gastos');



--
-- i18 en 'gl'
--

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 137, 'gl', 'SPAC_TBL_010', 'Tipos de Suelo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 138, 'gl', 'SPAC_TBL_011', 'Tipos de Finca');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 139, 'gl', 'SPAC_TBL_012', 'Localización de Obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 140, 'gl', 'SPAC_TBL_013', 'Tipos de Subvención');

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'SGL_OBRAS_MENORES', 'Obra Menor');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'DESCRIPCION', 'Descripción Obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'TIPO_OBJETO', 'Tipo de Objeto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'TIPO_LOCALIZADOR', 'Tipo de Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'LOCALIZADOR', 'Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'UBICACION_INMUEBLE', 'Ubicación inmueble');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'REFERENCIA_CATASTRAL', 'Referencia catastral');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'LOCALIZACION', 'Localización obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'TIPO_FINCA', 'Tipo de finca');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'TIPO_SUELO', 'Tipo de suelo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'ACTUACION_CASCO_HISTORICO', 'Actuación dentro del Casco Histórico');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'OCUPACION_VIA_PUBLICA', 'Necesidad ocupar vía pública');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'PRESUPUESTO_TOTAL', 'Presupuesto total (sin IVA)');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'FECHA_INICIO', 'Fecha Inicio obras');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 200, 'gl', 'FECHA_TERMINACION', 'Fecha Terminación');

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'gl', 'SGQ_QUEJAS', 'Datos específicos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'gl', 'ASUNTO', 'Texto de la Reclamación, Queja o Sugerencia');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'gl', 'ID_ORGANO_OBJETO', 'Órgano Destinatario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'gl', 'ORGANO_OBJETO', 'Órgano Destinatario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'gl', 'TIPO_OBJETO', 'Tipo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'gl', 'LOCALIZADOR_OBJETO', 'Localizador');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'gl', 'TIPO_ORGANO_DESTINO', 'Tipo del Órgano de Destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'gl', 'ID_ORGANO_INFORME', 'Id. de Órgano del Informe');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 201, 'gl', 'ORGANO_INFORME', 'Órgano del Informe');

INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'SGS_SUBVENCIONES', 'Subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'ANIO_PRESUPUESTARIO', 'Año presupuestario');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'FECHA_CONVOCATORIA', 'Fecha de convocatoria');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'TIPO_SUBVENCION', 'Tipo de subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'COLECTIVO_DESTINO', 'Colectivo destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'DENOMINACION_PROYECTO', 'Denominación del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'RESUMEN_PROYECTO', 'Resumen del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'FECHA_INICIO_EJECUCION', 'Fecha inicio de ejecución');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'FECHA_FIN_EJECUCION', 'Fecha fin de ejecución');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'DURACION_PROYECTO', 'Duración del proyecto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'IMPORTE_SOLICITADO', 'Importe solicitado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'IMPORTE_CONCEDIDO', 'Importe concedido');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'PREVISION_GASTOS', 'Gastos previstos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'IMPORTE_GASTOS', 'Importe gastos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'APORTACION_AYTO', 'Aportación ayuntamiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'APORTACION_ENTIDAD', 'Aportación entidad solicitante');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'OTRAS_APORTACIONES', 'Otras aportaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'TOTAL_APORTACIONES', 'Total aportacion');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'FINALIDAD_SUBVENCION', 'Finalidad subvención');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'OBSERVACIONES', 'Observaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'ID_PCD', 'Id. de Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (NEXTVAL FOR spac_sq_id_entidadesresources, 202, 'gl', 'TOTAL_GASTOS', 'Total gastos');


---
--- Ayudas de los procedimientos
---

INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (NEXTVAL FOR spac_sq_id_ayudas, 'Procedimiento de Reclamaciones, quejas y sugerencias', 3, 3, NULL, '');
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FORMULARIO EXPEDIENTE</h4></div><p>La pantalla que se muestra es la del &#147;expediente abierto&#148;, es decir, es como si se abriera una carpeta física con un expediente en formato papel y tuviera unos separadores por: <b>Expediente, Datos específicos, Participantes y Documentos</b>.</p><p><ul><li><b>Expediente</b>: Se muestran los datos principales del expediente, en la zona superior, y del Interesado principal, en la zona inferior.</li><p><li><b>Datos específicos</b>:se muestran los datos particulares del procedimiento que se está tramitando.</li><p><li><b>Participantes</b>: se muestra el formulario para dar de alta o consultar los datos de las personas relacionadas con el Expediente.</li><p><li><b>Documentos</b>: consta de dos zonas, la superior donde se ven, en este caso los documentos con todos sus campos y se podrá acceder a abrir su imagen y la parte inferior en donde se muestran en formato tabla, todos los documentos del expediente.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<p>Los documentos se añaden al expediente, generalmente, desde los trámites de cada fase, pero hay expedientes que se diseñan de manera que se le pueden añadir documentos desde esta pestaña de Documentos.</p><p>Para ver todos los campos de información de un documento y sus imágenes, se seleccionará de la lista inferior, pulsando sobre su nombre. Se comprobará que sus datos se muestran en la parte superior. Seleccionando el botón <b>Ver documento</b> (situado debajo de las pestañas), se abrirá el documento.</p></li></ul><p>Desde el menú de la izquierda de la pantalla se podrán realizar las siguientes acciones:</p><p><ul><li>Pulsando el botón <b>Acciones</b> se podrá <b>Delegar fase</b> o <b>Clonar expediente</b>.</li><p><li>Crear un <b>Nuevo trámite</b> pulsando el botón del mismo nombre.</li><p><li>Pulsar el botón <b>Avanzar fase</b> para que el expediente que hay en pantalla avance de fase.</li><p><li>Pulsar <b> Trámites</b>  para visualizar los trámites por los que ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'ha pasado el expediente.</li><p><li>Pulsar <b>Expedientes relacionados</b> para visualizar los expedientes relacionados.</li></ul><div class="titulo_ayuda"><label class="popUpInfo" >Información del trámite</label></div><p>Debajo de la etiqueta <b>Trámite/Documento</b>, hay una línea informativa en donde se visualiza: el nombre del trámite actual y la fecha interna de iniciación.<p>Debajo de esa línea se encuentran los siguientes campos del Trámite:<p><ul><li><b>Departamento responsable</b>:campo que rellena la aplicación con el valor del departamento que realiza el trámite</li><p><li><b>Tramitador responsable</b>:campo que rellena la aplicación, con el nombre del usuario que lo realiza</li><p><li><b>Fecha inicio plazo</b>:campo que rellenará el usuario, seleccionando una fecha desde el  icono calendario</li><p><li><b>Plazo</b>:un número de unidades: días, meses, años para el plazo</li><p><li><b>Unidades de Plazo</b>:se seleccionará el icono Lupa y se mostrarán en una ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'nueva ventana, las distintas unidades de plazo existentes, de las cuales se seleccionará el valor correspondiente</li><p><li><b>Fecha Alarma</b>:este campo lo cumplimenta la aplicación y será el que se utilice para avisar al usuario tramitador, que le vence un plazo</li></ul><p>En la parte inferior de la pantalla,bajo la etiqueta: <B>DOCUMENTOS ADJUNTOS</b>, se encuentra la zona de documentos del trámite, en donde se visualizarán los documentos del trámite si los hubiera.<p>Desde esta pantalla se podrán realizar las siguientes acciones para <b>Generar documentos</b>:<p><ul><li>Pulsar <b>Desde plantilla</b> para seleccionar una plantilla de la lista de plantillas asociadas al trámite<p>El usuario pulsará sobre la que corresponda y se mostrará el documento normalizado, con los datos del expediente incluidos en él<p>Este documento, a no ser que se defina en el trámite de otra manera, es modificable, por si se quiere complementar con algún dato, cambiar una expresión, etc.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'</li><p><li>Pulsar <b>Anexar fichero</b> para selección del tipo de documento que se va a anexar en el trámite</li></ul><p>Tanto si se genera un documento desde una plantilla o anexando un fichero, en la parte inferior de la pantalla de trámites se muestra el trámite con el fichero anexado y en la parte izquierda el nuevo trámite en la lista de trámites.<p>Para borrar un documento, en un trámite abierto, si se ha generado o anexado por error. Se seleccionará en la pantalla anterior el documento que se quiera borrar y se pulsará<b>Borrar documento</b>.<p>Para finalizar el trámite se pulsará <b>Terminar trámite</b>.<p>Un trámite se puede eliminar siempre que se encuentre abierto, una vez realizado, no es ni modificable. Para borrar un trámite, se seleccionará el botón de la pantalla de realización de trámites: <b>Eliminar trámite</b>.<p>Para delegar un trámite se pulsará el botón <b>Delegar</b> y se seleccionará el Destinatario.</p>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FICHA DEL PROCEDIMIENTO</h4></div><table><tr><td colspan=4><b>DATOS TÉCNICOS DEL PROCEDIMIENTO</B></td></tr><tr><td><b>FASES</b></td><td><b>Inicio</b></td><td>Trámites</td><td>Admisión a Trámite</td></tr><tr><td colspan=3></td><td>Comunicación de apertura</td></tr><tr><td></td><td><b>Instrucción</b></td><td>Trámites</td><td>Emisión Oficio de Respuesta</td></tr><tr><td colspan=3></td><td>Notificación Oficio de Respuesta</td></tr><tr><td colspan=3></td><td>Emisión Informe</td></tr><tr><td></td><td><b>Terminación</b></td><td>Trámites</td><td>Remisión Documentación</td></tr><tr><td colspan=3></td><td>Comunicación al interesado</td></tr><tr><td colspan=3></td><td>Emisión Oficio No Admisión</td></tr><tr><td colspan=3></td><td>Notificación</td></tr><tr><td colspan=3></td><td>Archivo de Expediente</td></tr><tr><td colspan=3><b>Resolución</b></td><td>Responsable Área de Atención al Cliente</td></tr><tr><td colspan=3><b>Plazo máximo de Notificación</b></td><td>3 meses ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'desde la entrada de la Solicitud en el Registro</td></tr><tr><td colspan=3><b>Tipo de Tramitación</b></td><td>Presencial y Telemática</td></tr><tr><td colspan=3><b>Efectos del Silencio Administrativo</b></td><td>Estimatorio, excepto que su concesión sea contraria a la legislación urbanística o se concedan al solicitante o a terceros facultades relativas al dominio o al servicio público</td></tr><tr><td colspan=3><b>Documentación Requerida</b></td><td>Solicitud de la Reclamación, Queja o Sugerencia</td></tr><tr><td colspan=4><b>NORMATIVA APLICABLE</b></td></tr><tr><td><b>Título</b></td><td colspan=3>Ejemplo: Plan General o Ley </td></tr><tr><td><b>Ámbito</b></td><td colspan=3></td></tr><tr><td><b>Fecha Vigor</b></td><td></td><td><b>Fecha publicación</b></td><td></td></table>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;

INSERT INTO spac_ayudas (id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (NEXTVAL FOR spac_sq_id_ayudas, 'Procedimiento de Concesión de subvención', 3, 4, NULL, '');
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FORMULARIO EXPEDIENTE</h4></div><p>La pantalla que se muestra es la del &#147;expediente abierto&#148;, es decir, es como si se abriera una carpeta física con un expediente en formato papel y tuviera unos separadores por: <b>Expediente, Datos específicos, Participantes y Documentos</b>.</p><p><ul><li><b>Expediente</b>: Se muestran los datos principales del expediente, en la zona superior, y del Interesado principal, en la zona inferior.</li><p><li><b>Datos específicos</b>:se muestran los datos particulares del procedimiento que se está tramitando.</li><p><li><b>Participantes</b>: se muestra el formulario para dar de alta o consultar los datos de las personas relacionadas con el Expediente.</li><p><li><b>Documentos</b>: consta de dos zonas, la superior donde se ven, en este caso los documentos con todos sus campos y se podrá acceder a abrir su imagen y la parte inferior en donde se muestran en formato tabla, todos los documentos del expediente.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<p>Los documentos se añaden al expediente, generalmente, desde los trámites de cada fase, pero hay expedientes que se diseñan de manera que se le pueden añadir documentos desde esta pestaña de Documentos.</p><p>Para ver todos los campos de información de un documento y sus imágenes, se seleccionará de la lista inferior, pulsando sobre su nombre. Se comprobará que sus datos se muestran en la parte superior. Seleccionando el botón <b>Ver documento</b> (situado debajo de las pestañas), se abrirá el documento.</p></li></ul><p>Desde el menú de la izquierda de la pantalla se podrán realizar las siguientes acciones:</p><p><ul><li>Pulsando el botón <b>Acciones</b> se podrá <b>Delegar fase</b> o <b>Clonar expediente</b>.</li><p><li>Crear un <b>Nuevo trámite</b> pulsando el botón del mismo nombre.</li><p><li>Pulsar el botón <b>Avanzar fase</b> para que el expediente que hay en pantalla avance de fase.</li><p><li>Pulsar <b> Trámites</b>  para visualizar los trámites por los que ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'ha pasado el expediente.</li><p><li>Pulsar <b>Expedientes relacionados</b> para visualizar los expedientes relacionados.</li></ul><div class="titulo_ayuda"><label class="popUpInfo" >Información del trámite</label></div><p>Debajo de la etiqueta <b>Trámite/Documento</b>, hay una línea informativa en donde se visualiza: el nombre del trámite actual y la fecha interna de iniciación.<p>Debajo de esa línea se encuentran los siguientes campos del Trámite:<p><ul><li><b>Departamento responsable</b>:campo que rellena la aplicación con el valor del departamento que realiza el trámite</li><p><li><b>Tramitador responsable</b>:campo que rellena la aplicación, con el nombre del usuario que lo realiza</li><p><li><b>Fecha inicio plazo</b>:campo que rellenará el usuario, seleccionando una fecha desde el  icono calendario</li><p><li><b>Plazo</b>:un número de unidades: días, meses, años para el plazo</li><p><li><b>Unidades de Plazo</b>:se seleccionará el icono Lupa y se mostrarán en una ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'nueva ventana, las distintas unidades de plazo existentes, de las cuales se seleccionará el valor correspondiente</li><p><li><b>Fecha Alarma</b>:este campo lo cumplimenta la aplicación y será el que se utilice para avisar al usuario tramitador, que le vence un plazo</li></ul><p>En la parte inferior de la pantalla,bajo la etiqueta: <B>DOCUMENTOS ADJUNTOS</b>, se encuentra la zona de documentos del trámite, en donde se visualizarán los documentos del trámite si los hubiera.<p>Desde esta pantalla se podrán realizar las siguientes acciones para <b>Generar documentos</b>:<p><ul><li>Pulsar <b>Desde plantilla</b> para seleccionar una plantilla de la lista de plantillas asociadas al trámite<p>El usuario pulsará sobre la que corresponda y se mostrará el documento normalizado, con los datos del expediente incluidos en él<p>Este documento, a no ser que se defina en el trámite de otra manera, es modificable, por si se quiere complementar con algún dato, cambiar una expresión, etc.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'</li><p><li>Pulsar <b>Anexar fichero</b> para selección del tipo de documento que se va a anexar en el trámite</li></ul><p>Tanto si se genera un documento desde una plantilla o anexando un fichero, en la parte inferior de la pantalla de trámites se muestra el trámite con el fichero anexado y en la parte izquierda el nuevo trámite en la lista de trámites.<p>Para borrar un documento, en un trámite abierto, si se ha generado o anexado por error. Se seleccionará en la pantalla anterior el documento que se quiera borrar y se pulsará<b>Borrar documento</b>.<p>Para finalizar el trámite se pulsará <b>Terminar trámite</b>.<p>Un trámite se puede eliminar siempre que se encuentre abierto, una vez realizado, no es ni modificable. Para borrar un trámite, se seleccionará el botón de la pantalla de realización de trámites: <b>Eliminar trámite</b>.<p>Para delegar un trámite se pulsará el botón <b>Delegar</b> y se seleccionará el Destinatario.</p>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FICHA DEL PROCEDIMIENTO</h4></div><table><tr><td colspan=2><b>Nombre</b></td><td colspan=2><b>Descripción</b></td></tr><tr><td colspan=2>TSUB</td><td colspan=2>Subvención genérica</td></tr><tr><td colspan=2><b>Familia de Procedimientos</b></td><td colspan=2><b>Descripción</b></td></tr><tr><td colspan=2>Subvenciones</td><td colspan=2>Procedimiento de concesión de una Subvención</td></tr><tr><td colspan=2><b>Organismo al que pertenece</b></td><td colspan=2>Unidad organizativa concesionaria de la Subvención</td></tr><tr><td colspan=2><b>Organismo competente</b></td><td colspan=2>Área de Concesión de Subvenciones</td></tr><tr><td colspan=2><b>Organismo que tramita</b></td><td colspan=2>Área de Concesión de Subvenciones</td></tr><tr><td colspan=2><b>Organismo que resuelve</b></td><td colspan=2>Área de Concesión de Subvenciones</td></tr><tr><td colspan=2><b>Objeto</b></td>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<td colspan=2>Procedimiento administrativo para la concesión de una Subvención</td></tr><tr><td colspan=4><b>DATOS TÉCNICOS DEL PROCEDIMIENTO</b></td></tr><tr><td><b>FASES</b></td><td><b>Inicio</b></td><td>Trámites</td><td>Solicitud Subsanación</td></tr><tr><td></td><td><b>Instrucción</b></td><td>Trámites</td><td>Propuesta de Resolución</td></tr><tr><td colspan=3></td><td>Acuerdo Consejo Gobierno</td></tr><tr><td colspan=3></td><td>Notificación</td></tr><tr><td colspan=3></td><td>Alegaciones</td></tr><tr><td></td><td><b>Terminación</b></td><td>Trámites</td><td>Decreto de concesión</td></tr><tr><td colspan=3></td><td>Notificación</td></tr><tr><td colspan=3></td><td>Archivo de Expediente</td></tr><tr><td colspan=3><b>Resolución</b></td><td>Responsable Área de Atención al Cliente</td></tr><tr><td colspan=3><b>Plazo máximo de Notificación</b></td><td>3 meses desde la entrada de la Solicitud en el Registro</td></tr>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<tr><td colspan=3><b>Tipo de Tramitación</b></td><td>Presencial y Telemática</td></tr><tr><td colspan=3><b>fectos del Silencio Administrativo</b></td><td>Estimatorio, excepto que su concesión sea contraria a la legislación urbanística o se concedan al solicitante o a terceros facultades relativas al dominio o al servicio público</td></tr><tr><td colspan=3><b>Documentación Requerida</b></td><td>Solicitud de la Subvención</td></tr><tr><td colspan=3></td><td>Documentos relativos a la Subvención </td></tr><tr><td colspan=4><b>NORMATIVA APLICABLE</b></td></tr><tr><td><b>Título</b></td><td colspan=3>Ejemplo: Plan General o Ley </td></tr><tr><td><b>Ámbito</b></td><td colspan=3></td></tr><tr><td><b>Fecha Vigor</b></td><td></td><td><b>Fecha publicación</b></td><td></td></table>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;

INSERT INTO spac_ayudas (id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (NEXTVAL FOR spac_sq_id_ayudas, 'Procedimiento de Obras menores', 3, 5, NULL, '');
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FORMULARIO EXPEDIENTE</h4></div><p>La pantalla que se muestra es la del &#147;expediente abierto&#148;, es decir, es como si se abriera una carpeta física con un expediente en formato papel y tuviera unos separadores por: <b>Expediente, Datos específicos, Participantes y Documentos</b>.</p><p><ul><li><b>Expediente</b>: Se muestran los datos principales del expediente, en la zona superior, y del Interesado principal, en la zona inferior.</li><p><li><b>Datos específicos</b>:se muestran los datos particulares del procedimiento que se está tramitando.</li><p><li><b>Participantes</b>: se muestra el formulario para dar de alta o consultar los datos de las personas relacionadas con el Expediente.</li><p><li><b>Documentos</b>: consta de dos zonas, la superior donde se ven, en este caso los documentos con todos sus campos y se podrá acceder a abrir su imagen y la parte inferior en donde se muestran en formato tabla, todos los documentos del expediente.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<p>Los documentos se añaden al expediente, generalmente, desde los trámites de cada fase, pero hay expedientes que se diseñan de manera que se le pueden añadir documentos desde esta pestaña de Documentos.</p><p>Para ver todos los campos de información de un documento y sus imágenes, se seleccionará de la lista inferior, pulsando sobre su nombre. Se comprobará que sus datos se muestran en la parte superior. Seleccionando el botón <b>Ver documento</b> (situado debajo de las pestañas), se abrirá el documento.</p></li></ul><p>Desde el menú de la izquierda de la pantalla se podrán realizar las siguientes acciones:</p><p><ul><li>Pulsando el botón <b>Acciones</b> se podrá <b>Delegar fase</b> o <b>Clonar expediente</b>.</li><p><li>Crear un <b>Nuevo trámite</b> pulsando el botón del mismo nombre.</li><p><li>Pulsar el botón <b>Avanzar fase</b> para que el expediente que hay en pantalla avance de fase.</li><p><li>Pulsar <b> Trámites</b>  para visualizar los trámites por los que ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'ha pasado el expediente.</li><p><li>Pulsar <b>Expedientes relacionados</b> para visualizar los expedientes relacionados.</li></ul><div class="titulo_ayuda"><label class="popUpInfo" >Información del trámite</label></div><p>Debajo de la etiqueta <b>Trámite/Documento</b>, hay una línea informativa en donde se visualiza: el nombre del trámite actual y la fecha interna de iniciación.<p>Debajo de esa línea se encuentran los siguientes campos del Trámite:<p><ul><li><b>Departamento responsable</b>:campo que rellena la aplicación con el valor del departamento que realiza el trámite</li><p><li><b>Tramitador responsable</b>:campo que rellena la aplicación, con el nombre del usuario que lo realiza</li><p><li><b>Fecha inicio plazo</b>:campo que rellenará el usuario, seleccionando una fecha desde el  icono calendario</li><p><li><b>Plazo</b>:un número de unidades: días, meses, años para el plazo</li><p><li><b>Unidades de Plazo</b>:se seleccionará el icono Lupa y se mostrarán en una ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'nueva ventana, las distintas unidades de plazo existentes, de las cuales se seleccionará el valor correspondiente</li><p><li><b>Fecha Alarma</b>:este campo lo cumplimenta la aplicación y será el que se utilice para avisar al usuario tramitador, que le vence un plazo</li></ul><p>En la parte inferior de la pantalla,bajo la etiqueta: <B>DOCUMENTOS ADJUNTOS</b>, se encuentra la zona de documentos del trámite, en donde se visualizarán los documentos del trámite si los hubiera.<p>Desde esta pantalla se podrán realizar las siguientes acciones para <b>Generar documentos</b>:<p><ul><li>Pulsar <b>Desde plantilla</b> para seleccionar una plantilla de la lista de plantillas asociadas al trámite<p>El usuario pulsará sobre la que corresponda y se mostrará el documento normalizado, con los datos del expediente incluidos en él<p>Este documento, a no ser que se defina en el trámite de otra manera, es modificable, por si se quiere complementar con algún dato, cambiar una expresión, etc.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'</li><p><li>Pulsar <b>Anexar fichero</b> para selección del tipo de documento que se va a anexar en el trámite</li></ul><p>Tanto si se genera un documento desde una plantilla o anexando un fichero, en la parte inferior de la pantalla de trámites se muestra el trámite con el fichero anexado y en la parte izquierda el nuevo trámite en la lista de trámites.<p>Para borrar un documento, en un trámite abierto, si se ha generado o anexado por error. Se seleccionará en la pantalla anterior el documento que se quiera borrar y se pulsará<b>Borrar documento</b>.<p>Para finalizar el trámite se pulsará <b>Terminar trámite</b>.<p>Un trámite se puede eliminar siempre que se encuentre abierto, una vez realizado, no es ni modificable. Para borrar un trámite, se seleccionará el botón de la pantalla de realización de trámites: <b>Eliminar trámite</b>.<p>Para delegar un trámite se pulsará el botón <b>Delegar</b> y se seleccionará el Destinatario.</p>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FICHA DEL PROCEDIMIENTO</h4></div><table><tr><td colspan=2><b>Nombre</b></td><td colspan=2><b>Descripción</b></td></tr><tr><td colspan=2>TLOM</td><td colspan=2>Licencia de Obra Menor</td></tr><tr><td colspan=2><b>Familia de Procedimientos</b></td><td colspan=2><b>Descripción</b></td></tr><tr><td colspan=2>Urbanismo</td><td colspan=2>Procedimiento de Urbanismo y Área Técnica</td></tr><tr><td colspan=2><b>Organismo al que pertenece</b></td><td colspan=2>Urbanismo y Área Técnica</td></tr><tr><td colspan=2><b>Organismo competente</b></td><td colspan=2>Urbanismo y Área Técnica</td></tr><tr><td colspan=2><b>Organismo que tramita</b></td><td colspan=2>Urbanismo y Área Técnica</td></tr><tr><td colspan=2><b>Organismo que resuelve</b></td><td colspan=2>Urbanismo y Área Técnica</td></tr><tr><td colspan=2><b<Objeto</b></td><td colspan=2>Procedimiento administrativo para la concesión de Licencia de Obra Menor</td></tr>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<tr><td colspan=4><b>DATOS TÉCNICOS DEL PROCEDIMIENTO</b></td></tr><tr><td><b>FASES</b></td><td><b>Inicio</b></td><td>Trámites</td><td>Solicitud subsanación</td></tr><tr><td></td><td><b>Instrucción</b></td><td>Trámites</td><td>Emisión informe</td></tr><tr><td colspan=3></td><td>Propuesta de resolución</td></tr><tr><td colspan=3></td><td>Notificación</td></tr><tr><td colspan=3></td><td>Alegaciones</td></tr><tr><td></td><td><b>Terminación</b></td><td>Trámites</td><td>Archivo del expediente</td></tr><tr><td colspan=3></td><td>Resolución expediente</td></tr><tr><td colspan=3></td><td>Notificación resolución</td></tr><tr><td colspan=3><b>Resolución</b></td><td>TTE. Alcalde delegado de Urbanismo por delegación del Alcalde-Presidente</td></tr><tr><td colspan=3><b>Plazo máximo de Notificación</b></td><td>3 meses desde la entrada de la Solicitud en el Registro</td></tr><tr><td colspan=3><b>Tipo de Tramitación</b></td><td>Presencial y Telemática</td></tr>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<tr><td colspan=3><b>Efectos del Silencio Administrativo</b></td><td>Estimatorio, excepto que su concesión sea contraria a la legislación urbanística o se concedan al solicitante o a terceros facultades relativas al dominio o al servicio público</td></tr><tr><td colspan=3><b>Documentación Requerida</b></td><td>Solicitud de la Licencia de Obra Menor</td></tr><tr><td colspan=3></td><td>Nota Simple Registral o</td></tr><tr><td colspan=3></td><td>Certificado de la Comisión Local de Casco Histórico (vivienda en casco histórico)</td></tr><tr><td colspan=4><b>NORMATIVA APLICABLE</b></td></tr><tr><td><b>Título</b></td><td colspan=3>Ejemplo: Plan General o Ley </td></tr><tr><td><b>Ámbito</b></td><td colspan=3>de Ordenación Urbana de ...</td></tr><tr><td><b>Fecha Vigor<b></td><td></td><td><b>Fecha publicación</b></td><td></td></table>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
