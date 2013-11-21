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
drop sequence scr_attachment_sign_info_seq;
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

--
-- scr_regint
--
ALTER TABLE scr_regint ALTER COLUMN name SET DATA TYPE varchar(95);

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


--tabla maestro que indica el tipo de validez de los adjuntos considerados documentos electronicos
CREATE TABLE scr_attachment_document_type(
    id integer NOT NULL,
    name  varchar(250),
    code varchar(2)
)
;



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

--Este script debe de ejecutarse en la BD de registro

CREATE INDEX iuserdata_ix_id ON iuserdata (id);

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
