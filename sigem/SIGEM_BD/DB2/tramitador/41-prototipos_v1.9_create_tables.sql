--
-- Tablas
--

CREATE TABLE actividad_clasificada (
    actividad VARCHAR(254),
    clasificacion VARCHAR(254),
    emplazamiento CLOB,
    tasas NUMERIC(14,2),
    numexp VARCHAR(30) NOT NULL,
    id integer NOT NULL,
    orden INTEGER
);

CREATE TABLE actividad_no_clasificada (
    actividad VARCHAR(254),
    emplazamiento CLOB,
    tasas NUMERIC(14,2),
    numexp VARCHAR(30) NOT NULL,
    id integer NOT NULL,
    orden INTEGER
);

CREATE TABLE camb_titular_la (
    actividad VARCHAR(254),
    emplazamiento CLOB,
    tasas NUMERIC(14,2),
    numexp VARCHAR(30) NOT NULL,
    id integer NOT NULL,
    fecha_licencia TIMESTAMP,
    fecha_aprobacion TIMESTAMP,
    denominacion VARCHAR(254),
    orden INTEGER
);

CREATE TABLE cert_urbanistico (
    fecha_emision TIMESTAMP,
    situacion VARCHAR(500),
    tecnico_nombre VARCHAR(100),
    tecnico_cargo VARCHAR(50),
    numexp VARCHAR(30) NOT NULL,
    id integer NOT NULL,
    orden INTEGER
);

CREATE TABLE clasificacion_actividades (
    id integer NOT NULL,
    valor VARCHAR(20),
    sustituto VARCHAR(500),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE contrato (
    id integer NOT NULL,
    numexp VARCHAR(30) NOT NULL,
    tipo VARCHAR(100),
    forma_adjudicacion VARCHAR(100),
    presupuesto_maximo NUMERIC(20,2),
    aplicacion VARCHAR(250),
    precio_adjudicacion NUMERIC(20,2),
    plazo_ejecucion integer,
    ud_plazo VARCHAR(1),
    garantia_provisional NUMERIC(20,2),
    garantia_definitiva NUMERIC(20,2),
    clasificacion VARCHAR(500),
    fecha_fin_ejecucion TIMESTAMP,
    fecha_fin_garantia TIMESTAMP,
    orden INTEGER
);

CREATE TABLE datos_tarjeta_minus (
    tipo_tarjeta VARCHAR(100),
    fecha_caducidad TIMESTAMP,
    numexp VARCHAR(30) NOT NULL,
    id integer NOT NULL,
    orden INTEGER
);

CREATE TABLE forma_adjudicacion (
    id integer NOT NULL,
    valor VARCHAR(100),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE lotes (
    id integer NOT NULL,
    numexp VARCHAR(30) NOT NULL,
    nombre VARCHAR(250),
    presupuesto NUMERIC(20,2),
    orden INTEGER
);

CREATE TABLE obra_menor (
    id integer NOT NULL,
    numexp VARCHAR(30) NOT NULL,
    obra_solicitada CLOB,
    situacion_obra VARCHAR(500),
    promotor VARCHAR(100),
    director_obra VARCHAR(100),
    director_ejecucion VARCHAR(100),
    presupuesto_promotor NUMERIC(14,2),
    "presupuesto_técnico_municipal" NUMERIC(14,2),
    autor_proyecto VARCHAR(100),
    uso_obra VARCHAR(200),
    clasificacion_suelo VARCHAR(100),
    cualificacion_suelo VARCHAR(100),
    importe_tasas NUMERIC(14,2),
    importe_icio NUMERIC(14,2),
    total_liquidacion NUMERIC(14,2),
    orden INTEGER
);

CREATE TABLE oferta (
    id_licitador integer,
    importe NUMERIC(20,2),
    plazo integer,
    ud_plazo VARCHAR(1),
    numexp VARCHAR(30) NOT NULL,
    id integer NOT NULL,
    orden INTEGER
);

CREATE TABLE rebaje_acera (
    id integer NOT NULL,
    valor VARCHAR(100),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE reclamacion (
    id integer NOT NULL,
    numexp VARCHAR(30) NOT NULL,
    tipo VARCHAR(64),
    descripcion CLOB,
    destinatario VARCHAR(32),
    aceptada VARCHAR(2),
    orden INTEGER
);

CREATE TABLE sancion (
    instructor VARCHAR(254),
    calificacion VARCHAR(100),
    importe NUMERIC(20,2),
    grado VARCHAR(100),
    infraccion CLOB,
    hechos CLOB,
    actuacion CLOB,
    numexp VARCHAR(30) NOT NULL,
    id integer NOT NULL,
    orden INTEGER
);

CREATE TABLE tipo_contrato (
    id integer NOT NULL,
    valor VARCHAR(100),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE tipo_tecnico (
    id integer NOT NULL,
    valor VARCHAR(10),
    sustituto VARCHAR(64),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE tipo_vado (
    id integer NOT NULL,
    valor VARCHAR(100),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE vado (
    numlicencia VARCHAR(100),
    ubicacion VARCHAR(250),
    tipo VARCHAR(100),
    largoentrada NUMERIC(14,2),
    anchoacera NUMERIC(14,2),
    superficieacera NUMERIC(14,2),
    superficielocal NUMERIC(14,2),
    actividad VARCHAR(250),
    rebaje VARCHAR(100),
    tasas NUMERIC(14,2),
    numexp VARCHAR(30) NOT NULL,
    id integer NOT NULL,
    numvehiculos NUMERIC(20,0),
    orden INTEGER
);

CREATE TABLE visto_bueno (
    id integer NOT NULL,
    numexp VARCHAR(30) NOT NULL,
    task_name VARCHAR(250),
    vb VARCHAR(2),
    task_id integer,
    stage_name VARCHAR(250),
    id_tram_exp integer,
    observaciones CLOB,
    orden INTEGER
);

CREATE TABLE vldtbl_calif_suelo (
    id integer NOT NULL,
    valor VARCHAR(10),
    sustituto VARCHAR(100),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE vldtbl_clasif_suelo (
    id integer NOT NULL,
    valor VARCHAR(10),
    sustituto VARCHAR(100),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE vldtbl_san_calificacion (
    id integer NOT NULL,
    valor VARCHAR(100),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE vldtbl_san_grado (
    id integer NOT NULL,
    valor VARCHAR(100),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE vldtbl_tipo_desti (
    id integer NOT NULL,
    valor VARCHAR(32),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE vldtbl_tipo_reclam (
    id integer NOT NULL,
    valor VARCHAR(64),
    vigente NUMERIC(1,0),
    orden INTEGER
);

CREATE TABLE vldtbl_tipo_tarjeta (
    id integer NOT NULL,
    valor VARCHAR(100),
    vigente NUMERIC(1,0),
    orden INTEGER
);