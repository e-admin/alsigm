-- Table: sgmrtregistro

-- DROP TABLE sgmrtregistro;

CREATE TABLE sgmrtregistro
(
  numero_registro character varying(16) NOT NULL,
  fecha_registro timestamp without time zone,
  emisor_id character varying(128) NOT NULL,
  nombre character varying(50) NOT NULL, -- Nombre de emisor
  apellidos character varying(50) NOT NULL, -- Apellidos del emisor
  correo_electronico character varying(60),
  asunto character varying(200) NOT NULL, -- Asunto del registro
  organo character varying(7) NOT NULL,
  tipo_emisor_id character varying(1) NOT NULL,
  info_adicional bytea,
  estado character varying(1) NOT NULL, -- Estado actual del registro
  oficina character varying(32),
  numero_expediente character varying(32),
  CONSTRAINT sgmrderegistry_pkey PRIMARY KEY (numero_registro)
) 
WITHOUT OIDS;
ALTER TABLE sgmrtregistro OWNER TO postgres;
COMMENT ON TABLE sgmrtregistro IS 'Tabla de registros';
COMMENT ON COLUMN sgmrtregistro.nombre IS 'Nombre de emisor';
COMMENT ON COLUMN sgmrtregistro.apellidos IS 'Apellidos del emisor';
COMMENT ON COLUMN sgmrtregistro.asunto IS 'Asunto del registro';
COMMENT ON COLUMN sgmrtregistro.estado IS 'Estado actual del registro';


-- Index: sgmrde_registry_ix1

-- DROP INDEX sgmrde_registry_ix1;

CREATE INDEX sgmrde_registry_ix1
  ON sgmrtregistro
  USING btree
  (emisor_id);

-- Index: sgmrde_registry_ix2

-- DROP INDEX sgmrde_registry_ix2;

CREATE INDEX sgmrde_registry_ix2
  ON sgmrtregistro
  USING btree
  (fecha_registro);

-- Index: sgmrde_registry_ix4

-- DROP INDEX sgmrde_registry_ix4;

CREATE INDEX sgmrde_registry_ix4
  ON sgmrtregistro
  USING btree
  (estado);

-- Index: sgmrde_registry_pk

-- DROP INDEX sgmrde_registry_pk;

CREATE INDEX sgmrde_registry_pk
  ON sgmrtregistro
  USING btree
  (numero_registro);



-- Table: sgmrtregistro_documentos

-- DROP TABLE sgmrtregistro_documentos;

CREATE TABLE sgmrtregistro_documentos
(
  numero_registro character varying(16) NOT NULL,
  codigo character varying(32) NOT NULL,
  guid character varying(32) NOT NULL,
  conector_firma character varying(32),
  conector_documento character varying(32)
) 
WITHOUT OIDS;
ALTER TABLE sgmrtregistro_documentos OWNER TO postgres;
COMMENT ON TABLE sgmrtregistro_documentos IS 'Tabla que asocia un registro con documentos';


-- Index: sgmrde_registry_ix

-- DROP INDEX sgmrde_registry_ix;

CREATE INDEX sgmrde_registry_ix
  ON sgmrtregistro_documentos
  USING btree
  (numero_registro);



-- Table: sgmrtregistro_secuencia

-- DROP TABLE sgmrtregistro_secuencia;

CREATE TABLE sgmrtregistro_secuencia
(
  anyo character varying(4) NOT NULL,
  etiqueta character varying(8) NOT NULL, -- Etiqueta intermedia
  secuencia numeric NOT NULL
) 
WITHOUT OIDS;
ALTER TABLE sgmrtregistro_secuencia OWNER TO postgres;
COMMENT ON COLUMN sgmrtregistro_secuencia.etiqueta IS 'Etiqueta intermedia';


INSERT INTO sgmrtregistro_secuencia (anyo, etiqueta, secuencia) VALUES ('2007', '00500', 0);


-- Table: sgmrttmp_documentos

-- DROP TABLE sgmrttmp_documentos;

CREATE TABLE sgmrttmp_documentos
(
  sesionid character varying(64) NOT NULL,
  guid character varying(32) NOT NULL,
  fecha timestamp without time zone NOT NULL
) 
WITHOUT OIDS;
ALTER TABLE sgmrttmp_documentos OWNER TO postgres;




