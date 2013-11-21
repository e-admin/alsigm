-- tabla para mapeo de las entidades registrales del directorio con la tabla scr_orgs
CREATE TABLE scr_entityreg(
    id NUMBER(10) NOT NULL,
    code VARCHAR2(21 CHAR) NOT NULL,
    name VARCHAR2(80 CHAR) NOT NULL,
    id_ofic NUMBER(10) NOT NULL
)
;


ALTER TABLE scr_entityreg
ADD CONSTRAINT pk_scr_entityreg PRIMARY KEY (id)
;

-- secuencia para el identificador de las entidades registral
CREATE SEQUENCE scr_entityreg_id_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;

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
    id NUMBER(10) NOT NULL,
    code_tramunit VARCHAR2(21 CHAR),
    name_tramunit VARCHAR2(80 CHAR),
    code_entity VARCHAR2(21 CHAR),
    name_entity VARCHAR2(80 CHAR),
    id_orgs NUMBER(10) NOT NULL
)
;

-- secuencia para el identificador de las unidades registral
CREATE SEQUENCE scr_tramunit_id_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;

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
    id NUMBER(10) NOT NULL,
    id_arch NUMBER(10)  NOT NULL,
    id_fdr NUMBER(10)  NOT NULL,
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

-- secuencia para scr_exregstate
CREATE SEQUENCE scr_exregstate_id_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;

