

-----------------------------------
-- Actualización de v5.2.5 a v5.2.6
-----------------------------------

--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '5.2.6', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '5.2.6', current_timestamp);


--
-- Añadir campos a la tabla SPAC_P_PROCEDIMIENTOS
--
ALTER TABLE spac_p_procedimientos ADD nombre_crt character varying(250);
ALTER TABLE spac_p_procedimientos ADD nombre_upd character varying(250);


--
-- Tamaños de los campos valor de las tablas de validación utilizadas en la definición de los procedimientos
-- y tamaños de los campos asociados a esas tablas en spac_ct_procedimientos
--
ALTER TABLE spac_vldtbl_acts_funcs
    ALTER COLUMN valor TYPE character varying(2);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 185;
ALTER TABLE spac_ct_procedimientos
	ALTER COLUMN act_func TYPE character varying(2);

ALTER TABLE spac_vldtbl_recursos
    ALTER COLUMN valor TYPE character varying(2);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 190;

ALTER TABLE spac_vldtbl_mats_comp
    ALTER COLUMN valor TYPE character varying(2);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 187;

ALTER TABLE spac_vldtbl_sit_tlm
    ALTER COLUMN valor TYPE character varying(2);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 191;

ALTER TABLE spac_tbl_002
    ALTER COLUMN valor TYPE character varying(4);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>4</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 105;

ALTER TABLE spac_vldtbl_forma_inic
    ALTER COLUMN valor TYPE character varying(1);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>1</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 188;

ALTER TABLE spac_tbl_001
    ALTER COLUMN valor TYPE character varying(1);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>1</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 104;

ALTER TABLE spac_vldtbl_efec_slncio
    ALTER COLUMN valor TYPE character varying(1);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>1</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 189;

ALTER TABLE spac_vldtbl_sist_productores
    ALTER COLUMN valor TYPE character varying(2);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 113;


--
-- Añadir campo de orden a los trámites de un procedimiento
--
ALTER TABLE spac_p_tramites ADD orden integer;
UPDATE spac_p_tramites SET orden=id;


	
CREATE OR REPLACE VIEW spac_wl_task AS 
 SELECT tram.id, tram.nombre AS name, tram.id_tramite AS id_task, tram.id_cttramite AS id_cttask, tram.id_resp AS resp, tram.id_pcd AS id_proc, pcd.nombre AS name_proc, tram.id_fase_pcd AS id_stage, p_fases.nombre AS name_stage, tram.id_exp, tram.numexp, tram.fecha_inicio AS initdate, tram.fecha_limite AS limitdate, tram.estado AS status, tram.tipo, proc.id AS id_subproceso, proc.id_pcd AS id_subpcd FROM spac_tramites tram LEFT JOIN spac_procesos proc ON tram.id_subproceso = proc.id, spac_p_fases p_fases, spac_p_procedimientos pcd WHERE tram.estado = 1 AND tram.id_pcd = pcd.id AND tram.id_fase_pcd = p_fases.id
UNION 
 SELECT tram.id, tram.nombre AS name, tram.id_tramite AS id_task, tram.id_cttramite AS id_cttask, fases.id_resp AS resp, tram.id_pcd AS id_proc, pcd.nombre AS name_proc, tram.id_fase_pcd AS id_stage, p_fases.nombre AS name_stage, tram.id_exp, tram.numexp, tram.fecha_inicio AS initdate, tram.fecha_limite AS limitdate, tram.estado AS status, tram.tipo, proc.id AS id_subproceso, proc.id_pcd AS id_subpcd FROM spac_tramites tram, spac_procesos proc, spac_fases fases, spac_p_fases p_fases, spac_p_procedimientos pcd WHERE tram.estado = 1 AND tram.tipo = 2 AND tram.id_subproceso = proc.id AND fases.id_exp = proc.id AND tram.id_subproceso = proc.id AND tram.id_pcd = pcd.id AND tram.id_fase_pcd = p_fases.id;


--
-- Añadir bloqueos a nivel de entidad en el expediente (la tabla spac_s_bloqueos tiene que estar vacía)
--
DELETE FROM spac_s_bloqueos;
ALTER TABLE spac_s_bloqueos ADD numexp character varying(30) NOT NULL;
ALTER TABLE spac_s_bloqueos DROP CONSTRAINT pk_spac_s_bloqueos;
ALTER TABLE ONLY spac_s_bloqueos
    ADD CONSTRAINT pk_spac_s_bloqueos PRIMARY KEY (tp_obj, id_obj, numexp);
	
-- Nueva regla	
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (15, 'ListadoMultiplesRegistros', 'Obtención de n campos de n registros', 'ieci.tdw.ispac.api.rule.docs.tags.ListadoMultiplesRegistrosRule', 1);
SELECT pg_catalog.setval('spac_sq_id_ctreglas', 100, true);

-- Se actualiza el nombre de la regla que retorno la fecha actual
UPDATE spac_ct_reglas SET nombre = 'FechaActual' WHERE ID = 11;


	
	