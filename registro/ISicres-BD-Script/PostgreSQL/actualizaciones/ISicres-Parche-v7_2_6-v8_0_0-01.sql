--Actualizamos el contador SCR_REPORTS al maximo id de la tabla SCR_REPORTS
UPDATE SCR_CONTADOR SET CONTADOR=(SELECT MAX(ID) from SCR_REPORTS) WHERE TABLAID = 'SCR_REPORTS';

--Borramos la entrada en SCR_CONTADOR con el contador SCR_REPORT
DELETE FROM SCR_CONTADOR WHERE TABLAID = 'SCR_REPORT';


--Borramos las tablas de intercambio registral
drop table scr_tramunit;
drop sequence scr_tramunit_id_seq;

drop table scr_entityreg;
drop sequence scr_entityreg_id_seq;

drop table scr_exreg;
drop sequence scr_exreg_id_seq;

drop table scr_exregaccept;
drop sequence scr_exregaccept_id_seq;


drop sequence scr_attachment_seq;
drop sequence  scr_attachment_sign_info_seq;
drop sequence scr_exregstate_id_seq;

drop table scr_attachment;
drop table scr_attachment_document_type;
drop table scr_attachment_sign_info;
drop table scr_attachment_validity_type;
drop table scr_exreg_in;
drop table scr_exregstate;

drop table scr_country;
drop table scr_provexreg;
drop table scr_citiesexreg;
drop table scr_repre;

DROP INDEX scr_userfilter2;
CREATE UNIQUE INDEX scr_userfilter2 ON scr_userfilter USING btree (idarch, iduser, type_obj);

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



INSERT INTO scr_typedoc (id, description, type_person, code) VALUES (5, 'Otros', 0, 'X');

--Actualizamos el contador SCR_REPORTS al maximo id de la tabla SCR_REPORTS
UPDATE SCR_CONTADOR SET CONTADOR=(SELECT MAX(ID) from SCR_REPORTS) WHERE TABLAID = 'SCR_REPORTS';

--Borramos la entrada en SCR_CONTADOR con el contador SCR_REPORT
DELETE FROM SCR_CONTADOR WHERE TABLAID = 'SCR_REPORT';

-------Inserts data
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (1,'COPIA','01');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (2,'COPIA COMPULSADA','02');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (3,'COPIA ORIGINAL','03');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (4,'ORIGINAL','04');


INSERT INTO scr_attachment_document_type(id,name,code) VALUES (1,'FORMULARIO','01');
INSERT INTO scr_attachment_document_type(id,name,code) VALUES (2,'DOCUMENTO ADJUNTO','02');
INSERT INTO scr_attachment_document_type(id,name,code) VALUES (3,'FICHERO TECNICO','03');

