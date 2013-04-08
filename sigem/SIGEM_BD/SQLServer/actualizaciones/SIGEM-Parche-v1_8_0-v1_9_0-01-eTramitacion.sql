--------------------------------------
-- ACTUALIZACION MODULO CALENDARIOS --
--------------------------------------

-- Tabla de configuraci贸n del calendario de tramitaci贸n electr贸nica
CREATE TABLE sgmrtcatalogo_calendario (
  laborables varchar(13) NOT NULL, -- Dias laborables de la semana
  hora_inicio varchar(2), -- Hora de inicio para consolidar
  minuto_inicio varchar(2), -- Minutos de inicio para consolidar
  hora_fin varchar(2), -- Hora de fin para consolidar
  minuto_fin varchar(2) -- Minuto de fin para consolidar
); 


-- Tabla de configuraci贸n de los d铆as festivos del calendario de tramitaci贸n electr贸nica
CREATE TABLE sgmrtcatalogo_diascalendario (
  id numeric NOT NULL,
  fecha varchar(5) NOT NULL, -- Fecha del festivo
  descripcion varchar(256) NOT NULL, -- Nombre del festivo
  fijo int NOT NULL -- Indica si es un festivo fijo o si cambio cada a帽o
); 

-- A帽adir campo de fecha efectiva del registro telem谩tico para la consolidaci贸n.
-- Para los registros existente, establece como fecha efectiva la fecha de registro.
ALTER TABLE sgmrtregistro ADD fecha_efectiva datetime;
ALTER TABLE sgmrtcatalogo_diascalendario ADD CONSTRAINT diascalendario_pkey PRIMARY KEY (id);
GO

UPDATE sgmrtregistro set sgmrtregistro.fecha_efectiva = sgmrtregistro.fecha_registro;

DROP INDEX fki_rde_registry_2 ON sgmrtregistro;
ALTER TABLE sgmrtregistro ALTER COLUMN fecha_registro datetime NOT NULL;
CREATE INDEX fki_rde_registry_2 ON sgmrtregistro (fecha_registro);

ALTER TABLE sgmrtregistro ALTER COLUMN fecha_efectiva datetime NOT NULL;

-----------------------------------------
-- ACTUALIZACION MODULO NOTIFICACIONES --
-----------------------------------------
-- En esta version se modifica la clave primaria de la tabla de notificaciones
-- para permitir en SIGEM, la creacion de varias notificaciones del mismo expediente al mismo
-- destinatario.
ALTER TABLE sgmntinfo_notificacion ADD cnotiid varchar (64) NOT NULL DEFAULT '';
UPDATE sgmntinfo_notificacion set cnotiid='ACT_' + cnumexp + '_' + cnifdest;
ALTER TABLE sgmntinfo_documento DROP CONSTRAINT nt_docs_noti;
ALTER TABLE sgmntinfo_notificacion DROP CONSTRAINT sgmntinfo_notif_pk;
ALTER TABLE sgmntinfo_notificacion ADD CONSTRAINT sgmntinfo_notif_pk PRIMARY KEY (cnotiid);

ALTER TABLE sgmntinfo_documento ADD cnotiid varchar(64) NOT NULL DEFAULT '';
UPDATE sgmntinfo_documento set cnotiid= 'ACT_' + cnotiexpe + '_' + cnotinifdest;
ALTER TABLE sgmntinfo_documento ADD CONSTRAINT nt_docs_noti FOREIGN KEY (cnotiid) REFERENCES sgmntinfo_notificacion(cnotiid);

-----------------------------------------
-- ACTUALIZACION MODULO NOTIFICACIONES --
-----------------------------------------
-- Cambios necesarios para integrar SMS en notificaciones. Hasta ahora solo Sisnot.
-- Modulo de notificaciones pasa a ser generico.
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cguid varchar(64) NOT NULL;
ALTER TABLE sgmntinfo_notificacion ADD csistemaid varchar(32);
ALTER TABLE sgmntinfo_notificacion ADD cdeu varchar(20);
ALTER TABLE sgmntinfo_notificacion ADD ctfnomovil varchar(14);

