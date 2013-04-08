------------------------------------
-- ACTUALIZACION PARA EL REGISTRO --
------------------------------------

-- Nuevos tramites telematicos
insert into sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) values ('TRAM_5', 'TBOEX', 'Bonificaciones y Exenciones ', '003', '0', '1', '999', 'PCD-27');
insert into sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) values ('TRAM_6', 'TODP', 'Ocupación del Dominio Público ', '003', '0', '1', '999', 'PCD-29');
insert into sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) values ('TRAM_17', 'TCEUR', 'Cédula Urbanística ', '003', '0', '1', '999', 'PCD-25');
insert into sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) values ('TRAM_18', 'TCERU', 'Certificaciones Urbanísticas ', '003', '0', '1', '999', 'PCD-4');
insert into sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) values ('TRAM_19', 'TSOCP', 'Solicitud de Cita Previa ', '003', '0', '1', '999', 'PCD-192');

-- Documentos asociados a los nuevos tramites
insert into sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) values ('TRAM_5', 'PDF', 'TRAM5D1', '1');
insert into sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) values ('TRAM_6', 'PDF', 'TRAM6D1', '1');
insert into sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) values ('TRAM_17', 'PDF', 'TRAM17D1', '1');
insert into sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) values ('TRAM_18', 'PDF', 'TRAM18D1', '1');
insert into sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) values ('TRAM_18', 'DOC', 'TRAM18D2', '1');

-- Conectores de autenticacion de los nuevos tramites
insert into sgmafconector_autenticacion (tramiteid, conectorid) values ('TRAM_5', 'AUTH_CERTIFICADO');
insert into sgmafconector_autenticacion (tramiteid, conectorid) values ('TRAM_5', 'AUTH_WEB_USER');
insert into sgmafconector_autenticacion (tramiteid, conectorid) values ('TRAM_6', 'AUTH_CERTIFICADO');
insert into sgmafconector_autenticacion (tramiteid, conectorid) values ('TRAM_6', 'AUTH_WEB_USER');
insert into sgmafconector_autenticacion (tramiteid, conectorid) values ('TRAM_17', 'AUTH_CERTIFICADO');
insert into sgmafconector_autenticacion (tramiteid, conectorid) values ('TRAM_17', 'AUTH_WEB_USER');
insert into sgmafconector_autenticacion (tramiteid, conectorid) values ('TRAM_18', 'AUTH_CERTIFICADO');
insert into sgmafconector_autenticacion (tramiteid, conectorid) values ('TRAM_18', 'AUTH_WEB_USER');
insert into sgmafconector_autenticacion (tramiteid, conectorid) values ('TRAM_19', 'AUTH_CERTIFICADO');
insert into sgmafconector_autenticacion (tramiteid, conectorid) values ('TRAM_19', 'AUTH_WEB_USER');