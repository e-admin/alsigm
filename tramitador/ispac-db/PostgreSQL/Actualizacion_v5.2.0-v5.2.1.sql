

-----------------------------------
-- Actualización de v5.2 a v5.2.1
-----------------------------------

-- Información de versión
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '5.2.1', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '5.2.1', current_timestamp);

-- Secuencia para los ids de la tabla
CREATE SEQUENCE spac_sq_id_pctosfirma INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

-- Establecer el tipo de un circuit de firmas (generico o especifico)
ALTER TABLE spac_ctos_firma_cabecera ADD tipo smallint;

-- Indica que por defecto el tipo será genérico: 1- genérico , 2- especifico
UPDATE spac_ctos_firma_cabecera SET tipo='1';

-- Crear la tabla para asociar el circuito de firmas al procedimiento
CREATE TABLE spac_p_ctosfirma (
    id integer NOT NULL,
    id_circuito integer,
	id_pcd integer
);

ALTER TABLE ONLY spac_p_ctosfirma
    ADD CONSTRAINT pk_p_ctosfirma PRIMARY KEY (id);

update spac_p_tramites set id_pcd_sub=null where id_pcd_sub=0;