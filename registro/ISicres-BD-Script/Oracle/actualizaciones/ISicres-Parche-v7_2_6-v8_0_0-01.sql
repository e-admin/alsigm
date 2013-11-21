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
CREATE UNIQUE INDEX scr_userfilter2 ON scr_userfilter (idarch, iduser, type_obj);

--
-- SCR_ATTACHMENT_SEQ
--

CREATE SEQUENCE SCR_ATTACHMENT_SEQ
 MINVALUE 1
 MAXVALUE 9223372036854775807
 INCREMENT BY 1
 START WITH 1
 ;


--
-- SCR_ATTACHMENT_SIGN_INFO_SEQ
--

CREATE SEQUENCE SCR_ATTACHMENT_SIGN_INFO_SEQ
 MINVALUE 1
 MAXVALUE 9223372036854775807
 INCREMENT BY 1
 START WITH 1
 ;


--
-- SCR_ENTITYREG_ID_SEQ
--

CREATE SEQUENCE SCR_ENTITYREG_ID_SEQ
 MINVALUE 1
 MAXVALUE 9223372036854775807
 INCREMENT BY 1
 START WITH 1
 ;


--
-- SCR_EXREGSTATE_ID_SEQ
--

CREATE SEQUENCE SCR_EXREGSTATE_ID_SEQ
 MINVALUE 1
 MAXVALUE 9223372036854775807
 INCREMENT BY 1
 START WITH 1
 ;


--
-- SCR_EXREG_ID_SEQ
--

CREATE SEQUENCE SCR_EXREG_ID_SEQ
 MINVALUE 1
 MAXVALUE 9223372036854775807
 INCREMENT BY 1
 START WITH 1
 ;


--
-- SCR_EXREG_IN_ID_SEQ
--

CREATE SEQUENCE SCR_EXREG_IN_ID_SEQ
 MINVALUE 1
 MAXVALUE 9223372036854775807
 INCREMENT BY 1
 START WITH 1
 ;


--
-- SCR_TRAMUNIT_ID_SEQ
--

CREATE SEQUENCE SCR_TRAMUNIT_ID_SEQ
 MINVALUE 1
 MAXVALUE 9223372036854775807
 INCREMENT BY 1
 START WITH 1
 ;



--
-- SCR_ATTACHMENT
--

CREATE TABLE SCR_ATTACHMENT (ID            NUMBER(10,0) NOT NULL,
                             BOOKID        NUMBER(10,0) NOT NULL,
                             FOLDERID      NUMBER(10,0) NOT NULL,
                             PAGEID        NUMBER(10,0) NOT NULL,
                             COMMENTS      VARCHAR2(50 CHAR) NULL,
                             MIME_TYPE     VARCHAR2(20 CHAR) NULL,
                             NAME          VARCHAR2(80 CHAR) NULL,
                             CODE_NAME     VARCHAR2(50 CHAR) NULL,
                             VALIDITY_TYPE NUMBER(10,0) NULL,
                             DOCUMENT_TYPE NUMBER(10,0) NULL)
 ;
CREATE UNIQUE INDEX PK_SCR_ATTACHMENT ON SCR_ATTACHMENT (ID ASC);

ALTER TABLE SCR_ATTACHMENT ADD CONSTRAINT PK_SCR_ATTACHMENT PRIMARY KEY (ID);


--
-- SCR_ATTACHMENT_DOCUMENT_TYPE
--

CREATE TABLE SCR_ATTACHMENT_DOCUMENT_TYPE (ID   NUMBER(10,0) NOT NULL,
                                           NAME VARCHAR2(250 CHAR) NULL,
                                           CODE VARCHAR2(2 CHAR) NULL);

CREATE UNIQUE INDEX PK_ATTACHMENT_DOCUMENT_TYPE ON SCR_ATTACHMENT_DOCUMENT_TYPE (ID ASC);

ALTER TABLE SCR_ATTACHMENT_DOCUMENT_TYPE ADD CONSTRAINT PK_ATTACHMENT_DOCUMENT_TYPE PRIMARY KEY (ID);

--
-- SCR_ATTACHMENT_SIGN_INFO
--

CREATE TABLE SCR_ATTACHMENT_SIGN_INFO (ID                   NUMBER(10,0) NOT NULL,
                                       ID_ATTACHMENT        NUMBER(10,0) NOT NULL UNIQUE,
                                       ID_ATTACHMENT_SIGNED NUMBER(10,0) NULL,
                                       CERT                 BLOB NULL,
                                       SIGNATURE            BLOB NULL,
                                       SIGNATURE_ALG        VARCHAR2(2 CHAR) NULL,
                                       SIGNATURE_FORMAT     VARCHAR2(2 CHAR) NULL,
                                       TIME_SIGNATURE       BLOB NULL,
                                       OCSP_VALIDATION      BLOB NULL,
                                       HASH_ALG             VARCHAR2(2 CHAR) NULL,
                                       HASH                 BLOB NULL);