-- Datos para la tabla scr_country
INSERT INTO scr_country VALUES (1, localtimestamp, 'AF',  'Afganistán');
INSERT INTO scr_country VALUES (2, localtimestamp, 'AX',  'Islas Gland');
INSERT INTO scr_country VALUES (3, localtimestamp, 'AL',  'Albania');
INSERT INTO scr_country VALUES (4, localtimestamp, 'DE',  'Alemania');
INSERT INTO scr_country VALUES (5, localtimestamp, 'AD',  'Andorra');
INSERT INTO scr_country VALUES (6, localtimestamp, 'AO',  'Angola');
INSERT INTO scr_country VALUES (7, localtimestamp, 'AI',  'Anguilla');
INSERT INTO scr_country VALUES (8, localtimestamp, 'AQ',  'Antártida');
INSERT INTO scr_country VALUES (9, localtimestamp, 'AG',  'Antigua y Barbuda');
INSERT INTO scr_country VALUES (10,localtimestamp,  'AN', 'Antillas Holandesas');
INSERT INTO scr_country VALUES (11,localtimestamp,  'SA', 'Arabia Saudí');
INSERT INTO scr_country VALUES (12,localtimestamp,  'DZ', 'Argelia');
INSERT INTO scr_country VALUES (13,localtimestamp,  'AR', 'Argentina');
INSERT INTO scr_country VALUES (14,localtimestamp,  'AM', 'Armenia');
INSERT INTO scr_country VALUES (15,localtimestamp,  'AW', 'Aruba');
INSERT INTO scr_country VALUES (16,localtimestamp,  'AU', 'Australia');
INSERT INTO scr_country VALUES (17,localtimestamp,  'AT', 'Austria');
INSERT INTO scr_country VALUES (18,localtimestamp,  'AZ', 'Azerbaiyán');
INSERT INTO scr_country VALUES (19,localtimestamp,  'BS', 'Bahamas');
INSERT INTO scr_country VALUES (20,localtimestamp,  'BH', 'Bahréin');
INSERT INTO scr_country VALUES (21,localtimestamp,  'BD', 'Bangladesh');
INSERT INTO scr_country VALUES (22,localtimestamp,  'BB', 'Barbados');
INSERT INTO scr_country VALUES (23,localtimestamp,  'BY', 'Bielorrusia');
INSERT INTO scr_country VALUES (24,localtimestamp,  'BE', 'Bélgica');
INSERT INTO scr_country VALUES (25,localtimestamp,  'BZ', 'Belice');
INSERT INTO scr_country VALUES (26,localtimestamp,  'BJ', 'Benin');
INSERT INTO scr_country VALUES (27,localtimestamp,  'BM', 'Bermudas');
INSERT INTO scr_country VALUES (28,localtimestamp,  'BT', 'Bhután');
INSERT INTO scr_country VALUES (29,localtimestamp,  'BO', 'Bolivia');
INSERT INTO scr_country VALUES (30,localtimestamp,  'BA', 'Bosnia y Herzegovina');
INSERT INTO scr_country VALUES (31,localtimestamp,  'BW', 'Botsuana');
INSERT INTO scr_country VALUES (32,localtimestamp,  'BV', 'Isla Bouvet');
INSERT INTO scr_country VALUES (33,localtimestamp,  'BR', 'Brasil');
INSERT INTO scr_country VALUES (34,localtimestamp,  'BN', 'Brunéi');
INSERT INTO scr_country VALUES (35,localtimestamp,  'BG', 'Bulgaria');
INSERT INTO scr_country VALUES (36,localtimestamp,  'BF', 'Burkina Faso');
INSERT INTO scr_country VALUES (37,localtimestamp,  'BI', 'Burundi');
INSERT INTO scr_country VALUES (38,localtimestamp,  'CV', 'Cabo Verde');
INSERT INTO scr_country VALUES (39,localtimestamp,  'KY', 'Islas Caimán');
INSERT INTO scr_country VALUES (40,localtimestamp,  'KH', 'Camboya');
INSERT INTO scr_country VALUES (41,localtimestamp,  'CM', 'Camerún');
INSERT INTO scr_country VALUES (42,localtimestamp,  'CA', 'Canadá');
INSERT INTO scr_country VALUES (43,localtimestamp,  'CF', 'República Centroafricana');
INSERT INTO scr_country VALUES (44,localtimestamp,  'TD', 'Chad');
INSERT INTO scr_country VALUES (45,localtimestamp,  'CZ', 'República Checa');
INSERT INTO scr_country VALUES (46,localtimestamp,  'CL', 'Chile');
INSERT INTO scr_country VALUES (47,localtimestamp,  'CN', 'China');
INSERT INTO scr_country VALUES (48,localtimestamp,  'CY', 'Chipre');
INSERT INTO scr_country VALUES (49,localtimestamp,  'CX', 'Isla de Navidad');
INSERT INTO scr_country VALUES (50,localtimestamp,  'VA', 'Ciudad del Vaticano');
INSERT INTO scr_country VALUES (51,localtimestamp,  'CC', 'Islas Cocos');
INSERT INTO scr_country VALUES (52,localtimestamp,  'CO', 'Colombia');
INSERT INTO scr_country VALUES (53,localtimestamp,  'KM', 'Comoras');
INSERT INTO scr_country VALUES (54,localtimestamp,  'CD', 'República Democrática del Congo');
INSERT INTO scr_country VALUES (55,localtimestamp,  'CG', 'Congo');
INSERT INTO scr_country VALUES (56,localtimestamp,  'CK', 'Islas Cook');
INSERT INTO scr_country VALUES (57,localtimestamp,  'KP', 'Corea del Norte');
INSERT INTO scr_country VALUES (58,localtimestamp,  'KR', 'Corea del Sur');
INSERT INTO scr_country VALUES (59,localtimestamp,  'CI', 'Costa de Marfil');
INSERT INTO scr_country VALUES (60,localtimestamp,  'CR', 'Costa Rica');
INSERT INTO scr_country VALUES (61,localtimestamp,  'HR', 'Croacia');
INSERT INTO scr_country VALUES (62,localtimestamp,  'CU', 'Cuba');
INSERT INTO scr_country VALUES (63,localtimestamp,  'DK', 'Dinamarca');
INSERT INTO scr_country VALUES (64,localtimestamp,  'DM', 'Dominica');
INSERT INTO scr_country VALUES (65,localtimestamp,  'DO', 'República Dominicana');
INSERT INTO scr_country VALUES (66,localtimestamp,  'EC', 'Ecuador');
INSERT INTO scr_country VALUES (67,localtimestamp,  'EG', 'Egipto');
INSERT INTO scr_country VALUES (68,localtimestamp,  'SV', 'El Salvador');
INSERT INTO scr_country VALUES (69,localtimestamp,  'AE', 'Emiratos Árabes Unidos');
INSERT INTO scr_country VALUES (70,localtimestamp,  'ER', 'Eritrea');
INSERT INTO scr_country VALUES (71,localtimestamp,  'SK', 'Eslovaquia');
INSERT INTO scr_country VALUES (72,localtimestamp,  'SI', 'Eslovenia');
INSERT INTO scr_country VALUES (73,localtimestamp,  'ES', 'España');
INSERT INTO scr_country VALUES (74,localtimestamp,  'UM', 'Islas ultramarinas de Estados Unidos');
INSERT INTO scr_country VALUES (75,localtimestamp,  'US', 'Estados Unidos');
INSERT INTO scr_country VALUES (76,localtimestamp,  'EE', 'Estonia');
INSERT INTO scr_country VALUES (77,localtimestamp,  'ET', 'Etiopía');
INSERT INTO scr_country VALUES (78,localtimestamp,  'FO', 'Islas Feroe');
INSERT INTO scr_country VALUES (79,localtimestamp,  'PH', 'Filipinas');
INSERT INTO scr_country VALUES (80,localtimestamp,  'FI', 'Finlandia');
INSERT INTO scr_country VALUES (81,localtimestamp,  'FJ', 'Fiyi');
INSERT INTO scr_country VALUES (82,localtimestamp,  'FR', 'Francia');
INSERT INTO scr_country VALUES (83,localtimestamp,  'GA', 'Gabón');
INSERT INTO scr_country VALUES (84,localtimestamp,  'GM', 'Gambia');
INSERT INTO scr_country VALUES (85,localtimestamp,  'GE', 'Georgia');
INSERT INTO scr_country VALUES (86,localtimestamp,  'GS', 'Islas Georgias del Sur y Sandwich del Sur');
INSERT INTO scr_country VALUES (87,localtimestamp,  'GH', 'Ghana');
INSERT INTO scr_country VALUES (88,localtimestamp,  'GI', 'Gibraltar');
INSERT INTO scr_country VALUES (89,localtimestamp,  'GD', 'Granada');
INSERT INTO scr_country VALUES (90,localtimestamp,  'GR', 'Grecia');
INSERT INTO scr_country VALUES (91,localtimestamp,  'GL', 'Groenlandia');
INSERT INTO scr_country VALUES (92,localtimestamp,  'GP', 'Guadalupe');
INSERT INTO scr_country VALUES (93,localtimestamp,  'GU', 'Guam');
INSERT INTO scr_country VALUES (94,localtimestamp,  'GT', 'Guatemala');
INSERT INTO scr_country VALUES (95,localtimestamp,  'GF', 'Guayana Francesa');
INSERT INTO scr_country VALUES (96,localtimestamp,  'GN', 'Guinea');
INSERT INTO scr_country VALUES (97,localtimestamp,  'GQ', 'Guinea Ecuatorial');
INSERT INTO scr_country VALUES (98,localtimestamp,  'GW', 'Guinea-Bissau');
INSERT INTO scr_country VALUES (99,localtimestamp,  'GY', 'Guyana');
INSERT INTO scr_country VALUES (100,localtimestamp, 'HT', 'Haití');
INSERT INTO scr_country VALUES (101,localtimestamp,  'HM','Islas Heard y McDonald');
INSERT INTO scr_country VALUES (102,localtimestamp,  'HN','Honduras');
INSERT INTO scr_country VALUES (103,localtimestamp,  'HK','Hong Kong');
INSERT INTO scr_country VALUES (104,localtimestamp,  'HU','Hungría');
INSERT INTO scr_country VALUES (105,localtimestamp,  'IN','India');
INSERT INTO scr_country VALUES (106,localtimestamp,  'ID','Indonesia');
INSERT INTO scr_country VALUES (107,localtimestamp,  'IR','Irán');
INSERT INTO scr_country VALUES (108,localtimestamp,  'IQ','Iraq');
INSERT INTO scr_country VALUES (109,localtimestamp,  'IE','Irlanda');
INSERT INTO scr_country VALUES (110,localtimestamp,  'IS','Islandia');
INSERT INTO scr_country VALUES (111,localtimestamp,  'IL','Israel');
INSERT INTO scr_country VALUES (112,localtimestamp,  'IT','Italia');
INSERT INTO scr_country VALUES (113,localtimestamp,  'JM','Jamaica');
INSERT INTO scr_country VALUES (114,localtimestamp,  'JP','Japón');
INSERT INTO scr_country VALUES (115,localtimestamp,  'JO','Jordania');
INSERT INTO scr_country VALUES (116,localtimestamp,  'KZ','Kazajstán');
INSERT INTO scr_country VALUES (117,localtimestamp,  'KE','Kenia');
INSERT INTO scr_country VALUES (118,localtimestamp,  'KG','Kirguistán');
INSERT INTO scr_country VALUES (119,localtimestamp,  'KI','Kiribati');
INSERT INTO scr_country VALUES (120,localtimestamp,  'KW','Kuwait');
INSERT INTO scr_country VALUES (121,localtimestamp,  'LA','Laos');
INSERT INTO scr_country VALUES (122,localtimestamp,  'LS','Lesotho');
INSERT INTO scr_country VALUES (123,localtimestamp,  'LV','Letonia');
INSERT INTO scr_country VALUES (124,localtimestamp,  'LB','Líbano');
INSERT INTO scr_country VALUES (125,localtimestamp,  'LR','Liberia');
INSERT INTO scr_country VALUES (126,localtimestamp,  'LY','Libia');
INSERT INTO scr_country VALUES (127,localtimestamp,  'LI','Liechtenstein');
INSERT INTO scr_country VALUES (128,localtimestamp,  'LT','Lituania');
INSERT INTO scr_country VALUES (129,localtimestamp,  'LU','Luxemburgo');
INSERT INTO scr_country VALUES (130,localtimestamp,  'MO','Macao');
INSERT INTO scr_country VALUES (131,localtimestamp,  'MK','ARY Macedonia');
INSERT INTO scr_country VALUES (132,localtimestamp,  'MG','Madagascar');
INSERT INTO scr_country VALUES (133,localtimestamp,  'MY','Malasia');
INSERT INTO scr_country VALUES (134,localtimestamp,  'MW','Malawi');
INSERT INTO scr_country VALUES (135,localtimestamp,  'MV','Maldivas');
INSERT INTO scr_country VALUES (136,localtimestamp,  'ML','Malí');
INSERT INTO scr_country VALUES (137,localtimestamp,  'MT','Malta');
INSERT INTO scr_country VALUES (138,localtimestamp,  'FK','Islas Malvinas');
INSERT INTO scr_country VALUES (139,localtimestamp,  'MP','Islas Marianas del Norte');
INSERT INTO scr_country VALUES (140,localtimestamp,  'MA','Marruecos');
INSERT INTO scr_country VALUES (141,localtimestamp,  'MH','Islas Marshall');
INSERT INTO scr_country VALUES (142,localtimestamp,  'MQ','Martinica');
INSERT INTO scr_country VALUES (143,localtimestamp,  'MU','Mauricio');
INSERT INTO scr_country VALUES (144,localtimestamp,  'MR','Mauritania');
INSERT INTO scr_country VALUES (145,localtimestamp,  'YT','Mayotte');
INSERT INTO scr_country VALUES (146,localtimestamp,  'MX','México');
INSERT INTO scr_country VALUES (147,localtimestamp,  'FM','Micronesia');
INSERT INTO scr_country VALUES (148,localtimestamp,  'MD','Moldavia');
INSERT INTO scr_country VALUES (149,localtimestamp,  'MC','Mónaco');
INSERT INTO scr_country VALUES (150,localtimestamp,  'MN','Mongolia');
INSERT INTO scr_country VALUES (151,localtimestamp,  'MS','Montserrat');
INSERT INTO scr_country VALUES (152,localtimestamp,  'MZ','Mozambique');
INSERT INTO scr_country VALUES (153,localtimestamp,  'MM','Myanmar');
INSERT INTO scr_country VALUES (154,localtimestamp,  'NA','Namibia');
INSERT INTO scr_country VALUES (155,localtimestamp,  'NR','Nauru');
INSERT INTO scr_country VALUES (156,localtimestamp,  'NP','Nepal');
INSERT INTO scr_country VALUES (157,localtimestamp,  'NI','Nicaragua');
INSERT INTO scr_country VALUES (158,localtimestamp,  'NE','Níger');
INSERT INTO scr_country VALUES (159,localtimestamp,  'NG','Nigeria');
INSERT INTO scr_country VALUES (160,localtimestamp,  'NU','Niue');
INSERT INTO scr_country VALUES (161,localtimestamp,  'NF','Isla Norfolk');
INSERT INTO scr_country VALUES (162,localtimestamp,  'NO','Noruega');
INSERT INTO scr_country VALUES (163,localtimestamp,  'NC','Nueva Caledonia');
INSERT INTO scr_country VALUES (164,localtimestamp,  'NZ','Nueva Zelanda');
INSERT INTO scr_country VALUES (165,localtimestamp,  'OM','Omán');
INSERT INTO scr_country VALUES (166,localtimestamp,  'NL','Países Bajos');
INSERT INTO scr_country VALUES (167,localtimestamp,  'PK','Pakistán');
INSERT INTO scr_country VALUES (168,localtimestamp,  'PW','Palau');
INSERT INTO scr_country VALUES (169,localtimestamp,  'PS','Palestina');
INSERT INTO scr_country VALUES (170,localtimestamp,  'PA','Panamá');
INSERT INTO scr_country VALUES (171,localtimestamp,  'PG','Papúa Nueva Guinea');
INSERT INTO scr_country VALUES (172,localtimestamp,  'PY','Paraguay');
INSERT INTO scr_country VALUES (173,localtimestamp,  'PE','Perú');
INSERT INTO scr_country VALUES (174,localtimestamp,  'PN','Islas Pitcairn');
INSERT INTO scr_country VALUES (175,localtimestamp,  'PF','Polinesia Francesa');
INSERT INTO scr_country VALUES (176,localtimestamp,  'PL','Polonia');
INSERT INTO scr_country VALUES (177,localtimestamp,  'PT','Portugal');
INSERT INTO scr_country VALUES (178,localtimestamp,  'PR','Puerto Rico');
INSERT INTO scr_country VALUES (179,localtimestamp,  'QA','Qatar');
INSERT INTO scr_country VALUES (180,localtimestamp,  'GB','Reino Unido');
INSERT INTO scr_country VALUES (181,localtimestamp,  'RE','Reunión');
INSERT INTO scr_country VALUES (182,localtimestamp,  'RW','Ruanda');
INSERT INTO scr_country VALUES (183,localtimestamp,  'RO','Rumania');
INSERT INTO scr_country VALUES (184,localtimestamp,  'RU','Rusia');
INSERT INTO scr_country VALUES (185,localtimestamp,  'EH','Sahara Occidental');
INSERT INTO scr_country VALUES (186,localtimestamp,  'SB','Islas Salomón');
INSERT INTO scr_country VALUES (187,localtimestamp,  'WS','Samoa');
INSERT INTO scr_country VALUES (188,localtimestamp,  'AS','Samoa Americana');
INSERT INTO scr_country VALUES (189,localtimestamp,  'KN','San Cristóbal y Nevis');
INSERT INTO scr_country VALUES (190,localtimestamp,  'SM','San Marino');
INSERT INTO scr_country VALUES (191,localtimestamp,  'PM','San Pedro y Miquelón');
INSERT INTO scr_country VALUES (192,localtimestamp,  'VC','San Vicente y las Granadinas');
INSERT INTO scr_country VALUES (193,localtimestamp,  'SH','Santa Helena');
INSERT INTO scr_country VALUES (194,localtimestamp,  'LC','Santa Lucía');
INSERT INTO scr_country VALUES (195,localtimestamp,  'ST','Santo Tomé y Príncipe');
INSERT INTO scr_country VALUES (196,localtimestamp,  'SN','Senegal');
INSERT INTO scr_country VALUES (197,localtimestamp,  'CS','Serbia y Montenegro');
INSERT INTO scr_country VALUES (198,localtimestamp,  'SC','Seychelles');
INSERT INTO scr_country VALUES (199,localtimestamp,  'SL','Sierra Leona');
INSERT INTO scr_country VALUES (200,localtimestamp,  'SG','Singapur');
INSERT INTO scr_country VALUES (201,localtimestamp,  'SY','Siria');
INSERT INTO scr_country VALUES (202,localtimestamp,  'SO','Somalia');
INSERT INTO scr_country VALUES (203,localtimestamp,  'LK','Sri Lanka');
INSERT INTO scr_country VALUES (204,localtimestamp,  'SZ','Suazilandia');
INSERT INTO scr_country VALUES (205,localtimestamp,  'ZA','Sudáfrica');
INSERT INTO scr_country VALUES (206,localtimestamp,  'SD','Sudán');
INSERT INTO scr_country VALUES (207,localtimestamp,  'SE','Suecia');
INSERT INTO scr_country VALUES (208,localtimestamp,  'CH','Suiza');
INSERT INTO scr_country VALUES (209,localtimestamp,  'SR','Surinam');
INSERT INTO scr_country VALUES (210,localtimestamp,  'SJ','Svalbard y Jan Mayen');
INSERT INTO scr_country VALUES (211,localtimestamp,  'TH','Tailandia');
INSERT INTO scr_country VALUES (212,localtimestamp,  'TW','Taiwán');
INSERT INTO scr_country VALUES (213,localtimestamp,  'TZ','Tanzania');
INSERT INTO scr_country VALUES (214,localtimestamp,  'TJ','Tayikistán');
INSERT INTO scr_country VALUES (215,localtimestamp,  'IO','Territorio Británico del Océano Índico');
INSERT INTO scr_country VALUES (216,localtimestamp,  'TF','Territorios Australes Franceses');
INSERT INTO scr_country VALUES (217,localtimestamp,  'TL','Timor Oriental');
INSERT INTO scr_country VALUES (218,localtimestamp,  'TG','Togo');
INSERT INTO scr_country VALUES (219,localtimestamp,  'TK','Tokelau');
INSERT INTO scr_country VALUES (220,localtimestamp,  'TO','Tonga');
INSERT INTO scr_country VALUES (221,localtimestamp,  'TT','Trinidad y Tobago');
INSERT INTO scr_country VALUES (222,localtimestamp,  'TN','Túnez');
INSERT INTO scr_country VALUES (223,localtimestamp,  'TC','Islas Turcas y Caicos');
INSERT INTO scr_country VALUES (224,localtimestamp,  'TM','Turkmenistán');
INSERT INTO scr_country VALUES (225,localtimestamp,  'TR','Turquía');
INSERT INTO scr_country VALUES (226,localtimestamp,  'TV','Tuvalu');
INSERT INTO scr_country VALUES (227,localtimestamp,  'UA','Ucrania');
INSERT INTO scr_country VALUES (228,localtimestamp,  'UG','Uganda');
INSERT INTO scr_country VALUES (229,localtimestamp,  'UY','Uruguay');
INSERT INTO scr_country VALUES (230,localtimestamp,  'UZ','Uzbekistán');
INSERT INTO scr_country VALUES (231,localtimestamp,  'VU','Vanuatu');
INSERT INTO scr_country VALUES (232,localtimestamp,  'VE','Venezuela');
INSERT INTO scr_country VALUES (233,localtimestamp,  'VN','Vietnam');
INSERT INTO scr_country VALUES (234,localtimestamp,  'VG','Islas Vírgenes Británicas');
INSERT INTO scr_country VALUES (235,localtimestamp,  'VI','Islas Vírgenes de los Estados Unidos');
INSERT INTO scr_country VALUES (236,localtimestamp,  'WF','Wallis y Futuna');
INSERT INTO scr_country VALUES (237,localtimestamp,  'YE','Yemen');
INSERT INTO scr_country VALUES (238,localtimestamp, 'DJ','Yibuti');
INSERT INTO scr_country VALUES (239,localtimestamp,  'ZM','Zambia');
INSERT INTO scr_country VALUES (240,localtimestamp,  'ZW','Zimbabue');

--TABLA SCR_PAGETYPE
INSERT INTO scr_pagetype VALUES(0,'Sin tipo');
INSERT INTO scr_pagetype VALUES(1,'Documento a compulsar');
INSERT INTO scr_pagetype VALUES(2,'Firma documento a compulsar');
INSERT INTO scr_pagetype VALUES(3,'Documento localizador de fichero a compulsar');
