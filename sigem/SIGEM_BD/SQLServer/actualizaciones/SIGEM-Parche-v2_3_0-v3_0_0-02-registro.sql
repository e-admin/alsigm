--
-- idocautonumctlg
--

ALTER TABLE idocautonumctlg ALTER COLUMN tblname VARCHAR(32) NOT NULL;

--
-- idocbtblctlg
--

ALTER TABLE idocbtblctlg ALTER COLUMN name VARCHAR(32) NOT NULL;

--
-- iuserdata
--

CREATE TABLE iuserdata (id             INT NOT NULL,
                        cargo          VARCHAR(256) NULL,
                        tfno_movil     VARCHAR(16) NULL,
                        id_certificado VARCHAR(256) NULL,
                        email          VARCHAR(256) NULL,
                        nombre         VARCHAR(256) NULL,
                        apellidos      VARCHAR(256) NULL)
    ;
CREATE  INDEX iuserdata_ix_id ON iuserdata (id ASC)
    ;

ALTER TABLE iuserdata ADD CONSTRAINT pk_iuserdata PRIMARY KEY (id)
    ;


--
-- scr_attachment
--

CREATE TABLE scr_attachment (id            INT NOT NULL,
                             bookid        INT NOT NULL,
                             folderid      INT NOT NULL,
                             pageid        INT NOT NULL,
                             comments      VARCHAR(50) NULL,
                             mime_type     VARCHAR(20) NULL,
                             name          VARCHAR(80) NULL,
                             code_name     VARCHAR(50) NULL,
                             validity_type INT NULL,
                             document_type INT NULL)
    ;
ALTER TABLE scr_attachment ADD CONSTRAINT pk_scr_attachment PRIMARY KEY (id)
    ;


--
-- scr_attachment_document_type
--

CREATE TABLE scr_attachment_document_type (id   INT NOT NULL,
                                           name VARCHAR(250) NULL,
                                           code VARCHAR(2) NULL)
    ;
ALTER TABLE scr_attachment_document_type ADD CONSTRAINT pk_scr_attachment_document_type PRIMARY KEY (id)
    ;


--
-- scr_attachment_seq
--

CREATE TABLE scr_attachment_seq (id INT IDENTITY(1,1) NOT NULL)
    ;


--
-- scr_attachment_sign_info
--

CREATE TABLE scr_attachment_sign_info (id                   INT NOT NULL,
                                       id_attachment        INT NOT NULL UNIQUE,
                                       id_attachment_signed INT NULL,
                                       cert                 IMAGE NULL,
                                       signature            IMAGE NULL,
                                       signature_alg        VARCHAR(2) NULL,
                                       signature_format     VARCHAR(2) NULL,
                                       time_signature       IMAGE NULL,
                                       ocsp_validation      IMAGE NULL,
                                       hash_alg             VARCHAR(2) NULL,
                                       hash                 IMAGE NULL)
    ;

ALTER TABLE scr_attachment_sign_info
ADD CONSTRAINT pk_scr_attachment_sign_info PRIMARY KEY (id)
;

--
-- scr_attachment_sign_info_seq
--

CREATE TABLE scr_attachment_sign_info_seq (id INT IDENTITY(1,1) NOT NULL)
    ;


--
-- scr_attachment_validity_type
--

CREATE TABLE scr_attachment_validity_type (id   INT NOT NULL,
                                           name VARCHAR(250) NULL,
                                           code VARCHAR(2) NULL) ;
ALTER TABLE scr_attachment_validity_type ADD CONSTRAINT pk_scr_attachment_validity_type PRIMARY KEY (id)
    ;


--
-- scr_cities
--

--DROP INDEX SCR_CITIES0 ON scr_cities;

--ALTER TABLE scr_cities ADD CONSTRAINT pk_cities PRIMARY KEY (id)
--  ;

--
-- scr_citiesexreg
--

CREATE TABLE scr_citiesexreg (id      INT NOT NULL,
                              code    VARCHAR(5) NOT NULL,
                              name    VARCHAR(80) NOT NULL,
                              id_city INT NOT NULL)
    ;
ALTER TABLE scr_citiesexreg ADD CONSTRAINT pk_scr_citiesexreg PRIMARY KEY (id)
    ;


--
-- scr_country
--

CREATE TABLE scr_country (id      INT NOT NULL,
                          tmstamp DATETIME NOT NULL,
                          code    VARCHAR(16) NOT NULL,
                          name    VARCHAR(50) NOT NULL)
    ;


--
-- scr_dom
--

ALTER TABLE scr_dom ALTER COLUMN address VARCHAR(160) NOT NULL;
ALTER TABLE scr_dom
ADD pais VARCHAR(50);

--
-- scr_entityreg
--

