-----------------------------------
-- Actualización de v5.2.5 a v5.2.6
-----------------------------------

--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '5.2.6', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '5.2.6', current_timestamp);


--
-- Añadir campos a la tabla SPAC_P_PROCEDIMIENTOS
--
ALTER TABLE spac_p_procedimientos ADD NOMBRE_CRT VARCHAR2(250);
ALTER TABLE spac_p_procedimientos ADD NOMBRE_UPD VARCHAR2(250);


--
-- Tamaños de los campos valor de las tablas de validación utilizadas en la definición de los procedimientos
-- y tamaños de los campos asociados a esas tablas en spac_ct_procedimientos
--
ALTER TABLE spac_vldtbl_acts_funcs
    MODIFY valor VARCHAR2(2);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 185;
ALTER TABLE spac_ct_procedimientos
	MODIFY act_func VARCHAR2(2);

ALTER TABLE spac_vldtbl_recursos
    MODIFY valor VARCHAR2(2);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 190;

ALTER TABLE spac_vldtbl_mats_comp
    MODIFY valor VARCHAR2(2);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 187;

ALTER TABLE spac_vldtbl_sit_tlm
    MODIFY valor VARCHAR2(2);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 191;

ALTER TABLE spac_tbl_002
    MODIFY valor VARCHAR2(4);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>4</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 105;

ALTER TABLE spac_vldtbl_forma_inic
    MODIFY valor VARCHAR2(1);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>1</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 188;

ALTER TABLE spac_tbl_001
    MODIFY valor VARCHAR2(1);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>1</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 104;

ALTER TABLE spac_vldtbl_efec_slncio
    MODIFY valor VARCHAR2(1);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>1</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 189;

ALTER TABLE spac_vldtbl_sist_productores
    MODIFY valor VARCHAR2(2);
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field></fields></entity>'
	WHERE id = 113;


--
-- Añadir campo de orden a los trámites de un procedimiento
--
ALTER TABLE spac_p_tramites ADD ORDEN NUMBER;
UPDATE spac_p_tramites SET orden=id;
	
	
CREATE OR REPLACE VIEW SPAC_WL_TASK AS 
 	SELECT TRAM.ID, TRAM.NOMBRE AS NAME, TRAM.ID_TRAMITE AS ID_TASK, TRAM.ID_CTTRAMITE AS ID_CTTASK, TRAM.ID_RESP AS RESP, TRAM.ID_PCD AS ID_PROC, PCD.NOMBRE AS NAME_PROC, TRAM.ID_FASE_PCD AS ID_STAGE, P_FASES.NOMBRE AS NAME_STAGE, TRAM.ID_EXP, TRAM.NUMEXP, TRAM.FECHA_INICIO AS INITDATE, TRAM.FECHA_LIMITE AS LIMITDATE, TRAM.ESTADO AS STATUS, TRAM.TIPO, PROC.ID AS ID_SUBPROCESO, PROC.ID_PCD AS ID_SUBPCD FROM SPAC_TRAMITES TRAM LEFT JOIN SPAC_PROCESOS PROC ON TRAM.ID_SUBPROCESO = PROC.ID, SPAC_P_FASES P_FASES, SPAC_P_PROCEDIMIENTOS PCD WHERE TRAM.ESTADO = 1 AND TRAM.ID_PCD = PCD.ID AND TRAM.ID_FASE_PCD = P_FASES.ID
	UNION 
	SELECT TRAM.ID, TRAM.NOMBRE AS NAME, TRAM.ID_TRAMITE AS ID_TASK, TRAM.ID_CTTRAMITE AS ID_CTTASK, FASES.ID_RESP AS RESP, TRAM.ID_PCD AS ID_PROC, PCD.NOMBRE AS NAME_PROC, TRAM.ID_FASE_PCD AS ID_STAGE, P_FASES.NOMBRE AS NAME_STAGE, TRAM.ID_EXP, TRAM.NUMEXP, TRAM.FECHA_INICIO AS INITDATE, TRAM.FECHA_LIMITE AS LIMITDATE, TRAM.ESTADO AS STATUS, TRAM.TIPO, PROC.ID AS ID_SUBPROCESO, PROC.ID_PCD AS ID_SUBPCD FROM SPAC_TRAMITES TRAM, SPAC_PROCESOS PROC, SPAC_FASES FASES, SPAC_P_FASES P_FASES, SPAC_P_PROCEDIMIENTOS PCD WHERE TRAM.ESTADO = 1 AND TRAM.TIPO = 2 AND TRAM.ID_SUBPROCESO = PROC.ID AND FASES.ID_EXP = PROC.ID AND TRAM.ID_SUBPROCESO = PROC.ID AND TRAM.ID_PCD = PCD.ID AND TRAM.ID_FASE_PCD = P_FASES.ID;

	
--
-- Añadir bloqueos a nivel de entidad en el expediente (la tabla spac_s_bloqueos tiene que estar vacía)
--
DELETE FROM SPAC_S_BLOQUEOS;
ALTER TABLE SPAC_S_BLOQUEOS ADD NUMEXP VARCHAR2(30) NOT NULL;
ALTER TABLE SPAC_S_BLOQUEOS DROP CONSTRAINT PK_SPAC_S_BLOQUEOS;
ALTER TABLE SPAC_S_BLOQUEOS
    ADD CONSTRAINT PK_SPAC_S_BLOQUEOS PRIMARY KEY (TP_OBJ, ID_OBJ, NUMEXP);	
-- Nueva regla

INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (15, 'ListadoMultiplesRegistros', 'Obtención de n campos de n registros', 'ieci.tdw.ispac.api.rule.docs.tags.ListadoMultiplesRegistrosRule', 1);

UPDATE spac_ct_reglas SET nombre = 'FechaActual' WHERE ID = 11;


--Actualizamos la secuencia
ALTER SEQUENCE SPAC_SQ_ID_CTREGLAS increment by 99;
SELECT SPAC_SQ_ID_CTREGLAS.NEXTVAL FROM dual;
ALTER SEQUENCE SPAC_SQ_ID_CTREGLAS increment by 1;

	
	