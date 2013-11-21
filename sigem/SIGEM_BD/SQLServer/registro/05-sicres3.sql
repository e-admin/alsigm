-- tabla para mapeo de las entidades registrales del directorio con la tabla scr_orgs
CREATE TABLE scr_entityreg(
    id int NOT NULL,
    code varchar(21) NOT NULL,
    name varchar(80) NOT NULL,
    id_ofic int NOT NULL
)
;


ALTER TABLE scr_entityreg
ADD CONSTRAINT pk_scr_entityreg PRIMARY KEY (id)
;

-- secuencia para el identificador de las entidades registral
CREATE TABLE scr_entityreg_id_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );

ALTER TABLE scr_entityreg
ADD CONSTRAINT fk_entityofic
FOREIGN KEY (id_ofic)
REFERENCES scr_ofic(id)
;

-- tabla de mapeo de las unidades de tramitacion del directorio con la tabla scr_orgs
CREATE TABLE scr_tramunit(
    id int NOT NULL,
    code_tramunit varchar(21),
    name_tramunit varchar(80),
    code_entity varchar(21),
    name_entity varchar(80),
    id_orgs int NOT NULL
)
;

-- secuencia para el identificador de las unidades registral
CREATE TABLE scr_tramunit_id_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );

ALTER TABLE scr_tramunit
ADD CONSTRAINT pk_scr_tramunit PRIMARY KEY (id)
;

ALTER TABLE scr_tramunit
ADD CONSTRAINT fk_tramunitgorgs
FOREIGN KEY (id_orgs)
REFERENCES scr_orgs(id)
;

ALTER TABLE scr_tramunit
  ADD CONSTRAINT u_code_tramunit UNIQUE (code_tramunit)
;

--tabla para almacenar los registros que se han enviado a un intercambio registral
--el estado podra ser pendiente de envio,enviado,enviado erroneo,aceptado,rechazado,anulado
-- type_orig identifica si el registro es de un libro de entrada o uno de salida
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

-- secuencia para scr_exregstate
CREATE TABLE scr_exregstate_id_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );



--tabla para almacenar los registros que se nos han llegado mediante intercambio registral
-- el estado podra ser aceptado, rechazado
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

  -- tablas de mapeos de codigos del directorio comun de provincias, de ciudades y de paises

--provincias
CREATE TABLE scr_provexreg(
    id int NOT NULL,
     code varchar(2) NOT NULL,
    name varchar(80) NOT NULL,
    id_prov int NOT NULL
);
ALTER TABLE scr_provexreg ADD CONSTRAINT pk_scr_provexreg PRIMARY KEY (id);
ALTER TABLE scr_provexreg ADD CONSTRAINT fk_provexregprov FOREIGN KEY (id_prov) REFERENCES scr_prov(id);

--ciudades/municipios
CREATE TABLE scr_citiesexreg(
    id int NOT NULL,
     code varchar(5) NOT NULL,
    name varchar(80) NOT NULL,
    id_city int NOT NULL
);
ALTER TABLE scr_citiesexreg ADD CONSTRAINT pk_scr_citiesexreg PRIMARY KEY (id);
ALTER TABLE scr_citiesexreg ADD CONSTRAINT fk_citiesexregcities FOREIGN KEY (id_city) REFERENCES scr_cities(id);

--modificacion del tamanyo del nombre de las personas juridicas
ALTER TABLE scr_pjur ALTER COLUMN name varchar(80) NOT NULL;

--nombre del interesado persona fisica
ALTER TABLE scr_pfis ALTER COLUMN surname varchar(30);
--apellido del interesado persona fisica
ALTER TABLE scr_pfis ALTER COLUMN first_name varchar(30) NOT NULL;
--segundo apellido del interesado persona fisica
ALTER TABLE scr_pfis ALTER COLUMN second_name varchar(30);

-- direccion domicilio
ALTER TABLE scr_dom ALTER COLUMN address varchar(160) NOT NULL;


