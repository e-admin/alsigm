-- TABLA scr_prov
ALTER TABLE scr_prov ADD CONSTRAINT pk_prov PRIMARY KEY (id);

-- TABLA scr_cities
ALTER TABLE scr_cities ADD CONSTRAINT pk_cities PRIMARY KEY (id);


-- tabla para mapeo de las entidades registrales del directorio con la tabla scr_orgs
CREATE TABLE scr_entityreg(
    id integer NOT NULL,
    code varchar(21) NOT NULL,
    name varchar(80) NOT NULL,
    id_orgs integer NOT NULL
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
ADD CONSTRAINT fk_entityregorgs
FOREIGN KEY (id_orgs)
REFERENCES scr_orgs(id)
;

-- tabla de mapeo de las unidades de tramitacion del directorio con la tabla scr_orgs
CREATE TABLE scr_tramunit(
    id integer NOT NULL,
    code varchar(21) NOT NULL,
    name varchar(80) NOT NULL,
    id_entityreg integer NOT NULL,
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
    exchange_date timestamp,
    state integer NOT NULL,
    state_date timestamp NOT NULL,
    id_exchange varchar(33)
    
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

--tabla para almacenar los registros que se nos han llegado mediante intercambio registral
-- el estado podra ser aceptado, rechazado
CREATE TABLE scr_exregaccept(
    id integer NOT NULL,
    id_arch integer  NOT NULL,
    id_fdr integer  NOT NULL,
    exchange_date timestamp NOT NULL,
    state integer NOT NULL,
    state_date timestamp NOT NULL,
    id_exchange varchar(33)
)
;
ALTER TABLE scr_exregaccept
ADD CONSTRAINT pk_scr_exregaccept PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales aceptados
CREATE SEQUENCE scr_exregaccept_id_seq
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

 
  
