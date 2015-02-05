-----------------------------------
-- Actualización de v5.2.7 a v5.3
-----------------------------------

--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '5.3', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '5.3', current_timestamp);


--
-- Relación múltiple entre sustitutos y períodos de fechas
--
CREATE SEQUENCE SPAC_SQ_ID_SSSUSTITUCIONFECHA START WITH 1 INCREMENT BY 1 MINVALUE 1 NOCACHE NOCYCLE ORDER;
CREATE TABLE SPAC_SS_SUSTITUCIONFECHA (
	ID NUMBER NOT NULL,
	ID_SUSTITUCION NUMBER,
	ID_FECHSUSTITUCION NUMBER
);
ALTER TABLE SPAC_SS_SUSTITUCIONFECHA
	ADD CONSTRAINT PK_SPAC_SS_SUSTITUCIONFECHA PRIMARY KEY (ID);
ALTER TABLE SPAC_SS_SUSTITUCIONFECHA
    ADD CONSTRAINT U_SPAC_SS_SUSTITUCIONFECHA UNIQUE (ID_SUSTITUCION, ID_FECHSUSTITUCION);
ALTER TABLE SPAC_SS_SUSTITUCION
    ADD CONSTRAINT U_SPAC_SS_SUSTITUCION UNIQUE (UID_SUSTITUTO, UID_SUSTITUIDO);
--
-- ¡¡ ATENCIÓN !!
--
-- Ejecutar Actualizacion_v5.2.7-v5.3_scripts.zip
--
-- y a continuación
--
-- UPDATE SPAC_SS_FECHSUSTITUCIONES
-- SET UID_SUSTITUIDO = NULL;
-- ALTER TABLE SPAC_SS_FECHSUSTITUCIONES
-- DROP UID_SUSTITUIDO;


--
-- Variable global del identificador de la Fase Archivo en el catalogo
--
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.nextval, 'ID_STAGE_ARCHIVO', '4', 'Identificador de la fase Archivo en el catálogo');


--
-- Registro entrada/salida en documentos
--
ALTER TABLE SPAC_DT_DOCUMENTOS
	MODIFY ORIGEN_ID VARCHAR2(20);
ALTER TABLE SPAC_DT_DOCUMENTOS
    MODIFY ORIGEN VARCHAR2(250);
ALTER TABLE SPAC_DT_DOCUMENTOS
    MODIFY DESTINO_ID VARCHAR2(20);


--
-- Eliminar regla InicializarValoresExpediente
--
DELETE FROM spac_ct_reglas WHERE id=4;
DELETE FROM spac_p_eventos WHERE id_regla=4;

--
-- Eliminar regla SetFechaCierreRule
--
DELETE FROM spac_p_eventos WHERE id_regla=10;

--
-- Eliminar regla AsignarTipoRegistro
--
DELETE FROM spac_ct_reglas WHERE id=7;
DELETE FROM spac_p_eventos WHERE id_regla=7;


--
-- Variable global con los Idiomas soportados
--
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (SPAC_SQ_ID_VARS.nextval, 'LANGUAGES', 'es;gl;eu;ca', 'Idiomas soportados');

