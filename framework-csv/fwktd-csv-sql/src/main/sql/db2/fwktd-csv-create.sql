--
-- Secuencias
--

CREATE SEQUENCE csv_apps_seq;
CREATE SEQUENCE csv_docs_seq;


--
-- Tablas
--

CREATE TABLE csv_aplicaciones (
  id integer NOT NULL,
  codigo varchar(32) NOT NULL,
  nombre varchar(1024) NOT NULL,
  info_conexion clob
);

COMMENT ON COLUMN csv_aplicaciones.id IS 'Identificador de la aplicación';
COMMENT ON COLUMN csv_aplicaciones.codigo IS 'Código de la aplicación';
COMMENT ON COLUMN csv_aplicaciones.nombre IS 'Nombre de la aplicación';
COMMENT ON COLUMN csv_aplicaciones.info_conexion IS 'Información de la conexión a la aplicación';

CREATE TABLE csv_documentos (
  id integer NOT NULL,
  nombre varchar(1024) NOT NULL,
  descripcion clob,
  tipo_mime varchar(256) NOT NULL,
  fecha_creacion timestamp,
  fecha_caducidad timestamp,
  csv varchar(128) NOT NULL,
  fecha_csv timestamp NOT NULL,
  disponibilidad integer NOT NULL,
  id_aplicacion integer NOT NULL
);

COMMENT ON COLUMN csv_documentos.id IS 'Identificador del documento';
COMMENT ON COLUMN csv_documentos.nombre IS 'Nombre del documento';
COMMENT ON COLUMN csv_documentos.descripcion IS 'Descripcion del documento';
COMMENT ON COLUMN csv_documentos.tipo_mime IS 'Tipo MIME del documento';
COMMENT ON COLUMN csv_documentos.fecha_creacion IS 'Fecha de creacion del documento';
COMMENT ON COLUMN csv_documentos.fecha_caducidad IS 'Fecha de caducidad del documento';
COMMENT ON COLUMN csv_documentos.csv IS 'CSV del documento';
COMMENT ON COLUMN csv_documentos.fecha_csv IS 'Fecha de generación del CSV del documento';
COMMENT ON COLUMN csv_documentos.disponibilidad IS 'Disponibilidad del documento';
COMMENT ON COLUMN csv_documentos.id_aplicacion IS 'Identificador de la aplicación que contiene el documento';


--
-- Restricciones
--

ALTER TABLE csv_aplicaciones ADD CONSTRAINT pk_csv_aplicaciones PRIMARY KEY(id);

ALTER TABLE csv_documentos ADD CONSTRAINT pk_csv_documentos PRIMARY KEY(id);
ALTER TABLE csv_documentos ADD CONSTRAINT fk_csv_documentos_aplicacion
	FOREIGN KEY (id_aplicacion) REFERENCES csv_aplicaciones(id);


--
-- Índices
--

CREATE UNIQUE INDEX ix_csv_aplicaciones_codigo ON csv_aplicaciones(codigo);
CREATE UNIQUE INDEX ix_csv_documentos_csv ON csv_documentos(csv);


