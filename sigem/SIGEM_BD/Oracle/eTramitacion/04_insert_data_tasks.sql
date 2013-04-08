

--
-- Data for: sgmrtcatalogo_tramites
--

INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_1', 'TRQS', 'Solicitud de Reclamación, queja y sugerencias', '001', '0', '1', '999', 'PCD-3');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_3', 'TSUB', 'Subvención', '003', '0', '1', '999', 'PCD-4');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_4', 'TLIC', 'Licencia de Obra Menor', '002', '0', '1', '999', 'PCD-5');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_13', 'TLAANC', 'Licencia de Apertura de Actividad No Clasificada ', '003', '0', '1', '999', '024');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_12', 'TSLV', 'Licencia de Vado', '003', '0', '1', '999', '023');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_15', 'TSRT', 'Reclamación Tributos', '003', '0', '1', '999', 'PCD-16');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_9', 'TSCU', 'Solicitud de Certificado Urbanístico', '003', '0', '1', '999', 'PCD-11');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_11', 'TSAA', 'Solicitud de Acometida de Agua', '001', '0', '1', '999', 'PCD-15');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_8', 'TCTLA', 'Cambio de Titularidad de Licencia de Apertura', '003', '0', '1', '999', '025');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_16', 'TCAPN', 'Contrato Negociado', '003', '0', '1', '999', 'PCD-10');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_7', 'TEXS', 'Expediente Sancionador', '003', '0', '1', '999', '019');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_10', 'TLAAC', 'Licencia de Apertura de Actividad Clasificada (RAMINP) ', '003', '0', '1', '999', '026');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_14', 'TSTEM', 'Solicitud de Tarjeta de Estacionamiento para Minusválidos', '003', '0', '1', '999', '013');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_5', 'TBOEX', 'Bonificaciones y Exenciones ', '003', '0', '1', '999', 'PCD-27');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_6', 'TODP', 'Ocupación del Dominio Público ', '003', '0', '1', '999', 'PCD-29');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_17', 'TCEUR', 'Cédula Urbanística ', '003', '0', '1', '999', 'PCD-25');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_18', 'TCERU', 'Certificaciones Urbanísticas ', '003', '0', '1', '999', 'PCD-4');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_19', 'TSOCP', 'Solicitud de Cita Previa ', '003', '0', '1', '999', 'PCD-192');


--
-- Data for: sgmrtcatalogo_docstramite
--

INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_1', 'PDF', 'TRAM1D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_1', 'DOC', 'TRAM1D2', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_3', 'PDF', 'TRAM3D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_4', 'PDF', 'TRAM4D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_13', 'PDF', 'TRAM13D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_13', 'PDF', 'TRAM13D2', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_13', 'PDF', 'TRAM13D3', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_13', 'PDF', 'TRAM13D4', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_13', 'PDF', 'TRAM13D5', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_13', 'PDF', 'TRAM13D6', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_12', 'PDF', 'TRAM12D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_12', 'PDF', 'TRAM12D2', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_12', 'PDF', 'TRAM12D3', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_12', 'PDF', 'TRAM12D4', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_12', 'PDF', 'TRAM12D5', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_12', 'PDF', 'TRAM12D6', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_15', 'PDF', 'TRAM15D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_15', 'PDF', 'TRAM15D2', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_9', 'PDF', 'TRAM9D2', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_9', 'PDF', 'TRAM9D3', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_9', 'PDF', 'TRAM9D4', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_9', 'PDF', 'TRAM9D1', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_11', 'PDF', 'TRAM11D2', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_11', 'PDF', 'TRAM11D1', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_11', 'PDF', 'TRAM11D3', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_11', 'PDF', 'TRAM11D4', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_11', 'PDF', 'TRAM11D5', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_11', 'PDF', 'TRAM11D6', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_8', 'PDF', 'TRAM8D1', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_8', 'PDF', 'TRAM8D2', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_8', 'PDF', 'TRAM8D3', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_8', 'PDF', 'TRAM8D4', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D1', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D2', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D3', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D4', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D5', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D6', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D7', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D8', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D9', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D10', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D11', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_16', 'PDF', 'TRAM16D12', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_10', 'PDF', 'TRAM10D2', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_10', 'PDF', 'TRAM10D3', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_10', 'PDF', 'TRAM10D4', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_10', 'PDF', 'TRAM10D5', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_10', 'PDF', 'TRAM10D6', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_10', 'PDF', 'TRAM10D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D2', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D3', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D4', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D6', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D7', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D8', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D9', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D10', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D11', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'JPEG', 'TRAM14D5', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_5', 'PDF', 'TRAM5D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_6', 'PDF', 'TRAM6D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_17', 'PDF', 'TRAM17D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_18', 'PDF', 'TRAM18D1', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_18', 'DOC', 'TRAM18D2', '1');


--
-- Data for: sgmafconector_autenticacion
--

INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_1', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_4', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_1', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_3', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_4', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_3', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_7', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_7', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_8', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_8', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_9', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_9', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_10', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_10', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_11', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_11', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_12', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_12', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_13', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_13', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_14', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_14', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_16', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_16', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_15', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_15', 'AUTH_WEB_USER');
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
