CREATE TABLE sgm_adm_entidades (
    id character varying(3 CHAR) NOT NULL,
    nombrecorto character varying(70 CHAR) NOT NULL,
    nombrelargo character varying(500 CHAR),
    codigo_ine character varying(12 CHAR)
);


CREATE TABLE sgm_adm_perfiles (
    id_entidad character varying(3 CHAR) NOT NULL,
    id_usuario character varying(15 CHAR) NOT NULL,
    id_aplicacion character varying(2 CHAR) NOT NULL
);


CREATE TABLE sgm_adm_usuarios (
    usuario character varying(15 CHAR) NOT NULL,
    password character varying(100 CHAR) NOT NULL,
    nombre character varying(50 CHAR) NOT NULL,
    apellidos character varying(50 CHAR) NOT NULL,
    fecha_alta date NOT NULL
);

CREATE TABLE sgm_adm_acciones (
    id NUMBER NOT NULL,
    nombre character varying(256 CHAR) NOT NULL,
    clase_config character varying(256 CHAR) NOT NULL,
    clase_exec character varying(256 CHAR) NOT NULL,
    info_adicional CLOB
);

CREATE VIEW sgm_adm_usuarios_v AS
    SELECT DISTINCT sgm_adm_perfiles.id_entidad, sgm_adm_perfiles.id_usuario, sgm_adm_usuarios.password, sgm_adm_usuarios.nombre, sgm_adm_usuarios.apellidos, sgm_adm_usuarios.fecha_alta 
	FROM sgm_adm_perfiles, sgm_adm_usuarios 
	WHERE sgm_adm_perfiles.id_usuario = sgm_adm_usuarios.usuario;

INSERT INTO sgm_adm_entidades (id, nombrecorto, nombrelargo, codigo_ine) VALUES ('000', 'Entidad por defecto', 'Entidad por defecto', '');

INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '1');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '2');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '3');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '4');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '5');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '6');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '7');
INSERT INTO sgm_adm_perfiles (id_entidad, id_usuario, id_aplicacion) VALUES ('000', 'administrador', '8');

INSERT INTO sgm_adm_usuarios (usuario, password, nombre, apellidos, fecha_alta) VALUES ('administrador', 'kfUWfDTEAHWBFcKmgm7C4w==', 'Usuario', 'Administrador', to_date('06-05-2008', 'DD-MM-YYYY'));

INSERT INTO sgm_adm_acciones (id, nombre, clase_config, clase_exec, info_adicional) VALUES (1, 'Comparar/Importar reglas', 'ieci.tecdoc.sgm.admsistema.action.accionesmultientidad.CompararImportarReglasAccionConfiguracion', 'ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad.CompararImportarReglasAccionEjecucion', NULL);
INSERT INTO sgm_adm_acciones (id, nombre, clase_config, clase_exec, info_adicional) VALUES (2, 'Importar Procedimiento', 'ieci.tecdoc.sgm.admsistema.action.accionesmultientidad.ImportarProcedimientoAccionConfiguracion', 'ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad.ImportarProcedimientoAccionEjecucion', NULL);

ALTER TABLE sgm_adm_entidades ADD CONSTRAINT pk_sgm_adm_entidades PRIMARY KEY (id);
ALTER TABLE sgm_adm_perfiles ADD CONSTRAINT sgm_adm_perfiles_pk PRIMARY KEY (id_entidad, id_usuario, id_aplicacion);
ALTER TABLE sgm_adm_usuarios ADD CONSTRAINT sgm_adm_usuarios_pk PRIMARY KEY (usuario);
ALTER TABLE sgm_adm_acciones ADD CONSTRAINT sgm_adm_acciones_pk PRIMARY KEY (id);

CREATE INDEX fki_sgm_adm_perfent_fk ON sgm_adm_perfiles (id_entidad);
CREATE INDEX fki_sgm_adm_perfusu_fk ON sgm_adm_perfiles (id_usuario);
CREATE UNIQUE INDEX i_sgm_adm_accnombre ON sgm_adm_acciones (nombre);

ALTER TABLE sgm_adm_perfiles ADD CONSTRAINT sgm_adm_perfent_fk FOREIGN KEY (id_entidad) REFERENCES sgm_adm_entidades(id);
ALTER TABLE sgm_adm_perfiles ADD CONSTRAINT sgm_adm_perfusu_fk FOREIGN KEY (id_usuario) REFERENCES sgm_adm_usuarios(usuario);
