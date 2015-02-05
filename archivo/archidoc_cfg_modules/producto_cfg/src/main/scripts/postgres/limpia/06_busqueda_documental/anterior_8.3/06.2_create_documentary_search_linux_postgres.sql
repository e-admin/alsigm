-- Creación del diccionario

DELETE FROM pg_ts_cfg WHERE ts_name = 'default_spanish';
INSERT INTO pg_ts_cfg (ts_name, prs_name, locale)
               VALUES ('default_spanish', 'default', 'es_ES@euro');

DELETE FROM pg_ts_dict WHERE dict_name = 'es_ispell';
INSERT INTO pg_ts_dict
  (SELECT 'es_ispell',dict_init,
                       'DictFile="/usr/local/pgsql/share/contrib/spanish.med",'
                       'AffFile="/usr/local/pgsql/share/contrib/spanish.aff",'
                       'StopFile="/usr/local/pgsql/share/contrib/spanish.stop"',
                       dict_lexize,
                       'Spanish Ispell'
                FROM pg_ts_dict
                WHERE dict_name = 'ispell_template');


DELETE FROM pg_ts_cfgmap WHERE ts_name = 'default_spanish';
INSERT INTO pg_ts_cfgmap (ts_name, tok_alias, dict_name)
               VALUES ('default_spanish', 'lhword', '{es_ispell }');
INSERT INTO pg_ts_cfgmap (ts_name, tok_alias, dict_name)
               VALUES ('default_spanish', 'lpart_hword', '{es_ispell}');
INSERT INTO pg_ts_cfgmap (ts_name, tok_alias, dict_name)
               VALUES ('default_spanish', 'lword', '{es_ispell}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'url', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'host', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'sfloat', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'uri', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'int', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'float', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'email', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'word', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'hword', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'nlword', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'nlpart_hword', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'part_hword', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'nlhword', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'file', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'uint', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'version', '{simple}');

-- Definición de búsqueda documental sobre la tabla ADVCTEXTCF
ALTER TABLE ADVCTEXTCF ADD COLUMN idxvalor tsvector;

UPDATE ADVCTEXTCF SET idxvalor=to_tsvector('default_spanish', "valor");
VACUUM FULL ANALYZE;

CREATE INDEX idxvalor_advctextcf_idx ON ADVCTEXTCF USING gist(idxvalor);
VACUUM FULL ANALYZE;

CREATE TRIGGER idxvalor_advctextcf_trigger BEFORE UPDATE OR INSERT ON ADVCTEXTCF
FOR EACH ROW EXECUTE PROCEDURE tsearch2(idxvalor,"valor");

-- Definición de búsqueda documental sobre la tabla ADVCTEXTDESCR

ALTER TABLE ADVCTEXTDESCR ADD COLUMN idxvalor tsvector;

UPDATE ADVCTEXTDESCR SET idxvalor=to_tsvector('default_spanish', "valor");
VACUUM FULL ANALYZE;

CREATE INDEX idxvalor_advctextdescr_idx ON ADVCTEXTDESCR USING gist(idxvalor);
VACUUM FULL ANALYZE;

CREATE TRIGGER idxvalor_advctextdescr_trigger BEFORE UPDATE OR INSERT ON ADVCTEXTDESCR
FOR EACH ROW EXECUTE PROCEDURE tsearch2(idxvalor,"valor");

-- Definición de búsqueda documental sobre la tabla ADVCTEXTLCF
ALTER TABLE ADVCTEXTLCF ADD COLUMN idxvalor tsvector;

UPDATE ADVCTEXTLCF SET idxvalor=to_tsvector('default_spanish', "valor");
VACUUM FULL ANALYZE;

CREATE INDEX idxvalor_advctextlcf_idx ON ADVCTEXTLCF USING gist(idxvalor);
VACUUM FULL ANALYZE;

CREATE TRIGGER idxvalor_advctextlcf_trigger BEFORE UPDATE OR INSERT ON ADVCTEXTLCF
FOR EACH ROW EXECUTE PROCEDURE tsearch2(idxvalor,"valor");

-- Definición de búsqueda documental sobre la tabla ADVCTEXTLDESCR
ALTER TABLE ADVCTEXTLDESCR ADD COLUMN idxvalor tsvector;

UPDATE ADVCTEXTLDESCR SET idxvalor=to_tsvector('default_spanish', "valor");
VACUUM FULL ANALYZE;

CREATE INDEX idxvalor_advctextldescr_idx ON ADVCTEXTLDESCR USING gist(idxvalor);
VACUUM FULL ANALYZE;

CREATE TRIGGER idxvalor_advctextldescr_trigger BEFORE UPDATE OR INSERT ON ADVCTEXTLDESCR
FOR EACH ROW EXECUTE PROCEDURE tsearch2(idxvalor,"valor");

-- Definición de búsqueda documental sobre la tabla ASGFELEMENTOCF
ALTER TABLE ASGFELEMENTOCF ADD COLUMN idxtitulo tsvector;

UPDATE ASGFELEMENTOCF SET idxtitulo=to_tsvector('default_spanish', "titulo");
VACUUM FULL ANALYZE;

CREATE INDEX idxtitulo_asgelementocf_idx ON ASGFELEMENTOCF USING gist(idxtitulo);
VACUUM FULL ANALYZE;

CREATE TRIGGER idxtitulo_asgelemento_trigger BEFORE UPDATE OR INSERT ON ASGFELEMENTOCF
FOR EACH ROW EXECUTE PROCEDURE tsearch2(idxtitulo,"titulo");


-- Definición de búsqueda documental sobre la tabla ADDESCRIPTOR
ALTER TABLE ADDESCRIPTOR ADD COLUMN idxnombre tsvector;

UPDATE ADDESCRIPTOR SET idxnombre=to_tsvector('default_spanish', "nombre");
VACUUM FULL ANALYZE;

CREATE INDEX idxnombre_addescriptor_idx ON ADDESCRIPTOR USING gist(idxnombre);
VACUUM FULL ANALYZE;

CREATE TRIGGER idxnombre_addescriptor_trigger BEFORE UPDATE OR INSERT ON ADDESCRIPTOR
FOR EACH ROW EXECUTE PROCEDURE tsearch2(idxnombre,"nombre");