--tabla maestro que indica el tipo de validez de los adjuntos considerados documentos electronicos
CREATE TABLE scr_attachment_validity_type(
    id int NOT NULL,
    name  varchar(250),
    code varchar(2)
)
;
ALTER TABLE scr_attachment_validity_type
ADD CONSTRAINT pk_scr_attachment_validity_type PRIMARY KEY (id)
;
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (1,'COPIA','01');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (2,'COPIA COMPULSADA','02');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (3,'COPIA ORIGINAL','03');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (4,'ORIGINAL','04');

--tabla maestro que indica el tipo de validez de los adjuntos considerados documentos electronicos
CREATE TABLE scr_attachment_document_type(
    id int NOT NULL,
    name  varchar(250),
    code varchar(2)
)
;
INSERT INTO scr_attachment_document_type(id,name,code) VALUES (1,'FORMULARIO','01');
INSERT INTO scr_attachment_document_type(id,name,code) VALUES (2,'DOCUMENTO ADJUNTO','02');
INSERT INTO scr_attachment_document_type(id,name,code) VALUES (3,'FICHERO TECNICO','03');


ALTER TABLE scr_attachment_document_type
ADD CONSTRAINT pk_scr_attachment_document_type PRIMARY KEY (id)
;

--tabla para almacenar informacion de los adjuntos considerados documentos electronicos de la norma sicres3
CREATE TABLE scr_attachment(
    id int NOT NULL,
    bookid int  NOT NULL,
    folderid int  NOT NULL,
    pageid int  NOT NULL,
    comments varchar(50),
    mime_type varchar(20),
    name  varchar(80),
    code_name varchar(50),
    validity_type int,
    document_type int
)
;
ALTER TABLE scr_attachment
ADD CONSTRAINT pk_scr_attachment PRIMARY KEY (id)
;

ALTER TABLE scr_attachment ADD CONSTRAINT fk_validity_type_attachment FOREIGN KEY (validity_type) REFERENCES scr_attachment_validity_type(id);
ALTER TABLE scr_attachment ADD CONSTRAINT fk_document_type_attachment FOREIGN KEY (document_type) REFERENCES scr_attachment_document_type(id);


-- secuencia para el identificador de scr_attachment
CREATE TABLE scr_attachment_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );

  --tabla para almacenar informacion de los adjuntos considerados documentos electronicos de la norma sicres3
CREATE TABLE scr_attachment_sign_info(
    id int NOT NULL,
    id_attachment int  NOT NULL UNIQUE,
    id_attachment_signed int,
    cert image,
    signature image ,
    signature_alg varchar(2), --enumerado
    signature_format varchar(2),--enumerado
    time_signature image,
    ocsp_validation image,
    hash_alg varchar(2), --enumerado
    hash image
)
;

ALTER TABLE scr_attachment_sign_info
ADD CONSTRAINT pk_scr_attachment_sign_info PRIMARY KEY (id)
;

ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_sign_info_attachment FOREIGN KEY (id_attachment) REFERENCES scr_attachment(id);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_sign_info_attachment_signed FOREIGN KEY (id_attachment_signed) REFERENCES scr_attachment(id);


-- secuencia para el identificador de scr_attachment
CREATE TABLE scr_attachment_sign_info_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );

-- modificaciones para terceros
-- paises
CREATE TABLE scr_country (
    id int NOT NULL,
    tmstamp datetime NOT NULL,
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

--Nuevos paises
INSERT INTO scr_country VALUES (242,getdate(),  'GG','Guernsey');
INSERT INTO scr_country VALUES (243,getdate(),  'IM','Isla de Man');
INSERT INTO scr_country VALUES (244,getdate(),  'JE','Jersey');
INSERT INTO scr_country VALUES (245,getdate(),  'ME','Montenegro');
INSERT INTO scr_country VALUES (246,getdate(),  'BL','San Bartolomé');
INSERT INTO scr_country VALUES (247,getdate(),  'MF','San Martín');
INSERT INTO scr_country VALUES (248,getdate(),  'RS','Serbia');

-- las direcciones fisicas de terceros llevan el pais
ALTER TABLE scr_dom ADD pais varchar(50);

-- representante de interesado
CREATE TABLE scr_repre (
	id int NOT NULL,
	id_representante int NOT NULL,
	id_representado int NOT NULL,
	id_address int,
	name varchar(95) NOT NULL
);

-- Tabla para mapeo de los códigos de tipo de transporte de la normativa de intercambio
-- registal de la normativa sicres 3 con la tabla SCR_TT.
CREATE TABLE scr_ttexreg (
    id int NOT NULL,
    code varchar(10) NOT NULL,
    name varchar(31) NOT NULL,
    id_scr_tt int not null
);

-- Secuencias

-- Secuencia para el identificador de la tabla scr_ttexreg
CREATE TABLE scr_ttexreg_id_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );



