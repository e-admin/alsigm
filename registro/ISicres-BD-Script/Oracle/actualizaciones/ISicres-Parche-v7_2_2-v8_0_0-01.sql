ALTER TABLE scr_entityreg DROP CONSTRAINT fk_entityregorgs;
ALTER TABLE scr_tramunit DROP CONSTRAINT fk_tramentityreg;
ALTER TABLE scr_tramunit DROP CONSTRAINT fk_tramunitgorgs;
DROP TABLE scr_entityreg;
DROP TABLE scr_exreg;
DROP TABLE scr_exregaccept;
DROP TABLE scr_tramunit;


----------------
--
-- scr_attachment_seq
--
-- secuencia para el identificador de las entidades registral
CREATE SEQUENCE scr_attachment_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;

--
-- scr_attachment_sign_info_seq
--
CREATE SEQUENCE scr_attachment_sign_info_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;


--
-- scr_exregstate_id_seq
--
CREATE SEQUENCE scr_exregstate_id_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;
--
-- scr_attachment
--
CREATE TABLE scr_attachment (id            NUMBER(10) NOT NULL,
                             bookid        NUMBER(10) NOT NULL,
                             folderid      NUMBER(10) NOT NULL,
                             pageid        NUMBER(10) NOT NULL,
                             comments       varchar2(50 CHAR) NULL,
                             mime_type     varchar2(20 CHAR) NULL,
                             name          varchar2(80 CHAR) NULL,
                             code_name     varchar2(50 CHAR) NULL,
                             validity_type NUMBER(10) NULL,
                             document_type NUMBER(10) NULL);
ALTER TABLE scr_attachment ADD CONSTRAINT pk_scr_attachment PRIMARY KEY (id);


--
-- scr_attachment_document_type
--

CREATE TABLE scr_attachment_document_type (id   NUMBER(10) NOT NULL,
                                           name varchar2(250 CHAR) NULL,
                                           code varchar2(2 CHAR) NULL);
ALTER TABLE scr_attachment_document_type ADD CONSTRAINT pk_attachment_document_type PRIMARY KEY (id);


--
-- scr_attachment_sign_info
--

CREATE TABLE scr_attachment_sign_info (id                   NUMBER(10) NOT NULL,
                                       id_attachment        NUMBER(10) NOT NULL,
                                       id_attachment_signed NUMBER(10) NULL,
                                       cert                 blob NULL,
                                       signature            blob NULL,
                                       signature_alg        varchar(2) NULL,
                                       signature_format     varchar(2) NULL,
                                       time_signature       blob NULL,
                                       ocsp_validation      blob NULL,
                                       hash_alg             varchar(2) NULL,
                                       hash                 blob NULL);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT pk_attachment_sign_info PRIMARY KEY (id);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_attachment_sign_info_id UNIQUE (id_attachment);

--
-- scr_attachment_validity_type
--

CREATE TABLE scr_attachment_validity_type (id   NUMBER(10) NOT NULL,
                                           name varchar2(250 CHAR) NULL,
                                           code varchar2(2 CHAR) NULL);
ALTER TABLE scr_attachment_validity_type ADD CONSTRAINT pk_attachment_validity_type PRIMARY KEY (id);


--
-- scr_entityreg
--

CREATE TABLE scr_entityreg (id      NUMBER(10) NOT NULL,
                            code    varchar2(21 CHAR) NOT NULL,
                            name    varchar2(80 CHAR) NOT NULL,
                            id_ofic NUMBER(10) NOT NULL);
ALTER TABLE scr_entityreg ADD CONSTRAINT pk_scr_entityreg PRIMARY KEY (id);


--
-- scr_exreg
--