CREATE UNIQUE INDEX PK_ATTACHMENT_SIGN_INFO ON SCR_ATTACHMENT_SIGN_INFO (ID ASC);

ALTER TABLE SCR_ATTACHMENT_SIGN_INFO ADD CONSTRAINT PK_ATTACHMENT_SIGN_INFO PRIMARY KEY (ID);

ALTER TABLE SCR_ATTACHMENT_SIGN_INFO ADD CONSTRAINT SYS_C00196999 UNIQUE (ID_ATTACHMENT);

--
-- SCR_ATTACHMENT_VALIDITY_TYPE
--

CREATE TABLE SCR_ATTACHMENT_VALIDITY_TYPE (ID   NUMBER(10,0) NOT NULL,
                                           NAME VARCHAR2(250 CHAR) NULL,
                                           CODE VARCHAR2(2 CHAR) NULL);

CREATE UNIQUE INDEX PK_ATTACHMENT_VALIDITY_TYPE ON SCR_ATTACHMENT_VALIDITY_TYPE (ID ASC);

ALTER TABLE SCR_ATTACHMENT_VALIDITY_TYPE ADD CONSTRAINT PK_ATTACHMENT_VALIDITY_TYPE PRIMARY KEY (ID);

--
-- SCR_BOOKSLANG
--

ALTER TABLE SCR_BOOKSLANG MODIFY NAME VARCHAR2(250 CHAR);

--
-- SCR_CITIES
--

ALTER TABLE SCR_CITIES ADD CONSTRAINT PK_CITIES PRIMARY KEY (ID);

--
-- SCR_CITIESEXREG
--

CREATE TABLE SCR_CITIESEXREG (ID      NUMBER(10,0) NOT NULL,
                              CODE    VARCHAR2(5 CHAR) NOT NULL,
                              NAME    VARCHAR2(80 CHAR) NOT NULL,
                              ID_CITY NUMBER(10,0) NOT NULL);

CREATE UNIQUE INDEX PK_SCR_CITIESEXREG ON SCR_CITIESEXREG (ID ASC);

ALTER TABLE SCR_CITIESEXREG ADD CONSTRAINT PK_SCR_CITIESEXREG PRIMARY KEY (ID);

--
-- SCR_COUNTRY
--

CREATE TABLE SCR_COUNTRY (ID      NUMBER(10,0) NOT NULL,
                          TMSTAMP DATE NOT NULL,
                          CODE    VARCHAR2(16 CHAR) NOT NULL,
                          NAME    VARCHAR2(50 CHAR) NOT NULL);

--
-- SCR_DOM
--

ALTER TABLE SCR_DOM MODIFY ADDRESS VARCHAR2(160 CHAR);
ALTER TABLE SCR_DOM ADD PAIS VARCHAR2(50 CHAR);


--
-- SCR_ENTITYREG
--

CREATE TABLE SCR_ENTITYREG (ID      NUMBER(10,0) NOT NULL,
                            CODE    VARCHAR2(21 CHAR) NOT NULL,
                            NAME    VARCHAR2(80 CHAR) NOT NULL,
                            ID_OFIC NUMBER(10,0) NOT NULL);

CREATE UNIQUE INDEX PK_SCR_ENTITYREG ON SCR_ENTITYREG (ID ASC);

ALTER TABLE SCR_ENTITYREG ADD CONSTRAINT PK_SCR_ENTITYREG PRIMARY KEY (ID);


--
-- SCR_EXREG
--

CREATE TABLE SCR_EXREG (ID              NUMBER(10,0) NOT NULL,
                        ID_ARCH         NUMBER(10,0) NOT NULL,
                        ID_FDR          NUMBER(10,0) NOT NULL,
                        ID_OFIC         NUMBER(10,0) NOT NULL,
                        TYPE_ORIG       NUMBER(10,0) NOT NULL,
                        EXCHANGE_DATE   DATE NULL,
                        STATE           NUMBER(10,0) NOT NULL,
                        STATE_DATE      DATE NOT NULL,
                        ID_EXCHANGE_SIR VARCHAR2(33 CHAR) NULL,
                        ID_EXCHANGE     NUMBER(10,0) NOT NULL,
                        USERNAME        VARCHAR2(32 CHAR) NOT NULL,
                        CODE_TRAMUNIT   VARCHAR2(21 CHAR) NULL,
                        NAME_TRAMUNIT   VARCHAR2(80 CHAR) NULL,
                        CODE_ENTITY     VARCHAR2(21 CHAR) NOT NULL,
                        NAME_ENTITY     VARCHAR2(80 CHAR) NOT NULL,
                        COMMENTS        VARCHAR2(4000 CHAR) NULL);

