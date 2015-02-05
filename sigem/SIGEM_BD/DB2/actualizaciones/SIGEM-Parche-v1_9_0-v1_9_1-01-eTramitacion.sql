------------------------------------
-- ACTUALIZACION PARA EL REGISTRO --
------------------------------------

-- Fecha efectiva puede ser nula
ALTER TABLE sgmrtregistro DROP CONSTRAINT sgmrderegistry_pk;

CREATE TABLE TMP_DATOS LIKE sgmrtregistro;
INSERT INTO TMP_DATOS SELECT * FROM sgmrtregistro;

DROP TABLE sgmrtregistro;

CREATE TABLE sgmrtregistro (
    numero_registro character varying(16) NOT NULL,
    fecha_registro timestamp NOT NULL,
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
    fecha_efectiva timestamp
);

ALTER TABLE sgmrtregistro ADD CONSTRAINT sgmrderegistry_pk PRIMARY KEY (numero_registro);
CREATE INDEX fki_rde_registry_1 ON sgmrtregistro (emisor_id);
CREATE INDEX fki_rde_registry_2 ON sgmrtregistro (fecha_registro);
CREATE INDEX fki_rde_registry_4 ON sgmrtregistro (estado);

INSERT INTO sgmrtregistro SELECT * FROM TMP_DATOS;
DROP TABLE TMP_DATOS;

-- Tamanio de los campos al igual que estan en registro
ALTER TABLE sgmrtcatalogo_organos DROP CONSTRAINT sgmrdecat_org_pk;

CREATE TABLE TMP_DATOS LIKE sgmrtcatalogo_organos;
INSERT INTO TMP_DATOS SELECT * FROM sgmrtcatalogo_organos;

DROP TABLE sgmrtcatalogo_organos;

CREATE TABLE sgmrtcatalogo_organos (
    organo character varying(16) NOT NULL,
    descripcion character varying(250) NOT NULL
);

ALTER TABLE sgmrtcatalogo_organos ADD CONSTRAINT sgmrdecat_org_pk PRIMARY KEY (organo);

INSERT INTO sgmrtcatalogo_organos SELECT * FROM TMP_DATOS;
DROP TABLE TMP_DATOS;

ALTER TABLE sgmrtcatalogo_docstramite DROP CONSTRAINT rdecat_proc_doc_pd;
ALTER TABLE sgmrtcatalogo_tramites DROP CONSTRAINT sgmrdecat_proc_pk;

CREATE TABLE TMP_DATOS LIKE sgmrtcatalogo_tramites;
INSERT INTO TMP_DATOS SELECT * FROM sgmrtcatalogo_tramites;

DROP TABLE sgmrtcatalogo_tramites;

CREATE TABLE sgmrtcatalogo_tramites (
    id character varying(32) NOT NULL,
    asunto character varying(32) NOT NULL,
    descripcion character varying(256) NOT NULL,
    organo character varying(16) NOT NULL,
    limite_documentos character varying(1) NOT NULL,
    firma character varying(1) DEFAULT '1' NOT NULL,
    oficina character varying(32) NOT NULL,
    id_procedimiento character varying(32)
);

ALTER TABLE sgmrtcatalogo_tramites ADD CONSTRAINT sgmrdecat_proc_pk PRIMARY KEY (id);

INSERT INTO sgmrtcatalogo_tramites SELECT * FROM TMP_DATOS;
DROP TABLE TMP_DATOS;

ALTER TABLE sgmrtcatalogo_docstramite ADD CONSTRAINT rdecat_proc_doc_pd FOREIGN KEY (tramite_id) REFERENCES sgmrtcatalogo_tramites(id);

-- Tramites
UPDATE sgmrtcatalogo_docstramite SET codigo_documento = 'TRAM14D2' WHERE tramite_id = 'TRAM_14' AND codigo_documento = 'TRAM15D2';