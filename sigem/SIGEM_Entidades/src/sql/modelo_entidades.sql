CREATE TABLE sgm_adm_entidades
(
  id character varying(3) NOT NULL,
  nombrecorto character varying(70) NOT NULL,
  nombrelargo character varying(500),
  codigo_ine character varying(12),
  CONSTRAINT pk_sgm_adm_entidades PRIMARY KEY (id)
) 
WITHOUT OIDS;
ALTER TABLE sgm_adm_entidades OWNER TO postgres;