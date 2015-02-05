	--
-- scr_attachment_seq
--

CREATE SEQUENCE scr_attachment_seq
 INCREMENT BY 1
 MINVALUE 1
 NO MAXVALUE
 CACHE 1
 NO CYCLE;


--
-- scr_attachment_sign_info_seq
--

CREATE SEQUENCE scr_attachment_sign_info_seq
 INCREMENT BY 1
 MINVALUE 1
 NO MAXVALUE
 CACHE 1
 NO CYCLE;


--
-- scr_entityreg_id_seq
--

CREATE SEQUENCE scr_entityreg_id_seq
 INCREMENT BY 1
 MINVALUE 1
 NO MAXVALUE
 CACHE 1
 NO CYCLE;


--
-- scr_exregstate_id_seq
--

CREATE SEQUENCE scr_exregstate_id_seq
 INCREMENT BY 1
 MINVALUE 1
 NO MAXVALUE
 CACHE 1
 NO CYCLE;


--
-- scr_exreg_id_seq
--

CREATE SEQUENCE scr_exreg_id_seq
 INCREMENT BY 1
 MINVALUE 1
 NO MAXVALUE
 CACHE 1
 NO CYCLE;


--
-- scr_exreg_in_id_seq
--

CREATE SEQUENCE scr_exreg_in_id_seq
 INCREMENT BY 1
 MINVALUE 1
 NO MAXVALUE
 CACHE 1
 NO CYCLE;


--
-- scr_tramunit_id_seq
--

CREATE SEQUENCE scr_tramunit_id_seq
 INCREMENT BY 1
 MINVALUE 1
 NO MAXVALUE
 CACHE 1
 NO CYCLE;


--
-- idocautonumctlg
--

ALTER TABLE idocautonumctlg ALTER COLUMN tblname TYPE CHARACTER VARYING(32);

--
-- idocbtblctlg
--

ALTER TABLE idocbtblctlg ALTER COLUMN name TYPE CHARACTER VARYING(32);

--
-- iuserdata
--

CREATE TABLE iuserdata (id             INTEGER NOT NULL,
                        cargo          CHARACTER VARYING(256) NULL,
                        tfno_movil     CHARACTER VARYING(16) NULL,
                        id_certificado CHARACTER VARYING(256) NULL,
                        email          CHARACTER VARYING(256) NULL,
                        nombre         CHARACTER VARYING(256) NULL,
                        apellidos      CHARACTER VARYING(256) NULL);
CREATE INDEX iuserdata_ix_id ON iuserdata (id);

ALTER TABLE iuserdata ADD CONSTRAINT pk_iuserdata PRIMARY KEY (id);


--
-- scr_attachment
--

CREATE TABLE scr_attachment (id            INTEGER NOT NULL,
                             bookid        INTEGER NOT NULL,
                             folderid      INTEGER NOT NULL,
                             pageid        INTEGER NOT NULL,
                             comments      CHARACTER VARYING(50) NULL,
                             mime_type     CHARACTER VARYING(20) NULL,
                             name          CHARACTER VARYING(80) NULL,
                             code_name     CHARACTER VARYING(50) NULL,
                             validity_type INTEGER NULL,
                             document_type INTEGER NULL);
ALTER TABLE scr_attachment ADD CONSTRAINT pk_scr_attachment PRIMARY KEY (id);


--
-- scr_attachment_document_type
--

CREATE TABLE scr_attachment_document_type (id   INTEGER NOT NULL,
                                           name CHARACTER VARYING(250) NULL,
                                           code CHARACTER VARYING(2) NULL);
ALTER TABLE scr_attachment_document_type ADD CONSTRAINT pk_scr_attachment_document_type PRIMARY KEY (id);


--
-- scr_attachment_sign_info
--

CREATE TABLE scr_attachment_sign_info (id                   INTEGER NOT NULL,
                                       id_attachment        INTEGER NOT NULL,
                                       id_attachment_signed INTEGER NULL,
                                       cert                 BYTEA NULL,
                                       signature            BYTEA NULL,
                                       signature_alg        CHARACTER VARYING(2) NULL,
                                       signature_format     CHARACTER VARYING(2) NULL,
                                       time_signature       BYTEA NULL,
                                       ocsp_validation      BYTEA NULL,
                                       hash_alg             CHARACTER VARYING(2) NULL,
                                       hash                 BYTEA NULL);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT pk_scr_attachment_sign_info PRIMARY KEY (id);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT scr_attachment_sign_info_id_attachment_key UNIQUE (id_attachment);


--
-- scr_attachment_validity_type
--

CREATE TABLE scr_attachment_validity_type (id   INTEGER NOT NULL,
                                           name CHARACTER VARYING(250) NULL,
                                           code CHARACTER VARYING(2) NULL);
ALTER TABLE scr_attachment_validity_type ADD CONSTRAINT pk_scr_attachment_validity_type PRIMARY KEY (id);


--
-- scr_cities
--

