CREATE TABLE documentos (
  id varchar(64) NOT NULL,
  nombre varchar(1024) NOT NULL,
  tipo_mime varchar(128),
  tamano bigint DEFAULT 0 NOT NULL,
  fecha_creacion datetime NOT NULL,
  fecha_modificacion datetime,
  contenido image,
  metadatos text
);

ALTER TABLE documentos ADD CONSTRAINT pk_documentos
  PRIMARY KEY (id);
