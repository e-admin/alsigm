
CREATE TABLE actividad_clasificada (
    actividad character varying(254),
    clasificacion character varying(254),
    emplazamiento text,
    tasas numeric(14,2),
    numexp character varying(30) NOT NULL,
    id integer NOT NULL,
    orden numeric(10,0)
);

CREATE TABLE actividad_no_clasificada (
    actividad character varying(254),
    emplazamiento text,
    tasas numeric(14,2),
    numexp character varying(30) NOT NULL,
    id integer NOT NULL,
    orden numeric(10,0)
);

CREATE TABLE camb_titular_la (
    actividad character varying(254),
    emplazamiento text,
    tasas numeric(14,2),
    numexp character varying(30) NOT NULL,
    id integer NOT NULL,
    fecha_licencia timestamp without time zone,
    fecha_aprobacion timestamp without time zone,
    denominacion character varying(254),
    orden numeric(10,0)
);

CREATE TABLE cert_urbanistico (
    fecha_emision timestamp without time zone,
    situacion character varying(500),
    tecnico_nombre character varying(100),
    tecnico_cargo character varying(50),
    numexp character varying(30) NOT NULL,
    id integer NOT NULL,
    orden numeric(10,0)
);

CREATE TABLE clasificacion_actividades (
    id integer NOT NULL,
    valor character varying(20),
    sustituto character varying(500),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE contrato (
    id integer NOT NULL,
    numexp character varying(30) NOT NULL,
    tipo character varying(100),
    forma_adjudicacion character varying(100),
    presupuesto_maximo numeric(20,2),
    aplicacion character varying(250),
    precio_adjudicacion numeric(20,2),
    plazo_ejecucion integer,
    ud_plazo character varying(1),
    garantia_provisional numeric(20,2),
    garantia_definitiva numeric(20,2),
    clasificacion character varying(500),
    fecha_fin_ejecucion timestamp without time zone,
    fecha_fin_garantia timestamp without time zone,
    orden numeric(10,0)
);

CREATE TABLE datos_tarjeta_minus (
    tipo_tarjeta character varying(100),
    fecha_caducidad timestamp without time zone,
    numexp character varying(30) NOT NULL,
    id integer NOT NULL,
    orden numeric(10,0)
);

CREATE TABLE forma_adjudicacion (
    id integer NOT NULL,
    valor character varying(100),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE lotes (
    id integer NOT NULL,
    numexp character varying(30) NOT NULL,
    nombre character varying(250),
    presupuesto numeric(20,2),
    orden numeric(10,0)
);

CREATE TABLE obra_menor (
    id integer NOT NULL,
    numexp character varying(30) NOT NULL,
    obra_solicitada text,
    situacion_obra character varying(500),
    promotor character varying(100),
    director_obra character varying(100),
    director_ejecucion character varying(100),
    presupuesto_promotor numeric(14,2),
    "presupuesto_técnico_municipal" numeric(14,2),
    autor_proyecto character varying(100),
    uso_obra character varying(200),
    clasificacion_suelo character varying(100),
    cualificacion_suelo character varying(100),
    importe_tasas numeric(14,2),
    importe_icio numeric(14,2),
    total_liquidacion numeric(14,2),
    orden numeric(10,0)
);

CREATE TABLE oferta (
    id_licitador integer,
    importe numeric(20,2),
    plazo integer,
    ud_plazo character varying(1),
    numexp character varying(30) NOT NULL,
    id integer NOT NULL,
    orden numeric(10,0)
);

CREATE TABLE rebaje_acera (
    id integer NOT NULL,
    valor character varying(100),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE reclamacion (
    id integer NOT NULL,
    numexp character varying(30) NOT NULL,
    tipo character varying(64),
    descripcion text,
    destinatario character varying(32),
    aceptada character varying(2),
    orden numeric(10,0)
);

CREATE TABLE sancion (
    instructor character varying(254),
    calificacion character varying(100),
    importe numeric(20,2),
    grado character varying(100),
    infraccion text,
    hechos text,
    actuacion text,
    numexp character varying(30) NOT NULL,
    id integer NOT NULL,
    orden numeric(10,0)
);

CREATE TABLE tipo_contrato (
    id integer NOT NULL,
    valor character varying(100),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE tipo_tecnico (
    id integer NOT NULL,
    valor character varying(10),
    sustituto character varying(64),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE tipo_vado (
    id integer NOT NULL,
    valor character varying(100),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE vado (
    numlicencia character varying(100),
    ubicacion character varying(250),
    tipo character varying(100),
    largoentrada numeric(14,2),
    anchoacera numeric(14,2),
    superficieacera numeric(14,2),
    superficielocal numeric(14,2),
    actividad character varying(250),
    rebaje character varying(100),
    tasas numeric(14,2),
    numexp character varying(30) NOT NULL,
    id integer NOT NULL,
    numvehiculos numeric(20,0),
    orden numeric(10,0)
);

CREATE TABLE visto_bueno (
    id integer NOT NULL,
    numexp character varying(30) NOT NULL,
    task_name character varying(250),
    vb character varying(2),
    task_id integer,
    stage_name character varying(250),
    id_tram_exp integer,
    observaciones text,
    orden numeric(10,0)
);

CREATE TABLE vldtbl_calif_suelo (
    id integer NOT NULL,
    valor character varying(10),
    sustituto character varying(100),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE vldtbl_clasif_suelo (
    id integer NOT NULL,
    valor character varying(10),
    sustituto character varying(100),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE vldtbl_san_calificacion (
    id integer NOT NULL,
    valor character varying(100),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE vldtbl_san_grado (
    id integer NOT NULL,
    valor character varying(100),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE vldtbl_tipo_desti (
    id integer NOT NULL,
    valor character varying(32),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE vldtbl_tipo_reclam (
    id integer NOT NULL,
    valor character varying(64),
    vigente numeric(1,0),
    orden numeric(10,0)
);

CREATE TABLE vldtbl_tipo_tarjeta (
    id integer NOT NULL,
    valor character varying(100),
    vigente numeric(1,0),
    orden numeric(10,0)
);