CREATE TABLE scr_entityreg (id      INT NOT NULL,
                            code    VARCHAR(21) NOT NULL,
                            name    VARCHAR(80) NOT NULL,
                            id_ofic INT NOT NULL)
    ;
ALTER TABLE scr_entityreg ADD CONSTRAINT pk_scr_entityreg PRIMARY KEY (id)
    ;


--
-- scr_entityreg_id_seq
--

CREATE TABLE scr_entityreg_id_seq (id INT IDENTITY(1,1) NOT NULL)
    ;


--
-- scr_exreg
--

CREATE TABLE scr_exreg (id              INT NOT NULL,
                        id_arch         INT NOT NULL,
                        id_fdr          INT NOT NULL,
                        id_ofic         INT NOT NULL,
                        type_orig       INT NOT NULL,
                        exchange_date   DATETIME NULL,
                        state           INT NOT NULL,
                        state_date      DATETIME NOT NULL,
                        id_exchange_sir VARCHAR(33) NULL,
                        id_exchange     INT NOT NULL,
                        username        VARCHAR(32) NOT NULL,
                        code_tramunit   VARCHAR(21) NULL,
                        name_tramunit   VARCHAR(80) NULL,
                        code_entity     VARCHAR(21) NOT NULL,
                        name_entity     VARCHAR(80) NOT NULL,
                        comments        VARCHAR(4000) NULL)
    ;
ALTER TABLE scr_exreg ADD CONSTRAINT pk_scr_exreg PRIMARY KEY (id)
    ;


--
-- scr_exregstate
--

CREATE TABLE scr_exregstate (id         INT NOT NULL,
                             id_exreg   INT NOT NULL,
                             state      INT NOT NULL,
                             state_date DATETIME NOT NULL,
                             username   VARCHAR(32) NOT NULL,
                             comments   VARCHAR(4000) NULL)
    ;
ALTER TABLE scr_exregstate ADD CONSTRAINT pk_scr_exregstate PRIMARY KEY (id)
    ;


--
-- scr_exregstate_id_seq
--

CREATE TABLE scr_exregstate_id_seq (id INT IDENTITY(1,1) NOT NULL)
    ;


--
-- scr_exreg_id_seq
--

CREATE TABLE scr_exreg_id_seq (id INT IDENTITY(1,1) NOT NULL)
    ;


--
-- scr_exreg_in
--

CREATE TABLE scr_exreg_in (id              INT NOT NULL,
                           id_arch         INT NULL,
                           id_fdr          INT NULL,
                           id_ofic         INT NOT NULL,
                           exchange_date   DATETIME NOT NULL,
                           state           INT NOT NULL,
                           state_date      DATETIME NOT NULL,
                           id_exchange_sir VARCHAR(33) NOT NULL,
                           id_exchange     INT NOT NULL,
                           username        VARCHAR(32) NOT NULL,
                           code_tramunit   VARCHAR(21) NULL,
                           name_tramunit   VARCHAR(80) NULL,
                           code_entity     VARCHAR(21) NOT NULL,
                           name_entity     VARCHAR(80) NOT NULL,
                           num_reg_orig    VARCHAR(20) NOT NULL,
                           contacto_orig   VARCHAR(160) NULL,
                           comments        VARCHAR(4000) NULL)
    ;
ALTER TABLE scr_exreg_in ADD CONSTRAINT pk_scr_exreg_in PRIMARY KEY (id)
    ;


--
-- scr_exreg_in_id_seq
--

CREATE TABLE scr_exreg_in_id_seq (id INT IDENTITY(1,1) NOT NULL)
    ;


--
-- scr_pagerepository
--

ALTER TABLE scr_pagerepository
ADD id_pagetype INT DEFAULT ((0)) NOT NULL;

--
-- scr_pagetype
--

CREATE TABLE scr_pagetype (id   INT NOT NULL,
                           name VARCHAR(100) NOT NULL)
    ;
ALTER TABLE scr_pagetype ADD CONSTRAINT pk_pagetype PRIMARY KEY (id)
    ;


--
-- scr_pfis
--

ALTER TABLE scr_pfis ALTER COLUMN first_name VARCHAR(30) NOT NULL;
ALTER TABLE scr_pfis ALTER COLUMN second_name VARCHAR(30) NULL;
ALTER TABLE scr_pfis ALTER COLUMN surname VARCHAR(30) NULL;

--
-- scr_pjur
--

ALTER TABLE scr_pjur ALTER COLUMN name VARCHAR(80) NOT NULL;

--
-- scr_regint
--

ALTER TABLE scr_regint ALTER COLUMN name VARCHAR(95) NOT NULL;

--
-- scr_prov
--

--DROP INDEX SCR_PROV0 ON scr_prov;
--CREATE UNIQUE INDEX pk_prov ON scr_prov (id ASC)
 --   ;
