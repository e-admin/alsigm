
-- Crear una relación un localizador y el documento compulsado
CREATE TABLE scr_doclocator
(
	bookID integer NOT NULL,
	folderID integer NOT NULL,
	pageID integer NOT NULL,
	locator character varying(128) NOT NULL
);

CREATE UNIQUE INDEX scr_doclocator0 ON scr_doclocator USING btree (locator);