-- character() to character varying()
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cnifdest varchar(10) NOT NULL;
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cnumreg varchar(16);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cnumexp varchar(32) NOT NULL;
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cproc varchar(50);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cusuario varchar(10);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN ctipocorrespondencia varchar(10);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN corganismo varchar(100);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN casunto varchar(100);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN ctipo varchar(20);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN ctexto varchar(1000);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cnombredest varchar(100);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN capellidosdest varchar(150);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN ccorreodest varchar(200);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cidioma varchar(20);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN ctipovia varchar(10);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cvia varchar(200);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cescaleravia varchar(10);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cpisovia varchar(10);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cpuertavia varchar(10);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN ctelefono varchar(20);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cmunicipio varchar(100);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cprovincia varchar(100);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN ccp varchar(10);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cerror varchar(100);


ALTER TABLE sgmntinfo_documento DROP CONSTRAINT nt_docs_noti;
DROP INDEX fki_nt_docs_notif ON sgmntinfo_documento;

ALTER TABLE sgmntinfo_documento ALTER COLUMN cnotiexpe varchar(32);
ALTER TABLE sgmntinfo_documento ALTER COLUMN cnotinifdest varchar(10);
ALTER TABLE sgmntinfo_documento ALTER COLUMN ccodigo varchar(200);
ALTER TABLE sgmntinfo_documento ALTER COLUMN cguid varchar(100);

ALTER TABLE sgmntinfo_documento ADD CONSTRAINT nt_docs_noti FOREIGN KEY (cnotiid) REFERENCES sgmntinfo_notificacion(cnotiid);
ALTER TABLE sgmntinfo_documento ADD ctipodoc integer DEFAULT 1;
CREATE INDEX fki_nt_docs_notif ON sgmntinfo_documento (cnotiexpe, cnotinifdest);

--------------------------------------------------
-- 10 NUEVOS TRAMITES EN EL REGISTRO TELEMATICO --
--------------------------------------------------

-- TRAMITES
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_13', 'TLAANC', 'Licencia de Apertura de Actividad No Clasificada ', '003', '0', '1', '999', '024');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_12', 'TSLV', 'Licencia de Vado', '003', '0', '1', '999', '023');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_15', 'TSRT', 'Reclamaci髇 Tributos', '003', '0', '1', '999', 'PCD-16');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_9', 'TSCU', 'Solicitud de Certificado Urban韘tico', '003', '0', '1', '999', 'PCD-11');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_11', 'TSAA', 'Solicitud de Acometida de Agua', '001', '0', '1', '999', 'PCD-15');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_8', 'TCTLA', 'Cambio de Titularidad de Licencia de Apertura', '003', '0', '1', '999', '025');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_16', 'TCAPN', 'Contrato Negociado', '003', '0', '1', '999', 'PCD-10');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_7', 'TEXS', 'Expediente Sancionador', '003', '0', '1', '999', '019');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_10', 'TLAAC', 'Licencia de Apertura de Actividad Clasificada (RAMINP) ', '003', '0', '1', '999', '026');
INSERT INTO sgmrtcatalogo_tramites (id, asunto, descripcion, organo, limite_documentos, firma, oficina, id_procedimiento) VALUES ('TRAM_14', 'TSTEM', 'Solicitud de Tarjeta de Estacionamiento para Min鷖validos', '003', '0', '1', '999', '013');

-- DOCUMENTOS POR TRAMITE
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
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM15D2', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D3', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D4', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D6', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D7', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D8', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D9', '1');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D10', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'PDF', 'TRAM14D11', '0');
INSERT INTO sgmrtcatalogo_docstramite (tramite_id, documento_id, codigo_documento, documento_obligatorio) VALUES ('TRAM_14', 'JPEG', 'TRAM14D5', '1');

-- CONECTORES DE AUTENTICACION POR TRAMITE
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

---------------------------------------------------------
-- ELIMINAR CONECTORES DE AUTENTICACION NO IMPLEMTADOS --
---------------------------------------------------------
DELETE FROM sgmrtcatalogo_conectores WHERE conectorid='HOOK_2';
DELETE FROM sgmrtcatalogo_conectores WHERE conectorid='HOOK_3';
DELETE FROM sgmrtcatalogo_conectores WHERE conectorid='HOOK_1';
DELETE FROM sgmrtcatalogo_conectores WHERE conectorid='AUTENTICACION_CD';
DELETE FROM sgmrtcatalogo_conectores WHERE conectorid='VALIDADOR_IMAGEN';