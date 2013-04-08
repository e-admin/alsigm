--Este script debe de ejecutarse en la BD de registro

--Tabla para los datos adicionales de usuarios
CREATE TABLE iuserdata
(
  id int NOT NULL,
  cargo varchar(256),
  tfno_movil varchar(16),
  id_certificado varchar(256),
  email varchar(256),
  nombre varchar(256),
  apellidos varchar(256)
);

ALTER TABLE iuserdata ADD CONSTRAINT pk_iuserdata PRIMARY KEY (id) ;

CREATE INDEX iuserdata_ix_id ON iuserdata (id);