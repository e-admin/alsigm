----------------
-- SECUENCIAS --
----------------
INSERT INTO <username>.SPAC_SQ_SEQUENCES (SEQUENCE_NAME, SEQUENCE_ID) VALUES('SGL_SQ_ID_OBRAS_MENORES',0);
INSERT INTO <username>.SPAC_SQ_SEQUENCES (SEQUENCE_NAME, SEQUENCE_ID) VALUES('SGQ_SQ_ID_QUEJAS',0);      
INSERT INTO <username>.SPAC_SQ_SEQUENCES (SEQUENCE_NAME, SEQUENCE_ID) VALUES('SGS_SQ_ID_SUBVENCIONES',0);
GO
--
-- Tablas de los pilotos
--
CREATE TABLE <username>.SGL_OBRAS_MENORES (
    ID INT NOT NULL,
    NUMEXP VARCHAR(30),
    TIPO_OBJETO VARCHAR(15),
    TIPO_LOCALIZADOR VARCHAR(15),
    LOCALIZADOR VARCHAR(15),
    ACTUACION_CASCO_HISTORICO VARCHAR(2),
    TIPO_FINCA VARCHAR(15),
    TIPO_SUELO VARCHAR(15),
    UBICACION_INMUEBLE VARCHAR(512),
    REFERENCIA_CATASTRAL VARCHAR(20),
    LOCALIZACION VARCHAR(15),
    DESCRIPCION TEXT,
    PRESUPUESTO_TOTAL NUMERIC(15,2),
    FECHA_INICIO DATETIME,
    FECHA_TERMINACION DATETIME,
    OCUPACION_VIA_PUBLICA VARCHAR(2)
)
GO
CREATE TABLE <username>.SGQ_QUEJAS (
    ID INT NOT NULL,
    NUMEXP VARCHAR(30),
    ID_ORGANO_OBJETO VARCHAR(64),
    ORGANO_OBJETO VARCHAR(512),
    TIPO_OBJETO VARCHAR(10),
    LOCALIZADOR_OBJETO VARCHAR(64),
    ASUNTO TEXT,
    TIPO_ORGANO_DESTINO VARCHAR(8),
    ID_ORGANO_INFORME VARCHAR(64),
    ORGANO_INFORME VARCHAR(512)
)
GO
CREATE TABLE <username>.SGS_SUBVENCIONES (
    ID INT NOT NULL,
    NUMEXP VARCHAR(30),
    ID_PCD INT,
    ANIO_PRESUPUESTARIO NUMERIC(4,0),
    TIPO_SUBVENCION VARCHAR(8),
    FECHA_CONVOCATORIA DATETIME,
    DENOMINACION_PROYECTO VARCHAR(120),
    RESUMEN_PROYECTO TEXT,
    FECHA_INICIO_EJECUCION DATETIME,
    FECHA_FIN_EJECUCION DATETIME,
    DURACION_PROYECTO NUMERIC(2,0),
    COLECTIVO_DESTINO VARCHAR(64),
    IMPORTE_SOLICITADO NUMERIC(13,2),
    IMPORTE_CONCEDIDO NUMERIC(13,2),
    PREVISION_GASTOS TEXT,
    IMPORTE_GASTOS NUMERIC(10,2),
    TOTAL_GASTOS NUMERIC(10,2),
    APORTACION_AYTO NUMERIC(13,2),
    APORTACION_ENTIDAD NUMERIC(13,2),
    OTRAS_APORTACIONES NUMERIC(13,2),
    TOTAL_APORTACIONES NUMERIC(13,2),
    FINALIDAD_SUBVENCION TEXT,
    OBSERVACIONES TEXT
)
GO


ALTER TABLE <username>.SGL_OBRAS_MENORES ADD CONSTRAINT PK_SGL_OBRAS_MENORES PRIMARY KEY (ID)
GO
ALTER TABLE <username>.SGQ_QUEJAS ADD CONSTRAINT PK_SGQ_QUEJAS PRIMARY KEY (ID)
GO
ALTER TABLE <username>.SGS_SUBVENCIONES ADD CONSTRAINT PK_SGS_SUBVENCIONES PRIMARY KEY (ID)
GO