ALTER TABLE scr_cities ADD CONSTRAINT pk_scr_cities0 PRIMARY KEY (id);

--
-- scr_citiesexreg
--

CREATE TABLE scr_citiesexreg (id      INTEGER NOT NULL,
                              code    CHARACTER VARYING(5) NOT NULL,
                              name    CHARACTER VARYING(80) NOT NULL,
                              id_city INTEGER NOT NULL);
ALTER TABLE scr_citiesexreg ADD CONSTRAINT pk_scr_citiesexreg PRIMARY KEY (id);


--
-- scr_country
--

CREATE TABLE scr_country (id      INTEGER NOT NULL,
                          tmstamp TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                          code    CHARACTER VARYING(16) NOT NULL,
                          name    CHARACTER VARYING(50) NOT NULL);


--
-- scr_dom
--

ALTER TABLE scr_dom ALTER COLUMN address TYPE CHARACTER VARYING(160);
ALTER TABLE scr_dom
ADD pais CHARACTER VARYING(50);

--
-- scr_entityreg
--

CREATE TABLE scr_entityreg (id      INTEGER NOT NULL,
                            code    CHARACTER VARYING(21) NOT NULL,
                            name    CHARACTER VARYING(80) NOT NULL,
                            id_ofic INTEGER NOT NULL);
ALTER TABLE scr_entityreg ADD CONSTRAINT pk_scr_entityreg PRIMARY KEY (id);


--
-- scr_exreg
--

CREATE TABLE scr_exreg (id              INTEGER NOT NULL,
                        id_arch         INTEGER NOT NULL,
                        id_fdr          INTEGER NOT NULL,
                        id_ofic         INTEGER NOT NULL,
                        type_orig       INTEGER NOT NULL,
                        exchange_date   TIMESTAMP WITHOUT TIME ZONE NULL,
                        state           INTEGER NOT NULL,
                        state_date      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                        id_exchange_sir CHARACTER VARYING(33) NULL,
                        id_exchange     INTEGER NOT NULL,
                        username        CHARACTER VARYING(32) NOT NULL,
                        code_tramunit   CHARACTER VARYING(21) NULL,
                        name_tramunit   CHARACTER VARYING(80) NULL,
                        code_entity     CHARACTER VARYING(21) NOT NULL,
                        name_entity     CHARACTER VARYING(80) NOT NULL,
                        comments        CHARACTER VARYING(4000) NULL);
ALTER TABLE scr_exreg ADD CONSTRAINT pk_scr_exreg PRIMARY KEY (id);


--
-- scr_exregstate
--

CREATE TABLE scr_exregstate (id         INTEGER NOT NULL,
                             id_exreg   INTEGER NOT NULL,
                             state      INTEGER NOT NULL,
                             state_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                             username   CHARACTER VARYING(32) NOT NULL,
                             comments   CHARACTER VARYING(4000) NULL);
ALTER TABLE scr_exregstate ADD CONSTRAINT pk_scr_exregstate PRIMARY KEY (id);


--
-- scr_exreg_in
--

CREATE TABLE scr_exreg_in (id              INTEGER NOT NULL,
                           id_arch         INTEGER NULL,
                           id_fdr          INTEGER NULL,
                           id_ofic         INTEGER NOT NULL,
                           exchange_date   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                           state           INTEGER NOT NULL,
                           state_date      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
                           id_exchange_sir CHARACTER VARYING(33) NOT NULL,
                           id_exchange     INTEGER NOT NULL,
                           username        CHARACTER VARYING(32) NOT NULL,
                           code_tramunit   CHARACTER VARYING(21) NULL,
                           name_tramunit   CHARACTER VARYING(80) NULL,
                           code_entity     CHARACTER VARYING(21) NOT NULL,
                           name_entity     CHARACTER VARYING(80) NOT NULL,
                           num_reg_orig    CHARACTER VARYING(20) NOT NULL,
                           contacto_orig   CHARACTER VARYING(160) NULL,
                           comments        CHARACTER VARYING(4000) NULL);
ALTER TABLE scr_exreg_in ADD CONSTRAINT pk_scr_exreg_in PRIMARY KEY (id);


--
-- scr_pagerepository
--

ALTER TABLE scr_pagerepository
ADD id_pagetype INTEGER DEFAULT 0 NOT NULL;

--
-- scr_pagetype
--

CREATE TABLE scr_pagetype (id   INTEGER NOT NULL,
                           name CHARACTER VARYING(100) NOT NULL);
ALTER TABLE scr_pagetype ADD CONSTRAINT pk_scr_pagetype PRIMARY KEY (id);


--
-- scr_pfis
--

ALTER TABLE scr_pfis ALTER COLUMN first_name TYPE CHARACTER VARYING(30);
ALTER TABLE scr_pfis ALTER COLUMN second_name TYPE CHARACTER VARYING(30);
ALTER TABLE scr_pfis ALTER COLUMN surname TYPE CHARACTER VARYING(30);

--
-- scr_pjur
--

