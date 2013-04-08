-- Primary keys en provincias y ciudades que no estaba para poder hacer foreings
-- TABLA scr_prov
ALTER TABLE scr_prov ADD CONSTRAINT pk_prov0 PRIMARY KEY (id);

-- TABLA scr_cities
ALTER TABLE scr_cities ADD CONSTRAINT pk_cities0 PRIMARY KEY (id);

-- tabla para mapeo de las entidades registrales del directorio con la tabla scr_orgs
CREATE TABLE scr_entityreg(
    id integer NOT NULL,
    code character varying(21) NOT NULL,
    name character varying(80) NOT NULL,
    id_orgs integer NOT NULL
)
;


ALTER TABLE scr_entityreg
ADD CONSTRAINT pk_scr_entityreg PRIMARY KEY (id)
;

-- secuencia para el identificador de las entidades registral
CREATE SEQUENCE scr_entityreg_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  ;

ALTER TABLE scr_entityreg
ADD CONSTRAINT fk_entityregorgs
FOREIGN KEY (id_orgs)
REFERENCES scr_orgs(id)
;

-- tabla de mapeo de las unidades de tramitacion del directorio con la tabla scr_orgs
CREATE TABLE scr_tramunit(
    id integer NOT NULL,
    code character varying(21) NOT NULL,
    name character varying(80) NOT NULL,
    id_entityreg integer NOT NULL,
    id_orgs integer NOT NULL
)
;

-- secuencia para el identificador de las unidades registral
CREATE SEQUENCE scr_tramunit_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
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
    id integer NOT NULL,
    id_arch integer  NOT NULL,
    id_fdr integer  NOT NULL,
    id_ofic integer  NOT NULL,
    type_orig integer NOT NULL,
    exchange_date timestamp without time zone,
    state integer NOT NULL,
    state_date timestamp without time zone NOT NULL,
    id_exchange character varying(33)
    
)
;
ALTER TABLE scr_exreg
ADD CONSTRAINT pk_scr_exreg PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales
CREATE SEQUENCE scr_exreg_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  ;

--tabla para almacenar los registros que se nos han llegado mediante intercambio registral
-- el estado podra ser aceptado, rechazado
CREATE TABLE scr_exregaccept(
    id integer NOT NULL,
    id_arch integer  NOT NULL,
    id_fdr integer  NOT NULL,
    exchange_date timestamp without time zone NOT NULL,
    state integer NOT NULL,
    state_date timestamp without time zone NOT NULL,
    id_exchange character varying(33)
)
;
ALTER TABLE scr_exregaccept
ADD CONSTRAINT pk_scr_exregaccept PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales aceptados
CREATE SEQUENCE scr_exregaccept_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  ;

-- tablas de mapeos de codigos del directorio comun de provincias, de ciudades y de paises

--provincias
CREATE TABLE scr_provexreg(
    id integer NOT NULL,
     code character varying(2) NOT NULL,
    name character varying(80) NOT NULL,
    id_prov integer NOT NULL
);
ALTER TABLE scr_provexreg ADD CONSTRAINT pk_scr_provexreg PRIMARY KEY (id);
ALTER TABLE scr_provexreg ADD CONSTRAINT fk_provexregprov FOREIGN KEY (id_prov) REFERENCES scr_prov(id);

--ciudades/municipios
CREATE TABLE scr_citiesexreg(
    id integer NOT NULL,
     code character varying(5) NOT NULL,
    name character varying(80) NOT NULL,
    id_city integer NOT NULL
);
ALTER TABLE scr_citiesexreg ADD CONSTRAINT pk_scr_citiesexreg PRIMARY KEY (id);
ALTER TABLE scr_citiesexreg ADD CONSTRAINT fk_citiesexregcities FOREIGN KEY (id_city) REFERENCES scr_cities(id);

--modificacion del tamanyo del nombre de las personas juridicas
ALTER TABLE scr_pjur ALTER name TYPE character varying(80); 

--nombre del interesado persona fisica
ALTER TABLE scr_pfis ALTER surname TYPE character varying(30);
--apellido del interesado persona fisica
ALTER TABLE scr_pfis ALTER first_name TYPE character varying(30);
--segundo apellido del interesado persona fisica
ALTER TABLE scr_pfis ALTER second_name TYPE character varying(30);

-- direccion domicilio
ALTER TABLE scr_dom ALTER address TYPE character varying(160);

 
  
