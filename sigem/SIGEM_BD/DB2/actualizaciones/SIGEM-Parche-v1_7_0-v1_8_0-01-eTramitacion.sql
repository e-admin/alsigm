ALTER TABLE sgmrdedocumentos ADD filehash character varying(64) NOT NULL DEFAULT '';
UPDATE sgmrdedocumentos SET filehash=hash;
ALTER TABLE sgmrdedocumentos ALTER filehash DROP DEFAULT;
ALTER TABLE sgmrdedocumentos DROP COLUMN hash;


ALTER TABLE sgmafsesion_info
    ALTER COLUMN emisor_certificado_solicitante SET DATA TYPE character varying(256);
ALTER TABLE sgmafsesion_info
    ALTER COLUMN solicitante_certificado_sn SET DATA TYPE character varying(256);

--si falla ejecutar: 
--ALTER TABLE sgmafsesion_info DROP CONSTRAINT sgmrdeses_info_pk;
--ALTER TABLE sgmafsesion_info DROP CONSTRAINT rdeses_info_hookid;

--CREATE TABLE TMP_DATOS LIKE sgmafsesion_info;
--INSERT INTO TMP_DATOS SELECT * FROM sgmafsesion_info;

--DROP TABLE sgmafsesion_info;

--CREATE TABLE sgmafsesion_info (
--    sesionid character varying(32) NOT NULL,
--    conectorid character varying(32) NOT NULL,
--    fecha_login character varying(19) NOT NULL,
--    nombre_solicitante character varying(128) NOT NULL,
--    solicitante_id character varying(32) NOT NULL,
--    correo_electronico_solicitante character varying(60) NOT NULL,
--    emisor_certificado_solicitante character varying(256),
--    solicitante_certificado_sn character varying(256),
--    solicitante_inquality character varying(32),
--    razon_social character varying(256),
--    cif character varying(9),
--    id_entidad character varying(10)
--);

--ALTER TABLE sgmafsesion_info ADD CONSTRAINT sgmrdeses_info_pk PRIMARY KEY (sesionid);
--CREATE INDEX fki_ses_inf_hookid ON sgmafsesion_info (conectorid);
--ALTER TABLE sgmafsesion_info ADD CONSTRAINT rdeses_info_hookid FOREIGN KEY (conectorid) REFERENCES sgmrtcatalogo_conectores(conectorid);

--INSERT INTO sgmafsesion_info SELECT * FROM TMP_DATOS;
--DROP TABLE TMP_DATOS;


ALTER TABLE sgmntinfo_documento
    ALTER COLUMN cnotiexpe SET DATA TYPE character(32);

--si falla ejecutar:
--ALTER TABLE sgmntinfo_documento DROP CONSTRAINT nt_docs_noti;

--CREATE TABLE TMP_DATOS LIKE sgmntinfo_documento;
--INSERT INTO TMP_DATOS SELECT * FROM sgmntinfo_documento;

--DROP TABLE sgmntinfo_documento;

--CREATE TABLE sgmntinfo_documento (
--    cnotiexpe character(32) NOT NULL,
--    cnotinifdest character(10) NOT NULL,
--    ccodigo character(200),
--    cguid character(100)
--);

--CREATE INDEX fki_nt_docs_notif ON sgmntinfo_documento (cnotiexpe, cnotinifdest);
--ALTER TABLE sgmntinfo_documento ADD CONSTRAINT nt_docs_noti FOREIGN KEY (cnotiexpe, cnotinifdest) REFERENCES sgmntinfo_notificacion(cnumexp, cnifdest);

--INSERT INTO sgmntinfo_documento SELECT * FROM TMP_DATOS;
--DROP TABLE TMP_DATOS;


ALTER TABLE sgmntinfo_notificacion
    ALTER COLUMN cguid SET DATA TYPE character varying(32);
ALTER TABLE sgmntinfo_notificacion
    ALTER COLUMN cnumexp SET DATA TYPE character varying(32);

--si falla ejecutar:
--ALTER TABLE sgmntinfo_notificacion DROP CONSTRAINT sgmntinfo_notif_pk;
--ALTER TABLE sgmntinfo_notificacion DROP CONSTRAINT nt_notif_est_noti;
--ALTER TABLE sgmntinfo_documento DROP CONSTRAINT nt_docs_noti;

--CREATE TABLE TMP_DATOS LIKE sgmntinfo_notificacion;
--INSERT INTO TMP_DATOS SELECT * FROM sgmntinfo_notificacion;

--DROP TABLE sgmntinfo_notificacion;

--CREATE TABLE sgmntinfo_notificacion (
--    cguid character varying(32),
--    cnifdest character varying(10) NOT NULL,
--    cnumreg character varying(16),
--    cfhreg date,
--    cnumexp character varying(32) NOT NULL,
--    cproc character varying(50),
--    cestado integer,
--    cfhestado date,
--    cfhentrega date,
--    cusuario character varying(10),
--    ctipocorrespondencia character varying(10),
--    corganismo character varying(100),
--    casunto character varying(100),
--    ctipo character varying(20),
--    ctexto character varying(1000),
--    cnombredest character varying(100),
--    capellidosdest character varying(150),
--    ccorreodest character varying(200),
--    cidioma character varying(20),
--    ctipovia character varying(10),
--    cvia character varying(200),
--    cnumerovia character varying(10),
--    cescaleravia character varying(10),
--    cpisovia character varying(10),
--    cpuertavia character varying(10),
--    ctelefono character varying(20),
--    cmunicipio character varying(100),
--    cprovincia character varying(100),
--    ccp character varying(10),
--    cerror character varying(100)
--);

--CREATE INDEX fki_nt_notif_estad ON sgmntinfo_notificacion (cestado);
--ALTER TABLE sgmntinfo_notificacion ADD CONSTRAINT sgmntinfo_notif_pk PRIMARY KEY (cnumexp, cnifdest);
--ALTER TABLE sgmntinfo_notificacion ADD CONSTRAINT nt_notif_est_noti FOREIGN KEY (cestado) REFERENCES sgmntinfo_estado_noti(cguid);
--ALTER TABLE sgmntinfo_documento ADD CONSTRAINT nt_docs_noti FOREIGN KEY (cnotiexpe, cnotinifdest) REFERENCES sgmntinfo_notificacion(cnumexp, cnifdest);

--INSERT INTO sgmntinfo_notificacion SELECT * FROM TMP_DATOS;
--DROP TABLE TMP_DATOS;
