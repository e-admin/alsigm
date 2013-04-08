--
-- Tabla necesaria por el conector de gestión documental con Sharepoint.
--

CREATE TABLE doc_path (
    guid VARCHAR(256) NOT NULL, 
    path CLOB NOT NULL 
); 

ALTER TABLE doc_path ADD CONSTRAINT pk_doc_path PRIMARY KEY (guid);
