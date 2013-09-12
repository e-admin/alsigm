-- Definición de búsqueda documental sobre la tabla ADVCTEXTCF
UPDATE ADVCTEXTCF SET idxvalor=to_tsvector('default_spanish', "valor");

-- Definición de búsqueda documental sobre la tabla ADVCTEXTDESCR
UPDATE ADVCTEXTDESCR SET idxvalor=to_tsvector('default_spanish', "valor");

-- Definición de búsqueda documental sobre la tabla ADVCTEXTLCF
UPDATE ADVCTEXTLCF SET idxvalor=to_tsvector('default_spanish', "valor");

-- Definición de búsqueda documental sobre la tabla ADVCTEXTLDESCR
UPDATE ADVCTEXTLDESCR SET idxvalor=to_tsvector('default_spanish', "valor");

-- Definición de búsqueda documental sobre la tabla ASGFELEMENTOCF
UPDATE ASGFELEMENTOCF SET idxtitulo=to_tsvector('default_spanish', "titulo");

-- Definición de búsqueda documental sobre la tabla ADDESCRIPTOR
UPDATE ADDESCRIPTOR SET idxnombre=to_tsvector('default_spanish', "nombre");
