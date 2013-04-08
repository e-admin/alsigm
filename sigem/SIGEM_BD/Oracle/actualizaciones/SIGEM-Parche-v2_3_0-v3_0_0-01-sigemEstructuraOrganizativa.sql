--Este script debe de ejecutarse en la BD de registro

--Tabla para los datos adicionales de usuarios
(
CREATE TABLE iuserdata
  id NUMBER NOT NULL,
  cargo VARCHAR2(256 CHAR),
  tfno_movil VARCHAR2(16),
  id_certificado VARCHAR2(256 CHAR),
  email VARCHAR2(256 CHAR),
  nombre VARCHAR2(256 CHAR),
  apellidos VARCHAR2(256 CHAR)
);

ALTER TABLE iuserdata ADD CONSTRAINT pk_iuserdata PRIMARY KEY (id) ;
	
CREATE INDEX iuserdata_ix_id ON iuserdata (id);