-- Constraints

-- TABLA scr_tt
ALTER TABLE scr_tt ADD CONSTRAINT pk_scr_scr_tt0 PRIMARY KEY (id);

CREATE UNIQUE INDEX uk_scr_scr_tt0 ON scr_tt(transport);

-- TABLA scr_ttexreg
CREATE UNIQUE INDEX uk_scr_ttexreg0 ON scr_ttexreg(name);
ALTER TABLE scr_ttexreg ADD CONSTRAINT pk_scr_ttexreg0 PRIMARY KEY (id);
ALTER TABLE scr_ttexreg ADD CONSTRAINT fk_scr_ttexreg0 FOREIGN KEY (id_scr_tt) REFERENCES scr_tt(id) ON DELETE CASCADE;



-- Nueva tabla mapeo country para intercambio registral con la tabla scr_country
CREATE TABLE scr_countryexreg(
    id int NOT NULL,
    code varchar(4) NOT NULL,
    name varchar(80) NOT NULL,
    id_country int NOT NULL
);

-- TABLA scr_country
ALTER TABLE scr_country ADD CONSTRAINT pk_scr_country0 PRIMARY KEY (id);

-- TABLA scr_dom
ALTER TABLE scr_dom ADD CONSTRAINT pk_scr_dom0 PRIMARY KEY (id);

-- TABLA scr_countryexreg
ALTER TABLE scr_countryexreg ADD CONSTRAINT pk_scr_countryexreg0 PRIMARY KEY (id);
CREATE UNIQUE INDEX uk_scr_countryexreg0 ON scr_countryexreg (code);
CREATE UNIQUE INDEX uk_scr_countryexreg1 ON scr_countryexreg (name);
ALTER TABLE scr_countryexreg ADD CONSTRAINT fk_scr_countryexreg0 FOREIGN KEY (id_country) REFERENCES scr_country(id) ON DELETE CASCADE;

