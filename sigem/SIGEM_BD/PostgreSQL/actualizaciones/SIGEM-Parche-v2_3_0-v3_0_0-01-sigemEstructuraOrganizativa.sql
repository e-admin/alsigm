--Este script debe de ejecutarse en la BD de registro

--Tabla para los datos adicionales de usuarios
CREATE TABLE iuserdata
(
  id integer NOT NULL,
  cargo character varying(256),
  tfno_movil character varying(16),
  id_certificado character varying(256),
  email character varying(256),
  nombre character varying(256),
  apellidos character varying(256),
  CONSTRAINT pk_iuserdata PRIMARY KEY (id)
);

CREATE INDEX iuserdata_ix_id
   ON iuserdata USING btree (id);