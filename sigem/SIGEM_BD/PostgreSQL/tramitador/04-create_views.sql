CREATE OR REPLACE VIEW spac_sq_sequences AS
    SELECT sequences_names.sequence_name, currval((sequences_names.relid)::regclass) AS sequence_id FROM (SELECT pg_statio_user_sequences.relid, pg_statio_user_sequences.relname AS sequence_name FROM pg_statio_user_sequences) sequences_names;

CREATE OR REPLACE VIEW spac_wl_batchtask AS 
 SELECT spac_tramitaciones_agrupadas.id AS id_agrupacion, spac_tramitaciones_agrupadas.id_resp, spac_tramitaciones_agrupadas.estado, spac_tramitaciones_agrupadas.fecha_creacion, spac_p_fases.nombre AS fase, spac_tramitaciones_agrupadas.id_ultimo_tramite, spac_tramitaciones_agrupadas.id_ultimo_template, spac_tramitaciones_agrupadas.id_ultimo_tipodoc, spac_ct_procedimientos.cod_pcd, spac_ct_procedimientos.nombre AS procedimiento, spac_tramitaciones_agrupadas.id_fase
   FROM spac_tramitaciones_agrupadas, spac_p_fases, spac_ct_procedimientos
  WHERE spac_tramitaciones_agrupadas.id_fase = spac_p_fases.id AND spac_p_fases.id_pcd = spac_ct_procedimientos.id;
    
    
CREATE OR REPLACE VIEW spac_wl_proc AS 
	SELECT fases.id_exp AS id, fases.numexp, fases.id_pcd, fases.id_fase AS id_stagepcd, fases.id AS id_stage, fasespcd.nombre AS name_stage, fases.fecha_inicio AS initdate, fases.fecha_limite AS limitdate, fases.id_resp AS resp, fases.tipo FROM spac_fases fases, spac_p_fases fasespcd WHERE fases.id_fase = fasespcd.id AND fases.estado = 1;

CREATE OR REPLACE VIEW  SPAC_WL_PCD AS
SELECT
 PCD.ID, PCD.NOMBRE AS NAME, PCD.NVERSION AS NVERSION, FASES.ID_RESP AS RESP, FASES.TIPO
FROM
 SPAC_FASES FASES, SPAC_P_PROCEDIMIENTOS PCD
WHERE
 FASES.ESTADO = 1 AND PCD.ID = FASES.ID_PCD
GROUP BY
 PCD.ID, PCD.NOMBRE, PCD.NVERSION, FASES.ID_RESP, FASES.TIPO
UNION
SELECT
 PCD.ID, PCD.NOMBRE AS NAME, PCD.NVERSION AS NVERSION, SPC_PERMS.ID_RESP AS RESP, FASES.TIPO
FROM
 SPAC_FASES FASES, SPAC_P_PROCEDIMIENTOS PCD, SPAC_PERMISOS SPC_PERMS
WHERE
 FASES.ESTADO = 1 AND PCD.ID = FASES.ID_PCD
 AND
 SPC_PERMS.TP_OBJ = 1 AND SPC_PERMS.ID_OBJ = FASES.ID_PCD
GROUP BY
 PCD.ID, PCD.NOMBRE, PCD.NVERSION, SPC_PERMS.ID_RESP, FASES.TIPO;
 
CREATE OR REPLACE  VIEW SPAC_WL_TASK  AS
	SELECT TRAM.ID, TRAM.NOMBRE AS NAME, TRAM.ID_TRAMITE AS ID_TASK, TRAM.ID_CTTRAMITE AS ID_CTTASK, TRAM.ID_RESP AS RESP, TRAM.ID_PCD AS ID_PROC, PCD.NOMBRE AS NAME_PROC, TRAM.ID_FASE_PCD AS ID_STAGE, P_FASES.NOMBRE AS NAME_STAGE, TRAM.ID_EXP, TRAM.ID_FASE_EXP, TRAM.NUMEXP, TRAM.FECHA_INICIO AS INITDATE, TRAM.FECHA_LIMITE AS LIMITDATE, TRAM.ESTADO AS STATUS, TRAM.TIPO, PRO.ID AS ID_SUBPROCESO, PRO.ID_PCD AS ID_SUBPCD FROM SPAC_TRAMITES TRAM LEFT JOIN SPAC_PROCESOS PRO ON TRAM.ID_SUBPROCESO = PRO.ID, SPAC_P_FASES P_FASES, SPAC_P_PROCEDIMIENTOS PCD WHERE TRAM.ESTADO = 1 AND TRAM.ID_PCD = PCD.ID AND TRAM.ID_FASE_PCD = P_FASES.ID
	UNION
	SELECT TRAM.ID, TRAM.NOMBRE AS NAME, TRAM.ID_TRAMITE AS ID_TASK, TRAM.ID_CTTRAMITE AS ID_CTTASK, FASES.ID_RESP AS RESP,TRAM.ID_PCD AS ID_PROC, PCD.NOMBRE AS NAME_PROC, TRAM.ID_FASE_PCD AS ID_STAGE, P_FASES.NOMBRE AS NAME_STAGE, TRAM.ID_EXP, TRAM.ID_FASE_EXP, TRAM.NUMEXP, TRAM.FECHA_INICIO AS INITDATE, TRAM.FECHA_LIMITE AS LIMITDATE, TRAM.ESTADO AS STATUS, TRAM.TIPO, PRO.ID AS ID_SUBPROCESO, PRO.ID_PCD AS ID_SUBPCD FROM SPAC_TRAMITES TRAM, SPAC_PROCESOS PRO, SPAC_FASES FASES, SPAC_P_FASES P_FASES, SPAC_P_PROCEDIMIENTOS PCD WHERE TRAM.ESTADO = 1 AND TRAM.TIPO = 2 AND TRAM.ID_SUBPROCESO = PRO.ID AND FASES.ID_EXP = PRO.ID AND TRAM.ID_SUBPROCESO = PRO.ID AND TRAM.ID_PCD = PCD.ID AND TRAM.ID_FASE_PCD = P_FASES.ID;

