
--Contador
---==============================================================================

CREATE TABLE SEC_CONTADORES(
    ID integer PRIMARY KEY ,
    anio integer not null,
    contador integer ,
    tipo integer not null,
    formato character varying (32));


--Tablas de validación usadas por la vista de Organo/Cargo
--==============================================================================


CREATE TABLE  SEC_VLDTBL_ORGANO_GOBIERNO(
    ID integer PRIMARY KEY,
    VALOR character varying(2),
    SUSTITUTO character varying(64),
    VIGENTE  numeric(1),
	ORDEN numeric(10)
);


CREATE TABLE  SEC_VLDTBL_CARGO_ORGANO(
     ID integer PRIMARY KEY,
    VALOR character varying(2),
    SUSTITUTO character varying(64),
    VIGENTE numeric(1),
	ORDEN numeric(10)
);




--Tablas de validación usadas por la vista de Area/Cargo area
--==============================================================================


CREATE TABLE  SEC_VLDTBL_AREA_GOBIERNO(
    ID integer PRIMARY KEY,
    VALOR character varying(2),
    SUSTITUTO character varying(64),
    VIGENTE  numeric(1),
	ORDEN  numeric(10)
);




CREATE TABLE  SEC_VLDTBL_CARGO_AREA(
    ID integer PRIMARY KEY,
    VALOR character varying(2),
    SUSTITUTO character varying(64),
    VIGENTE numeric(1),
	ORDEN numeric(10)
);

