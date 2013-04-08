-- Table: sgmcertificacion

-- DROP TABLE sgmcertificacion;

CREATE TABLE sgmcertificacion
(
  id_fichero character varying(32) NOT NULL, -- GUID del fichero en el RDE
  id_usuario character varying(32) NOT NULL, -- Identificador de usuario
  CONSTRAINT sgmcertificacion_pkey PRIMARY KEY (id_fichero)
) 
WITHOUT OIDS;
ALTER TABLE sgmcertificacion OWNER TO postgres;
COMMENT ON COLUMN sgmcertificacion.id_fichero IS 'GUID del fichero en el RDE';
COMMENT ON COLUMN sgmcertificacion.id_usuario IS 'Identificador de usuario';
