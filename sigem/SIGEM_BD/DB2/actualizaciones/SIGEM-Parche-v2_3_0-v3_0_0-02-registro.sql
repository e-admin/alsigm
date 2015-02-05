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

--scr_regint
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

--Tabla para los datos adicionales de usuarios.
CREATE TABLE iuserdata
(
  id INTEGER NOT NULL,
  cargo varchar(256),
  tfno_movil varchar(16),
  id_certificado varchar(256),
  email varchar(256),
  nombre varchar(256),
  apellidos varchar(256)
);

ALTER TABLE iuserdata ADD CONSTRAINT pk_iuserdata PRIMARY KEY (id) ;

CREATE INDEX iuserdata_ix_id ON iuserdata (id);