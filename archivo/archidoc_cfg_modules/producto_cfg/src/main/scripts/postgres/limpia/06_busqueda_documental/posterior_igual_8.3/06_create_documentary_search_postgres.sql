-- Creación del diccionario
DROP TEXT SEARCH CONFIGURATION IF EXISTS default_spanish CASCADE;
CREATE TEXT SEARCH CONFIGURATION default_spanish ( COPY = pg_catalog.spanish );

DROP TEXT SEARCH DICTIONARY IF EXISTS default_spanish CASCADE;
CREATE TEXT SEARCH DICTIONARY default_spanish (
    TEMPLATE = ispell,
    DictFile = es_es,
    AffFile = es_es,
    StopWords = es_es
);

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ALTER MAPPING FOR asciiword, asciihword, hword_asciipart,
                      word, hword, hword_part
    WITH default_spanish, default_spanish, spanish_stem;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    DROP MAPPING FOR email, url, url_path, sfloat, float;

-- Definición de búsqueda documental sobre la tabla ADVCTEXTCF
ALTER TABLE ADVCTEXTCF ADD COLUMN idxvalor tsvector;

CREATE INDEX idxvalor_advctextcf_idx ON ADVCTEXTCF USING gist(idxvalor);

CREATE TRIGGER idxvalor_advctextcf_trigger BEFORE UPDATE OR INSERT ON ADVCTEXTCF
FOR EACH ROW EXECUTE PROCEDURE tsvector_update_trigger('idxvalor', 'public.default_spanish', 'valor');

UPDATE ADVCTEXTCF SET idxvalor=to_tsvector('default_spanish', "valor");

-- Definición de búsqueda documental sobre la tabla ADVCTEXTDESCR
ALTER TABLE ADVCTEXTDESCR ADD COLUMN idxvalor tsvector;

CREATE INDEX idxvalor_advctextdescr_idx ON ADVCTEXTDESCR USING gist(idxvalor);

CREATE TRIGGER idxvalor_advctextdescr_trigger BEFORE UPDATE OR INSERT ON ADVCTEXTDESCR
FOR EACH ROW EXECUTE PROCEDURE tsvector_update_trigger('idxvalor', 'public.default_spanish', 'valor');

UPDATE ADVCTEXTDESCR SET idxvalor=to_tsvector('default_spanish', "valor");

-- Definición de búsqueda documental sobre la tabla ADVCTEXTLCF
ALTER TABLE ADVCTEXTLCF ADD COLUMN idxvalor tsvector;

CREATE INDEX idxvalor_advctextlcf_idx ON ADVCTEXTLCF USING gist(idxvalor);

CREATE TRIGGER idxvalor_advctextlcf_trigger BEFORE UPDATE OR INSERT ON ADVCTEXTLCF
FOR EACH ROW EXECUTE PROCEDURE tsvector_update_trigger('idxvalor', 'public.default_spanish', 'valor');

UPDATE ADVCTEXTLCF SET idxvalor=to_tsvector('default_spanish', "valor");

-- Definición de búsqueda documental sobre la tabla ADVCTEXTLDESCR
ALTER TABLE ADVCTEXTLDESCR ADD COLUMN idxvalor tsvector;

CREATE INDEX idxvalor_advctextldescr_idx ON ADVCTEXTLDESCR USING gist(idxvalor);

CREATE TRIGGER idxvalor_advctextldescr_trigger BEFORE UPDATE OR INSERT ON ADVCTEXTLDESCR
FOR EACH ROW EXECUTE PROCEDURE tsvector_update_trigger('idxvalor', 'public.default_spanish', 'valor');

UPDATE ADVCTEXTLDESCR SET idxvalor=to_tsvector('default_spanish', "valor");

-- Definición de búsqueda documental sobre la tabla ASGFELEMENTOCF
ALTER TABLE ASGFELEMENTOCF ADD COLUMN idxtitulo tsvector;

CREATE INDEX idxtitulo_asgelementocf_idx ON ASGFELEMENTOCF USING gist(idxtitulo);

CREATE TRIGGER idxtitulo_asgelemento_trigger BEFORE UPDATE OR INSERT ON ASGFELEMENTOCF
FOR EACH ROW EXECUTE PROCEDURE tsvector_update_trigger('idxtitulo', 'public.default_spanish', 'titulo');

UPDATE ASGFELEMENTOCF SET idxtitulo=to_tsvector('default_spanish', "titulo");

-- Definición de búsqueda documental sobre la tabla ADDESCRIPTOR
ALTER TABLE ADDESCRIPTOR ADD COLUMN idxnombre tsvector;

CREATE INDEX idxnombre_addescriptor_idx ON ADDESCRIPTOR USING gist(idxnombre);

CREATE TRIGGER idxnombre_addescriptor_trigger BEFORE UPDATE OR INSERT ON ADDESCRIPTOR
FOR EACH ROW EXECUTE PROCEDURE tsvector_update_trigger('idxnombre', 'public.default_spanish', 'nombre');

UPDATE ADDESCRIPTOR SET idxnombre=to_tsvector('default_spanish', "nombre");
