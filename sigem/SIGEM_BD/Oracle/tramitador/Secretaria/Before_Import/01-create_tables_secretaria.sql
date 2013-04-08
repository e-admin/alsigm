
--Contador
---==============================================================================

CREATE TABLE SEC_CONTADORES(
    ID NUMBER PRIMARY KEY ,
    anio NUMBER not null,
    contador NUMBER ,
    tipo NUMBER not null,
    formato VARCHAR2 (32 CHAR));


--Tablas de validación usadas por la vista de Organo/Cargo
--==============================================================================


CREATE TABLE  SEC_VLDTBL_ORGANO_GOBIERNO(
    ID NUMBER PRIMARY KEY,
    VALOR VARCHAR2(2 CHAR),
    SUSTITUTO VARCHAR2(64 CHAR),
    VIGENTE NUMBER(1) ,
	ORDEN NUMBER(10)
);


CREATE TABLE  SEC_VLDTBL_CARGO_ORGANO(
     ID NUMBER PRIMARY KEY,
    VALOR VARCHAR2(2 CHAR),
    SUSTITUTO VARCHAR2(64 CHAR),
    VIGENTE NUMBER(1),
	ORDEN NUMBER(10)
);




--Tablas de validación usadas por la vista de Area/Cargo area
--==============================================================================


CREATE TABLE  SEC_VLDTBL_AREA_GOBIERNO(
    ID NUMBER PRIMARY KEY,
    VALOR VARCHAR2(2 CHAR),
    SUSTITUTO VARCHAR2(64 CHAR),
    VIGENTE NUMBER (1),
	ORDEN NUMBER(10)
);




CREATE TABLE  SEC_VLDTBL_CARGO_AREA(
   ID NUMBER PRIMARY KEY,
    VALOR VARCHAR2(2 CHAR),
    SUSTITUTO VARCHAR2(64 CHAR),
    VIGENTE NUMBER (1),
	ORDEN NUMBER(10)
);

