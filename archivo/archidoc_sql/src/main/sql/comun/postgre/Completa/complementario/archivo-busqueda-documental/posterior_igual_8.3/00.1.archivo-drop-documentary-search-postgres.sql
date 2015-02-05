DROP TEXT SEARCH CONFIGURATION IF EXISTS default_spanish CASCADE;
DROP TEXT SEARCH DICTIONARY IF EXISTS default_spanish CASCADE;

-- Definición de búsqueda documental sobre la tabla ADVCTEXTCF
ALTER TABLE ADVCTEXTCF DROP COLUMN idxvalor;
DROP TRIGGER idxvalor_advctextcf_trigger ON ADVCTEXTCF;

-- Definición de búsqueda documental sobre la tabla ADVCTEXTDESCR
ALTER TABLE ADVCTEXTDESCR DROP COLUMN idxvalor;
DROP TRIGGER idxvalor_advctextdescr_trigger ON ADVCTEXTDESCR;

-- Definición de búsqueda documental sobre la tabla ADVCTEXTLCF
ALTER TABLE ADVCTEXTLCF DROP COLUMN idxvalor;
DROP TRIGGER idxvalor_advctextlcf_trigger ON ADVCTEXTLCF;

-- Definición de búsqueda documental sobre la tabla ADVCTEXTLDESCR
ALTER TABLE ADVCTEXTLDESCR DROP COLUMN idxvalor;
DROP TRIGGER idxvalor_advctextldescr_trigger ON ADVCTEXTLDESCR;

-- Definición de búsqueda documental sobre la tabla ASGFELEMENTOCF
ALTER TABLE ASGFELEMENTOCF DROP COLUMN idxtitulo;
DROP TRIGGER idxtitulo_asgelemento_trigger ON ASGFELEMENTOCF;

-- Definición de búsqueda documental sobre la tabla ADDESCRIPTOR
ALTER TABLE ADDESCRIPTOR DROP COLUMN idxnombre;
DROP TRIGGER idxnombre_addescriptor_trigger ON ADDESCRIPTOR;
