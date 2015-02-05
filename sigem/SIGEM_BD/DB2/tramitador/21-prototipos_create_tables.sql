--
-- Tablas de los pilotos
--
CREATE TABLE sgl_obras_menores (
    id INTEGER NOT NULL,
    numexp VARCHAR(30),
    tipo_objeto VARCHAR(15),
    tipo_localizador VARCHAR(15),
    localizador VARCHAR(15),
    actuacion_casco_historico VARCHAR(2),
    tipo_finca VARCHAR(15),
    tipo_suelo VARCHAR(15),
    ubicacion_inmueble VARCHAR(512),
    referencia_catastral VARCHAR(20),
    localizacion VARCHAR(15),
    descripcion CLOB,
    presupuesto_total NUMERIC(15,2),
    fecha_inicio TIMESTAMP,
    fecha_terminacion TIMESTAMP,
    ocupacion_via_publica VARCHAR(2)
);
CREATE TABLE sgq_quejas (
    id INTEGER NOT NULL,
    numexp VARCHAR(30),
    id_organo_objeto VARCHAR(64),
    organo_objeto VARCHAR(512),
    tipo_objeto VARCHAR(10),
    localizador_objeto VARCHAR(64),
    asunto CLOB,
    tipo_organo_destino VARCHAR(8),
    id_organo_informe VARCHAR(64),
    organo_informe VARCHAR(512)
);
CREATE TABLE sgs_subvenciones (
    id INTEGER NOT NULL,
    numexp VARCHAR(30),
    id_pcd INTEGER,
    anio_presupuestario NUMERIC(4,0),
    tipo_subvencion VARCHAR(8),
    fecha_convocatoria TIMESTAMP,
    denominacion_proyecto VARCHAR(120),
    resumen_proyecto CLOB,
    fecha_inicio_ejecucion TIMESTAMP,
    fecha_fin_ejecucion TIMESTAMP,
    duracion_proyecto NUMERIC(2,0),
    colectivo_destino VARCHAR(64),
    importe_solicitado NUMERIC(13,2),
    importe_concedido NUMERIC(13,2),
    prevision_gastos CLOB,
    importe_gastos NUMERIC(10,2),
    total_gastos NUMERIC(10,2),
    aportacion_ayto NUMERIC(13,2),
    aportacion_entidad NUMERIC(13,2),
    otras_aportaciones NUMERIC(13,2),
    total_aportaciones NUMERIC(13,2),
    finalidad_subvencion CLOB,
    observaciones CLOB
);



-------------------
-- OBRAS MENORES --
-------------------

--
-- Tablas de validación
--

-- Tabla de validación para Tipos de Suelo
CREATE TABLE spac_tbl_010 (
    id INTEGER NOT NULL,
    valor VARCHAR(8),
    sustituto VARCHAR(64),
    vigente SMALLINT,
	orden int
);


-- Tabla de validación para Tipos de Finca
CREATE TABLE spac_tbl_011 (
  	id INTEGER NOT NULL,
  	valor VARCHAR(8),
  	sustituto VARCHAR(64),
  	vigente SMALLINT,
	orden int
);


-- Tabla de validación para Localización de Obras
CREATE TABLE spac_tbl_012 (
  	id INTEGER NOT NULL,
  	valor VARCHAR(8),
  	sustituto VARCHAR(64),
  	vigente SMALLINT,
	orden int
);


------------------
-- SUBVENCIONES --
------------------

--
-- Tablas de validación
--

-- Tabla de validación para Tipos de Subvención
CREATE TABLE spac_tbl_013 (
  	id INTEGER NOT NULL,
  	valor VARCHAR(8),
  	sustituto VARCHAR(64),
  	vigente SMALLINT,
	orden int
);