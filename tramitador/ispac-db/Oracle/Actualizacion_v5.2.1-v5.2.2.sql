-----------------------------------
-- Actualización de v5.2.1 a v5.2.2
-----------------------------------

--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '5.2.2', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '5.2.2', current_timestamp);


--
-- Eliminar tablas y secuencias de organismos
--
DROP TABLE spac_organismos;
DROP SEQUENCE spac_sq_id_organismos;
DROP TABLE spac_organismos_numexp;


--
-- Tablas para Contador de número de expedientes
--


CREATE TABLE SPAC_NUMEXP_CONTADOR
(
  ANIO NUMBER NOT NULL,
  CONTADOR NUMBER,
  FORMATO VARCHAR2(32)
);
ALTER TABLE SPAC_NUMEXP_CONTADOR
    ADD CONSTRAINT PK_SPAC_NUMEXP_CONTADOR PRIMARY KEY (ANIO);

-- Inicializar el contador del número de expediente
INSERT INTO SPAC_NUMEXP_CONTADOR(ANIO, CONTADOR, FORMATO) VALUES (2008, 1, 'EXP$YR$/$NM$');

-- Añadir columna para el identificador de notificación en los documentos
ALTER TABLE SPAC_DT_DOCUMENTOS ADD ID_NOTIFICACION VARCHAR2(64);

-- Añadir estados de notificación a la tabla de validación
INSERT INTO spac_tbl_006 (id, valor, sustituto, vigente) VALUES (SPAC_SQ_SPAC_TBL_006.NEXTVAL, 'RE', 'Rechazada', 1);
INSERT INTO spac_tbl_006 (id, valor, sustituto, vigente) VALUES (SPAC_SQ_SPAC_TBL_006.NEXTVAL, 'ER', 'Error', 1);