CREATE UNIQUE INDEX PK_SCR_EXREG ON SCR_EXREG (ID ASC);

ALTER TABLE SCR_EXREG ADD CONSTRAINT PK_SCR_EXREG PRIMARY KEY (ID);


--
-- SCR_EXREGSTATE
--

CREATE TABLE SCR_EXREGSTATE (ID         NUMBER(10,0) NOT NULL,
                             ID_EXREG   NUMBER(10,0) NOT NULL,
                             STATE      NUMBER(10,0) NOT NULL,
                             STATE_DATE DATE NOT NULL,
                             USERNAME   VARCHAR2(32 CHAR) NOT NULL,
                             COMMENTS   VARCHAR2(4000 CHAR) NULL)
                             ;
CREATE UNIQUE INDEX PK_SCR_EXREGSTATE ON SCR_EXREGSTATE (ID ASC);

ALTER TABLE SCR_EXREGSTATE ADD CONSTRAINT PK_SCR_EXREGSTATE PRIMARY KEY (ID);



--
-- SCR_EXREG_IN
--

CREATE TABLE SCR_EXREG_IN (ID              NUMBER(10,0) NOT NULL,
                           ID_ARCH         NUMBER(10,0) NULL,
                           ID_FDR          NUMBER(10,0) NULL,
                           ID_OFIC         NUMBER(10,0) NOT NULL,
                           EXCHANGE_DATE   DATE NOT NULL,
                           STATE           NUMBER(10,0) NOT NULL,
                           STATE_DATE      DATE NOT NULL,
                           ID_EXCHANGE_SIR VARCHAR2(33 CHAR) NOT NULL,
                           ID_EXCHANGE     NUMBER(10,0) NOT NULL,
                           USERNAME        VARCHAR2(32 CHAR) NOT NULL,
                           CODE_TRAMUNIT   VARCHAR2(21 CHAR) NULL,
                           NAME_TRAMUNIT   VARCHAR2(80 CHAR) NULL,
                           CODE_ENTITY     VARCHAR2(21 CHAR) NOT NULL,
                           NAME_ENTITY     VARCHAR2(80 CHAR) NOT NULL,
                           NUM_REG_ORIG    VARCHAR2(20 CHAR) NOT NULL,
                           CONTACTO_ORIG   VARCHAR2(160 CHAR) NULL,
                           COMMENTS        VARCHAR2(4000 CHAR) NULL);

CREATE UNIQUE INDEX PK_SCR_EXREG_IN ON SCR_EXREG_IN (ID ASC);

ALTER TABLE SCR_EXREG_IN ADD CONSTRAINT PK_SCR_EXREG_IN PRIMARY KEY (ID);
--
-- SCR_PAGEREPOSITORY
--

ALTER TABLE SCR_PAGEREPOSITORY ADD ID_PAGETYPE NUMBER DEFAULT 0 NOT NULL;


--
-- SCR_PAGETYPE
--

CREATE TABLE SCR_PAGETYPE (ID   NUMBER NOT NULL,
                           NAME VARCHAR2(100 CHAR) NOT NULL);

CREATE UNIQUE INDEX PK_PAGETYPE ON SCR_PAGETYPE (ID ASC);

ALTER TABLE SCR_PAGETYPE ADD CONSTRAINT PK_PAGETYPE PRIMARY KEY (ID);

--
-- SCR_PFIS
--

ALTER TABLE SCR_PFIS MODIFY FIRST_NAME VARCHAR2(30 CHAR);
ALTER TABLE SCR_PFIS MODIFY SECOND_NAME VARCHAR2(30 CHAR);
ALTER TABLE SCR_PFIS MODIFY SURNAME VARCHAR2(30 CHAR);

--
-- SCR_PJUR
--

ALTER TABLE SCR_PJUR MODIFY NAME VARCHAR2(80 CHAR);

--
-- SCR_REGINT
--

ALTER TABLE SCR_REGINT MODIFY NAME VARCHAR2(95 CHAR);

--
-- SCR_PROV
--
ALTER TABLE SCR_PROV ADD CONSTRAINT PK_PROV PRIMARY KEY (ID);

