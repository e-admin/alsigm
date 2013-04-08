------------------------------------
-- ACTUALIZACION PARA EL REGISTRO TELEMATICO--
------------------------------------

-- Nuevos tramites
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_5', 'TBOEX', 'Bonificaciones y Exenciones ', '003', '0', '1', '999', 'PCD-27');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_6', 'TODP', 'Ocupación del Dominio Público ', '003', '0', '1', '999', 'PCD-29');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_17', 'TCEUR', 'Cédula Urbanística ', '003', '0', '1', '999', 'PCD-25');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_18', 'TCERU', 'Certificaciones Urbanísticas ', '003', '0', '1', '999', 'PCD-4');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_19', 'TSOCP', 'Solicitud de Cita Previa ', '003', '0', '1', '999', 'PCD-192');

-- Documentos asociados a los nuevos tramites
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_5', 'PDF', 'TRAM5D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_6', 'PDF', 'TRAM6D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_17', 'PDF', 'TRAM17D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_18', 'PDF', 'TRAM18D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_18', 'DOC', 'TRAM18D2', '1');

-- Conectores de autenticacion para los nuevos tramites
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_5', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_5', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_6', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_6', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_17', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_17', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_18', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_18', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_19', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_19', 'AUTH_WEB_USER');