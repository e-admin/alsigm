-----------------------------------
-- Actualización de v5.3 a v5.4
-----------------------------------

--
-- ¡¡ ATENCIÓN !!
--
-- En los scripts de instalación de la base de datos nueva, se ha
-- actualizado el formulario de la entidad de EXPEDIENTES en la 
-- tabla SPAC_CT_APLICACIONES.
--
-- Como las sentencias de actualización dan error por ser el texto
-- demasiado grande, habrá que hacer una actualización de estos
-- dos formularios a mano: 
--
-- Formulario para expedientes
--
--     Actualizacion_v5.3-v5.4_Files\expedientForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=1;
--     Aumentar la versión del formulario
--
-- Formulario para participantes
--
--     Actualizacion_v5.3-v5.4_Files\thirdForm.jsp 
--         --> SPAC_CT_APLICACIONES.FRM_JSP cuando id=3;
--     Aumentar la versión del formulario
--
-- Lo mismo ocurre con el formulario de búsqueda por defecto:
--
--     Actualizacion_v5.3-v5.4_Files\formularioBusqueda.xml 
--         --> SPAC_CT_FRMBUSQUEDA.FRM_JSP cuando id=1;
--     Aumentar la versión del formulario
--
-- También se han modificado las siguientes plantillas en 
-- la tabla SPAC_P_BLP:
--
--     Actualizacion_v5.3-v5.4_Files\plantillas\18.doc
--     Actualizacion_v5.3-v5.4_Files\plantillas\25.doc
--     Actualizacion_v5.3-v5.4_Files\plantillas\38.doc
--     Actualizacion_v5.3-v5.4_Files\plantillas\39.doc
--

--
-- Información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '5.4', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '5.4', current_timestamp);


--
-- Añadir columna para el tipo del formulario de busqueda (generico/especifico)
--
ALTER TABLE SPAC_CT_FRMBUSQUEDA ADD TIPO NUMBER(3);


--
-- Establecemos el tipo de los formublarios de busqueda ya existentes a generico
--
UPDATE SPAC_CT_FRMBUSQUEDA SET TIPO = 1;


--
-- Asociacion de formulario de busqueda a objetos de organizacion
--
CREATE TABLE SPAC_CT_FRMBUSQUEDA_ORG
(
  ID NUMBER NOT NULL,
  ID_SEARCHFRM NUMBER,
  UID_USR VARCHAR2(32),
  CONSTRAINT PK_SPAC_CT_FRMBUSQUEDA_ORG PRIMARY KEY (ID)
);

CREATE SEQUENCE SPAC_SQ_ID_CTFRMBUSQUEDA_ORG START WITH 1 INCREMENT BY 1 MINVALUE 1 NOCACHE  NOCYCLE  ORDER ;


--
-- Formularios en solo lectura
--
ALTER TABLE SPAC_P_ENTIDADES ADD FRM_READONLY NUMBER(1);
ALTER TABLE SPAC_P_FRMFASES ADD FRM_READONLY NUMBER(1);
ALTER TABLE SPAC_P_FRMTRAMITES ADD FRM_READONLY NUMBER(1);


--
-- Cambio de nombres de columnas
--

-- Renombrar los campos VERSION
ALTER TABLE SPAC_CT_PROCEDIMIENTOS RENAME COLUMN version TO NVERSION; 
ALTER TABLE SPAC_P_PROCEDIMIENTOS RENAME COLUMN version TO NVERSION;
ALTER TABLE SPAC_EXPEDIENTES RENAME COLUMN version TO NVERSION;

-- Renombrar los campos TIMESTAMP
ALTER TABLE PUB_HITOS_ACTIVOS RENAME COLUMN timestamp TO FECHA; 
ALTER TABLE SPAC_AVISOS_ELECTRONICOS RENAME COLUMN timestamp TO FECHA; 
ALTER TABLE SPAC_CT_ENTIDADES RENAME COLUMN timestamp TO FECHA; 

-- Renombrar los campos LANGUAGE
ALTER TABLE SPAC_CT_ENTIDADES_RESOURCES RENAME COLUMN language TO IDIOMA; 

-- Renombrar los campos VALUE
ALTER TABLE SPAC_CT_ENTIDADES_RESOURCES RENAME COLUMN value TO VALOR; 


--
-- Cambio de tipo de columnas
--
ALTER TABLE SPAC_GESTION_FASES MODIFY ID_APLICACION_GESTION NUMBER;