--Se borra el contenido de las tablas
delete from DIR_OFICINA;
delete from DIR_UNIDAD_ORGANICA;
delete from DIR_ESTADO_ACTUALIZACION;

--Se crea la tabla DIR_RELAC_UNID_ORG_OFIC
CREATE TABLE DIR_RELAC_UNID_ORG_OFIC (
	CODIGO_OFICINA                    			VARCHAR(9)         NOT NULL,
	DENOMINACION_OFICINA              			VARCHAR(300)       NOT NULL,
	CODIGO_UNIDAD_ORGANICA                		VARCHAR(9)         NOT NULL,
	DENOM_UNIDAD_ORGANICA               		VARCHAR(300)       NOT NULL
);