CREATE TABLE scr_exreg(
    id NUMBER(10) NOT NULL,
    id_arch NUMBER(10) ,
    id_fdr NUMBER(10)  ,
    id_ofic NUMBER(10)  NOT NULL,
    type_orig NUMBER(10) NOT NULL,
    exchange_date DATE,
    state NUMBER(10) NOT NULL,
    state_date DATE NOT NULL,
    id_exchange_sir VARCHAR2(33 CHAR),
    id_exchange NUMBER(10) NOT NULL,
    username VARCHAR2(32 CHAR) NOT NULL,
    code_tramunit VARCHAR2(21 CHAR) ,
    name_tramunit VARCHAR2(80 CHAR) ,
    code_entity VARCHAR2(21 CHAR) NOT NULL,
    name_entity VARCHAR2(80 CHAR) NOT NULL,
    comments VARCHAR2(4000 CHAR)

)
;
ALTER TABLE scr_exreg
ADD CONSTRAINT pk_scr_exreg PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales
CREATE SEQUENCE scr_exreg_id_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;


--
-- scr_exreg_in
--

--tabla para almacenar los registros que se nos han llegado mediante intercambio registral
-- el estado podra ser aceptado, rechazado
CREATE TABLE scr_exreg_in(
    id NUMBER(10) NOT NULL,
    id_arch NUMBER(10)  NOT NULL,
    id_fdr NUMBER(10)  NOT NULL,
    id_ofic NUMBER(10) NOT NULL,
    exchange_date DATE NOT NULL,
    state NUMBER(10) NOT NULL,
    state_date DATE NOT NULL,
    id_exchange_sir VARCHAR2(33 CHAR) NOT NULL,
    id_exchange NUMBER(10) NOT NULL,
    username VARCHAR2(32 CHAR) NOT NULL,
    code_tramunit VARCHAR2(21 CHAR) ,
    name_tramunit VARCHAR2(80 CHAR) ,
    code_entity VARCHAR2(21 CHAR) NOT NULL,
    name_entity VARCHAR2(80 CHAR) NOT NULL,
    num_reg_orig VARCHAR2(20 CHAR) NOT NULL,
    contacto_orig VARCHAR2(160 CHAR),
    comments VARCHAR2(4000 CHAR)

)
;
ALTER TABLE scr_exreg_in
ADD CONSTRAINT pk_scr_exreg_in PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales aceptados
CREATE SEQUENCE scr_exreg_in_id_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;



--
-- scr_exregstate
--

--tabla para almacenar el historial de los intercambios de salida
CREATE TABLE scr_exregstate (
    id NUMBER(10) NOT NULL,
    id_exreg NUMBER(10) NOT NULL,
    state NUMBER(10) NOT NULL,
    state_date DATE NOT NULL,
    username VARCHAR2(32 CHAR) NOT NULL,
    comments VARCHAR2(4000 CHAR)
)
;
ALTER TABLE scr_exregstate
ADD CONSTRAINT pk_scr_exregstate PRIMARY KEY (id)
;

--
-- scr_tramunit
--

CREATE TABLE scr_tramunit (id            NUMBER(10) NOT NULL,
                           code_tramunit varchar2(21 CHAR) NULL,
                           name_tramunit varchar2(80 CHAR) NULL,
                           code_entity   varchar2(21 CHAR) NULL,
                           name_entity   varchar2(80 CHAR) NULL,
                           id_orgs       NUMBER(10) NOT NULL);
ALTER TABLE scr_tramunit ADD CONSTRAINT pk_scr_tramunit PRIMARY KEY (id);
ALTER TABLE scr_tramunit ADD CONSTRAINT u_code_tramunit UNIQUE (code_tramunit);


--
-- FOREIGN KEYS [CREATE]
--

ALTER TABLE scr_attachment ADD CONSTRAINT fk_document_type_attachment FOREIGN KEY (document_type) REFERENCES scr_attachment_document_type (id);
ALTER TABLE scr_attachment ADD CONSTRAINT fk_validity_type_attachment FOREIGN KEY (validity_type) REFERENCES scr_attachment_validity_type (id);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_sign_info_attachment FOREIGN KEY (id_attachment) REFERENCES scr_attachment (id);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_sign_info_attachment_signed FOREIGN KEY (id_attachment_signed) REFERENCES scr_attachment (id);
ALTER TABLE scr_entityreg ADD CONSTRAINT fk_entityofic FOREIGN KEY (id_ofic) REFERENCES scr_ofic (id);
ALTER TABLE scr_tramunit ADD CONSTRAINT fk_tramunitgorgs FOREIGN KEY (id_orgs) REFERENCES scr_orgs (id);


