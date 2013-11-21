--Actualizacion de la version 8.0.2 a la version 8.0.3

-- Se detecta que en SQLServer estas columnas se generaban como NOT NULL
ALTER TABLE scr_tramunit ALTER COLUMN code_tramunit varchar(21) NULL;
ALTER TABLE scr_tramunit ALTER COLUMN name_tramunit varchar(80) NULL;
ALTER TABLE scr_tramunit ALTER COLUMN code_entity   varchar(21) NULL;
ALTER TABLE scr_tramunit ALTER COLUMN name_entity   varchar(80) NULL;


-- Tablas

-- Tabla para mapeo de los códigos de tipo de transporte de la normativa de intercambio
-- registal de la normativa sicres 3 con la tabla SCR_TT.
CREATE TABLE scr_ttexreg (
    id int NOT NULL,
    code varchar(10) NOT NULL,
    name varchar(31) NOT NULL,
    id_scr_tt int not null
);

-- Secuencias

-- Secuencia para el identificador de la tabla scr_ttexreg
CREATE TABLE scr_ttexreg_id_seq(
  id integer IDENTITY (1, 1) NOT NULL
  );



-- Constraints

-- TABLA scr_tt
ALTER TABLE scr_tt ADD CONSTRAINT pk_scr_scr_tt0 PRIMARY KEY (id);

CREATE UNIQUE INDEX uk_scr_scr_tt0 ON scr_tt(transport);

-- TABLA scr_ttexreg
CREATE UNIQUE INDEX uk_scr_ttexreg0 ON scr_ttexreg(name);
ALTER TABLE scr_ttexreg ADD CONSTRAINT pk_scr_ttexreg0 PRIMARY KEY (id);
ALTER TABLE scr_ttexreg ADD CONSTRAINT fk_scr_ttexreg0 FOREIGN KEY (id_scr_tt) REFERENCES scr_tt(id) ON DELETE CASCADE;
