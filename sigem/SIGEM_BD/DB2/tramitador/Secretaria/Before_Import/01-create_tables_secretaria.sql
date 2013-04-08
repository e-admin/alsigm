
--Contador
---==============================================================================

CREATE TABLE SEC_CONTADORES(
    ID integer not null ,
    anio integer not null,
    contador integer ,
    tipo integer not null,
    formato varchar (32));


--Tablas de validación usadas por la vista de Organo/Cargo
--==============================================================================


CREATE TABLE  SEC_VLDTBL_ORGANO_GOBIERNO(
    ID integer not null,
    VALOR VARCHAR(2),
    SUSTITUTO VARCHAR(64),
    VIGENTE  SMALLINT,
	ORDEN SMALLINT
);




CREATE TABLE  SEC_VLDTBL_CARGO_ORGANO(
     ID integer not null,
    VALOR VARCHAR(2),
    SUSTITUTO VARCHAR(64),
    VIGENTE SMALLINT,
	ORDEN SMALLINT
);




--Tablas de validación usadas por la vista de Area/Cargo area
--==============================================================================


CREATE TABLE  SEC_VLDTBL_AREA_GOBIERNO(
    ID integer not null,
    VALOR VARCHAR(2),
    SUSTITUTO VARCHAR(64),
    VIGENTE  SMALLINT,
	ORDEN  SMALLINT
);




CREATE TABLE  SEC_VLDTBL_CARGO_AREA(
    ID integer not null,
    VALOR VARCHAR(2),
    SUSTITUTO VARCHAR(64),
    VIGENTE SMALLINT,
	ORDEN SMALLINT
);



