------------------------------------
-- ACTUALIZACION PARA EL REGISTRO --
------------------------------------

--
-- Cambio del tipo de las columnas de fecha en las tablas que almacenan los hitos del expediente
--
ALTER TABLE sgm_cthitoestexp DROP CONSTRAINT sgm_cthitoestex_pk;

CREATE TABLE TMP_DATOS LIKE sgm_cthitoestexp;
INSERT INTO TMP_DATOS SELECT * FROM sgm_cthitoestexp;

DROP TABLE sgm_cthitoestexp;

CREATE TABLE sgm_cthitoestexp (
    cnumexp character varying(32) NOT NULL,
    cguid character varying(32) NOT NULL,
    ccod character varying(32),
    cfecha timestamp NOT NULL,
    cdescr character varying(4096) NOT NULL,
    cinfoaux blob
);

ALTER TABLE sgm_cthitoestexp ADD CONSTRAINT sgm_cthitoestex_pk PRIMARY KEY (cnumexp);

INSERT INTO sgm_cthitoestexp SELECT * FROM TMP_DATOS;
DROP TABLE TMP_DATOS;


CREATE TABLE TMP_DATOS LIKE sgm_cthitohistexp;
INSERT INTO TMP_DATOS SELECT * FROM sgm_cthitohistexp;

DROP TABLE sgm_cthitohistexp;

CREATE TABLE sgm_cthitohistexp (
    cnumexp character varying(32) NOT NULL,
    cguid character varying(32) NOT NULL,
    ccod character varying(32),
    cfecha timestamp NOT NULL,
    cdescr character varying(4096) NOT NULL,
    cinfoaux blob
);

INSERT INTO sgm_cthitohistexp SELECT * FROM TMP_DATOS;
DROP TABLE TMP_DATOS;

-- Tamanio de los campos al igual que estan en el tramite
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
    organo character varying(16) NOT NULL,
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

--------------------------------
-- ACTUALIZACION PARA EL PAGO --
--------------------------------

-- Modificar los registros de pagos manejados desde la consulta del registro telematico
-- para cambiar el identificador de las autoliquidaciones, por el correcto 
UPDATE sgmctpago
SET autoliquidacion_id='200'
WHERE autoliquidacion_id='100';