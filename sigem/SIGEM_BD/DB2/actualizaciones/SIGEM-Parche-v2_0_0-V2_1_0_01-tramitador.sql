-----------------------------------

--NOTAS:
--Este script está realizado para la versión DB2 8.2
--Se debe sustituir <USUARIO> y <CLAVE> por el usuario y clave de conexión.
--Se debe sustituir <TABLESPACE> por el espacio de tabla usado por las tablas afectadas.
--Se debe sustituir <NAME_NODE> por el nombre del nodo, suele igual que el nombre de la base de datos pero no tiene porqué.
--Este script debe ser ejecutado desde el 'procesador de línea de mandatos' de la siguiente forma:
--db2 -f <nombre_script> -v
--ej: db2 -f c:\SIGEM-Parche-v2_0_0-v2_1_0-01-tramitador.sql -v
--
--

CONNECT TO "<NAME_NODE>" USER "<USUARIO>" USING "<CLAVE>"


--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (NEXTVAL FOR spac_sq_id_infosistema, 'VERSIONBD', '6.1', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (NEXTVAL FOR spac_sq_id_infosistema, 'VERSIONAPP', '6.1', current_timestamp);


--
-- Actualización del EntityApp de la lista de documentos del expediente
--
UPDATE spac_ct_aplicaciones set clase='ieci.tdw.ispac.ispacmgr.app.ListDocumentsEntityApp' where id =5;

--
-- Cambio del tipo de la columna PARAMETROS en SPAC_CT_APLICACIONES
--
ALTER TABLE SPAC_CT_APLICACIONES ADD COLUMN PARAMETROS_TEMP CLOB;
UPDATE SPAC_CT_APLICACIONES SET PARAMETROS_TEMP = PARAMETROS;

-- Eliminar columna PARAMETROS
CALL SYSPROC.ALTOBJ ( 'APPLY_CONTINUE_ON_ERROR', 'CREATE TABLE SPAC_CT_APLICACIONES ( ID INTEGER NOT NULL,NOMBRE VARCHAR(100),DESCRIPCION VARCHAR(250),CLASE VARCHAR(250),PAGINA VARCHAR(250),PARAMETROS_TEMP CLOB,FORMATEADOR VARCHAR(250),XML_FORMATEADOR CLOB,FRM_JSP CLOB,FRM_VERSION SMALLINT,ENT_PRINCIPAL_ID INTEGER,ENT_PRINCIPAL_NOMBRE VARCHAR(100),DOCUMENTOS  VARCHAR(2) DEFAULT ''NO'') ', -1, ? );

-- Crear columna PARAMETROS de tipo CLOB y restaurar el contenido
ALTER TABLE SPAC_CT_APLICACIONES ADD COLUMN PARAMETROS CLOB;
UPDATE SPAC_CT_APLICACIONES SET PARAMETROS = PARAMETROS_TEMP; 

-- Eliminar columna PARAMETROS_TEMP
CALL SYSPROC.ALTOBJ ( 'APPLY_CONTINUE_ON_ERROR', 'CREATE TABLE SPAC_CT_APLICACIONES ( ID INTEGER NOT NULL,NOMBRE VARCHAR(100),DESCRIPCION VARCHAR(250),CLASE VARCHAR(250),PAGINA VARCHAR(250),PARAMETROS CLOB,FORMATEADOR VARCHAR(250),XML_FORMATEADOR CLOB,FRM_JSP CLOB,FRM_VERSION SMALLINT,ENT_PRINCIPAL_ID INTEGER,ENT_PRINCIPAL_NOMBRE VARCHAR(100),DOCUMENTOS  VARCHAR(2) DEFAULT ''NO'') ', -1, ? );

CONNECT RESET