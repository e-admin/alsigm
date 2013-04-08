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


INSERT INTO csv_apps_seq default values;

--
-- Alta de aplicación para el Registro Telemático
--

INSERT INTO csv_aplicaciones(id, codigo, nombre, info_conexion)
	SELECT (SELECT max(id) FROM csv_apps_seq), 'SGM_REG_TEL', 'SIGM - Registro Telemático', '<?xml version=''1.0'' encoding=''UTF-8''?><connection-config><connector>SIGEM_WEB_SERVICE</connector><parameters><parameter name="WSDL_LOCATION">http://localhost:8080/SIGEM_RegistroTelematicoWS/services/AplicacionExternaCSVConnectorWS?wsdl</parameter></parameters></connection-config>';


INSERT INTO csv_apps_seq default values;

--
-- Alta de aplicación para la Tramitación de Expedientes
--

INSERT INTO csv_aplicaciones(id, codigo, nombre, info_conexion)
	SELECT (SELECT max(id) FROM csv_apps_seq), 'SGM_TRAM_EXP', 'SIGM - Tramitación de Expedientes', '<?xml version=''1.0'' encoding=''UTF-8''?><connection-config><connector>SIGEM_WEB_SERVICE</connector><parameters><parameter name="WSDL_LOCATION">http://localhost:8080/SIGEM_TramitacionWS/services/AplicacionExternaCSVConnectorWS?wsdl</parameter></parameters></connection-config>';