--tabla para almacenar los registros que se nos han llegado mediante intercambio registral
-- el estado podra ser aceptado, rechazado
CREATE TABLE scr_exreg_in(
    id NUMBER(10) NOT NULL,
    id_arch NUMBER(10)  ,
    id_fdr NUMBER(10)  ,
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

-- tablas de mapeos de codigos del directorio comun de provincias, de ciudades y de paises

--provincias
CREATE TABLE scr_provexreg(
    id NUMBER(10) NOT NULL,
     code VARCHAR2(2 CHAR) NOT NULL,
    name VARCHAR2(80 CHAR) NOT NULL,
    id_prov NUMBER(10) NOT NULL
);
ALTER TABLE scr_provexreg ADD CONSTRAINT pk_scr_provexreg PRIMARY KEY (id);
ALTER TABLE scr_provexreg ADD CONSTRAINT fk_provexregprov FOREIGN KEY (id_prov) REFERENCES scr_prov(id);

--ciudades/municipios
CREATE TABLE scr_citiesexreg(
    id NUMBER(10) NOT NULL,
     code VARCHAR2(5 CHAR) NOT NULL,
    name VARCHAR2(80 CHAR) NOT NULL,
    id_city NUMBER(10) NOT NULL
);
ALTER TABLE scr_citiesexreg ADD CONSTRAINT pk_scr_citiesexreg PRIMARY KEY (id);
ALTER TABLE scr_citiesexreg ADD CONSTRAINT fk_citiesexregcities FOREIGN KEY (id_city) REFERENCES scr_cities(id);

--modificacion del tamanyo del nombre de las personas juridicas
ALTER TABLE scr_pjur MODIFY (name VARCHAR2(80 CHAR));

--nombre del interesado persona fisica
ALTER TABLE scr_pfis MODIFY (surname  VARCHAR2(30 CHAR));
--apellido del interesado persona fisica
ALTER TABLE scr_pfis MODIFY (first_name  VARCHAR2(30 CHAR));
--segundo apellido del interesado persona fisica
ALTER TABLE scr_pfis MODIFY (second_name  VARCHAR2(30 CHAR));

-- direccion domicilio
ALTER TABLE scr_dom MODIFY (address  VARCHAR2(160 CHAR));



--tabla maestro que indica el tipo de validez de los adjuntos considerados documentos electronicos
CREATE TABLE scr_attachment_validity_type(
    id NUMBER(10) NOT NULL,
    name  VARCHAR2 (250 CHAR),
    code VARCHAR2(2 CHAR)
)
;
ALTER TABLE scr_attachment_validity_type
ADD CONSTRAINT pk_attachment_validity_type PRIMARY KEY (id)
;
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (1,'COPIA','01');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (2,'COPIA COMPULSADA','02');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (3,'COPIA ORIGINAL','03');
INSERT INTO scr_attachment_validity_type(id,name,code) VALUES (4,'ORIGINAL','04');

--tabla maestro que indica el tipo de validez de los adjuntos considerados documentos electronicos
CREATE TABLE scr_attachment_document_type(
    id NUMBER(10) NOT NULL,
    name VARCHAR2(250 CHAR),
    code VARCHAR2(2 CHAR)
)
;
INSERT INTO scr_attachment_document_type(id,name,code) VALUES (1,'FORMULARIO','01');
INSERT INTO scr_attachment_document_type(id,name,code) VALUES (2,'DOCUMENTO ADJUNTO','02');
INSERT INTO scr_attachment_document_type(id,name,code) VALUES (3,'FICHERO TECNICO','03');


ALTER TABLE scr_attachment_document_type
ADD CONSTRAINT pk_attachment_document_type PRIMARY KEY (id)
;

--tabla para almacenar informacion de los adjuntos considerados documentos electronicos de la norma sicres3
CREATE TABLE scr_attachment(
    id NUMBER(10) NOT NULL,
    bookid NUMBER(10)  NOT NULL,
    folderid NUMBER(10)  NOT NULL,
    pageid NUMBER(10)  NOT NULL,
    comments VARCHAR2(50 CHAR),
    mime_type VARCHAR2(20 CHAR),
    name  VARCHAR2(80 CHAR),
    code_name VARCHAR2(50 CHAR),
    validity_type NUMBER(10),
    document_type NUMBER(10)
)
;
ALTER TABLE scr_attachment
ADD CONSTRAINT pk_scr_attachment PRIMARY KEY (id)
;

ALTER TABLE scr_attachment ADD CONSTRAINT fk_validity_type_attachment FOREIGN KEY (validity_type) REFERENCES scr_attachment_validity_type(id);
ALTER TABLE scr_attachment ADD CONSTRAINT fk_document_type_attachment FOREIGN KEY (document_type) REFERENCES scr_attachment_document_type(id);


-- secuencia para el identificador de scr_attachment
CREATE SEQUENCE scr_attachment_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;

  --tabla para almacenar informacion de los adjuntos considerados documentos electronicos de la norma sicres3
CREATE TABLE scr_attachment_sign_info(
    id NUMBER(10) NOT NULL,
    id_attachment NUMBER(10)  NOT NULL UNIQUE,
    id_attachment_signed NUMBER(10),
    cert blob,
    signature blob ,
    signature_alg VARCHAR2(2 CHAR), --enumerado
    signature_format VARCHAR2(2 CHAR),--enumerado
    time_signature blob,
    ocsp_validation blob,
    hash_alg VARCHAR2(2 CHAR), --enumerado
    hash blob
)
;

ALTER TABLE scr_attachment_sign_info
ADD CONSTRAINT pk_attachment_sign_info PRIMARY KEY (id)
;

ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_sign_info_attachment FOREIGN KEY (id_attachment) REFERENCES scr_attachment(id);
ALTER TABLE scr_attachment_sign_info ADD CONSTRAINT fk_sign_info_attachment_signed FOREIGN KEY (id_attachment_signed) REFERENCES scr_attachment(id);


-- secuencia para el identificador de scr_attachment
CREATE SEQUENCE scr_attachment_sign_info_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;

-- modificaciones para terceros
-- paises
CREATE TABLE scr_country (
    id NUMBER(10) NOT NULL,
    tmstamp DATE NOT NULL,
    code VARCHAR2(16 CHAR) NOT NULL,
    name VARCHAR2(50 CHAR) NOT NULL
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

--Nuevos paises a partir de la version 8.0.3
INSERT INTO scr_country VALUES (242,sysdate,  'GG','Guernsey');
INSERT INTO scr_country VALUES (243,sysdate,  'IM','Isla de Man');
INSERT INTO scr_country VALUES (244,sysdate,  'JE','Jersey');
INSERT INTO scr_country VALUES (245,sysdate,  'ME','Montenegro');
INSERT INTO scr_country VALUES (246,sysdate,  'BL','San Bartolomé');
INSERT INTO scr_country VALUES (247,sysdate,  'MF','San Martín');
INSERT INTO scr_country VALUES (248,sysdate,  'RS','Serbia');

-- las direcciones fisicas de terceros llevan el pais
ALTER TABLE scr_dom ADD pais VARCHAR2(50 CHAR);

-- representante de interesado
CREATE TABLE scr_repre (
	id NUMBER(10) NOT NULL,
	id_representante NUMBER(10) NOT NULL,
	id_representado NUMBER(10) NOT NULL,
	id_address NUMBER(10),
	name VARCHAR2(95 CHAR) NOT NULL
);

--Actualizacion de la version 8.0.2 a la version 8.0.3

-- Tablas

-- Tabla para mapeo de los códigos de tipo de transporte de la normativa de intercambio
-- registal de la normativa sicres 3 con la tabla SCR_TT.
CREATE TABLE scr_ttexreg (
    id NUMBER(10) NOT NULL,
    code VARCHAR2(10 CHAR) NOT NULL,
    name VARCHAR2(31 CHAR) NOT NULL,
    id_scr_tt NUMBER(10) NOT NULL
);

-- Secuencias

-- Secuencia para el identificador de la tabla scr_ttexreg
CREATE SEQUENCE scr_ttexreg_id_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START WITH 1
  ;

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
    id NUMBER(10) NOT NULL,
    code varchar2(4 CHAR) NOT NULL,
    name varchar2(80 CHAR) NOT NULL,
    id_country NUMBER(10) NOT NULL
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
