/* TABLES OR VIEWS --------------------------------------- */
CREATE TABLE DIR_OFICINA (
	CODIGO_OFICINA                    	character varying(9)         NOT NULL,
	DENOMINACION_OFICINA              	character varying(300)       NOT NULL,
	CODIGO_UNIDAD_RESPONSABLE       	character varying(9)         NULL,
	EXTERNO_FUENTE            			character varying(40)        NULL,
	PROVINCIA	            			character varying(100)       NULL,
	LOCALIDAD	            			character varying(100)       NULL,
	DIRECCION	            			character varying(300)       NULL
);


CREATE TABLE DIR_UNIDAD_ORGANICA (
	CODIGO_UNIDAD_ORGANICA                		character varying(9)         NOT NULL,
	NOMBRE_UNIDAD_ORGANICA               		character varying(300)       NOT NULL,
	CODIGO_UNIDAD_SUP_JERARQUICA     			character varying(9)         NULL,
	DENOM_UNIDAD_SUP_JERARQUICA    				character varying(300) 		 NULL,
	CODIGO_EXTERNO_FUENTE             			character varying(40)        NULL
);

CREATE TABLE DIR_ESTADO_ACTUALIZACION (
	ID 											bigint 							NOT NULL,
	FECHA_ACTUALIZACION							timestamp without time zone     NOT NULL,
	ESTADO										character varying(10)			NOT NULL
);

CREATE TABLE DIR_RELAC_UNID_ORG_OFIC(
	CODIGO_OFICINA                    			character varying(9)         NOT NULL,
	DENOMINACION_OFICINA              			character varying(300)       NOT NULL,
	CODIGO_UNIDAD_ORGANICA              		character varying(9)         NOT NULL,
	DENOM_UNIDAD_ORGANICA		           		character varying(300)       NOT NULL
);

/* PRIMARY KEYS --------------------------------------- */
ALTER TABLE DIR_OFICINA ADD CONSTRAINT PK_DIR_OFICINA PRIMARY KEY (CODIGO_OFICINA);

ALTER TABLE DIR_UNIDAD_ORGANICA ADD CONSTRAINT PK_DIR_UNIDAD_ORGANICA PRIMARY KEY (CODIGO_UNIDAD_ORGANICA);

ALTER TABLE DIR_ESTADO_ACTUALIZACION ADD CONSTRAINT PK_ESTADO_ACTUALIZACION PRIMARY KEY(ID);