---------------------------------------------------------
-- DATOS EXTRAIDOS DEL SCRIPT datos_iniciales  --
---------------------------------------------------------
-- Tipos de documento
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (1, 'Acuerdo Consejo de Gobierno', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (2, 'Admisión a trámite', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (3, 'Alegaciones', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'ENTRADA', NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (4, 'Archivo del expediente', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (5, 'Comunicación al interesado', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (6, 'Comunicación de apertura', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (7, 'Decreto de concesión', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (9, 'Emisión oficio de respuesta', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (10, 'Emisión oficio no admisión', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (12, 'Notificación oficio de respuesta', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (13, 'Propuesta de resolución', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (14, 'Remisión documentación', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'ENTRADA', NULL);
INSERT INTO <username>.SPAC_CT_TPDOC (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (16, 'Notificación resolución', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-09 00:00:00', 'SALIDA', NULL);
GO

-- Trámites
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (1, 'Admisión a trámite', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (2, 'Alegaciones', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (3, 'Archivo del expediente', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (4, 'Comunicación al interesado', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (5, 'Comunicación de apertura', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (6, 'Decreto de concesión', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (8, 'Emisión oficio de respuesta', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (9, 'Emisión oficio no admisión', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (11, 'Notificación oficio de respuesta', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (12, 'Propuesta de resolución', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (13, 'Remisión documentación', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (15, 'Acuerdo Consejo de Gobierno', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO <username>.SPAC_CT_TRAMITES (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (17, 'Notificación resolución', NULL, NULL, NULL, NULL, NULL, '2007-10-09 00:00:00', 'SYSSUPERUSER');
GO


-- Trámites asociados a las fases
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (1, 1, 1);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (2, 1, 5);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (4, 2, 2);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (6, 2, 12);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (7, 2, 15);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (9, 2, 8);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (10, 2, 11);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (11, 3, 3);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (12, 3, 4);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (13, 3, 6);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (14, 3, 9);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (16, 3, 13);
INSERT INTO <username>.SPAC_CT_FSTR (id, id_fase, id_tramite) VALUES (18, 3, 17);
GO

-- Tipos de documentos asociados a los trámites
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (1, 1, 2);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (2, 2, 3);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (3, 4, 5);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (5, 5, 6);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (6, 6, 7);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (8, 8, 9);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (9, 12, 13);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (10, 13, 14);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (12, 15, 1);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (13, 11, 12);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (14, 9, 10);
INSERT INTO <username>.SPAC_CT_TRTD (id, id_tramite, id_tpdoc) VALUES (16, 17, 16);
GO

--Plantillas
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (1, 2, '2007-10-08 18:46:19', 'Admisión a trámite', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (2, 6, '2007-10-08 18:48:10', 'Comunicación de apertura', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (3, 9, '2007-10-08 18:49:49', 'Emisión oficio de respuesta', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (4, 12, '2007-10-08 18:52:06', 'Notificación oficio de respuesta', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (6, 14, '2007-10-08 18:57:52', 'Remisión documentación', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (7, 5, '2007-10-08 18:59:48', 'Comunicación al interesado', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (8, 10, '2007-10-08 19:01:59', 'Emisión oficio no admisión', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (11, 13, '2007-10-09 09:32:51', 'Propuesta de resolución', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (12, 1, '2007-10-09 09:34:00', 'Acuerdo Consejo de Gobierno', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (13, 3, '2007-10-09 09:38:39', 'Alegaciones', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (18, 7, '2007-10-09 13:41:07', 'Decreto de concesión', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (34, 8, '2007-10-09 16:37:13', 'Emisión informe técnico', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (35, 8, '2007-10-09 16:37:44', 'Emisión informe jurídico', '', NULL, NULL, NULL);
INSERT INTO <username>.SPAC_P_PLANTDOC (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (38, 16, '2007-10-09 16:51:22', 'Notificación resolución', '', NULL, NULL, NULL);
GO


-------------------
-- OBRAS MENORES --
-------------------

--
-- Tablas de validación
--

-- Tabla de validación para Tipos de Suelo
INSERT INTO <username>.SPAC_SQ_SEQUENCES (SEQUENCE_NAME) VALUES('SPAC_SQ_SPAC_TBL_010');
CREATE TABLE <username>.SPAC_TBL_010 (
    ID INT NOT NULL,
    VALOR VARCHAR(8),
    SUSTITUTO VARCHAR(64),
    VIGENTE INT,
	ORDEN INT
)
GO
ALTER TABLE <username>.SPAC_TBL_010 ADD CONSTRAINT PK_SPAC_TBL_010 PRIMARY KEY (ID);
GO

UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = (SELECT MAX(ID) FROM SPAC_CT_ENTIDADES_RESOURCES)+1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES'
GO

INSERT INTO <username>.SPAC_CT_ENTIDADES (ID, TIPO, NOMBRE, CAMPO_PK, CAMPO_NUMEXP, SCHEMA_EXPR, FRM_EDIT, DESCRIPCION, SEC_PK, DEFINICION) VALUES (137, 3, 'SPAC_TBL_010', 'ID', NULL, NULL, NULL, 'Tabla de validación para Tipos de Suelo', 'SPAC_SQ_SPAC_TBL_010', '<entity version=''1.00''><editable>N</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
DECLARE @sequence_resources_id int
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 137, 'es', 'SPAC_TBL_010', 'Tipos de Suelo');
INSERT INTO <username>.SPAC_TBL_010 (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (1, '1', 'Urbanizable', 1, 1);
INSERT INTO <username>.SPAC_TBL_010 (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (2, '2', 'No urbanizable', 1, 2);
INSERT INTO <username>.SPAC_TBL_010 (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (3, '3', 'Urbano', 1, 3);
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 4 WHERE SEQUENCE_NAME = 'SPAC_SQ_SPAC_TBL_010';
GO


-- Tabla de validación para Tipos de Finca
INSERT INTO <username>.SPAC_SQ_SEQUENCES (SEQUENCE_NAME) VALUES('SPAC_SQ_SPAC_TBL_011');
CREATE TABLE <username>.SPAC_TBL_011 (
  	ID INT NOT NULL,
  	VALOR VARCHAR(8),
  	SUSTITUTO VARCHAR(64),
  	VIGENTE INT,
	ORDEN INT
)
GO
ALTER TABLE <username>.SPAC_TBL_011 ADD CONSTRAINT PK_SPAC_TBL_011 PRIMARY KEY (ID);
GO

INSERT INTO <username>.SPAC_CT_ENTIDADES (ID, TIPO, NOMBRE, CAMPO_PK, CAMPO_NUMEXP, SCHEMA_EXPR, FRM_EDIT, DESCRIPCION, SEC_PK, DEFINICION) VALUES (138, 3, 'SPAC_TBL_011', 'ID', NULL, NULL, NULL, 'Tabla de validación para Tipos de Finca', 'SPAC_SQ_SPAC_TBL_011', '<entity version=''1.00''><editable>N</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
DECLARE @sequence_resources_id int
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 138, 'es', 'SPAC_TBL_011', 'Tipos de Finca');
INSERT INTO <username>.SPAC_TBL_011 (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (1, '1', 'Unifamiliar', 1, 1);
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 2 WHERE SEQUENCE_NAME = 'SPAC_SQ_SPAC_TBL_011';
GO


-- Tabla de validación para Localización de Obras
INSERT INTO <username>.SPAC_SQ_SEQUENCES (SEQUENCE_NAME) VALUES('SPAC_SQ_SPAC_TBL_012');
GO
CREATE TABLE <username>.SPAC_TBL_012 (
  	ID INT NOT NULL,
  	VALOR VARCHAR(8),
  	SUSTITUTO VARCHAR(64),
  	VIGENTE INT,
	ORDEN INT
)
GO

ALTER TABLE <username>.SPAC_TBL_012 ADD CONSTRAINT PK_SPAC_TBL_012 PRIMARY KEY (ID);
GO

INSERT INTO <username>.SPAC_CT_ENTIDADES (ID, TIPO, NOMBRE, CAMPO_PK, CAMPO_NUMEXP, SCHEMA_EXPR, FRM_EDIT, DESCRIPCION, SEC_PK, DEFINICION) VALUES (139, 3, 'SPAC_TBL_012', 'ID', NULL, NULL, NULL, 'Tabla de validación para Localización de Obras', 'SPAC_SQ_SPAC_TBL_012', '<entity version=''1.00''><editable>N</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
DECLARE @sequence_resources_id int
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 139, 'es', 'SPAC_TBL_012', 'Localización de Obras');
INSERT INTO <username>.SPAC_TBL_012 (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (1, '1', 'Exterior', 1, 1);
INSERT INTO <username>.SPAC_TBL_012 (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (2, '2', 'Interior', 1, 2);
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 3 WHERE SEQUENCE_NAME = 'SPAC_SQ_SPAC_TBL_012';
GO

--
-- Entidad
--
INSERT INTO <username>.SPAC_CT_ENTIDADES (ID, TIPO, NOMBRE, CAMPO_PK, CAMPO_NUMEXP, SCHEMA_EXPR, FRM_EDIT, DESCRIPCION, SEC_PK, DEFINICION, FRM_JSP, FECHA)
	VALUES (200, 1, 'SGL_OBRAS_MENORES', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Obra Menor', 'SGL_SQ_ID_OBRAS_MENORES', NULL, NULL, getdate());

UPDATE <username>.SPAC_CT_ENTIDADES SET DEFINICION ='<entity version=''1.00''>
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
		<table>139</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''2''>
		<fieldId>5</fieldId>
		<table>138</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''3''>
		<fieldId>6</fieldId>
		<table>137</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''4''>
		<fieldId>4</fieldId>
		<table>136</table>
		<tableType>2</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''5''>
		<fieldId>14</fieldId>
		<table>136</table>
		<tableType>2</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
</validations>
</entity>'
WHERE ID = 200;
GO

DECLARE @sequence_resources_id int
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'SGL_OBRAS_MENORES', 'Obra Menor');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'DESCRIPCION', 'Descripción Obras');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'TIPO_OBJETO', 'Tipo de Objeto');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'TIPO_LOCALIZADOR', 'Tipo de Localizador');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'LOCALIZADOR', 'Localizador');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'UBICACION_INMUEBLE', 'Ubicación inmueble');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'REFERENCIA_CATASTRAL', 'Referencia catastral');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'LOCALIZACION', 'Localización obras');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'TIPO_FINCA', 'Tipo de finca');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'TIPO_SUELO', 'Tipo de suelo');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'ACTUACION_CASCO_HISTORICO', 'Actuación dentro del Casco Histórico');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'OCUPACION_VIA_PUBLICA', 'Necesidad ocupar vía pública');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'PRESUPUESTO_TOTAL', 'Presupuesto total (sin IVA)');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'FECHA_INICIO', 'Fecha Inicio obras');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 200, 'es', 'FECHA_TERMINACION', 'Fecha Terminación');
GO

--
-- Formulario
--
INSERT INTO <username>.SPAC_CT_APLICACIONES (ID, NOMBRE, DESCRIPCION, CLASE, PAGINA, PARAMETROS, FORMATEADOR, ENT_PRINCIPAL_ID, ENT_PRINCIPAL_NOMBRE)
	VALUES (10, 'Obra Menor', 'Licencia para Obra Menor', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/SGL_OBRAS_MENORES/Obra_Menor.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_012</table><relation type=''FLD''><primary-field>LOCALIZACION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_011</table><relation type=''FLD''><primary-field>TIPO_FINCA</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_010</table><relation type=''FLD''><primary-field>TIPO_SUELO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, 200, 'SGL_OBRAS_MENORES');
UPDATE <username>.SPAC_CT_ENTIDADES SET FRM_EDIT = 10 WHERE ID = 200;

GO

UPDATE <username>.SPAC_CT_APLICACIONES SET FRM_JSP = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
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
, FRM_VERSION = 1
WHERE ID = 10;
GO
--
-- Reglas
--
INSERT INTO <username>.SPAC_CT_REGLAS (ID, NOMBRE, DESCRIPCION, CLASE, TIPO) VALUES (20, 'InitObraMenorRule', 'Crea e inicializa los datos de una Obra Menor', 'ieci.tdw.ispac.api.rule.procedures.obras.InitObraMenorRule', 1);
GO
--
-- Procedimiento
--
INSERT INTO <username>.SPAC_CT_PROCEDIMIENTOS (ID, COD_PCD, NOMBRE, ID_PADRE, TITULO, OBJETO, ASUNTO, ACT_FUNC, MTRS_COMP, SIT_TLM, URL, INTERESADO, TP_REL, ORG_RSLTR, FORMA_INIC, PLZ_RESOL, UNID_PLZ, FINICIO, FFIN, EFEC_SILEN, FIN_VIA_ADMIN, RECURSOS, FCATALOG, AUTOR, ESTADO, NVERSION, OBSERVACIONES, LUGAR_PRESENT, CNDS_ECNMCS, INGRESOS, F_CBR_PGO, INFR_SANC, CALENDARIO, DSCR_TRAM, NORMATIVA, CND_PARTICIP, DOCUMENTACION, GRUPOS_DELEGACION, COD_SISTEMA_PRODUCTOR, MAPEO_RT)
	VALUES (5, 'PCD-5', 'Obras menores', 1, 'Obras menores', NULL, 'Obras menores', NULL, NULL, NULL, NULL, NULL, 'INT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Fotocopia DNI
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

INSERT INTO <username>.SPAC_P_PROCEDIMIENTOS (ID, ID_PCD_BPM, NVERSION, NOMBRE, ESTADO, ID_RESP, ID_RESP_SEC, ID_GROUP, ID_CRT, TS_CRT, ID_UPD, TS_UPD) 
	VALUES (5, 5, 1, 'Obras menores', 2, NULL, NULL, 5, NULL, getdate(), NULL, NULL);
GO
--
-- Nodos
--
INSERT INTO <username>.SPAC_P_NODOS (ID, ID_NODO_BPM, ID_PCD, TIPO, G_INFO) VALUES (14, 14, 5, 1, NULL);
INSERT INTO <username>.SPAC_P_NODOS (ID, ID_NODO_BPM, ID_PCD, TIPO, G_INFO) VALUES (15, 15, 5, 1, NULL);
INSERT INTO <username>.SPAC_P_NODOS (ID, ID_NODO_BPM, ID_PCD, TIPO, G_INFO) VALUES (16, 16, 5, 1, NULL);
GO
--
-- Fases
--
INSERT INTO <username>.SPAC_P_FASES (ID, ID_CTFASE, ID_PCD, NOMBRE, ID_RESP, ID_RESP_SEC) VALUES (14, 1, 5, 'Fase Inicio', NULL, NULL);
INSERT INTO <username>.SPAC_P_FASES (ID, ID_CTFASE, ID_PCD, NOMBRE, ID_RESP, ID_RESP_SEC) VALUES (15, 2, 5, 'Fase Instrucción', NULL, NULL);
INSERT INTO <username>.SPAC_P_FASES (ID, ID_CTFASE, ID_PCD, NOMBRE, ID_RESP, ID_RESP_SEC) VALUES (16, 3, 5, 'Fase Terminación', NULL, NULL);
GO
--
-- Flujos
--
INSERT INTO <username>.SPAC_P_FLUJOS (ID, ID_FLUJO_BPM, ID_PCD, ID_ORIGEN, ID_DESTINO) VALUES (18, 18, 5, 14, 15);
INSERT INTO <username>.SPAC_P_FLUJOS (ID, ID_FLUJO_BPM, ID_PCD, ID_ORIGEN, ID_DESTINO) VALUES (19, 19, 5, 15, 16);
GO

--
-- Trámites
--
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (27, 27, 7, 5, 15, 'Emisión informe', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (32, 32, 3, 5, 16, 'Archivo del expediente', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (33, 33, 14, 5, 14, 'Solicitud subsanación', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (34, 34, 12, 5, 15, 'Propuesta de resolución', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (35, 35, 10, 5, 15, 'Notificación', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (36, 36, 2, 5, 15, 'Alegaciones', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (37, 37, 16, 5, 16, 'Resolución expediente', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (38, 38, 17, 5, 16, 'Notificación resolución', 0, NULL, NULL, null);
GO

--
-- Eventos
--
INSERT INTO <username>.SPAC_P_EVENTOS (ID_OBJ, TP_OBJ, EVENTO, ORDEN, ID_REGLA) VALUES (5, 1, 32, 5, 20);
GO
--
-- Entidades
--
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (19, 1, 5, 0, 1, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (20, 7, 5, 1, 3, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (21, 3, 5, 1, 4, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (22, 2, 5, 1, 23, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (23, 200, 5, 0, 2, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (26, 8, 5, 1, 5, NULL);
GO

------------
-- QUEJAS --
------------

--
-- Entidad
--
INSERT INTO <username>.SPAC_CT_ENTIDADES (ID, TIPO, NOMBRE, CAMPO_PK, CAMPO_NUMEXP, SCHEMA_EXPR, FRM_EDIT, DESCRIPCION, SEC_PK, DEFINICION, FRM_JSP, FECHA)
	VALUES (201, 1, 'SGQ_QUEJAS', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Datos específicos', 'SGQ_SQ_ID_QUEJAS', NULL, NULL, getdate());
GO
UPDATE <username>.SPAC_CT_ENTIDADES SET DEFINICION ='<entity version=''1.00''>
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
WHERE ID = 201;
GO

DECLARE @sequence_resources_id int
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 201, 'es', 'SGQ_QUEJAS', 'Datos específicos');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 201, 'es', 'ASUNTO', 'Texto de la Reclamación, Queja o Sugerencia');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 201, 'es', 'ID_ORGANO_OBJETO', 'Órgano Destinatario');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 201, 'es', 'ORGANO_OBJETO', 'Órgano Destinatario');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 201, 'es', 'TIPO_OBJETO', 'Tipo');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 201, 'es', 'LOCALIZADOR_OBJETO', 'Localizador');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 201, 'es', 'TIPO_ORGANO_DESTINO', 'Tipo del Órgano de Destino');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 201, 'es', 'ID_ORGANO_INFORME', 'Id. de Órgano del Informe');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 201, 'es', 'ORGANO_INFORME', 'Órgano del Informe');
GO

--
-- Formulario
--
INSERT INTO <username>.SPAC_CT_APLICACIONES (ID, NOMBRE, DESCRIPCION, CLASE, PAGINA, PARAMETROS, FORMATEADOR, ENT_PRINCIPAL_ID, ENT_PRINCIPAL_NOMBRE)
	VALUES (11, 'Queja', 'Reclamación, Queja o Sugerencia', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/SGQ_QUEJAS/Queja.jsp', NULL, NULL, 201, 'SGQ_QUEJAS');
UPDATE <username>.SPAC_CT_ENTIDADES SET FRM_EDIT = 11 WHERE ID = 201;
GO
UPDATE <username>.SPAC_CT_APLICACIONES SET FRM_JSP = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
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
, FRM_VERSION = 1
WHERE ID = 11;
GO
--
-- Reglas
--
INSERT INTO <username>.SPAC_CT_REGLAS (ID, NOMBRE, DESCRIPCION, CLASE, TIPO) VALUES (21, 'InitQuejaRule', 'Crea e inicializa los datos de una Queja, Reclamación o Sugerencia', 'ieci.tdw.ispac.api.rule.procedures.quejas.InitQuejaRule', 1);
GO
--
-- Procedimiento
--
INSERT INTO <username>.SPAC_CT_PROCEDIMIENTOS (ID, COD_PCD, NOMBRE, ID_PADRE, TITULO, OBJETO, ASUNTO, ACT_FUNC, MTRS_COMP, SIT_TLM, URL, INTERESADO, TP_REL, ORG_RSLTR, FORMA_INIC, PLZ_RESOL, UNID_PLZ, FINICIO, FFIN, EFEC_SILEN, FIN_VIA_ADMIN, RECURSOS, FCATALOG, AUTOR, ESTADO, NVERSION, OBSERVACIONES, LUGAR_PRESENT, CNDS_ECNMCS, INGRESOS, F_CBR_PGO, INFR_SANC, CALENDARIO, DSCR_TRAM, NORMATIVA, CND_PARTICIP, DOCUMENTACION, GRUPOS_DELEGACION, COD_SISTEMA_PRODUCTOR, MAPEO_RT) 
	VALUES (3, 'PCD-3', 'Reclamaciones, quejas y sugerencias', 1, 'Reclamaciones, quejas y sugerencias', NULL, 'Reclamaciones, quejas y sugerencias', NULL, NULL, NULL, NULL, NULL, 'INT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Fotocopia del DNI
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
GO
INSERT INTO <username>.SPAC_P_PROCEDIMIENTOS (ID, ID_PCD_BPM, NVERSION, NOMBRE, ESTADO, ID_RESP, ID_RESP_SEC, ID_GROUP, ID_CRT, TS_CRT, ID_UPD, TS_UPD) 
	VALUES (3, 3, 1, 'Reclamaciones, quejas y sugerencias', 2, NULL, NULL, 3, NULL, getdate(), NULL, NULL);
GO
--
-- Nodos
--
INSERT INTO <username>.SPAC_P_NODOS (ID, ID_NODO_BPM, ID_PCD, TIPO, G_INFO) VALUES (6, 6, 3, 1, NULL);
INSERT INTO <username>.SPAC_P_NODOS (ID, ID_NODO_BPM, ID_PCD, TIPO, G_INFO) VALUES (7, 7, 3, 1, NULL);
INSERT INTO <username>.SPAC_P_NODOS (ID, ID_NODO_BPM, ID_PCD, TIPO, G_INFO) VALUES (8, 8, 3, 1, NULL);
GO
--
-- Fases
--
INSERT INTO <username>.SPAC_P_FASES (ID, ID_CTFASE, ID_PCD, NOMBRE, ID_RESP, ID_RESP_SEC) VALUES (6, 1, 3, 'Fase Inicio', NULL, NULL);
INSERT INTO <username>.SPAC_P_FASES (ID, ID_CTFASE, ID_PCD, NOMBRE, ID_RESP, ID_RESP_SEC) VALUES (7, 2, 3, 'Fase Instrucción', NULL, NULL);
INSERT INTO <username>.SPAC_P_FASES (ID, ID_CTFASE, ID_PCD, NOMBRE, ID_RESP, ID_RESP_SEC) VALUES (8, 3, 3, 'Fase Terminación', NULL, NULL);
GO
--
-- Flujos
--
INSERT INTO <username>.SPAC_P_FLUJOS (ID, ID_FLUJO_BPM, ID_PCD, ID_ORIGEN, ID_DESTINO) VALUES (8, 8, 3, 6, 7);
INSERT INTO <username>.SPAC_P_FLUJOS (ID, ID_FLUJO_BPM, ID_PCD, ID_ORIGEN, ID_DESTINO) VALUES (9, 9, 3, 7, 8);
GO
--
-- Trámites
--
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (5, 5, 1, 3, 6, 'Admisión a trámite', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (6, 6, 5, 3, 6, 'Comunicación de apertura', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (7, 7, 8, 3, 7, 'Emisión oficio de respuesta', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (8, 8, 11, 3, 7, 'Notificación oficio de respuesta', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (9, 9, 7, 3, 7, 'Emisión informe', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (10, 10, 13, 3, 8, 'Remisión documentación', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (11, 11, 4, 3, 8, 'Comunicación al interesado', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (12, 12, 9, 3, 8, 'Emisión oficio no admisión', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (13, 13, 10, 3, 8, 'Notificación', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (14, 14, 3, 3, 8, 'Archivo del expediente', 0, NULL, NULL, null);
GO
--	    
-- Eventos
--
INSERT INTO <username>.SPAC_P_EVENTOS (ID_OBJ, TP_OBJ, EVENTO, ORDEN, ID_REGLA) VALUES (3, 1, 32, 7, 21);

GO
--
-- Entidades
--
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (11, 1, 3, 0, 1, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (12, 7, 3, 1, 3, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (13, 3, 3, 1, 4, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (14, 2, 3, 1, 25, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (25, 201, 3, 0, 2, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (27, 8, 3, 1, 5, NULL);
GO
------------------
-- SUBVENCIONES --
------------------

--
-- Tablas de validación
--

-- Tabla de validación para Tipos de Subvención
INSERT INTO <username>.SPAC_SQ_SEQUENCES (SEQUENCE_NAME) VALUES('SPAC_SQ_SPAC_TBL_013');
GO
CREATE TABLE <username>.SPAC_TBL_013 (
  	ID INT NOT NULL,
  	VALOR VARCHAR(8),
  	SUSTITUTO VARCHAR(64),
  	VIGENTE INT,
	ORDEN INT
)
GO
ALTER TABLE <username>.SPAC_TBL_013 ADD CONSTRAINT PK_SPAC_TBL_013 PRIMARY KEY (ID);
GO

INSERT INTO <username>.SPAC_CT_ENTIDADES (ID, TIPO, NOMBRE, CAMPO_PK, CAMPO_NUMEXP, SCHEMA_EXPR, FRM_EDIT, DESCRIPCION, SEC_PK, DEFINICION) VALUES (140, 3, 'SPAC_TBL_013', 'ID', NULL, NULL, NULL, 'Tabla de validación para Tipos de Subvención', 'SPAC_SQ_SPAC_TBL_013', '<entity version=''1.00''><editable>N</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
DECLARE @sequence_resources_id int
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES (ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 140, 'es', 'SPAC_TBL_013', 'Tipos de Subvención');
INSERT INTO <username>.SPAC_TBL_013 (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (1, 'INV', 'Investigación', 1, 1);
INSERT INTO <username>.SPAC_TBL_013 (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (2, 'TIC', 'Innovación Tecnológica', 1, 2);
INSERT INTO <username>.SPAC_TBL_013 (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (3, 'PRO', 'Actividad Promocional', 1, 3);
INSERT INTO <username>.SPAC_TBL_013 (ID, VALOR, SUSTITUTO, VIGENTE, ORDEN) VALUES (4, 'OBR', 'Obras Menores', 1, 4);
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 5 WHERE SEQUENCE_NAME = 'SPAC_SQ_SPAC_TBL_013';
GO

--
-- Entidad
--
INSERT INTO <username>.SPAC_CT_ENTIDADES (ID, TIPO, NOMBRE, CAMPO_PK, CAMPO_NUMEXP, SCHEMA_EXPR, FRM_EDIT, DESCRIPCION, SEC_PK, DEFINICION, FRM_JSP, FECHA)
	VALUES (202, 1, 'SGS_SUBVENCIONES', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Subvención', 'SGS_SQ_ID_SUBVENCIONES', NULL, NULL, getdate());
GO
UPDATE <username>.SPAC_CT_ENTIDADES SET DEFINICION ='<entity version=''1.00''>
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
		<table>140</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
</validations>
</entity>'
WHERE ID = 202;
GO

DECLARE @sequence_resources_id int
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'SGS_SUBVENCIONES', 'Subvención');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'ANIO_PRESUPUESTARIO', 'Año presupuestario');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'FECHA_CONVOCATORIA', 'Fecha de convocatoria');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'TIPO_SUBVENCION', 'Tipo de subvención');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'COLECTIVO_DESTINO', 'Colectivo destino');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'DENOMINACION_PROYECTO', 'Denominación del proyecto');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'RESUMEN_PROYECTO', 'Resumen del proyecto');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'FECHA_INICIO_EJECUCION', 'Fecha inicio de ejecución');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'FECHA_FIN_EJECUCION', 'Fecha fin de ejecución');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'DURACION_PROYECTO', 'Duración del proyecto');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'IMPORTE_SOLICITADO', 'Importe solicitado');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'IMPORTE_CONCEDIDO', 'Importe concedido');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'PREVISION_GASTOS', 'Gastos previstos');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'IMPORTE_GASTOS', 'Importe gastos');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'APORTACION_AYTO', 'Aportación ayuntamiento');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'APORTACION_ENTIDAD', 'Aportación entidad solicitante');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'OTRAS_APORTACIONES', 'Otras aportaciones');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'TOTAL_APORTACIONES', 'Total aportacion');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'FINALIDAD_SUBVENCION', 'Finalidad subvención');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'OBSERVACIONES', 'Observaciones');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'ID_PCD', 'Id. de Procedimiento');
UPDATE <username>.SPAC_SQ_SEQUENCES	SET @sequence_resources_id = sequence_id = sequence_id + 1 WHERE sequence_name = 'SPAC_SQ_ID_ENTIDADESRESOURCES';
INSERT INTO <username>.SPAC_CT_ENTIDADES_RESOURCES(ID, ID_ENT, IDIOMA, CLAVE, VALOR) VALUES (@sequence_resources_id, 202, 'es', 'TOTAL_GASTOS', 'Total gastos');
GO

--
-- Formulario
--
INSERT INTO <username>.SPAC_CT_APLICACIONES (ID, NOMBRE, DESCRIPCION, CLASE, PAGINA, PARAMETROS, FORMATEADOR, ENT_PRINCIPAL_ID, ENT_PRINCIPAL_NOMBRE)
	VALUES (12, 'Subvención', 'Subvención', 'ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp', '/forms/SGS_SUBVENCIONES/Subvención.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_013</table><relation type=''FLD''><primary-field>TIPO_SUBVENCION</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, 202, 'SGS_SUBVENCIONES');
GO
UPDATE <username>.SPAC_CT_ENTIDADES SET FRM_EDIT = 12 WHERE ID = 202;
GO
UPDATE <username>.SPAC_CT_APLICACIONES SET FRM_JSP = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
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
, FRM_VERSION = 1
WHERE ID = 12;
GO
--
-- Reglas
--
INSERT INTO <username>.SPAC_CT_REGLAS (ID, NOMBRE, DESCRIPCION, CLASE, TIPO) VALUES (22, 'InitSubvencionRule', 'Crea e inicializa los datos de una Subvención', 'ieci.tdw.ispac.api.rule.procedures.subvenciones.InitSubvencionRule', 1);
INSERT INTO <username>.SPAC_CT_REGLAS (ID, NOMBRE, DESCRIPCION, CLASE, TIPO) VALUES (23, 'StoreSubventionRule', 'Calcula el total de una Subvención cuando se guarda', 'ieci.tdw.ispac.api.rule.procedures.subvenciones.StoreSubventionRule', 1);
GO
--
-- Procedimiento
--
INSERT INTO <username>.SPAC_CT_PROCEDIMIENTOS (ID, COD_PCD, NOMBRE, ID_PADRE, TITULO, OBJETO, ASUNTO, ACT_FUNC, MTRS_COMP, SIT_TLM, URL, INTERESADO, TP_REL, ORG_RSLTR, FORMA_INIC, PLZ_RESOL, UNID_PLZ, FINICIO, FFIN, EFEC_SILEN, FIN_VIA_ADMIN, RECURSOS, FCATALOG, AUTOR, ESTADO, NVERSION, OBSERVACIONES, LUGAR_PRESENT, CNDS_ECNMCS, INGRESOS, F_CBR_PGO, INFR_SANC, CALENDARIO, DSCR_TRAM, NORMATIVA, CND_PARTICIP, DOCUMENTACION, GRUPOS_DELEGACION, COD_SISTEMA_PRODUCTOR, MAPEO_RT) 
	VALUES (4, 'PCD-4', 'Concesión de subvención', 1, 'Concesión de subvención', NULL, 'Concesión de subvención
', NULL, NULL, NULL, NULL, NULL, 'INT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Fotocopia DNI
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
GO
INSERT INTO <username>.SPAC_P_PROCEDIMIENTOS (ID, ID_PCD_BPM, NVERSION, NOMBRE, ESTADO, ID_RESP, ID_RESP_SEC, ID_GROUP, ID_CRT, TS_CRT, ID_UPD, TS_UPD) 
VALUES (4, 4, 1, 'Concesión de subvención', 2, NULL, NULL, 4, NULL, getdate(), NULL, NULL);
GO
--
-- Nodos
--
INSERT INTO <username>.SPAC_P_NODOS (ID, ID_NODO_BPM, ID_PCD, TIPO, G_INFO) VALUES (10, 10, 4, 1, NULL);
INSERT INTO <username>.SPAC_P_NODOS (ID, ID_NODO_BPM, ID_PCD, TIPO, G_INFO) VALUES (11, 11, 4, 1, NULL);
INSERT INTO <username>.SPAC_P_NODOS (ID, ID_NODO_BPM, ID_PCD, TIPO, G_INFO) VALUES (12, 12, 4, 1, NULL);
GO
--
-- Fases
--
INSERT INTO <username>.SPAC_P_FASES (ID, ID_CTFASE, ID_PCD, NOMBRE, ID_RESP, ID_RESP_SEC) VALUES (10, 1, 4, 'Fase Inicio', NULL, NULL);
INSERT INTO <username>.SPAC_P_FASES (ID, ID_CTFASE, ID_PCD, NOMBRE, ID_RESP, ID_RESP_SEC) VALUES (11, 2, 4, 'Fase Instrucción', NULL, NULL);
INSERT INTO <username>.SPAC_P_FASES (ID, ID_CTFASE, ID_PCD, NOMBRE, ID_RESP, ID_RESP_SEC) VALUES (12, 3, 4, 'Fase Terminación', NULL, NULL);
GO
--
-- Flujos
--
INSERT INTO <username>.SPAC_P_FLUJOS (ID, ID_FLUJO_BPM, ID_PCD, ID_ORIGEN, ID_DESTINO) VALUES (13, 13, 4, 10, 11);
INSERT INTO <username>.SPAC_P_FLUJOS (ID, ID_FLUJO_BPM, ID_PCD, ID_ORIGEN, ID_DESTINO) VALUES (14, 14, 4, 11, 12);
GO
--
-- Trámites
--
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (15, 15, 14, 4, 10, 'Solicitud subsanación', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (16, 16, 12, 4, 11, 'Propuesta de resolución', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (17, 17, 15, 4, 11, 'Acuerdo Consejo de Gobierno', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (18, 18, 10, 4, 11, 'Notificación', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (19, 19, 2, 4, 11, 'Alegaciones', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (20, 20, 6, 4, 12, 'Decreto de concesión', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (21, 21, 10, 4, 12, 'Notificación', 0, NULL, NULL, null);
INSERT INTO <username>.SPAC_P_TRAMITES (ID, ID_TRAMITE_BPM, ID_CTTRAMITE, ID_PCD, ID_FASE, NOMBRE, LIBRE, ID_RESP, ID_RESP_SEC, ID_PCD_SUB) VALUES (22, 22, 3, 4, 12, 'Archivo del expediente', 0, NULL, NULL, null);
GO	    
--
-- Eventos
--
INSERT INTO <username>.SPAC_P_EVENTOS (ID_OBJ, TP_OBJ, EVENTO, ORDEN, ID_REGLA) VALUES (4, 1, 32, 10, 22);
INSERT INTO <username>.SPAC_P_EVENTOS (ID_OBJ, TP_OBJ, EVENTO, ORDEN, ID_REGLA) VALUES (24, 5, 12, 12, 23);
GO
--
-- Entidades
--
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (15, 1, 4, 0, 1, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (16, 7, 4, 1, 3, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (17, 3, 4, 1, 4, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (18, 2, 4, 1, 24, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (24, 202, 4, 0, 2, NULL);
INSERT INTO <username>.SPAC_P_ENTIDADES (ID, ID_ENT, ID_PCD, TP_REL, ORDEN, FRM_EDIT) VALUES (28, 8, 4, 1, 5, NULL);
GO

---------------------------
-- ACTUALIZAR SECUENCIAS --
---------------------------

GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 203 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_CTENTIDADES';
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 13 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_CTAPLICACIONES';
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 24 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_CTREGLAS';
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 6 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PPROCEDIMIENTOS';
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 17 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PNODOS';
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 39 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PTRAMITES';
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 100 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PEVENTOS';
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 50 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PENTIDADES';
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 6 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_SSPERMISOS';
GO
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 20 WHERE SEQUENCE_NAME = 'SPAC_SQ_ID_PFLUJOS';
GO             
UPDATE <username>.SPAC_SQ_SEQUENCES SET SEQUENCE_ID = 0 WHERE SEQUENCE_ID IS NULL;
GO             
