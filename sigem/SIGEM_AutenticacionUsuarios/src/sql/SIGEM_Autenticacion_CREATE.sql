-- Table: sgmafusuarios

-- DROP TABLE sgmafusuarios;

CREATE TABLE sgm_au_usuarios
(
  nombre character varying(50) NOT NULL,
  apellidos character varying(50) NOT NULL,
  nif character varying(32) NOT NULL,
  correo_electronico character varying(60) NOT NULL,
  usuario character varying(15) NOT NULL,
  "password" character varying(100) NOT NULL,
  CONSTRAINT sgmafusuarios_pkey PRIMARY KEY (usuario)
) 
WITHOUT OIDS;
ALTER TABLE sgm_au_usuarios OWNER TO postgres;




