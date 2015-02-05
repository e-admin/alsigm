
-----------------------------------
-- Actualización de v5.2 a v5.2.1
-----------------------------------

-- Información de versión
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONBD', '5.2.1', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (SPAC_SQ_ID_INFOSISTEMA.NEXTVAL, 'VERSIONAPP', '5.2.1', current_timestamp);

-- Secuencia para los ids de la tabla
CREATE SEQUENCE SPAC_SQ_ID_PCTOSFIRMA START WITH 1 INCREMENT BY 1 MINVALUE 1 NOCACHE  NOCYCLE  ORDER ;

-- Establecer el tipo de un circuito de firmas (generico o especifico)
ALTER TABLE SPAC_CTOS_FIRMA_CABECERA ADD TIPO NUMBER;

-- Indica que por defecto el tipo será genérico: 1- genérico , 2- especifico
UPDATE SPAC_CTOS_FIRMA_CABECERA SET tipo='1';

-- Crear la tabla para asociar el circuito de firmas al procedimiento
CREATE TABLE SPAC_P_CTOSFIRMA (
    ID NUMBER NOT NULL,
    ID_CIRCUITO NUMBER,
    ID_PCD NUMBER
);

ALTER TABLE SPAC_P_CTOSFIRMA
    ADD CONSTRAINT PK_SPAC_P_CTOSFIRMA PRIMARY KEY (id);

update spac_p_tramites set id_pcd_sub=null where id_pcd_sub=0;