CREATE OR REPLACE VIEW SPAC_WL_CLOSE_TASK AS
	SELECT t.id,t.nombre AS name,t.id AS id_task,t.id_tram_ctl AS id_cttask,t.id_resp_closed AS resp,p.id AS id_proc,CASE WHEN (t.id_subproceso IS NULL) THEN 1 ELSE 2 END AS tipo, p.nombre AS name_proc,t.id_fase_pcd AS id_stage,p.nombre AS name_stage,pc.id AS id_exp,t.id_fase_exp,t.numexp,t.id_subproceso AS id_subpcd,t.fecha_inicio AS initdate,t.fecha_cierre AS enddate
	FROM spac_dt_tramites t,spac_ct_procedimientos p,spac_p_fases f,spac_procesos pc
	WHERE t.numexp = pc.numexp AND pc.id_pcd = p.id AND t.id_fase_pcd = f.id AND t.estado = 3;
	
CREATE OR REPLACE VIEW SPAC_WL_ACTIVITY AS 
  SELECT SPAC_FASES.ID, SPAC_P_FASES.NOMBRE, SPAC_FASES.ID_FASE_BPM, SPAC_FASES.ID_EXP, SPAC_FASES.ID_PCD, SPAC_FASES.ID_FASE, SPAC_FASES.NUMEXP, SPAC_FASES.FECHA_INICIO, SPAC_FASES.FECHA_LIMITE, SPAC_FASES.ESTADO, SPAC_FASES.OBSERVACIONES, SPAC_FASES.ID_RESP, SPAC_FASES.RESP, SPAC_FASES.ID_RESP_SEC, SPAC_FASES.RESP_SEC, SPAC_FASES.ESTADO_PLAZO, SPAC_FASES.NUM_DIAS_PLAZO, SPAC_FASES.ID_CALENDARIO, SPAC_FASES.FECHA_INICIO_PLAZO, SPAC_FASES.ESTADO_ANTERIOR, SPAC_TRAMITES.ID AS ID_TRAMITE, SPAC_TRAMITES.ID_EXP AS ID_EXP_TRAMITE, SPAC_TRAMITES.ID_PCD AS ID_PCD_TRAMITE, SPAC_TRAMITES.ID_FASE_EXP AS ID_FASE_TRAMITE, SPAC_TRAMITES.ID_FASE_PCD AS ID_FASE_PCD_TRAMITE, SPAC_TRAMITES.ID_TRAMITE AS ID_TRAMITE_PCD_TRAMITE, SPAC_TRAMITES.ID_CTTRAMITE AS ID_CTTRAMITE_TRAMITE
  FROM SPAC_FASES, SPAC_TRAMITES, SPAC_P_FASES
  WHERE SPAC_FASES.ESTADO = 1 AND SPAC_FASES.TIPO = 2 AND SPAC_FASES.ID_FASE = SPAC_P_FASES.ID AND SPAC_FASES.ID_EXP = SPAC_TRAMITES.ID_SUBPROCESO;


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
  
CREATE OR REPLACE VIEW spac_pcd_permisos AS
SELECT  id_pcd , uid_usr, permiso
FROM spac_ss_permisos 
UNION
SELECT id_obj, id_resp, permiso
FROM spac_permisos WHERE tp_obj=1;


CREATE VIEW spac_v_avisos_electronicos AS
	SELECT A.*, F.NOMBRE AS NOMBRE_FASE, T.NOMBRE AS NOMBRE_TRAMITE FROM SPAC_AVISOS_ELECTRONICOS A LEFT OUTER JOIN SPAC_P_FASES F 
	ON A.ID_FASE_PCD = F.ID LEFT OUTER JOIN SPAC_P_TRAMITES T 
	ON A.ID_TRAMITE_PCD = T.ID;

--Vista para obtener las fases activas que estén en la papelera
CREATE  VIEW spac_wl_proc_trash AS 
  SELECT fases.id_exp AS id, fases.numexp, fases.id_pcd, fases.id_fase AS id_stagepcd, fases.id AS id_stage, fasespcd.nombre AS name_stage, fases.fecha_inicio AS initdate, fases.fecha_limite AS limitdate, fases.id_resp AS resp, fases.tipo
  FROM spac_fases fases, spac_p_fases fasespcd
  WHERE fases.id_fase = fasespcd.id AND fases.estado = 5;
