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
CREATE TABLE scr_attachment_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );
    
--
-- scr_attachment_sign_info_seq
--
CREATE TABLE scr_attachment_sign_info_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );
  
    
    

  --
-- scr_attachment
--
CREATE TABLE scr_attachment (id            int NOT NULL,
                             bookid        int NOT NULL,
                             folderid      int NOT NULL,
                             pageid        int NOT NULL,
                             comments      varchar(50) NULL,
                             mime_type     varchar(20) NULL,
                             name          varchar(80) NULL,
                             code_name     varchar(50) NULL,
                             validity_type int NULL,
                             document_type int NULL);
ALTER TABLE scr_attachment ADD CONSTRAINT pk_scr_attachment PRIMARY KEY (id);


--
-- scr_attachment_document_type
--

CREATE TABLE scr_attachment_document_type (id   int NOT NULL,
                                           name varchar(250) NULL,
                                           code varchar(2) NULL);
ALTER TABLE scr_attachment_document_type ADD CONSTRAINT pk_attachment_document_type PRIMARY KEY (id);


--
-- scr_attachment_sign_info
--

CREATE TABLE scr_attachment_sign_info (id                   int NOT NULL,
                                       id_attachment        int NOT NULL,
                                       id_attachment_signed int NULL,
                                       cert                 image NULL,
                                       signature            image NULL,
                                       signature_alg        varchar(2) NULL,
                                       signature_format     varchar(2) NULL,
                                       time_signature       image NULL,
                                       ocsp_validation      image NULL,
                                       hash_alg             varchar(2) NULL,
                                       hash                 image NULL);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT pk_attachment_sign_info PRIMARY KEY (id);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_attachment_sign_info_id UNIQUE (id_attachment);

--
-- scr_attachment_validity_type
--

CREATE TABLE scr_attachment_validity_type (id   int NOT NULL,
                                           name varchar(250) NULL,
                                           code varchar(2) NULL);
ALTER TABLE scr_attachment_validity_type ADD CONSTRAINT pk_attachment_validity_type PRIMARY KEY (id);


--
-- scr_entityreg
--

CREATE TABLE scr_entityreg (id      int NOT NULL,
                            code    varchar(21) NOT NULL,
                            name    varchar(80) NOT NULL,
                            id_ofic int NOT NULL);
ALTER TABLE scr_entityreg ADD CONSTRAINT pk_scr_entityreg PRIMARY KEY (id);


--
-- scr_exreg
--

CREATE TABLE scr_exreg(
    id int NOT NULL,
    id_arch int  NOT NULL,
    id_fdr int  NOT NULL,
    id_ofic int  NOT NULL,
    type_orig int NOT NULL,
    exchange_date datetime,
    state int NOT NULL,
    state_date datetime NOT NULL,
    id_exchange_sir varchar(33),
    id_exchange int NOT NULL,
    username varchar(32) NOT NULL,
    code_tramunit varchar(21) ,
    name_tramunit varchar(80) ,
    code_entity varchar(21) NOT NULL,
    name_entity varchar(80) NOT NULL,
    comments varchar(4000) 
)
;
ALTER TABLE scr_exreg
ADD CONSTRAINT pk_scr_exreg PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales
CREATE TABLE scr_exreg_id_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );


--
-- scr_exreg_in
--

CREATE TABLE scr_exreg_in(
    id int NOT NULL,
    id_arch int  ,
    id_fdr int  ,
    id_ofic int NOT NULL,
    exchange_date datetime NOT NULL,
    state int NOT NULL,
    state_date datetime NOT NULL,
    id_exchange_sir varchar(33) NOT NULL,
    id_exchange int NOT NULL,
    username varchar(32) NOT NULL,
    code_tramunit varchar(21) ,
    name_tramunit varchar(80) ,
    code_entity varchar(21) NOT NULL,
    name_entity varchar(80) NOT NULL,
    num_reg_orig varchar(20) NOT NULL,
    contacto_orig varchar(160),
    comments varchar(4000)
)
;
ALTER TABLE scr_exreg_in
ADD CONSTRAINT pk_scr_exreg_in PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales aceptados
CREATE TABLE scr_exreg_in_id_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );


