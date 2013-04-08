-----------------------------------
-- Actualización de v6.2 a v6.3
-----------------------------------


CREATE TABLE doc_path (
    guid VARCHAR(256) NOT NULL, 
    path text NOT NULL 
); 

ALTER TABLE doc_path ADD CONSTRAINT pk_doc_path PRIMARY KEY (guid);
