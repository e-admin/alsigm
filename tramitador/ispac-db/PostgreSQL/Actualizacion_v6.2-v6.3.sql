-----------------------------------
-- Actualización de v6.2 a v6.3
-----------------------------------


--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '6.3', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '6.3', current_timestamp);



--
-- Se añaden dos columnas más a spac_procesos spac_fases y spac_tramites para guardar el nombre del responsable 
-- y el nombre del responsable secundario.
--
ALTER TABLE SPAC_PROCESOS ADD COLUMN RESP CHARACTER VARYING(250);
ALTER TABLE SPAC_FASES ADD COLUMN RESP CHARACTER VARYING (250);
ALTER TABLE SPAC_TRAMITES ADD COLUMN RESP CHARACTER VARYING (250);
ALTER TABLE SPAC_PROCESOS ADD COLUMN RESP_SEC CHARACTER VARYING(250);
ALTER TABLE SPAC_FASES ADD COLUMN RESP_SEC CHARACTER VARYING (250);
ALTER TABLE SPAC_TRAMITES ADD COLUMN RESP_SEC CHARACTER VARYING (250);


--
-- Actualización de la vista de plazos
--

CREATE OR REPLACE VIEW spac_deadline AS 
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

CREATE INDEX spac_expedientes_ix_numexp ON spac_expedientes (numexp);
CREATE INDEX spac_dt_documentos_ix_numexp ON spac_dt_documentos (numexp);
CREATE INDEX spac_dt_tramites_ix_numexp ON spac_dt_tramites (numexp);
CREATE INDEX spac_dt_tramites_ix_idtramexp ON  spac_dt_tramites (id_tram_exp);
CREATE INDEX spac_dt_int_ix_numexp ON  spac_dt_intervinientes (numexp);

CREATE INDEX spac_procesos_ix_numexp ON spac_procesos (numexp);
CREATE INDEX spac_procesos_ix_idresp ON spac_procesos (id_resp);
CREATE INDEX spac_fases_ix_numexp ON spac_fases (numexp);
CREATE INDEX spac_fases_ix_idresp ON spac_fases (id_resp);
CREATE INDEX SPAC_tramites_ix_numexp ON SPAC_tramites (numexp);
CREATE INDEX SPAC_tramites_ix_idresp ON SPAC_tramites (id_resp);