-------Inserts data
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (1,'COPIA','01');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (2,'COPIA COMPULSADA','02');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (3,'COPIA ORIGINAL','03');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (4,'ORIGINAL','04');


INSERT INTO scr_attachment_document_type(id,name,code) VALUES (1,'FORMULARIO','01');
INSERT INTO scr_attachment_document_type(id,name,code) VALUES (2,'DOCUMENTO ADJUNTO','02');
INSERT INTO scr_attachment_document_type(id,name,code) VALUES (3,'FICHERO TECNICO','03');

-- Actualizaciones para terceros
-- las direcciones fisicas de terceros llevan el pais
ALTER TABLE scr_dom ADD pais varchar(50);

CREATE TABLE scr_country (
    id NUMBER(10) NOT NULL,
    tmstamp DATE NOT NULL,
    code varchar2(16 CHAR) NOT NULL,
    name varchar2(50 CHAR) NOT NULL
);

-- Datos para la tabla scr_country
INSERT INTO scr_country VALUES (1, sysdate, 'AF',  'Afganistán');
INSERT INTO scr_country VALUES (2, sysdate, 'AX',  'Islas Gland');
INSERT INTO scr_country VALUES (3, sysdate, 'AL',  'Albania');
INSERT INTO scr_country VALUES (4, sysdate, 'DE',  'Alemania');
INSERT INTO scr_country VALUES (5, sysdate, 'AD',  'Andorra');
INSERT INTO scr_country VALUES (6, sysdate, 'AO',  'Angola');
INSERT INTO scr_country VALUES (7, sysdate, 'AI',  'Anguilla');
INSERT INTO scr_country VALUES (8, sysdate, 'AQ',  'Antártida');
INSERT INTO scr_country VALUES (9, sysdate, 'AG',  'Antigua y Barbuda');
INSERT INTO scr_country VALUES (10,sysdate,  'AN', 'Antillas Holandesas');
INSERT INTO scr_country VALUES (11,sysdate,  'SA', 'Arabia Saudí');
INSERT INTO scr_country VALUES (12,sysdate,  'DZ', 'Argelia');
INSERT INTO scr_country VALUES (13,sysdate,  'AR', 'Argentina');
INSERT INTO scr_country VALUES (14,sysdate,  'AM', 'Armenia');
INSERT INTO scr_country VALUES (15,sysdate,  'AW', 'Aruba');
INSERT INTO scr_country VALUES (16,sysdate,  'AU', 'Australia');
INSERT INTO scr_country VALUES (17,sysdate,  'AT', 'Austria');
INSERT INTO scr_country VALUES (18,sysdate,  'AZ', 'Azerbaiyán');
INSERT INTO scr_country VALUES (19,sysdate,  'BS', 'Bahamas');
INSERT INTO scr_country VALUES (20,sysdate,  'BH', 'Bahréin');
INSERT INTO scr_country VALUES (21,sysdate,  'BD', 'Bangladesh');
INSERT INTO scr_country VALUES (22,sysdate,  'BB', 'Barbados');
INSERT INTO scr_country VALUES (23,sysdate,  'BY', 'Bielorrusia');
INSERT INTO scr_country VALUES (24,sysdate,  'BE', 'Bélgica');
INSERT INTO scr_country VALUES (25,sysdate,  'BZ', 'Belice');
INSERT INTO scr_country VALUES (26,sysdate,  'BJ', 'Benin');
INSERT INTO scr_country VALUES (27,sysdate,  'BM', 'Bermudas');
INSERT INTO scr_country VALUES (28,sysdate,  'BT', 'Bhután');
INSERT INTO scr_country VALUES (29,sysdate,  'BO', 'Bolivia');
INSERT INTO scr_country VALUES (30,sysdate,  'BA', 'Bosnia y Herzegovina');
INSERT INTO scr_country VALUES (31,sysdate,  'BW', 'Botsuana');
INSERT INTO scr_country VALUES (32,sysdate,  'BV', 'Isla Bouvet');
INSERT INTO scr_country VALUES (33,sysdate,  'BR', 'Brasil');
INSERT INTO scr_country VALUES (34,sysdate,  'BN', 'Brunéi');
INSERT INTO scr_country VALUES (35,sysdate,  'BG', 'Bulgaria');
INSERT INTO scr_country VALUES (36,sysdate,  'BF', 'Burkina Faso');
INSERT INTO scr_country VALUES (37,sysdate,  'BI', 'Burundi');
INSERT INTO scr_country VALUES (38,sysdate,  'CV', 'Cabo Verde');
INSERT INTO scr_country VALUES (39,sysdate,  'KY', 'Islas Caimán');
INSERT INTO scr_country VALUES (40,sysdate,  'KH', 'Camboya');
INSERT INTO scr_country VALUES (41,sysdate,  'CM', 'Camerún');
INSERT INTO scr_country VALUES (42,sysdate,  'CA', 'Canadá');
INSERT INTO scr_country VALUES (43,sysdate,  'CF', 'República Centroafricana');
INSERT INTO scr_country VALUES (44,sysdate,  'TD', 'Chad');
INSERT INTO scr_country VALUES (45,sysdate,  'CZ', 'República Checa');
INSERT INTO scr_country VALUES (46,sysdate,  'CL', 'Chile');
INSERT INTO scr_country VALUES (47,sysdate,  'CN', 'China');
INSERT INTO scr_country VALUES (48,sysdate,  'CY', 'Chipre');
INSERT INTO scr_country VALUES (49,sysdate,  'CX', 'Isla de Navidad');
INSERT INTO scr_country VALUES (50,sysdate,  'VA', 'Ciudad del Vaticano');
INSERT INTO scr_country VALUES (51,sysdate,  'CC', 'Islas Cocos');
INSERT INTO scr_country VALUES (52,sysdate,  'CO', 'Colombia');
INSERT INTO scr_country VALUES (53,sysdate,  'KM', 'Comoras');
INSERT INTO scr_country VALUES (54,sysdate,  'CD', 'República Democrática del Congo');
INSERT INTO scr_country VALUES (55,sysdate,  'CG', 'Congo');
INSERT INTO scr_country VALUES (56,sysdate,  'CK', 'Islas Cook');
INSERT INTO scr_country VALUES (57,sysdate,  'KP', 'Corea del Norte');
INSERT INTO scr_country VALUES (58,sysdate,  'KR', 'Corea del Sur');
INSERT INTO scr_country VALUES (59,sysdate,  'CI', 'Costa de Marfil');
INSERT INTO scr_country VALUES (60,sysdate,  'CR', 'Costa Rica');
INSERT INTO scr_country VALUES (61,sysdate,  'HR', 'Croacia');
INSERT INTO scr_country VALUES (62,sysdate,  'CU', 'Cuba');
INSERT INTO scr_country VALUES (63,sysdate,  'DK', 'Dinamarca');
INSERT INTO scr_country VALUES (64,sysdate,  'DM', 'Dominica');
INSERT INTO scr_country VALUES (65,sysdate,  'DO', 'República Dominicana');
INSERT INTO scr_country VALUES (66,sysdate,  'EC', 'Ecuador');
INSERT INTO scr_country VALUES (67,sysdate,  'EG', 'Egipto');
INSERT INTO scr_country VALUES (68,sysdate,  'SV', 'El Salvador');
INSERT INTO scr_country VALUES (69,sysdate,  'AE', 'Emiratos Árabes Unidos');
INSERT INTO scr_country VALUES (70,sysdate,  'ER', 'Eritrea');
INSERT INTO scr_country VALUES (71,sysdate,  'SK', 'Eslovaquia');
INSERT INTO scr_country VALUES (72,sysdate,  'SI', 'Eslovenia');
INSERT INTO scr_country VALUES (73,sysdate,  'ES', 'España');
INSERT INTO scr_country VALUES (74,sysdate,  'UM', 'Islas ultramarinas de Estados Unidos');
INSERT INTO scr_country VALUES (75,sysdate,  'US', 'Estados Unidos');
INSERT INTO scr_country VALUES (76,sysdate,  'EE', 'Estonia');
INSERT INTO scr_country VALUES (77,sysdate,  'ET', 'Etiopía');
INSERT INTO scr_country VALUES (78,sysdate,  'FO', 'Islas Feroe');
INSERT INTO scr_country VALUES (79,sysdate,  'PH', 'Filipinas');
INSERT INTO scr_country VALUES (80,sysdate,  'FI', 'Finlandia');
INSERT INTO scr_country VALUES (81,sysdate,  'FJ', 'Fiyi');
INSERT INTO scr_country VALUES (82,sysdate,  'FR', 'Francia');
INSERT INTO scr_country VALUES (83,sysdate,  'GA', 'Gabón');
INSERT INTO scr_country VALUES (84,sysdate,  'GM', 'Gambia');
INSERT INTO scr_country VALUES (85,sysdate,  'GE', 'Georgia');
INSERT INTO scr_country VALUES (86,sysdate,  'GS', 'Islas Georgias del Sur y Sandwich del Sur');
INSERT INTO scr_country VALUES (87,sysdate,  'GH', 'Ghana');
INSERT INTO scr_country VALUES (88,sysdate,  'GI', 'Gibraltar');
INSERT INTO scr_country VALUES (89,sysdate,  'GD', 'Granada');
INSERT INTO scr_country VALUES (90,sysdate,  'GR', 'Grecia');
INSERT INTO scr_country VALUES (91,sysdate,  'GL', 'Groenlandia');
INSERT INTO scr_country VALUES (92,sysdate,  'GP', 'Guadalupe');
INSERT INTO scr_country VALUES (93,sysdate,  'GU', 'Guam');
INSERT INTO scr_country VALUES (94,sysdate,  'GT', 'Guatemala');
INSERT INTO scr_country VALUES (95,sysdate,  'GF', 'Guayana Francesa');
INSERT INTO scr_country VALUES (96,sysdate,  'GN', 'Guinea');
INSERT INTO scr_country VALUES (97,sysdate,  'GQ', 'Guinea Ecuatorial');
INSERT INTO scr_country VALUES (98,sysdate,  'GW', 'Guinea-Bissau');
INSERT INTO scr_country VALUES (99,sysdate,  'GY', 'Guyana');
INSERT INTO scr_country VALUES (100,sysdate, 'HT', 'Haití');
INSERT INTO scr_country VALUES (101,sysdate,  'HM','Islas Heard y McDonald');
INSERT INTO scr_country VALUES (102,sysdate,  'HN','Honduras');
INSERT INTO scr_country VALUES (103,sysdate,  'HK','Hong Kong');
INSERT INTO scr_country VALUES (104,sysdate,  'HU','Hungría');
INSERT INTO scr_country VALUES (105,sysdate,  'IN','India');
INSERT INTO scr_country VALUES (106,sysdate,  'ID','Indonesia');
INSERT INTO scr_country VALUES (107,sysdate,  'IR','Irán');
INSERT INTO scr_country VALUES (108,sysdate,  'IQ','Iraq');
INSERT INTO scr_country VALUES (109,sysdate,  'IE','Irlanda');
INSERT INTO scr_country VALUES (110,sysdate,  'IS','Islandia');
INSERT INTO scr_country VALUES (111,sysdate,  'IL','Israel');
INSERT INTO scr_country VALUES (112,sysdate,  'IT','Italia');
INSERT INTO scr_country VALUES (113,sysdate,  'JM','Jamaica');
INSERT INTO scr_country VALUES (114,sysdate,  'JP','Japón');
INSERT INTO scr_country VALUES (115,sysdate,  'JO','Jordania');
INSERT INTO scr_country VALUES (116,sysdate,  'KZ','Kazajstán');
INSERT INTO scr_country VALUES (117,sysdate,  'KE','Kenia');
INSERT INTO scr_country VALUES (118,sysdate,  'KG','Kirguistán');
INSERT INTO scr_country VALUES (119,sysdate,  'KI','Kiribati');
INSERT INTO scr_country VALUES (120,sysdate,  'KW','Kuwait');
INSERT INTO scr_country VALUES (121,sysdate,  'LA','Laos');
INSERT INTO scr_country VALUES (122,sysdate,  'LS','Lesotho');
INSERT INTO scr_country VALUES (123,sysdate,  'LV','Letonia');
INSERT INTO scr_country VALUES (124,sysdate,  'LB','Líbano');
INSERT INTO scr_country VALUES (125,sysdate,  'LR','Liberia');
INSERT INTO scr_country VALUES (126,sysdate,  'LY','Libia');
INSERT INTO scr_country VALUES (127,sysdate,  'LI','Liechtenstein');
INSERT INTO scr_country VALUES (128,sysdate,  'LT','Lituania');
INSERT INTO scr_country VALUES (129,sysdate,  'LU','Luxemburgo');
INSERT INTO scr_country VALUES (130,sysdate,  'MO','Macao');
INSERT INTO scr_country VALUES (131,sysdate,  'MK','ARY Macedonia');
INSERT INTO scr_country VALUES (132,sysdate,  'MG','Madagascar');
INSERT INTO scr_country VALUES (133,sysdate,  'MY','Malasia');
INSERT INTO scr_country VALUES (134,sysdate,  'MW','Malawi');
INSERT INTO scr_country VALUES (135,sysdate,  'MV','Maldivas');
INSERT INTO scr_country VALUES (136,sysdate,  'ML','Malí');
INSERT INTO scr_country VALUES (137,sysdate,  'MT','Malta');
INSERT INTO scr_country VALUES (138,sysdate,  'FK','Islas Malvinas');
INSERT INTO scr_country VALUES (139,sysdate,  'MP','Islas Marianas del Norte');
INSERT INTO scr_country VALUES (140,sysdate,  'MA','Marruecos');
INSERT INTO scr_country VALUES (141,sysdate,  'MH','Islas Marshall');
INSERT INTO scr_country VALUES (142,sysdate,  'MQ','Martinica');
INSERT INTO scr_country VALUES (143,sysdate,  'MU','Mauricio');
INSERT INTO scr_country VALUES (144,sysdate,  'MR','Mauritania');
INSERT INTO scr_country VALUES (145,sysdate,  'YT','Mayotte');
INSERT INTO scr_country VALUES (146,sysdate,  'MX','México');
INSERT INTO scr_country VALUES (147,sysdate,  'FM','Micronesia');
INSERT INTO scr_country VALUES (148,sysdate,  'MD','Moldavia');
INSERT INTO scr_country VALUES (149,sysdate,  'MC','Mónaco');
INSERT INTO scr_country VALUES (150,sysdate,  'MN','Mongolia');
INSERT INTO scr_country VALUES (151,sysdate,  'MS','Montserrat');
INSERT INTO scr_country VALUES (152,sysdate,  'MZ','Mozambique');
INSERT INTO scr_country VALUES (153,sysdate,  'MM','Myanmar');
INSERT INTO scr_country VALUES (154,sysdate,  'NA','Namibia');
INSERT INTO scr_country VALUES (155,sysdate,  'NR','Nauru');
INSERT INTO scr_country VALUES (156,sysdate,  'NP','Nepal');
INSERT INTO scr_country VALUES (157,sysdate,  'NI','Nicaragua');
INSERT INTO scr_country VALUES (158,sysdate,  'NE','Níger');
INSERT INTO scr_country VALUES (159,sysdate,  'NG','Nigeria');
INSERT INTO scr_country VALUES (160,sysdate,  'NU','Niue');
INSERT INTO scr_country VALUES (161,sysdate,  'NF','Isla Norfolk');
INSERT INTO scr_country VALUES (162,sysdate,  'NO','Noruega');
INSERT INTO scr_country VALUES (163,sysdate,  'NC','Nueva Caledonia');
INSERT INTO scr_country VALUES (164,sysdate,  'NZ','Nueva Zelanda');
INSERT INTO scr_country VALUES (165,sysdate,  'OM','Omán');
INSERT INTO scr_country VALUES (166,sysdate,  'NL','Países Bajos');
INSERT INTO scr_country VALUES (167,sysdate,  'PK','Pakistán');
INSERT INTO scr_country VALUES (168,sysdate,  'PW','Palau');
INSERT INTO scr_country VALUES (169,sysdate,  'PS','Palestina');
INSERT INTO scr_country VALUES (170,sysdate,  'PA','Panamá');
INSERT INTO scr_country VALUES (171,sysdate,  'PG','Papúa Nueva Guinea');
INSERT INTO scr_country VALUES (172,sysdate,  'PY','Paraguay');
INSERT INTO scr_country VALUES (173,sysdate,  'PE','Perú');
INSERT INTO scr_country VALUES (174,sysdate,  'PN','Islas Pitcairn');
INSERT INTO scr_country VALUES (175,sysdate,  'PF','Polinesia Francesa');
INSERT INTO scr_country VALUES (176,sysdate,  'PL','Polonia');
INSERT INTO scr_country VALUES (177,sysdate,  'PT','Portugal');
INSERT INTO scr_country VALUES (178,sysdate,  'PR','Puerto Rico');
INSERT INTO scr_country VALUES (179,sysdate,  'QA','Qatar');
INSERT INTO scr_country VALUES (180,sysdate,  'GB','Reino Unido');
INSERT INTO scr_country VALUES (181,sysdate,  'RE','Reunión');
INSERT INTO scr_country VALUES (182,sysdate,  'RW','Ruanda');
INSERT INTO scr_country VALUES (183,sysdate,  'RO','Rumania');
INSERT INTO scr_country VALUES (184,sysdate,  'RU','Rusia');
INSERT INTO scr_country VALUES (185,sysdate,  'EH','Sahara Occidental');
INSERT INTO scr_country VALUES (186,sysdate,  'SB','Islas Salomón');
INSERT INTO scr_country VALUES (187,sysdate,  'WS','Samoa');
INSERT INTO scr_country VALUES (188,sysdate,  'AS','Samoa Americana');
INSERT INTO scr_country VALUES (189,sysdate,  'KN','San Cristóbal y Nevis');
INSERT INTO scr_country VALUES (190,sysdate,  'SM','San Marino');
INSERT INTO scr_country VALUES (191,sysdate,  'PM','San Pedro y Miquelón');
INSERT INTO scr_country VALUES (192,sysdate,  'VC','San Vicente y las Granadinas');
INSERT INTO scr_country VALUES (193,sysdate,  'SH','Santa Helena');
INSERT INTO scr_country VALUES (194,sysdate,  'LC','Santa Lucía');
INSERT INTO scr_country VALUES (195,sysdate,  'ST','Santo Tomé y Príncipe');
INSERT INTO scr_country VALUES (196,sysdate,  'SN','Senegal');
INSERT INTO scr_country VALUES (197,sysdate,  'CS','Serbia y Montenegro');
INSERT INTO scr_country VALUES (198,sysdate,  'SC','Seychelles');
INSERT INTO scr_country VALUES (199,sysdate,  'SL','Sierra Leona');
INSERT INTO scr_country VALUES (200,sysdate,  'SG','Singapur');
INSERT INTO scr_country VALUES (201,sysdate,  'SY','Siria');
INSERT INTO scr_country VALUES (202,sysdate,  'SO','Somalia');
INSERT INTO scr_country VALUES (203,sysdate,  'LK','Sri Lanka');
INSERT INTO scr_country VALUES (204,sysdate,  'SZ','Suazilandia');
INSERT INTO scr_country VALUES (205,sysdate,  'ZA','Sudáfrica');
INSERT INTO scr_country VALUES (206,sysdate,  'SD','Sudán');
INSERT INTO scr_country VALUES (207,sysdate,  'SE','Suecia');
INSERT INTO scr_country VALUES (208,sysdate,  'CH','Suiza');
INSERT INTO scr_country VALUES (209,sysdate,  'SR','Surinam');
INSERT INTO scr_country VALUES (210,sysdate,  'SJ','Svalbard y Jan Mayen');
INSERT INTO scr_country VALUES (211,sysdate,  'TH','Tailandia');
INSERT INTO scr_country VALUES (212,sysdate,  'TW','Taiwán');
INSERT INTO scr_country VALUES (213,sysdate,  'TZ','Tanzania');
INSERT INTO scr_country VALUES (214,sysdate,  'TJ','Tayikistán');
INSERT INTO scr_country VALUES (215,sysdate,  'IO','Territorio Británico del Océano Índico');
INSERT INTO scr_country VALUES (216,sysdate,  'TF','Territorios Australes Franceses');
INSERT INTO scr_country VALUES (217,sysdate,  'TL','Timor Oriental');
INSERT INTO scr_country VALUES (218,sysdate,  'TG','Togo');
INSERT INTO scr_country VALUES (219,sysdate,  'TK','Tokelau');
INSERT INTO scr_country VALUES (220,sysdate,  'TO','Tonga');
INSERT INTO scr_country VALUES (221,sysdate,  'TT','Trinidad y Tobago');
INSERT INTO scr_country VALUES (222,sysdate,  'TN','Túnez');
INSERT INTO scr_country VALUES (223,sysdate,  'TC','Islas Turcas y Caicos');
INSERT INTO scr_country VALUES (224,sysdate,  'TM','Turkmenistán');
INSERT INTO scr_country VALUES (225,sysdate,  'TR','Turquía');
INSERT INTO scr_country VALUES (226,sysdate,  'TV','Tuvalu');
INSERT INTO scr_country VALUES (227,sysdate,  'UA','Ucrania');
INSERT INTO scr_country VALUES (228,sysdate,  'UG','Uganda');
INSERT INTO scr_country VALUES (229,sysdate,  'UY','Uruguay');
INSERT INTO scr_country VALUES (230,sysdate,  'UZ','Uzbekistán');
INSERT INTO scr_country VALUES (231,sysdate,  'VU','Vanuatu');
INSERT INTO scr_country VALUES (232,sysdate,  'VE','Venezuela');
INSERT INTO scr_country VALUES (233,sysdate,  'VN','Vietnam');
INSERT INTO scr_country VALUES (234,sysdate,  'VG','Islas Vírgenes Británicas');
INSERT INTO scr_country VALUES (235,sysdate,  'VI','Islas Vírgenes de los Estados Unidos');
INSERT INTO scr_country VALUES (236,sysdate,  'WF','Wallis y Futuna');
INSERT INTO scr_country VALUES (237,sysdate,  'YE','Yemen');
INSERT INTO scr_country VALUES (238,sysdate, 'DJ','Yibuti');
INSERT INTO scr_country VALUES (239,sysdate,  'ZM','Zambia');
INSERT INTO scr_country VALUES (240,sysdate,  'ZW','Zimbabue');

-- representante de interesado
CREATE TABLE scr_repre (
	id NUMBER(10) NOT NULL,
	id_representante NUMBER(10) NOT NULL,
	id_representado NUMBER(10) NOT NULL,
	id_address NUMBER(10),
	name varchar2(80 CHAR) NOT NULL
);