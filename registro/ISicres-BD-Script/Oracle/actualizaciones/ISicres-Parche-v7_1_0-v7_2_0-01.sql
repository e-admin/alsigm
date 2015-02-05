-- Primary keys en provincias y ciudades que no estaba para poder hacer foreings
-- TABLA scr_prov
ALTER TABLE scr_prov ADD CONSTRAINT pk_prov PRIMARY KEY (id);

-- TABLA scr_cities
ALTER TABLE scr_cities ADD CONSTRAINT pk_cities PRIMARY KEY (id);

-- tabla para mapeo de las entidades registrales del directorio con la tabla scr_orgs
CREATE TABLE scr_entityreg(
    id NUMBER(10) NOT NULL,
    code VARCHAR2(21 CHAR) NOT NULL,
    name VARCHAR2(80 CHAR) NOT NULL,
    id_orgs NUMBER(10) NOT NULL
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
ADD CONSTRAINT fk_entityregorgs
FOREIGN KEY (id_orgs)
REFERENCES scr_orgs(id)
;

-- tabla de mapeo de las unidades de tramitacion del directorio con la tabla scr_orgs
CREATE TABLE scr_tramunit(
    id NUMBER(10) NOT NULL,
    code VARCHAR2(21 CHAR) NOT NULL,
    name VARCHAR2(80 CHAR) NOT NULL,
    id_entityreg NUMBER(10) NOT NULL,
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
ADD CONSTRAINT fk_tramentityreg
FOREIGN KEY (id_entityreg)
REFERENCES scr_entityreg(id)
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
    id_exchange VARCHAR2(33 CHAR)

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

--tabla para almacenar los registros que se nos han llegado mediante intercambio registral
-- el estado podra ser aceptado, rechazado
CREATE TABLE scr_exregaccept(
    id NUMBER(10) NOT NULL,
    id_arch NUMBER(10)  NOT NULL,
    id_fdr NUMBER(10)  NOT NULL,
    exchange_date DATE NOT NULL,
    state NUMBER(10) NOT NULL,
    state_date DATE NOT NULL,
    id_exchange VARCHAR2(33 CHAR)
)
;
ALTER TABLE scr_exregaccept
ADD CONSTRAINT pk_scr_exregaccept PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales aceptados
CREATE SEQUENCE scr_exregaccept_id_seq
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