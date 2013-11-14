-- tabla para mapeo de las entidades registrales del directorio con la tabla scr_orgs
CREATE TABLE scr_entityreg(
    id integer NOT NULL,
    code varchar(21) NOT NULL,
    name varchar(80) NOT NULL,
    id_ofic integer NOT NULL
)
;


ALTER TABLE scr_entityreg
ADD CONSTRAINT pk_scr_entityreg PRIMARY KEY (id)
;

-- secuencia para el identificador de las entidades registral
CREATE SEQUENCE scr_entityreg_id_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

ALTER TABLE scr_entityreg
ADD CONSTRAINT fk_entityofic
FOREIGN KEY (id_ofic)
REFERENCES scr_ofic(id)
;

-- tabla de mapeo de las unidades de tramitacion del directorio con la tabla scr_orgs
-- una unidad organizativa estara mapeada contra una unidad de tramitacion del dco
-- una unidad organizativa estara mapeada contura una entidad registral de tramitacion
-- para que se pueda realizar finalmente el intercambio el dato necesario sera al menos el de la entidad registrar
CREATE TABLE scr_tramunit(
    id integer NOT NULL,
    code_tramunit varchar(21) ,
    name_tramunit varchar(80),
    code_entity varchar(21),
    name_entity varchar(80),
    id_orgs integer NOT NULL
)
;

-- secuencia para el identificador de las unidades registral
CREATE SEQUENCE scr_tramunit_id_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

ALTER TABLE scr_tramunit
ADD CONSTRAINT pk_scr_tramunit PRIMARY KEY (id)
;

ALTER TABLE scr_tramunit
ADD CONSTRAINT fk_tramunitgorgs
FOREIGN KEY (id_orgs)
REFERENCES scr_orgs(id)
;

CREATE UNIQUE INDEX u_code_tramunit ON scr_tramunit  (code_tramunit);
--ALTER TABLE scr_tramunit
--  ADD CONSTRAINT u_code_tramunit UNIQUE (code_tramunit)
--;

