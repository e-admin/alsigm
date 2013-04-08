
-- Table: sgmrtcatalogo_conectores

-- DROP TABLE sgmrtcatalogo_conectores;

CREATE TABLE sgmrtcatalogo_conectores
(
  conectorid character varying(32) NOT NULL,
  descripcion character varying(256) NOT NULL,
  tipo character varying(32) NOT NULL, -- Tipo de conector
  CONSTRAINT sgmrdecat_hook_pkey PRIMARY KEY (conectorid)
) 
WITHOUT OIDS;
ALTER TABLE sgmrtcatalogo_conectores OWNER TO postgres;
COMMENT ON TABLE sgmrtcatalogo_conectores IS 'Tabla de conectores';
COMMENT ON COLUMN sgmrtcatalogo_conectores.tipo IS 'Tipo de conector';

INSERT INTO sgmrtcatalogo_conectores (conectorid, descripcion, tipo) VALUES ('HOOK_2', 'Conector de documentos Word', '5');
INSERT INTO sgmrtcatalogo_conectores (conectorid, descripcion, tipo) VALUES ('HOOK_3', 'Conector de firmas', '4');
INSERT INTO sgmrtcatalogo_conectores (conectorid, descripcion, tipo) VALUES ('HOOK_1', 'Conector de documentos PDF', '5');
INSERT INTO sgmrtcatalogo_conectores (conectorid, descripcion, tipo) VALUES ('AUTH_CERTIFICADO', 'Autenticación con certificado digital', '1');
INSERT INTO sgmrtcatalogo_conectores (conectorid, descripcion, tipo) VALUES ('AUTH_WEB_USER', 'Autenticación con usuario Web de Nivel 2', '2');
INSERT INTO sgmrtcatalogo_conectores (conectorid, descripcion, tipo) VALUES ('AUTENTICACION_CD', 'Autenticación de usuarios con certificado digital', '1');
INSERT INTO sgmrtcatalogo_conectores (conectorid, descripcion, tipo) VALUES ('VALIDADOR_IMAGEN', 'Conector de validación de imágenes', '5');

-- Index: fki_sgmrdecat_hook_type

-- DROP INDEX fki_sgmrdecat_hook_type;

CREATE INDEX fki_sgmrdecat_hook_type
  ON sgmrtcatalogo_conectores
  USING btree
  (tipo);



-- Table: sgmrtcatalogo_documentos

-- DROP TABLE sgmrtcatalogo_documentos;

CREATE TABLE sgmrtcatalogo_documentos
(
  id character varying(32) NOT NULL,
  descripcion character varying(256) NOT NULL,
  extension character varying(128) NOT NULL,
  conector_firma character varying(32), -- Conector de firma asociado al tipo de documento
  conector_contenido character varying(32), -- Conector de contenido asociado al tipo de documento
  CONSTRAINT sgmrdecat_document_pkey PRIMARY KEY (id)
) 
WITHOUT OIDS;
ALTER TABLE sgmrtcatalogo_documentos OWNER TO postgres;
COMMENT ON TABLE sgmrtcatalogo_documentos IS 'Tabla de tipos de documentos';
COMMENT ON COLUMN sgmrtcatalogo_documentos.conector_firma IS 'Conector de firma asociado al tipo de documento';
COMMENT ON COLUMN sgmrtcatalogo_documentos.conector_contenido IS 'Conector de contenido asociado al tipo de documento';

INSERT INTO sgmrtcatalogo_documentos (id, descripcion, extension, conector_firma, conector_contenido) VALUES ('PDF', 'Documento PDF', 'PDF', NULL, NULL);
INSERT INTO sgmrtcatalogo_documentos (id, descripcion, extension, conector_firma, conector_contenido) VALUES ('JPEG', 'Documento imagen con formato JPEG', 'JPG', '', '');
INSERT INTO sgmrtcatalogo_documentos (id, descripcion, extension, conector_firma, conector_contenido) VALUES ('DOC', 'Documento DOC', 'DOC', '', 'HOOK_2');
INSERT INTO sgmrtcatalogo_documentos (id, descripcion, extension, conector_firma, conector_contenido) VALUES ('DOC_GIF', 'Fotografía', 'GIF', '', 'VALIDADOR_IMAGEN');

-- Index: sgmrdecat_document_index

-- DROP INDEX sgmrdecat_document_index;

CREATE INDEX sgmrdecat_document_index
  ON sgmrtcatalogo_documentos
  USING btree
  (id);


-- Table: sgmrtcatalogo_tramites

-- DROP TABLE sgmrtcatalogo_tramites;

