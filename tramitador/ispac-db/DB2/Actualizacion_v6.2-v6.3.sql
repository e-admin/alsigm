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
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (NEXTVAL FOR spac_sq_id_infosistema, 'VERSIONBD', '6.3', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (NEXTVAL FOR spac_sq_id_infosistema, 'VERSIONAPP', '6.3', current_timestamp);


--
-- Se añaden dos columnas más a spac_procesos spac_fases y spac_tramites para guardar el nombre del responsable 
-- y el nombre del responsable secundario.
--
ALTER TABLE SPAC_PROCESOS ADD COLUMN RESP VARCHAR(250);
ALTER TABLE SPAC_FASES ADD COLUMN RESP VARCHAR(250);
ALTER TABLE SPAC_TRAMITES ADD COLUMN RESP VARCHAR(250);
ALTER TABLE SPAC_PROCESOS ADD COLUMN RESP_SEC VARCHAR(250);
ALTER TABLE SPAC_FASES ADD COLUMN RESP_SEC VARCHAR(250);
ALTER TABLE SPAC_TRAMITES ADD COLUMN RESP_SEC VARCHAR(250);


--
-- Actualización de la vista de plazos
--
DROP VIEW spac_deadline;
CREATE VIEW spac_deadline AS 
SELECT obj.numexp, obj.fecha_limite, procedimiento.nombre AS nombre_pcd, obj.id_resp, obj.id AS ID_OBJETO, 'Resolución Expediente' AS descripcion, 1 AS tipo
   FROM spac_procesos obj, spac_ct_procedimientos procedimiento
  WHERE obj.id_pcd = procedimiento.id AND obj.estado = 1 AND obj.fecha_limite IS NOT NULL
UNION 
 SELECT obj.numexp, obj.fecha_limite, procedimiento.nombre AS nombre_pcd, obj.id_resp, obj.id AS ID_OBJETO, 'Resolución Fase' AS descripcion, 2 AS tipo
   FROM spac_fases obj, spac_ct_procedimientos procedimiento
  WHERE obj.id_pcd = procedimiento.id AND obj.estado = 1 AND obj.tipo=1 AND obj.fecha_limite IS NOT NULL
UNION 
 SELECT obj.numexp, obj.fecha_limite, procedimiento.nombre AS nombre_pcd, obj.id_resp, obj.id AS ID_OBJETO, obj.nombre AS descripcion, 3 AS tipo
   FROM spac_tramites obj, spac_ct_procedimientos procedimiento
  WHERE obj.id_pcd = procedimiento.id AND obj.estado = 1 AND obj.fecha_limite IS NOT NULL
UNION 
 SELECT obj.numexp, obj.fecha_limite, procedimiento.nombre AS nombre_pcd, obj.id_resp, obj.id AS ID_OBJETO, 'Resolución Actividad' AS descripcion, 4 AS tipo
   FROM spac_fases obj, spac_p_procedimientos procedimiento
  WHERE obj.id_pcd = procedimiento.id AND obj.estado = 1 AND obj.tipo=2 AND obj.fecha_limite IS NOT NULL;


--
-- Creación de índices
--

CREATE INDEX ix_exps_numexp ON spac_expedientes (numexp);
CREATE INDEX ix_dtdocs_numexp ON spac_dt_documentos (numexp);
CREATE INDEX ix_dttram_numexp ON spac_dt_tramites (numexp);
CREATE INDEX ix_dttram_idtrex ON  spac_dt_tramites (id_tram_exp);
CREATE INDEX ix_dtint_numexp ON  spac_dt_intervinientes (numexp);

CREATE INDEX ix_procs_numexp ON spac_procesos (numexp);
CREATE INDEX ix_procs_idresps ON spac_procesos (id_resp);
CREATE INDEX ix_fases_numexp ON spac_fases (numexp);
CREATE INDEX ix_fases_idresps ON spac_fases (id_resp);
CREATE INDEX ix_trams_numexp ON SPAC_tramites (numexp);
CREATE INDEX ix_trams_idresps ON SPAC_tramites (id_resp);



CONNECT RESET