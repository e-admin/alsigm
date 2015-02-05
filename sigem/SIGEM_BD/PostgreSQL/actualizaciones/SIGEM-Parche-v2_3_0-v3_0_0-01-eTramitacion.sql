------------------------------------
-- ACTUALIZACION PARA EL REGISTRO --
------------------------------------

-- Nombre, primer apellido y segundo apellido en la informacion de sesion
ALTER TABLE sgmafsesion_info ADD COLUMN solicitante_firstname varchar(36);
ALTER TABLE sgmafsesion_info ADD COLUMN solicitante_surname varchar(45);
ALTER TABLE sgmafsesion_info ADD COLUMN solicitante_surname2 varchar(45);

----------------------------------------------------------------------------
-- ACTUALIZACION PARA ALMACENAR LA ASOCIACION DE DOCUMENTOS ENTRE GUID Y CSV
----------------------------------------------------------------------------

---
--- Table: sgmrtregistro_docs_csv (Asociacion entre el guid de un documento y el csv generado para dicho documento)
---
CREATE TABLE sgmrtregistro_docs_csv (
	guid character varying (32) NOT NULL,
	csv character varying (128) NOT NULL
);

ALTER TABLE ONLY sgmrtregistro_docs_csv ADD CONSTRAINT sgmrtregistro_docs_csv_pkey PRIMARY KEY (guid);
ALTER TABLE ONLY sgmrtregistro_docs_csv ADD CONSTRAINT sgmrtregistro_docs_csv_key UNIQUE (csv);

CREATE INDEX fki_sgmrtregistro_docs_csv ON sgmrtregistro_docs_csv (guid,csv);

