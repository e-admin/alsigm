--------------------------------------
-- ACTUALIZACION MODULO CALENDARIOS --
--------------------------------------

-- Tabla de configuraci贸n del calendario de tramitaci贸n electr贸nica
CREATE TABLE sgmrtcatalogo_calendario (
  laborables character varying(13) NOT NULL, -- Dias laborables de la semana
  hora_inicio character varying(2), -- Hora de inicio para consolidar
  minuto_inicio character varying(2), -- Minutos de inicio para consolidar
  hora_fin character varying(2), -- Hora de fin para consolidar
  minuto_fin character varying(2) -- Minuto de fin para consolidar
); 


-- Tabla de configuraci贸n de los d铆as festivos del calendario de tramitaci贸n electr贸nica
CREATE TABLE sgmrtcatalogo_diascalendario (
  id integer NOT NULL,
  fecha character varying(5) NOT NULL, -- Fecha del festivo
  descripcion character varying(256) NOT NULL, -- Nombre del festivo
  fijo integer NOT NULL -- Indica si es un festivo fijo o si cambio cada a帽o
); 

-- A帽adir campo de fecha efectiva del registro telem谩tico para la consolidaci贸n.
-- Para los registros existente, establece como fecha efectiva la fecha de registro.
ALTER TABLE sgmrtcatalogo_diascalendario ADD CONSTRAINT diascalendario_pkey PRIMARY KEY (id);

ALTER TABLE sgmrtregistro ADD COLUMN fecha_efectiva timestamp without time zone;
UPDATE sgmrtregistro set fecha_efectiva = fecha_registro;

ALTER TABLE sgmrtregistro ALTER COLUMN fecha_registro SET NOT NULL;
ALTER TABLE sgmrtregistro ALTER COLUMN fecha_efectiva SET NOT NULL;

-----------------------------------------
-- ACTUALIZACION MODULO NOTIFICACIONES --
-----------------------------------------
-- En esta version se modifica la clave primaria y externa en las tablas de notificaciones
-- para permitir en SIGEM, la creacion de varias notificaciones del mismo expediente al mismo
-- destinatario.
ALTER TABLE sgmntinfo_notificacion ADD cnotiid character varying(64) NOT NULL DEFAULT '';
UPDATE sgmntinfo_notificacion set cnotiid= 'ACT_' || cnumexp || '_' || cnifdest;
ALTER TABLE sgmntinfo_documento DROP CONSTRAINT sgmntinfo_documentos_noti_fk;
ALTER TABLE sgmntinfo_notificacion DROP CONSTRAINT sgmntinfo_notificacion_pk;
ALTER TABLE sgmntinfo_notificacion ADD CONSTRAINT sgmntinfo_notificacion_pk PRIMARY KEY (cnotiid);

ALTER TABLE sgmntinfo_documento ADD cnotiid character varying(64) NOT NULL DEFAULT '';
UPDATE sgmntinfo_documento set cnotiid= 'ACT_' || cnotiexpe || '_' || cnotinifdest;
ALTER TABLE sgmntinfo_documento ADD CONSTRAINT sgmntinfo_documentos_noti_fk FOREIGN KEY (cnotiid) REFERENCES sgmntinfo_notificacion(cnotiid);

-----------------------------------------
-- ACTUALIZACION MODULO NOTIFICACIONES --
-----------------------------------------
-- Cambios necesarios para integrar SMS en notificaciones. Hasta ahora solo Sisnot.
-- Modulo de notificaciones pasa a ser generico.
ALTER TABLE sgmntinfo_notificacion ALTER cguid TYPE character varying(64);
ALTER TABLE sgmntinfo_notificacion ADD csistemaid character varying(32);
ALTER TABLE sgmntinfo_notificacion ADD cdeu character varying(20);
ALTER TABLE sgmntinfo_notificacion ADD ctfnomovil character varying(14);

ALTER TABLE sgmntinfo_documento ADD ctipodoc integer DEFAULT 1;

-- character() to character varying()
ALTER TABLE ONLY sgmntinfo_documento DROP CONSTRAINT sgmntinfo_documentos_noti_fk;

ALTER TABLE sgmntinfo_notificacion ALTER cguid TYPE character varying(64);
ALTER TABLE sgmntinfo_notificacion ALTER cnifdest TYPE character varying(10);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cnifdest SET NOT NULL;
ALTER TABLE sgmntinfo_notificacion ALTER cnumreg TYPE character varying(16);
ALTER TABLE sgmntinfo_notificacion ALTER cnumexp TYPE character varying(32);
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cnumexp SET NOT NULL;
ALTER TABLE sgmntinfo_notificacion ALTER cproc TYPE character varying(50);
ALTER TABLE sgmntinfo_notificacion ALTER cusuario TYPE character varying(10);
ALTER TABLE sgmntinfo_notificacion ALTER ctipocorrespondencia TYPE character varying(10);
ALTER TABLE sgmntinfo_notificacion ALTER corganismo TYPE character varying(100);
ALTER TABLE sgmntinfo_notificacion ALTER casunto TYPE character varying(100);
ALTER TABLE sgmntinfo_notificacion ALTER ctipo TYPE character varying(20);
ALTER TABLE sgmntinfo_notificacion ALTER ctexto TYPE character varying(1000);
ALTER TABLE sgmntinfo_notificacion ALTER cnombredest TYPE character varying(100);
ALTER TABLE sgmntinfo_notificacion ALTER capellidosdest TYPE character varying(150);
ALTER TABLE sgmntinfo_notificacion ALTER ccorreodest TYPE character varying(200);
ALTER TABLE sgmntinfo_notificacion ALTER cidioma TYPE character varying(20);
ALTER TABLE sgmntinfo_notificacion ALTER ctipovia TYPE character varying(10);
ALTER TABLE sgmntinfo_notificacion ALTER cvia TYPE character varying(200);
ALTER TABLE sgmntinfo_notificacion ALTER cnumerovia TYPE character varying(10);
ALTER TABLE sgmntinfo_notificacion ALTER cescaleravia TYPE character varying(10);
ALTER TABLE sgmntinfo_notificacion ALTER cpisovia TYPE character varying(10);
ALTER TABLE sgmntinfo_notificacion ALTER cpuertavia TYPE character varying(10);
ALTER TABLE sgmntinfo_notificacion ALTER ctelefono TYPE character varying(20);
ALTER TABLE sgmntinfo_notificacion ALTER cmunicipio TYPE character varying(100);
ALTER TABLE sgmntinfo_notificacion ALTER cprovincia TYPE character varying(100);
ALTER TABLE sgmntinfo_notificacion ALTER ccp TYPE character varying(10);
ALTER TABLE sgmntinfo_notificacion ALTER cerror TYPE character varying(100);

ALTER TABLE sgmntinfo_documento ALTER cnotiexpe TYPE character varying(32);
ALTER TABLE sgmntinfo_documento ALTER cnotinifdest TYPE character varying(10);
ALTER TABLE sgmntinfo_documento ALTER ccodigo TYPE character varying(200);
ALTER TABLE sgmntinfo_documento ALTER cguid TYPE character varying(100);

ALTER TABLE ONLY sgmntinfo_documento ADD CONSTRAINT sgmntinfo_documentos_noti_fk 
	FOREIGN KEY (cnotiid) REFERENCES sgmntinfo_notificacion(cnotiid);

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