--
-- SCR_PROVEXREG
--
CREATE TABLE SCR_PROVEXREG (ID      NUMBER(10,0) NOT NULL,
                            CODE    VARCHAR2(2 CHAR) NOT NULL,
                            NAME    VARCHAR2(80 CHAR) NOT NULL,
                            ID_PROV NUMBER(10,0) NOT NULL);

CREATE UNIQUE INDEX PK_SCR_PROVEXREG ON SCR_PROVEXREG (ID ASC);

ALTER TABLE SCR_PROVEXREG ADD CONSTRAINT PK_SCR_PROVEXREG PRIMARY KEY (ID);


--
-- SCR_REPRE
--

CREATE TABLE SCR_REPRE (ID               NUMBER(10,0) NOT NULL,
                        ID_REPRESENTANTE NUMBER(10,0) NOT NULL,
                        ID_REPRESENTADO  NUMBER(10,0) NOT NULL,
                        ID_ADDRESS       NUMBER(10,0) NULL,
                        NAME             VARCHAR2(95 CHAR) NOT NULL);


--
-- SCR_TRAMUNIT
--

CREATE TABLE SCR_TRAMUNIT (ID            NUMBER(10,0) NOT NULL,
                           CODE_TRAMUNIT VARCHAR2(21 CHAR) NULL,
                           NAME_TRAMUNIT VARCHAR2(80 CHAR) NULL,
                           CODE_ENTITY   VARCHAR2(21 CHAR) NULL,
                           NAME_ENTITY   VARCHAR2(80 CHAR) NULL,
                           ID_ORGS       NUMBER(10,0) NOT NULL);

CREATE UNIQUE INDEX PK_SCR_TRAMUNIT ON SCR_TRAMUNIT (ID ASC);

CREATE UNIQUE INDEX U_CODE_TRAMUNIT ON SCR_TRAMUNIT (CODE_TRAMUNIT ASC);

ALTER TABLE SCR_TRAMUNIT ADD CONSTRAINT U_CODE_TRAMUNIT UNIQUE (CODE_TRAMUNIT);

ALTER TABLE SCR_TRAMUNIT ADD CONSTRAINT PK_SCR_TRAMUNIT PRIMARY KEY (ID);


--
-- SCR_USERFILTER
--

DROP INDEX SCR_USERFILTER2;
CREATE UNIQUE INDEX SCR_USERFILTER2 ON SCR_USERFILTER (IDARCH ASC, IDUSER ASC, TYPE_OBJ ASC);

--
-- FOREIGN KEYS [CREATE]
--

ALTER TABLE SCR_ATTACHMENT ADD CONSTRAINT FK_DOCUMENT_TYPE_ATTACHMENT FOREIGN KEY (DOCUMENT_TYPE) REFERENCES SCR_ATTACHMENT_DOCUMENT_TYPE (ID);
ALTER TABLE SCR_ATTACHMENT ADD CONSTRAINT FK_VALIDITY_TYPE_ATTACHMENT FOREIGN KEY (VALIDITY_TYPE) REFERENCES SCR_ATTACHMENT_VALIDITY_TYPE (ID);
ALTER TABLE SCR_ATTACHMENT_SIGN_INFO ADD CONSTRAINT FK_SIGN_INFO_ATTACHMENT_SIGNED FOREIGN KEY (ID_ATTACHMENT_SIGNED) REFERENCES SCR_ATTACHMENT (ID);
ALTER TABLE SCR_ATTACHMENT_SIGN_INFO ADD CONSTRAINT FK_SIGN_INFO_ATTACHMENT FOREIGN KEY (ID_ATTACHMENT) REFERENCES SCR_ATTACHMENT (ID);
ALTER TABLE SCR_CITIESEXREG ADD CONSTRAINT FK_CITIESEXREGCITIES FOREIGN KEY (ID_CITY) REFERENCES SCR_CITIES (ID);
ALTER TABLE SCR_ENTITYREG ADD CONSTRAINT FK_ENTITYOFIC FOREIGN KEY (ID_OFIC) REFERENCES SCR_OFIC (ID);
ALTER TABLE SCR_PAGEREPOSITORY ADD CONSTRAINT FK_PAGEREPOSITORY0 FOREIGN KEY (ID_PAGETYPE) REFERENCES SCR_PAGETYPE (ID);
ALTER TABLE SCR_PROVEXREG ADD CONSTRAINT FK_PROVEXREGPROV FOREIGN KEY (ID_PROV) REFERENCES SCR_PROV (ID);
ALTER TABLE SCR_TRAMUNIT ADD CONSTRAINT FK_TRAMUNITGORGS FOREIGN KEY (ID_ORGS) REFERENCES SCR_ORGS (ID);

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
