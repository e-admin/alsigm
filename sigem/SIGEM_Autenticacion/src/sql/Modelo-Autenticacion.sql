-- Table: sgmafconector_autenticacion

-- DROP TABLE sgmafconector_autenticacion;

CREATE TABLE sgmafconector_autenticacion
(
  tramiteid character varying(32) NOT NULL,
  conectorid character varying(32) NOT NULL,
  CONSTRAINT sgmafconector_autenticacion_pkey PRIMARY KEY (tramiteid, conectorid)
) 
WITHOUT OIDS;
ALTER TABLE sgmafconector_autenticacion OWNER TO postgres;

INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_1', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_4', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_1', 'AUTH_WEB_USER');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_3', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_4', 'AUTH_CERTIFICADO');
INSERT INTO sgmafconector_autenticacion (tramiteid, conectorid) VALUES ('TRAM_3', 'AUTH_WEB_USER');


-- Table: sgmafsesion_info

-- DROP TABLE sgmafsesion_info;

CREATE TABLE sgmafsesion_info
(
  sesionid character varying(32) NOT NULL,
  conectorid character varying(32) NOT NULL,
  fecha_login character varying(19) NOT NULL,
  nombre_solicitante character varying(50) NOT NULL, -- Nombre del usuario logado
  apellidos_solicitante character varying(50) NOT NULL, -- Apellidos del usuario logado
  solicitante_id character varying(32) NOT NULL,
  correo_electronico_solicitante character varying(60) NOT NULL,
  emisor_certificado_solicitante character varying(32),
  solicitante_certificado_sn character varying(32),
  solicitante_inquality character varying(32),
  razon_social character varying(256),
  cif character varying(9), -- Docuemnto de identidad del usuario logado
  id_entidad character varying(10),
  CONSTRAINT sgmrdesession_info_pkey PRIMARY KEY (sesionid),
  CONSTRAINT sgmrdesession_info_hookid_fkey FOREIGN KEY (conectorid)
      REFERENCES sgmrtcatalogo_conectores (conectorid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
) 
WITHOUT OIDS;
ALTER TABLE sgmafsesion_info OWNER TO postgres;
COMMENT ON COLUMN sgmafsesion_info.nombre_solicitante IS 'Nombre del usuario logado';
COMMENT ON COLUMN sgmafsesion_info.apellidos_solicitante IS 'Apellidos del usuario logado';
COMMENT ON COLUMN sgmafsesion_info.cif IS 'Docuemnto de identidad del usuario logado';


-- Index: fki_session_info_hookid

-- DROP INDEX fki_session_info_hookid;

CREATE INDEX fki_session_info_hookid
  ON sgmafsesion_info
  USING btree
  (conectorid);



