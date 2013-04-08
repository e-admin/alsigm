--
-- Tablas de los pilotos
--
CREATE TABLE sgl_obras_menores (
    id integer NOT NULL,
    numexp character varying(30),
    tipo_objeto character varying(15),
    tipo_localizador character varying(15),
    localizador character varying(15),
    actuacion_casco_historico character varying(2),
    tipo_finca character varying(15),
    tipo_suelo character varying(15),
    ubicacion_inmueble character varying(512),
    referencia_catastral character varying(20),
    localizacion character varying(15),
    descripcion text,
    presupuesto_total numeric(15,2),
    fecha_inicio timestamp,
    fecha_terminacion timestamp,
    ocupacion_via_publica character varying(2)
);
CREATE TABLE sgq_quejas (
    id integer NOT NULL,
    numexp character varying(30),
    id_organo_objeto character varying(64),
    organo_objeto character varying(512),
    tipo_objeto character(10),
    localizador_objeto character varying(64),
    asunto text,
    tipo_organo_destino character varying(8),
    id_organo_informe character varying(64),
    organo_informe character varying(512)
);
CREATE TABLE sgs_subvenciones (
    id integer NOT NULL,
    numexp character varying(30),
    id_pcd integer,
    anio_presupuestario numeric(4,0),
    tipo_subvencion character varying(8),
    fecha_convocatoria timestamp,
    denominacion_proyecto character varying(120),
    resumen_proyecto text,
    fecha_inicio_ejecucion timestamp,
    fecha_fin_ejecucion timestamp,
    duracion_proyecto numeric(2,0),
    colectivo_destino character varying(64),
    importe_solicitado numeric(13,2),
    importe_concedido numeric(13,2),
    prevision_gastos text,
    importe_gastos numeric(10,2),
    total_gastos numeric(10,2),
    aportacion_ayto numeric(13,2),
    aportacion_entidad numeric(13,2),
    otras_aportaciones numeric(13,2),
    total_aportaciones numeric(13,2),
    finalidad_subvencion text,
    observaciones text
);


-------------------
-- OBRAS MENORES --
-------------------

--
-- Tablas de validación
--

-- Tabla de validación para Tipos de Suelo

CREATE TABLE spac_tbl_010 (
    id integer NOT NULL,
    valor character varying(8),
    sustituto character varying(64),
    vigente numeric(1),
	orden numeric(10)
);

-- Tabla de validación para Tipos de Finca
CREATE TABLE spac_tbl_011 (
  	id integer NOT NULL,
  	valor character varying(8),
  	sustituto character varying(64),
  	vigente numeric(1),
	orden numeric(10)
);


-- Tabla de validación para Localización de Obras
CREATE TABLE spac_tbl_012 (
  	id integer NOT NULL,
  	valor character varying(8),
  	sustituto character varying(64),
  	vigente numeric(1),
	orden numeric(10)
);


------------------
-- SUBVENCIONES --
------------------

--
-- Tablas de validación
--

-- Tabla de validación para Tipos de Subvención
CREATE TABLE spac_tbl_013 (
  	id integer NOT NULL,
  	valor character varying(8),
  	sustituto character varying(64),
  	vigente numeric(1),
	orden numeric(10)
);