CREATE TABLE sgmrtcatalogo_tramites
(
  id character varying(32) NOT NULL,
  asunto character varying(32) NOT NULL,
  descripcion character varying(256) NOT NULL,
  organo character varying(32) NOT NULL,
  limite_documentos character varying(1) NOT NULL,
  firma character varying(1) NOT NULL DEFAULT 1,
  oficina character varying(32) NOT NULL,
  id_procedimiento character varying(32),
  CONSTRAINT sgmrdecat_procedure_pkey PRIMARY KEY (id)
) 
WITHOUT OIDS;
ALTER TABLE sgmrtcatalogo_tramites OWNER TO postgres;
COMMENT ON TABLE sgmrtcatalogo_tramites IS 'Tabla de tramites';

INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina) VALUES ('TRAM_1', 'TRQS', 'Solicitud de Reclamación, queja y sugerencias', '001', '0', '1', '005','PCD-14');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina) VALUES ('TRAM_3', '003', 'Subvención', '003', '0', '1', '005','PCD-15');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina) VALUES ('TRAM_4', '002', 'Licencia de Obra Menor', '002', '0', '1', '005','PCD-16');

-- Index: sgmrdecat_procedure_index

-- DROP INDEX sgmrdecat_procedure_index;

CREATE INDEX sgmrdecat_procedure_index
  ON sgmrtcatalogo_tramites
  USING btree
  (id);


-- Table: sgmrtcatalogo_documentostramite

-- DROP TABLE sgmrtcatalogo_documentostramite;

CREATE TABLE sgmrtcatalogo_docstramite
(
  tramite_id character varying(32) NOT NULL,
  documento_id character varying(32) NOT NULL,
  codigo_documento character varying(32) NOT NULL,
  documento_obligatorio character varying(1) NOT NULL,
  CONSTRAINT sgmrdecat_procedure_document_pkey PRIMARY KEY (tramite_id, documento_id),
  CONSTRAINT sgmrdecat_procedure_document_document_id_fkey FOREIGN KEY (documento_id)
      REFERENCES sgmrtcatalogo_documentos (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT sgmrdecat_procedure_document_procedure_id_fkey FOREIGN KEY (tramite_id)
      REFERENCES sgmrtcatalogo_tramites (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;
ALTER TABLE sgmrtcatalogo_docstramite OWNER TO postgres;

INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_1', 'PDF', 'TRAM1D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_1', 'DOC', 'TRAM1D2', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_3', 'PDF', 'TRAM3D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_4', 'PDF', 'TRAM4D1', '1');

-- Index: fki_

-- DROP INDEX fki_;

CREATE INDEX fki_sgmrtcatalogo_docstramite
  ON sgmrtcatalogo_docstramite
  USING btree
  (documento_id);



-- Table: sgmrtcatalogo_organos

-- DROP TABLE sgmrtcatalogo_organos;

CREATE TABLE sgmrtcatalogo_organos
(
  organo character varying(7) NOT NULL,
  descripcion character varying(256) NOT NULL,
  CONSTRAINT sgmrdecat_addressee_pkey PRIMARY KEY (organo)
) 
WITHOUT OIDS;
ALTER TABLE sgmrtcatalogo_organos OWNER TO postgres;

INSERT INTO sgmrtcatalogo_organos (organo, descripcion) VALUES ('001', 'Servicio de Relaciones con el Ciudadano');
INSERT INTO sgmrtcatalogo_organos (organo, descripcion) VALUES ('002', 'Servicio de Tramitación de Licencias');
INSERT INTO sgmrtcatalogo_organos (organo, descripcion) VALUES ('003', 'Servicio de Secretaría');



-- Table: sgmrtcatalogo_tipoconector

-- DROP TABLE sgmrtcatalogo_tipoconector;

CREATE TABLE sgmrtcatalogo_tipoconector
(
  tipoid character varying(32) NOT NULL,
  descripcion character varying(256) NOT NULL,
  CONSTRAINT sgmrdecat_hooktype_pkey PRIMARY KEY (tipoid)
) 
WITHOUT OIDS;
ALTER TABLE sgmrtcatalogo_tipoconector OWNER TO postgres;
COMMENT ON TABLE sgmrtcatalogo_tipoconector IS 'Table de tipos de conectores';

INSERT INTO sgmrtcatalogo_tipoconector (tipoid, descripcion) VALUES ('1', 'Autenticación con certificado digital');
INSERT INTO sgmrtcatalogo_tipoconector (tipoid, descripcion) VALUES ('2', 'Autenticación con usuario Web de nivel 2');
INSERT INTO sgmrtcatalogo_tipoconector (tipoid, descripcion) VALUES ('4', 'Verificación de firma');
INSERT INTO sgmrtcatalogo_tipoconector (tipoid, descripcion) VALUES ('5', 'Validación del contenido de un documento');
INSERT INTO sgmrtcatalogo_tipoconector (tipoid, descripcion) VALUES ('7', 'Autenticación con certificado digital vía web');
INSERT INTO sgmrtcatalogo_tipoconector (tipoid, descripcion) VALUES ('6', 'Obtención del certificado');
INSERT INTO sgmrtcatalogo_tipoconector (tipoid, descripcion) VALUES ('3', 'Creación de firma');