--tabla para almacenar los registros que se han enviado a un intercambio registral
--el estado podra ser pendiente de envio,enviado,enviado erroneo,aceptado,rechazado,anulado
-- type_orig identifica si el registro es de un libro de entrada o uno de salida
CREATE TABLE scr_exreg(
    id integer NOT NULL,
    id_arch integer  NOT NULL,
    id_fdr integer  NOT NULL,
    id_ofic integer  NOT NULL,
    type_orig integer NOT NULL,
    exchange_date timestamp,
    state integer NOT NULL,
    state_date timestamp NOT NULL,
    id_exchange_sir varchar(33),
    id_exchange integer NOT NULL,
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
CREATE SEQUENCE scr_exreg_id_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

--tabla para almacenar el historial de los intercambios de salida
CREATE TABLE scr_exregstate (
    id integer NOT NULL,
    id_exreg integer NOT NULL,
    state integer NOT NULL,
    state_date timestamp NOT NULL,
    username VARCHAR(32) NOT NULL,
    comments varchar(4000)
)
;
ALTER TABLE scr_exregstate
ADD CONSTRAINT pk_scr_exregstate PRIMARY KEY (id)
;

-- secuencia para scr_exregstate
CREATE SEQUENCE scr_exregstate_id_seq
 START WITH 1
    INCREMENT BY 1
    CACHE 20
  ;


--tabla para almacenar los registros que se nos han llegado mediante intercambio registral
-- el estado podra ser aceptado, rechazado
CREATE TABLE scr_exreg_in(
    id integer NOT NULL,
    id_arch integer ,
    id_fdr integer  ,
    id_ofic integer  NOT NULL,
    exchange_date timestamp NOT NULL,
    state integer NOT NULL,
    state_date timestamp NOT NULL,
    id_exchange_sir VARCHAR(33) NOT NULL,
    id_exchange integer NOT NULL,
    username VARCHAR(32) NOT NULL,
    code_tramunit VARCHAR(21) ,
    name_tramunit VARCHAR(80) ,
    code_entity VARCHAR(21) NOT NULL,
    name_entity VARCHAR(80) NOT NULL,
    num_reg_orig VARCHAR(20) NOT NULL,
    contacto_orig VARCHAR(160),
    comments varchar(4000)
)
;
ALTER TABLE scr_exreg_in
ADD CONSTRAINT pk_scr_exreg_in PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales aceptados
CREATE SEQUENCE scr_exreg_in_id_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

  -- tablas de mapeos de codigos del directorio comun de provincias, de ciudades y de paises

--provincias
CREATE TABLE scr_provexreg(
    id integer NOT NULL,
     code varchar(2) NOT NULL,
    name varchar(80) NOT NULL,
    id_prov integer NOT NULL
);
ALTER TABLE scr_provexreg ADD CONSTRAINT pk_scr_provexreg PRIMARY KEY (id);
ALTER TABLE scr_provexreg ADD CONSTRAINT fk_provexregprov FOREIGN KEY (id_prov) REFERENCES scr_prov(id);

--ciudades/municipios
CREATE TABLE scr_citiesexreg(
    id integer NOT NULL,
     code varchar(5) NOT NULL,
    name varchar(80) NOT NULL,
    id_city integer NOT NULL
);
ALTER TABLE scr_citiesexreg ADD CONSTRAINT pk_scr_citiesexreg PRIMARY KEY (id);
ALTER TABLE scr_citiesexreg ADD CONSTRAINT fk_citiesexregci FOREIGN KEY (id_city) REFERENCES scr_cities(id);

--modificacion del tamanyo del nombre de las personas juridicas
ALTER TABLE scr_pjur ALTER COLUMN name SET DATA TYPE varchar(80);

--nombre del interesado persona fisica
ALTER TABLE scr_pfis ALTER COLUMN surname SET DATA TYPE varchar(30);
--apellido del interesado persona fisica
ALTER TABLE scr_pfis ALTER COLUMN first_name SET DATA TYPE varchar(30);
--segundo apellido del interesado persona fisica
ALTER TABLE scr_pfis ALTER COLUMN second_name SET DATA TYPE varchar(30);

-- direccion domicilio
ALTER TABLE scr_dom ALTER COLUMN address SET DATA TYPE varchar(160);


--tabla maestro que indica el tipo de validez de los adjuntos considerados documentos electronicos
CREATE TABLE scr_attachment_validity_type(
    id integer NOT NULL,
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
    id integer NOT NULL,
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
    id integer NOT NULL,
    bookid integer  NOT NULL,
    folderid integer  NOT NULL,
    pageid integer  NOT NULL,
    comments varchar(50),
    mime_type varchar(20),
    name  varchar(80),
    code_name varchar(50),
    validity_type integer,
    document_type integer
)
;
ALTER TABLE scr_attachment
ADD CONSTRAINT pk_scr_attachment PRIMARY KEY (id)
;

ALTER TABLE scr_attachment ADD CONSTRAINT fk_validity_type_attachment FOREIGN KEY (validity_type) REFERENCES scr_attachment_validity_type(id);
ALTER TABLE scr_attachment ADD CONSTRAINT fk_document_type_attachment FOREIGN KEY (document_type) REFERENCES scr_attachment_document_type(id);


-- secuencia para el identificador de scr_attachment
CREATE SEQUENCE scr_attachment_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

  --tabla para almacenar informacion de los adjuntos considerados documentos electronicos de la norma sicres3
CREATE TABLE scr_attachment_sign_info(
    id integer NOT NULL,
    id_attachment integer  NOT NULL UNIQUE,
    id_attachment_signed integer,
    cert blob(307200),
    signature blob(307200) ,
    signature_alg varchar(2), --enumerado
    signature_format varchar(2),--enumerado
    time_signature blob(307200),
    ocsp_validation blob(307200),
    hash_alg varchar(2), --enumerado
    hash blob(307200)
)
;

ALTER TABLE scr_attachment_sign_info
ADD CONSTRAINT pk_scr_attachment_sign_info PRIMARY KEY (id)
;

ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_sign_info_attachment FOREIGN KEY (id_attachment) REFERENCES scr_attachment(id);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_sign_info_attachment_signed FOREIGN KEY (id_attachment_signed) REFERENCES scr_attachment(id);


-- secuencia para el identificador de scr_attachment
CREATE SEQUENCE scr_attachment_sign_info_seq
    START WITH 1
    INCREMENT BY 1
    CACHE 20;

-- modificaciones para terceros
-- paises
CREATE TABLE scr_country (
    id integer NOT NULL,
    tmstamp timestamp NOT NULL,
    code varchar(16) NOT NULL,
    name varchar(50) NOT NULL
);

-- Datos para la tabla scr_country
INSERT INTO scr_country VALUES (1, CURRENT TIMESTAMP, 'AF',  'Afganistán');
INSERT INTO scr_country VALUES (2, CURRENT TIMESTAMP, 'AX',  'Islas Gland');
INSERT INTO scr_country VALUES (3, CURRENT TIMESTAMP, 'AL',  'Albania');
INSERT INTO scr_country VALUES (4, CURRENT TIMESTAMP, 'DE',  'Alemania');
INSERT INTO scr_country VALUES (5, CURRENT TIMESTAMP, 'AD',  'Andorra');
INSERT INTO scr_country VALUES (6, CURRENT TIMESTAMP, 'AO',  'Angola');
INSERT INTO scr_country VALUES (7, CURRENT TIMESTAMP, 'AI',  'Anguilla');
INSERT INTO scr_country VALUES (8, CURRENT TIMESTAMP, 'AQ',  'Antártida');
INSERT INTO scr_country VALUES (9, CURRENT TIMESTAMP, 'AG',  'Antigua y Barbuda');
INSERT INTO scr_country VALUES (10,CURRENT TIMESTAMP,  'AN', 'Antillas Holandesas');
INSERT INTO scr_country VALUES (11,CURRENT TIMESTAMP,  'SA', 'Arabia Saudí');
INSERT INTO scr_country VALUES (12,CURRENT TIMESTAMP,  'DZ', 'Argelia');
INSERT INTO scr_country VALUES (13,CURRENT TIMESTAMP,  'AR', 'Argentina');
INSERT INTO scr_country VALUES (14,CURRENT TIMESTAMP,  'AM', 'Armenia');
INSERT INTO scr_country VALUES (15,CURRENT TIMESTAMP,  'AW', 'Aruba');
INSERT INTO scr_country VALUES (16,CURRENT TIMESTAMP,  'AU', 'Australia');
INSERT INTO scr_country VALUES (17,CURRENT TIMESTAMP,  'AT', 'Austria');
INSERT INTO scr_country VALUES (18,CURRENT TIMESTAMP,  'AZ', 'Azerbaiyán');
INSERT INTO scr_country VALUES (19,CURRENT TIMESTAMP,  'BS', 'Bahamas');
INSERT INTO scr_country VALUES (20,CURRENT TIMESTAMP,  'BH', 'Bahréin');
INSERT INTO scr_country VALUES (21,CURRENT TIMESTAMP,  'BD', 'Bangladesh');
INSERT INTO scr_country VALUES (22,CURRENT TIMESTAMP,  'BB', 'Barbados');
INSERT INTO scr_country VALUES (23,CURRENT TIMESTAMP,  'BY', 'Bielorrusia');
INSERT INTO scr_country VALUES (24,CURRENT TIMESTAMP,  'BE', 'Bélgica');
INSERT INTO scr_country VALUES (25,CURRENT TIMESTAMP,  'BZ', 'Belice');
INSERT INTO scr_country VALUES (26,CURRENT TIMESTAMP,  'BJ', 'Benin');
INSERT INTO scr_country VALUES (27,CURRENT TIMESTAMP,  'BM', 'Bermudas');
INSERT INTO scr_country VALUES (28,CURRENT TIMESTAMP,  'BT', 'Bhután');
INSERT INTO scr_country VALUES (29,CURRENT TIMESTAMP,  'BO', 'Bolivia');
INSERT INTO scr_country VALUES (30,CURRENT TIMESTAMP,  'BA', 'Bosnia y Herzegovina');
INSERT INTO scr_country VALUES (31,CURRENT TIMESTAMP,  'BW', 'Botsuana');
INSERT INTO scr_country VALUES (32,CURRENT TIMESTAMP,  'BV', 'Isla Bouvet');
INSERT INTO scr_country VALUES (33,CURRENT TIMESTAMP,  'BR', 'Brasil');
INSERT INTO scr_country VALUES (34,CURRENT TIMESTAMP,  'BN', 'Brunéi');
INSERT INTO scr_country VALUES (35,CURRENT TIMESTAMP,  'BG', 'Bulgaria');
INSERT INTO scr_country VALUES (36,CURRENT TIMESTAMP,  'BF', 'Burkina Faso');
INSERT INTO scr_country VALUES (37,CURRENT TIMESTAMP,  'BI', 'Burundi');
INSERT INTO scr_country VALUES (38,CURRENT TIMESTAMP,  'CV', 'Cabo Verde');
INSERT INTO scr_country VALUES (39,CURRENT TIMESTAMP,  'KY', 'Islas Caimán');
INSERT INTO scr_country VALUES (40,CURRENT TIMESTAMP,  'KH', 'Camboya');
INSERT INTO scr_country VALUES (41,CURRENT TIMESTAMP,  'CM', 'Camerún');
INSERT INTO scr_country VALUES (42,CURRENT TIMESTAMP,  'CA', 'Canadá');
INSERT INTO scr_country VALUES (43,CURRENT TIMESTAMP,  'CF', 'República Centroafricana');
INSERT INTO scr_country VALUES (44,CURRENT TIMESTAMP,  'TD', 'Chad');
INSERT INTO scr_country VALUES (45,CURRENT TIMESTAMP,  'CZ', 'República Checa');
INSERT INTO scr_country VALUES (46,CURRENT TIMESTAMP,  'CL', 'Chile');
INSERT INTO scr_country VALUES (47,CURRENT TIMESTAMP,  'CN', 'China');
INSERT INTO scr_country VALUES (48,CURRENT TIMESTAMP,  'CY', 'Chipre');
INSERT INTO scr_country VALUES (49,CURRENT TIMESTAMP,  'CX', 'Isla de Navidad');
INSERT INTO scr_country VALUES (50,CURRENT TIMESTAMP,  'VA', 'Ciudad del Vaticano');
INSERT INTO scr_country VALUES (51,CURRENT TIMESTAMP,  'CC', 'Islas Cocos');
INSERT INTO scr_country VALUES (52,CURRENT TIMESTAMP,  'CO', 'Colombia');
INSERT INTO scr_country VALUES (53,CURRENT TIMESTAMP,  'KM', 'Comoras');
INSERT INTO scr_country VALUES (54,CURRENT TIMESTAMP,  'CD', 'República Democrática del Congo');
INSERT INTO scr_country VALUES (55,CURRENT TIMESTAMP,  'CG', 'Congo');
INSERT INTO scr_country VALUES (56,CURRENT TIMESTAMP,  'CK', 'Islas Cook');
INSERT INTO scr_country VALUES (57,CURRENT TIMESTAMP,  'KP', 'Corea del Norte');
INSERT INTO scr_country VALUES (58,CURRENT TIMESTAMP,  'KR', 'Corea del Sur');
INSERT INTO scr_country VALUES (59,CURRENT TIMESTAMP,  'CI', 'Costa de Marfil');
INSERT INTO scr_country VALUES (60,CURRENT TIMESTAMP,  'CR', 'Costa Rica');
INSERT INTO scr_country VALUES (61,CURRENT TIMESTAMP,  'HR', 'Croacia');
INSERT INTO scr_country VALUES (62,CURRENT TIMESTAMP,  'CU', 'Cuba');
INSERT INTO scr_country VALUES (63,CURRENT TIMESTAMP,  'DK', 'Dinamarca');
INSERT INTO scr_country VALUES (64,CURRENT TIMESTAMP,  'DM', 'Dominica');
INSERT INTO scr_country VALUES (65,CURRENT TIMESTAMP,  'DO', 'República Dominicana');
INSERT INTO scr_country VALUES (66,CURRENT TIMESTAMP,  'EC', 'Ecuador');
INSERT INTO scr_country VALUES (67,CURRENT TIMESTAMP,  'EG', 'Egipto');
INSERT INTO scr_country VALUES (68,CURRENT TIMESTAMP,  'SV', 'El Salvador');
INSERT INTO scr_country VALUES (69,CURRENT TIMESTAMP,  'AE', 'Emiratos Árabes Unidos');
INSERT INTO scr_country VALUES (70,CURRENT TIMESTAMP,  'ER', 'Eritrea');
INSERT INTO scr_country VALUES (71,CURRENT TIMESTAMP,  'SK', 'Eslovaquia');
INSERT INTO scr_country VALUES (72,CURRENT TIMESTAMP,  'SI', 'Eslovenia');
INSERT INTO scr_country VALUES (73,CURRENT TIMESTAMP,  'ES', 'España');
INSERT INTO scr_country VALUES (74,CURRENT TIMESTAMP,  'UM', 'Islas ultramarinas de Estados Unidos');
INSERT INTO scr_country VALUES (75,CURRENT TIMESTAMP,  'US', 'Estados Unidos');
INSERT INTO scr_country VALUES (76,CURRENT TIMESTAMP,  'EE', 'Estonia');
INSERT INTO scr_country VALUES (77,CURRENT TIMESTAMP,  'ET', 'Etiopía');
INSERT INTO scr_country VALUES (78,CURRENT TIMESTAMP,  'FO', 'Islas Feroe');
INSERT INTO scr_country VALUES (79,CURRENT TIMESTAMP,  'PH', 'Filipinas');
INSERT INTO scr_country VALUES (80,CURRENT TIMESTAMP,  'FI', 'Finlandia');
INSERT INTO scr_country VALUES (81,CURRENT TIMESTAMP,  'FJ', 'Fiyi');
INSERT INTO scr_country VALUES (82,CURRENT TIMESTAMP,  'FR', 'Francia');
INSERT INTO scr_country VALUES (83,CURRENT TIMESTAMP,  'GA', 'Gabón');
INSERT INTO scr_country VALUES (84,CURRENT TIMESTAMP,  'GM', 'Gambia');
INSERT INTO scr_country VALUES (85,CURRENT TIMESTAMP,  'GE', 'Georgia');
INSERT INTO scr_country VALUES (86,CURRENT TIMESTAMP,  'GS', 'Islas Georgias del Sur y Sandwich del Sur');
INSERT INTO scr_country VALUES (87,CURRENT TIMESTAMP,  'GH', 'Ghana');
INSERT INTO scr_country VALUES (88,CURRENT TIMESTAMP,  'GI', 'Gibraltar');
INSERT INTO scr_country VALUES (89,CURRENT TIMESTAMP,  'GD', 'Granada');
INSERT INTO scr_country VALUES (90,CURRENT TIMESTAMP,  'GR', 'Grecia');
INSERT INTO scr_country VALUES (91,CURRENT TIMESTAMP,  'GL', 'Groenlandia');
INSERT INTO scr_country VALUES (92,CURRENT TIMESTAMP,  'GP', 'Guadalupe');
INSERT INTO scr_country VALUES (93,CURRENT TIMESTAMP,  'GU', 'Guam');
INSERT INTO scr_country VALUES (94,CURRENT TIMESTAMP,  'GT', 'Guatemala');
INSERT INTO scr_country VALUES (95,CURRENT TIMESTAMP,  'GF', 'Guayana Francesa');
INSERT INTO scr_country VALUES (96,CURRENT TIMESTAMP,  'GN', 'Guinea');
INSERT INTO scr_country VALUES (97,CURRENT TIMESTAMP,  'GQ', 'Guinea Ecuatorial');
INSERT INTO scr_country VALUES (98,CURRENT TIMESTAMP,  'GW', 'Guinea-Bissau');
INSERT INTO scr_country VALUES (99,CURRENT TIMESTAMP,  'GY', 'Guyana');
INSERT INTO scr_country VALUES (100,CURRENT TIMESTAMP, 'HT', 'Haití');
INSERT INTO scr_country VALUES (101,CURRENT TIMESTAMP,  'HM','Islas Heard y McDonald');
INSERT INTO scr_country VALUES (102,CURRENT TIMESTAMP,  'HN','Honduras');
INSERT INTO scr_country VALUES (103,CURRENT TIMESTAMP,  'HK','Hong Kong');
INSERT INTO scr_country VALUES (104,CURRENT TIMESTAMP,  'HU','Hungría');
INSERT INTO scr_country VALUES (105,CURRENT TIMESTAMP,  'IN','India');
INSERT INTO scr_country VALUES (106,CURRENT TIMESTAMP,  'ID','Indonesia');
INSERT INTO scr_country VALUES (107,CURRENT TIMESTAMP,  'IR','Irán');
INSERT INTO scr_country VALUES (108,CURRENT TIMESTAMP,  'IQ','Iraq');
INSERT INTO scr_country VALUES (109,CURRENT TIMESTAMP,  'IE','Irlanda');
INSERT INTO scr_country VALUES (110,CURRENT TIMESTAMP,  'IS','Islandia');
INSERT INTO scr_country VALUES (111,CURRENT TIMESTAMP,  'IL','Israel');
INSERT INTO scr_country VALUES (112,CURRENT TIMESTAMP,  'IT','Italia');
INSERT INTO scr_country VALUES (113,CURRENT TIMESTAMP,  'JM','Jamaica');
INSERT INTO scr_country VALUES (114,CURRENT TIMESTAMP,  'JP','Japón');
INSERT INTO scr_country VALUES (115,CURRENT TIMESTAMP,  'JO','Jordania');
INSERT INTO scr_country VALUES (116,CURRENT TIMESTAMP,  'KZ','Kazajstán');
INSERT INTO scr_country VALUES (117,CURRENT TIMESTAMP,  'KE','Kenia');
INSERT INTO scr_country VALUES (118,CURRENT TIMESTAMP,  'KG','Kirguistán');
INSERT INTO scr_country VALUES (119,CURRENT TIMESTAMP,  'KI','Kiribati');
INSERT INTO scr_country VALUES (120,CURRENT TIMESTAMP,  'KW','Kuwait');
INSERT INTO scr_country VALUES (121,CURRENT TIMESTAMP,  'LA','Laos');
INSERT INTO scr_country VALUES (122,CURRENT TIMESTAMP,  'LS','Lesotho');
INSERT INTO scr_country VALUES (123,CURRENT TIMESTAMP,  'LV','Letonia');
INSERT INTO scr_country VALUES (124,CURRENT TIMESTAMP,  'LB','Líbano');
INSERT INTO scr_country VALUES (125,CURRENT TIMESTAMP,  'LR','Liberia');
INSERT INTO scr_country VALUES (126,CURRENT TIMESTAMP,  'LY','Libia');
INSERT INTO scr_country VALUES (127,CURRENT TIMESTAMP,  'LI','Liechtenstein');
INSERT INTO scr_country VALUES (128,CURRENT TIMESTAMP,  'LT','Lituania');
INSERT INTO scr_country VALUES (129,CURRENT TIMESTAMP,  'LU','Luxemburgo');
INSERT INTO scr_country VALUES (130,CURRENT TIMESTAMP,  'MO','Macao');
INSERT INTO scr_country VALUES (131,CURRENT TIMESTAMP,  'MK','ARY Macedonia');
INSERT INTO scr_country VALUES (132,CURRENT TIMESTAMP,  'MG','Madagascar');
INSERT INTO scr_country VALUES (133,CURRENT TIMESTAMP,  'MY','Malasia');
INSERT INTO scr_country VALUES (134,CURRENT TIMESTAMP,  'MW','Malawi');
INSERT INTO scr_country VALUES (135,CURRENT TIMESTAMP,  'MV','Maldivas');
INSERT INTO scr_country VALUES (136,CURRENT TIMESTAMP,  'ML','Malí');
INSERT INTO scr_country VALUES (137,CURRENT TIMESTAMP,  'MT','Malta');
INSERT INTO scr_country VALUES (138,CURRENT TIMESTAMP,  'FK','Islas Malvinas');
INSERT INTO scr_country VALUES (139,CURRENT TIMESTAMP,  'MP','Islas Marianas del Norte');
INSERT INTO scr_country VALUES (140,CURRENT TIMESTAMP,  'MA','Marruecos');
INSERT INTO scr_country VALUES (141,CURRENT TIMESTAMP,  'MH','Islas Marshall');
INSERT INTO scr_country VALUES (142,CURRENT TIMESTAMP,  'MQ','Martinica');
INSERT INTO scr_country VALUES (143,CURRENT TIMESTAMP,  'MU','Mauricio');
INSERT INTO scr_country VALUES (144,CURRENT TIMESTAMP,  'MR','Mauritania');
INSERT INTO scr_country VALUES (145,CURRENT TIMESTAMP,  'YT','Mayotte');
INSERT INTO scr_country VALUES (146,CURRENT TIMESTAMP,  'MX','México');
INSERT INTO scr_country VALUES (147,CURRENT TIMESTAMP,  'FM','Micronesia');
INSERT INTO scr_country VALUES (148,CURRENT TIMESTAMP,  'MD','Moldavia');
INSERT INTO scr_country VALUES (149,CURRENT TIMESTAMP,  'MC','Mónaco');
INSERT INTO scr_country VALUES (150,CURRENT TIMESTAMP,  'MN','Mongolia');
INSERT INTO scr_country VALUES (151,CURRENT TIMESTAMP,  'MS','Montserrat');
INSERT INTO scr_country VALUES (152,CURRENT TIMESTAMP,  'MZ','Mozambique');
INSERT INTO scr_country VALUES (153,CURRENT TIMESTAMP,  'MM','Myanmar');
INSERT INTO scr_country VALUES (154,CURRENT TIMESTAMP,  'NA','Namibia');
INSERT INTO scr_country VALUES (155,CURRENT TIMESTAMP,  'NR','Nauru');
INSERT INTO scr_country VALUES (156,CURRENT TIMESTAMP,  'NP','Nepal');
INSERT INTO scr_country VALUES (157,CURRENT TIMESTAMP,  'NI','Nicaragua');
INSERT INTO scr_country VALUES (158,CURRENT TIMESTAMP,  'NE','Níger');
INSERT INTO scr_country VALUES (159,CURRENT TIMESTAMP,  'NG','Nigeria');
INSERT INTO scr_country VALUES (160,CURRENT TIMESTAMP,  'NU','Niue');
INSERT INTO scr_country VALUES (161,CURRENT TIMESTAMP,  'NF','Isla Norfolk');
INSERT INTO scr_country VALUES (162,CURRENT TIMESTAMP,  'NO','Noruega');
INSERT INTO scr_country VALUES (163,CURRENT TIMESTAMP,  'NC','Nueva Caledonia');
INSERT INTO scr_country VALUES (164,CURRENT TIMESTAMP,  'NZ','Nueva Zelanda');
INSERT INTO scr_country VALUES (165,CURRENT TIMESTAMP,  'OM','Omán');
INSERT INTO scr_country VALUES (166,CURRENT TIMESTAMP,  'NL','Países Bajos');
INSERT INTO scr_country VALUES (167,CURRENT TIMESTAMP,  'PK','Pakistán');
INSERT INTO scr_country VALUES (168,CURRENT TIMESTAMP,  'PW','Palau');
INSERT INTO scr_country VALUES (169,CURRENT TIMESTAMP,  'PS','Palestina');
INSERT INTO scr_country VALUES (170,CURRENT TIMESTAMP,  'PA','Panamá');
INSERT INTO scr_country VALUES (171,CURRENT TIMESTAMP,  'PG','Papúa Nueva Guinea');
INSERT INTO scr_country VALUES (172,CURRENT TIMESTAMP,  'PY','Paraguay');
INSERT INTO scr_country VALUES (173,CURRENT TIMESTAMP,  'PE','Perú');
INSERT INTO scr_country VALUES (174,CURRENT TIMESTAMP,  'PN','Islas Pitcairn');
INSERT INTO scr_country VALUES (175,CURRENT TIMESTAMP,  'PF','Polinesia Francesa');
INSERT INTO scr_country VALUES (176,CURRENT TIMESTAMP,  'PL','Polonia');
INSERT INTO scr_country VALUES (177,CURRENT TIMESTAMP,  'PT','Portugal');
INSERT INTO scr_country VALUES (178,CURRENT TIMESTAMP,  'PR','Puerto Rico');
INSERT INTO scr_country VALUES (179,CURRENT TIMESTAMP,  'QA','Qatar');
INSERT INTO scr_country VALUES (180,CURRENT TIMESTAMP,  'GB','Reino Unido');
INSERT INTO scr_country VALUES (181,CURRENT TIMESTAMP,  'RE','Reunión');
INSERT INTO scr_country VALUES (182,CURRENT TIMESTAMP,  'RW','Ruanda');
INSERT INTO scr_country VALUES (183,CURRENT TIMESTAMP,  'RO','Rumania');
INSERT INTO scr_country VALUES (184,CURRENT TIMESTAMP,  'RU','Rusia');
INSERT INTO scr_country VALUES (185,CURRENT TIMESTAMP,  'EH','Sahara Occidental');
INSERT INTO scr_country VALUES (186,CURRENT TIMESTAMP,  'SB','Islas Salomón');
INSERT INTO scr_country VALUES (187,CURRENT TIMESTAMP,  'WS','Samoa');
INSERT INTO scr_country VALUES (188,CURRENT TIMESTAMP,  'AS','Samoa Americana');
INSERT INTO scr_country VALUES (189,CURRENT TIMESTAMP,  'KN','San Cristóbal y Nevis');
INSERT INTO scr_country VALUES (190,CURRENT TIMESTAMP,  'SM','San Marino');
INSERT INTO scr_country VALUES (191,CURRENT TIMESTAMP,  'PM','San Pedro y Miquelón');
INSERT INTO scr_country VALUES (192,CURRENT TIMESTAMP,  'VC','San Vicente y las Granadinas');
INSERT INTO scr_country VALUES (193,CURRENT TIMESTAMP,  'SH','Santa Helena');
INSERT INTO scr_country VALUES (194,CURRENT TIMESTAMP,  'LC','Santa Lucía');
INSERT INTO scr_country VALUES (195,CURRENT TIMESTAMP,  'ST','Santo Tomé y Príncipe');
INSERT INTO scr_country VALUES (196,CURRENT TIMESTAMP,  'SN','Senegal');
INSERT INTO scr_country VALUES (197,CURRENT TIMESTAMP,  'CS','Serbia y Montenegro');
INSERT INTO scr_country VALUES (198,CURRENT TIMESTAMP,  'SC','Seychelles');
INSERT INTO scr_country VALUES (199,CURRENT TIMESTAMP,  'SL','Sierra Leona');
INSERT INTO scr_country VALUES (200,CURRENT TIMESTAMP,  'SG','Singapur');
INSERT INTO scr_country VALUES (201,CURRENT TIMESTAMP,  'SY','Siria');
INSERT INTO scr_country VALUES (202,CURRENT TIMESTAMP,  'SO','Somalia');
INSERT INTO scr_country VALUES (203,CURRENT TIMESTAMP,  'LK','Sri Lanka');
INSERT INTO scr_country VALUES (204,CURRENT TIMESTAMP,  'SZ','Suazilandia');
INSERT INTO scr_country VALUES (205,CURRENT TIMESTAMP,  'ZA','Sudáfrica');
INSERT INTO scr_country VALUES (206,CURRENT TIMESTAMP,  'SD','Sudán');
INSERT INTO scr_country VALUES (207,CURRENT TIMESTAMP,  'SE','Suecia');
INSERT INTO scr_country VALUES (208,CURRENT TIMESTAMP,  'CH','Suiza');
INSERT INTO scr_country VALUES (209,CURRENT TIMESTAMP,  'SR','Surinam');
INSERT INTO scr_country VALUES (210,CURRENT TIMESTAMP,  'SJ','Svalbard y Jan Mayen');
INSERT INTO scr_country VALUES (211,CURRENT TIMESTAMP,  'TH','Tailandia');
INSERT INTO scr_country VALUES (212,CURRENT TIMESTAMP,  'TW','Taiwán');
INSERT INTO scr_country VALUES (213,CURRENT TIMESTAMP,  'TZ','Tanzania');
INSERT INTO scr_country VALUES (214,CURRENT TIMESTAMP,  'TJ','Tayikistán');
INSERT INTO scr_country VALUES (215,CURRENT TIMESTAMP,  'IO','Territorio Británico del Océano Índico');
INSERT INTO scr_country VALUES (216,CURRENT TIMESTAMP,  'TF','Territorios Australes Franceses');
INSERT INTO scr_country VALUES (217,CURRENT TIMESTAMP,  'TL','Timor Oriental');
INSERT INTO scr_country VALUES (218,CURRENT TIMESTAMP,  'TG','Togo');
INSERT INTO scr_country VALUES (219,CURRENT TIMESTAMP,  'TK','Tokelau');
INSERT INTO scr_country VALUES (220,CURRENT TIMESTAMP,  'TO','Tonga');
INSERT INTO scr_country VALUES (221,CURRENT TIMESTAMP,  'TT','Trinidad y Tobago');
INSERT INTO scr_country VALUES (222,CURRENT TIMESTAMP,  'TN','Túnez');
INSERT INTO scr_country VALUES (223,CURRENT TIMESTAMP,  'TC','Islas Turcas y Caicos');
INSERT INTO scr_country VALUES (224,CURRENT TIMESTAMP,  'TM','Turkmenistán');
INSERT INTO scr_country VALUES (225,CURRENT TIMESTAMP,  'TR','Turquía');
INSERT INTO scr_country VALUES (226,CURRENT TIMESTAMP,  'TV','Tuvalu');
INSERT INTO scr_country VALUES (227,CURRENT TIMESTAMP,  'UA','Ucrania');
INSERT INTO scr_country VALUES (228,CURRENT TIMESTAMP,  'UG','Uganda');
INSERT INTO scr_country VALUES (229,CURRENT TIMESTAMP,  'UY','Uruguay');
INSERT INTO scr_country VALUES (230,CURRENT TIMESTAMP,  'UZ','Uzbekistán');
INSERT INTO scr_country VALUES (231,CURRENT TIMESTAMP,  'VU','Vanuatu');
INSERT INTO scr_country VALUES (232,CURRENT TIMESTAMP,  'VE','Venezuela');
INSERT INTO scr_country VALUES (233,CURRENT TIMESTAMP,  'VN','Vietnam');
INSERT INTO scr_country VALUES (234,CURRENT TIMESTAMP,  'VG','Islas Vírgenes Británicas');
INSERT INTO scr_country VALUES (235,CURRENT TIMESTAMP,  'VI','Islas Vírgenes de los Estados Unidos');
INSERT INTO scr_country VALUES (236,CURRENT TIMESTAMP,  'WF','Wallis y Futuna');
INSERT INTO scr_country VALUES (237,CURRENT TIMESTAMP,  'YE','Yemen');
INSERT INTO scr_country VALUES (238,CURRENT TIMESTAMP, 'DJ','Yibuti');
INSERT INTO scr_country VALUES (239,CURRENT TIMESTAMP,  'ZM','Zambia');
INSERT INTO scr_country VALUES (240,CURRENT TIMESTAMP,  'ZW','Zimbabue');

--Nuevos paises a partir de la version 8.0.3
INSERT INTO scr_country VALUES (242,CURRENT TIMESTAMP,  'GG','Guernsey');
INSERT INTO scr_country VALUES (243,CURRENT TIMESTAMP,  'IM','Isla de Man');
INSERT INTO scr_country VALUES (244,CURRENT TIMESTAMP,  'JE','Jersey');
INSERT INTO scr_country VALUES (245,CURRENT TIMESTAMP,  'ME','Montenegro');
INSERT INTO scr_country VALUES (246,CURRENT TIMESTAMP,  'BL','San Bartolomé');
INSERT INTO scr_country VALUES (247,CURRENT TIMESTAMP,  'MF','San Martín');
INSERT INTO scr_country VALUES (248,CURRENT TIMESTAMP,  'RS','Serbia');

-- las direcciones fisicas de terceros llevan el pais
ALTER TABLE scr_dom ADD COLUMN pais varchar(50);

-- representante de interesado
CREATE TABLE scr_repre (
	id integer NOT NULL,
	id_representante integer NOT NULL,
	id_representado integer NOT NULL,
	id_address integer,
	name varchar(95) NOT NULL
);

--Actualizacion de la version 8.0.2 a la version 8.0.3

-- Tablas

-- Tabla para mapeo de los códigos de tipo de transporte de la normativa de intercambio
-- registal de la normativa sicres 3 con la tabla SCR_TT.
CREATE TABLE scr_ttexreg (
    id integer NOT NULL,
    code varchar(10) NOT NULL,
    name varchar(31) NOT NULL,
    id_scr_tt integer not null
);

-- Secuencias

-- Secuencia para el identificador de la tabla scr_ttexreg
CREATE SEQUENCE scr_ttexreg_id_seq
	START WITH 1
    INCREMENT BY 1
    CACHE 20;


-- Constraints

-- TABLA scr_tt
ALTER TABLE scr_tt ADD CONSTRAINT pk_scr_tt0 PRIMARY KEY (id);
CREATE UNIQUE INDEX scr_tt0 ON scr_tt(transport);

-- TABLA scr_ttexreg
CREATE UNIQUE INDEX scr_ttexreg0 ON scr_ttexreg (name);

ALTER TABLE scr_ttexreg ADD CONSTRAINT pk_scr_ttexreg0 PRIMARY KEY (id);
ALTER TABLE scr_ttexreg ADD CONSTRAINT fk_scr_ttexreg0 FOREIGN KEY (id_scr_tt) REFERENCES scr_tt(id) ON DELETE CASCADE;

-- Nueva tabla mapeo country para intercambio registral con la tabla scr_country
CREATE TABLE scr_countryexreg(
    id integer NOT NULL,
    code varchar(4) NOT NULL,
    name varchar(80) NOT NULL,
    id_country integer NOT NULL
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
