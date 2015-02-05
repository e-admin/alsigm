--------------------------------------
-- ACTUALIZACION MODULO CALENDARIOS --
--------------------------------------

-- Tabla de configuraci贸n del calendario de tramitaci贸n electr贸nica
CREATE TABLE sgmrtcatalogo_calendario (
  laborables character varying(13) NOT NULL,
  hora_inicio character varying(2),
  minuto_inicio character varying(2),
  hora_fin character varying(2),
  minuto_fin character varying(2)
); 


-- Tabla de configuraci贸n de los d铆as festivos del calendario de tramitaci贸n electr贸nica
CREATE TABLE sgmrtcatalogo_diascalendario (
  id integer NOT NULL,
  fecha character varying(5) NOT NULL,
  descripcion character varying(256) NOT NULL,
  fijo integer NOT NULL
); 

-- A帽adir campo de fecha efectiva del registro telem谩tico para la consolidaci贸n.
-- Para los registros existente, establece como fecha efectiva la fecha de registro.
ALTER TABLE sgmrtcatalogo_diascalendario ADD CONSTRAINT diascalendar_pkey PRIMARY KEY (id);

ALTER TABLE sgmrtregistro ADD COLUMN fecha_efectiva timestamp NOT NULL DEFAULT CURRENT TIMESTAMP;
UPDATE sgmrtregistro set fecha_efectiva = fecha_registro;


-- SENTENCIAS SOLO NECESARIAS PARA VERSIONES 8.x y anteriores de DB2.
-- A partir de DB2 v9 se puede usar la siguiente l韓ea
--ALTER TABLE sgmrtregistro ALTER COLUMN fecha_registro SET NOT NULL;

RENAME TABLE sgmrtregistro TO sgmrtregistro2;
CREATE TABLE sgmrtregistro (
    numero_registro character varying(16) NOT NULL,
		fecha_registro timestamp NOT NULL DEFAULT CURRENT TIMESTAMP,
    emisor_id character varying(128) NOT NULL,
    nombre character varying(128) NOT NULL,
    correo_electronico character varying(60),
    asunto character varying(200) NOT NULL,
    organo character varying(7) NOT NULL,
    tipo_emisor_id character varying(1) NOT NULL,
    info_adicional blob,
    estado character varying(1) NOT NULL,
    oficina character varying(32),
    numero_expediente character varying(32),
		fecha_efectiva timestamp NOT NULL DEFAULT CURRENT TIMESTAMP
);
ALTER TABLE sgmrtregistro ADD CONSTRAINT sgmrderegistry_pk PRIMARY KEY (numero_registro);
INSERT INTO sgmrtregistro SELECT * FROM sgmrtregistro2;
DROP TABLE sgmrtregistro2;

CREATE INDEX fki_rde_registry_1 ON sgmrtregistro (emisor_id);
CREATE INDEX fki_rde_registry_2 ON sgmrtregistro (fecha_registro);
CREATE INDEX fki_rde_registry_4 ON sgmrtregistro (estado);

-----------------------------------------
-- ACTUALIZACION MODULO NOTIFICACIONES --
-----------------------------------------
-- En esta version se modifica la clave primaria de la tabla de notificaciones
-- para permitir en SIGEM, la creacion de varias notificaciones del mismo expediente al mismo
-- destinatario.
ALTER TABLE sgmntinfo_notificacion ADD cnotiid character varying(64) NOT NULL DEFAULT '';
UPDATE sgmntinfo_notificacion set cnotiid='ACT_' || cnumexp || '_' || cnifdest;
ALTER TABLE sgmntinfo_documento DROP CONSTRAINT nt_docs_noti;
ALTER TABLE sgmntinfo_notificacion DROP CONSTRAINT sgmntinfo_notif_pk;
ALTER TABLE sgmntinfo_notificacion ADD CONSTRAINT sgmntinfo_notif_pk PRIMARY KEY (cnotiid);

ALTER TABLE sgmntinfo_documento ADD cnotiid character varying(64) NOT NULL DEFAULT '';
UPDATE sgmntinfo_documento set cnotiid= 'ACT_' || cnotiexpe || '_' || cnotinifdest;
ALTER TABLE sgmntinfo_documento ADD CONSTRAINT nt_docs_noti FOREIGN KEY (cnotiid) REFERENCES sgmntinfo_notificacion(cnotiid);

-----------------------------------------
-- ACTUALIZACION MODULO NOTIFICACIONES --
-----------------------------------------
-- Cambios necesarios para integrar SMS en notificaciones. Hasta ahora solo Sisnot.
-- Modulo de notificaciones pasa a ser generico.
ALTER TABLE sgmntinfo_notificacion ALTER COLUMN cguid SET DATA TYPE  character varying(64);
ALTER TABLE sgmntinfo_notificacion ADD csistemaid character varying(32);
ALTER TABLE sgmntinfo_notificacion ADD cdeu character varying(20);
ALTER TABLE sgmntinfo_notificacion ADD ctfnomovil character varying(14);

-- character() to character varying()
ALTER TABLE sgmntinfo_documento DROP CONSTRAINT nt_docs_noti;

-- SENTENCIAS SOLO NECESARIAS PARA VERSIONES 8.x y anteriores de DB2.
-- Es posible que en posteriores versiones de postgres las siguiente sentencias funcionen correctamente
--
--ALTER TABLE sgmntinfo_documento ALTER COLUMN cnotiexpe SET DATA TYPE character varying(32);
--ALTER TABLE sgmntinfo_documento ALTER COLUMN cnotinifdest SET DATA TYPE character varying(10);
--ALTER TABLE sgmntinfo_documento ALTER COLUMN ccodigo SET DATA TYPE character varying(200);
--ALTER TABLE sgmntinfo_documento ALTER COLUMN cguid SET DATA TYPE character varying(100);

RENAME TABLE sgmntinfo_documento TO sgmntinfo_documento2;
CREATE TABLE sgmntinfo_documento (
    cnotiexpe character varying(32) NOT NULL,
    cnotinifdest character varying(10) NOT NULL,
    ccodigo character varying(200),
    cguid character varying(100),
	cnotiid character varying(64) NOT NULL
);
INSERT INTO sgmntinfo_documento SELECT * FROM sgmntinfo_documento2;
DROP TABLE sgmntinfo_documento2;

ALTER TABLE sgmntinfo_documento ADD CONSTRAINT nt_docs_noti FOREIGN KEY (cnotiid) REFERENCES sgmntinfo_notificacion(cnotiid);

ALTER TABLE sgmntinfo_documento ADD ctipodoc integer DEFAULT 1;


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