--Asociacion de los paises de sicres con los paises del DCO
INSERT INTO scr_countryexreg VALUES (1,'004','Afganistán',1);
INSERT INTO scr_countryexreg VALUES (2,'764','Tailandia',211);
INSERT INTO scr_countryexreg VALUES (3,'248','Åland',2);
INSERT INTO scr_countryexreg VALUES (4,'008','Albania',3);
INSERT INTO scr_countryexreg VALUES (5,'276','Alemania',4);
INSERT INTO scr_countryexreg VALUES (6,'020','Andorra',5);
INSERT INTO scr_countryexreg VALUES (7,'024','Angola',6);
INSERT INTO scr_countryexreg VALUES (8,'660','Anguila',7);
INSERT INTO scr_countryexreg VALUES (9,'010','Antártida',8);
INSERT INTO scr_countryexreg VALUES (10,'028','Antigua y Barbuda',9);
INSERT INTO scr_countryexreg VALUES (11,'530','Antillas Neerlandesas',10);
INSERT INTO scr_countryexreg VALUES (12,'682','Arabia Saudita',11);
INSERT INTO scr_countryexreg VALUES (13,'012','Argelia',12);
INSERT INTO scr_countryexreg VALUES (14,'032','Argentina',13);
INSERT INTO scr_countryexreg VALUES (15,'051','Armenia',14);
INSERT INTO scr_countryexreg VALUES (16,'533','Aruba',15);
INSERT INTO scr_countryexreg VALUES (17,'036','Australia',16);
INSERT INTO scr_countryexreg VALUES (18,'040','Austria',17);
INSERT INTO scr_countryexreg VALUES (19,'275','Autoridad Nacional Palestina',169);
INSERT INTO scr_countryexreg VALUES (20,'031','Azerbaiyán',18);
INSERT INTO scr_countryexreg VALUES (21,'044','Bahamas',19);
INSERT INTO scr_countryexreg VALUES (22,'050','Bangladés',21);
INSERT INTO scr_countryexreg VALUES (23,'052','Barbados',22);
INSERT INTO scr_countryexreg VALUES (24,'048','Baréin',20);
INSERT INTO scr_countryexreg VALUES (25,'056','Bélgica',24);
INSERT INTO scr_countryexreg VALUES (26,'084','Belice',25);
INSERT INTO scr_countryexreg VALUES (27,'204','Benín',26);
INSERT INTO scr_countryexreg VALUES (28,'060','Bermudas',27);
INSERT INTO scr_countryexreg VALUES (29,'112','Bielorrusia',23);
INSERT INTO scr_countryexreg VALUES (30,'104','Myanmar',153);
INSERT INTO scr_countryexreg VALUES (31,'068','Bolivia',29);
INSERT INTO scr_countryexreg VALUES (32,'070','Bosnia y Herzegovina',30);
INSERT INTO scr_countryexreg VALUES (33,'072','Botsuana',31);
INSERT INTO scr_countryexreg VALUES (34,'076','Brasil',33);
INSERT INTO scr_countryexreg VALUES (35,'096','Brunéi',34);
INSERT INTO scr_countryexreg VALUES (36,'100','Bulgaria',35);
INSERT INTO scr_countryexreg VALUES (37,'854','Burkina Faso',36);
INSERT INTO scr_countryexreg VALUES (38,'108','Burundi',37);
INSERT INTO scr_countryexreg VALUES (39,'064','Bután',28);
INSERT INTO scr_countryexreg VALUES (40,'132','Cabo Verde',38);
INSERT INTO scr_countryexreg VALUES (41,'116','Camboya',40);
INSERT INTO scr_countryexreg VALUES (42,'120','Camerún',41);
INSERT INTO scr_countryexreg VALUES (43,'124','Canadá',42);
INSERT INTO scr_countryexreg VALUES (44,'634','Catar',179);
INSERT INTO scr_countryexreg VALUES (45,'148','Chad',44);
INSERT INTO scr_countryexreg VALUES (46,'152','Chile',46);
INSERT INTO scr_countryexreg VALUES (47,'156','China',47);
INSERT INTO scr_countryexreg VALUES (48,'196','Chipre',48);
INSERT INTO scr_countryexreg VALUES (49,'336','Ciudad del Vaticano',50);
INSERT INTO scr_countryexreg VALUES (50,'170','Colombia',52);
INSERT INTO scr_countryexreg VALUES (51,'174','Comoras',53);
INSERT INTO scr_countryexreg VALUES (52,'408','Corea del Norte',57);
INSERT INTO scr_countryexreg VALUES (53,'410','Corea del Sur',58);
INSERT INTO scr_countryexreg VALUES (54,'384','Costa de Marfil',59);
INSERT INTO scr_countryexreg VALUES (55,'188','Costa Rica',60);
INSERT INTO scr_countryexreg VALUES (56,'191','Croacia',61);
INSERT INTO scr_countryexreg VALUES (57,'192','Cuba',62);
INSERT INTO scr_countryexreg VALUES (58,'208','Dinamarca',63);
INSERT INTO scr_countryexreg VALUES (59,'212','Dominica',64);
INSERT INTO scr_countryexreg VALUES (60,'218','Ecuador',66);
INSERT INTO scr_countryexreg VALUES (61,'818','Egipto',67);
INSERT INTO scr_countryexreg VALUES (62,'222','El Salvador',68);
INSERT INTO scr_countryexreg VALUES (63,'784','Emiratos Árabes Unidos',69);
INSERT INTO scr_countryexreg VALUES (64,'232','Eritrea',70);
INSERT INTO scr_countryexreg VALUES (65,'703','Eslovaquia',71);
INSERT INTO scr_countryexreg VALUES (66,'705','Eslovenia',72);
INSERT INTO scr_countryexreg VALUES (67,'724','España',73);
INSERT INTO scr_countryexreg VALUES (68,'840','Estados Unidos',75);
INSERT INTO scr_countryexreg VALUES (69,'233','Estonia',76);
INSERT INTO scr_countryexreg VALUES (70,'231','Etiopía',77);
INSERT INTO scr_countryexreg VALUES (71,'608','Filipinas',79);
INSERT INTO scr_countryexreg VALUES (72,'246','Finlandia',80);
INSERT INTO scr_countryexreg VALUES (73,'242','Fiyi',81);
INSERT INTO scr_countryexreg VALUES (74,'250','Francia',82);
INSERT INTO scr_countryexreg VALUES (75,'266','Gabón',83);
INSERT INTO scr_countryexreg VALUES (76,'270','Gambia',84);
INSERT INTO scr_countryexreg VALUES (77,'268','Georgia',85);
INSERT INTO scr_countryexreg VALUES (78,'288','Ghana',87);
INSERT INTO scr_countryexreg VALUES (79,'292','Gibraltar',88);
INSERT INTO scr_countryexreg VALUES (80,'308','Granada',89);
INSERT INTO scr_countryexreg VALUES (81,'300','Grecia',90);
INSERT INTO scr_countryexreg VALUES (82,'304','Groenlandia',91);
INSERT INTO scr_countryexreg VALUES (83,'312','Guadalupe',92);
INSERT INTO scr_countryexreg VALUES (84,'316','Guam',93);
INSERT INTO scr_countryexreg VALUES (85,'320','Guatemala',94);
INSERT INTO scr_countryexreg VALUES (86,'254','Guayana Francesa',95);
INSERT INTO scr_countryexreg VALUES (87,'831','Guernsey',242);
INSERT INTO scr_countryexreg VALUES (88,'324','Guinea',96);
INSERT INTO scr_countryexreg VALUES (89,'226','Guinea Ecuatorial',97);
INSERT INTO scr_countryexreg VALUES (90,'624','Guinea-Bissau',98);
INSERT INTO scr_countryexreg VALUES (91,'328','Guyana',99);
INSERT INTO scr_countryexreg VALUES (92,'332','Haití',100);
INSERT INTO scr_countryexreg VALUES (93,'340','Honduras',102);
INSERT INTO scr_countryexreg VALUES (94,'344','Hong Kong',103);
INSERT INTO scr_countryexreg VALUES (95,'348','Hungría',104);
INSERT INTO scr_countryexreg VALUES (96,'356','India',105);
INSERT INTO scr_countryexreg VALUES (97,'360','Indonesia',106);
INSERT INTO scr_countryexreg VALUES (98,'368','Irak',108);
INSERT INTO scr_countryexreg VALUES (99,'364','Irán',107);
INSERT INTO scr_countryexreg VALUES (100,'372','Irlanda',109);
INSERT INTO scr_countryexreg VALUES (101,'074','Isla Bouvet',32);
INSERT INTO scr_countryexreg VALUES (102,'833','Isla de Man',243);
INSERT INTO scr_countryexreg VALUES (103,'162','Isla de Navidad',49);
INSERT INTO scr_countryexreg VALUES (104,'352','Islandia',110);
INSERT INTO scr_countryexreg VALUES (105,'136','Islas Caimán',39);
INSERT INTO scr_countryexreg VALUES (106,'166','Islas Cocos',51);
INSERT INTO scr_countryexreg VALUES (107,'184','Islas Cook',56);
INSERT INTO scr_countryexreg VALUES (108,'234','Islas Feroe',78);
INSERT INTO scr_countryexreg VALUES (109,'239','Islas Georgias del Sur y Sandwich del Sur',86);
INSERT INTO scr_countryexreg VALUES (110,'334','Islas Heard y McDonald',101);
INSERT INTO scr_countryexreg VALUES (111,'238','Islas Malvinas',138);
INSERT INTO scr_countryexreg VALUES (112,'580','Islas Marianas del Norte',139);
INSERT INTO scr_countryexreg VALUES (113,'584','Islas Marshall',141);
INSERT INTO scr_countryexreg VALUES (114,'612','Islas Pitcairn',174);
INSERT INTO scr_countryexreg VALUES (115,'090','Islas Salomón',186);
INSERT INTO scr_countryexreg VALUES (116,'796','Islas Turcas y Caicos',223);
INSERT INTO scr_countryexreg VALUES (117,'581','Islas ultramarinas de Estados Unidos',74);
INSERT INTO scr_countryexreg VALUES (118,'092','Islas Vírgenes Británicas',234);
INSERT INTO scr_countryexreg VALUES (119,'850','Islas Vírgenes de los Estados Unidos',235);
INSERT INTO scr_countryexreg VALUES (120,'376','Israel',111);
INSERT INTO scr_countryexreg VALUES (121,'380','Italia',112);
INSERT INTO scr_countryexreg VALUES (122,'388','Jamaica',113);
INSERT INTO scr_countryexreg VALUES (123,'392','Japón',114);
INSERT INTO scr_countryexreg VALUES (124,'832','Jersey',244);
INSERT INTO scr_countryexreg VALUES (125,'400','Jordania',115);
INSERT INTO scr_countryexreg VALUES (126,'398','Kazajistán',116);
INSERT INTO scr_countryexreg VALUES (127,'404','Kenia',117);
INSERT INTO scr_countryexreg VALUES (128,'417','Kirguistán',118);
INSERT INTO scr_countryexreg VALUES (129,'296','Kiribati',119);
INSERT INTO scr_countryexreg VALUES (130,'414','Kuwait',120);
INSERT INTO scr_countryexreg VALUES (131,'418','Laos',121);
INSERT INTO scr_countryexreg VALUES (132,'426','Lesoto',122);
INSERT INTO scr_countryexreg VALUES (133,'428','Letonia',123);
INSERT INTO scr_countryexreg VALUES (134,'422','Líbano',124);
INSERT INTO scr_countryexreg VALUES (135,'430','Liberia',125);
INSERT INTO scr_countryexreg VALUES (136,'434','Libia',126);
INSERT INTO scr_countryexreg VALUES (137,'438','Liechtenstein',127);
INSERT INTO scr_countryexreg VALUES (138,'440','Lituania',128);
INSERT INTO scr_countryexreg VALUES (139,'442','Luxemburgo',129);
INSERT INTO scr_countryexreg VALUES (140,'446','Macao',130);
INSERT INTO scr_countryexreg VALUES (141,'450','Madagascar',132);
INSERT INTO scr_countryexreg VALUES (142,'458','Malasia',133);
INSERT INTO scr_countryexreg VALUES (143,'454','Malaui',134);
INSERT INTO scr_countryexreg VALUES (144,'462','Maldivas',135);
INSERT INTO scr_countryexreg VALUES (145,'466','Malí',136);
INSERT INTO scr_countryexreg VALUES (146,'470','Malta',137);
INSERT INTO scr_countryexreg VALUES (147,'504','Marruecos',140);
INSERT INTO scr_countryexreg VALUES (148,'474','Martinica',142);
INSERT INTO scr_countryexreg VALUES (149,'480','Mauricio',143);
INSERT INTO scr_countryexreg VALUES (150,'478','Mauritania',144);
INSERT INTO scr_countryexreg VALUES (151,'175','Mayotte',145);
INSERT INTO scr_countryexreg VALUES (152,'484','México',146);
INSERT INTO scr_countryexreg VALUES (153,'583','Micronesia',147);
INSERT INTO scr_countryexreg VALUES (154,'498','Moldavia',148);
INSERT INTO scr_countryexreg VALUES (155,'492','Mónaco',149);
INSERT INTO scr_countryexreg VALUES (156,'496','Mongolia',150);
INSERT INTO scr_countryexreg VALUES (157,'499','Montenegro',245);
INSERT INTO scr_countryexreg VALUES (158,'500','Montserrat',151);
INSERT INTO scr_countryexreg VALUES (159,'508','Mozambique',152);
INSERT INTO scr_countryexreg VALUES (160,'516','Namibia',154);
INSERT INTO scr_countryexreg VALUES (161,'520','Nauru',155);
INSERT INTO scr_countryexreg VALUES (162,'524','Nepal',156);
INSERT INTO scr_countryexreg VALUES (163,'558','Nicaragua',157);
INSERT INTO scr_countryexreg VALUES (164,'562','Níger',158);
INSERT INTO scr_countryexreg VALUES (165,'566','Nigeria',159);
INSERT INTO scr_countryexreg VALUES (166,'570','Niue',160);
INSERT INTO scr_countryexreg VALUES (167,'574','Norfolk',161);
INSERT INTO scr_countryexreg VALUES (168,'578','Noruega',162);
INSERT INTO scr_countryexreg VALUES (169,'540','Nueva Caledonia',163);
INSERT INTO scr_countryexreg VALUES (170,'554','Nueva Zelanda',164);
INSERT INTO scr_countryexreg VALUES (171,'512','Omán',165);
INSERT INTO scr_countryexreg VALUES (172,'528','Países Bajos',166);
INSERT INTO scr_countryexreg VALUES (173,'586','Pakistán',167);
INSERT INTO scr_countryexreg VALUES (174,'585','Palaos',168);
INSERT INTO scr_countryexreg VALUES (175,'591','Panamá',170);
INSERT INTO scr_countryexreg VALUES (176,'598','Papúa Nueva Guinea',171);
INSERT INTO scr_countryexreg VALUES (177,'600','Paraguay',172);
INSERT INTO scr_countryexreg VALUES (178,'604','Perú',173);
INSERT INTO scr_countryexreg VALUES (179,'258','Polinesia Francesa',175);
INSERT INTO scr_countryexreg VALUES (180,'616','Polonia',176);
INSERT INTO scr_countryexreg VALUES (181,'620','Portugal',177);
INSERT INTO scr_countryexreg VALUES (182,'630','Puerto Rico',178);
INSERT INTO scr_countryexreg VALUES (183,'826','Reino Unido',180);
INSERT INTO scr_countryexreg VALUES (184,'180','Rep. Dem. del Congo',54);
INSERT INTO scr_countryexreg VALUES (185,'732','República Árabe Saharaui Democrática',185);
INSERT INTO scr_countryexreg VALUES (186,'140','República Centroafricana',43);
INSERT INTO scr_countryexreg VALUES (187,'203','República Checa',45);
INSERT INTO scr_countryexreg VALUES (188,'807','República de Macedonia',131);
INSERT INTO scr_countryexreg VALUES (189,'178','República del Congo',55);
INSERT INTO scr_countryexreg VALUES (190,'214','República Dominicana',65);
INSERT INTO scr_countryexreg VALUES (191,'638','Reunión',181);
INSERT INTO scr_countryexreg VALUES (192,'646','Ruanda',182);
INSERT INTO scr_countryexreg VALUES (193,'642','Rumanía',183);
INSERT INTO scr_countryexreg VALUES (194,'643','Rusia',184);
INSERT INTO scr_countryexreg VALUES (195,'882','Samoa',187);
INSERT INTO scr_countryexreg VALUES (196,'016','Samoa Estadounidense',188);
INSERT INTO scr_countryexreg VALUES (197,'652','San Bartolomé',246);
INSERT INTO scr_countryexreg VALUES (198,'659','San Cristóbal y Nieves',189);
INSERT INTO scr_countryexreg VALUES (199,'674','San Marino',190);
INSERT INTO scr_countryexreg VALUES (200,'663','San Martín',247);
INSERT INTO scr_countryexreg VALUES (201,'666','San Pedro y Miquelón',191);
INSERT INTO scr_countryexreg VALUES (202,'670','San Vicente y las Granadinas',192);
INSERT INTO scr_countryexreg VALUES (203,'654','Santa Helena, A. y T.',193);
INSERT INTO scr_countryexreg VALUES (204,'662','Santa Lucía',194);
INSERT INTO scr_countryexreg VALUES (205,'678','Santo Tomé y Príncipe',195);
INSERT INTO scr_countryexreg VALUES (206,'686','Senegal',196);
INSERT INTO scr_countryexreg VALUES (207,'688','Serbia',248);
INSERT INTO scr_countryexreg VALUES (208,'891','Serbia y Montenegro',197);
INSERT INTO scr_countryexreg VALUES (209,'690','Seychelles',198);
INSERT INTO scr_countryexreg VALUES (210,'694','Sierra Leona',199);
INSERT INTO scr_countryexreg VALUES (211,'702','Singapur',200);
INSERT INTO scr_countryexreg VALUES (212,'760','Siria',201);
INSERT INTO scr_countryexreg VALUES (213,'706','Somalia',202);
INSERT INTO scr_countryexreg VALUES (214,'144','Sri Lanka',203);
INSERT INTO scr_countryexreg VALUES (215,'748','Suazilandia',204);
INSERT INTO scr_countryexreg VALUES (216,'710','Sudáfrica',205);
INSERT INTO scr_countryexreg VALUES (217,'736','Sudán',206);
INSERT INTO scr_countryexreg VALUES (218,'752','Suecia',207);
INSERT INTO scr_countryexreg VALUES (219,'756','Suiza',208);
INSERT INTO scr_countryexreg VALUES (220,'740','Surinam',209);
INSERT INTO scr_countryexreg VALUES (221,'744','Svalbard y Jan Mayen',210);
INSERT INTO scr_countryexreg VALUES (222,'158','Taiwán',212);
INSERT INTO scr_countryexreg VALUES (223,'834','Tanzania',213);
INSERT INTO scr_countryexreg VALUES (224,'762','Tayikistán',214);
INSERT INTO scr_countryexreg VALUES (225,'086','Territorio Británico del Océano Índico',215);
INSERT INTO scr_countryexreg VALUES (226,'260','Territorios Australes Franceses',216);
INSERT INTO scr_countryexreg VALUES (227,'626','Timor Oriental',217);
INSERT INTO scr_countryexreg VALUES (228,'768','Togo',218);
INSERT INTO scr_countryexreg VALUES (229,'772','Tokelau',219);
INSERT INTO scr_countryexreg VALUES (230,'776','Tonga',220);
INSERT INTO scr_countryexreg VALUES (231,'780','Trinidad y Tobago',221);
INSERT INTO scr_countryexreg VALUES (232,'788','Túnez',222);
INSERT INTO scr_countryexreg VALUES (233,'795','Turkmenistán',224);
INSERT INTO scr_countryexreg VALUES (234,'792','Turquía',225);
INSERT INTO scr_countryexreg VALUES (235,'798','Tuvalu',226);
INSERT INTO scr_countryexreg VALUES (236,'804','Ucrania',227);
INSERT INTO scr_countryexreg VALUES (237,'800','Uganda',228);
INSERT INTO scr_countryexreg VALUES (238,'858','Uruguay',229);
INSERT INTO scr_countryexreg VALUES (239,'860','Uzbekistán',230);
INSERT INTO scr_countryexreg VALUES (240,'548','Vanuatu',231);
INSERT INTO scr_countryexreg VALUES (241,'862','Venezuela',232);
INSERT INTO scr_countryexreg VALUES (242,'704','Vietnam',233);
INSERT INTO scr_countryexreg VALUES (243,'876','Wallis y Futuna',236);
INSERT INTO scr_countryexreg VALUES (244,'887','Yemen',237);
INSERT INTO scr_countryexreg VALUES (245,'262','Yibuti',238);
INSERT INTO scr_countryexreg VALUES (246,'894','Zambia',239);
INSERT INTO scr_countryexreg VALUES (247,'716','Zimbabue',240);