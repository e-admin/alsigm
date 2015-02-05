CREATE TABLE sgm_adm_acciones (
    id integer NOT NULL,
    nombre character varying(256) NOT NULL,
    clase_config character varying(256) NOT NULL,
    clase_exec character varying(256) NOT NULL,
    info_adicional CLOB
);

ALTER TABLE sgm_adm_acciones ADD CONSTRAINT sgm_adm_acciones_pk PRIMARY KEY (id);

CREATE UNIQUE INDEX i_sgm_adm_accnombre ON sgm_adm_acciones (nombre);

INSERT INTO sgm_adm_acciones (id, nombre, clase_config, clase_exec, info_adicional) VALUES (1, 'Comparar/Importar reglas', 'ieci.tecdoc.sgm.admsistema.action.accionesmultientidad.CompararImportarReglasAccionConfiguracion', 'ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad.CompararImportarReglasAccionEjecucion', NULL);
INSERT INTO sgm_adm_acciones (id, nombre, clase_config, clase_exec, info_adicional) VALUES (2, 'Importar Procedimiento', 'ieci.tecdoc.sgm.admsistema.action.accionesmultientidad.ImportarProcedimientoAccionConfiguracion', 'ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad.ImportarProcedimientoAccionEjecucion', NULL);