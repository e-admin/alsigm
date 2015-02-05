-----------------------------------
-- Actualización de v6.2 a v6.3
-----------------------------------
--NOTAS:
--Este script está realizado para la versión DB2 8.2
--Se debe sustituir <USUARIO> y <CLAVE> por el usuario y clave de conexión.
--Se debe sustituir <TABLESPACE> por el espacio de tabla usado por las tablas afectadas.
--Se debe sustituir <NAME_NODE> por el nombre del nodo, suele igual que el nombre de la base de datos pero no tiene porqué.
--Este script debe ser ejecutado desde el 'procesador de línea de mandatos' de la siguiente forma:
--db2 -f <nombre_script> -v
--ej: db2 -f c:\Actualizacion.sql -v
--
--

CONNECT TO "<NAME_NODE>" USER "<USUARIO>" USING "<CLAVE>"

--
-- Tabla necesaria por el conector de gestión documental con Sharepoint.
--

CREATE TABLE doc_path (
    guid VARCHAR(256) NOT NULL, 
    path CLOB NOT NULL 
); 

ALTER TABLE doc_path ADD CONSTRAINT pk_doc_path PRIMARY KEY (guid);

CONNECT RESET