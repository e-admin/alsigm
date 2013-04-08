-- Primary keys que faltaban en provincias y ciudades para poder hacer la foreign
ALTER TABLE scr_prov ADD CONSTRAINT pk_prov
	PRIMARY KEY (id)
;

ALTER TABLE scr_cities ADD CONSTRAINT pk_cities
	PRIMARY KEY (id)
;

-- tabla para mapeo de las entidades registrales del directorio con la tabla scr_orgs
CREATE TABLE scr_entityreg(
    id int NOT NULL,
    code varchar(21) NOT NULL,
    name varchar(80) NOT NULL,
    id_orgs int NOT NULL
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
ADD CONSTRAINT fk_entityregorgs
FOREIGN KEY (id_orgs)
REFERENCES scr_orgs(id)
;

-- tabla de mapeo de las unidades de tramitacion del directorio con la tabla scr_orgs
CREATE TABLE scr_tramunit(
    id int NOT NULL,
    code varchar(21) NOT NULL,
    name varchar(80) NOT NULL,
    id_entityreg int NOT NULL,
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
ADD CONSTRAINT fk_tramentityreg
FOREIGN KEY (id_entityreg)
REFERENCES scr_entityreg(id)
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
    id_exchange varchar(33)
    
)
;
ALTER TABLE scr_exreg
ADD CONSTRAINT pk_scr_exreg PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales
CREATE TABLE scr_exreg_id_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );

--tabla para almacenar los registros que se nos han llegado mediante intercambio registral
-- el estado podra ser aceptado, rechazado
CREATE TABLE scr_exregaccept(
    id int NOT NULL,
    id_arch int  NOT NULL,
    id_fdr int  NOT NULL,
    exchange_date datetime NOT NULL,
    state int NOT NULL,
    state_date datetime NOT NULL,
    id_exchange varchar(33)
)
;
ALTER TABLE scr_exregaccept
ADD CONSTRAINT pk_scr_exregaccept PRIMARY KEY (id)
;

-- secuencia para el identificador de los intercambios registrales aceptados
CREATE TABLE scr_exregaccept_id_seq(
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


