CREATE TABLE documentos (
  id character varying(64) NOT NULL,
  nombre character varying(1024) NOT NULL,
  tipo_mime character varying(128),
  tamano bigint NOT NULL DEFAULT 0,
  fecha_creacion timestamp with time zone NOT NULL,
  fecha_modificacion timestamp with time zone,
  contenido bytea,
  metadatos text
);

ALTER TABLE documentos ADD CONSTRAINT pk_documentos
  PRIMARY KEY (id);