--ALTER TABLE scr_prov ADD CONSTRAINT pk_prov PRIMARY KEY (id)
--    ;

--
-- scr_provexreg
--

CREATE TABLE scr_provexreg (id      INT NOT NULL,
                            code    VARCHAR(2) NOT NULL,
                            name    VARCHAR(80) NOT NULL,
                            id_prov INT NOT NULL)
    ;
ALTER TABLE scr_provexreg ADD CONSTRAINT pk_scr_provexreg PRIMARY KEY (id)
    ;


--
-- scr_repre
--

CREATE TABLE scr_repre (id               INT NOT NULL,
                        id_representante INT NOT NULL,
                        id_representado  INT NOT NULL,
                        id_address       INT NULL,
                        name             VARCHAR(95) NOT NULL)
    ;


--
-- scr_tramunit
--

CREATE TABLE scr_tramunit (id            INT NOT NULL,
                           code_tramunit VARCHAR(21) NOT NULL,
                           name_tramunit VARCHAR(80) NOT NULL,
                           code_entity   VARCHAR(21) NOT NULL,
                           name_entity   VARCHAR(80) NOT NULL,
                           id_orgs       INT NOT NULL)
    ;
ALTER TABLE scr_tramunit ADD CONSTRAINT pk_scr_tramunit PRIMARY KEY (id)
    ;
ALTER TABLE scr_tramunit ADD CONSTRAINT u_code_tramunit UNIQUE (code_tramunit)
    ;


--
-- scr_tramunit_id_seq
--

CREATE TABLE scr_tramunit_id_seq (id INT IDENTITY(1,1) NOT NULL) ;


--
-- scr_userfilter
--

DROP INDEX SCR_USERFILTER2 ON scr_userfilter;
CREATE  INDEX SCR_USERFILTER2 ON scr_userfilter (idarch ASC, iduser ASC, type_obj ASC)   ;

--
-- FOREIGN KEYS [CREATE]
--

ALTER TABLE scr_attachment WITH CHECK ADD CONSTRAINT fk_validity_type_attachment FOREIGN KEY (validity_type) REFERENCES scr_attachment_validity_type (id);
ALTER TABLE scr_attachment CHECK CONSTRAINT fk_validity_type_attachment;
ALTER TABLE scr_attachment WITH CHECK ADD CONSTRAINT fk_document_type_attachment FOREIGN KEY (document_type) REFERENCES scr_attachment_document_type (id);
ALTER TABLE scr_attachment CHECK CONSTRAINT fk_document_type_attachment;

ALTER TABLE scr_attachment_sign_info WITH CHECK ADD CONSTRAINT fk_sign_info_attachment FOREIGN KEY (id_attachment) REFERENCES scr_attachment (id);
ALTER TABLE scr_attachment_sign_info CHECK CONSTRAINT fk_sign_info_attachment;
ALTER TABLE scr_attachment_sign_info WITH CHECK ADD CONSTRAINT fk_sign_info_attachment_signed FOREIGN KEY (id_attachment_signed) REFERENCES scr_attachment (id);
ALTER TABLE scr_attachment_sign_info CHECK CONSTRAINT fk_sign_info_attachment_signed;

ALTER TABLE scr_cities ADD CONSTRAINT pk_cities PRIMARY KEY (id);
ALTER TABLE scr_citiesexreg WITH CHECK ADD CONSTRAINT fk_citiesexregcities FOREIGN KEY (id_city) REFERENCES scr_cities (id);
ALTER TABLE scr_citiesexreg CHECK CONSTRAINT fk_citiesexregcities;
ALTER TABLE scr_entityreg WITH CHECK ADD CONSTRAINT fk_entityofic FOREIGN KEY (id_ofic) REFERENCES scr_ofic (id);
ALTER TABLE scr_entityreg CHECK CONSTRAINT fk_entityofic;

ALTER TABLE scr_pagerepository WITH CHECK ADD CONSTRAINT fk_pagerepository0 FOREIGN KEY (id_pagetype) REFERENCES scr_pagetype (id);
ALTER TABLE scr_pagerepository CHECK CONSTRAINT fk_pagerepository0;

ALTER TABLE scr_prov ADD CONSTRAINT pk_prov PRIMARY KEY (id);
ALTER TABLE scr_provexreg WITH CHECK ADD CONSTRAINT fk_provexregprov FOREIGN KEY (id_prov) REFERENCES scr_prov (id);
ALTER TABLE scr_provexreg CHECK CONSTRAINT fk_provexregprov;

ALTER TABLE scr_tramunit WITH CHECK ADD CONSTRAINT fk_tramunitgorgs FOREIGN KEY (id_orgs) REFERENCES scr_orgs (id);
ALTER TABLE scr_tramunit CHECK CONSTRAINT fk_tramunitgorgs;
