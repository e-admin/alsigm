--Se borra el contenido de las tablas
delete from DIR_OFICINA;
delete from DIR_UNIDAD_ORGANICA;
delete from DIR_ESTADO_ACTUALIZACION;

-- Se crea la tabla DIR_RELAC_UNID_ORG_OFIC
CREATE TABLE DIR_RELAC_UNID_ORG_OFIC (
	CODIGO_OFICINA                    			VARCHAR2(9 CHAR)         NOT NULL,
	DENOMINACION_OFICINA              			VARCHAR2(300 CHAR)       NOT NULL,
	CODIGO_UNIDAD_ORGANICA                		VARCHAR2(9 CHAR)         NOT NULL,
	DENOM_UNIDAD_ORGANICA		          		VARCHAR2(300 CHAR)       NOT NULL
);