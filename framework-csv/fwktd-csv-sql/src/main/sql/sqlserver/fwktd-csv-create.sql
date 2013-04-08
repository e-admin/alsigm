--
-- Secuencias
--

CREATE TABLE csv_apps_seq (
	id bigint IDENTITY(1, 1) NOT NULL
);

CREATE TABLE csv_docs_seq (
	id bigint IDENTITY(1, 1) NOT NULL
);


--
-- Tablas
--

CREATE TABLE csv_aplicaciones (
  id integer NOT NULL,
  codigo varchar(32) NOT NULL,
  nombre varchar(1024) NOT NULL,
  info_conexion text
);

CREATE TABLE csv_documentos (
  id integer NOT NULL,
  nombre varchar(1024) NOT NULL,
  descripcion text,
  tipo_mime varchar(256) NOT NULL,
  fecha_creacion datetime,
  fecha_caducidad datetime,
  csv varchar(128) NOT NULL,
  fecha_csv datetime NOT NULL,
  disponibilidad integer NOT NULL,
  id_aplicacion integer NOT NULL
);


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