ALTER TABLE scr_pjur ALTER COLUMN name TYPE CHARACTER VARYING(80);

--
-- scr_regint
--

ALTER TABLE scr_regint ALTER COLUMN name TYPE CHARACTER VARYING(95);

--
-- scr_prov
--

ALTER TABLE scr_prov ADD CONSTRAINT pk_scr_prov0 PRIMARY KEY (id);

--
-- scr_provexreg
--

CREATE TABLE scr_provexreg (id      INTEGER NOT NULL,
                            code    CHARACTER VARYING(2) NOT NULL,
                            name    CHARACTER VARYING(80) NOT NULL,
                            id_prov INTEGER NOT NULL);
ALTER TABLE scr_provexreg ADD CONSTRAINT pk_scr_provexreg PRIMARY KEY (id);


--
-- scr_repre
--

CREATE TABLE scr_repre (id               INTEGER NOT NULL,
                        id_representante INTEGER NOT NULL,
                        id_representado  INTEGER NOT NULL,
                        id_address       INTEGER NULL,
                        name             CHARACTER VARYING(95) NOT NULL);


--
-- scr_tramunit
--

CREATE TABLE scr_tramunit (id            INTEGER NOT NULL,
                           code_tramunit CHARACTER VARYING(21) NULL,
                           name_tramunit CHARACTER VARYING(80) NULL,
                           code_entity   CHARACTER VARYING(21) NULL,
                           name_entity   CHARACTER VARYING(80) NULL,
                           id_orgs       INTEGER NOT NULL);
ALTER TABLE scr_tramunit ADD CONSTRAINT pk_scr_tramunit PRIMARY KEY (id);
ALTER TABLE scr_tramunit ADD CONSTRAINT u_code_tramunit UNIQUE (code_tramunit);


--
-- scr_userfilter
--

DROP INDEX scr_userfilter2;
CREATE UNIQUE INDEX scr_userfilter2 ON scr_userfilter (idarch, iduser, type_obj);

--
-- FOREIGN KEYS [CREATE]
--

ALTER TABLE scr_attachment ADD CONSTRAINT fk_document_type_attachment FOREIGN KEY (document_type) REFERENCES scr_attachment_document_type (id)
                                                                      ON DELETE NO ACTION
                                                                      ON UPDATE NO ACTION
                                                                      NOT DEFERRABLE
                                                                      INITIALLY IMMEDIATE;
ALTER TABLE scr_attachment ADD CONSTRAINT fk_validity_type_attachment FOREIGN KEY (validity_type) REFERENCES scr_attachment_validity_type (id)
                                                                      ON DELETE NO ACTION
                                                                      ON UPDATE NO ACTION
                                                                      NOT DEFERRABLE
                                                                      INITIALLY IMMEDIATE;
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_sign_info_attachment FOREIGN KEY (id_attachment) REFERENCES scr_attachment (id)
                                                                            ON DELETE NO ACTION
                                                                            ON UPDATE NO ACTION
                                                                            NOT DEFERRABLE
                                                                            INITIALLY IMMEDIATE;
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_sign_info_attachment_signed FOREIGN KEY (id_attachment_signed) REFERENCES scr_attachment (id)
                                                                                   ON DELETE NO ACTION
                                                                                   ON UPDATE NO ACTION
                                                                                   NOT DEFERRABLE
                                                                                   INITIALLY IMMEDIATE;
ALTER TABLE scr_citiesexreg ADD CONSTRAINT fk_citiesexregcities FOREIGN KEY (id_city) REFERENCES scr_cities (id)
                                                                ON DELETE NO ACTION
                                                                ON UPDATE NO ACTION
                                                                NOT DEFERRABLE
                                                                INITIALLY IMMEDIATE;
ALTER TABLE scr_entityreg ADD CONSTRAINT fk_entityofic FOREIGN KEY (id_ofic) REFERENCES scr_ofic (id)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION
                                                       NOT DEFERRABLE
                                                       INITIALLY IMMEDIATE;
ALTER TABLE scr_pagerepository ADD CONSTRAINT fk_pagerepository0 FOREIGN KEY (id_pagetype) REFERENCES scr_pagetype (id)
                                                                 ON DELETE NO ACTION
                                                                 ON UPDATE NO ACTION
                                                                 NOT DEFERRABLE
                                                                 INITIALLY IMMEDIATE;
ALTER TABLE scr_provexreg ADD CONSTRAINT fk_provexregprov FOREIGN KEY (id_prov) REFERENCES scr_prov (id)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION
                                                          NOT DEFERRABLE
                                                          INITIALLY IMMEDIATE;
ALTER TABLE scr_tramunit ADD CONSTRAINT fk_tramunitgorgs FOREIGN KEY (id_orgs) REFERENCES scr_orgs (id)
                                                         ON DELETE NO ACTION
                                                         ON UPDATE NO ACTION
                                                         NOT DEFERRABLE
                                                         INITIALLY IMMEDIATE;




