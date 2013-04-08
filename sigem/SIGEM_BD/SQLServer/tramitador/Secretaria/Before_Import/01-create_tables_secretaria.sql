
--Contador
---==============================================================================

CREATE TABLE SEC_CONTADORES(
    ID int PRIMARY KEY ,
    anio int not null,
    contador int ,
    tipo int not null,
    formato varchar (32));


--Tablas de validación usadas por la vista de Organo/Cargo
--==============================================================================


CREATE TABLE  SEC_VLDTBL_ORGANO_GOBIERNO(
    ID int PRIMARY KEY,
    VALOR varchar(2),
    SUSTITUTO varchar(64),
    VIGENTE int NULL,
	ORDEN int
);


CREATE TABLE  SEC_VLDTBL_CARGO_ORGANO(
     ID int PRIMARY KEY,
    VALOR varchar(2),
    SUSTITUTO varchar(64),
    VIGENTE int NULL,
	ORDEN int
);




--Tablas de validación usadas por la vista de Area/Cargo area
--==============================================================================


CREATE TABLE  SEC_VLDTBL_AREA_GOBIERNO(
    ID int PRIMARY KEY,
    VALOR varchar(2),
    SUSTITUTO varchar(64),
    VIGENTE int NULL,
	ORDEN int
);




CREATE TABLE  SEC_VLDTBL_CARGO_AREA(
    ID int PRIMARY KEY,
    VALOR varchar(2),
    SUSTITUTO varchar(64),
    VIGENTE int NULL,
	ORDEN int
);