--
-- scr_exregstate
--
--tabla para almacenar el historial de los intercambios de salida
CREATE TABLE scr_exregstate (
    id int NOT NULL,
    id_exreg int NOT NULL,
    state int NOT NULL,
    state_date datetime NOT NULL,
    username varchar(32) NOT NULL,
    comments varchar(4000)
)
;
ALTER TABLE scr_exregstate
ADD CONSTRAINT pk_scr_exregstate PRIMARY KEY (id)
;


--
-- scr_tramunit
--

CREATE TABLE scr_tramunit (id            int NOT NULL,
                           code_tramunit varchar(21) NULL,
                           name_tramunit varchar(80) NULL,
                           code_entity   varchar(21) NULL,
                           name_entity   varchar(80) NULL,
                           id_orgs       int NOT NULL);
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
    id int NOT NULL,
    tmstamp DATE NOT NULL,
    code varchar(16) NOT NULL,
    name varchar(50) NOT NULL
);

-- Datos para la tabla scr_country
INSERT INTO scr_country VALUES (1, getdate(), 'AF',  'Afganistán');
INSERT INTO scr_country VALUES (2, getdate(), 'AX',  'Islas Gland');
INSERT INTO scr_country VALUES (3, getdate(), 'AL',  'Albania');
INSERT INTO scr_country VALUES (4, getdate(), 'DE',  'Alemania');
INSERT INTO scr_country VALUES (5, getdate(), 'AD',  'Andorra');
INSERT INTO scr_country VALUES (6, getdate(), 'AO',  'Angola');
INSERT INTO scr_country VALUES (7, getdate(), 'AI',  'Anguilla');
INSERT INTO scr_country VALUES (8, getdate(), 'AQ',  'Antártida');
INSERT INTO scr_country VALUES (9, getdate(), 'AG',  'Antigua y Barbuda');
INSERT INTO scr_country VALUES (10,getdate(),  'AN', 'Antillas Holandesas');
INSERT INTO scr_country VALUES (11,getdate(),  'SA', 'Arabia Saudí');
INSERT INTO scr_country VALUES (12,getdate(),  'DZ', 'Argelia');
INSERT INTO scr_country VALUES (13,getdate(),  'AR', 'Argentina');
INSERT INTO scr_country VALUES (14,getdate(),  'AM', 'Armenia');
INSERT INTO scr_country VALUES (15,getdate(),  'AW', 'Aruba');
INSERT INTO scr_country VALUES (16,getdate(),  'AU', 'Australia');
INSERT INTO scr_country VALUES (17,getdate(),  'AT', 'Austria');
INSERT INTO scr_country VALUES (18,getdate(),  'AZ', 'Azerbaiyán');
INSERT INTO scr_country VALUES (19,getdate(),  'BS', 'Bahamas');
INSERT INTO scr_country VALUES (20,getdate(),  'BH', 'Bahréin');
INSERT INTO scr_country VALUES (21,getdate(),  'BD', 'Bangladesh');
INSERT INTO scr_country VALUES (22,getdate(),  'BB', 'Barbados');
INSERT INTO scr_country VALUES (23,getdate(),  'BY', 'Bielorrusia');
INSERT INTO scr_country VALUES (24,getdate(),  'BE', 'Bélgica');
INSERT INTO scr_country VALUES (25,getdate(),  'BZ', 'Belice');
INSERT INTO scr_country VALUES (26,getdate(),  'BJ', 'Benin');
INSERT INTO scr_country VALUES (27,getdate(),  'BM', 'Bermudas');
INSERT INTO scr_country VALUES (28,getdate(),  'BT', 'Bhután');
INSERT INTO scr_country VALUES (29,getdate(),  'BO', 'Bolivia');
INSERT INTO scr_country VALUES (30,getdate(),  'BA', 'Bosnia y Herzegovina');
INSERT INTO scr_country VALUES (31,getdate(),  'BW', 'Botsuana');
INSERT INTO scr_country VALUES (32,getdate(),  'BV', 'Isla Bouvet');
INSERT INTO scr_country VALUES (33,getdate(),  'BR', 'Brasil');
INSERT INTO scr_country VALUES (34,getdate(),  'BN', 'Brunéi');
INSERT INTO scr_country VALUES (35,getdate(),  'BG', 'Bulgaria');
INSERT INTO scr_country VALUES (36,getdate(),  'BF', 'Burkina Faso');
INSERT INTO scr_country VALUES (37,getdate(),  'BI', 'Burundi');
INSERT INTO scr_country VALUES (38,getdate(),  'CV', 'Cabo Verde');
INSERT INTO scr_country VALUES (39,getdate(),  'KY', 'Islas Caimán');
INSERT INTO scr_country VALUES (40,getdate(),  'KH', 'Camboya');
INSERT INTO scr_country VALUES (41,getdate(),  'CM', 'Camerún');
INSERT INTO scr_country VALUES (42,getdate(),  'CA', 'Canadá');
INSERT INTO scr_country VALUES (43,getdate(),  'CF', 'República Centroafricana');
INSERT INTO scr_country VALUES (44,getdate(),  'TD', 'Chad');
INSERT INTO scr_country VALUES (45,getdate(),  'CZ', 'República Checa');
INSERT INTO scr_country VALUES (46,getdate(),  'CL', 'Chile');
INSERT INTO scr_country VALUES (47,getdate(),  'CN', 'China');
INSERT INTO scr_country VALUES (48,getdate(),  'CY', 'Chipre');
INSERT INTO scr_country VALUES (49,getdate(),  'CX', 'Isla de Navidad');
INSERT INTO scr_country VALUES (50,getdate(),  'VA', 'Ciudad del Vaticano');
INSERT INTO scr_country VALUES (51,getdate(),  'CC', 'Islas Cocos');
INSERT INTO scr_country VALUES (52,getdate(),  'CO', 'Colombia');
INSERT INTO scr_country VALUES (53,getdate(),  'KM', 'Comoras');
INSERT INTO scr_country VALUES (54,getdate(),  'CD', 'República Democrática del Congo');
INSERT INTO scr_country VALUES (55,getdate(),  'CG', 'Congo');
INSERT INTO scr_country VALUES (56,getdate(),  'CK', 'Islas Cook');
INSERT INTO scr_country VALUES (57,getdate(),  'KP', 'Corea del Norte');
INSERT INTO scr_country VALUES (58,getdate(),  'KR', 'Corea del Sur');
INSERT INTO scr_country VALUES (59,getdate(),  'CI', 'Costa de Marfil');
INSERT INTO scr_country VALUES (60,getdate(),  'CR', 'Costa Rica');
INSERT INTO scr_country VALUES (61,getdate(),  'HR', 'Croacia');
INSERT INTO scr_country VALUES (62,getdate(),  'CU', 'Cuba');
INSERT INTO scr_country VALUES (63,getdate(),  'DK', 'Dinamarca');
INSERT INTO scr_country VALUES (64,getdate(),  'DM', 'Dominica');
INSERT INTO scr_country VALUES (65,getdate(),  'DO', 'República Dominicana');
INSERT INTO scr_country VALUES (66,getdate(),  'EC', 'Ecuador');
INSERT INTO scr_country VALUES (67,getdate(),  'EG', 'Egipto');
INSERT INTO scr_country VALUES (68,getdate(),  'SV', 'El Salvador');
INSERT INTO scr_country VALUES (69,getdate(),  'AE', 'Emiratos Árabes Unidos');
INSERT INTO scr_country VALUES (70,getdate(),  'ER', 'Eritrea');
INSERT INTO scr_country VALUES (71,getdate(),  'SK', 'Eslovaquia');
INSERT INTO scr_country VALUES (72,getdate(),  'SI', 'Eslovenia');
INSERT INTO scr_country VALUES (73,getdate(),  'ES', 'España');
INSERT INTO scr_country VALUES (74,getdate(),  'UM', 'Islas ultramarinas de Estados Unidos');
INSERT INTO scr_country VALUES (75,getdate(),  'US', 'Estados Unidos');
INSERT INTO scr_country VALUES (76,getdate(),  'EE', 'Estonia');
INSERT INTO scr_country VALUES (77,getdate(),  'ET', 'Etiopía');
INSERT INTO scr_country VALUES (78,getdate(),  'FO', 'Islas Feroe');
INSERT INTO scr_country VALUES (79,getdate(),  'PH', 'Filipinas');
INSERT INTO scr_country VALUES (80,getdate(),  'FI', 'Finlandia');
INSERT INTO scr_country VALUES (81,getdate(),  'FJ', 'Fiyi');
INSERT INTO scr_country VALUES (82,getdate(),  'FR', 'Francia');
INSERT INTO scr_country VALUES (83,getdate(),  'GA', 'Gabón');
INSERT INTO scr_country VALUES (84,getdate(),  'GM', 'Gambia');
INSERT INTO scr_country VALUES (85,getdate(),  'GE', 'Georgia');
INSERT INTO scr_country VALUES (86,getdate(),  'GS', 'Islas Georgias del Sur y Sandwich del Sur');
INSERT INTO scr_country VALUES (87,getdate(),  'GH', 'Ghana');
INSERT INTO scr_country VALUES (88,getdate(),  'GI', 'Gibraltar');
INSERT INTO scr_country VALUES (89,getdate(),  'GD', 'Granada');
INSERT INTO scr_country VALUES (90,getdate(),  'GR', 'Grecia');
INSERT INTO scr_country VALUES (91,getdate(),  'GL', 'Groenlandia');
INSERT INTO scr_country VALUES (92,getdate(),  'GP', 'Guadalupe');
INSERT INTO scr_country VALUES (93,getdate(),  'GU', 'Guam');
INSERT INTO scr_country VALUES (94,getdate(),  'GT', 'Guatemala');
INSERT INTO scr_country VALUES (95,getdate(),  'GF', 'Guayana Francesa');
INSERT INTO scr_country VALUES (96,getdate(),  'GN', 'Guinea');
INSERT INTO scr_country VALUES (97,getdate(),  'GQ', 'Guinea Ecuatorial');
INSERT INTO scr_country VALUES (98,getdate(),  'GW', 'Guinea-Bissau');
INSERT INTO scr_country VALUES (99,getdate(),  'GY', 'Guyana');
INSERT INTO scr_country VALUES (100,getdate(), 'HT', 'Haití');
INSERT INTO scr_country VALUES (101,getdate(),  'HM','Islas Heard y McDonald');
INSERT INTO scr_country VALUES (102,getdate(),  'HN','Honduras');
INSERT INTO scr_country VALUES (103,getdate(),  'HK','Hong Kong');
INSERT INTO scr_country VALUES (104,getdate(),  'HU','Hungría');
INSERT INTO scr_country VALUES (105,getdate(),  'IN','India');
INSERT INTO scr_country VALUES (106,getdate(),  'ID','Indonesia');
INSERT INTO scr_country VALUES (107,getdate(),  'IR','Irán');
INSERT INTO scr_country VALUES (108,getdate(),  'IQ','Iraq');
INSERT INTO scr_country VALUES (109,getdate(),  'IE','Irlanda');
INSERT INTO scr_country VALUES (110,getdate(),  'IS','Islandia');
INSERT INTO scr_country VALUES (111,getdate(),  'IL','Israel');
INSERT INTO scr_country VALUES (112,getdate(),  'IT','Italia');
INSERT INTO scr_country VALUES (113,getdate(),  'JM','Jamaica');
INSERT INTO scr_country VALUES (114,getdate(),  'JP','Japón');
INSERT INTO scr_country VALUES (115,getdate(),  'JO','Jordania');
INSERT INTO scr_country VALUES (116,getdate(),  'KZ','Kazajstán');
INSERT INTO scr_country VALUES (117,getdate(),  'KE','Kenia');
INSERT INTO scr_country VALUES (118,getdate(),  'KG','Kirguistán');
INSERT INTO scr_country VALUES (119,getdate(),  'KI','Kiribati');
INSERT INTO scr_country VALUES (120,getdate(),  'KW','Kuwait');
INSERT INTO scr_country VALUES (121,getdate(),  'LA','Laos');
INSERT INTO scr_country VALUES (122,getdate(),  'LS','Lesotho');
INSERT INTO scr_country VALUES (123,getdate(),  'LV','Letonia');
INSERT INTO scr_country VALUES (124,getdate(),  'LB','Líbano');
INSERT INTO scr_country VALUES (125,getdate(),  'LR','Liberia');
INSERT INTO scr_country VALUES (126,getdate(),  'LY','Libia');
INSERT INTO scr_country VALUES (127,getdate(),  'LI','Liechtenstein');
INSERT INTO scr_country VALUES (128,getdate(),  'LT','Lituania');
INSERT INTO scr_country VALUES (129,getdate(),  'LU','Luxemburgo');
INSERT INTO scr_country VALUES (130,getdate(),  'MO','Macao');
INSERT INTO scr_country VALUES (131,getdate(),  'MK','ARY Macedonia');
INSERT INTO scr_country VALUES (132,getdate(),  'MG','Madagascar');
INSERT INTO scr_country VALUES (133,getdate(),  'MY','Malasia');
INSERT INTO scr_country VALUES (134,getdate(),  'MW','Malawi');
INSERT INTO scr_country VALUES (135,getdate(),  'MV','Maldivas');
INSERT INTO scr_country VALUES (136,getdate(),  'ML','Malí');
INSERT INTO scr_country VALUES (137,getdate(),  'MT','Malta');
INSERT INTO scr_country VALUES (138,getdate(),  'FK','Islas Malvinas');
INSERT INTO scr_country VALUES (139,getdate(),  'MP','Islas Marianas del Norte');
INSERT INTO scr_country VALUES (140,getdate(),  'MA','Marruecos');
INSERT INTO scr_country VALUES (141,getdate(),  'MH','Islas Marshall');
INSERT INTO scr_country VALUES (142,getdate(),  'MQ','Martinica');
INSERT INTO scr_country VALUES (143,getdate(),  'MU','Mauricio');
INSERT INTO scr_country VALUES (144,getdate(),  'MR','Mauritania');
INSERT INTO scr_country VALUES (145,getdate(),  'YT','Mayotte');
INSERT INTO scr_country VALUES (146,getdate(),  'MX','México');
INSERT INTO scr_country VALUES (147,getdate(),  'FM','Micronesia');
INSERT INTO scr_country VALUES (148,getdate(),  'MD','Moldavia');
INSERT INTO scr_country VALUES (149,getdate(),  'MC','Mónaco');
INSERT INTO scr_country VALUES (150,getdate(),  'MN','Mongolia');
INSERT INTO scr_country VALUES (151,getdate(),  'MS','Montserrat');
INSERT INTO scr_country VALUES (152,getdate(),  'MZ','Mozambique');
INSERT INTO scr_country VALUES (153,getdate(),  'MM','Myanmar');
INSERT INTO scr_country VALUES (154,getdate(),  'NA','Namibia');
INSERT INTO scr_country VALUES (155,getdate(),  'NR','Nauru');
INSERT INTO scr_country VALUES (156,getdate(),  'NP','Nepal');
INSERT INTO scr_country VALUES (157,getdate(),  'NI','Nicaragua');
INSERT INTO scr_country VALUES (158,getdate(),  'NE','Níger');
INSERT INTO scr_country VALUES (159,getdate(),  'NG','Nigeria');
INSERT INTO scr_country VALUES (160,getdate(),  'NU','Niue');
INSERT INTO scr_country VALUES (161,getdate(),  'NF','Isla Norfolk');
INSERT INTO scr_country VALUES (162,getdate(),  'NO','Noruega');
INSERT INTO scr_country VALUES (163,getdate(),  'NC','Nueva Caledonia');
INSERT INTO scr_country VALUES (164,getdate(),  'NZ','Nueva Zelanda');
INSERT INTO scr_country VALUES (165,getdate(),  'OM','Omán');
INSERT INTO scr_country VALUES (166,getdate(),  'NL','Países Bajos');
INSERT INTO scr_country VALUES (167,getdate(),  'PK','Pakistán');
INSERT INTO scr_country VALUES (168,getdate(),  'PW','Palau');
INSERT INTO scr_country VALUES (169,getdate(),  'PS','Palestina');
INSERT INTO scr_country VALUES (170,getdate(),  'PA','Panamá');
INSERT INTO scr_country VALUES (171,getdate(),  'PG','Papúa Nueva Guinea');
INSERT INTO scr_country VALUES (172,getdate(),  'PY','Paraguay');
INSERT INTO scr_country VALUES (173,getdate(),  'PE','Perú');
INSERT INTO scr_country VALUES (174,getdate(),  'PN','Islas Pitcairn');
INSERT INTO scr_country VALUES (175,getdate(),  'PF','Polinesia Francesa');
INSERT INTO scr_country VALUES (176,getdate(),  'PL','Polonia');
INSERT INTO scr_country VALUES (177,getdate(),  'PT','Portugal');
INSERT INTO scr_country VALUES (178,getdate(),  'PR','Puerto Rico');
INSERT INTO scr_country VALUES (179,getdate(),  'QA','Qatar');
INSERT INTO scr_country VALUES (180,getdate(),  'GB','Reino Unido');
INSERT INTO scr_country VALUES (181,getdate(),  'RE','Reunión');
INSERT INTO scr_country VALUES (182,getdate(),  'RW','Ruanda');
INSERT INTO scr_country VALUES (183,getdate(),  'RO','Rumania');
INSERT INTO scr_country VALUES (184,getdate(),  'RU','Rusia');
INSERT INTO scr_country VALUES (185,getdate(),  'EH','Sahara Occidental');
INSERT INTO scr_country VALUES (186,getdate(),  'SB','Islas Salomón');
INSERT INTO scr_country VALUES (187,getdate(),  'WS','Samoa');
INSERT INTO scr_country VALUES (188,getdate(),  'AS','Samoa Americana');
INSERT INTO scr_country VALUES (189,getdate(),  'KN','San Cristóbal y Nevis');
INSERT INTO scr_country VALUES (190,getdate(),  'SM','San Marino');
INSERT INTO scr_country VALUES (191,getdate(),  'PM','San Pedro y Miquelón');
INSERT INTO scr_country VALUES (192,getdate(),  'VC','San Vicente y las Granadinas');
INSERT INTO scr_country VALUES (193,getdate(),  'SH','Santa Helena');
INSERT INTO scr_country VALUES (194,getdate(),  'LC','Santa Lucía');
INSERT INTO scr_country VALUES (195,getdate(),  'ST','Santo Tomé y Príncipe');
INSERT INTO scr_country VALUES (196,getdate(),  'SN','Senegal');
INSERT INTO scr_country VALUES (197,getdate(),  'CS','Serbia y Montenegro');
INSERT INTO scr_country VALUES (198,getdate(),  'SC','Seychelles');
INSERT INTO scr_country VALUES (199,getdate(),  'SL','Sierra Leona');
INSERT INTO scr_country VALUES (200,getdate(),  'SG','Singapur');
INSERT INTO scr_country VALUES (201,getdate(),  'SY','Siria');
INSERT INTO scr_country VALUES (202,getdate(),  'SO','Somalia');
INSERT INTO scr_country VALUES (203,getdate(),  'LK','Sri Lanka');
INSERT INTO scr_country VALUES (204,getdate(),  'SZ','Suazilandia');
INSERT INTO scr_country VALUES (205,getdate(),  'ZA','Sudáfrica');
INSERT INTO scr_country VALUES (206,getdate(),  'SD','Sudán');
INSERT INTO scr_country VALUES (207,getdate(),  'SE','Suecia');
INSERT INTO scr_country VALUES (208,getdate(),  'CH','Suiza');
INSERT INTO scr_country VALUES (209,getdate(),  'SR','Surinam');
INSERT INTO scr_country VALUES (210,getdate(),  'SJ','Svalbard y Jan Mayen');
INSERT INTO scr_country VALUES (211,getdate(),  'TH','Tailandia');
INSERT INTO scr_country VALUES (212,getdate(),  'TW','Taiwán');
INSERT INTO scr_country VALUES (213,getdate(),  'TZ','Tanzania');
INSERT INTO scr_country VALUES (214,getdate(),  'TJ','Tayikistán');
INSERT INTO scr_country VALUES (215,getdate(),  'IO','Territorio Británico del Océano Índico');
INSERT INTO scr_country VALUES (216,getdate(),  'TF','Territorios Australes Franceses');
INSERT INTO scr_country VALUES (217,getdate(),  'TL','Timor Oriental');
INSERT INTO scr_country VALUES (218,getdate(),  'TG','Togo');
INSERT INTO scr_country VALUES (219,getdate(),  'TK','Tokelau');
INSERT INTO scr_country VALUES (220,getdate(),  'TO','Tonga');
INSERT INTO scr_country VALUES (221,getdate(),  'TT','Trinidad y Tobago');
INSERT INTO scr_country VALUES (222,getdate(),  'TN','Túnez');
INSERT INTO scr_country VALUES (223,getdate(),  'TC','Islas Turcas y Caicos');
INSERT INTO scr_country VALUES (224,getdate(),  'TM','Turkmenistán');
INSERT INTO scr_country VALUES (225,getdate(),  'TR','Turquía');
INSERT INTO scr_country VALUES (226,getdate(),  'TV','Tuvalu');
INSERT INTO scr_country VALUES (227,getdate(),  'UA','Ucrania');
INSERT INTO scr_country VALUES (228,getdate(),  'UG','Uganda');
INSERT INTO scr_country VALUES (229,getdate(),  'UY','Uruguay');
INSERT INTO scr_country VALUES (230,getdate(),  'UZ','Uzbekistán');
INSERT INTO scr_country VALUES (231,getdate(),  'VU','Vanuatu');
INSERT INTO scr_country VALUES (232,getdate(),  'VE','Venezuela');
INSERT INTO scr_country VALUES (233,getdate(),  'VN','Vietnam');
INSERT INTO scr_country VALUES (234,getdate(),  'VG','Islas Vírgenes Británicas');
INSERT INTO scr_country VALUES (235,getdate(),  'VI','Islas Vírgenes de los Estados Unidos');
INSERT INTO scr_country VALUES (236,getdate(),  'WF','Wallis y Futuna');
INSERT INTO scr_country VALUES (237,getdate(),  'YE','Yemen');
INSERT INTO scr_country VALUES (238,getdate(), 'DJ','Yibuti');
INSERT INTO scr_country VALUES (239,getdate(),  'ZM','Zambia');
INSERT INTO scr_country VALUES (240,getdate(),  'ZW','Zimbabue');

-- representante de interesado
CREATE TABLE scr_repre (
	id int NOT NULL,
	id_representante int NOT NULL,
	id_representado int NOT NULL,
	id_address int,
	name varchar(80) NOT NULL
);