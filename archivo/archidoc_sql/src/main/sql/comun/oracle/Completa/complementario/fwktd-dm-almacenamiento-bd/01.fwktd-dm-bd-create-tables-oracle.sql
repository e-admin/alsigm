CREATE TABLE documentos (
  id varchar2(64) NOT NULL,
  nombre varchar2(1024) NOT NULL,
  tipo_mime varchar2(128),
  tamano long DEFAULT 0 NOT NULL,
  fecha_creacion timestamp NOT NULL,
  fecha_modificacion timestamp,
  contenido blob,
  metadatos clob
);

ALTER TABLE documentos ADD CONSTRAINT pk_documentos
  PRIMARY KEY (id);
