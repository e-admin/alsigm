-- Creación de tablas
CREATE TABLE sgm_adm_entidades (
    id varchar(3) NOT NULL,
    nombrecorto varchar(70) NOT NULL,
    nombrelargo varchar(500),
    codigo_ine varchar(12)
);
GO

CREATE TABLE sgm_adm_perfiles (
    id_entidad varchar(3) NOT NULL,
    id_usuario varchar(15) NOT NULL,
    id_aplicacion varchar(2) NOT NULL
);
GO

CREATE TABLE sgm_adm_usuarios (
    usuario varchar(15) NOT NULL,
    password varchar(100) NOT NULL,
    nombre varchar(50) NOT NULL,
    apellidos varchar(50) NOT NULL,
    fecha_alta datetime NOT NULL
);

GO

CREATE TABLE sgm_adm_acciones (
    id int NOT NULL,
    nombre character varying(256) NOT NULL,
    clase_config character varying(256) NOT NULL,
    clase_exec character varying(256) NOT NULL,
    info_adicional text
);

GO

-- Creación de vistas
CREATE VIEW sgm_adm_usuarios_v AS
    SELECT DISTINCT sgm_adm_perfiles.id_entidad,
		   sgm_adm_perfiles.id_usuario,
		   sgm_adm_usuarios.password,
		   sgm_adm_usuarios.nombre,
		   sgm_adm_usuarios.apellidos,
		   sgm_adm_usuarios.fecha_alta 
	FROM sgm_adm_perfiles, sgm_adm_usuarios 
	WHERE sgm_adm_perfiles.id_usuario = sgm_adm_usuarios.usuario

GO


-- Claves primarias
ALTER TABLE sgm_adm_entidades ADD CONSTRAINT pk_sgm_adm_entidades PRIMARY KEY (id);
GO
ALTER TABLE sgm_adm_perfiles ADD CONSTRAINT sgm_adm_perfiles_pk PRIMARY KEY (id_entidad, id_usuario, id_aplicacion);
GO
ALTER TABLE sgm_adm_usuarios ADD CONSTRAINT sgm_adm_usuarios_pk PRIMARY KEY (usuario);
GO
ALTER TABLE sgm_adm_acciones ADD CONSTRAINT sgm_adm_acciones_pk PRIMARY KEY (id);
GO

-- Índices
CREATE INDEX fki_sgm_adm_perfent_fk ON sgm_adm_perfiles (id_entidad);
GO
CREATE INDEX fki_sgm_adm_perfusu_fk ON sgm_adm_perfiles (id_usuario);
GO
CREATE UNIQUE INDEX i_sgm_adm_accnombre ON sgm_adm_acciones (nombre);
GO

-- Claves ajenas
ALTER TABLE sgm_adm_perfiles ADD CONSTRAINT sgm_adm_perfent_fk FOREIGN KEY (id_entidad) REFERENCES sgm_adm_entidades(id);
GO
ALTER TABLE sgm_adm_perfiles ADD CONSTRAINT sgm_adm_perfusu_fk FOREIGN KEY (id_usuario) REFERENCES sgm_adm_usuarios(usuario);
GO